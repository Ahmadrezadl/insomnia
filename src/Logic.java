import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class Logic implements Runnable{
    private GUIComponents guiComponents;
    private Dimension lastSize = new Dimension(700,500);
    private boolean fullScreen = true;
    private ListItem currentItem=null;


    public Request sendCommand(String [] args)
    {
        if(checkHelp(args))return null;
        if(checkList(args))return null;
        try {
            if(checkFire(args))return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Object is not in list");
        }
        String address = args[0];
        String headers = "";
        boolean response = false;
        String method = "GET";
        boolean save = false;
        String output = "";
        String data = "";
        boolean followRedirect = false;

        for(int i = 0;i < args.length;i++)
        {
            String arg = args[i];
            if(arg.toLowerCase().contains("http://"))
            {
                address = arg;
            }
            else if(arg.equals("--method") || arg.equals("-M"))
            {
                if(args.length == i+1)
                {
                    throw new IllegalArgumentException("Not Enough Arguments after --method");
                }
                method = args[i+1];
                i++;
            }
            else if(arg.equals("--headers") || arg.equals("-H"))
            {
                if(args.length == i+1)
                {
                    throw new IllegalArgumentException("Not Enough Arguments after --headers");
                }
                headers = args[i+1];
                i++;
            }
            else if(arg.equals("-i"))
            {
                response = true;
            }
            else if(arg.equals("--output") || arg.equals("-O"))
            {
                if(i != args.length-1 && !args[i+1].split("")[0].equals("-"))
                {
                    output = args[i+1];
                    i++;
                }
                else
                {
                    DateFormat df = new SimpleDateFormat("yyddMMHHmmss");
                    Date date = new Date();
                    output = "output_" + df.format(date);
                }
            }
            else if(arg.equals("--save") || arg.equals("-S"))
            {
                save = true;
            }
            else if(arg.toLowerCase().toLowerCase().equals("-f"))
            {
                followRedirect = true;
            }
            else if(arg.equals("--data") || arg.toLowerCase().equals("-d"))
            {
                if(args.length == i+1)
                {
                    throw new IllegalArgumentException("Not Enough Arguments after --data");
                }
                data = args[i+1];
                i++;
            }
        }

        Request request = new Request(address,headers,response,method,output,data,followRedirect);
        request.sendRequest();


        try{
            if(save)
            {
                ArrayList<Request> requests = new ArrayList<>();
                File saveFile = new File("saves.log");
                if(saveFile.exists())
                {
                    FileInputStream fis = new FileInputStream(saveFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    requests = (ArrayList<Request>) ois.readObject();
                }
                requests.add(request);
                FileOutputStream fos = new FileOutputStream(new File("saves.log"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(requests);
                System.out.println("File saved!");
                oos.close();
                fos.close();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return request;
    }

    private boolean checkFire(String[] args) throws IOException, ClassNotFoundException {

        if(args[0].contains("-fire"))
        {
            for(String arg : args)
            {
                if(arg.contains("-fire")) continue;
                int n = Integer.parseInt(arg);
                FileInputStream fis = new FileInputStream(new File("saves.log"));
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Request> requests = (ArrayList<Request>) ois.readObject();
                if(n <= requests.size())
                {
                    requests.get(n-1).sendRequest();
                }
                else
                {
                    System.out.println("Wrong input");
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkList(String[] args) {
        if(args[0].contains("-list"))
        {
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(new File("saves.log"));
                ois = new ObjectInputStream(fis);
                ArrayList<Request> requests = (ArrayList<Request>) ois.readObject();
                int i = 1;
                for(Request request : requests)
                {
                    System.out.println(i + ": " + request.toString());
                    i++;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally
            {
                try{
                    if(ois != null)ois.close();
                    if(fis != null)fis.close();
                }catch (Exception ignored){}
            }

            return true;
        }
        return false;
    }

    private boolean checkHelp(String[] args) {
        if(args[0].equals("--help") || args[0].equals("-H"))
        {
            System.out.println("Usage: <url> [options...]\n" +
                    "\n" +
                    "Set request Method:\n" +
                    "-M  --method <method>\n" +
                    "    default = GET\n" +
                    "    example: --method POST\n" +
                    "\n" +
                    "Send request headers:\n" +
                    "-H  --headers <headers>\n" +
                    "    example: –-headers “key1:value1;key2:value2”\n" +
                    "\n" +
                    "Save request output in file?\n" +
                    "-O  --output <file_name>\n" +
                    "    default = output_<time>.<content_type>\n" +
                    "    example: --output picture.png\n" +
                    "\n" +
                    "Set message body for request:\n" +
                    "-d  --data <form_data>\n" +
                    "    example: \"firstName=Ahmadreza&lastName=Kamali\"\n" +
                    "\n" +
                    "Save request?\n" +
                    "-S  --save\n" +
                    "\n" +
                    "show response headers?\n" +
                    "-i\n" +
                    "\n" +
                    "\n");
            return true;
        }
        return false;
    }

    public void sendCommand(String command){
        command = command.toLowerCase();
        if(command.equals("exit"))
        {
            System.exit(0);
        }
        if(command.equals("toggle full screen"))
        {
            if(fullScreen)return;
            fullScreen = true;
            guiComponents.leftPanel.setVisible(true);
            guiComponents.mainFrame.setExtendedState(MAXIMIZED_BOTH);
            return;
        }
        if(command.equals("toggle sidebar"))
        {
            if(!fullScreen)return;
            fullScreen = false;
            guiComponents.mainFrame.setSize(lastSize);
            guiComponents.leftPanel.setVisible(false);
            return;
        }
        if(command.toLowerCase().equals("options"))
        {
            OptionFrame optionFrame = new OptionFrame();
            optionFrame.setVisible(true);
            return;
        }
        if(command.toLowerCase().equals("help"))
        {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://support.insomnia.rest/category/19-using-insomnia"));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            return;
        }if(command.toLowerCase().equals("about"))
        {
            JOptionPane.showMessageDialog(GUIComponents.mainFrame,"Programmed by Ahmadrezadl\nThanks for using.");
        }

        sendCommand(command.split("\\s+"));


    }


    public void setGuiComponents(GUIComponents guiComponents)
    {
        this.guiComponents = guiComponents;
    }

    public void sendCommandFromGUI() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        String header = "";
        for(Component component : guiComponents.headerPanel.getComponents())
        {
            if(component instanceof HeaderRequest)
            {
                HeaderRequest h = (HeaderRequest) component;
                if(!h.getHeader().equals(":") && h.isAvailable())
                    header += h.getHeader() + ";";
            }
        }
        if(!header.equals(""))
        {
            header = "\"" + header.substring(0,header.length()-1) + "\"" ;
        }
        String command = guiComponents.url.getText()
                + " --method " + guiComponents.comboBox.getSelectedItem().toString();

        if(!header.equals(""))
        {
            command += " --headers " + header;
        }
        if(GUIComponents.followRedirect)
        {
            command += " -f ";
        }
        if(!guiComponents.formText.getText().equals(""))
        {
            command += " --data \"" + guiComponents.formText.getText() + "\"";
        }


        System.out.println(command);
        Request request = sendCommand(command.split("\\s+"));

        //age jpg ya png nabod raw data ro neshon bede
        if(!request.getOutHeaders().get("Content-Type").toString().toLowerCase().contains("png"))
        {
            guiComponents.outData.setText(request.res);
        }


        for(Component component : guiComponents.outHeaderPanel.getComponents())
        {
            if(component instanceof TwoField)
                guiComponents.outHeaderPanel.remove(component);
        }
        guiComponents.responseMessage.setText(request.getStatusCode() + " " + request.getStatus());
        if(request.getStatusCode() != 200)
        {
            guiComponents.responseMessage.setBackground(Color.RED);
        }
        else
        {
            guiComponents.responseMessage.setBackground(Color.GREEN);
        }
        guiComponents.responseTime.setText(request.time);
        request.getOutHeaders().forEach((k,v) -> guiComponents.outHeaderPanel.add(new TwoField(k,v.toString())));
        if(guiComponents.preview.getComponents().length != 0)
            guiComponents.preview.remove(0);
        if(guiComponents.outHeaderPanel.getHeaders().toLowerCase().contains("png"))
        {
            for(Component c : guiComponents.preview.getComponents())
            {
                guiComponents.preview.remove(c);
            }
            BufferedImage img = null;
            try {
                img = ImageIO.read(new URL(guiComponents.url.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel photo = new JLabel();
            photo.setIcon(new ImageIcon(img));
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayout(1,1));
            jPanel.setBackground(Color.white);
            jPanel.add(photo);
            guiComponents.preview.add(jPanel);
        }
        else
        {
            JEditorPane jEditorPane = new JEditorPane();
            jEditorPane.setContentType("text/html");
            jEditorPane.setEditable(false);
            try {
                jEditorPane.setPage(new URL(guiComponents.url.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            guiComponents.preview.add(jEditorPane);
        }

        guiComponents.preview.repaint();
        guiComponents.preview.revalidate();
        guiComponents.rightPanel.repaint();
        guiComponents.rightPanel.revalidate();
    }

    public ListItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ListItem currentItem) {
        this.currentItem = currentItem;
    }
}
