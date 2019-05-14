package back_end.command.user_defined;

import back_end.command.Command;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * Class for custom user variables
 *
 * @author Lucas Liu
 */
public class VariableCommand extends Command {

    private String variableName;

    @Override
    public int getNumberOfArguments() {
        return 0;
    }

    public VariableCommand(String variableName){
        this.variableName = variableName;
    }

    /**
     * Gets variable value from map based on name
     * @param state
     */
    @Override
    protected void execute(State state) {
        value = state.getVariables().get(variableName);
    }

    @Override
    public void calculateNewState(State state, List<ImmutableState> states) {
        execute(state);
    }

    public String getVariableName() {
        return variableName;
    }

    protected void setVariableValue(Double value, State state){
        state.getVariables().put(variableName,value);
    }
}
