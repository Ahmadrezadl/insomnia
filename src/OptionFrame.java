import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class OptionFrame extends JFrame {
    public OptionFrame(){
        super("Options");
        setSize(new Dimension(300,80));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("Follow Redirect"));
        JCheckBox fr = new JCheckBox();
        add(fr);
        fr.setSelected(GUIComponents.followRedirect);
        add(new JLabel("Exit on close"));
        JCheckBox eon = new JCheckBox();
        eon.setSelected(GUIComponents.exitOnClose);
        add(eon);
        eon.addActionListener(e->{
            GUIComponents.exitOnClose=eon.isSelected();
            if(GUIComponents.exitOnClose)
            {
                GUIComponents.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            else
            {
                GUIComponents.mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
            try {
                String settings = GUIComponents.exitOnClose + " " + GUIComponents.followRedirect;
                FileWriter fw=new FileWriter("settings.sav");
                fw.write(settings);
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        fr.addActionListener(e->{
            GUIComponents.followRedirect=fr.isSelected();
            try {
                String settings = GUIComponents.exitOnClose + " " + GUIComponents.followRedirect;
                FileWriter fw=new FileWriter("settings.sav");
                fw.write(settings);
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}
