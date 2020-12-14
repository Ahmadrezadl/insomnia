import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadingFrame extends JFrame {
    public LoadingFrame(){
        this.setSize(639,639);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setUndecorated(true);
        JLabel Logo = new JLabel();
        Image logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("images\\loadingLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logo.setIcon(new ImageIcon(logo));
        add(Logo);
        this.setVisible(true);

    }
}
