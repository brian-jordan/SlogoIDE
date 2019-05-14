package back_end.command.turtle_visual;

import back_end.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Example turtle visual. Simply define attribute name and desired action
 *
 * @author Lucas Liu
 */
public class PenDown extends TurtleVisual {

    private static final String ATTRIBUTE = "pendown";

    public PenDown() {
        super(new ArrayList<>(), ATTRIBUTE, true);
    }

    public PenDown(List<Command> arguments) {
        super(arguments, ATTRIBUTE, true);
    }
}
