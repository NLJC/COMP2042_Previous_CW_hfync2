import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {
    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickShape;

    /**
     * @param point
     * @param shape
     * create initial brick "Cement Brick" with same brick shape
     */
    public CementBrick(Point point, Dimension shape){
        super(NAME,point,shape,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickShape = super.brickShape;
    }

    /**
     * @param pos
     * @param shape
     * @return
     * returns brick shape and position
     */
    @Override
    protected Shape makeBrickShape(Point pos, Dimension shape) {return new Rectangle(pos,shape);}

    /**
     * @param point
     * @param dir
     * @return
     * checks if brick is broken, then runs it through impact class if false, then makes crack if brick not broken
     */
    @Override
    public boolean checkImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    /**
     * @return
     * get brick shape
     */
    @Override
    public Shape getBrick() {
        return brickShape;
    }

    /**
     *update brick with cracked brick shape
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickShape,false);
            brickShape = gp;
        }
    }

    /**
     *resets brick to its original shape
     */
    public void repairBrick(){
        super.repairBrick();
        crack.reset();
        brickShape = super.brickShape;
    }
}
