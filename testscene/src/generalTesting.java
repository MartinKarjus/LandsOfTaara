import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class generalTesting {
    public static void main(String args[]) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("./resources/eesti-maakonnad.jpg"));
        } catch (IOException e) {
        }
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //System.out.println("x:" + x + ",y:" + y +" has:" + image.getRGB(x, y));
                int  clr   = image.getRGB(x, y);
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;
                System.out.println("XY:" + x + "," + y + " ,red:" + red + " ,blue:" + blue + " ,green:" +green);
                image.setRGB(x, y, clr);
            }
        }

    }
}
