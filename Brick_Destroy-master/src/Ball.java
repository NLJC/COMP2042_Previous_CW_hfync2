import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
public abstract class Ball {

    private Shape ballShape;

    private Point2D ballCoordinates;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color borderColor;
    private Color innerColor;

    public int velocityX;
    public int velocityY;

    /**
     * @param ballCoordinates
     * @param ballXLength
     * @param ballYLength
     * @param innerColor
     * @param borderColor
     * declare up, down, left, right, create ball shape and ball colors
     */
    public Ball(Point2D ballCoordinates,int ballXLength,int ballYLength,Color innerColor,Color borderColor){
        this.ballCoordinates = ballCoordinates;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        ballShape = makeBall(ballCoordinates,ballXLength,ballYLength);
        this.borderColor = borderColor;
        this.innerColor  = innerColor;
    }

    /**
     * @param ballCoordinates
     * @param ballXLength
     * @param ballYLength
     * @return
     * create abstract class
     */
    protected abstract Shape makeBall(Point2D ballCoordinates,int ballXLength,int ballYLength);

    /**
     *move the ball in the direction of its Velocity
     */
    public void ballMovement(){
        RectangularShape tmp = (RectangularShape) ballShape;
        ballCoordinates.setLocation((ballCoordinates.getX() + velocityX),(ballCoordinates.getY() + velocityY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((ballCoordinates.getX() -(w / 2)),(ballCoordinates.getY() - (h / 2)),w,h);
        checkBallMovement(w,h);
    }

    /**
     * @param width
     * @param height
     * tells where the ball will move next frame
     */
    private void checkBallMovement(double width,double height){
        up.setLocation(ballCoordinates.getX(),ballCoordinates.getY()-(height / 2));
        down.setLocation(ballCoordinates.getX(),ballCoordinates.getY()+(height / 2));
        left.setLocation(ballCoordinates.getX()-(width / 2),ballCoordinates.getY());
        right.setLocation(ballCoordinates.getX()+(width / 2),ballCoordinates.getY());
    }

    /**
     * @param p
     * moves the ball to a specified coordinate in game
     */
    public void moveToPoint(Point p){
        ballCoordinates.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballShape;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((ballCoordinates.getX() -(w / 2)),(ballCoordinates.getY() - (h / 2)),w,h);
    }

    /**
     * @param s
     * sets ball's x Velocity
     */
    public void setBallXVelocity(int s){velocityX = s;}

    /**
     * @param s
     * sets ball's y Velocity
     */
    public void setBallYVelocity(int s){velocityY = s;}

    /**
     * @return
     * get x velocity of ball
     */
    public int getVelocityX(){
        return velocityX;
    }

    /**
     * @return
     * get y Velocity of ball
     */
    public int getVelocityY(){
        return velocityY;
    }

    /**
     * reverse the direction of x Velocity
     */
    public void reverseX(){
        velocityX *= -1;
    }

    /**
     * reverse the direcction of y Velocity
     */
    public void reverseY(){
        velocityY *= -1;
    }

    /**
     * @return
     * get color of ball's border
     */
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * @return
     * get color of ball
     */
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * @return
     * get position of ball
     */
    public Point2D getPosition(){
        return ballCoordinates;
    }

    /**
     * @return
     * get shape of ball
     */
    public Shape getBallShape(){
        return ballShape;
    }
}
