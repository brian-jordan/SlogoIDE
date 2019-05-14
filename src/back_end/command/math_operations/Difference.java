package back_end.command.math_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Example math operation, only need to define bifunction
 */
public class Difference extends MathOperations{

    private final static BiFunction<Double,Double,Double> function = (a, b) -> a -= b;

    public Difference(List<Command> arguments) {
        super(arguments, function);
    }

    public Difference() {
        super(function);
    }
}
