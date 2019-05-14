package front_end.screens;

import front_end.RunSlogo;
import front_end.uitools.TextGenerator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShapePaletteScreen {
    private Stage stage;
    private Group container;
    public ShapePaletteScreen() {
        stage = new Stage();
        stage.setTitle("Available Shapes");
        container = new Group();
        stage.setScene(new Scene(container, 170, 175));
        stage.show();
        stage.setResizable(false);

        Text indexText = TextGenerator.makeText("Select a shape\nto view index", RunSlogo.sofiaProSmall,
                Color.SLATEGREY, 0, stage.getHeight()*(9.0/10) - 25);
        indexText.setX(stage.getWidth()/2 - indexText.getLayoutBounds().getWidth()/2);

        int sides = 3;
        for(int j = 0; j < 6; j++) {
            double[] pointsUpwards = new double[sides*2];
            for (int i = 0; i < sides*2; i += 2) {
                double angle = Math.PI * (0.5 + i / ((double) sides));
                pointsUpwards[i] = Math.cos(angle);
                pointsUpwards[i + 1] = -Math.sin(angle);
            }
            var shape = new Polygon(pointsUpwards);
            shape.setStrokeWidth(20);
            shape.setStroke(Color.BLACK);
            shape.setLayoutX(((j%3)+1)*30 + (j%3)*25);
            shape.setLayoutY(((j/3)+1)*30 + (j/3)*25);
            container.getChildren().add(shape);
            final int index = j;
            shape.setOnMouseClicked(e -> {
                indexText.setText("Shape index: " + index);
            });
            sides++;
        }

        container.getChildren().addAll(indexText);
    }
}
