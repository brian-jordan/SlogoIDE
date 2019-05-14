package front_end.screens;

import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import front_end.RunSlogo;
import front_end.utils.Callback;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class to display a window with a turtle's
 */
public class StatusScreen {
    private double x, y, heading, thickness;
    private boolean penUp;
    private Color penColor;
    private Stage stage;
    private Group container;
    private Text bigText;

    public StatusScreen (ImmutableState state, int ID, Callback callback) {
        stage = new Stage();
        stage.setTitle("Status for Turtle ID " + ID);
        container = new Group();
        stage.setScene(new Scene(container, 250, 150));
        stage.show();

        bigText = new Text();
        bigText.setFont(RunSlogo.sofiaProSmall);
        bigText.setFill(Color.SLATEGREY);
        String btContent = "";
        for(var ts : state.getImmutableTurtles()) {
            if (ts.getID() == ID) {
                int r = state.getImmutablePalette().get(state.getImmutableTurtles().get(0).getPenColor())[0];
                int g = state.getImmutablePalette().get(state.getImmutableTurtles().get(0).getPenColor())[1];
                int b = state.getImmutablePalette().get(state.getImmutableTurtles().get(0).getPenColor())[2];
                String colourString = Color.rgb(r, g, b).toString();
                colourString = colourString.substring(colourString.indexOf("0x")+2);

                btContent += " Turtle position: ("+(int)ts.getxCor()+","+(int)ts.getyCor()+")\n";
                btContent += " Turtle heading: "+ts.getHeading()+" degrees\n";
                btContent += " Turtle pen is: "+(ts.isPendown() ? " down" : " up")+"\n";
                btContent += " Turtle pen color: #"+colourString+"\n";
                btContent += " Turtle pen thickness: "+ts.getPenSize()+"pt\n";
            }
        }
        bigText.setText(btContent);
        bigText.setX(stage.getWidth()/2 - bigText.getLayoutBounds().getWidth()/2);
        bigText.setY(stage.getHeight()/2 - bigText.getLayoutBounds().getHeight()/2);
        container.getChildren().add(bigText);


        stage.setOnCloseRequest(e -> {
            callback.onCallback();
        });
    }

}
