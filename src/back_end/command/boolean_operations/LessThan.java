package back_end.command.boolean_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Example of a boolean command. All you really need is the lambda
 *
 * @author Lucas Liu
 */
public class LessThan extends BooleanCommand {

    private final static BiFunction<Double, Double, Boolean> function = (a, b) -> a < b;

    public LessThan(List<Command> arguments) {
        super(arguments, function);
    }

    public LessThan() {
        super(function);
    }

}
