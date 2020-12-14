import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeaderRequest extends JPanel {
    private HintTextField key;
    private HintTextField value;
    private JCheckBox checkBox;
    public HeaderRequest(JPanel mother , GUIComponents guiComponents){
        super();
        this.setPreferredSize(new Dimension(100,60));
        this.setMaximumSize(new Dimension(10000,60));
        this.setBackground(Colors.almostGrey());
        this.setForeground(Color.WHITE);
        key = new HintTextField("  header",Color.WHITE);
        key.setBackground(Colors.almostBlack());
        value = new HintTextField("  value",Color.WHITE);
        value.setBackground(Colors.almostBlack());
        Button remove = new Button("trash");
        checkBox = new JCheckBox();
        checkBox.setBackground(Colors.almostBlack());
        checkBox.setSelected(true);
        setLayout(new BorderLayout());
        JPanel middle = new JPanel();
        JPanel right = new JPanel();
        middle.setLayout(new GridLayout(1,2));
        right.setLayout(new GridLayout(1,2));
        middle.add(key);
        middle.add(value);
        right.add(remove);
        right.add(checkBox);
        JPanel tmp = new JPanel();
        tmp.setBackground(Colors.almostBlack());
        this.add(tmp,BorderLayout.WEST);
        tmp.setMinimumSize(new Dimension(50,0));
        this.add(middle,BorderLayout.CENTER);
        this.add(right,BorderLayout.EAST);
        JPanel temp = new JPanel();
        temp.setBackground(Colors.almostBlack());
        this.add(temp,BorderLayout.NORTH);

        JPanel thisPanel = this;
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mother.remove(thisPanel);
                guiComponents.mainFrame.setVisible(true);
                mother.repaint();

            }
        });
    }
    public String getHeader()
    {
        return key.getText()+":"+value.getText();
    }
    public boolean isAvailable()
    {
        return checkBox.isSelected();
    }

    public void setHeader(String s) {
        if(s.length()<2)return;
        System.out.println(s);
        key.setText(s.split(":")[0]);
        value.setText(s.split(":")[1]);
        key.showingHint = false;
        value.showingHint = false;
        key.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);
    }
}
