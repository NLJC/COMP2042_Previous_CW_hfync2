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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";


    private static final Color BG_COLOR = new Color(0,0,0, 0);
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = Color.WHITE;//new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = Color.WHITE.darker();
    private static final Color CLICKED_TEXT = Color.WHITE.darker();
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuBoard;
    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle infoButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean startClicked;
    private boolean exitClicked;
    private boolean infoClicked;


    /**
     * @param owner
     * @param area
     * creates rectangle that acts as the menuBoard
     * creates rectangles that will act as buttons
     */
    public HomeMenu(GameFrame owner, Dimension area){
        initializeHomeMenu();
        this.owner = owner;

        menuBoard = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        setFont();
    }


    /**
     * @param g
     * paint menu over menuBoard
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    /**
     * @param g2d
     * draws menu text and buttons
     */
    public void drawMenu(Graphics2D g2d){
        paintMenu(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuBoard.getX();
        double y = menuBoard.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * @param g2d
     * paint colors in the menu, as well as the background
     */
    private void paintMenu(Graphics2D g2d){
        Image backgroundimage = Toolkit.getDefaultToolkit().getImage("Brick wall background.jpg");
        g2d.drawImage(backgroundimage, 0, 0, this);

        Color prev = g2d.getColor();

        //reference: https://www.javatpoint.com/Displaying-image-in-swing
        g2d.setColor(BG_COLOR);
        g2d.fill(menuBoard);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuBoard);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuBoard);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * @param g2d
     * draws all text in their respective fonts and colors in menuBoard
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuBoard.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuBoard.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuBoard.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuBoard.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * @param g2d
     * draws all buttons in their respective shapes and positions in menuBoard
     */
    private void drawButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuBoard.width - startButton.width) / 2;
        int y =(int) ((menuBoard.height - startButton.height) * 0.8);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        exitButton.setLocation(x,y);




        x = (int)(exitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        if(exitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 0.8;

        infoButton.setLocation(x,y);




        x = (int)(infoButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (startButton.height * 0.9);

        if(infoClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }
    }

    /**
     * @param mouseEvent
     * starts game if startButton clicked
     * exits game if exitButton clicked
     * opens infoBoard if infoButton clicked
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(infoButton.contains(p)){
            owner.enableInfoBoard();
        }
    }


    /**
     * @param mouseEvent
     * changes the color of the buttons when pressed
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    /**
     * @param mouseEvent
     * changes the button color back to normal when mouse released
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(exitClicked){
            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if(infoClicked){
            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    /**
     * @param mouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     * changes the cursor to a hand when hovering over buttons
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * initialized homeMenu with specified settings
     */
    public void initializeHomeMenu(){
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * sets fonts for greetings, title, credits and buttons
     */
    public void setFont(){
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }
}
