import javax.swing.*;
import java.awt.*;

public class OutHeaderPanel extends JPanel {
    public OutHeaderPanel(){
        super();
    }
    public void setHeaders(String headers,GUIComponents guiComponents) {
        for (Component component : this.getComponents())
        {
            if (component instanceof TwoField)
            {
                this.remove(component);
            }
        }
        if(!headers.equals(""))
        {
            for(String s : headers.split(";"))
            {
                TwoField headerRequest=null;
                try
                {
                    headerRequest = new TwoField(s.split(":")[0],s.split(":")[1]);
                    this.add(headerRequest);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {

                }


            }
        }
    }
    public String getHeaders()
    {
        String ans = "";
        for(Component component : this.getComponents())
        {
            if(component instanceof TwoField)
            {
                TwoField h = (TwoField) component;
                ans+=h.getText()+";";
            }
        }
        if(!ans.equals(""))
            ans = ans.substring(0,ans.length()-1);
        return ans;
    }
}
