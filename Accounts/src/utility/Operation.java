package utility;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class Operation {
    public static void openInternalFrame(JDesktopPane deskpane,JInternalFrame jif){
        deskpane.add(jif);
        jif.setVisible(true);
    }
}
