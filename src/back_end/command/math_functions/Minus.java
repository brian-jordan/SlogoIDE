package back_end.command.math_functions;

import back_end.command.Command;

import java.util.List;
import java.util.function.Function;

/**
 * @author Lucas Liu
 */
public class Minus extends MathFunction {

    private final static Function<Double, Double> function = a -> -a;

    public Minus(List<Command> arguments) {
        super(arguments, function);
    }

    public Minus() {
        super(function);
    }
}
