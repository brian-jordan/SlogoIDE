package back_end.command.turtle_other;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class SetHeading extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 1;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public SetHeading(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void executeSingle(TurtleState turtleState){
        double degreesRotated = Math.abs(arguments.get(0).getValue() - turtleState.getHeading());
        turtleState.setHeading(checkAngle(arguments.get(0).getValue()));
        value = degreesRotated;
    }

}
