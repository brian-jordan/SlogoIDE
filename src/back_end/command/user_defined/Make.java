package back_end.command.user_defined;

import back_end.command.Command;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * @author Lucas Liu
 */
public class Make extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public Make() {
        super();
    }

    public Make(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(0) instanceof VariableCommand)) {
            throw new IllegalArgumentException("First Command in Make should be a VariableCommand");
        }

    }

    @Override

    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        arguments.get(1).calculateNewState(state, stateSequence);

        execute(state);
        stateSequence.add(state.copy());
    }

    @Override
    protected void execute(State state) {

        value = arguments.get(1).getValue();
        state.getVariables().put(((VariableCommand) arguments.get(0)).getVariableName(), value);
    }
}
