package JFrames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Gif.CreateGif;
import Painter.Painter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class JMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMain frame = new JMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public JMain() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setTitle("By Agzam4 | Class GifWriter by memorynotfound.com");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		JLabel iv = new JLabel(new ImageIcon(bi));
		contentPane.add(iv, BorderLayout.CENTER);
		
		JProgressBar progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.NORTH);
		
		
		Thread drawer = new Thread() {
			
			@Override
			public void run() {

				final int arrSize = 6;//(int) (Math.random()*15 + 5);

				double[] d = new double[arrSize];
				double[] s = new double[arrSize];
				
				for (int i = 0; i < arrSize; i++) {
					d[i] = Math.random()*360;
					s[i] = s[i] + Math.random()*25 + 5;
				}
				
				Painter painter = new Painter(bi, d, s, true, true, true);
				//new int[] {0,166,-156,171,23}, new int[] {0,24,91,20,4}
//				Painter painter = new Painter(bi, new int[] {0,45}, new int[] {30,20},
//						true, true);
				
				double speed = painter.toGif.length-1;
				System.out.println(speed);
				for (int i = 0; i < speed; i++) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					painter.DrawScreen();
					iv.repaint();
				}
				painter.sCircle = false;
				painter.sRadius = false;
				painter.sMain = false;
				painter.DrawScreen();
				iv.repaint();
				
				if(JOptionPane.showConfirmDialog(null, "Create Gif") == 0) {
					try {
						CreateGif.main(painter.toGif, "gif", progressBar);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		drawer.start();
	}

}
