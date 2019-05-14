package front_end.uitools;

import front_end.utils.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Source: https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75
 */
public class ToggleSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }

    private void init(boolean isPenDown) {
        label.setText("PEN DOWN");

        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        label.setOnMouseClicked((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        setStyle();
        bindProperties();

        if (!isPenDown) {
            switchedOn.set(!switchedOn.get());
        }
    }

    private void setStyle() {
        //Default Width
        setWidth(160);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleSwitch(boolean isPenDown, Callback callbackUp, Callback callbackDown) {
        init(isPenDown);
        switchedOn.addListener((a,b,c) -> {
            if (c) {
                label.setText("PEN UP");
                setStyle("-fx-background-color: green;");
                label.toFront();
                callbackUp.onCallback();
            }
            else {
                label.setText("PEN DOWN");
                setStyle("-fx-background-color: grey;");
                button.toFront();
                callbackDown.onCallback();
            }
        });
    }
}