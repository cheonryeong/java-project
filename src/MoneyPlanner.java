import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MoneyPlanner extends JFrame {

	JPanel back = new JPanel();

	// 버튼의 크기조정은 setSize, 위치관리는 setLocation

	JButton start = new JButton("시작"); // 시작 버튼 생성
	JButton exit = new JButton("종료"); // 종료 버튼 생성

	JLabel mainText = new JLabel("가계부");
	Font f1 = new Font("돋움",Font.BOLD,40);

	public MoneyPlanner() // 시작 화면
	{	
		setSize(500,800); // 화면 크기 (가로, 세로)
		setVisible(true); // 프레임 화면표시 여부

		Container c = getContentPane();

		setTitle("MoneyPlanner"); //제목
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료하면 윈도우에서도 종료

		c.add(mainText); // 가계부 글씨 출력
		mainText.setFont(f1);
		mainText.setBounds(185,70,300,300);

		c.setLayout(null); // 버튼 배치를 위해 필요 - 없으면 화면 전체가 버튼이 됨

		c.add(start); // 시작 버튼 추가
		start.setBounds(145,400,90,50);

		start.addActionListener(new ActionListener() // 종료버튼 누르면 종료
				{
			public void actionPerformed(ActionEvent e) // ActionListener() 필수 메소드
			{
				dispose(); // 창 닫기
				new InCome().setVisible(true); // 새 창 띄우기
			}
				});

		c.add(exit); // 종료 버튼 추가
		exit.setBounds(255,400,90,50); // 버튼 위치 설정(위치 좌표 x, 위치 좌표 y, 가로, 세로)

		exit.addActionListener(new ActionListener() // 종료버튼 누르면 종료
				{
			public void actionPerformed(ActionEvent e) // ActionListener() 필수 메소드
			{
				System.exit(0);    // 프로그램 종료
			}
				});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MoneyPlanner();
	}
}