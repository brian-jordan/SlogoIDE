package back_end.command.math_operations;

import back_end.command.Command;
import back_end.window_state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Abstract Class for math operations, that may take any number of arguments. The implied desired outcome is a successive application of the math
 * operation, starting with the first and second argument, then taking this result and applying the math function to the third argument, and so on...
 *
 * @author Lucas Liu
 */
public abstract class MathOperations extends Command {

    private BiFunction function;

    /**
     * Constructor for subclass
     * @param commands
     * @param function
     */
    public MathOperations(List<Command> commands, BiFunction<Double, Double, Double> function) {
        super(commands);
        this.function = function;
    }

    /**
     * Constructor for subclass, only giving the lambda
     * @param function
     */
    public MathOperations(BiFunction<Double, Double, Double> function) {
        arguments = new ArrayList<>();
        this.function = function;
    }

    /**
     * Carry out calculation by successively passing to the lambda function, thus allowing for any number of arguments
     * @param state
     */
    @Override
    protected void execute(State state) {

        double result = arguments.get(0).getValue();
        for (int i = 1; i < arguments.size(); i++) {
            result = (Double) function.apply(result, arguments.get(i).getValue());
        }

        value = result;

    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */
    
    @Override
    public int getNumberOfArguments() {

        // Math Operations can take any number of arguments
        return -1;
    }

}
