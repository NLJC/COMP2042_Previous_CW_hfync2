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

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;



public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;


    private JButton skipLevel;
    private JButton resetBalls;

    private JSlider ballXVelocity;
    private JSlider ballYVelocity;

    private GameBoard gameBoard;
    private Ball ball;

    /**
     * @param gameBoard
     * creates debugPanel
     */
    public DebugPanel(GameBoard gameBoard, Ball ball){
        this.gameBoard = gameBoard;
        this.ball = ball;

        initialize();

        skipLevel = makeButton("Skip Level",e -> gameBoard.nextLevel());
        resetBalls = makeButton("Reset Balls",e -> gameBoard.resetBallCount());

        ballXVelocity = makeSlider(-4,4,e -> ball.setBallXVelocity(ballXVelocity.getValue()));
        ballYVelocity = makeSlider(-4,4,e -> ball.setBallYVelocity(ballYVelocity.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXVelocity);
        this.add(ballYVelocity);
    }

    /**
     * sets the background and layout of DebugPanel
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * @param title
     * @param e
     * @return
     * creates button at specified p
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * @param min
     * @param max
     * @param e
     * @return
     * creates slider at specified point
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * @param x
     * @param y
     * used to set ball x and y values using sliders
     */
    public void setValues(int x,int y){
        ballXVelocity.setValue(x);
        ballYVelocity.setValue(y);
    }
}
