import javax.swing.*;
import java.awt.*;

public class GUIComponents {
    static boolean followRedirect = false;
    static boolean exitOnClose = false;
    static MainFrame mainFrame;
    JPanel preview;
    Button addHeaderButton;
    JLabel responseTime;
    JLabel responseMessage;
    Logic logic;
    MenuBar menuBar;
    LeftPanel leftPanel;
    CenterPanel centerPanel;
    RightPanel rightPanel;
    JTextArea formText;
    JTextField url;
    JComboBox<RequestEnum> comboBox;
    JTextArea outData;
    OutHeaderPanel outHeaderPanel;
    InHeaderPanel headerPanel;
    ListItem currentListItem;
    boolean comboChangedWithMouse = true;

    public GUIComponents(Logic logic){
        LoadingFrame loadingFrame = new LoadingFrame();
        this.logic = logic;

        mainFrame = new MainFrame(this);
        menuBar = new MenuBar(this);
        leftPanel = new LeftPanel(this);
        centerPanel = new CenterPanel(this);
        rightPanel = new RightPanel(this);


        mainFrame.add(menuBar, BorderLayout.NORTH);
        mainFrame.add(leftPanel,BorderLayout.WEST);
        mainFrame.add(rightPanel,BorderLayout.EAST);
        mainFrame.add(centerPanel,BorderLayout.CENTER);

        loadingFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    public void setOutHeadersPanel(OutHeaderPanel header) {
        this.outHeaderPanel=header;
    }


    public void setData(JTextArea formText) {
        this.formText = formText;
    }

    public void setUrl(HintTextField url) {
        this.url = url;
    }

    public void setRequestMethod(JComboBox<RequestEnum> comboBox) {
        this.comboBox = comboBox;
    }

    public void setOutputData(JTextArea outData) {
        this.outData = outData;
    }

    public void setInHeaderPanel(InHeaderPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    public void setSelectedItem(ListItem listItem) {
        currentListItem = listItem;
    }
}
