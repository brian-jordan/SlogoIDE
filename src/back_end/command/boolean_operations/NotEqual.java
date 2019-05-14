package back_end.command.boolean_operations;

import back_end.command.Command;

import java.util.List;
import java.util.function.BiFunction;

public class NotEqual extends BooleanCommand {


    /**
     * Example of a boolean command. All you really need is the lambda.
     *
     * @author Lucas Liu
     */
    private final static BiFunction<Double, Double, Boolean> function = (a, b) -> a != b;

    public NotEqual(List<Command> arguments) {
        super(arguments, function);
    }

    public NotEqual() {
        super(function);
    }
}
