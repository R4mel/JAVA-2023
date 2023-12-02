package CH10_event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IndepClassListener extends JFrame {
    public IndepClassListener() {
        setTitle("Action 이벤트 리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JButton btn = new JButton("Action");
        btn.addActionListener(new MyActionListener()); // Action 리스너 달기
        c.add(btn);

        setSize(350, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new IndepClassListener();
    }
}

class MyActionListener implements ActionListener { // ActionListener라는
    @Override
    public void actionPerformed(ActionEvent e) { // 인터페이스의 추상함수 구현
        JButton b = (JButton) e.getSource();
        if (b.getText().equals("Action")) // String b.getText()
            b.setText("액션");
        else
            b.setText("Action");
    }
}
