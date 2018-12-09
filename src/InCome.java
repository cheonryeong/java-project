import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InCome extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3952577344779588276L;
	JPanel date_panel = new JPanel(); // 스크롤바와 날짜, 돈 합계가 들어갈 패널
	JPanel menu_panel = new JPanel(); // menu 패널
	JPanel button_panel = new JPanel(); // Button 패널

	Calendar cal = Calendar.getInstance();

	JButton left = new CircleButton("←");
	JButton date = new RoundedButton(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월");
	JButton right = new CircleButton("→");
	JButton result = new RoundedButton(""); // 합계 표시
	JPanel list[] = new JPanel[31];
	JButton day[] = new RoundedButton[31];
	JButton expend = new JButton("지출내역");
	JButton income = new JButton("수입내역");
	JScrollPane sp = null;/* = new JScrollPane(date_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)*/;

	Font menu = new Font("맑은 고딕", Font.BOLD, 15);
	Font font = new Font("맑은 고딕", Font.BOLD, 13);

	public InCome() {
		setTitle("수입");
		setLayout(null);
		setSize(500, 600);
		getContentPane().setBackground(Color.white);
		setResizable(false); // 창 크기 고정

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int y = 0, i, max;

		date_panel.setLayout(null);
		menu_panel.setLayout(null);
		button_panel.setLayout(null);

		menu_panel.setBounds(0, 0, 500, 40);
		button_panel.setBounds(0, 40, 500, 40);
		
		sp = new JScrollPane(date_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		sp.setBounds(0, 130, 490, 370);

		date_panel.setBackground(Color.red);
		button_panel.setBackground(Color.blue);

		button_panel.add(left);
		button_panel.add(date);
		button_panel.add(right);
		button_panel.add(result);

		menu_panel.add(expend);
		menu_panel.add(income);

		expend.setFont(menu);
		income.setFont(menu);
		expend.setBounds(0, 0, 250, 40);
		expend.setBorder(new TitledBorder(new LineBorder(Color.black, 1)));
		expend.setBackground(Color.white);
		income.setBounds(251, 0, 249, 40);
		income.setBorder(new TitledBorder(new LineBorder(Color.black, 1)));
		income.setBackground(Color.white);

		// 버튼 설정
		left.setBounds(10, 5, 30, 30);
		date.setBounds(45, 5, 90, 30);
		right.setBounds(140, 5, 30, 30);
		result.setBounds(200, 5, 280, 30);
		
		max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Dimension size = new Dimension(date_panel.getWidth(), 35 * max);
		
		date_panel.setPreferredSize(size);
		
		sp.getVerticalScrollBar().setUnitIncrement(18);
		sp.setViewportView(date_panel);

		// 그 달의 마지막 일까지 저장
		for (i = 0; i < max; i++) {
			list[i] = new JPanel();
			list[i].setLayout(null);
			list[i].setSize(500, 80);

			day[i] = new RoundedButton(cal.get(Calendar.MONTH) + 1 + "월 " + (i+1) + "일");
			day[i].setBounds(10, 2, 100, 30);

			list[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
			list[i].add(day[i]);
			list[i].setBackground(Color.white);
		}

		// 1일 ~ 최대 일 표시
		for (i = 0; i < max ; i++) {
			date_panel.add(list[i]);
			list[i].setBounds(0, y, 500, 35);
			y += 35;
		}
		
		expend.addActionListener(new ActionListener() // 지출 버튼 누르면 지출창 띄우기
		{
			public void actionPerformed(ActionEvent e) // ActionListener() 필수 메소드
			{
				dispose(); // 창 닫기
				new Expend().setVisible(true); // 새 창 띄우기
			}
		});
		
		add(menu_panel);
		add(button_panel);
		add(sp);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InCome();
	}
}