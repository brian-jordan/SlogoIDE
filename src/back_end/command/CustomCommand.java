package back_end.command;

import back_end.command.user_defined.VariableCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * A Custom Command is how a user created To command is called after it is created. It looks for the relevant list of commands from the map, and
 * executes
 * @author Lucas Liu
 */
public class CustomCommand extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;
    private String customName;

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public CustomCommand(List<Command> arguments, String customName) {
        super(arguments);
        this.customName = customName;

    }
    public CustomCommand(List<Command> arguments) {
        super(arguments);
        this.customName = ((VariableCommand) arguments.get(0)).getVariableName();
        arguments.remove(0);

    }

    /**
     * Calculate by looking for the correct command details from the user commands map
     * @param state
     * @param stateSequence
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {
        for(Command command: arguments){
            command.calculateNewState(state, stateSequence);
        }
        state.getUserCommands().get(customName).runCommand(state, stateSequence,arguments);
        execute(state);
    }

    /**
     * return command value
     * @param state
     */
    @Override
    protected void execute(State state) {
        value = state.getUserCommands().get(customName).getValue();
    }

}
