import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class ToughBrick extends Brick {
    private static final String NAME = "Tough Brick";
    private static final Color DEF_INNER = Color.yellow.darker();
    private static final Color DEF_BORDER = Color.black.brighter();
    private static final int TOUGH_STRENGTH = 2;
    private static final double TOUGH_PROBABILITY = 0.5;

    private Random rnd;
    private Crack crack;
    private Shape brickShape;

    public ToughBrick(Point point, Dimension shape){
        super(NAME,point,shape,DEF_BORDER,DEF_INNER,TOUGH_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickShape = super.brickShape;
        rnd = new Random();
    }

    @Override
    protected Shape makeBrickShape(Point pos, Dimension shape){return new Rectangle(pos,shape);}

    @Override
    public boolean checkImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    public void impact(){
        if(rnd.nextDouble() < TOUGH_PROBABILITY){
            super.impact();
        }
    }

    @Override
    public Shape getBrick() {
        return brickShape;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickShape,false);
            brickShape = gp;
        }
    }

    public void repairBrick(){
        super.repairBrick();
        crack.reset();
        brickShape = super.brickShape;
    }
}
