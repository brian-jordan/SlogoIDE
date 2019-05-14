package back_end.command.multiTurtle_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.query.QueryCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;
import back_end.window_state.TurtleState;

import java.util.List;

public class AskWith extends GroupExecution {

    public AskWith() {
        super();
    }
    public AskWith(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(0) instanceof QueryCommand)) {
            throw new IllegalArgumentException("First Command in AskWith should be a QueryCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in AskWith should be a ListCommand");
        }
    }

    @Override
    protected void setActiveTurtles(State state, List<ImmutableState> stateSequence){
        for (TurtleState turtleState : state.getTurtles()){
            state.getActiveTurtles().add(turtleState.getID());
            arguments.get(0).calculateNewState(state, stateSequence);
            if(arguments.get(0).getValue() != 1){
                state.getActiveTurtles().remove(state.getActiveTurtles().size() - 1);
            }
        }
    }


}
