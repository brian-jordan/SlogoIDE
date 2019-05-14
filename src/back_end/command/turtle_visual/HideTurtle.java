package back_end.command.turtle_visual;

import back_end.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Example turtle visual. Simply define attribute name and desired action
 *
 * @author Lucas Liu
 */
public class HideTurtle extends TurtleVisual {
    private static final String ATTRIBUTE = "showing";

    public HideTurtle() {
        super(new ArrayList<>(), ATTRIBUTE, false);
    }

    public HideTurtle(List<Command> arguments) {
        super(arguments, ATTRIBUTE, false);
    }

}
