import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Brick  {

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double BREAK_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;

        private GeneralPath crack;

        private int crackDepth;
        private int steps;


        /**
         * @param crackDepth
         * @param steps
         * initializes crack, and sets the values for crackDepth and steps
         */
        public Crack(int crackDepth, int steps){
            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;
        }


        /**
         * @return
         * returns the shape of the crack
         */
        public GeneralPath draw(){
            return crack;
        }

        /**
         * resets the crack so that the brick has no crack
         */
        public void reset(){
            crack.reset();
        }

        /**
         * @param point
         * @param impactDirection
         * determines start and end points of crack to be made
         */
        protected void makeCrack(Point2D point, int impactDirection){
            Rectangle bounds = Brick.this.brickShape.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();
            Point tmp;


            switch(impactDirection){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;
            }
        }

        /**
         * @param start
         * @param end
         * creates the crack based on start and end points
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();

            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int breakProbability  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += probabilityCheck(breakProbability,BREAK_PROBABILITY);

                path.lineTo(x,y);
            }
            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        /**
         * @param bound
         * @return
         * returns a random point within brick boundaries
         */
        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        /**
         * @param i
         * @param steps
         * @param divisions
         * @return
         * returns true if i is in middle of brick
         */
        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        /**
         * @param bound
         * @param probability
         * @return
         * returns a random value in boundaries if random number is higher than probability
         */
        private int probabilityCheck(int bound,double probability){

            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        /**
         * @param from
         * @param to
         * @param direction
         * @return
         * returns a random x and y position
         */
        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    Shape brickShape;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;


    /**
     * @param name
     * @param pos
     * @param size
     * @param border
     * @param inner
     * @param strength
     * creates an initial brick
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickShape = makeBrickShape(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * @param pos
     * @param size
     * @return
     * create abstract class
     */
    protected abstract Shape makeBrickShape(Point pos,Dimension size);

    /**
     * @param point
     * @param dir
     * @return
     * returns false if broken is true, and returns value of boolean broken after running it through impact()
     */
    public  boolean checkImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * @return
     * creates abstract class
     */
    public abstract Shape getBrick();


    /**
     * @return
     * returns brick border color
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * @return
     * returns brick color
     */
    public Color getInnerColor(){
        return inner;
    }


    /**
     * @param b
     * @return
     * returns a value based on where the ball will collide with the brick if brick not broken
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickShape.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickShape.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickShape.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickShape.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * @return
     * returns the value of boolean broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * changes brick status to unbroken, and sets brick strength to its full strength
     */
    public void repairBrick() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * reduces brick strength by 1, and changes status to broken if brick has no strength
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }




}





