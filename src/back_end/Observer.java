package back_end;

import back_end.window_state.ImmutableState;
import back_end.window_state.State;

import java.util.List;

/**
 * 
 */
public interface Observer {


    void reportChange( List<ImmutableState> stateSequence);

}