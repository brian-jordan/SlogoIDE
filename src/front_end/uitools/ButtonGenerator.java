package front_end.uitools;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static front_end.RunSlogo.bebasKaiMedium;

public class ButtonGenerator {
    public static Group createButton(String content) {
        var button = new Group();
        var background = new Rectangle(300, 50);
        background.setFill(Color.MIDNIGHTBLUE);
        var text = TextGenerator.makeTextRelative(content, bebasKaiMedium, Color.WHITE,
                background.getWidth() / 2,
                background.getHeight());
        background.setEffect(new DropShadow(10, Color.DARKGREY));
        background.setArcWidth(20);
        background.setArcHeight(20);
        button.getChildren().addAll(background, text);
        button.setCursor(Cursor.HAND);
        return button;
    }
}
