package CH9_AWT_Swing;

import javax.swing.*;
import java.awt.*;

public class ContentPaneEx extends JFrame {
    public ContentPaneEx() {
        setTitle("ContentPane과 JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setBackground(Color.orange);
        c.setLayout(new FlowLayout());

        c.add(new JButton("OK"));
        c.add(new JButton("Cancel"));
        c.add(new JButton("Ignore"));

        setSize(300, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        ContentPaneEx frame = new ContentPaneEx();
        // main()함수는 바로 리턴함
    }
}
