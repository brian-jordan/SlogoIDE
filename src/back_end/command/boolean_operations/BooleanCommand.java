package back_end.command.boolean_operations;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Abstract class for commands with boolean results. Uses lambda from subclass to determine boolean logic.
 *
 * @author Lucas Liu
 */
public abstract class BooleanCommand extends Command {

    private BiFunction function;

    /**
     * Constructor for subclass, allows for initialized commands and lambda
     *
     * @param commands
     * @param function
     */
    public BooleanCommand(List<Command> commands, BiFunction<Double, Double, Boolean> function) {
        super(commands);
        this.function = function;
    }

    /**
     * Constructor used for subclass construction, must provide lambda.
     *
     * @param function
     */
    public BooleanCommand(BiFunction<Double, Double, Boolean> function) {
        arguments = new ArrayList<>();
        this.function = function;
    }

    /**
     * Use the subclass lambda to determine value
     *
     * @param state
     */
    @Override
    protected void execute(State state) {
        value = (Boolean) function.apply(arguments.get(0).getValue(), arguments.get(1).getValue()) ? 1 : 0;
    }

    @Override
    public int getNumberOfArguments() {
        return 2;
    }
}