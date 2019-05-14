package back_end.command.display_commands;

import back_end.command.Command;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import back_end.window_state.TurtleState;
import java.util.List;

public class ClearScreen extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 0;

    public ClearScreen(){}
    public ClearScreen(List<Command> arguments) {
        super(arguments);
    }

    /**
     * Calls same method in super class and sets the State clearScreen flag to false
     * @param state - State object indicating status of the Turtle window
     * @param stateSequence - List of copies of State objects altered by current command list
     */

    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {
        super.calculateNewState(state, stateSequence);
        state.setClearScreen(false);
    }

    @Override
    protected void execute(State state) {
        state.setClearScreen(true);
        state.setCurrentExecuting(state.getTurtles().get(0));
        state.getTurtles().clear();
        state.getActiveTurtles().clear();
        state.getTurtles().add(state.getCurrentExecuting());
        state.getActiveTurtles().add(state.getCurrentExecuting().getID());

        value = resetTurtle(state.getCurrentExecuting());
    }

    private double resetTurtle(TurtleState turtleState){
        double distanceToTravel = Math.sqrt(Math.pow(turtleState.getxCor(), 2)  + Math.pow(turtleState.getyCor(), 2));
        turtleState.setStartingValues();
        return distanceToTravel;
    }

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
