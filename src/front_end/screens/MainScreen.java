package front_end.screens;

import back_end.Observer;
import back_end.Parser;
import back_end.command.Command;
import back_end.model_info.Model;
import back_end.props_parser_utils.ClassNameParser;
import back_end.window_state.ImmutableState;
import back_end.window_state.ImmutableTurtleState;
import back_end.window_state.State;
import back_end.window_state.TurtleState;
import front_end.RunSlogo;
import front_end.controls.CloseControl;
import front_end.controls.Control;
import front_end.uitools.AutocompleteField;
import front_end.uitools.TextGenerator;
import front_end.utils.Dialogs;
import front_end.utils.History;
import front_end.utils.XMLGenerator;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Element;
import util.ParserTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

import static front_end.Dimensions.*;
import static front_end.RunSlogo.bebasKai;
import static front_end.RunSlogo.bebasKaiMedium;
import static front_end.Strings.ENV_DEFAULT_TITLE;

public class MainScreen implements Observer{
    private TextArea prompt, output;
    private List<TurtleStruct> turtles;
    private ImageView runButton, menuButton;
    private List<Line> drawings;
    private Group container;
    private Color turtleColor;
    private RunSlogo context;
    private double originX, originY;
    private Model model;
    private boolean overridePenColor = false, overridePenImage = false;
    private List<ImageView> penImages;
    private int numTurtles;
    private boolean isShiftPressed = false;
    private List<String> cmdStrHistory = new ArrayList<>();
    private List<List<String>> cmdStrHistoryHistory = new ArrayList<>();
    private List<String> outputHistory = new ArrayList<>();

    public MainScreen() {
        drawings = new ArrayList<>();
        turtleColor = Color.BLACK;
        model = new Model();
        latestState = new State();
        model.addObserver(this);
        numTurtles = 1;
        turtles = new ArrayList<>();
        penImages = new ArrayList<>();
    }

    private Stage stage;
    private Rectangle bgRect;

    public Stage createMainScreenAndLoad(Stage stage, RunSlogo context) {
        Stage toreturn = createMainScreen(stage, context);
        handleLoadEnvironment();
        return toreturn;
    }

    public Stage createMainScreen(Stage stage, RunSlogo context) {
        var root = new Group();
        container = new Group();
        root.getChildren().add(container);
        // create a place to see the shapes
        var scene = new Scene(root, ENV_WINDOW_WIDTH, ENV_WINDOW_HEIGHT, Color.WHITE);

        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                isShiftPressed = false;
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                isShiftPressed = true;
            }
        });

        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle(ENV_DEFAULT_TITLE);

        bgRect = new Rectangle(0, 0, ENV_WINDOW_WIDTH, STAGE_HEIGHT);
        bgRect.setFill(Color.WHITE);
        bgRect.setViewOrder(5);
        container.getChildren().addAll(bgRect);

        setupScene(container);

        this.context = context;

        stage.setOnCloseRequest(e -> context.notifyMainScreenClosed());
        stage.setResizable(false);
        this.stage = stage;

        return stage;
    }

    private Group menuGroup;

    /**
     * Display the options menu
     */
    public void showMenu() {
        menuGroup = new Group();
        Rectangle dialogBox = new Rectangle(0, 0, 350, 550);
        dialogBox.setEffect(new DropShadow(25, 0, 0, Color.web("#333333")));
        dialogBox.setArcWidth(20);
        dialogBox.setArcHeight(20);
        dialogBox.setFill(Color.WHITE);

        double originY = stage.getScene().getHeight()/2 - dialogBox.getLayoutBounds().getHeight()/2 - 15;
        double originX = stage.getScene().getWidth()/2 - dialogBox.getLayoutBounds().getWidth()/2;

        Control closeControl = new CloseControl(this);
        closeControl.getView().setLayoutX(dialogBox.getWidth() - 50);
        closeControl.getView().setLayoutY(10);
        closeControl.getView().setCursor(Cursor.HAND);
        closeControl.getView().setTooltip(new Tooltip("Close Menu"));

        Text graphText = TextGenerator.makeTextRelative("Change Pen Color", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85);
        graphText.setCursor(Cursor.HAND);
        graphText.setOnMouseClicked((event)->{
            handlePenColorChange();
        });

        Text settingsText = TextGenerator.makeTextRelative("Change Background Color", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 40);
        settingsText.setCursor(Cursor.HAND);
        settingsText.setOnMouseClicked((event)->{
            handleBgColorChange();
        });

        Text saveText = TextGenerator.makeTextRelative("Change Pen Image", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 80);
        saveText.setCursor(Cursor.HAND);
        saveText.setOnMouseClicked((event)->{
            handlePenImageChange();
        });

        Text compareText = TextGenerator.makeTextRelative("Change Language", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 120);
        compareText.setCursor(Cursor.HAND);
        compareText.setOnMouseClicked((event)->{
            handleLanguage();
        });

        Text variableText = TextGenerator.makeTextRelative("Show Variables In Workspace", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 160);
        variableText.setCursor(Cursor.HAND);
        variableText.setOnMouseClicked((event)->{
            handleVariables();
        });

        Text colourPaletteText = TextGenerator.makeTextRelative("Show Available Colours", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 200);
        colourPaletteText.setCursor(Cursor.HAND);
        colourPaletteText.setOnMouseClicked((event)->{
            handleColourPalette();
        });

        Text shapePaletteText = TextGenerator.makeTextRelative("Show Available Shapes", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 240);
        shapePaletteText.setCursor(Cursor.HAND);
        shapePaletteText.setOnMouseClicked((event)->{
            handleShapePalette();
        });

        Text joystickText = TextGenerator.makeTextRelative("Joystick", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 280);
        joystickText.setCursor(Cursor.HAND);
        joystickText.setOnMouseClicked((event)->{
            handleJoystick();
        });

        Text newEnvText = TextGenerator.makeTextRelative("New Environment", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 320);
        newEnvText.setCursor(Cursor.HAND);
        newEnvText.setOnMouseClicked((event)->{
            handleNewEnvironment();
        });

        Text saveEnvText = TextGenerator.makeTextRelative("Save Environment", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 360);
        saveEnvText.setCursor(Cursor.HAND);
        saveEnvText.setOnMouseClicked((event)->{
            handleSaveEnvironment();
        });

        Text loadEnvText = TextGenerator.makeTextRelative("Load Environment", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 400);
        loadEnvText.setCursor(Cursor.HAND);
        loadEnvText.setOnMouseClicked((event)->{
            handleLoadEnvironment();
        });

        Text customCommands = TextGenerator.makeTextRelative("Show Custom Commands", bebasKaiMedium, Color.SLATEGREY,
                dialogBox.getWidth()/2, 85 + 440);
        customCommands.setCursor(Cursor.HAND);
        customCommands.setOnMouseClicked((event)->{
            handleCustomCommands();
        });

        menuGroup.getChildren().addAll(dialogBox, closeControl.getView(), graphText, settingsText, saveText,
                compareText, variableText, colourPaletteText, shapePaletteText, joystickText, newEnvText,
                saveEnvText, loadEnvText, customCommands);
        menuGroup.setLayoutY(originY - 50);
        menuGroup.setLayoutX(originX);

        container.getChildren().addAll(menuGroup);
    }

    private void handleLoadEnvironment() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("data/config/"));
        File inputFile = fileChooser.showOpenDialog(stage);
        if (inputFile != null) {
            Element rootNode = ParserTools.getRootNode(inputFile);
            String[] commands = ((Element) rootNode.getElementsByTagName("Commands").item(0)).getTextContent().split("\\s+");
            this.cmdStrHistory.addAll(Arrays.asList(commands));
            String separator = (output.getText().isBlank() || output.getText().isEmpty()) ? "" : "\n";
            output.setText(output.getText() + separator +
                    ((Element) rootNode.getElementsByTagName("Output").item(0)).getTextContent());
            model.executeCommands(new Parser().parse(commands));
            stage.setTitle(inputFile.getName());
        }
        closeMenu();
    }

    private void handleSaveEnvironment() {
        String xml = XMLGenerator.makeXML(cmdStrHistory.toArray(new String[0]), output.getText());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("data/config/"));
        File chosenFile = fileChooser.showSaveDialog(stage);
        if (chosenFile != null) {
            // Now save current state in this file
            if(!chosenFile.getAbsolutePath().endsWith(".env")) {
                chosenFile = new File(chosenFile.getAbsolutePath()+".env");
            }
            try (PrintStream out = new PrintStream(new FileOutputStream(chosenFile.getAbsolutePath()))) {
                out.print(xml);
                Dialogs.showAlert("File saved!");
                stage.setTitle(chosenFile.getName());
            } catch (Exception e) {
                // They just clicked cancel. No need to handle.
            }
        }
        closeMenu();
    }

    private void handleNewEnvironment() {
        closeMenu();
        Stage newStage = new MainScreen().createMainScreen(new Stage(), context);
        newStage.show();
        context.notifyOfNewMainScreen();
        newStage.setOnCloseRequest(e -> context.notifyMainScreenClosed());
        newStage.setResizable(false);
    }

    private void handleJoystick() {
        closeMenu();
        new JoystickScreen(model, latestState);
    }

    private void handleShapePalette() {
        closeMenu();
        new ShapePaletteScreen();
    }

    private void handleColourPalette() {
        closeMenu();
        new ColourPaletteScreen(latestState);
    }

    private void handleLanguage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("data/config/"));
        File chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile != null) {
            try {
                ClassNameParser.init(chosenFile.getAbsolutePath());
                closeMenu();
            } catch (Exception e) {
                Dialogs.showAlert("Error while choosing file!");
            }
        }
    }

    private void handleVariables() {
        closeMenu();
        new VariableScreen(latestState.getImmutableVariables(), model);
    }

    private void handleCustomCommands() {
        closeMenu();
        new CustomCommandsScreen(latestState.getImmutableUserCommands(), model);
    }

    private void handlePenImageChange() {
        closeMenu();
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            int index= 0;
            for (var tStruct : turtles) {
                if (isTurtleActive(tStruct.ID, latestState)) {
                    Image image = new Image(file.toURI().toString());
                    penImages.get(index).setImage(image);
                    penImages.get(index).setX(penImages.get(index).getX() - penImages.get(index).getLayoutBounds().getWidth() / 2);
                    penImages.get(index).setY(penImages.get(index).getY() - penImages.get(index).getLayoutBounds().getHeight() / 2);
                    penImages.get(index).setVisible(true);
                    tStruct.turtleShape.setVisible(false);
                    overridePenImage = true;
                }
                index++;
            }
        }
    }

    private void handlePenColorChange() {
        closeMenu();
        Stage stage = new Stage();
        stage.setTitle("Pen Color");
        Scene scene = new Scene(new HBox(20), 100, 50);
        HBox box = (HBox) scene.getRoot();
        box.setPadding(new Insets(5, 5, 5, 5));
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(turtleColor);
        colorPicker.setOnAction(e -> {
            for (var tStruct : turtles) {
                if (MainScreen.this.isTurtleActive(tStruct.ID, latestState)) {
                    turtleColor = colorPicker.getValue();
                    overridePenColor = true;
                    tStruct.turtleShape.setFill(colorPicker.getValue());
                    tStruct.turtleShape.setStroke(colorPicker.getValue());
                    stage.close();
                }
            }
        });
        box.getChildren().addAll(colorPicker);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private void handleBgColorChange() {
        closeMenu();
        Stage stage = new Stage();
        stage.setTitle("Background Color");
        Scene scene = new Scene(new HBox(20), 100, 50);
        HBox box = (HBox) scene.getRoot();
        box.setPadding(new Insets(5, 5, 5, 5));
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color)bgRect.getFill());
        colorPicker.setOnAction(e -> {
            bgRect.setFill(colorPicker.getValue());
            stage.close();
        });
        box.getChildren().addAll(colorPicker);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }


    /**
     * Close the options menu
     */
    public void closeMenu() {
        container.getChildren().remove(menuGroup);
        menuGroup = null;
    }

    private List<String> makeStringDeepCopy(List<String> a) {
        List<String> b = new ArrayList<>();
        for (var x : a) {
            b.add(x+"");
        }
        return b;
    }

    private ImmutableState latestState;

    /**
     * Model notifies us of state changes here
     * @param stateSequence sequence of states
     */
    @Override
    public void reportChange(List<ImmutableState> stateSequence) {
        if (stateSequence.size() > 0) {
            if (stateSequence.get(0).getImmutableTurtles().size() > numTurtles) {
                int newTurtles = stateSequence.get(0).getImmutableTurtles().size() - numTurtles;
                while (newTurtles-- > 0) {
                    turtleCreatorHelper(container, numTurtles++);
                }
            }
            ImmutableState latest = null;
            for (ImmutableState state : stateSequence) {
                renderState(state);
                latest = state;
            }
            distingushTurtles(latestState);
            latestState = latest;
        }
    }

    private void distingushTurtles(ImmutableState state) {
        // Go thru all turtles and mark inactive ones differently
        for(var tStruct : turtles) {
            if(!state.getImmutableActiveTurtles().contains(tStruct.ID)) {
                tStruct.turtleShape.setStrokeWidth(5);
            }
        }
    }

    private boolean isTurtleActive(int ID, ImmutableState state) {
        return state.getImmutableActiveTurtles().contains(ID);
    }

    private void renderState(ImmutableState state) {
        if (state.isClearScreen()) {
            System.out.println("Clear screen!");
            clearScreen(state);
        } else {
            setBackgroundColour(state);
            setTurtlesDown(state);
            setTurtleShape(state);
            setTurtleColour(state);
            showHideTurtle(state);
            rotateTurtle(state);
            drawLine(state);
            moveTurtle(state);
        }
    }

    private void setBackgroundColour(ImmutableState state) {
        int r = state.getImmutablePalette().get(state.getBackgroundColor())[0];
        int g = state.getImmutablePalette().get(state.getBackgroundColor())[1];
        int b = state.getImmutablePalette().get(state.getBackgroundColor())[2];
        bgRect.setFill(Color.rgb(r, g, b));
    }

    private void setTurtlesDown(ImmutableState state) {
        int index = 0;
        for (ImmutableTurtleState s : state.getImmutableTurtles()) {
            turtles.get(index++).down = s.isPendown();
        }
    }

    private void drawLine(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state) && t.isPendown()) {
                double yNew = originY - t.getyCor();
                if (yNew > STAGE_HEIGHT) {
                    yNew = STAGE_HEIGHT;
                }
                double yOld = turtles.get(index).turtleShape.getLayoutY();
                if (yOld > STAGE_HEIGHT) {
                    yOld = STAGE_HEIGHT;
                }
                Line line = new Line(turtles.get(index).turtleShape.getLayoutX(), yOld, originX + t.getxCor(), yNew);
                line.setStrokeWidth(t.getPenSize());
                line.setStroke(turtleColor);
                drawings.add(line);
                container.getChildren().add(line);
            }
            index++;
        }
    }

    private void moveTurtle(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state)) {
                turtles.get(index).turtleShape.setLayoutX(originX + t.getxCor());
                turtles.get(index).turtleShape.setLayoutY(originY - t.getyCor());
                penImages.get(index).setLayoutX(originX + t.getxCor());
                penImages.get(index).setLayoutY(originY - t.getyCor());
                turtles.get(index).turtleShape.setViewOrder(4);
                penImages.get(index).setViewOrder(4);
            }
            index++;
        }
    }

    private void rotateTurtle(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state)) {
                turtles.get(index).turtleShape.setRotate(-t.getHeading() + 90);
                penImages.get(index).setRotate(-t.getHeading() + 90);
            }
            index++;
        }
    }

    private void showHideTurtle(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state)) {
                if (!overridePenImage)
                    turtles.get(index).turtleShape.setVisible(t.isShowing());
                else
                    penImages.get(index++).setVisible(t.isShowing());
            }
        }
    }

    private void setTurtleColour(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state)) {
                if (!overridePenColor) {
                    int r = state.getImmutablePalette().get(t.getPenColor())[0];
                    int g = state.getImmutablePalette().get(t.getPenColor())[1];
                    int b = state.getImmutablePalette().get(t.getPenColor())[2];
                    this.turtleColor = Color.rgb(r, g, b);
                    turtles.get(index).turtleShape.setStroke(turtleColor);
                    turtles.get(index).turtleShape.setFill(turtleColor);
                }
            }
            index++;
        }
    }

    private  void setTurtleShape(ImmutableState state) {
        int index = 0;
        for (var t : state.getImmutableTurtles()) {
            if (isTurtleActive(t.getID(), state)) {
                if (!overridePenImage) {
                    turtles.get(index).shape = t.getTurtleShape();
                    double[] pointsUpwards = new double[(3+t.getTurtleShape())*2];
                    for (int i = 0; i < (3+t.getTurtleShape())*2; i += 2) {
                        double angle = Math.PI * (0.5 + i / ((double) (3+t.getTurtleShape())));
                        pointsUpwards[i] = Math.cos(angle);
                        pointsUpwards[i + 1] = -Math.sin(angle);
                    }
                    var newPoly = new Polygon(pointsUpwards);
                    newPoly.setLayoutY(turtles.get(index).turtleShape.getLayoutY());
                    newPoly.setLayoutX(turtles.get(index).turtleShape.getLayoutX());
                    container.getChildren().remove(turtles.get(index).turtleShape);
                    turtles.get(index).turtleShape = newPoly;
                    container.getChildren().add(turtles.get(index).turtleShape);
                    turtles.get(index).turtleShape.setStrokeWidth(10);
                    turtles.get(index).attachListener();
                }
            }
            index++;
        }
    }

    private void clearScreen(ImmutableState state) {
        if (state.isClearScreen()) {
            for (var d : drawings) {
                container.getChildren().remove(d);
            }
            drawings.clear();
            turtles.get(0).turtleShape.setLayoutX(ENV_WINDOW_WIDTH / 2);
            turtles.get(0).turtleShape.setLayoutY(STAGE_HEIGHT / 2);
            turtles.get(0).turtleShape.setRotate(0);
        }
        int index = 0;
        for (var t : turtles) {
            if (index++ != 0)
                container.getChildren().remove(t.turtleShape);
        }
        Iterator<TurtleStruct> iter = turtles.iterator();
        iter.next();
        while (iter.hasNext()) {
            iter.remove();
            iter.next();
        }
    }

    private void setupScene(Group container) {
        createBackgroundRectangles(container);
        createAndPlaceTurtle(container);
        createAndPlaceRunButton(container);
        createAndPlaceMenuButton(container);
        createAndPlaceHistoryButtons(container);
    }

    private void createAndPlaceHistoryButtons(Group container) {
        var prevHistory = new ImageView(new Image(MainScreen.class.getResourceAsStream("/img/historyprev.png")));
        prevHistory.setFitWidth(30);
        prevHistory.setFitHeight(30);
        prevHistory.setLayoutX(ENV_WINDOW_WIDTH - 40);
        prevHistory.setLayoutY(ENV_WINDOW_HEIGHT - 100);
        prevHistory.setCursor(Cursor.HAND);
        prevHistory.setOnMouseClicked(e -> {
            if (cmdStrHistoryHistory.size() > 0) {
                cmdStrHistoryHistory.remove(cmdStrHistoryHistory.size() - 1);
                outputHistory.remove(outputHistory.size() - 1);
                for (var d : drawings) {
                    container.getChildren().remove(d);
                }

                for (var t : turtles) {
                    container.getChildren().remove(t.turtleShape);
                }


                drawings = new ArrayList<>();
                turtleColor = Color.BLACK;
                model = new Model();
                latestState = new State();
                model.addObserver(this);
                numTurtles = 1;
                turtles = new ArrayList<>();
                penImages = new ArrayList<>();
                createAndPlaceTurtle(container);
                renderState(latestState);


                if (cmdStrHistoryHistory.size() > 0) {
                    model.executeCommands(new Parser().parse(cmdStrHistoryHistory.get(cmdStrHistoryHistory.size() - 1).toArray(new String[0])));
                    output.setText(outputHistory.get(outputHistory.size() - 1));
                } else {
                    output.setText("");
                }
            }
        });
        Tooltip t = new Tooltip("Undo");
        Tooltip.install(prevHistory, t);

        container.getChildren().addAll(prevHistory);
    }

    private void createAndPlaceRunButton(Group container) {
        runButton = new ImageView(new Image(MainScreen.class.getResourceAsStream("/img/runbutton.png")));
        runButton.setFitWidth(30);
        runButton.setFitHeight(30);
        runButton.setLayoutX(ENV_WINDOW_WIDTH - 40);
        runButton.setLayoutY(ENV_WINDOW_HEIGHT- 40);
        runButton.setCursor(Cursor.HAND);
        runButton.setOnMouseClicked(e -> {
            String[] cmdText;
            String separator = (output.getText().isBlank() || output.getText().isEmpty() ? "" : "\n");
            if (prompt.getText().isBlank() || prompt.getText().isEmpty()) {
                if (!output.getSelectedText().isEmpty() && !output.getSelectedText().isBlank()) {
                    cmdText = output.getSelectedText().split("\\s+");
                    output.setText(output.getText() + separator + output.getSelectedText());
                } else {
                    return;
                }
            } else {
                cmdText = prompt.getText().toLowerCase().split("\\s+");
                output.setText(output.getText() + separator + prompt.getText());
            }
            separator = "\n";
            try {
                List<Command> commands = new Parser().parse(cmdText);
                model.executeCommands(commands);
                cmdStrHistory.addAll(Arrays.asList(cmdText));
                cmdStrHistoryHistory.add(makeStringDeepCopy(cmdStrHistory));
                outputHistory.add(output.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                output.setText(output.getText() + separator + "Invalid command: " + ex.getMessage());
            }
            prompt.setText("");
            output.setScrollTop(output.getMaxHeight());

        });
        Tooltip t = new Tooltip("Run commands");
        Tooltip.install(runButton, t);
        container.getChildren().add(runButton);
    }

    private void createAndPlaceMenuButton(Group container) {
        menuButton = new ImageView(new Image(MainScreen.class.getResourceAsStream("/img/menubutton.png")));
        menuButton.setFitWidth(30);
        menuButton.setFitHeight(30);
        menuButton.setLayoutX(ENV_WINDOW_WIDTH - 40);
        menuButton.setLayoutY(STAGE_HEIGHT + 10);
        menuButton.setCursor(Cursor.HAND);
        menuButton.setOnMouseClicked(e -> showMenu());
        Tooltip t = new Tooltip("Show menu");
        Tooltip.install(runButton, t);
        container.getChildren().add(menuButton);
    }

    private void createAndPlaceTurtle(Group container) {
        turtleCreatorHelper(container, 1);
    }

    private void turtleCreatorHelper(Group container, int ID) {
        double[] pointsUpwards = new double[6];
        for (int i = 0; i < 6; i += 2) {
            double angle = Math.PI * (0.5 + i / 3d);
            pointsUpwards[i] = Math.cos(angle);
            pointsUpwards[i + 1] = -Math.sin(angle);
        }
        var turtle = new Polygon(pointsUpwards);
        turtle.setLayoutX(ENV_WINDOW_WIDTH/2);
        originX = ENV_WINDOW_WIDTH/2;
        turtle.setLayoutY(STAGE_HEIGHT/2);
        originY = STAGE_HEIGHT/2;
        turtle.setStroke(Color.BLACK);
        turtle.setStrokeWidth(10);
        var penImage = new ImageView();
        penImage.setVisible(false);
        penImage.setLayoutX(ENV_WINDOW_WIDTH/2);
        penImage.setLayoutY(STAGE_HEIGHT/2);
        penImages.add(penImage);
        container.getChildren().addAll(penImage);

        container.getChildren().add(turtle);
        var tStruct = new TurtleStruct(turtle, ID, true);
        turtles.add(tStruct);
    }

    private void createBackgroundRectangles(Group container) {
        var controlRectangle = new Rectangle(ENV_WINDOW_WIDTH, ENV_WINDOW_HEIGHT - STAGE_HEIGHT);
        controlRectangle.setLayoutY(ENV_WINDOW_HEIGHT - controlRectangle.getLayoutBounds().getHeight());
        controlRectangle.setFill(Color.MIDNIGHTBLUE);
        controlRectangle.setViewOrder(2);

        var promptRectangle = new Rectangle(ENV_WINDOW_WIDTH, 75);
        promptRectangle.setLayoutY(ENV_WINDOW_HEIGHT - 50);
        promptRectangle.setFill(Color.MIDNIGHTBLUE);
        promptRectangle.setViewOrder(2);

        var promptText = new Text(">>");
        promptText.setStroke(Color.WHITE);
        promptText.setFill(Color.WHITE);
        promptText.setFont(bebasKai);
        promptText.setX(15);
        promptText.setViewOrder(0);
        promptText.setY(ENV_WINDOW_HEIGHT - VERTICAL_PADDING*1.5);

        AutocompleteField ta = new AutocompleteField();
        ta.getEntries().addAll(ClassNameParser.getAvailableCommands());
        ta.setMaxHeight(20);
        ta.setPrefHeight(20);
        ta.setViewOrder(0);
        ta.setBorder(Border.EMPTY);
        ta.setBackground(Background.EMPTY);
        ta.setMaxWidth(ENV_WINDOW_WIDTH - 100);
        ta.setPrefWidth(ENV_WINDOW_WIDTH - 100);

        ta.setFont(bebasKai);
        ta.setLayoutX(50);
        ta.setLayoutY(ENV_WINDOW_HEIGHT - 50 - 2.5);
        ta.setStyle(".text-area { -fx-text-box-border: none; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #F8F8FF;}");
        prompt = ta;

        TextArea taInactive = new TextArea();
        taInactive.setMaxHeight(175);
        taInactive.setPrefHeight(175);
        taInactive.setViewOrder(0);
        taInactive.setBorder(Border.EMPTY);
        taInactive.setBackground(Background.EMPTY);
        taInactive.setMaxWidth(ENV_WINDOW_WIDTH - 100);
        taInactive.setPrefWidth(ENV_WINDOW_WIDTH - 100);

        taInactive.setFont(bebasKai);
        taInactive.setLayoutX(50);
        taInactive.setEditable(false);
        taInactive.setLayoutY(ENV_WINDOW_HEIGHT - 50 - 175 - 15 - 2.5);
        taInactive.setStyle(".text-area { -fx-text-box-border: none; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #F8F8FF;} .text-area > .scroll-pane{\n" +
                "\n" +
                "-fx-vbar-policy:always; /* Control the vertical ScrollBar (always,needed,never) */\n" +
                "-fx-hbar-policy:never; /* Control the horizontal ScrollBar (always,needed,never) */\n" +
                "\n" +
                "}");
        output = taInactive;

        container.getChildren().addAll(controlRectangle, promptRectangle, promptText, ta, taInactive);
    }

    private class TurtleStruct {
        public Polygon turtleShape;
        public int ID;
        public boolean down;
        public int shape = 0;
        public boolean isStatusDisplaying = false;
        public TurtleStruct(Polygon turtleShape, int ID, boolean down) {
            this.turtleShape = turtleShape; this.ID = ID; this.down = down;
            attachListener();
        }
        public void attachListener() {
            this.turtleShape.setOnMouseClicked(e -> {
                if (!isStatusDisplaying && isShiftPressed) {
                    System.out.println("Here 1");
                    isStatusDisplaying = true;
                    new StatusScreen(latestState, ID, () -> isStatusDisplaying = false);
                } else {
                    System.out.println("Here 2");
                    if (isTurtleActive(ID, latestState)) {
                        latestState.getImmutableActiveTurtles().remove((Integer) ID);
                        turtleShape.setStrokeWidth(5);
                    } else {
                        latestState.getImmutableActiveTurtles().add(ID);
                        turtleShape.setStrokeWidth(10);
                    }
                    String toExecute = "tell [ ";
                    for (var i : latestState.getImmutableActiveTurtles()) {
                        toExecute += i.toString()+" ";
                    }
                    toExecute+="]";
                    System.out.println(toExecute);
                    model.executeCommands(new Parser().parse(toExecute.split("\\s+")));
                }
            });
        }
    }
}
