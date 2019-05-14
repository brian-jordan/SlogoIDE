package back_end.command.math_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;
/**
 * @author Lucas Liu
 */
public class Product extends MathOperations{

    private final static BiFunction<Double,Double,Double> function = (a, b) -> a *= b;

    public Product(List<Command> arguments) {
        super(arguments, function);
    }

    public Product() {
        super(function);
    }
}
