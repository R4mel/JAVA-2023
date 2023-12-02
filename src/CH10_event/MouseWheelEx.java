package CH10_event;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MouseWheelEx extends JFrame {
	public MouseWheelEx() {
		setTitle("Mouse 휠 이벤트 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		var c = getContentPane();
		var myMouseAdapter = new MyMouseAdapter();
		c.addMouseWheelListener(myMouseAdapter);
		setSize(250, 150);
		setVisible(true);
	}

	class MyMouseAdapter extends MouseAdapter {
		public void mouseWheelMoved(MouseWheelEvent e) {
			System.out.println("\nMouseWheelEvent: ");
			System.out.println("e.getPreciseWheelRotation(): " + e.getPreciseWheelRotation());
			System.out.println("e.getScrollAmount(): " + e.getScrollAmount());
			System.out.println("e.getScrollType(): " + e.getScrollType());
			System.out.println("e.getUnitsToScroll(): " + e.getUnitsToScroll());
			System.out.println("e.getWheelRotation(): " + e.getWheelRotation());
		}
	}

	public static void main(String[] args) {
		new MouseWheelEx();
	}
}
