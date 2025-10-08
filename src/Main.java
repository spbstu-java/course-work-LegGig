import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Image appIcon = new Image("file:src/view/icon.png");
            primaryStage.getIcons().add(appIcon);
        } catch (Exception e) {
            System.err.println("Не удалось загрузить иконку приложения: " + e.getMessage());
        }

        TabPane tabPane = new TabPane();

        Tab lab1Tab = new Lab1Tab();
        Tab lab2Tab = new Lab2Tab();
        Tab lab3Tab = new Lab3Tab();
        Tab lab4Tab = new Lab4Tab();

        tabPane.getTabs().addAll(lab1Tab, lab2Tab, lab3Tab, lab4Tab);

        StackPane root = new StackPane();

        try {
            Image gifImage = new Image("file:src/view/Destiny-2-Loading-screen-with-sound-fx-looped.gif");
            ImageView imageView = new ImageView(gifImage);

            imageView.setPreserveRatio(false);
            imageView.fitWidthProperty().bind(root.widthProperty());
            imageView.fitHeightProperty().bind(root.heightProperty());

            root.getChildren().addAll(imageView, tabPane);

        } catch (Exception e) {
            System.err.println("Ошибка загрузки фона: " + e.getMessage());

            try {
                BackgroundImage background = getBackgroundImage();
                tabPane.setBackground(new Background(background));
            } catch (Exception ex) {
                tabPane.setStyle("-fx-background-color: #2c3e50;");
            }

            root.getChildren().add(tabPane);
        }

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Курсовая работа - Лабораторные работы 1-4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static BackgroundImage getBackgroundImage() {
        Image backgroundImage = new Image("file:src/view/destiny-2-loading-1920x1080.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        return new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
    }

}