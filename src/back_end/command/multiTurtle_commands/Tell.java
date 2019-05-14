package back_end.command.multiTurtle_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.window_state.State;
import back_end.window_state.TurtleState;

import java.util.List;

public class Tell extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 1;

    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    public Tell() {
        super();
    }
    public Tell(List<Command> arguments) {
        super(arguments);
        if (!(arguments.get(0) instanceof ListCommand)) {
            throw new IllegalArgumentException("First Command in Tell should be a ListCommand");
        }

        for (Command c : arguments.get(0).getArguments()){
            if (c.getValue() <= 0) {
                throw new IllegalArgumentException("Turtle Indices Must be Positive");
            }
        }
    }

    @Override
    protected void execute(State state) {
        state.getActiveTurtles().clear();
        for (Command c : arguments.get(0).getArguments()){
            if (c.getValue() <= state.getTurtles().size()){
                state.getActiveTurtles().add((int)c.getValue());
            }
            else {
                makeNewTurtles(c, state);
            }
        }
        double firstActive = state.getActiveTurtles().get(0);
        state.setCurrentExecuting(state.getTurtles().get(((int)firstActive - 1)));
        value = state.getActiveTurtles().get(state.getActiveTurtles().size() - 1);
    }

    private void makeNewTurtles(Command c, State s){
        int initialNumTurtles = s.getTurtles().size();
        for (int i = initialNumTurtles + 1; i <= c.getValue(); i++){
            TurtleState newTurtle = new TurtleState(i);
            s.getTurtles().add(newTurtle);
            s.getActiveTurtles().add(i);
        }
    }
}
