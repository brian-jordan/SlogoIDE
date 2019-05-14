package back_end.command;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.user_defined.VariableCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

public class DoTimes extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;
    protected ListCommand variableSeries;
    protected ListCommand executionSeries;
    private String userVariable;
    private Command limit;

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public DoTimes(List<Command> arguments) {
        super(arguments);
        if (!(arguments.get(0) instanceof ListCommand)) {
            throw new IllegalArgumentException("First Command in DOTIMES should be a ListCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in DOTIMES should be a ListCommand");
        }

            variableSeries = (ListCommand) arguments.get(0);
            executionSeries = (ListCommand) arguments.get(1);
            userVariable = ((VariableCommand) variableSeries.arguments.get(0)).getVariableName();
            limit = variableSeries.arguments.get(1);

    }

    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        limit.calculateNewState(state, stateSequence);

        for (int i = 1; i < limit.getValue(); i++) {
            state.getVariables().put(userVariable,(double) i);
            executionSeries.calculateNewState(state,stateSequence);
        }

        execute(state);
    }

    @Override
    protected void execute(State state) {
        value = executionSeries.arguments.get(executionSeries.arguments.size()-1).getValue();
    }
}
