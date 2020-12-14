import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends JPanel {
    public RightPanel(GUIComponents guiComponents){
        super();
        setLayout(new BorderLayout());
        Font font = new Font("Arial",Font.BOLD,15);
        JPanel details = new JPanel();
        details.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel responseMessage = new JLabel("Request Status");
        guiComponents.responseMessage = responseMessage;
        responseMessage.setFont(font);
        responseMessage.setBackground(Color.GREEN);
        responseMessage.setOpaque(true);
        JLabel responseTime = new JLabel("0.000 S");
        guiComponents.responseTime = responseTime;
        responseTime.setFont(font);
        details.add(responseMessage);
        details.add(responseTime);
        add(details,BorderLayout.NORTH);
        setMinimumSize(new Dimension(400,0));
        setPreferredSize(new Dimension(400,0));

        String [] items = {"Raw","Preview"};
        JComboBox messageBody = new JComboBox(items);
        JButton headerButton = new JButton("Header");
        JPanel tabbedPane = new JPanel();
        tabbedPane.setLayout(new BorderLayout());
        JPanel tabs = new JPanel();
        tabs.setLayout(new FlowLayout(FlowLayout.LEFT));
        tabs.add(messageBody);
        tabs.add(headerButton);
        tabbedPane.add(tabs,BorderLayout.NORTH);
        JPanel bot = new JPanel();
        tabbedPane.add(bot,BorderLayout.CENTER);
        CardLayout cd = new CardLayout();
        bot.setLayout(cd);
        JPanel raw = new JPanel();
        JTextArea outData = new JTextArea();
        raw.setLayout(new GridLayout(1,1));
        JScrollPane scrollRaw = new JScrollPane(outData,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        raw.add(scrollRaw);
        outData.setEditable(false);
        outData.setLineWrap(true);
        outData.setWrapStyleWord(true);
        outData.setFont(new Font("Arial",Font.PLAIN,15));
        guiComponents.setOutputData(outData);

        JPanel previewPanel = new JPanel();
        JPanel previewContent = new JPanel();
        previewContent.setLayout(new FlowLayout());
        previewPanel.setLayout(new GridLayout(1,1));
        JScrollPane scrollPreview = new JScrollPane(previewContent,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        previewPanel.add(scrollPreview);
        guiComponents.preview = previewContent;


        bot.add(raw,"Raw");
        bot.add(previewPanel,"Preview");
        OutHeaderPanel header = new OutHeaderPanel();
        guiComponents.setOutHeadersPanel(header);
        header.setBackground(Colors.almostBlack());
        bot.add(header,"Header");
        add(tabbedPane,BorderLayout.CENTER);

        JPanel justCopy = new JPanel();
        justCopy.setMaximumSize(new Dimension(100000,50));
        justCopy.setLayout(new GridLayout(1,1));
        JButton copy = new JButton("Copy to clipboard");

        header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
        justCopy.add(copy);
        header.add(justCopy);

        headerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cd.show(bot,"Header");
            }
        });

        messageBody.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cd.show(bot,messageBody.getSelectedItem().toString());
                guiComponents.preview.repaint();
                guiComponents.preview.revalidate();
            }
        });

        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "\"";
                for(Component component : header.getComponents())
                {
                    if(component instanceof TwoField)
                    {
                        TwoField twoField = (TwoField)component;
                        text += twoField.getText()+";";
                    }
                }
                if(!text.equals(""))
                {
                    text = text.substring(0,text.length()-1);
                    text += "\"";
                }
                StringSelection selection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
    }
}
