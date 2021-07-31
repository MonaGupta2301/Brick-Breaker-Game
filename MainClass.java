package DemoGame;
import javax.swing.*;

public class MainClass {

	public static void main(String[] args)
	{
	    JFrame f=new JFrame();
	    f.setTitle("Brick Breaker");
        f.setSize(700,600);     //setSize(Width,Height);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        
        GamePlay gm=new GamePlay();
        f.add(gm);
	}

}
