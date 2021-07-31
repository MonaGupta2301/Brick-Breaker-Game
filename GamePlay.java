package DemoGame;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GamePlay extends JPanel implements ActionListener,KeyListener
{
    // private static final Graphics2D Graphi) = null;
	private boolean play=false;
     private int totalBrikes=21;
     private Timer timer;
     private int score=0;
     private int delay=5;
     private int ballPosX=120;
     private int ballPosY=350;
     private int ballXDir=-1;
     private int ballYDir=-2;
     private int Playerx=350;
     private MapGenerator map;
     
     public GamePlay()
     {
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(true);
    	
    	timer = new Timer(delay,this);
    	timer.start();
    	map = new MapGenerator(3,7);
     }
     
     public void paint(Graphics g)
     {
    	 //We will Make the BackGround Read
    	 g.setColor(Color.DARK_GRAY);
    	 g.fillRect(1,1,692,592);
    	 
    	 //uper boarder
    	 g.setColor(Color.white);
    	 g.fillRect(0,0,692,3);//top
    	 //g.fillRect(0,3,3,592);//write
    	 //g.fillRect(691,3,3,0);//left(left,,width,height)
    	 
    	 //padle
    	 g.setColor(Color.pink);
   	     g.fillRect(Playerx, 550,100,8);
   	     
   	     //bricks
   	     map.draw((Graphics2D) g);
   	     
   	     //ball
   	     g.setColor(Color.white);
   	     g.fillOval(ballPosX, ballPosY, 20,20);
     
        //Score
   	     g.setColor(Color.cyan);
         g.setFont(new Font("Times New Roman",Font.BOLD,20));
   	     g.drawString("High Score :"+score, 550, 30 );
   	     
        //Game Over
   	     if(ballPosY>=670)
   	     {
   	    	 play=false;
   	    	 ballXDir=0;
   	    	 ballYDir=0;
   	    	 
   	    	 g.setColor(Color.red);
             g.setFont(new Font("Times New Roman",Font.BOLD,25));
      	     g.drawString("* * * Game Over * * *\n", 250, 300 );
      	     
             g.setColor(Color.cyan);
             g.setFont(new Font("Times New Roman",Font.BOLD,25));
      	     g.drawString("  Your Score :\n"+score, 250, 330 );
      	     
      	     g.setColor(Color.cyan);
             g.setFont(new Font("Times New Roman",Font.BOLD,20));
      	     g.drawString(" Press Enter To Continue ", 230, 360 );
   	     }
   	     
   	     //player won
   	     if(totalBrikes<=0)
   	     {
   	    	 play=false;
  	    	 ballXDir=0;
  	    	 ballYDir=0;
  	    	 
  	    	 g.setColor(Color.red);
             g.setFont(new Font("Times New Roman",Font.BOLD,25));
     	     g.drawString(" YOU WON...\n", 250, 300 );
     	     

             g.setColor(Color.cyan);
             g.setFont(new Font("Times New Roman",Font.BOLD,25));
      	     g.drawString("  HIGHEST SCORE :\n"+score, 250, 330 );
      	     
   	     }
     }
      private void moveleft()
      {
    	  Playerx=Playerx-20;
      }

      private void moveright()
      {
    	  Playerx=Playerx+20;
      }

	@Override
	public void keyPressed(KeyEvent e)
	{
		//paddle movement 
		
	   	if(e.getKeyCode()==KeyEvent.VK_LEFT)
	   	{
	   		play=true;
	   		if(Playerx<=0)
	   		{
	   		    Playerx=0;	
	   		}
	   		else
	   		{	
	    	   moveleft();
	   		}
	   	}
	   	else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	   	{
	   		play=true;
	   		if(Playerx>=600)
	   		{
	   		    Playerx=600;	
	   		}
	   		else
	   		{
	   	      moveright();
	   		}
	   	}
	   	if(e.getKeyCode()==KeyEvent.VK_ENTER)
	   	{
        if(!play)
        {
	       score=0;
	       totalBrikes=21;
	       ballPosX=120;
	       ballPosY=350;
	       ballXDir=-1;
	       ballYDir=-2;
	       Playerx=320;
	      
	       map=new MapGenerator(3,7);
        }
	   	}
		repaint();
	}

	@Override
    public void actionPerformed(ActionEvent arg0)
	{
		//ball movement
		if(play)
		{
			if(ballPosX<=0)
			{
				ballXDir=-ballXDir;	
			}
			if(ballPosX>=670)
			{
				//ballXDir=+ballXDir;
				ballXDir=-ballXDir;
			}
			if(ballPosY<=0)
			{
				ballYDir=-ballYDir;	
			}

			Rectangle Ballrect=new Rectangle(ballPosX, ballPosY, 20,20);
			Rectangle paddlerect=new Rectangle(Playerx, 550,100,8);
			
			if(Ballrect.intersects(paddlerect))
			{
				ballYDir=-ballYDir;
			}
		A: for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
                    {
                    	int width=map.brickWidth;
                    	int height=map.brickHeight;
			            Rectangle BrickRect = new Rectangle(j*width+80,i*height+50, width, height);
			            
			            if(Ballrect.intersects(BrickRect))
			            {
			            	map.setBrick(0,i,j);
			            	totalBrikes--;
			            	score=score+5;
			            	
			            	if((ballPosX+19<=(j*width+80))||(ballPosX+1>=((j*width+80)+width)))
			            	{
			            		ballXDir=-ballXDir;
			            	}
			            	else
			            	{
			            		ballYDir=-ballYDir;
			            	}
			            	break A;
			            }
                    }
				}
			}
			
			ballPosX=ballPosX+ballXDir;
			ballPosY=ballPosY+ballYDir;
		}
		
		
		
		 repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

     

     
     
}

