import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args) {
        Logic logic = new Logic();
        if(args.length != 0)
        {
            logic.sendCommand(args);
        }
        else
        {
            Scanner sc = new Scanner(System.in);
            while(true)
            {
                logic.sendCommand(sc.nextLine());
            }
        }
    }
}
