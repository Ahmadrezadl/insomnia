import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Button extends javax.swing.JButton {
    Color background = Colors.almostBlack();
    Color touch = Colors.grey();
    Color click = Colors.almostGrey();
    public Button(String name,Color background,Color touch,Color click){
        super(name);
        this.background = background;
        this.touch = touch;
        this.click = click;
        setBorderPainted(false);
        setBorder(null);
        setFocusable(false);
        setMargin(new Insets(0, 0, 0, 0));
        setOpaque(true);
        setFocusPainted(false);
        setBackground(background);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setContentAreaFilled(false);
                setBackground(click);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setContentAreaFilled(true);
                setBackground(touch);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(touch);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(background);

            }
        });

    }
    public Button(String image)
    {
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("images\\" + image + ".png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        setIcon(new ImageIcon(icon));
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(null);
        setMargin(new Insets(0, 0, 0, 0));
        setBackground(background);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setContentAreaFilled(false);
                setBackground(click);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setContentAreaFilled(true);
                setBackground(touch);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(touch);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(background);

            }
        });
    }


}

