import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import java.lang.reflect.Method;

import core.lab_2.TestClass;
import core.lab_2.InvokeTimes;

public class Lab2Tab extends Tab {
    private final TextArea outputArea;
    private final Button executeButton;

    public Lab2Tab() {
        setText("Лаб 2: Аннотации");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-control-inner-background: #0a0a0a; -fx-text-fill: white; -fx-font-size: 14px;");

        executeButton = new Button("Выполнить (Ctrl+E)");
        executeButton.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        executeButton.setOnMouseEntered(e -> {
            executeButton.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        executeButton.setOnMouseExited(e -> {
            executeButton.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        executeButton.setOnAction(e -> executeLab2());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new HBox(executeButton),
                outputArea
        );

        setContent(layout);

        setupKeyboardShortcuts();
    }

    private void setupKeyboardShortcuts() {
        KeyCombination executeCombo = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);

        getContent().setOnKeyPressed(event -> {
            if (executeCombo.match(event)) {
                executeLab2();
                event.consume();
            }
        });

        executeButton.setTooltip(new javafx.scene.control.Tooltip("Нажмите Ctrl+E для быстрого выполнения"));
    }

    private void executeLab2() {
        StringBuilder output = new StringBuilder();
        try {
            TestClass testInstance = new TestClass();
            invokeAnnotatedMethods(testInstance, output);
        } catch (Exception ex) {
            output.append("Ошибка: ").append(ex.getMessage());
        }
        outputArea.setText(output.toString());

        executeButton.setStyle(
                "-fx-background-color: rgba(0, 100, 0, 0.6); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;"
        );

        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.millis(300)
        );
        pause.setOnFinished(e -> {
            executeButton.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });
        pause.play();
    }

    private static void invokeAnnotatedMethods(Object instance, StringBuilder output) {
        Class<?> clazz = instance.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InvokeTimes.class)) {
                if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                    continue;
                }

                InvokeTimes annotation = method.getAnnotation(InvokeTimes.class);
                int times = annotation.value();

                method.setAccessible(true);

                Class<?>[] paramTypes = method.getParameterTypes();
                Object[] params = createDefaultValues(paramTypes);

                output.append("Вызов метода: ").append(method.getName())
                        .append(" ").append(times).append(" раз\n");

                for (int i = 0; i < times; i++) {
                    try {
                        method.invoke(instance, params);
                        output.append("Успешный вывод\n");
                    } catch (Exception e) {
                        output.append("Ошибка вызова метода ").append(method.getName())
                                .append(": ").append(e.getMessage()).append("\n");
                    }
                }
            }
        }
    }

    private static Object[] createDefaultValues(Class<?>[] paramTypes) {
        Object[] values = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];
            values[i] = switch (type.getName()) {
                case "int" -> 0;
                case "long" -> 0L;
                case "double" -> 0.0;
                case "float" -> 0.0f;
                case "boolean" -> false;
                case "char" -> 'A';
                case "byte" -> (byte) 0;
                case "short" -> (short) 0;
                case "java.lang.String" -> "default";
                default -> null;
            };
        }
        return values;
    }
}