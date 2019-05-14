package back_end.command.query;


import back_end.command.Command;
import back_end.command.TurtleCommand;
import back_end.window_state.TurtleState;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Abstract class for any queries of State information. Uses reflection to determine correct field at runtime.
 *
 * @author Lucas Liu
 */
public abstract class QueryCommand extends TurtleCommand {
    private String stateField;

    private final static int NUMBER_OF_ARGUMENTS = 0;

    public QueryCommand(List<Command> arguments) {
        super(arguments);
    }

    /**
     * Construct queryCommand with the String name of the desired field
     * @param stateField
     */
    public QueryCommand(String stateField){
        this.stateField = stateField;
    }

    /**
     * Query Commands should have no arguments passed
     * @return
     */
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }

    /**
     * type of turtle command, so it needs to implement executeSingle. For Queries, this means using reflection to extract the correct field name
     * @param turtleState
     */
    @Override
    protected void executeSingle(TurtleState turtleState){
        try {
            Class<?> c = turtleState.getClass();
            final Field field = Class.forName(c.getName()).getDeclaredField(stateField);
            field.setAccessible(true);
            value = (double) field.get(new TurtleState(turtleState.getID()));
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field does Not Exist");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to execute QueryCommand");
        }
    }


}