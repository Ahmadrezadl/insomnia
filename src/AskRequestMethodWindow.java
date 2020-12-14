import javax.swing.*;
import java.awt.*;

public class AskRequestMethodWindow {
    public AskRequestMethodWindow(ListItem def,GUIComponents guiComponents){
        JFrame jFrame = new JFrame("Choose Method");
        jFrame.setSize(new Dimension(250,250));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width/2-jFrame.getSize().width/2, dim.height/2-jFrame.getSize().height/2);
        jFrame.setLayout(new GridLayout(2,2));
        JButton get = new JButton("GET");
        jFrame.add(get);
        JButton post = new JButton("POST");
        jFrame.add(post);
        JButton delete = new JButton("DELETE");
        jFrame.add(delete);
        JButton put = new JButton("PUT");
        jFrame.add(put);
        jFrame.setVisible(true);
        get.addActionListener(e->{
            def.setComboBox(RequestEnum.valueOf("GET"),false);
            jFrame.setVisible(false);
            guiComponents.leftPanel.list.repaint();
            guiComponents.leftPanel.list.revalidate();
            guiComponents.leftPanel.list.setVisible(true);

        });
        post.addActionListener(e->{
            def.setComboBox(RequestEnum.valueOf("POST"),false);
            jFrame.setVisible(false);
            guiComponents.leftPanel.list.repaint();
            guiComponents.leftPanel.list.revalidate();
            guiComponents.leftPanel.list.setVisible(true);

        });
        put.addActionListener(e->{
            def.setComboBox(RequestEnum.valueOf("PUT"),false);
            jFrame.setVisible(false);
            guiComponents.leftPanel.list.repaint();
            guiComponents.leftPanel.list.revalidate();
            guiComponents.leftPanel.list.setVisible(true);
        });
        delete.addActionListener(e->{
            def.setComboBox(RequestEnum.valueOf("DELETE"),false);
            jFrame.setVisible(false);
            guiComponents.leftPanel.list.repaint();
            guiComponents.leftPanel.list.revalidate();
            guiComponents.leftPanel.list.setVisible(true);

        });
    }
}
