import java.awt.*;
import java.awt.Point;


/**
 * Created by filippo on 04/09/16.
 * Refactored by Nicholas Chandra
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * @param point
     * @param size
     * create initial brick "Clay Brick" with same brick shape
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * @param pos
     * @param size
     * @return
     * returns brick shape and position
     */
    @Override
    protected Shape makeBrickShape(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * @return
     * returns Brick class's brick shape
     */
    @Override
    public Shape getBrick() {
        return super.brickShape;
    }


}
