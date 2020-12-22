package Painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Painter {

	private BufferedImage screen;
	private BufferedImage bg;
	private BufferedImage s1;
	
	public BufferedImage[] toGif;
	
	private double[] dirs;
	private double[] size;
	
	public double speed = 0.1;
	
	public boolean sRadius = false;
	public boolean sCircle = false;
	public boolean sMain = true;
	
	public Painter(BufferedImage img, double[] directions, double[] lenghts) {
		idGif = 0;
		toGif = new BufferedImage[(int) ((Math.PI*2)/speed) + 3];
		screen = img;
		bg = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		s1 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		dirs = directions.clone();
		size = lenghts.clone();
	}
	
	public Painter(BufferedImage img, double[] directions, double[] lenghts,
			boolean showRadius, boolean showCircle, boolean showMain) {
		idGif = 0;
		toGif = new BufferedImage[(int) ((Math.PI*2)/speed) + 3];
		screen = img;

		bg = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		s1 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		dirs = directions.clone();
		size = lenghts.clone();
		sCircle = showCircle;
		sRadius = showRadius;
		sMain = showMain;
	}

	double px;
	double py;
	double lpx = -1;
	double lpy = -1;
	double dir;
	int idGif = 0;
	
	public void DrawScreen() {
		Graphics2D gmain = (Graphics2D) screen.getGraphics();
		Graphics2D gbg = (Graphics2D) bg.getGraphics();
		Graphics2D gs = (Graphics2D) s1.getGraphics();

		px = screen.getWidth()/2;
		py = screen.getHeight()/2;
		dir = 0;

		gbg.clearRect(0, 0, s1.getWidth(), s1.getHeight());
		gbg.setColor(new Color(255,255,255,50));
		for (int i = 0; i < dirs.length; i++) {
			dir += dirs[i];
			int pxx = (int) px;
			int pyy = (int) py;
			go(size[i]);
			if(sRadius) {
				gbg.drawLine(pxx, pyy, (int)px, (int)py);
				gbg.fillOval(pxx-2, pyy-2, 4, 4);
			}
//			if(sCircle) {
//				g.drawOval(pxx, pyy, (int)(px-pxx), (int)(py-pyy));
//			}
			setNewDir(i);
		}
		
		gs.setColor(Color.CYAN);//Color.RED

		if(sMain) {
			gs.fillOval((int)px-3, (int)py-3, 6, 6);
			gs.setStroke(new BasicStroke(6, 0, 0));
			if(lpx > -1)gs.drawLine((int)lpx,(int)lpy, (int)px, (int)py);
		}
		lpx = px;
		lpy = py;
		gs.dispose();
		gbg.dispose();
		
		gmain.drawImage(bg, 0, 0, null);
		gmain.drawImage(s1, 0, 0, null);
		gmain.dispose();
		
		toGif[idGif] = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D ggif = (Graphics2D) toGif[idGif].getGraphics();
		ggif.drawImage(screen, 0, 0, null);
		ggif.dispose();
		idGif++;
	}
	
	private void setNewDir(int id) {
		dirs[id] += speed * Math.pow(-1, id) * id;
	}
	
	private void go(double steps) {
		px += steps * Math.sin(dir);
		py += steps * Math.cos(dir);
	}
}
