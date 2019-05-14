package front_end.controls;

import front_end.screens.MainScreen;
import javafx.scene.image.Image;

/**
 * Author: Anshu Dwibhashi
 * Control to open menu
 */
public class CloseControl extends Control {
    private MainScreen context;

    public CloseControl(MainScreen context) {
        super(new Image(context.getClass().getResourceAsStream("/img/close.png")));
        this.context = context;
    }

    /**
     * Method executed when this control is clicked
     */
    @Override
    public void onClick() {
        context.closeMenu();
    }
}
