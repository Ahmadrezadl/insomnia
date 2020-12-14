import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

public class MainFrame extends JFrame{
    private GUIComponents guiComponents;
    public MainFrame(GUIComponents guiComponents)
    {
        super("Insomnia");
        GUIComponents.mainFrame = this;
        try {
            Image icon = ImageIO.read(getClass().getResource("images\\insomnia.png"));
            TrayIcon trayIcon = new TrayIcon(icon);
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    GUIComponents.mainFrame.setVisible(true);
                    if(e.getButton() == MouseEvent.BUTTON3) {
                        System.exit(0);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            SystemTray tray = SystemTray.getSystemTray();

            tray.add(trayIcon);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }


        this.guiComponents = guiComponents;
        this.setMinimumSize(new Dimension(1000,500));
        setExtendedState(JFrame.ICONIFIED);
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
    }

}
