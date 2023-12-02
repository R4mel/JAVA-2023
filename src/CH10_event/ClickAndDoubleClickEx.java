package CH10_event;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ClickAndDoubleClickEx extends JFrame {
	public ClickAndDoubleClickEx() {
		setTitle("Click and DoubleClick 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.addMouseListener(new MyMouseListener());
		setSize(300, 200);
		setVisible(true);
	}

	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {// 더블 클릭
				int r = (int) (Math.random() * 256); // [0~255] 사이의
				int g = (int) (Math.random() * 256); // 정수 값 리턴
				int b = (int) (Math.random() * 256);
				Component component = (Component) e.getSource();
				component.setBackground(new Color(r, g, b));
			}
		}
	}

	public static void main(String[] args) {
		new ClickAndDoubleClickEx();
	}
}
