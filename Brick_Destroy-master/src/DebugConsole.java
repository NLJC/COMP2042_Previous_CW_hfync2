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
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";


    private JFrame owner;
    private DebugPanel debugPanel;
    private GamePanel gamePanel;
    private GameBoard gameBoard;


    /**
     * @param owner
     * @param gameBoard
     * @param gamePanel
     * creates debugConsole
     */
    public DebugConsole(JFrame owner, GameBoard gameBoard, GamePanel gamePanel){

        this.gameBoard = gameBoard;
        this.owner = owner;
        this.gamePanel = gamePanel;
        initialize();

        debugPanel = new DebugPanel(gameBoard);
        this.add(debugPanel,BorderLayout.CENTER);


        this.pack();
    }

    /**
     * initializes debugConsole with specified settings
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }


    /**
     * set the location of the debug console to the middle of the gamePanel
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }


    /**
     * @param windowEvent
     */
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gamePanel.repaint();
    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     * places debugPanel in the middle of window, then gets number of balls and ball x and y speed
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = gameBoard.ball;
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
