package back_end.command.turtle_basic;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.State;
import back_end.window_state.TurtleState;

import java.util.List;

public class SetShape extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 1;

    public SetShape() {
    }

    public SetShape(List<Command> arguments) {
        super(arguments);

        if (arguments.get(0).getValue() <= 0 || arguments.get(0).getValue() > State.NUMBER_OF_SHAPES) {
            throw new IllegalArgumentException("Shape options do not contain given index");
        }
    }

    @Override
    protected void executeSingle(TurtleState turtleState){
        turtleState.setTurtleShape(((int)arguments.get(0).getValue()));
        value = turtleState.getTurtleShape();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

}
