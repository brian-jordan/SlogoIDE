package back_end.command.control_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.ValueCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * If Else command
 *
 * @author Lucas Liu
 */
public class IfElse extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 3;

    public IfElse(List<Command> arguments) {
        super(arguments);
        if (!(arguments.get(0) instanceof ValueCommand)) {
            throw new IllegalArgumentException("First Command in IFELSE should be a ValueCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in IFELSE should be a ListCommand");
        }

        if (!(arguments.get(2) instanceof ListCommand)) {
            throw new IllegalArgumentException("Third Command in IFELSE should be a ListCommand");
        }
    }

    /**
     * logic for if else
     * @param state
     * @param stateSequence
     */
    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        arguments.get(0).calculateNewState(state, stateSequence);

        if(arguments.get(0).getValue() != 0) {
            arguments.get(1).calculateNewState(state, stateSequence);
            value = arguments.get(1).getValue();
        } else{
            arguments.get(2).calculateNewState(state, stateSequence);
            value = arguments.get(2).getValue();
        }


    }


    @Override
    protected void execute(State state) {
        //do nothing
    }

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
