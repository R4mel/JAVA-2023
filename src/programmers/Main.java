package programmers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
    private JCheckBox[] fruits = new JCheckBox[3];
    private String[] names = {"사과", "배", "체리"};
    private JLabel sumLabel;

    public Main() {
        setTitle("체크박스와 ItemEvent 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JLabel("사과 100원, 배 500원, 체리 20000원"));
        MyItemListener listener = new MyItemListener();
        for (int i = 0; i < fruits.length; i++) {
            fruits[i] = new JCheckBox(names[i]);
            fruits[i].setBorderPainted(true);
            c.add(fruits[i]);
            fruits[i].addItemListener(listener);
        }
        sumLabel = new JLabel("현재 0 원 입니다.");
        c.add(sumLabel);
        setSize(250, 200);
        setVisible(true);
    }

    class MyItemListener implements ItemListener {
        private int sum = 0; // 가격의 합, 공유변수임

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == fruits[0])
                    sum += 100; // if (fruits[0].isSelected())
                else if (e.getItem() == fruits[1])
                    sum += 500;
                else
                    sum += 20000;
            } else { // e.getStateChange() == ItemEvent.DESELECTED
                if (e.getItem() == fruits[0])
                    sum -= 100; // if (fruits[0].isSelected())
                else if (e.getItem() == fruits[1])
                    sum -= 500;
                else
                    sum -= 20000;
            }
            sumLabel.setText("현재 " + sum + "원 입니다.");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}