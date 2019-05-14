package back_end.command.display_commands;

import back_end.command.Command;
import back_end.window_state.State;
import back_end.window_state.TurtleState;

import java.lang.reflect.Field;
import java.util.List;

public class ClearStamps extends Command {

    private final static int NUMBER_OF_ARGUMENTS = 0;
    private final String SHOWING_FIELD = "showing";

    public ClearStamps(){}
    public ClearStamps(List<Command> arguments) {
        super(arguments);
    }

    @Override
    protected void execute(State state) {
        value = 0;
        for(TurtleState ts : state.getTurtles()){
            if (ts.getID() < 0){
                value = 1;
//                ts.setShowing(false);

                try {
                    Class<?> c = ts.getClass();
                    final Field field = Class.forName(c.getName()).getDeclaredField(SHOWING_FIELD);
                    field.setAccessible(true);
                    field.set(ts, false);
                }
                catch (Exception e){
                    System.out.println("Field not found");
                }
            }
            System.out.println(ts.getID());
            System.out.println(ts.isShowing());
        }
    }


    /**
     * Returns number of arguments of the command
     * @return - integer number of arguments
     */

    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
