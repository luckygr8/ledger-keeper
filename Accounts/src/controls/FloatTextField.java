package controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class FloatTextField extends JTextField {

    private boolean dot_found;

    public FloatTextField() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                boolean process_key = true;
                if (!Character.isDigit(ch)) {
                    if (ch == '.') {
                        if (dot_found) {
                            process_key = false;
                        } else {
                            dot_found = true;
                        }
                    } else {
                        process_key = false;
                    }
                }
                if(ch=='\b'){
                    dot_found = getText().contains(".");
                }
                if (!process_key) {
                    e.consume();
                }
            }
        });
    }

}
