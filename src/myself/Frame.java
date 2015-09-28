package myself;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame()
	
	  {
		int cols=20;
		int rows=15;
		int width=cols*Map.blocksize;
		int height=rows*Map.blocksize;
		JPanel Main= new Graphic();
		//add(new Main());
	    Main.setBounds(20,20, width, height);
	    add(Main);
		setTitle("Pacman---(@PHC)");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(width+200, height+100);
	    setLocationRelativeTo(null);
	    setVisible(true);
	  }
	 public static void main(String[] args) {
	      new Frame();
	  }
}
