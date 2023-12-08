package practice9to11;

import javax.swing.*;
import java.awt.*;

public class P5 extends JFrame {
    Color[] color = {Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.GRAY,
            Color.PINK, Color.LIGHT_GRAY};

    class NorthPanel extends JPanel {
        NorthPanel() {
            setLayout(new FlowLayout());
            add(new Label("Happy New Year"));
        }
    }

    class CenterPanel extends JPanel {
        CenterPanel() {
            setBackground(Color.WHITE);
            setLayout(null);
            for (int i = 0; i < 10; i++) {
                JLabel l = new JLabel("*");
                l.setSize(20, 20);
                l.setForeground(Color.RED);
                l.setLocation((int) (Math.random() * 200 + 10), (int) (Math.random() * 200 + 10));
                add(l);
            }
        }
    }

    class SouthPanel extends JPanel {
        SouthPanel() {
            setLayout(new GridLayout(5, 2, 1, 1));
            for (int i = 0; i < color.length; i++) {
                JButton jButton = new JButton(String.valueOf(i));
                jButton.setBackground(color[i]);
                add(jButton);
            }
        }
    }

    public P5() {
        setTitle("P5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentpane = getContentPane();
        contentpane.setLayout(new BorderLayout(1, 1));
        contentpane.add(new NorthPanel(), BorderLayout.NORTH);
        contentpane.add(new CenterPanel(), BorderLayout.CENTER);
        contentpane.add(new SouthPanel(), BorderLayout.SOUTH);
        setSize(350, 450); // 프레임 크기 300×200 설정
        setVisible(true); // 프레임을 화면에 출력
    }

    public static void main(String[] args) {
        new P5();
    }
}