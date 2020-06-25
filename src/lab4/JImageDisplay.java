package lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    BufferedImage image;

    public JImageDisplay(int width, int height){
        image =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
    }

    // отрисовка изображения в компоненте
    public void paintComponent (Graphics g){
        g.drawImage (image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    // устонавливает все пиксели изображения в черный цвет
    public void clearImage () {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x,y,0);
            }
        }
    }
    // устонавливает пиксель в определенный цвет
    public void  drawPixel (int x, int y, int rgbColor){
        image.setRGB(x,y, rgbColor);
    }

    public BufferedImage getBufferedImage() {
        return image;
    }
}