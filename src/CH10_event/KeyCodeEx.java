package CH10_event;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class KeyCodeEx extends JFrame {
	private JLabel la = new JLabel();

	public KeyCodeEx() {
		setTitle("Key Code 예제 : F1키:초록색, % 키 노란색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();

		c.addKeyListener(new MyKeyListener());
		c.add(la);

		setSize(300, 150);
		setVisible(true);

		c.setFocusable(true);
		c.requestFocus();
		// 키 입력을 받을 수 있도록 포커스를 준다.
	}

	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			la.setText(e.getKeyText(e.getKeyCode()) + "키가 입력되었음");
			// % 키를 입력하면 e.getKeyCode()는 VK_5가 리턴
			if (e.getKeyChar() == '%')
				KeyCodeEx.this.setBackground(Color.YELLOW);
			// % 키를 판별하기 위해 e.getKeyChar() 호출
			else if (e.getKeyCode() == KeyEvent.VK_F1)
				KeyCodeEx.this.setBackground(Color.GREEN);
			// 키 입력을 받을 수 있도록 포커스를 준다. KeyEvent.VK_F1 값과 비교
		}
	}

	public static void main(String[] args) {
		new KeyCodeEx();
	}
}