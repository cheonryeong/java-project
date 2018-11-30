import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.Calendar;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InCome extends JFrame {

	JPanel jp = new JPanel(); // jp에 스크롤바
	JPanel mp = new JPanel(); // menu 패널
	JPanel bp = new JPanel(); // Button 패널

	Calendar cal = Calendar.getInstance();

	JButton left = new CircleButton("←");
	JButton date = new RoundedButton(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH)+1) + "월");
	JButton right = new CircleButton("→");
	JLabel day[] = new JLabel[31];
	JLabel expend = new JLabel("지출내역", JLabel.CENTER);
	JLabel income = new JLabel("수입내역", JLabel.CENTER);
	JScrollPane sp = new JScrollPane(jp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	Font menu = new Font("맑은 고딕", Font.BOLD, 15);
	Font font = new Font("맑은 고딕", Font.BOLD, 13);

	public InCome() // 생성자
	{
		setTitle("수입");
		setLayout(null);
		setSize(500,800);
		setResizable(false); // 창 크기 고정

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/////////////////////////////////////////////////////////////////////////////////
		int y = 5, i;

		Container d = getContentPane(); // JFrame 의 또 다른 자아쯤 Container

		// 31일까지 저장
		for(i=0;i<31;i++)
		{
			day[i] = new JLabel(Objects.toString((cal.get(Calendar.MONTH)+1) + "월 " + (i+1) + "일"));
			day[i].setFont(font);
		}

		jp.setLayout(null);
		mp.setLayout(null);
		bp.setLayout(null);


		sp.setBounds(0,160,490,600); // jp위치 = sp위치
		mp.setBounds(0,0,500,80);
		bp.setBounds(0,80,500,80);



		jp.setBackground(Color.red);
		mp.setBackground(Color.gray);
		bp.setBackground(Color.blue);

		d.add(mp);
		d.add(bp);

		bp.add(left);
		bp.add(date);
		bp.add(right);

		mp.add(expend);
		mp.add(income);
		expend.setFont(menu);
		income.setFont(menu);
		expend.setBounds(0,0,250,80);
		expend.setBorder(new TitledBorder(new LineBorder(Color.black,1)));
		income.setBounds(251,0,249,80);
		income.setBorder(new TitledBorder(new LineBorder(Color.black,1)));

		d.setBackground(Color.DARK_GRAY);
		d.add(sp);	

		// 버튼 설정
		left.setBounds(10,10,50,50);
		date.setBounds(70,10,120,50);
		right.setBounds(200,10,50,50);
		date.setVerticalAlignment(date.CENTER);
		date.setHorizontalAlignment(date.CENTER);

		// 1일 ~ 최대 31일 표시
		for(i=0;i<31;i++)
		{
			jp.add(day[i]);
			day[i].setBounds(30,y,80,50);
			y += 25;
		}

		setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InCome();
	}
}