package back_end.command.turtle_other;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.util.List;

public class SetTowards extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public SetTowards(List<Command> arguments) {
        super(arguments);

        if(checkLocation(arguments.get(0).getValue(), arguments.get(1).getValue())){
            throw new IllegalArgumentException("Coordinate is not within window");
        }
    }

    @Override
    protected void executeSingle(TurtleState turtleState){
        double xDifference = arguments.get(0).getValue() - turtleState.getxCor();
        double yDifference = arguments.get(1).getValue() - turtleState.getyCor();
        double newHeading = Math.toDegrees(Math.atan(yDifference / xDifference));
        if(xDifference < 0) {
            newHeading = newHeading + 180;
        }
        double degreesRotated = Math.abs(newHeading - turtleState.getHeading());
        turtleState.setHeading(checkAngle(newHeading));
        value = degreesRotated;
    }
}
