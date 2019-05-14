package back_end.command.multiTurtle_commands;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.List;

public class ID extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public ID() {
        super();
    }

    public ID(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void execute(State state) {
        value = state.getCurrentExecuting().getID();
    }
}
