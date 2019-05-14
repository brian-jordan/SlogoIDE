package back_end.command.math_functions;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Abstract class for math commands that require special functions, such as trigonometry.
 *
 * @author Lucas Liu
 */
public abstract class MathFunction extends Command {

    private Function function;

    /**
     * Constructor for subclass
     * @param commands
     * @param function
     */
    public MathFunction(List<Command> commands, Function<Double, Double> function) {
        super(commands);
        this.function = function;
    }

    /**
     * Constructor for subclass, giving only the lambda function for calculation
     * @param function
     */
    public MathFunction(Function<Double, Double> function) {
        arguments = new ArrayList<>();
        this.function = function;
    }

    /**
     * Determine value from subclass lambda
     * @param state
     */
    @Override
    protected void execute(State state) {
        value = (Double) function.apply(arguments.get(0).getValue());
    }

    @Override
    public int getNumberOfArguments() {
        return 1;
    }
}
