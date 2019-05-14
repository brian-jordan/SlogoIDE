package back_end.command.turtle_other;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class SetPosition extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public SetPosition(){}
    public SetPosition(List<Command> arguments) {
        super(arguments);

        if(checkLocation(arguments.get(0).getValue(), arguments.get(1).getValue())){
            throw new IllegalArgumentException("Coordinate is not within window");
        }
    }

    @Override
    protected void executeSingle(TurtleState turtleState){
        double distanceMoved = Math.sqrt(Math.pow(arguments.get(0).getValue() - turtleState.getxCor(), 2)
                + Math.pow(arguments.get(1).getValue() - turtleState.getyCor(), 2));
        turtleState.setxCor(arguments.get(0).getValue());
        turtleState.setyCor(arguments.get(1).getValue());
        value = distanceMoved;
    }
}
