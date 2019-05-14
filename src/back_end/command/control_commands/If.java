package back_end.command.control_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * If command
 *
 * @author Lucas Liu
 */
public class If extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    public If() {
    }

    public If(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in IF should be a ListCommand");
        }
    }

    /**
     * Execute if logic
     * @param state
     * @param stateSequence
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        arguments.get(0).calculateNewState(state, stateSequence);

        if (arguments.get(0).getValue() != 0) {
            arguments.get(1).calculateNewState(state, stateSequence);
        } else {
            value = 0;
            return;
        }

        execute(state);
    }


    @Override
    protected void execute(State state) {
        value = arguments.get(0).getValue();
    }


    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
