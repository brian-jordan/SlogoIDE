package back_end.window_state;

public interface ImmutableTurtleState {

    int getID();

    double getxCor();

    double getyCor();

    double getHeading();

    boolean isPendown();

    boolean isShowing();

    int getPenColor();

    int getPenSize();

    int getTurtleShape();
}
