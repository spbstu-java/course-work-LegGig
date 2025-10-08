import core.lab_3.Dictionary;
import core.lab_3.TextTranslator;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import java.io.File;

public class Lab3Tab extends Tab {
    private TextArea outputArea;
    private TextArea inputArea;
    private TextField dictionaryPathField;
    private TextTranslator translator;
    private Button loadDictionaryButton;
    private Button browseDictionaryButton;
    private Button loadTextButton;
    private Button translateButton;
    private Button clearButton;

    public Lab3Tab() {
        setText("Лаб 3: Переводчик");

        initializeComponents();
        setupEventHandlers();
        setupLayout();
        setupKeyboardShortcuts();
    }

    private void initializeComponents() {
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-control-inner-background: #0a0a0a; -fx-text-fill: white; -fx-font-size: 14px;");
        outputArea.setPrefHeight(200);

        inputArea = new TextArea();
        inputArea.setWrapText(true);
        inputArea.setPromptText("Введите текст для перевода здесь...");
        inputArea.setStyle("-fx-control-inner-background: #0a0a0a; -fx-text-fill: white; -fx-font-size: 14px; -fx-prompt-text-fill: #666;");
        inputArea.setPrefHeight(200);

        dictionaryPathField = new TextField();
        dictionaryPathField.setPromptText("Путь к файлу словаря");
        dictionaryPathField.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-border-color: #333; -fx-font-size: 14px;");

        loadDictionaryButton = createStyledButton("Загрузить словарь (Ctrl+L)");
        browseDictionaryButton = createStyledButton("Обзор... (Ctrl+B)");
        loadTextButton = createStyledButton("Загрузить текст (Ctrl+T)");
        translateButton = createStyledButton("Перевести (Ctrl+Enter)");
        clearButton = createStyledButton("Очистить (Ctrl+D)");
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 15; " +
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
                            "-fx-padding: 10 15; " +
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
                            "-fx-padding: 10 15; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        return button;
    }

    private void setupEventHandlers() {
        loadDictionaryButton.setOnAction(e -> loadDictionary());
        browseDictionaryButton.setOnAction(e -> browseDictionary());
        loadTextButton.setOnAction(e -> loadTextFromFile());
        translateButton.setOnAction(e -> translateText());
        clearButton.setOnAction(e -> clearAll());
    }

    private void setupLayout() {
        Label titleLabel = new Label("ПЕРЕВОДЧИК ТЕКСТА");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #494949;");

        Label dictLabel = new Label("Словарь:");
        dictLabel.setStyle("-fx-text-fill: #494949; -fx-font-size: 14px; -fx-font-weight: bold;");

        HBox dictionarySection = new HBox(10);
        dictionarySection.getChildren().addAll(
                dictLabel, dictionaryPathField, loadDictionaryButton, browseDictionaryButton
        );

        HBox textControls = new HBox(10);
        textControls.getChildren().addAll(loadTextButton, clearButton);

        Label inputLabel = new Label("Входной текст:");
        inputLabel.setStyle("-fx-text-fill: #494949; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label outputLabel = new Label("Результат перевода:");
        outputLabel.setStyle("-fx-text-fill: #494949; -fx-font-size: 14px; -fx-font-weight: bold;");

        VBox layout = new VBox(15);
        layout.getChildren().addAll(
                titleLabel,
                dictionarySection,
                textControls,
                inputLabel,
                inputArea,
                new HBox(translateButton),
                outputLabel,
                outputArea
        );

        VBox.setVgrow(inputArea, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        setContent(layout);
    }

    private void setupKeyboardShortcuts() {
        KeyCombination loadDictCombo = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        KeyCombination browseCombo = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
        KeyCombination loadTextCombo = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
        KeyCombination translateCombo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
        KeyCombination clearCombo = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);

        getContent().setOnKeyPressed(event -> {
            if (loadDictCombo.match(event)) {
                loadDictionary();
                event.consume();
            } else if (browseCombo.match(event)) {
                browseDictionary();
                event.consume();
            } else if (loadTextCombo.match(event)) {
                loadTextFromFile();
                event.consume();
            } else if (translateCombo.match(event)) {
                translateText();
                event.consume();
            } else if (clearCombo.match(event)) {
                clearAll();
                event.consume();
            }
        });

        loadDictionaryButton.setTooltip(new javafx.scene.control.Tooltip("Загрузить словарь из указанного пути"));
        browseDictionaryButton.setTooltip(new javafx.scene.control.Tooltip("Выбрать файл словаря через диалог"));
        loadTextButton.setTooltip(new javafx.scene.control.Tooltip("Загрузить текст из файла"));
        translateButton.setTooltip(new javafx.scene.control.Tooltip("Выполнить перевод текста"));
        clearButton.setTooltip(new javafx.scene.control.Tooltip("Очистить все поля"));
    }

    private void loadDictionary() {
        try {
            String path = dictionaryPathField.getText().trim();
            if (path.isEmpty()) {
                outputArea.setText("Укажите путь к файлу словаря");
                return;
            }

            Dictionary dictionary = Dictionary.loadFromFile(path);
            translator = new TextTranslator(dictionary);
            outputArea.setText("Словарь загружен успешно. Записей: " +
                    dictionary.entries().size());

            showSuccessFeedback(loadDictionaryButton);
        } catch (Exception e) {
            outputArea.setText("Ошибка загрузки словаря: " + e.getMessage());
            showErrorFeedback(loadDictionaryButton);
        }
    }

    private void browseDictionary() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл словаря");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt")
        );

        File file = fileChooser.showOpenDialog(((Stage) getTabPane().getScene().getWindow()));
        if (file != null) {
            dictionaryPathField.setText(file.getAbsolutePath());
            showSuccessFeedback(browseDictionaryButton);
        }
    }

    private void loadTextFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с текстом");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*")
        );

        File file = fileChooser.showOpenDialog(((Stage) getTabPane().getScene().getWindow()));
        if (file != null) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                inputArea.setText(content);
                showSuccessFeedback(loadTextButton);
            } catch (Exception e) {
                outputArea.setText("Ошибка чтения файла: " + e.getMessage());
                showErrorFeedback(loadTextButton);
            }
        }
    }

    private void translateText() {
        if (translator == null) {
            outputArea.setText("Сначала загрузите словарь!");
            showErrorFeedback(translateButton);
            return;
        }

        String text = inputArea.getText();
        if (text.isEmpty()) {
            outputArea.setText("Введите текст для перевода");
            showErrorFeedback(translateButton);
            return;
        }

        try {
            String result = translator.translate(text);
            outputArea.setText(result);
            showSuccessFeedback(translateButton);
        } catch (Exception e) {
            outputArea.setText("Ошибка перевода: " + e.getMessage());
            showErrorFeedback(translateButton);
        }
    }

    private void clearAll() {
        inputArea.clear();
        outputArea.clear();
        dictionaryPathField.clear();
        showSuccessFeedback(clearButton);
    }

    private void showSuccessFeedback(Button button) {
        String originalStyle = button.getStyle();
        button.setStyle(
                "-fx-background-color: rgba(0, 100, 0, 0.6); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 15; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

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
                        "-fx-padding: 10 15; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.millis(300)
        );
        pause.setOnFinished(e -> button.setStyle(originalStyle));
        pause.play();
    }
}