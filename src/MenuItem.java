import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuItem extends JMenuItem implements ActionListener {
    private Logic logic;
    public MenuItem(String name,Logic logic,int mnemonic)
    {
        super(name);
        this.logic = logic;
        setMnemonic(mnemonic);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logic.sendCommand(this.getText().toLowerCase());
    }
}
