package practice9to11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExchangeRate extends JFrame {
    public ExchangeRate() {
        setTitle("환전 계산기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setBackground(Color.YELLOW);
        c.setLayout(new GridLayout(3, 2, 10, 10));

        c.add(new JLabel("원화"));
        c.add(new JLabel("100,000"));
        JButton dollar = new JButton("달러 계산");
        c.add(dollar);
        JButton euro = new JButton("유로 계산");
        c.add(euro);
        c.add(new JLabel("환전 금액"));
        JLabel jLabel = new JLabel("0");
        jLabel.setForeground(Color.RED);
        c.add(jLabel);

        dollar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("달러 계산")) {
                    jLabel.setText(100000 / 1200 + " USD");
                }
            }
        });

        euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("유로 계산")) {
                    jLabel.setText(100000 / 1350 + " EUR");
                }
            }
        });

        setSize(350, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ExchangeRate();
    }
}