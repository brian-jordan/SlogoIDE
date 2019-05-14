package front_end;

import back_end.props_parser_utils.ClassNameParser;
import back_end.props_parser_utils.ConstructorParser;
import back_end.props_parser_utils.NumArgsParser;
import front_end.screens.MainScreen;
import front_end.screens.SplashScreen;
import front_end.utils.ScreenType;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static front_end.utils.ScreenType.MAIN_SCREEN;
import static front_end.utils.ScreenType.SPLASH_SCREEN;

/**
 * 
 */
public class RunSlogo extends Application {
    // Global constants
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 500;
    public static final String WINDOW_TITLE = "SLogo";
    public static final Paint BACKGROUND = Color.GHOSTWHITE;

    public static  Font sofiaPro, sofiaProSmall, bebasKai, bebasKaiMedium;


    private Stage mainStage;

    public boolean isInTransition() {
        return inTransition;
    }

    public void setInTransition(boolean inTransition) {
        this.inTransition = inTransition;
    }

    private boolean inTransition = false;
    private ScreenType currentScreen = SPLASH_SCREEN;

    /**
     * Sets variable indicating what type of a screen is currently active
     *
     * @param currentScreen
     */
    public void setCurrentScreen(ScreenType currentScreen) {
        this.currentScreen = currentScreen;
    }

    /**
     * Function where we build the stage with the first scene
     * @param stage provided to us by the library
     * @throws Exception
     */

    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        mainStage = new SplashScreen().createSplashScreen(stage, this);

        stage.show();
        stage.setResizable(false);
    }
    /**
     * Main function to serve as entry point
     * @param args: cmd line args
     */
    public static void main(String args[]) {
        // load custom font
        try {
            NumArgsParser.init();
            ConstructorParser.init();
            ClassNameParser.init("English");

            sofiaPro = Font.loadFont(RunSlogo.class.getResource("/fonts/sofiapro-light.otf").openStream(),30);
            sofiaProSmall = Font.loadFont(RunSlogo.class.getResource("/fonts/sofiapro-light.otf").openStream(),15);
            bebasKai = Font.loadFont(RunSlogo.class.getResource("/fonts/bebaskai.otf").openStream(),15);
            bebasKaiMedium = Font.loadFont(RunSlogo.class.getResource("/fonts/bebaskai.otf").openStream(),25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }

    int numOfMainScreensSpawned = 0;

    public void notifyOfNewMainScreen() {
        numOfMainScreensSpawned += 1;
    }

    public void notifyMainScreenClosed() {
        numOfMainScreensSpawned -= 1;
        if (numOfMainScreensSpawned == 0) {
            loadSplashScreen();
        }
    }

    public void loadMainScreen () {
        numOfMainScreensSpawned += 1;
        mainStage.close();
        mainStage = new MainScreen().createMainScreen(new Stage(), this);
        currentScreen = MAIN_SCREEN;

        mainStage.show();
    }

    public void loadMainScreenAndLoad () {
        numOfMainScreensSpawned += 1;
        mainStage.close();
        mainStage = new MainScreen().createMainScreenAndLoad(new Stage(), this);
        currentScreen = MAIN_SCREEN;

        mainStage.show();
    }

    public void loadSplashScreen () {
        mainStage.close();
        mainStage = new SplashScreen().createSplashScreen(new Stage(), this);
        currentScreen = MAIN_SCREEN;

        mainStage.show();
    }

}