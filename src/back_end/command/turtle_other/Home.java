package back_end.command.turtle_other;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class Home extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public Home(){    }
    public Home(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void executeSingle(TurtleState turtleState) {
        double distanceMoved = Math.sqrt(Math.pow(turtleState.getxCor(), 2)  + Math.pow(turtleState.getyCor(), 2));
        turtleState.setxCor(0);
        turtleState.setyCor(0);
        value = distanceMoved;
    }
}
