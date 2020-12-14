import javax.swing.*;
import java.awt.*;

public class InHeaderPanel extends JPanel {
    public InHeaderPanel(){
        super();
    }
    public void setHeaders(String headers,GUIComponents guiComponents) {
        for (Component component : this.getComponents())
        {
            if (component instanceof HeaderRequest || component instanceof JButton)
            {
                this.remove(component);
            }
        }
        if(!headers.equals(""))
        {
            for(String s : headers.split(";"))
            {
                HeaderRequest headerRequest = new HeaderRequest(this,guiComponents);
                this.add(headerRequest);
                headerRequest.setHeader(s);
            }
        }
        this.add(guiComponents.addHeaderButton);
    }
    public String getHeaders()
    {
        String ans = "";
        for(Component component : this.getComponents())
        {
            if(component instanceof HeaderRequest)
            {
                HeaderRequest h = (HeaderRequest) component;
                ans+=h.getHeader()+";";
            }
        }
        if(!ans.equals(""))
        ans = ans.substring(0,ans.length()-1);
        return ans;
    }
}
