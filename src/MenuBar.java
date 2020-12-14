import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    public MenuBar(GUIComponents guiComponents)
    {
        super();

        JMenu application = new JMenu("Application");
        MenuItem options = new MenuItem("Options",guiComponents.logic, KeyEvent.VK_O);
        options.setAccelerator(KeyStroke.getKeyStroke("control O"));
        MenuItem exit = new MenuItem("Exit",guiComponents.logic, KeyEvent.VK_Q);
        exit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        this.add(application);

        application.add(options);


        application.add(exit);
        JMenu view = new JMenu("View");
        JMenuItem viewFull = new MenuItem("Toggle Full Screen",guiComponents.logic, KeyEvent.VK_F);
        viewFull.setAccelerator(KeyStroke.getKeyStroke("control F"));
        JMenuItem viewSide = new MenuItem("Toggle Sidebar",guiComponents.logic, KeyEvent.VK_S);
        viewSide.setAccelerator(KeyStroke.getKeyStroke("control S"));
        view.add(viewFull);
        view.add(viewSide);
        add(view);


        JMenu help = new JMenu("Help");
        JMenuItem about = new MenuItem("About",guiComponents.logic, KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke("control A"));
        JMenuItem helpItem = new MenuItem("Help",guiComponents.logic, KeyEvent.VK_H);
        helpItem.setAccelerator(KeyStroke.getKeyStroke("control H"));
        help.add(about);
        help.add(helpItem);
        add(help);

    }
}
