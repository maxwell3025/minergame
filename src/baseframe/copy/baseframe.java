package baseframe.copy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class baseframe extends JFrame
		implements KeyListener, MouseMotionListener, MouseListener, Runnable, MouseWheelListener {
	protected int mousex;
	protected int mousey;
	protected int resolution = 1000;
	protected Color[][] palette = new Color[resolution][resolution];
	protected int time = 0;
	protected double pixelheight = this.getHeight() / resolution;
	protected double pixelwidth = this.getWidth() / resolution;
	protected int mouselevel = 800;
	protected boolean mousedown = false;
	protected boolean mouseheld;
	protected boolean showframe = true;
	protected String display1;
	protected boolean display = true;
	protected boolean[] keyspressed = new boolean[1000 ];
	public baseframe(boolean visible, int x, int y) {
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);
		setSize(x, y);
		setVisible(visible);
		pixelheight = this.getHeight() / resolution;
		pixelwidth = this.getWidth() / resolution;
	}

	public void paint(Graphics g) {

		for (int x = 0; x < resolution; x++) {
			for (int y = 0; y < resolution; y++) {
				g.setColor(palette[x][y]);
				g.fillRect((int) (x * pixelwidth), (int) (y * pixelheight), (int) pixelwidth + 1,
						(int) pixelheight + 1);
			}
		}
		if (showframe) {
			g.setFont(new Font(getTitle(), 20, 100));
			g.setColor(Color.BLACK);
			g.drawString("" + time, 100, 100);
		}
		if (display) {
			g.drawString(display1, 100, 200);
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		keyspressed[e.getKeyCode()]=true;

	}

	public void keyReleased(KeyEvent e) {
		keyspressed[e.getKeyCode()]=false;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(ABORT);
		}

	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(1);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time++;
			pixelheight = (double) this.getHeight() / resolution;
			pixelwidth = (double) this.getWidth() / resolution;
			resolution = mouselevel;

			update();
			mousedown = false;
			repaint();
		}
	}

	public void drawline(int x1, int y1, int x2, int y2) {

	}

	public double distance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		mousex = (int) ((double) e.getX());
		mousey = (int) ((double) e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		mousex = (int) ((double) e.getX());
		mousey = (int) ((double) e.getY());
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		mousedown = true;
		mouseheld = true;

	}

	public void mouseReleased(MouseEvent e) {
		mouseheld = false;

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int clip255(int x) {
		if ((x <= 255) && (x >= 0)) {
			return x;
		} else if (x < 0) {
			return 0;
		} else {
			return 255;
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		mouselevel -= e.getWheelRotation();

	}

	public Color inputcolor(int a, int b, int c) {
		return new Color(clip255(a), clip255(b), clip255(c));
	}
	public void playsound(String filename){
		try {
	         URL url = this.getClass().getClassLoader().getResource(filename);
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         Clip clip = AudioSystem.getClip();
	         clip.open(audioIn);
	         clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	         System.out.println("1");
	      } catch (IOException e) {
	         e.printStackTrace();
	         System.out.println("2");
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	         System.out.println("3");
	      }
	}

}
