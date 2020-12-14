import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainGUI {
    public static void main(String[] args) {
        try {
            File file = new File("settings.sav");
            Scanner sc = new Scanner(file);
            GUIComponents.exitOnClose = sc.next().equals("true");
            GUIComponents.followRedirect = sc.next().equals("true");
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            GUIComponents.exitOnClose = false;
            GUIComponents.followRedirect = false;
        }
        Logic logic = new Logic();
        logic.setGuiComponents(new GUIComponents(logic));

    }
}
