import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class ListItem extends Button implements Runnable {
    private String disName;
    String realName;
    String formText = "";
    String url = "";
    RequestEnum comboBox = RequestEnum.GET;
    String outData = "";
    String outHeaderPanel = "";
    String headerPanel = "";
    String status = "Request Status";
    String time = "0:000S";
    Component preview = null;
    GUIComponents guiComponents;

    public ListItem(String name , String photo,GUIComponents guiComponents){
        super(name,Colors.almostBlack(),Colors.grey(),Colors.almostGrey());
        this.realName = name;
        findDisName();
        this.guiComponents = guiComponents;
        setText(disName);
        setHorizontalAlignment(LEFT);
        setMinimumSize(new Dimension(300,30));
        setMaximumSize(new Dimension(300,30));
        setPreferredSize(new Dimension(300,30));

        setForeground(Color.WHITE);
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("images\\" + photo + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        setIcon(new ImageIcon(icon));
        this.addActionListener(e -> {
            guiComponents.comboChangedWithMouse = false;

            guiComponents.currentListItem.url = guiComponents.url.getText();
            guiComponents.url.setText(this.url);

            guiComponents.currentListItem.formText = guiComponents.formText.getText();
            guiComponents.formText.setText(this.formText);

            guiComponents.currentListItem.comboBox = (RequestEnum) guiComponents.comboBox.getSelectedItem();
            guiComponents.comboBox.setSelectedItem(this.comboBox);

            guiComponents.currentListItem.outData = guiComponents.outData.getText();
            guiComponents.outData.setText(this.outData);

            guiComponents.currentListItem.headerPanel = guiComponents.headerPanel.getHeaders();
            guiComponents.headerPanel.setHeaders(this.headerPanel,guiComponents);
            guiComponents.headerPanel.repaint();
            guiComponents.headerPanel.revalidate();

            guiComponents.currentListItem.outHeaderPanel = guiComponents.outHeaderPanel.getHeaders();
            guiComponents.outHeaderPanel.setHeaders(this.outHeaderPanel,guiComponents);
            guiComponents.outHeaderPanel.repaint();
            guiComponents.outHeaderPanel.revalidate();

            guiComponents.currentListItem.status = guiComponents.responseMessage.getText();
            guiComponents.responseMessage.setText(this.status);

            if(guiComponents.preview.getComponents().length != 0)
            {
                guiComponents.currentListItem.preview = guiComponents.preview.getComponent(0);
                guiComponents.preview.remove(0);
            }
            if(this.preview!= null)
                guiComponents.preview.add(this.preview);
            guiComponents.preview.repaint();
            guiComponents.preview.revalidate();

            guiComponents.currentListItem.time = guiComponents.responseTime.getText();
            guiComponents.responseTime.setText(this.time);

            guiComponents.setSelectedItem(this);


        });
        Thread thread = new Thread(this);
        thread.start();
    }

    private void findDisName() {
        if(realName.length() > 24)
        {
            disName = realName.substring(0,26) + "...";
        }
        else
        {
            disName = realName;
        }
    }

    public void setComboBox(RequestEnum requestEnum,boolean changeMain)
    {
        if(changeMain)
        guiComponents.comboBox.setSelectedItem(requestEnum);
        this.comboBox = requestEnum;
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("images\\" + requestEnum.name() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        setIcon(new ImageIcon(icon));
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(100);
                if(!disName.contains(">") && guiComponents.currentListItem==this)
                {
                    disName = "> " + disName;
                    this.setText(disName);
                }
                else if(disName.contains(">") && guiComponents.currentListItem!=this)
                {
                    disName = disName.replace("> ","");
                    this.setText(disName);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
