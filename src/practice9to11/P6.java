package practice9to11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class P6 extends JFrame {
    private JTextField tf = new JTextField(10);
    private JComboBox<String> combo_coffee = new JComboBox<String>();
    private JComboBox<String> combo_tea = new JComboBox<String>();
    ButtonGroup group = new ButtonGroup();
    JRadioButton radio_coffee = new JRadioButton("Coffee");
    JRadioButton radio_tea = new JRadioButton("Tea");

    public P6() {
        setTitle("P6");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentpane = getContentPane();
        contentpane.setLayout(new GridLayout(3, 1, 1, 1));
        // =========== 첫번째 Panel 구성
        JPanel firstPanel = new JPanel();
        firstPanel.add(new Label("Menu"));

        // =========== 두번째 Panel 구성
        JPanel secondPanel = new JPanel();
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(Color.GRAY);
        group.add(radio_coffee);
        group.add(radio_tea);
        radioPanel.add(radio_coffee);
        radioPanel.add(radio_tea);


        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && radio_coffee.isSelected()) {
                    combo_coffee.addItem(tf.getText() + "\n");
                    tf.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && radio_tea.isSelected()) {
                    combo_tea.addItem(tf.getText() + "\n");
                    tf.setText("");
                }
            }
        });

        secondPanel.add(radioPanel);
        secondPanel.add(tf);

        // ============= 세 번째 Panel 구성
        JPanel thirdPanel = new JPanel();
        thirdPanel.add(combo_coffee);
        thirdPanel.add(combo_tea);
        // ============== 세 개의 Panel 추가 하기

        add(firstPanel);
        add(secondPanel);
        add(thirdPanel);
        radio_coffee.setSelected(true);
        setSize(450, 250); // 프레임 크기 설정
        setVisible(true); // 프레임을 화면에 출력
    }

    public static void main(String[] args) {
        new P6();
    }
}
