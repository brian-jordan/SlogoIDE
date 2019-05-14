package back_end.command.turtle_other;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.State;
import back_end.window_state.TurtleState;

import java.lang.reflect.Field;
import java.util.List;

public class Stamp extends TurtleCommand {

    private final static int NUMBER_OF_ARGUMENTS = 0;
    private TurtleState myNewStamp;
    private final String ID_FIELD = "ID";

    public Stamp() {
        super();
    }

    public Stamp(List<Command> arguments) {
        super(arguments);
    }


    @Override
    protected void executeSingle(TurtleState turtleState) {
        myNewStamp = turtleState.copy();
//        myNewStamp.setID(-1 * turtleState.getID());
        try {
            Class<?> c = myNewStamp.getClass();
            final Field field = Class.forName(c.getName()).getDeclaredField(ID_FIELD);
            field.setAccessible(true);
            field.set(myNewStamp, -1 * turtleState.getID());
        }
        catch (Exception e){
            System.out.println("Field not found");
        }
        System.out.println(myNewStamp.getID());
    }

    @Override
    protected void execute(State state){
        state.getTurtles().add(myNewStamp);
        // Try this
//        state.getActiveTurtles().add(myNewStamp.getID());
        value = -1 * myNewStamp.getID();
    }


    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */
    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
