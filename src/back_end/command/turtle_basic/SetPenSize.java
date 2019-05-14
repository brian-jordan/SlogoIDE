package back_end.command.turtle_basic;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class SetPenSize extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 1;

    public SetPenSize(){}

    public SetPenSize(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void executeSingle(TurtleState turtleState){
        turtleState.setPenSize(((int)arguments.get(0).getValue()));
        value = turtleState.getPenSize();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
