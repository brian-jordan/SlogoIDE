package back_end.command.boolean_operations;

import back_end.command.Command;
import back_end.command.ValueCommand;

import java.util.List;
import java.util.function.BiFunction;

public class Not extends BooleanCommand {

    /**
     * Example of a boolean command. All you really need is the lambda. Workaround for system - needs 2 arguments to follow formula, but will only
     * ever use the first
     *
     * @author Lucas Liu
     */
    private final static BiFunction<Double, Double, Boolean> function = (a, b) -> b != 0;

    public Not(List<Command> arguments) {
        super(arguments, function);
        arguments.add(0, new ValueCommand(0));
    }

    public Not() {
        super(function);
        arguments.add(new ValueCommand(0));
    }

}
