package back_end.window_state;

public class TurtleState implements ImmutableTurtleState{
    private int ID;
    private double xCor;
    private double yCor;
    private double heading;
    private boolean pendown;
    private boolean showing;
    private int penColor;
    private int penSize;
    private int turtleShape;

    /**
     * Creates an instance of a TurtleState object and sets variables to their initial values.
     * @param id - integer identification value of the specific Turtle
     */

    public TurtleState(int id){
        ID = id;
        setStartingValues();
    }

    /**
     * Performs a deep copy of the TurtleState and returns the new TurtleState object
     * @return - new identical TurtleState object
     */

    public TurtleState copy() {
        TurtleState newTurtleState = new TurtleState(ID);
        newTurtleState.xCor = xCor;
        newTurtleState.yCor = yCor;
        newTurtleState.heading = heading;
        newTurtleState.pendown = pendown;
        newTurtleState.showing = showing;
        newTurtleState.penColor = penColor;
        newTurtleState.penSize = penSize;
        newTurtleState.turtleShape = turtleShape;
        return newTurtleState;
    }

    /**
     * Sets the TurtleState's variables to their initial values
     */

    public void setStartingValues(){
        xCor = 0;
        yCor = 0;
        heading = 90;
        pendown = true;
        showing = true;
        penColor = 0;
        penSize = 1;
        turtleShape = 0;
    }

    /**
     * Returns the ID value of the corresponding Turtle
     * @return - integer ID value
     */

    @Override
    public int getID() {
        return ID;
    }

    /**
     * Returns the current x coordinate of the corresponding Turtle in the window
     * @return - double x coordinate
     */

    @Override
    public double getxCor() {
        return xCor;
    }

    /**
     * Returns the current y coordinate of the corresponding Turtle in the window
     * @return - double y coordinate
     */

    @Override
    public double getyCor() {
        return yCor;
    }

    /**
     * Returns the angle in degrees in which the Turtle is facing in the window
     * @return - double angle value in degrees
     */

    @Override
    public double getHeading() {
        return heading;
    }

    /**
     * Returns the value of the boolean flag indicating whether or not the Turtle's pen is down
     * @return - boolean flag indicating pen status
     */

    @Override
    public boolean isPendown() {
        return pendown;
    }

    /**
     * Returns the value of the boolean flag indicating whether or not the correspondingTurtle is showing in the window
     * @return - boolean flag indicating visualization status
     */

    @Override
    public boolean isShowing() {
        return showing;
    }

    /**
     * Returns the index into the palette corresponding to the pen color of the respective Turtle
     * @return - integer index into the palette
     */

    @Override
    public int getPenColor() {
        return penColor;
    }

    /**
     * Returns the size of the pen of the corresponding Turtle
     * @return - integer pen width
     */

    @Override
    public int getPenSize() {
        return penSize;
    }

    /**
     * Returns the index into the shape possibilities corresponding the the respective Turtle's visualization
     * @return - integer index into shape selections
     */

    @Override
    public int getTurtleShape() {
        return turtleShape;
    }

    /**
     * Sets the current x coordinate of the corresponding Turtle in the window
     * @param xCor - double, new x position value
     */

    public void setxCor(double xCor) {
        this.xCor = xCor;
    }

    /**
     * Sets the current y coordinate of the corresponding Turtle in the window
     * @param yCor - double, new y position value
     */

    public void setyCor(double yCor) {
        this.yCor = yCor;
    }

    /**
<<<<<<< HEAD
=======
     * Returns the angle in degrees in which the Turtle is facing in the window
     * @return - double angle value in degrees
     */

    /**
>>>>>>> 801a8390da33ae37ca5b7244824efb0035a70ed2
     * Sets the angle in degrees in which the Turtle is facing in the window
     * @param heading - double new Turtle direction value in degrees
     */

    public void setHeading(double heading) {
        this.heading = heading;
    }

    /**
     * Sets the value of the boolean flag indicating whether or not the Turtle's pen is down
     * @param pendown - new boolean value of pendown flag
     */

    public void setPendown(boolean pendown) {
        this.pendown = pendown;
    }

    /**
     * Sets the index into the palette of the corresponding Turtle's pen
     * @param penColor - integer index value into palette
     */

    public void setPenColor(int penColor) {
        this.penColor = penColor;
    }

    /**
     * Sets the size of the corresponding Turtle's pen
     * @param penSize - double desired pen width
     */

    public void setPenSize(int penSize) {
        this.penSize = penSize;
    }

    /**
     * Sets the index into the turtle shape options of the corresponding Turtle's desired visualization
     * @param turtleShape - integer index value into turtle shape options
     */

    public void setTurtleShape(int turtleShape) {
        this.turtleShape = turtleShape;
    }

//    /**
//     * Sets the ID of the turtle
//     * @param newID - int new ID of the turtle
//     */
//    public void setID(int newID){
//        this.ID = newID;
//    }
//
//    /**
//     * Sets the
//     * @param showing
//     */
//    public void setShowing(boolean showing){
//        this.showing = showing;
//    }
}
