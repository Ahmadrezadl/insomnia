import javax.swing.*;
import java.awt.*;

public class TwoField extends JPanel {
    private JTextField key;
    private JTextField value;
    public TwoField(String field1, String fields2)
    {
        super();
        setLayout(new GridLayout(1,2));
        key = new JTextField(field1);
        value = new JTextField(fields2);
        key.setEditable(false);
        value.setEditable(false);
        add(key);
        add(value);
        key.setBackground(Colors.almostBlack());
        value.setBackground(Colors.almostBlack());
        key.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);
        setMaximumSize(new Dimension(1000,70));
    }
    public String getText()
    {
        return key.getText() + ":" + value.getText();
    }
}
