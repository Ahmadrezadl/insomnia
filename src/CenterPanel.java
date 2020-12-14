import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CenterPanel extends JPanel {
    private String[] requests = {"No body" , "Form Data" ,"Json"};
    public CenterPanel(GUIComponents guiComponents){
        super();
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel tabs = new JPanel();

        JComboBox<RequestEnum> comboBox = new JComboBox<>(RequestEnum.values());
        guiComponents.setRequestMethod(comboBox);
        HintTextField url = new HintTextField("https://api.myproduct.com/v1/users");
        guiComponents.setUrl(url);
        Button send = new Button("Send",Color.WHITE,Color.gray,Color.lightGray);
        send.setPreferredSize(new Dimension(100,60));
        send.setFont(new Font("Arial",Font.BOLD,20));
        send.addActionListener(e -> guiComponents.logic.sendCommandFromGUI());
        tabs.setLayout(new BoxLayout(tabs,BoxLayout.X_AXIS));
        JButton header = new JButton("Header");

        JComboBox requestBody = new JComboBox(requests);
        header.setPreferredSize(new Dimension(120,60));
        header.setMinimumSize(new Dimension(120,60));
        header.setMaximumSize(new Dimension(120,60));
        requestBody.setPreferredSize(new Dimension(120,60));
        requestBody.setMinimumSize(new Dimension(120,60));
        requestBody.setMaximumSize(new Dimension(120,60));
        header.setFont(new Font("Arial",Font.BOLD,20));
        requestBody.setFont(new Font("Arial",Font.BOLD,15));
        tabs.add(requestBody);
        tabs.add(header);

        tabs.setBackground(Colors.almostBlack());
        bottomPanel.setBackground(Colors.grey());

        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0,120));
        topPanel.add(comboBox,BorderLayout.WEST);
        topPanel.add(url,BorderLayout.CENTER);
        topPanel.add(send,BorderLayout.EAST);
        topPanel.add(tabs,BorderLayout.SOUTH);

        add(topPanel,BorderLayout.NORTH);
        add(bottomPanel,BorderLayout.CENTER);

        //Bottom panel____________________________________________
        CardLayout card = new CardLayout();
        bottomPanel.setBackground(Colors.almostBlack());
        bottomPanel.setLayout(card);
        JPanel noBody = new JPanel();
        JScrollPane json = new JScrollPane();
        JTextArea formText = new JTextArea();
        formText.setFont(new Font("Arial", Font.PLAIN ,16));
        guiComponents.setData(formText);
        JPanel formDataPanel = new JPanel();
        formDataPanel.setLayout(new GridLayout(1,1));
        JScrollPane formData = new JScrollPane(formText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        noBody.setLayout(new GridLayout(1,1));
        noBody.setBackground(Colors.almostBlack());
        formDataPanel.add(formData);
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("images\\select.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        noBody.add(new JLabel(new ImageIcon(icon)));
        bottomPanel.add(noBody,"No body");
        bottomPanel.add(json,"Json");
        bottomPanel.add(formDataPanel,"Form Data");


        InHeaderPanel headerPanel = new InHeaderPanel();
        guiComponents.setInHeaderPanel(headerPanel);
        JScrollPane headerScrollPane = new JScrollPane(headerPanel);
        headerPanel.setLayout(new BoxLayout(headerPanel,BoxLayout.Y_AXIS));
        headerPanel.add(new HeaderRequest(headerPanel,guiComponents));
        Button addHeader = new Button("  + Add Header   ",Colors.almostBlack(),Color.gray,Color.lightGray);
        guiComponents.addHeaderButton  = addHeader;
        addHeader.setForeground(Color.WHITE);
        addHeader.setFont(new Font("Arial",Font.BOLD,17));
        headerPanel.add(addHeader);

        headerPanel.setBackground(Colors.almostBlack());
        bottomPanel.add(headerScrollPane,"header");

        addHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                headerPanel.add(new HeaderRequest(headerPanel,guiComponents));
                headerPanel.remove(addHeader);
                headerPanel.add(addHeader);
                guiComponents.mainFrame.setVisible(true);
            }
        });

        header.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(bottomPanel,"header");
            }
        });

        comboBox.addActionListener(e->{
            if(guiComponents.comboChangedWithMouse)
            {
                guiComponents.currentListItem.setComboBox((RequestEnum) comboBox.getSelectedItem(),true);
                guiComponents.currentListItem.comboBox = (RequestEnum) comboBox.getSelectedItem();
            }
            else
            {
                guiComponents.comboChangedWithMouse = true;
            }

        });


        requestBody.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(requestBody.getSelectedItem().toString() );
                card.show(bottomPanel,requestBody.getSelectedItem().toString());
            }
        });

    }
}
