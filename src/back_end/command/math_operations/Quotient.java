package back_end.command.math_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;


/**
 * @author Lucas Liu
 */
public class Quotient extends MathOperations{

    private final static BiFunction<Double,Double,Double> function = (a, b) -> a /= b;

    public Quotient(List<Command> arguments) {
        super(arguments, function);

        if (arguments.get(1).getValue() == 0){
            throw new IllegalArgumentException("Cannot Divide by 0");
        }
    }

    public Quotient() {
        super(function);
    }
}
