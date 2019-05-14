package back_end.command.math_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;


/**
 * @author Lucas Liu
 */
public class Power extends MathOperations {

    private final static BiFunction<Double, Double, Double> function = (a, b) -> Math.pow(a, b);

    public Power(List<Command> arguments) {
        super(arguments, function);
    }

    public Power() {
        super(function);
    }

}
