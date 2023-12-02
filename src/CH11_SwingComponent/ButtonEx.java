package CH11_SwingComponent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonEx extends JFrame {
	public ButtonEx() {
		setTitle("이미지 버튼 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		ImageIcon normalIcon = new ImageIcon("");
		ImageIcon rolloverIcon = new ImageIcon("");
		ImageIcon pressedIcon = new ImageIcon("");

		JButton btnButton = new JButton("call~~", normalIcon);
		btnButton.setPressedIcon(pressedIcon);
		btnButton.setRolloverIcon(rolloverIcon);

		container.add(btnButton);
		setSize(250, 150);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ButtonEx();
	}
}
