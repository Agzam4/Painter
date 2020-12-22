package Gif;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import java.awt.image.BufferedImage;
import java.io.File;

public class CreateGif {

    public static void main(BufferedImage[] images, String file, JProgressBar progressBar) throws Exception {
    	
        BufferedImage first = images[0];
        ImageOutputStream output = new FileImageOutputStream(new File(file + ".gif"));

        GifWriter writer = new GifWriter(output, BufferedImage.TYPE_INT_RGB, 50, true);
        writer.writeToSequence(first);

        progressBar.setMaximum(images.length-1);
        for (int i = 0; i < images.length; i++) {
        	writer.writeToSequence(images[i]);
        	progressBar.setValue(i);
		}
        
    	writer.writeToSequence(images[0]);
    	
        writer.close();
        output.close();
        
        JOptionPane.showMessageDialog(null, "READY");
    }
}