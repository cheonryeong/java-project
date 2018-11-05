import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ObjectAvoid extends JFrame { // JFrame 상속

	JPanel j1 = new JPanel();

	// 버튼의 크기조정은 setSize, 위치관리는 setLocation
	
	JButton start = new JButton("게임 시작"); // 시작 버튼 생성
	JButton exit = new JButton("게임 종료"); // 종료 버튼 생성

	public ObjectAvoid(String fileName) throws IOException {
		Image backgroundImage = ImageIO.read(new File("1.jpg"));
	}

	public ObjectAvoid()
	{
		
		Container c = getContentPane();

		setTitle("Object Avoid Game"); //게임판 타이틀
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 게임을 종료하면 윈도우에서도 종료

		c.setLayout(null);

		c.add(start); // 버튼 추가
		start.setBounds(210, 350, 90, 50); // 시작 버튼 위치 설정(위치 좌표 x, 위치 좌표 y, 가로, 세로)
		c.add(exit); // 버튼 추가
		exit.setBounds(340, 350, 90, 50); // 종료 버튼 위치 설정

		setSize(640,480); // 게임판 크기 (가로, 세로)
		setVisible(true); // 프레임 화면표시 여부
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ObjectAvoid(); // 게임판 생성
	}
}