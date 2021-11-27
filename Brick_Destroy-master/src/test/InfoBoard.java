package test;

import javax.swing.*;
import java.awt.*;

public class InfoBoard{
        JFrame infoboard = new JFrame();

        //create labels to write down instructions on info page
        JLabel info1 = new JLabel("# Brick_Destroy");
        JLabel info2 = new JLabel("This is a simple arcace video game.");
        JLabel info3 = new JLabel("Player's goal is to destroy a wall with a small ball.");
        JLabel info4 = new JLabel("The game has  very simple commmand:");
        JLabel info5 = new JLabel("SPACE start/pause the game");
        JLabel info6 = new JLabel("A move left the player");
        JLabel info7 = new JLabel("D move right the player");
        JLabel info8 = new JLabel("ESC enter/exit pause menu");
        JLabel info9 = new JLabel("F1 open console");
        JLabel info10 = new JLabel("the game automatically pause if the frame loses focus");

        JLabel info11 = new JLabel("Enjoy ;-)");

        InfoBoard(){
            info1.setBounds(0, 0, 800, 30);
            info1.setFont(new Font(null, Font.PLAIN, 20));
            info2.setBounds(0, 30, 800, 30);
            info2.setFont(new Font(null, Font.PLAIN, 20));
            info3.setBounds(0, 60, 800, 30);
            info3.setFont(new Font(null, Font.PLAIN, 20));
            info4.setBounds(0, 90, 800, 30);
            info4.setFont(new Font(null, Font.PLAIN, 20));
            info5.setBounds(0, 120, 800, 30);
            info5.setFont(new Font(null, Font.PLAIN, 20));
            info6.setBounds(0, 150, 800, 30);
            info6.setFont(new Font(null, Font.PLAIN, 20));
            info7.setBounds(0, 180, 800, 30);
            info7.setFont(new Font(null, Font.PLAIN, 20));
            info8.setBounds(0, 210, 800, 30);
            info8.setFont(new Font(null, Font.PLAIN, 20));
            info9.setBounds(0, 240, 800, 30);
            info9.setFont(new Font(null, Font.PLAIN, 20));
            info10.setBounds(0, 270, 800, 30);
            info10.setFont(new Font(null, Font.PLAIN, 20));
            info11.setBounds(0, 330, 800, 30);
            info11.setFont(new Font(null, Font.PLAIN, 20));


            infoboard.add(info1);
            infoboard.add(info2);
            infoboard.add(info3);
            infoboard.add(info4);
            infoboard.add(info5);
            infoboard.add(info6);
            infoboard.add(info7);
            infoboard.add(info8);
            infoboard.add(info9);
            infoboard.add(info10);
            infoboard.add(info11);

            infoboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            infoboard.setSize(600, 350);
            infoboard.setLayout(null);
            infoboard.setVisible(true);
        }
}