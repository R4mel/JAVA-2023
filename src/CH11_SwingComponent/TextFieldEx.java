package CH11_SwingComponent;

import javax.swing.*;
import java.awt.*;

public class TextFieldEx extends JFrame {
	public TextFieldEx() {
		setTitle("텍스트필드 만들기 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		container.add(new JLabel("이름 "));
		container.add(new JTextField(20));
		container.add(new JLabel("학과 "));
		container.add(new JTextField("컴퓨터공학과", 20));
		container.add(new JLabel("주소 "));
		container.add(new JTextField("서울시 ...", 20));

		setSize(300, 150);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TextFieldEx();
	}
}
