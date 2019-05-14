package back_end.command;

import back_end.command.user_defined.VariableCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import front_end.screens.VariableScreen;

import java.util.List;

/**
 * How a custom user command is first defined and stored. The instructions are put into the State map
 * @author Lucas Liu
 */
public class To extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 3;
    private ListCommand variableSeries;
    private ListCommand executionSeries;
    private String customName;

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    /**
     * Constructor using only list of commands. List is broken down into needed pieces. Includes error checking.
     * @param arguments
     */
    public To(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(2) instanceof ListCommand) || !(arguments.get(1) instanceof ListCommand) || !(arguments.get(0) instanceof VariableCommand)) {
            value = 0;
            return;
        }
            value = 1;
            this.customName = ((VariableCommand) arguments.get(0)).getVariableName();
            variableSeries = (ListCommand) arguments.get(1);
            executionSeries = (ListCommand) arguments.get(2);
        }

    /**
     * Constructor that includes the name as well
     * @param arguments
     * @param customName
     */
    public To(List<Command> arguments, String customName) {
        super(arguments);
        if (!(arguments.get(0) instanceof ListCommand) || !(arguments.get(1) instanceof ListCommand)) {
            value = 0;
            return;
        }
        value = 1;
        this.customName = customName;
        variableSeries = (ListCommand) arguments.get(0);
        executionSeries = (ListCommand) arguments.get(1);

    }

    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {
        execute(state);
        stateSequence.add(state.copy());
    }

    /**
     * Adding this custom command into the user commands map
     * @param state
     */
    @Override
    protected void execute(State state) {
        state.getUserCommands().put(customName, this);
    }

    /**
     * How the command is run in the future when the user calls the custom function
     * @param state
     * @param stateSequence
     * @param commands
     */
    protected void runCommand(State state, List<ImmutableState> stateSequence, List<Command> commands) {
        for (int i = 0; i < variableSeries.arguments.size(); i++) {
            state.getVariables().put(((VariableCommand) variableSeries.arguments.get(i)).getVariableName(), commands.get(i).getValue());
        }
        executionSeries.calculateNewState(state, stateSequence);
        value = executionSeries.getValue();
    }

    /**
     * Used for giving the number of arguments
     * @return
     */
    public int getVariableCount() {
        return variableSeries.arguments.size();
    }

}
