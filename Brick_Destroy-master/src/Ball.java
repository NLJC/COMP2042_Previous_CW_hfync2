import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * @param center
     * @param radiusA
     * @param radiusB
     * @param inner
     * @param border
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * @param center
     * @param radiusA
     * @param radiusB
     * @return
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     *
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }

    /**
     * @param x
     * @param y
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * @param s
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * @param s
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     *
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     *
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * @return
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * @return
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * @return
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * @return
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * @param p
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * @param width
     * @param height
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * @return
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * @return
     */
    public int getSpeedY(){
        return speedY;
    }


}
