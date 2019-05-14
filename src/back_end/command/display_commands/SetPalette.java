package back_end.command.display_commands;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.List;

public class SetPalette extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 4;

    public SetPalette() {
        super();
    }
    public SetPalette(List<Command> arguments) {
        super(arguments);

        if(arguments.get(0).getValue() <= 0 || arguments.get(0).getValue() > State.PALETTE_SIZE){
            throw new IllegalArgumentException("Palette does not contain given index");
        }

        for(int i = 1; i < NUMBER_OF_ARGUMENTS; i++){
            if (arguments.get(i).getValue() < 0 || arguments.get(i).getValue() > 255){
                throw new IllegalArgumentException("Input has incorrect RGB value");
            }
        }
    }

    @Override
    protected void execute(State state) {
        int[] colors = {((int)arguments.get(1).getValue()), ((int)arguments.get(2).getValue()), ((int)arguments.get(3).getValue())};
        state.getPalette().set((int)arguments.get(0).getValue(), colors);
        value = arguments.get(0).getValue();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
