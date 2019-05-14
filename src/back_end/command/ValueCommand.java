package back_end.command;

import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * Useful concept class for just holding a number, while not breaking/necessitating a hardcoded case (Since this is also a Command, that mostly
 * does nothing)
 */
public class ValueCommand extends Command {

    public ValueCommand(double value){
        super(value);
    }

    @Override
    protected void execute(State state) {
        //do nothing
    }

    @Override
    public int getNumberOfArguments() {
        return 0;
    }

    @Override
    public void calculateNewState(State state, List<ImmutableState> states) {
        //do nothing
    }
}
