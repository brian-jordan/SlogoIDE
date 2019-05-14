package front_end.screens;

import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import front_end.RunSlogo;
import front_end.uitools.TextGenerator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ColourPaletteScreen {
    private Stage stage;
    private Group container;
    public ColourPaletteScreen(ImmutableState state) {
        stage = new Stage();
        stage.setTitle("Available Colors");
        container = new Group();
        stage.setScene(new Scene(container, 175, 225));
        stage.show();
        stage.setResizable(false);

        Text indexText = TextGenerator.makeText("Select a color\nto view index", RunSlogo.sofiaProSmall,
                Color.SLATEGREY, 0, stage.getHeight()*(9.0/10) - 25);
        indexText.setX(stage.getWidth()/2 - indexText.getLayoutBounds().getWidth()/2);

        for(int i = 0; i < 9; i++) {
            int r = state.getImmutablePalette().get(i)[0];
            int g = state.getImmutablePalette().get(i)[1];
            int b = state.getImmutablePalette().get(i)[2];
            var swatch = new Rectangle(25, 25, Color.rgb(r, g, b));
            swatch.setX(((i%3)+1)*25 + (i%3)*25);
            swatch.setY(((i/3)+1)*25 + (i/3)*25);
            swatch.setStroke(Color.BLACK);
            swatch.setStrokeWidth(1);
            container.getChildren().add(swatch);
            final int index = i;
            swatch.setOnMouseClicked(e -> {
               indexText.setText("Color index: " + index);
            });
        }

        container.getChildren().addAll(indexText);
    }
}
