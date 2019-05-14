package back_end.command.math_functions;

import back_end.command.Command;

import java.util.List;
import java.util.function.Function;

/**
 * @author Lucas Liu
 */
public class NaturalLog extends MathFunction {

    private final static Function<Double, Double> function = a -> Math.log(a);

    public NaturalLog(List<Command> arguments) {
        super(arguments, function);

        if (arguments.get(0).getValue() <= 0){
            throw new IllegalArgumentException("Cannot take natural log of a non-positive number");
        }
    }

    public NaturalLog() {
        super(function);
    }

}
