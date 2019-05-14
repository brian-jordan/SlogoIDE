package back_end.command.math_functions;

import back_end.command.Command;

import java.util.List;
import java.util.function.Function;


/**
 * @author Lucas Liu
 */
public class Random extends MathFunction {

    private final static Function<Double, Double> function = a -> Math.random() *a;

    public Random(List<Command> arguments) {
        super(arguments, function);
    }

    public Random() {
        super(function);
    }
}
