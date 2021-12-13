/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;

public class GameBoard {

    private static final int LEVELS_COUNT = 15;

    public int currentscore=0;
    public int highscore;
    public String higherscore;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int TOUGH = 4;
    private static final int HELL = 5;

    private Random rnd;
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    /**
     * @param drawArea
     * @param brickCount
     * @param lineCount
     * @param brickDimensionRatio
     * @param ballPos
     * create ball and player in their initial positions, and set random ball velocity
     */
    public GameBoard(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){
        this.startPoint = new Point(ballPos);
        rnd = new Random();

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        //create ball in starting position
        makeBall(ballPos);

        //set ball velocity to random velocity when ball has no velocity
        int velocityX=0;
        int velocityY=0;
        do{
            velocityX = rnd.nextInt(6) - 3;
        }while(velocityX == 0);
        do{
            velocityY = -rnd.nextInt(4);
        }while(velocityY == 0);

        ball.setBallXVelocity(velocityX);
        ball.setBallYVelocity(velocityY);

        //clone ball position and put player there
        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
    }

    /**
     * @param drawArea
     * @param brickCount
     * @param lineCount
     * @param brickSizeRatio
     * @param type
     * @return
     * generates the level's bricks and places them down when there is only 1 type of brick
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCount, int lineCount, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCount -= brickCount % lineCount;

        int brickOnLine = brickCount / lineCount;

        double brickLength = drawArea.getWidth() / brickOnLine;
        double brickHeight = brickLength / brickSizeRatio;

        brickCount += lineCount / 2;

        Brick[] tmp  = new Brick[brickCount];

        Dimension brickSize = new Dimension((int) brickLength,(int) brickHeight);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCount)
                break;
            double x = (i % brickOnLine) * brickLength;
            x =(line % 2 == 0) ? x : (x - (brickLength / 2));
            double y = (line) * brickHeight;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHeight;i < tmp.length;i++, y += 2*brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;
    }

    /**
     * @param drawArea
     * @param brickCount
     * @param lineCount
     * @param brickSizeRatio
     * @param typeA
     * @param typeB
     * @return
     * generates the level's bricks and places them down when there are 2 types of bricks
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCount, int lineCount, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCount -= brickCount % lineCount;

        int brickOnLine = brickCount / lineCount;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLength = drawArea.getWidth() / brickOnLine;
        double brickHeight = brickLength / brickSizeRatio;

        brickCount += lineCount / 2;

        Brick[] tmp  = new Brick[brickCount];

        Dimension brickSize = new Dimension((int) brickLength,(int) brickHeight);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCount)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLength;
            x =(line % 2 == 0) ? x : (x - (brickLength / 2));
            double y = (line) * brickHeight;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHeight;i < tmp.length;i++, y += 2*brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * @param ballPos
     * creates the ball at starting position
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * @param drawArea
     * @param brickCount
     * @param lineCount
     * @param brickDimensionRatio
     * @return
     * generates the levels, also decides what kind of bricks to use each level
     */
    private Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,CEMENT);
        tmp[5] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[6] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,TOUGH);
        tmp[7] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,TOUGH);
        tmp[8] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,TOUGH);
        tmp[9] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,TOUGH,TOUGH);
        tmp[10] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,HELL);
        tmp[11] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,HELL);
        tmp[12] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,HELL);
        tmp[13] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,TOUGH,HELL);
        tmp[14] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,HELL,HELL);
        return tmp;
    }

    /**
     * calls the methods that move the player and move the ball
     */
    public void move(){
        player.movePlayer();
        ball.ballMovement();
    }

    /**
     * if paddle and ball are about to collide, reverse the y velocity of ball
     * if ball is about to collide with a brick, reverse either its x or y velocity depending on where it collides
     */
    public void findImpacts(){
        if(player.checkImpact(ball)){
            ball.reverseY();
        }
        else if(impactBrickWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            currentscore++;
            if (currentscore > highscore){
                highscore = currentscore;
                higherscore = String.valueOf(highscore);
                try {
                    FileWriter writeHighscore = new FileWriter("highscorelist.txt");
                    writeHighscore.write(higherscore);
                    writeHighscore.close();
                }catch (Exception e){

                }
            }
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * @return
     * changes the ball's direction if it collides with a brick, change depends on where it collides with
     */
    private boolean impactBrickWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.checkImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.checkImpact(ball.up, Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.checkImpact(ball.right, Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.checkImpact(ball.left, Brick.Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * @return
     * changes ball's direction when it reaches the border of gameBoard
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * @return
     * returns the number of remaining bricks
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * @return
     * return the number of remaining balls
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * @return
     * return true if ball is lost, false if it is not lost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * resets the ball amd player to their starting positions, and sets random ball velocity
     */
    public void ballReset(){
        player.moveToPoint(startPoint);
        ball.moveToPoint(startPoint);
        int velocityX,velocityY;
        do{
            velocityX = rnd.nextInt(5) - 2;
        }while(velocityX == 0);
        do{
            velocityY = -rnd.nextInt(3);
        }while(velocityY == 0);

        ball.setBallXVelocity(velocityX);
        ball.setBallYVelocity(velocityY);
        ballLost = false;
    }

    /**
     * repairs all bricks, then resets the brick count and ball count
     */
    public void brickWallReset(){
        for(Brick b : bricks)
            b.repairBrick();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * @return
     * returns true if no more balls remaining, else returns false
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * @return
     * returns true if no more bricks remaining, else returns false
     */
    public boolean allBricksDestroyed(){
        return brickCount == 0;
    }

    /**
     * changes the bricks to the next level's bricks, then resets brickCount
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * @return
     * returns true if there are still levels remaining, else returns false
     */
    public boolean hasLevel(){
        return level < levels.length;
    }

    /**
     * resets the number of balls remaining to maximum
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * @param point
     * @param size
     * @param type
     * @return
     * returns the corresponding brick
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case TOUGH:
                out = new ToughBrick(point, size);
                break;
            case HELL:
                out = new HellBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

    /**
     * gets high score from highsccorelist.txt
     */
    public void getHighscore(){
        File score = new File("highscorelist.txt");

        try{
            Scanner scoreScan = new Scanner(score);
            while (scoreScan.hasNextLine()){
                higherscore = scoreScan.nextLine();
                highscore = Integer.parseInt(higherscore);
            }
            scoreScan.close();
        }catch(Exception e){ }
    }
}
