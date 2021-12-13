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


public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerShape;
    private Point playerlocation;
    private int moveAmount;
    private int min;
    private int max;


    /**
     * @param playerlocation
     * @param width
     * @param height
     * @param container
     * create player's paddle
     */
    public Player(Point playerlocation,int width,int height,Rectangle container) {
        this.playerlocation = playerlocation;
        moveAmount = 0;
        playerShape = makePaddle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * @param width
     * @param height
     * @return
     * draw player's paddle
     */
    private Rectangle makePaddle(int width,int height){
        Point p = new Point((int)(playerlocation.getX() - (width / 2)),(int)playerlocation.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * @param b
     * @return
     * check if player paddle will impact ball, and return true if so
     */
    public boolean checkImpact(Ball b){
        return playerShape.contains(b.getPosition()) && playerShape.contains(b.down);
    }

    /**
     * moves player in the direction of moveAmount
     * stops player when player is on border
     */
    public void movePlayer(){
        double x = playerlocation.getX() + moveAmount;
        if(x < min || x > max)
            return;
        playerlocation.setLocation(x,playerlocation.getY());
        playerShape.setLocation(playerlocation.x - (int)playerShape.getWidth()/2,playerlocation.y);
    }

    /**
     * changes moveAmount to -5
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * changes moveAmount to 5
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * changes moveAmount to 0
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * @return
     * return player shape
     */
    public Shape getPlayerShape(){
        return  playerShape;
    }

    /**
     * @param p
     * move player to a specified position
     */
    public void moveToPoint(Point p){
        playerlocation.setLocation(p);
        playerShape.setLocation(playerlocation.x - (int)playerShape.getWidth()/2,playerlocation.y);
    }
}
