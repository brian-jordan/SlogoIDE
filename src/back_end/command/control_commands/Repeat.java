package back_end.command.control_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.ValueCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * Repeat command
 *
 * @author Lucas Liu
 */
public class Repeat extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;


    public Repeat() {
    }

    public Repeat(List<Command> arguments) {
        super(arguments);
        if (!(arguments.get(0) instanceof ValueCommand)) {
            throw new IllegalArgumentException("First Command in Repeat should be a ValueCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in Repeat should be a ListCommand");
        }
    }

    /**
     * Logic for repeat. Has a special rule about setting the variable repcount each time
     * @param state
     * @param stateSequence
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        arguments.get(0).calculateNewState(state, stateSequence);

        if (arguments.get(0).getValue() == 0) {
            value = 0;
            return;
        }
        state.getVariables().put("repcount", 1.0);
        for (int i = 1; i <= arguments.get(0).getValue(); i++) {
            arguments.get(1).calculateNewState(state, stateSequence);
            state.getVariables().put("repcount", state.getVariables().get("repcount") + 1);
            stateSequence.add(state.copy());
        }

        execute(state);
        stateSequence.add(state.copy());
    }

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    @Override
    protected void execute(State state) {
        value = arguments.get(1).getValue();
    }
}
