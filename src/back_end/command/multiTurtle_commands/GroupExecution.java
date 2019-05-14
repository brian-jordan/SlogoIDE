package back_end.command.multiTurtle_commands;

import back_end.command.Command;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

public abstract class GroupExecution extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public GroupExecution() {
        super();
    }
    public GroupExecution(List<Command> arguments) {
        super(arguments);
    }

    protected abstract void setActiveTurtles(State state, List<ImmutableState> stateSequence);

    /**
     * Changes the list of active turtles, executes list of commands on the active turtles, and resets list of active turtles
     * @param state - State object indicating status of the Turtle window
     * @param stateSequence - List of copies of State objects altered by current command list
     */

    @Override
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {
        List<Integer> prevActiveTurtles = state.getActiveTurtles();
        state.getActiveTurtles().clear();
        setActiveTurtles(state, stateSequence);
        arguments.get(1).calculateNewState(state, stateSequence);
        execute(state);
        state.setActiveTurtles(prevActiveTurtles);
    }

    @Override
    protected void execute(State state) {
        value = arguments.get(1).getValue();
    }
}
