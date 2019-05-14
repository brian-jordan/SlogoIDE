package back_end.command.turtle_basic;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class Forward extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 1;


    public Forward() {
        super();
    }

    public Forward(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void executeSingle(TurtleState turtleState) {
        turtleState.setxCor(turtleState.getxCor() + (arguments.get(0).getValue() * Math.cos(Math.toRadians(turtleState.getHeading()))));
        turtleState.setyCor(turtleState.getyCor() + (arguments.get(0).getValue() * Math.sin(Math.toRadians(turtleState.getHeading()))));
        //checkTurtlePosition(turtleState);
        value = arguments.get(0).getValue();
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}