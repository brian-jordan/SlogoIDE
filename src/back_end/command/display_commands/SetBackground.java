package back_end.command.display_commands;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.List;

public class SetBackground extends Command{

    private final static int NUMBER_OF_ARGUMENTS = 1;

    public SetBackground() {
        super();
    }
    public SetBackground(List<Command> arguments) {
        super(arguments);

        if(arguments.get(0).getValue() <= 0 || arguments.get(0).getValue() > State.PALETTE_SIZE){
            throw new IllegalArgumentException("Palette does not contain given index");
        }
    }

    @Override
    protected void execute(State state) {
        state.setBackgroundColor((int)arguments.get(0).getValue());
        value = state.getBackgroundColor();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
