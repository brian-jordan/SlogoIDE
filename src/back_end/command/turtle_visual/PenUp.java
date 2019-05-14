package back_end.command.turtle_visual;

import back_end.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Example turtle visual. Simply define attribute name and desired action
 *
 * @author Lucas Liu
 */
public class PenUp extends TurtleVisual {

    private static final String ATTRIBUTE = "pendown";

    public PenUp() {
        super(new ArrayList<>(), ATTRIBUTE, false);
    }

    public PenUp(List<Command> arguments) {
        super(arguments, ATTRIBUTE, false);
    }
}
