package front_end.utils;

import back_end.window_state.State;
import javafx.scene.shape.Line;

import java.util.List;

/**
 * Created by Anshu Dwibhashi
 * Just a history structure to save history
 */
public class History {
    public List<Line> drawings;
    public State state;
    public String output;
    public History(List<Line> drawings, State state, String output) {
        this.drawings = drawings; this.state = state; this.output = output;
    }
}
