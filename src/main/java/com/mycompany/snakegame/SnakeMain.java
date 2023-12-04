
package com.mycompany.snakegame;

import javax.swing.JFrame;
public class SnakeMain extends JFrame{
    
    SnakeMain () {
        this.add(new GamePanel());
        this.setTitle("S N A K E");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

 
                

        }
    
               
    
}
