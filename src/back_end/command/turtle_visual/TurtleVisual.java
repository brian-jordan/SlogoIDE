package back_end.command.turtle_visual;

import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Abstract class for commands that change the value of turtle attributes. Uses reflection to change relevant turtle field.
 *
 * @author Lucas Liu
 */
public abstract class TurtleVisual extends TurtleCommand {

    private String stateField;
    private boolean flag;

    /**
     * Constructor for subclass. Must give name of field, and desired boolean outcome 
     * @param commands
     * @param stateField
     * @param flag
     */
    public TurtleVisual(List<Command> commands, String stateField, Boolean flag) {
        super(commands);
        this.stateField = stateField;
        this.flag = flag;
    }

    /**
     * Visual Commands should take no arguments
     * @return
     */
    @Override
    public int getNumberOfArguments() {
        return 0;
    }

    /**
     * Changes the turtle's attribute and determines value based on subclass information
     * @param turtleState
     */
    @Override
    protected void executeSingle(TurtleState turtleState) {

        this.value = flag ? 1 : 0;
        try {
            Class<?> c = turtleState.getClass();
            final Field field = Class.forName(c.getName()).getDeclaredField(stateField);
            field.setAccessible(true);
            field.set(turtleState,flag);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}