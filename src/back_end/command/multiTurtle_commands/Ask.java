package back_end.command.multiTurtle_commands;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

public class Ask extends GroupExecution {

    public Ask() {
        super();
    }
    public Ask(List<Command> arguments) {
        super(arguments);

        if (!(arguments.get(0) instanceof ListCommand)) {
            throw new IllegalArgumentException("First Command in Ask should be a ListCommand");
        }

        if (!(arguments.get(1) instanceof ListCommand)) {
            throw new IllegalArgumentException("Second Command in Ask should be a ListCommand");
        }

        for (Command c : arguments.get(0).getArguments()){
            if (c.getValue() <= 0) {
                throw new IllegalArgumentException("Turtle Indices Must be Positive");
            }
        }
    }

    @Override
    protected void setActiveTurtles(State state, List<ImmutableState> stateSequence){
        for (Command c : arguments.get(0).getArguments()){
            state.getActiveTurtles().add((int)c.getValue());
        }
    }
}
