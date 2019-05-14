package back_end.command.math_functions;

import back_end.command.Command;

import java.util.List;
import java.util.function.Function;


/**
 * @author Lucas Liu
 */
public class Sine extends MathFunction {

    private final static Function<Double, Double> function = a -> Math.sin(Math.toRadians(a));

    public Sine(List<Command> arguments) {
        super(arguments, function);
    }

    public Sine() {
        super(function);
    }
}
