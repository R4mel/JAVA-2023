package CH11_SwingComponent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JComponentEx extends JFrame {
	public JComponentEx() {
		super("JComponent의 공통 메소드 예제");
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		JButton b1 = new JButton("Magenta/Yellow Button");
		JButton b2 = new JButton(" Disabled Button ");
		JButton b3 = new JButton("getX(), getY()");

		b1.setBackground(Color.YELLOW); // 배경색 지정
		b1.setForeground(Color.MAGENTA); // 글자색 설정
		b1.setFont(new Font("Arial", Font.ITALIC, 20)); // Arial, 20픽셀 폰트 설정
		b2.setEnabled(false); // 버튼 비활성화
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				JComponentEx framEx = (JComponentEx) b1.getTopLevelAncestor();
				framEx.setTitle(button.getX() + "," + button.getY()); // 타이틀에 버튼 좌표 출력

			}
		});
		container.add(b1);
		container.add(b2);
		container.add(b3);
		setSize(260, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new JComponentEx();
	}
}
