package back_end.command.math_functions;

import back_end.command.Command;

import java.util.List;
import java.util.function.Function;

/**
 * Example math function. only need to define function.
 *
 * @author Lucas Liu
 */
public class ArcTan extends MathFunction {

    private final static Function<Double, Double> function = a -> Math.atan(Math.toRadians(a));

    public ArcTan(List<Command> arguments) {
        super(arguments, function);
    }

    public ArcTan() {
        super(function);
    }
}
