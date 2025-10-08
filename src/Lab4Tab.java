import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import java.util.*;
import java.util.stream.Collectors;

public class Lab4Tab extends Tab {
    private final TextArea outputArea;
    private final Map<String, TextField> inputFields;
    private final Map<String, Button> methodButtons;
    private Button clearButton;
    private Button executeAllButton;

    public Lab4Tab() {
        setText("Лаб 4: Потоки");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-control-inner-background: #0a0a0a; -fx-text-fill: white; -fx-font-size: 14px;");
        outputArea.setPrefHeight(300);

        inputFields = new HashMap<>();
        methodButtons = new HashMap<>();

        // Создание layout
        setupLayout();

        // Установка горячих клавиш
        setupKeyboardShortcuts();
    }

    private void setupLayout() {
        Label titleLabel = new Label("РАБОТА СО ПОТОКАМИ ДАННЫХ");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #494949;");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(15);
        inputGrid.setVgap(12);
        inputGrid.setStyle("-fx-padding: 15;");

        // Поля ввода для каждого метода
        addMethodInput(inputGrid, "average", "Среднее значение чисел:", "1", 0);
        addMethodInput(inputGrid, "toNewStrings", "Строки в верхнем регистре:", "2", 1);
        addMethodInput(inputGrid, "squaresOfDistinct", "Квадраты уникальных чисел:", "3", 2);
        addMethodInput(inputGrid, "lastElement", "Последний элемент коллекции:", "4", 3);
        addMethodInput(inputGrid, "sumOfEvenNumbers", "Сумма четных чисел:", "5", 4);
        addMethodInput(inputGrid, "toMapByFirstChar", "Map по первому символу:", "6", 5);

        // Кнопки управления
        clearButton = createStyledButton("Очистить все (Ctrl+D)");
        executeAllButton = createStyledButton("Выполнить все (Ctrl+A)");

        clearButton.setOnAction(e -> clearAll());
        executeAllButton.setOnAction(e -> executeAllMethods());

        HBox controlButtons = new HBox(15);
        controlButtons.getChildren().addAll(executeAllButton, clearButton);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(
                titleLabel,
                new Label("Входные данные для методов:"),
                inputGrid,
                controlButtons,
                new Label("Результаты:"),
                outputArea
        );

        VBox.setVgrow(outputArea, Priority.ALWAYS);

        setContent(layout);
    }

    private void addMethodInput(GridPane grid, String methodName, String label, String hotkey, int row) {
        Label methodLabel = new Label(label);
        methodLabel.setStyle("-fx-text-fill: #494949; -fx-font-size: 14px; -fx-font-weight: bold;");

        TextField inputField = new TextField();
        inputField.setPrefWidth(300);
        inputField.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-text-fill: white; -fx-border-color: #333; -fx-font-size: 14px;");

        switch (methodName) {
            case "average" -> inputField.setPromptText("Например: 1, 2, 3, 4, 5");
            case "toNewStrings" -> inputField.setPromptText("Например: apple, banana, cherry");
            case "squaresOfDistinct" -> inputField.setPromptText("Например: 1, 2, 2, 3, 3, 3");
            case "lastElement" -> inputField.setPromptText("Например: first, second, third");
            case "sumOfEvenNumbers" -> inputField.setPromptText("Например: 1, 2, 3, 4, 5, 6");
            case "toMapByFirstChar" -> inputField.setPromptText("Например: apple, banana, avocado");
        }

        Button executeButton = createStyledButton("Выполнить (Ctrl+" + hotkey + ")");
        executeButton.setOnAction(e -> executeMethod(methodName, inputField.getText()));

        inputFields.put(methodName, inputField);
        methodButtons.put(methodName, executeButton);

        grid.add(methodLabel, 0, row);
        grid.add(inputField, 1, row);
        grid.add(executeButton, 2, row);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8 15; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8 15; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        return button;
    }

    private void setupKeyboardShortcuts() {
        KeyCombination[] methodCombos = {
                new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN),
                new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN),
                new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN),
                new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN),
                new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN),
                new KeyCodeCombination(KeyCode.DIGIT6, KeyCombination.CONTROL_DOWN)
        };

        String[] methodNames = {"average", "toNewStrings", "squaresOfDistinct", "lastElement", "sumOfEvenNumbers", "toMapByFirstChar"};

        KeyCombination clearCombo = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        KeyCombination executeAllCombo = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);

        getContent().setOnKeyPressed(event -> {
            for (int i = 0; i < methodCombos.length; i++) {
                if (methodCombos[i].match(event)) {
                    String methodName = methodNames[i];
                    String input = inputFields.get(methodName).getText();
                    executeMethod(methodName, input);
                    event.consume();
                    return;
                }
            }

            if (clearCombo.match(event)) {
                clearAll();
                event.consume();
            } else if (executeAllCombo.match(event)) {
                executeAllMethods();
                event.consume();
            }
        });

        methodButtons.get("average").setTooltip(new javafx.scene.control.Tooltip("Вычислить среднее значение чисел"));
        methodButtons.get("toNewStrings").setTooltip(new javafx.scene.control.Tooltip("Преобразовать строки в верхний регистр"));
        methodButtons.get("squaresOfDistinct").setTooltip(new javafx.scene.control.Tooltip("Получить квадраты уникальных чисел"));
        methodButtons.get("lastElement").setTooltip(new javafx.scene.control.Tooltip("Получить последний элемент коллекции"));
        methodButtons.get("sumOfEvenNumbers").setTooltip(new javafx.scene.control.Tooltip("Вычислить сумму четных чисел"));
        methodButtons.get("toMapByFirstChar").setTooltip(new javafx.scene.control.Tooltip("Создать Map по первому символу строк"));

        executeAllButton.setTooltip(new javafx.scene.control.Tooltip("Выполнить все методы последовательно"));
        clearButton.setTooltip(new javafx.scene.control.Tooltip("Очистить все поля ввода и результаты"));
    }

    private void executeMethod(String methodName, String input) {
        try {
            String result = switch (methodName) {
                case "average" -> executeAverage(input);
                case "toNewStrings" -> executeToNewStrings(input);
                case "squaresOfDistinct" -> executeSquaresOfDistinct(input);
                case "lastElement" -> executeLastElement(input);
                case "sumOfEvenNumbers" -> executeSumOfEvenNumbers(input);
                case "toMapByFirstChar" -> executeToMapByFirstChar(input);
                default -> "Неизвестный метод";
            };

            outputArea.appendText("=== " + methodName.toUpperCase() + " ===\n");
            outputArea.appendText("Вход: " + input + "\n");
            outputArea.appendText("Результат: " + result + "\n\n");
            showSuccessFeedback(methodButtons.get(methodName));
        } catch (Exception e) {
            outputArea.appendText("=== " + methodName.toUpperCase() + " - ОШИБКА ===\n");
            outputArea.appendText("Вход: " + input + "\n");
            outputArea.appendText("Ошибка: " + e.getMessage() + "\n\n");
            showErrorFeedback(methodButtons.get(methodName));
        }
    }

    private void executeAllMethods() {
        outputArea.appendText("=== ВЫПОЛНЕНИЕ ВСЕХ МЕТОДОВ ===\n\n");

        for (String methodName : inputFields.keySet()) {
            String input = inputFields.get(methodName).getText();
            if (!input.trim().isEmpty()) {
                executeMethod(methodName, input);
            }
        }

        showSuccessFeedback(executeAllButton);
    }

    private void clearAll() {
        outputArea.clear();
        for (TextField field : inputFields.values()) {
            field.clear();
        }
        showSuccessFeedback(clearButton);
    }

    private String executeAverage(String input) {
        List<Integer> numbers = Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        OptionalDouble result = core.lab_4.Lab_4.average(numbers);
        return result.isPresent() ? String.format("%.2f", result.getAsDouble()) : "Пустой список";
    }

    private String executeToNewStrings(String input) {
        List<String> strings = Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> result = core.lab_4.Lab_4.toNewStrings(strings);
        return String.join(", ", result);
    }

    private String executeSquaresOfDistinct(String input) {
        List<Integer> numbers = Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> result = core.lab_4.Lab_4.squaresOfDistinct(numbers);
        return result.toString();
    }

    private String executeLastElement(String input) {
        List<String> elements = Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        String result = core.lab_4.Lab_4.lastElement(elements);
        return result != null ? result : "Пустая коллекция";
    }

    private String executeSumOfEvenNumbers(String input) {
        int[] numbers = Arrays.stream(input.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int result = core.lab_4.Lab_4.sumOfEvenNumbers(numbers);
        return String.valueOf(result);
    }

    private String executeToMapByFirstChar(String input) {
        List<String> strings = Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        Map<Character, String> result = core.lab_4.Lab_4.toMapByFirstChar(strings);
        return result.toString();
    }

    private void showSuccessFeedback(Button button) {
        String originalStyle = button.getStyle();
        button.setStyle(
                "-fx-background-color: rgba(0, 100, 0, 0.6); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        // Возвращаем исходный стиль через короткое время
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.millis(300)
        );
        pause.setOnFinished(e -> button.setStyle(originalStyle));
        pause.play();
    }

    private void showErrorFeedback(Button button) {
        String originalStyle = button.getStyle();
        button.setStyle(
                "-fx-background-color: rgba(100, 0, 0, 0.6); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        // Возвращаем исходный стиль через короткое время
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.millis(300)
        );
        pause.setOnFinished(e -> button.setStyle(originalStyle));
        pause.play();
    }
}