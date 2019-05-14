package back_end.command;

import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * Useful class that simply serves as a container to associated multiple commands together. Very useful for groups of commands that stay together.
 * Reduces duplication elsewhere
 *
 * @author Lucas Liu
 */
public class ListCommand extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    public ListCommand() {
        super();
    }

    public ListCommand(List<Command> arguments) {
        this.arguments = arguments;
    }

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    @Override
    protected void execute(State state) {
        //do nothing
    }

    /**
     * Simply go through the list and execute each individual command in the list
     * @param state
     * @param states
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> states) {
        if (arguments.size() == 0) {
            value = 0;
            return;
        }
        for (Command command : arguments) {
            command.calculateNewState(state, states);
        }
        value = arguments.get(arguments.size() - 1).getValue();

    }
}
