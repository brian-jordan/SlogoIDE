package back_end.model_info;

import back_end.Observer;
import back_end.command.Command;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class Model {

    public static final double TURTLE_WINDOW_WIDTH = 1000;
    public static final double TURTLE_WINDOW_HEIGHT = 500;

    private List<Observer> observers;
    private List<ImmutableState> stateSequence;
    private State currentState;
    private State nextState;
    // ADD MAP TO STORE USER MADE COMMANDS

    /**
     * Default constructor
     */
    public Model() {
        currentState = new State();
        nextState = new State();
        stateSequence = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void executeCommands(List<Command> commands) {
        nextState = currentState.copy();
        for (Command command : commands) {
            executeCommand(command);
        }
        notifyObservers();

    }

    private void executeCommand(Command command) {
        command.calculateNewState(nextState, stateSequence);
    }

    private void notifyObservers() {

        for (Observer observer : observers) {

            observer.reportChange(stateSequence);
        }
        stateSequence.clear();
        currentState = nextState;
    }

    public void setActiveTurtles(List<Integer> activeTurtles) {
        // Just a way of getting things done for now. Can change later to better encapsulate.
        this.currentState.setActiveTurtles(activeTurtles);
    }

    public void addObserver( Observer observer){
        observers.add(observer);
    }

}