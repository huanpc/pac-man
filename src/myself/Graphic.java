package myself;

import sun.audio.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import myself.Pacman;

public class Graphic extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public Timer timer;
	Dimension d;
	Image img;
	Image pacman1, pacman2up, pacman2down, pacman2right, pacman2left;
	Image pacman3up, pacman3down, pacman3right, pacman3left;
	Image pacman4up, pacman4down, pacman4right, pacman4left;
	Image ghost1, ghost2, ghost3, ghost4;
	Image fruit;
	static int currentspeed;
	static short[] currentMap;
	static int level;
	Font smallfont = new Font("Cooper Black", Font.BOLD, 18);
	FontMetrics fmsmall, fmlarge;
	static int ghostx, ghosty;
	DataInputStream fin = null;
	DataOutputStream fout = null;
	static int higScore;
	static int timeout=0;
	

	// ------------------------------------------------------------------------
	public Graphic() {
		getImage();
		addKeyListener(new TAdapter());
		timer = new Timer(40, this);
		timer.start();
		d = new Dimension(Map.cols * Map.blocksize, Map.rows * Map.blocksize);
		currentMap = new short[Map.cols * Map.rows];
		setFocusable(true);
		level = 0;
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		new Ghost();
		new Pacman();
		new Map();
	}

	public void playGame(Graphics2D g2d) {
		
		if (Pacman.dying)
			Pacman.isDeath();
		else{
			Pacman.move();
			drawPacman(g2d);
			Ghost.move();
			drawGhosts(g2d);
			checkMaze();
		}
	}

	// ------------------------------------------------------------------------
	// Ve Pacman
	public void drawPacman(Graphics2D g2d) {
		if (Pacman.viewdx == -1)
			drawPacmanLeft(g2d);
		else if (Pacman.viewdx == 1)
			drawPacmanRight(g2d);
		else if (Pacman.viewdy == -1)
			drawPacmanUp(g2d);
		else
			drawPacmanDown(g2d);
	}

	public void drawPacmanUp(Graphics2D g2d) {
		switch (Pacman.state) {
		case 1:
			g2d.drawImage(pacman2up, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 2:
			g2d.drawImage(pacman3up, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 3:
			g2d.drawImage(pacman4up, Pacman.x + 1, Pacman.y + 1, this);
			break;
		default:
			g2d.drawImage(pacman1, Pacman.x + 1, Pacman.y + 1, this);
			break;
		}
	}

	public void drawPacmanDown(Graphics2D g2d) {
		switch (Pacman.state) {
		case 1:
			g2d.drawImage(pacman2down, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 2:
			g2d.drawImage(pacman3down, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 3:
			g2d.drawImage(pacman4down, Pacman.x + 1, Pacman.y + 1, this);
			break;
		default:
			g2d.drawImage(pacman1, Pacman.x + 1, Pacman.y + 1, this);
			break;
		}
	}

	public void drawPacmanLeft(Graphics2D g2d) {
		switch (Pacman.state) {
		case 1:
			g2d.drawImage(pacman2left, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 2:
			g2d.drawImage(pacman3left, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 3:
			g2d.drawImage(pacman4left, Pacman.x + 1, Pacman.y + 1, this);
			break;
		default:
			g2d.drawImage(pacman1, Pacman.x + 1, Pacman.y + 1, this);
			break;
		}
	}

	public void drawPacmanRight(Graphics2D g2d) {
		switch (Pacman.state) {
		case 1:
			g2d.drawImage(pacman2right, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 2:
			g2d.drawImage(pacman3right, Pacman.x + 1, Pacman.y + 1, this);
			break;
		case 3:
			g2d.drawImage(pacman4right, Pacman.x + 1, Pacman.y + 1, this);
			break;
		default:
			g2d.drawImage(pacman1, Pacman.x + 1, Pacman.y + 1, this);
			break;
		}
	}

	// Ve ban do
	public void drawMaze(Graphics2D g2d) {
		short i = 0;
		int x, y;
		int blocksize = Map.blocksize;
		for (y = 0; y < Map.rows * blocksize; y += blocksize) {
			for (x = 0; x < Map.cols * blocksize; x += blocksize) {
				g2d.setColor(Map.mazecolor);
				g2d.setStroke(new BasicStroke(4));
				if ((currentMap[i] & 1) != 0) // ve bien trai quy uoc 1
				{
					g2d.drawLine(x, y, x, y + Map.blocksize - 1);

				}
				if ((currentMap[i] & 2) != 0) // ve bien tren quy uoc 2
				{
					g2d.drawLine(x, y, x + Map.blocksize - 1, y);

				}
				if ((currentMap[i] & 4) != 0) // ve bien phai quy uoc 4
				{
					g2d.drawLine(x + Map.blocksize - 1, y, x + Map.blocksize
							- 1, y + Map.blocksize - 1);

				}
				if ((currentMap[i] & 8) != 0) // ve bien duoi
				{
					g2d.drawLine(x, y + Map.blocksize - 1, x + Map.blocksize
							- 1, y + Map.blocksize - 1);

				}

				if ((currentMap[i] & 16) != 0) // ve diem an duoc
				{
					g2d.setColor(Color.YELLOW);

					g2d.fillOval(x + 11, y + 11, 5, 5);

				}
				
				if ((currentMap[i] & 32) != 0) // ve diem an duoc
				{	if(timeout!=500)
						g2d.drawImage(fruit, x + 3, y + 3, this);
					else
						currentMap[i]=(short) ((currentMap[i]&31)+16);
				}
				
				i++;
			}
		}
	}

	// Ve diem
	public void drawScore(Graphics2D g2d) {
		int i;
		String s;
		Font Score = new Font("Cooper Black", Font.BOLD, 18);

		g2d.setFont(Score);
		//
		g2d.setColor(Color.RED);
		s = "HI-SCORE ";
		g2d.drawString(s, Map.width + 11, 30);
		s = "YOUR SCORE";
		g2d.drawString(s, Map.width + 11, 90);
		s = "Level " + Integer.toString(level + 1);
		g2d.drawString(s, Map.width + 11, 150);
		//
		g2d.setColor(Color.WHITE);
		String Hscore = Integer.toString(higScore);
		g2d.drawString(Hscore, Map.width + 15, 60);

		String YourScore = Integer.toString(Pacman.score);
		g2d.drawString(YourScore, Map.width + 15, 120);
		for (i = 0; i < Pacman.pacleft; i++)
			g2d.drawImage(pacman3left, i * 28 + Map.width + 12, 300, this);
	}

	// Intro screen
	public void showMenu(Graphics2D g2d) {
		if (Pacman.gameover) {
			showGameOver(g2d);
		} else if (Pacman.wingame) {
			showWinGame(g2d);
		} else {
			g2d.setColor(Color.GRAY);
			g2d.fillRect(10, Map.height / 2, Map.width, 50);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(10, Map.height / 2, Map.width, 50);
			String s = "Press S to start New Game.";
			FontMetrics metr = this.getFontMetrics(smallfont);

			g2d.setColor(Color.GREEN);
			g2d.setFont(smallfont);
			g2d.drawString(s, (Map.width - metr.stringWidth(s)) / 2 + 10,
					Map.height / 2 + 30);
		}

	}

	public void showGameOver(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		g2d.fillRect(10, Map.height / 2, Map.width, 50);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10, Map.height / 2, Map.width, 50);
		String s = "Game Over!!!--Press S to start New Game";
		FontMetrics metr = this.getFontMetrics(smallfont);

		g2d.setColor(Color.ORANGE);
		g2d.setFont(smallfont);
		g2d.drawString(s, (Map.width - metr.stringWidth(s)) / 2 + 10,
				Map.height / 2 + 30);

	}

	public void showWinGame(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		g2d.fillRect(10, Map.height / 2, Map.width, 50);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10, Map.height / 2, Map.width, 50);
		String s = "You are Winner :)	Press S to start New Game";
		FontMetrics metr = this.getFontMetrics(smallfont);

		g2d.setColor(Color.YELLOW);
		g2d.setFont(smallfont);
		g2d.drawString(s, (Map.width - metr.stringWidth(s)) / 2 + 10,
				Map.height / 2 + 30);

	}

	// Check map
	public void checkMaze() {
		short i = 0;
		boolean finish = true;

		while (i < Map.cols * Map.rows && finish) {
			if (currentMap[i] > 15)
				finish = false;
			i++;
		}
		if (finish)
			nextLevel();

	}

	// Ve nhieu Ghost
	public void drawGhosts(Graphics2D g2d) {
		short i;
		for (i = 0; i < Ghost.nofghosts; i++)
			drawGhost(g2d, Ghost.ghostx[i] + 1, Ghost.ghosty[i] + 1, i);
	}

	// Ve 1 ghost
	public void drawGhost(Graphics2D g2d, int x, int y, int i) {
		switch (i) {
		case 0:
			g2d.drawImage(ghost1, x, y, this);
			break;
		case 1:
			g2d.drawImage(ghost2, x, y, this);
			break;
		case 2:
			g2d.drawImage(ghost3, x, y, this);
			break;
		case 3:
			g2d.drawImage(ghost4, x, y, this);
			break;
		default:
			g2d.drawImage(ghost2, x, y, this);
		}
	}

	public void initGame() {
		Pacman.pacleft = Pacman.life;
		Pacman.score = 0;
		currentspeed = 3;
		Ghost.nofghosts = 4;
		level=0;
		initLevel();
	}

	public void nextLevel() {
		if (level < 1) {
			Pacman.pacleft = Pacman.life;
			// Pacman.score=0;
			currentspeed += 2;	
			level++;
			Pacman.speed++;
			initLevel();
		} else {
			Pacman.ingame = false;
			Pacman.wingame = true;
		}
	}

	public void initLevel() {
		int i;
		// Create Map
		for (i = 0; i < Map.cols * Map.rows; i++)
			currentMap[i] = Map.data[level][i];
		continueLevel();
	}

	public static void continueLevel() {
		int i;
		int[] dirx = { -1, 1, 0, 0 };
		int[] diry = { 0, 0, -1, 1 };
		int k = 0;
		// Init Ghosts
		for (i = 0; i < Ghost.nofghosts; i++) {
			Ghost.ghostx[i] = 9 * Map.blocksize;
			Ghost.ghosty[i] = 8 * Map.blocksize;
			Ghost.ghostdx[i] = dirx[k];
			Ghost.ghostdy[i] = diry[k];
			k++;
			Ghost.speed[i] = currentspeed;
		}
		// Init Pacman
		Pacman.x = 7 * Map.blocksize; // vi tri pacman ban dau
		Pacman.y = 10 * Map.blocksize;
		Pacman.dx = 0;
		Pacman.dy = 0;
		Pacman.pressX = 0;
		Pacman.pressY = 0;
		Pacman.viewdx = -1;
		Pacman.viewdy = 0;
		Pacman.dying = false;
	}

	// -----------------------------------------------------------------------------

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (Pacman.ingame) {
				if (key == KeyEvent.VK_LEFT) {
					Pacman.pressX = -1;
					Pacman.pressY = 0;
				} else if (key == KeyEvent.VK_RIGHT) {
					Pacman.pressX = 1;
					Pacman.pressY = 0;
				} else if (key == KeyEvent.VK_UP) {
					Pacman.pressX = 0;
					Pacman.pressY = -1;
				} else if (key == KeyEvent.VK_DOWN) {
					Pacman.pressX = 0;
					Pacman.pressY = 1;
				} else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
					Pacman.ingame = false;
				} else if (key == KeyEvent.VK_PAUSE) {
					if (timer.isRunning())
						timer.stop();
					else
						timer.start();
				}
			} else

			if (key == 's' || key == 'S') {
				Pacman.ingame = true;
				initGame();
			}
		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP
					|| key == Event.DOWN) {
				Pacman.pressX = 0;
				Pacman.pressY = 0;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	// -----------------------------------------------------------
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);
		timeout++;
		drawMaze(g2d);
		drawScore(g2d);
		Pacman.changeState();
		if (Pacman.ingame)
			playGame(g2d);
		else {
			showMenu(g2d);
			try {
				loadData();
			} catch (IOException e) {
				higScore = 0;
			}
			if (Pacman.score > higScore)
				try {
					higScore = Pacman.score;
					writeData();
				} catch (IOException e) {
					// not doing anything
				}
		}
		g.drawImage(img, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void getImage() {
		pacman1 = new ImageIcon("pacicon/pacman.png").getImage();
		pacman2up = new ImageIcon("pacicon/up1.png").getImage();
		pacman3up = new ImageIcon("pacicon/up2.png").getImage();
		pacman4up = new ImageIcon("pacicon/up3.png").getImage();
		pacman2down = new ImageIcon("pacicon/down1.png").getImage();
		pacman3down = new ImageIcon("pacicon/down2.png").getImage();
		pacman4down = new ImageIcon("pacicon/down3.png").getImage();
		pacman2left = new ImageIcon("pacicon/left1.png").getImage();
		pacman3left = new ImageIcon("pacicon/left2.png").getImage();
		pacman4left = new ImageIcon("pacicon/left3.png").getImage();
		pacman2right = new ImageIcon("pacicon/right1.png").getImage();
		pacman3right = new ImageIcon("pacicon/right2.png").getImage();
		pacman4right = new ImageIcon("pacicon/right3.png").getImage();
		// ghost=new ImageIcon("pacicon/ghost1.png").getImage();
		ghost1 = new ImageIcon("pacicon/ghost1.png").getImage();
		ghost2 = new ImageIcon("pacicon/ghost2.png").getImage();
		ghost3 = new ImageIcon("pacicon/ghost3.png").getImage();
		ghost4 = new ImageIcon("pacicon/ghost4.png").getImage();
		fruit = new ImageIcon("pacicon/fruit.png").getImage();
	}

	public void loadData() throws IOException {
		try {
			fin = new DataInputStream(new FileInputStream("data.txt"));
		} catch (IOException exc) {
			higScore = 0;
		}
		try {
			if (fin.available() > 0)
				higScore = fin.readInt();
		} catch (IOException exc) {
			higScore = 0;
		}
		fin.close();
	}

	public void writeData() throws IOException {
		try {
			fout = new DataOutputStream(new FileOutputStream("data.txt"));
		} catch (IOException exc) {
			// none
		}
		try {
			fout.writeInt(higScore);
		} catch (IOException exc) {
			// none
		}
		fout.close();
	}
}
