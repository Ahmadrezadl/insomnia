import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.IOException;

public class LeftPanel extends JPanel {
    JPanel list;
    public LeftPanel(GUIComponents guiComponents){
        super();
        setLayout(new BorderLayout());
        list = new JPanel();
        list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));
        add(list,BorderLayout.CENTER);
        list.setBackground(Colors.almostGrey());
        JPanel settings = new JPanel();
        settings.setLayout(new BorderLayout());
        Button newRequest = new Button("",Colors.almostBlack(),Colors.grey(),Colors.almostGrey());
        HintTextField search = new HintTextField("Filter");
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("images\\addButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        newRequest.setIcon(new ImageIcon(icon));
        newRequest.addActionListener(e->{
            String name=JOptionPane.showInputDialog(guiComponents.mainFrame,"Enter Name","Request");
            ListItem def = new ListItem(name,"get",guiComponents);
            list.add(def);
            new AskRequestMethodWindow(def,guiComponents);
//            guiComponents.currentListItem = def;
        });
        settings.add(newRequest,BorderLayout.EAST);
        settings.add(search,BorderLayout.CENTER);


        settings.setMaximumSize(new Dimension(300,30));
        settings.setPreferredSize(new Dimension(300,30));
        add(settings,BorderLayout.NORTH);

        ListItem def = new ListItem("Default","get",guiComponents);
        list.add(def);
        guiComponents.currentListItem = def;
        search.addActionListener(e -> {
            for(Component component : list.getComponents())
            {
                ListItem listItem = (ListItem) component;
                if(listItem.realName.toLowerCase().contains(search.getText().toLowerCase()))
                {
                    listItem.setVisible(true);
                }
                else
                {
                    listItem.setVisible(false);
                }
            }
        });

        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(list.getComponents().length >= 12 || search.getText().equals("Filter"))return;
                for(Component component : list.getComponents())
                {
                    ListItem listItem = (ListItem) component;
                    if(listItem.realName.toLowerCase().contains(search.getText().toLowerCase()))
                    {
                        listItem.setVisible(true);
                    }
                    else
                    {
                        listItem.setVisible(false);
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(list.getComponents().length >= 12|| search.getText().equals("Filter"))return;
                for(Component component : list.getComponents())
                {
                    ListItem listItem = (ListItem) component;
                    if(listItem.realName.toLowerCase().contains(search.getText().toLowerCase()))
                    {
                        listItem.setVisible(true);
                    }
                    else
                    {
                        listItem.setVisible(false);
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

    }
}
