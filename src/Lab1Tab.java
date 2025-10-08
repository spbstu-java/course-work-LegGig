import core.lab_1.*;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;

public class Lab1Tab extends Tab {
    private final TextArea outputArea;
    private final TextField positionField;
    private final TextField fromField;
    private final TextField toField;
    private final ComboBox<String> strategyComboBox;
    private final Hero hero;
    private MovementStrategy[] strategies;

    public Lab1Tab() {
        setText("Лаб 1: Стратегии перемещения");

        hero = new Hero();
        initializeStrategies();

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(300);
        outputArea.setStyle("-fx-control-inner-background: #0a0a0a; -fx-text-fill: white; -fx-font-size: 14px;");

        positionField = new TextField();
        positionField.setEditable(false);
        positionField.setText(hero.getCurrentPosition());
        positionField.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 14px;");

        fromField = new TextField();
        fromField.setPromptText("Начальная точка");
        fromField.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-border-color: #333; -fx-font-size: 14px;");

        toField = new TextField();
        toField.setPromptText("Конечная точка");
        toField.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-border-color: #333; -fx-font-size: 14px;");

        strategyComboBox = new ComboBox<>();
        strategyComboBox.setItems(FXCollections.observableArrayList(
                "Пешком", "На лошади", "На машине", "Полёт", "Телепортация"
        ));

        strategyComboBox.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                        "-fx-border-width: 1; " +
                        "-fx-background-radius: 4; " +
                        "-fx-border-radius: 4;"
        );

        strategyComboBox.setCellFactory(lv -> {
            var cell = new javafx.scene.control.ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle(
                                "-fx-background-color: rgba(0, 0, 0, 0.8); " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 14px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-padding: 8 12;"
                        );
                    }
                }
            };

            // Эффект при наведении на элементы списка
            cell.setOnMouseEntered(e -> {
                cell.setStyle(
                        "-fx-background-color: rgba(0, 0, 0, 0.9); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 12;"
                );
            });

            cell.setOnMouseExited(e -> {
                if (!cell.isEmpty()) {
                    cell.setStyle(
                            "-fx-background-color: rgba(0, 0, 0, 0.8); " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-padding: 8 12;"
                    );
                }
            });

            return cell;
        });

        Button selectStrategyBtn = createTransparentButton("Выбрать стратегию");
        Button moveToBtn = createTransparentButton("Переместиться в точку");
        Button moveFromToBtn = createTransparentButton("Переместиться из точки в точку");
        Button setPositionBtn = createTransparentButton("Установить позицию");
        Button showHistoryBtn = createTransparentButton("Показать историю");
        Button currentStrategyBtn = createTransparentButton("Текущая стратегия");
        Button clearBtn = createTransparentButton("Очистить");

        selectStrategyBtn.setOnAction(e -> selectStrategy());
        moveToBtn.setOnAction(e -> moveTo());
        moveFromToBtn.setOnAction(e -> moveFromTo());
        setPositionBtn.setOnAction(e -> setPosition());
        showHistoryBtn.setOnAction(e -> showHistory());
        currentStrategyBtn.setOnAction(e -> showCurrentStrategy());
        clearBtn.setOnAction(e -> clearOutput());

        Label titleLabel = new Label("УПРАВЛЕНИЕ ПЕРЕМЕЩЕНИЯМИ ГЕРОЯ");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #494949;");
        StackPane centeredTitle = new StackPane(titleLabel);
        centeredTitle.setAlignment(Pos.CENTER);

        GridPane controlGrid = new GridPane();
        controlGrid.setHgap(10);
        controlGrid.setVgap(12);
        controlGrid.setAlignment(Pos.CENTER);
        controlGrid.setPadding(new Insets(15));

        Label currentPosLabel = createTransparentLabel("Текущая позиция:");
        currentPosLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #494949; -fx-padding: 0 5 0 0;");

        Label strategyLabel = createTransparentLabel("Стратегия:");
        strategyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #494949; -fx-padding: 0 5 0 0;");

        Label fromLabel = createTransparentLabel("Из:");
        fromLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #494949; -fx-padding: 0 5 0 0;");

        Label toLabel = createTransparentLabel("В:");
        toLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #494949; -fx-padding: 0 5 0 0;");

        GridPane.setHalignment(currentPosLabel, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(strategyLabel, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(fromLabel, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(toLabel, javafx.geometry.HPos.RIGHT);

        positionField.setPadding(new Insets(5, 8, 5, 8));
        fromField.setPadding(new Insets(5, 8, 5, 8));
        toField.setPadding(new Insets(5, 8, 5, 8));
        strategyComboBox.setPadding(new Insets(2, 8, 2, 8));

        controlGrid.add(currentPosLabel, 0, 0);
        controlGrid.add(positionField, 1, 0);
        controlGrid.add(setPositionBtn, 2, 0);

        controlGrid.add(strategyLabel, 0, 1);
        controlGrid.add(strategyComboBox, 1, 1);
        controlGrid.add(selectStrategyBtn, 2, 1);

        controlGrid.add(fromLabel, 0, 2);
        controlGrid.add(fromField, 1, 2);
        controlGrid.add(moveFromToBtn, 2, 2);

        controlGrid.add(toLabel, 0, 3);
        controlGrid.add(toField, 1, 3);
        controlGrid.add(moveToBtn, 2, 3);

        HBox buttonRow = new HBox(10, showHistoryBtn, currentStrategyBtn, clearBtn); // Уменьшено расстояние между кнопками
        buttonRow.setAlignment(Pos.CENTER);

        controlGrid.add(buttonRow, 0, 4, 3, 1);

        VBox layout = new VBox(15); // Уменьшено расстояние между элементами
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20)); // Уменьшены отступы
        layout.getChildren().addAll(
                centeredTitle,
                controlGrid,
                outputArea
        );

        setContent(layout);
    }

    private Button createTransparentButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20; " + // Уменьшены отступы
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
                            "-fx-padding: 10 20; " + // Уменьшены отступы
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
                            "-fx-padding: 10 20; " + // Уменьшены отступы
                            "-fx-background-radius: 4; " +
                            "-fx-border-radius: 4; " +
                            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-width: 1; " +
                            "-fx-cursor: hand;"
            );
        });

        return button;
    }

    private Label createTransparentLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        return label;
    }

    private void initializeStrategies() {
        strategies = new MovementStrategy[] {
                new WalkingStrategy(),
                new HorseRidingStrategy(),
                new CarStrategy(),
                new FlyingStrategy(),
                new TeleportStrategy()
        };
    }

    private void selectStrategy() {
        int selectedIndex = strategyComboBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            hero.setMovementStrategy(strategies[selectedIndex]);
            outputArea.appendText("Выбрана стратегия: " + strategies[selectedIndex].getDescription() + "\n");
        } else {
            outputArea.appendText("Сначала выберите стратегию из списка!\n");
        }
    }

    private void moveTo() {
        String to = toField.getText().trim();
        if (to.isEmpty()) {
            outputArea.appendText("Введите конечную точку!\n");
            return;
        }
        if (hero.getCurrentStrategy() == null) {
            outputArea.appendText("Сначала выберите стратегию перемещения!\n");
            return;
        }

        outputArea.appendText("Перемещение в '" + to + "'...\n");
        hero.move(to);
        positionField.setText(hero.getCurrentPosition());
        toField.clear();
    }

    private void moveFromTo() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        if (from.isEmpty() || to.isEmpty()) {
            outputArea.appendText("Введите начальную и конечную точки!\n");
            return;
        }
        if (hero.getCurrentStrategy() == null) {
            outputArea.appendText("Сначала выберите стратегию перемещения!\n");
            return;
        }

        outputArea.appendText("Перемещение из '" + from + "' в '" + to + "'...\n");
        hero.move(from, to);
        positionField.setText(hero.getCurrentPosition());
        fromField.clear();
        toField.clear();
    }

    private void setPosition() {
        String newPosition = toField.getText().trim();
        if (newPosition.isEmpty()) {
            outputArea.appendText("Введите новую позицию!\n");
            return;
        }

        hero.setPosition(newPosition);
        positionField.setText(hero.getCurrentPosition());
        outputArea.appendText("Позиция установлена: " + newPosition + "\n");
        toField.clear();
    }

    private void showHistory() {
        outputArea.appendText("=== История перемещений ===\n");
        printMoveHistoryToOutput();
        outputArea.appendText("======================\n");
    }

    private void printMoveHistoryToOutput() {
        outputArea.appendText("Текущее положение: " + hero.getCurrentPosition() + "\n");

        MovementStrategy current = hero.getCurrentStrategy();
        if (current != null) {
            outputArea.appendText("Текущая стратегия: " + current.getDescription() + "\n");
        } else {
            outputArea.appendText("Стратегия не выбрана\n");
        }
    }

    private void showCurrentStrategy() {
        MovementStrategy current = hero.getCurrentStrategy();
        if (current != null) {
            outputArea.appendText("Текущий способ перемещения: " + current.getDescription() + "\n");
        } else {
            outputArea.appendText("Способ перемещения не выбран\n");
        }
    }

    private void clearOutput() {
        outputArea.clear();
    }
}