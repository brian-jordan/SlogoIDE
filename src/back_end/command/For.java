package back_end.command;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.user_defined.VariableCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * Class for FOR command. Start-end-increment logic.
 * @author Lucas Liu
 */
public class For extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;
    protected ListCommand variableSeries;
    protected ListCommand executionSeries;
    private String userVariable;

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    /**
     * Constructor with error checking
     * @param arguments
     */
    public For(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(0) instanceof ListCommand)) {
            throw new IllegalArgumentException("First Command in FOR should be a ListCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in FOR should be a ListCommand");
        }

        variableSeries = (ListCommand) arguments.get(0);
        executionSeries = (ListCommand) arguments.get(1);
        userVariable = ((VariableCommand) variableSeries.arguments.get(0)).getVariableName();
        variableSeries.arguments.remove(0);

    }

    /**
     * Required for loop implementation
     * @param state
     * @param stateSequence
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        double var = variableSeries.arguments.get(0).getValue();

        state.getVariables().put(userVariable, var);
        for (int i = (int)var; i <= variableSeries.arguments.get(1).getValue(); i = (int) (i+variableSeries.arguments.get(2).getValue())) {
            arguments.get(1).calculateNewState(state, stateSequence);
            state.getVariables().put(userVariable, (double) i);
            stateSequence.add(state.copy());
        }

        execute(state);
        stateSequence.add(state.copy());

    }

    @Override
    protected void execute(State state) {
        value = executionSeries.arguments.get(executionSeries.arguments.size() - 1).getValue();
    }
}
