package minergame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import baseframe.copy.baseframe;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class minerframe extends JFrame implements KeyListener, Runnable {
	BufferedImage screenbuffer = new BufferedImage(900, 900, BufferedImage.TYPE_4BYTE_ABGR);
	Graphics2D g = screenbuffer.createGraphics();
	BufferedImage[] b = new BufferedImage[10];
	int[][] room1 = { { 8, 8, 3, 8, 8 }, { 8, 3, 3, 3, 8 }, { 3, 3, 9, 3, 3 }, { 8, 3, 3, 3, 8 }, { 8, 8, 3, 8, 8 } };
	enemy[] enemies = new enemy[100];
	int enemiesloaded = 0;
	String update = "1.0.1";
	int[][] field = new int[100][100];
	double minerx = 50;
	double minery = 50;
	int points = 0;
	int fuel = 50;
	boolean playing = true;
	baseframe speaker = new baseframe(false, 0, 0);

	public minerframe() {
		
		try {
			b[0] = ImageIO.read(this.getClass().getClassLoader().getResource("images/image1.png"));
			b[1] = ImageIO.read(this.getClass().getClassLoader().getResource("images/image2.png"));
			b[2] = ImageIO.read(this.getClass().getClassLoader().getResource("images/image3.png"));
		} catch (IOException e) {
		}
		setTitle("hi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		speaker.playsound("audio/backgroundmusic.wav");
		addKeyListener(this);
		setSize(900, 900);
		setVisible(true);
		for (int x = 1; x < 100; x++) {
			for (int y = 1; y < 100; y++) {
				if (Math.random() < 0.01) {
					for (int coal = 1; coal < 10; coal++) {
						int coalx = x + (int) Math.round((Math.random() - 0.5) * (Math.random() * coal));
						int coaly = y + (int) Math.round((Math.random() - 0.5) * (Math.random() * coal));
						if ((coalx < 100 && coalx > 0) && (coaly < 100 && coaly > 0)) {
							field[coalx][coaly] = 7;
						}
						field[x][y] = 6;
					}

				}
				if (Math.random() < 0.02) {
					field[x][y] = 2;
					if (Math.random() < 0.125) {
						if (Math.random() < 0.125) {

							field[x][y] = 5;
						} else {
							field[x][y] = 4;
						}
					}
				}
				for (int z = -1; z < 2; z++) {
					for (int w = -1; w < 2; w++) {
						field[z + 50][w + 50] = 3;
					}
				}
			}
		}
		for (int x = 1; x < 20; x++) {
			for (int y = 1; y < 20; y++) {
				if (Math.random() < 0.0625) {
					int realx = x * 5;
					int realy = y * 5;
					for (int m = 0; m < 5; m++) {
						for (int h = 0; h < 5; h++) {
							field[realx + m][realy + h] = room1[m][h];
							enemies[enemiesloaded] = new enemy(realx + 2, realy + 2, 0);
						}
					}
				}
			}
		}
		draw(g);
	}
public void drawimg(Graphics2D g){
	g.fillRect(0, 0, 900, 900);
	if (playing) {
		for (double x = -4; x < 5; x+=0.5) {
			for (double y = -4; y < 5; y+=0.5) {
				if ((minerx + x < 1 || minerx + x > 99) || (minery + y < 1 || minery + y > 99)) {
					g.setColor(Color.BLACK);
					g.fillRect((int)((x + 4) * 100), ((int)(y + 4)) * 100, 100, 100);
				} else if (field[(int) (minerx + x)][(int)(minery + y)] == 0) {
					g.drawImage(b[2], (int)((x + 4) * 100), (int)((y + 4) * 100), 100, 100, null);
				}else if (field[(int)(minerx + x)][(int)(minery + y)] == 1) {
					g.setColor(Color.GRAY.darker());
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 2) {
					g.setColor(new Color(196, 180, 0));
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 3) {
					g.setColor(Color.white);
					g.fillRect((int)((x + 4) * 100), (int)((y + 4) * 100), 100, 100);
					
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 4) {
					g.setColor(new Color(255, 0, 255));
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 5) {
					g.setColor(Color.green);
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 6) {
					g.setColor(Color.blue);
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 7) {
					g.setColor(Color.orange);
					g.fillRect((int)(x + 4) * 100, (int)(y + 4) * 100, 100, 100);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 8) {
					g.setColor(Color.gray);
					g.fillRect((int)((x + 4) * 100), (int)((y + 4) * 100), 100, 100);
					g.setColor(Color.blue.brighter());
					g.fillRect((int)((x + 4) * 100) + 25, (int)((y + 4) * 100) + 25, 50, 50);
				} else if (field[(int)(minerx + x)][(int)(minery + y)] == 9) {
					g.drawImage(b[1], (int)(x + 4) * 100, (int)(y + 4) * 100,100,100,null);
				}
			}
		}
		// g.setColor(Color.RED);
		// g.fillOval(400, 400, 100, 100);
		// g.setColor(Color.BLACK);
		// g.drawOval(420, 425, 10, 10);
		// g.drawOval(470, 425, 10, 10);
		// g.drawLine(425, 475, 450, 487);
		// g.drawLine(475, 475, 450, 487);
		// g.drawLine(425, 475, 475, 475);
		g.drawImage(b[0], 400, 400, 100, 100, null);
		g.setColor(Color.GREEN);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.drawString(points + " points", 300, 200);
		g.drawString(fuel + " fuel", 300, 300);
		g.drawString(update, 300, 100);
		g.drawString("?", 50, 100);
		for (int i = 0; i < enemiesloaded; i++) {

			g.setColor(Color.blue);

		}

	} else {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 900, 900);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		g.drawString("you lost with " + points + " points", 200, 500);
	}
}
	public void paint(Graphics g) {
		/*g.clearRect(0, 0, 900, 900);
		if (playing) {
			for (int x = -4; x < 5; x++) {
				for (int y = -4; y < 5; y++) {
					if ((minerx + x < 1 || minerx + x > 99) || (minery + y < 1 || minery + y > 99)) {
						g.setColor(Color.BLACK);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 1) {
						g.setColor(Color.GRAY.darker());
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 2) {
						g.setColor(new Color(196, 180, 0));
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 3) {
						g.setColor(Color.white);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 4) {
						g.setColor(new Color(255, 0, 255));
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 5) {
						g.setColor(Color.green);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 6) {
						g.setColor(Color.blue);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 7) {
						g.setColor(Color.orange);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
					} else if (field[minerx + x][minery + y] == 8) {
						g.setColor(Color.gray);
						g.fillRect((x + 4) * 100, (y + 4) * 100, 100, 100);
						g.setColor(Color.blue.brighter());
						g.fillRect((x + 4) * 100 + 25, (y + 4) * 100 + 25, 50, 50);
					} else if (field[minerx + x][minery + y] == 9) {
						g.drawImage(b[1], (x + 4) * 100, (y + 4) * 100,100,100,null);
					}
				}
			}
			// g.setColor(Color.RED);
			// g.fillOval(400, 400, 100, 100);
			// g.setColor(Color.BLACK);
			// g.drawOval(420, 425, 10, 10);
			// g.drawOval(470, 425, 10, 10);
			// g.drawLine(425, 475, 450, 487);
			// g.drawLine(475, 475, 450, 487);
			// g.drawLine(425, 475, 475, 475);
			g.drawImage(b[0], 400, 400, 100, 100, null);
			g.setColor(Color.GREEN);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
			g.drawString(points + " points", 300, 200);
			g.drawString(fuel + " fuel", 300, 300);
			g.drawString(update, 300, 100);
			g.drawString("?", 50, 100);
			for (int i = 0; i < enemiesloaded; i++) {

				g.setColor(Color.blue);

			}

		} else {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 900, 900);
			g.setColor(Color.RED);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
			g.drawString("you lost with " + points + " points", 200, 500);
		}*/
		g.drawImage(screenbuffer, 0, 0, null);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(ABORT);
		}
		if (playing) {
			if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && minery > 1) {
				if (!(field[(int)minerx][(int)minery - 1] == 8)) {
					minery-=1;

				}
				fuel--;
			}
			if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && minerx > 1) {
				if (!(field[(int)minerx - 1][(int)minery] == 8)) {
					minerx-=1;

				}
				fuel--;
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && minery < 99) {
			if (!(field[(int)minerx][(int)minery + 1] == 8)) {
				minery+=1;

			}
			fuel--;
		}
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && minerx < 99) {
			if (!(field[(int)minerx + 1][(int)minery] == 8)) {
				minerx+=1;

			}
			fuel--;
		}
		if (!(field[(int)minerx][(int)minery] == 0) && !(field[(int)minerx][(int)minery] == 3)) {
			speaker.playsound("audio/drillsound.wav");
		}
		if (field[(int)minerx][(int)minery] == 1) {
			points++;
		}
		if (field[(int)minerx][(int)minery] == 2) {
			points++;
		}
		if (field[(int)minerx][(int)minery] == 3) {
			fuel++;
		}

		if (field[(int)minerx][(int)minery] == 4) {
			points = points + 4;
		}
		if (field[(int)minerx][(int)minery] == 5) {
			points = points + 10;
		}
		if (field[(int)minerx][(int)minery] == 6) {
			fuel = fuel + 11;
		}
		if (field[(int)minerx][(int)minery] == 7) {
			fuel = fuel + 3;
		}
		if (field[(int)minerx][(int)minery] == 9) {
			if (Math.random() < 0.5) {
				speaker.playsound("audio/wonprize.wav");
				points = points + 20;
			} else if (Math.random() < 0.5) {
				speaker.playsound("audio/wonprize.wav");
				fuel = fuel + 50;
			} else {
				points = points - 10;
			}
		}
		if (fuel == 0) {
			speaker.dispose();
			new baseframe(false, 0,0).playsound("audio/deathnoise.wav");
			playing = false;
		}

		field[(int)minerx][(int)minery] = 3;
		draw(g);
		repaint();
	}
	public void draw(Graphics2D g){
		drawimg(g);
	}
	public static void main(String[] args) {
		minerframe m = new minerframe();
		new Thread(m).start();
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static int[] inttocolor(int color) {
		int[] product = new int[4];
		product[0] = (color & 0xff000000) >> 24;
		product[1] = (color & 0x00ff0000) >> 16;
		product[2] = (color & 0x0000ff00) >> 8;
		product[3] = color & 0x000000ff;
		return product;
	}
}
