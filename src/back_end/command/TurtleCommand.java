package back_end.command;

import back_end.model_info.Model;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import back_end.window_state.TurtleState;
import java.util.ArrayList;
import java.util.List;

public abstract class TurtleCommand extends Command {

    public TurtleCommand(List<Command> arguments) { super(arguments);}

    public TurtleCommand() {
        arguments = new ArrayList<>();
    }

    /**
     * Recursively calls the same method of the Command Objects arguments then loops through active turtles executing the command
     * @param state - State object indicating status of the Turtle window
     * @param stateSequence - List of copies of State objects altered by current command list
     */

    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {
        for (Command command : arguments) {
            command.calculateNewState(state, stateSequence);
        }

        for (int activeTurtle : state.getActiveTurtles()){
            state.setCurrentExecuting(state.getTurtles().get(activeTurtle - 1));
            executeSingle(state.getCurrentExecuting());
            execute(state);
            stateSequence.add(state.copy());
        }
    }

    protected void execute(State state){
        // Not Used
    }

    protected abstract void executeSingle(TurtleState turtleState);

    /**
     * Indicates if a given position is located within the Turtle window
     * @param x - x position in window
     * @param y - y position in window
     * @return - boolean indicator if position is within window
     */

    public boolean checkLocation(double x, double y){
        return Math.abs(x) > Model.TURTLE_WINDOW_WIDTH || Math.abs(y) > Model.TURTLE_WINDOW_HEIGHT;
    }

    /**
     * Adjusts the angle the Turtle is facing to always be between 0 and 360 degrees
     * @param newAngle - new heading value
     * @return - adjusted heading value
     */

    public double checkAngle(double newAngle){
        while(newAngle < 0){
            newAngle+= 360;
        }
        return newAngle % 360;
    }

}
