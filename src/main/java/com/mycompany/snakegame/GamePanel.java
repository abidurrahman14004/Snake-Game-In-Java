
package com.mycompany.snakegame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;


public  class GamePanel extends JPanel implements ActionListener{
  
    static final int SCREEN_WIDTH=900;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE= 10;
    static final int GAME_UNITS= (SCREEN_WIDTH* SCREEN_HEIGHT)/UNIT_SIZE;
 
    
    final int x[]=new int[GAME_UNITS];
    final int y[]= new int [GAME_UNITS];
    
    int bodyparts=6;
    int appleseaten;
    int appleseten;
    int applex;
    int appley;
    char diraction='R';
    boolean running =false;
    Timer timer;
    Random random;
    
    
    
    GamePanel (){
       random= new Random();
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.black);
       this.setFocusable(true);
       this.addKeyListener(new Mykeyadapter());
       startgame();
       
    
    
    } 
    public void backgroundmusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file= new File("NeverPlay.wav");
        AudioInputStream input= AudioSystem.getAudioInputStream(file);
        Clip clip= AudioSystem.getClip();
        clip.open(input);
        clip.start();
    }
    public void startgame(){
        newapple();
        running= true;
        timer= new Timer(80,this);
        timer.start();
       
    }
    public void paintComponent(Graphics g){
       super.paintComponent(g);
       draw(g);
    }
     
    public void draw(Graphics g){
        if(running){
        for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT); 
         g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);   
        }
        g.setColor(Color.pink);
    
        g.fillOval(applex,appley,UNIT_SIZE,UNIT_SIZE);
    
    for(int i=0;i<bodyparts;i++){
        if (i==0){
            g.setColor(Color.yellow);
            g.fillRect(x[i], y[i],UNIT_SIZE, UNIT_SIZE);
        }
        else{
            g.setColor(new Color(45,180,0));
            g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
        }
    }
        }else {
            gameOver(g);
        }
    }
    
    

    public void newapple(){
        
    applex= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    appley= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; 
    
    
    }
    public void move() {
    for (int i = bodyparts; i > 0; i--) {
        x[i] = x[i-1];
        y[i] = y[i-1];
    }
    switch (diraction) {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
    }
}


    public void checkapple(){
        if((x[0] == applex) && (y[0] == appley)) {
			bodyparts++;
			appleseaten++;
			newapple();
		}
    }
    public void checkcollission(){
        for(int i=bodyparts; i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running =false;
            }
        }
        //check head if it touches left border
        if(x[0]<0){
            running= false;
        }
          //check head if it touches right border
        if(x[0]>SCREEN_WIDTH){
            running= false;
        }
        
          //check head if it touches upborder
        if(y[0]<0){
            running= false;
        }
        
          //check head if it touches down border
        if(y[0]>SCREEN_HEIGHT){
            running= false;
        }
        if(!running){
            timer.stop();
        }
        
        
        
    }
  public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Comic Sans MS",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+appleseaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+appleseaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Comic Sans MS",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("You are a looser!!!", (SCREEN_WIDTH - metrics2.stringWidth("You are a looser!!"))/2, SCREEN_HEIGHT/2);
	}
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(running){
            move();
            checkapple();
            checkcollission();
            
        }
        repaint();
    }
    
    public class Mykeyadapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(diraction!='R'){
                        diraction ='L';
                    }
                    break;
                    
                     case KeyEvent.VK_RIGHT:
                    if(diraction!='L'){
                        diraction ='R';
                    }
                    break;
                     case KeyEvent.VK_UP:
                    if(diraction!='D'){
                        diraction ='U';
                    }
                    break;
                     case KeyEvent.VK_DOWN:
                    if(diraction!='U'){
                        diraction ='D';
                    }
                    break;
                    
                
            }
        }
    }
}
