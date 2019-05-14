package back_end.command;


import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Highest level abstraction/superclass of a Command. A command is a set of instructions for the back-end (Model) to handle, which may involve
 * returning a desired query or altering a Model's State in various ways. The Command is the root node of a tree, which may contain other commands
 * as arguments (children). The execution proceeds through the tree starting at the leaf nodes. All commands have a return value (double), a
 * "calculateNewState" method, and an "execute" method. These methods allow the command to create necessary changes, and determine its own
 * appropriate return value.
 * @author Lucas Liu
 */
public abstract class Command {

    protected List<Command> arguments;
    protected double value = Double.NaN;

    /**
     * Various constructors allow for flexible Command creation
     */
    public Command() {
        arguments = new ArrayList<>();
    }

    public Command(List<Command> arguments) {
        this.arguments = arguments;
    }

    public Command(double value) {
        this.value = value;
    }

    public List<Command> getArguments() {
        return arguments;
    }

    /**
     * Method to check for number of arguments, then run through children, execute self, and add to a state sequence
     * @param state
     * @param stateSequence
     */
    public void calculateNewState(State state, List<ImmutableState> stateSequence) {

        for (Command command : arguments) {
            command.calculateNewState(state, stateSequence);
        }
        execute(state);
        stateSequence.add(state.copy());
    }

    /**
     * Method to change state if necessary, and perform a calculation to determine a return value
     * @param state
     */
    protected abstract void execute(State state);

    /**
     * Return the value of this command
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Number of arguments this command should have
     * @return
     */
    protected abstract int getNumberOfArguments();

}