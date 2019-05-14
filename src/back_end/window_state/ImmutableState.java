package back_end.window_state;

import back_end.command.To;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ImmutableState {

    boolean isClearScreen();

    int getBackgroundColor();

    Map<String, Double> getImmutableVariables();

    List<int[]> getImmutablePalette();

    List<ImmutableTurtleState> getImmutableTurtles();

    List<Integer> getImmutableActiveTurtles();

    ImmutableTurtleState getImmutableCurrentExecuting();

    Map<String, To> getImmutableUserCommands();

}
