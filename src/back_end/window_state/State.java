package back_end.window_state;


import back_end.command.To;

import java.util.*;

public class State implements ImmutableState{

    public static final int PALETTE_SIZE = 9;
    public static final int NUMBER_OF_SHAPES = 6;
    public static final int[] BLACK_RGB = {0, 0, 0};
    public static final int[] WHITE_RGB = {255, 255, 255};
    public static final int INITIAL_WHITE_CNT = 8;

    private boolean clearScreen;
    private int backgroundColor;
    private Map<String, Double> variables;
    private List<int[]> palette;
    private List<TurtleState> turtles;
    private List<Integer> activeTurtles;
    private TurtleState currentExecuting;
    private Map<String, To> userCommands;

    /**
     * Creates an instance of a state object and sets variables to their initial values.
     */

    public State() {
        backgroundColor = 1;
        clearScreen = false;
        variables = new HashMap<>();
        palette = new ArrayList<>();
        turtles = new ArrayList<>();
        activeTurtles = new ArrayList<>();
        TurtleState firstTurtle = new TurtleState(1);
        turtles.add(firstTurtle);
        activeTurtles.add(firstTurtle.getID());
        currentExecuting = firstTurtle;
        userCommands = new HashMap<>();
        // Populate palette with 1 black and 8 white colours
        setInitialPalette();
    }

    private void setInitialPalette() {
        palette.add(BLACK_RGB);
        int iter = INITIAL_WHITE_CNT;
        while (iter-- > 0) {
            palette.add(WHITE_RGB);
        }
    }

    /**
     * Returns the value of the clearScreen flag
     * @return - boolean clearScreen flag value
     */

    @Override
    public boolean isClearScreen() {
        return clearScreen;
    }

    /**
     * Returns the palette index of the windows background color
     * @return - integer index of palette
     */

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Returns map of the user created variables matched to their values
     * @return - map of user variables
     */

    public Map<String, Double> getVariables() {
        return variables;
    }

    /**
     * Returns an immutable version of the user created variables matched to their values
     * @return - immutable map of user variables
     */

    @Override
    public Map<String, Double> getImmutableVariables() {
        return Collections.unmodifiableMap(copyVariablesMap(variables));
    }

    /**
     * Returns a list of the RGB values of the colors currently in the palette
     * @return - list of integer arrays corresponding to color RGB values
     */

    public List<int[]> getPalette() {
        return palette;
    }

    /**
     * Returns an immutable list of the RGB values of the colors currently in the palette
     * @return - immutable list of integer arrays corresponding to color RGB values
     */

    @Override
    public List<int[]> getImmutablePalette() {
        return Collections.unmodifiableList(copyPalette(palette));
    }

    /**
     * Returns a list of the current TurtleState's of the turtles in the display window
     * @return - list of current TurtleStates
     */

    public List<TurtleState> getTurtles() {
        return turtles;
    }

    /**
     * Returns an immutable list of the current TurtleState's of the turtles in the display window
     * @return - immutable list of current TurtleStates
     */

    @Override
    public List<ImmutableTurtleState> getImmutableTurtles() {
        return Collections.unmodifiableList(copyTurtlesList(turtles));
    }

    /**
     * Returns a list of the Turtle ID values of the currently active turtles
     * @return - list of Integer Turtle ID values
     */

    public List<Integer> getActiveTurtles() {
        return activeTurtles;
    }

    /**
     * Returns an immutable list of the Turtle ID values of the currently active turtles
     * @return - immutable list of Integer Turtle ID values
     */

    @Override
    public List<Integer> getImmutableActiveTurtles() {
        return Collections.unmodifiableList(copyActiveTurtlesList(activeTurtles));
    }

    /**
     * Returns the TurtleState of the Turtle that is currently executing a command
     * @return - TurtleState of currently executing Turtle
     */

    public TurtleState getCurrentExecuting() {
        return currentExecuting;
    }

    /**
     * Returns the immutable TurtleState of the Turtle that is currently executing a command
     * @return - immutable TurtleState of currently executing Turtle
     */

    @Override
    public ImmutableTurtleState getImmutableCurrentExecuting() {
        return currentExecuting.copy();
    }

    /**
     * Returns a map of the user created commands matched to their To object
     * @return - Map of user commands
     */

    public Map<String, To> getUserCommands() {
        return userCommands;
    }

    /**
     * Returns an immutable map of the user created commands matched to their To object
     * @return - immutable map of user commands
     */

    @Override
    public Map<String, To> getImmutableUserCommands() {
        return Collections.unmodifiableMap(copyUserCommandMap(userCommands));
    }

    /**
     * Sets the value of the clearScreen flag
     * @param clearScreen - boolean indicator of the call of the Clear Screen command
     */

    public void setClearScreen(boolean clearScreen) {
        this.clearScreen = clearScreen;
    }

    /**
     * Sets the palette index of the windows background color
     * @param backgroundColor - an index into the palette
     */

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets the currentExecuting value to a specific TurtleState
     * @param currentExecuting - TurtleState of new currently executing Turtle
     */

    public void setCurrentExecuting(TurtleState currentExecuting) {
        this.currentExecuting = currentExecuting;
    }

    /**
     * Sets the active turtles list to the Turtle IDs of the Turtles that should execute following commands
     * @param activeTurtles - list of integer Turtle ID values
     */

    public void setActiveTurtles(List<Integer> activeTurtles) {
        this.activeTurtles = activeTurtles;
    }

    /**
     * Performs a deep copy of the State and returns the new State object
     * @return - new identical State object
     */

    public State copy() {
        State newState = new State();
        newState.clearScreen = clearScreen;
        newState.backgroundColor = backgroundColor;
        newState.variables = copyVariablesMap(variables);
        newState.palette = copyPalette(palette);
        newState.turtles = copyTurtlesList(turtles);
        newState.activeTurtles = copyActiveTurtlesList(activeTurtles);
        newState.currentExecuting = currentExecuting.copy();
        newState.userCommands = copyUserCommandMap(userCommands);

        return newState;
    }

    private List<TurtleState> copyTurtlesList(List<TurtleState> listToCopy){
        ArrayList<TurtleState> newList = new ArrayList<>();
        for (TurtleState turtleState : listToCopy){
            newList.add(turtleState.copy());
        }
        return newList;
    }

    private Map<String, Double> copyVariablesMap(Map<String, Double> mapToCopy){
        HashMap<String, Double> newMap = new HashMap<>();
        for (String s : mapToCopy.keySet()){
            newMap.put(s, mapToCopy.get(s));
        }
        return newMap;
    }

    private List<int[]> copyPalette(List<int[]> listToCopy){
        ArrayList<int[]> newList = new ArrayList<>();
        for (int[] intArray : listToCopy){
            int[] newElement = new int[intArray.length];
            for (int i = 0; i < newElement.length; i++){
                newElement[i] = intArray[i];
            }
            newList.add(newElement);
        }
        return newList;
    }

    private List<Integer> copyActiveTurtlesList(List<Integer> listToCopy){
        ArrayList<Integer> newList = new ArrayList<>();
        for (int i : listToCopy){
            newList.add(i);
        }
        return newList;
    }

    private Map<String, To> copyUserCommandMap(Map<String, To> mapToCopy){
        HashMap<String, To> newMap = new HashMap<>();
        for (String s : mapToCopy.keySet()){
            newMap.put(s, mapToCopy.get(s));
        }
        return newMap;
    }


}