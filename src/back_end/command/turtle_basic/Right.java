package back_end.command.turtle_basic;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class Right extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 1;



    public Right(List<Command> arguments) {
        super(arguments);
    }

    public Right() {
        super();
    }

    @Override
    protected void executeSingle(TurtleState turtleState) {
        turtleState.setHeading(turtleState.getHeading() - arguments.get(0).getValue());
        turtleState.setHeading(checkAngle(turtleState.getHeading()));
        value = arguments.get(0).getValue();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
