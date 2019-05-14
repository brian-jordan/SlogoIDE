package front_end.screens;

import back_end.Parser;
import back_end.command.Command;
import back_end.model_info.Model;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import front_end.RunSlogo;
import front_end.uitools.TextGenerator;
import front_end.uitools.ToggleSwitch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class JoystickScreen {
    public JoystickScreen(Model model, ImmutableState state) {
        var stage = new Stage();
        stage.setTitle("Joystick");
        var container = new Group();
        stage.setScene(new Scene(container, 200, 200));
        stage.show();
        stage.setResizable(false);

        var moveTurtleText = TextGenerator.makeTextRelative("Move turtle", RunSlogo.sofiaProSmall,
                Color.SLATEGREY, stage.getWidth()/2, 35);

        var left = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/stepleft.png")));
        var right = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/stepright.png")));
        var forw = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/speedup.png")));
        var back = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/speeddown.png")));

        left.setX(7.5); left.setY(35);
        right.setX(57.5); right.setY(35);
        forw.setX(107.5); forw.setY(35);
        back.setX(157.5); back.setY(35);

        left.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse("lt 45".split(" "));
            model.executeCommands(commands);
        });

        right.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse("rt 45".split(" "));
            model.executeCommands(commands);
        });

        forw.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse("fd 25".split(" "));
            model.executeCommands(commands);
        });

        back.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse("bk 25".split(" "));
            model.executeCommands(commands);
        });

        var thick = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/speedup.png")));
        var thin = new ImageView(new Image(JoystickScreen.class.getResourceAsStream("/img/speeddown.png")));
        var thicknessText = TextGenerator.makeTextRelative("Set thickness", RunSlogo.sofiaProSmall,
                Color.SLATEGREY, stage.getWidth()/2, 100);
        var actualThickness = TextGenerator.makeTextRelative(state.getImmutableTurtles().get(0).getPenSize()+"", RunSlogo.sofiaPro,
                Color.SLATEGREY, stage.getWidth()/2, 140);

        thick.setX(50); thick.setY(100);
        thin.setX(120); thin.setY(100);

        thick.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse(new String[] {"setpensize", (Integer.valueOf(actualThickness.getText())+1)+""});
            model.executeCommands(commands);
            actualThickness.setText((Integer.valueOf(actualThickness.getText())+1)+"");
        });

        thin.setOnMouseClicked(e -> {
            List<Command> commands = new Parser().parse(new String[] {"setpensize", (Integer.valueOf(actualThickness.getText())-1)+""});
            model.executeCommands(commands);
            actualThickness.setText((Integer.valueOf(actualThickness.getText())-1)+"");
        });

        ToggleSwitch toggleSwitch = new ToggleSwitch(state.getImmutableTurtles().get(0).isPendown(), () -> {
            List<Command> commands = new Parser().parse(new String[] {"pu"});
            model.executeCommands(commands);
        }, () -> {
            List<Command> commands = new Parser().parse(new String[] {"pd"});
            model.executeCommands(commands);
        });
        toggleSwitch.setLayoutX(stage.getWidth()/2 - toggleSwitch.getLayoutBounds().getWidth()/2);
        toggleSwitch.setLayoutY(150);

        container.getChildren().addAll(moveTurtleText, left, right, forw, back, thick, thin, thicknessText,
                actualThickness, toggleSwitch);

    }
}
