package front_end.screens;

import back_end.Parser;
import back_end.command.To;
import back_end.model_info.Model;
import front_end.RunSlogo;
import front_end.uitools.TextGenerator;
import front_end.utils.Dialogs;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static front_end.Dimensions.ENV_WINDOW_HEIGHT;
import static front_end.Dimensions.ENV_WINDOW_WIDTH;
import static front_end.RunSlogo.*;

public class CustomCommandsScreen {
    public CustomCommandsScreen(Map<String, To> customs, Model model) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Commands");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Your commands");
        label.setLayoutX(25);
        label.setLayoutY(25);
        label.setFont(RunSlogo.sofiaPro);

        String allCommands = String.join(", ", customs.keySet().toArray(new String[0]));
        if (!allCommands.isEmpty() && !allCommands.isBlank()) {
            if (customs.keySet().size() > 1)
                allCommands = allCommands.substring(0, allCommands.lastIndexOf(", "));
        } else {
            allCommands = "No custom commands found";
        }


        TextArea taInactive = new TextArea();
        taInactive.setMaxHeight(360);
        taInactive.setPrefHeight(360);
        taInactive.setViewOrder(0);
        taInactive.setBorder(Border.EMPTY);
        taInactive.setBackground(Background.EMPTY);
        taInactive.setMaxWidth(250);
        taInactive.setPrefWidth(250);

        taInactive.setFont(sofiaProSmall);
        taInactive.setLayoutX(25);
        taInactive.setEditable(false);
        taInactive.setLayoutY(65);
        taInactive.setStyle(".text-area { -fx-text-box-border: none; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #F8F8FF;} .text-area > .scroll-pane{\n" +
                "\n" +
                "-fx-vbar-policy:always; /* Control the vertical ScrollBar (always,needed,never) */\n" +
                "-fx-hbar-policy:never; /* Control the horizontal ScrollBar (always,needed,never) */\n" +
                "\n" +
                "}");

        taInactive.setText(allCommands);

        var t = new Text("Select a command above and press\nthe run button on the right");
        t.setFont(bebasKai);
        t.setFill(Color.SLATEGREY);
        t.setLayoutX(25);
        t.setLayoutY(450);

        var b = new Button("Run");
        b.setLayoutX(300 - 65);
        b.setLayoutY(435);
        b.setOnMouseClicked(e -> {
            if (!taInactive.getSelectedText().isBlank() && !taInactive.getSelectedText().isEmpty()) {
                TextInputDialog dialog = new TextInputDialog("");

                dialog.setTitle("Parameters");
                dialog.setHeaderText("Enter parameters for this command");
                dialog.setContentText("Separated by spaces");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(res -> {
                    try {
                        model.executeCommands(new Parser().parse((taInactive.getSelectedText().toLowerCase() + " " + res).split("\\s+")));
                    } catch (Exception ex) {
                        Dialogs.showAlert("Invalid command: " + ex.getMessage());
                    }
                });
            }
        });

        ((Group)scene.getRoot()).getChildren().addAll(label, taInactive, t, b);

        stage.setScene(scene);
        stage.show();
    }
}
