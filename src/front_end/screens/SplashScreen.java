package front_end.screens;

import front_end.RunSlogo;
import front_end.uitools.ButtonGenerator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static front_end.Dimensions.SHADOW_ADJUSTMENT;
import static front_end.Dimensions.VERTICAL_PADDING;
import static front_end.RunSlogo.*;
import static front_end.uitools.TextGenerator.makeText;

public class SplashScreen {
    public Stage createSplashScreen(Stage stage, RunSlogo context) {
        var root = new Group();
        var container = new Group();
        root.getChildren().add(container);
        // create a place to see the shapes
        var scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);

        container.getChildren().add(makeText("SLogo", sofiaPro, Color.MIDNIGHTBLUE,
                scene.getWidth() / 2,
                scene.getHeight() / 5));
        container.getChildren().add(makeText("What do you want to do?", bebasKai, Color.MIDNIGHTBLUE,
                scene.getWidth() / 2,
                scene.getHeight() / 5 + VERTICAL_PADDING));
        var newEnvButton = ButtonGenerator.createButton("New SLogo Environment");
        newEnvButton.setLayoutX(scene.getWidth() / 2 - newEnvButton.getLayoutBounds().getWidth() / 2 + SHADOW_ADJUSTMENT);
        newEnvButton.setLayoutY(scene.getWidth() / 2.5 - newEnvButton.getLayoutBounds().getHeight() / 2);
        newEnvButton.setOnMouseClicked(e -> context.loadMainScreen());

        var loadEnvButton = ButtonGenerator.createButton("Load SLogo Environment");
        loadEnvButton.setLayoutX(scene.getWidth() / 2 - loadEnvButton.getLayoutBounds().getWidth() / 2 + SHADOW_ADJUSTMENT);
        loadEnvButton.setLayoutY(newEnvButton.getLayoutY() + newEnvButton.getLayoutBounds().getHeight());
        loadEnvButton.setOnMouseClicked(e -> context.loadMainScreenAndLoad());

        var helpButton = ButtonGenerator.createButton("Help");
        helpButton.setLayoutX(scene.getWidth() / 2 - helpButton.getLayoutBounds().getWidth() / 2 + SHADOW_ADJUSTMENT);
        helpButton.setLayoutY(loadEnvButton.getLayoutY() + loadEnvButton.getLayoutBounds().getHeight());
        helpButton.setOnMouseClicked(e -> {
            context.getHostServices().showDocument("https://www2.cs.duke.edu/courses/spring19/compsci308/assign/03_slogo/commands.php");
        });
        container.getChildren().addAll(newEnvButton, loadEnvButton, helpButton);

        scene.setRoot(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(WINDOW_TITLE);

        return stage;
    }
}
