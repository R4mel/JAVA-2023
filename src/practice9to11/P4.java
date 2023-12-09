package practice9to11;


import javax.swing.*;
import java.awt.*;

public class P4 extends JFrame {
    class SouthPanel extends JPanel {
        SouthPanel() {
            setLayout(new GridLayout(5, 3, 1, 1));
            add(new JButton("1"));
            add(new JButton("2"));
            add(new JButton("3"));
            add(new JButton("4"));
            add(new JButton("5"));
            add(new JButton("6"));
            add(new JButton("7"));
            add(new JButton("8"));
            add(new JButton("9"));
            add(new JButton("*"));
            add(new JButton("0"));
            add(new JButton("#"));
            add(new JButton("Send"));
            add(new JButton(""));
            add(new JButton("End"));
        }
    }

    class CenterPanel extends JPanel {
        CenterPanel() {
            setLayout(null);
            setBackground(Color.WHITE);
            for (int i = 0; i < 10; i++) {
                JLabel l = new JLabel("*");
                l.setSize(20, 20);
                l.setForeground(Color.RED);
                l.setLocation((int) (Math.random() * 200 + 10), (int) (Math.random() * 200 + 10));
                add(l);
            }
        }
    }

    public P4() {
        setTitle("P4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentpane = getContentPane();
        contentpane.setLayout(new BorderLayout(5, 5));
        contentpane.add(new JTextField(), BorderLayout.NORTH);
        contentpane.add(new JButton("Clear"), BorderLayout.WEST);
        contentpane.add(new SouthPanel(), BorderLayout.SOUTH);
        contentpane.add(new CenterPanel(), BorderLayout.CENTER);
        setSize(350, 450); // 프레임 크기 300×200 설정
        setVisible(true); // 프레임을 화면에 출력
    }

    public static void main(String[] args) {
        new P4();
    }
}