import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InCome extends JFrame implements ActionListener {

	JPanel date_panel = new JPanel(); // 스크롤바와 날짜, 돈 합계가 들어갈 패널
	JPanel menu_panel = new JPanel(); // menu 패널
	JPanel button_panel = new JPanel(); // Button 패널

	Calendar cal = Calendar.getInstance();

	JButton left = new CircleButton("←");
	JButton date = new RoundedButton(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월");
	JButton right = new CircleButton("→");
	JButton result = new RoundedButton(""); // 합계 표시
	JButton plus = new CircleButton("+");
	JPanel list[] = new JPanel[31];
	JLabel money[] = new JLabel[31];
	JButton day[] = new RoundedButton[31];
	JButton expend = new JButton("지출내역");
	JButton income = new JButton("수입내역");
	JScrollPane sp = null;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dimes = toolkit.getScreenSize();
	int screenWidth = (int) dimes.getWidth();
	int screenHeight = (int) dimes.getHeight();
	Font menu = new Font("맑은 고딕", Font.BOLD, 15);
	Font font = new Font("맑은 고딕", Font.BOLD, 13);

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private void refresh(int max) {
		int i = 0, min = max;
		int y = cal.get(Calendar.YEAR);
		String m;
		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10)
			m = "0" + month;
		else
			m = String.valueOf(month);

		for (i = 0; i < max; i++) {
			if (i < 9)
				day[i].setText(y + "년 " + m + "월 0" + (i + 1) + "일");
			else
				day[i].setText(y + "년 " + m + "월 " + (i + 1) + "일");

			money[i].setText("₩0");
		}

		if (max < 31) {
			for (; min < 31; min++) {
				day[min].setText("-");
				money[min].setText("₩-");
			}
		}

		long total = 0L;

		String sql = "select cast(date_format(date, '%d') as signed), sum(pay) from money1 where sort like '수입' and date like concat('"
				+ (y + "-" + m) + "', '%') group by date";

		try {
			conn = DbConnect.makeConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				money[rs.getInt(1) - 1].setText("₩" + rs.getLong(2));

				total += rs.getLong(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		result.setText("₩" + total);
	}

	public InCome() {
		setTitle("수입");
		setLayout(null);
		setSize(500, 600);
		Image img = toolkit.getImage("money.png");
		setIconImage(img);
		setLocation(screenWidth / 2 - 250, screenHeight / 2 - 300);
		getContentPane().setBackground(Color.white);
		setResizable(false); // 창 크기 고정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int y = 0, i = 0, max;

		date_panel.setLayout(null);
		menu_panel.setLayout(null);
		button_panel.setLayout(null);

		menu_panel.setBounds(0, 0, 500, 40);
		button_panel.setBounds(0, 40, 500, 80);

		sp = new JScrollPane(date_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sp.setBorder(null);
		sp.setBounds(0, 130, 490, 370);

		button_panel.setBackground(Color.white);

		expend.setFont(menu);
		income.setFont(menu);
		expend.setBounds(0, 0, 250, 40);
		expend.setBorder(new TitledBorder(new LineBorder(Color.black, 0)));
		expend.setBackground(Color.white);
		income.setBounds(251, 0, 249, 40);
		income.setBorder(new TitledBorder(new LineBorder(Color.black, 0)));
		income.setBackground(Color.white);

		// 버튼 설정
		left.setBounds(10, 35, 30, 30);
		date.setBounds(45, 35, 90, 30);
		right.setBounds(140, 35, 30, 30);
		result.setBounds(200, 35, 280, 30);
		left.setFont(font);
		right.setFont(font);
		date.setFont(font);
		result.setFont(font);

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

			if (i < 10)
				day[i] = new RoundedButton(
						cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 0" + (i + 1) + "일");
			else
				day[i] = new RoundedButton(
						cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 " + (i + 1) + "일");
			day[i].setBounds(10, 2, 125, 30);
			day[i].setFont(font);
			day[i].addActionListener(this);
			money[i] = new JLabel("₩0", JLabel.RIGHT);
			money[i].setFont(font);
			money[i].setBounds(360, 2, 100, 30);

			list[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
			list[i].add(day[i]);
			list[i].add(money[i]);
			list[i].setBackground(Color.white);
		}

		// 1일 ~ 최대 일 표시
		for (i = 0; i < max; i++) {
			date_panel.add(list[i]);
			list[i].setBounds(0, y, 500, 35);
			y += 35;
		}
		refresh(max);

		plus.setBounds(sp.getX() + sp.getWidth() - 40, sp.getY() + sp.getHeight() + 10, 40, 40);
		plus.setFont(font);

		button_panel.add(left);
		button_panel.add(date);
		button_panel.add(right);
		button_panel.add(result);
		menu_panel.add(expend);
		menu_panel.add(income);

		add(menu_panel);
		add(button_panel);
		add(sp);
		add(plus);

		plus.addActionListener(this);
		expend.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		date.addActionListener(this);

		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void clickDate() {
		Date d = new Date();
		date.setText((d.getYear() + 1900) + "년 " + (d.getMonth() + 1) + "월");
		cal.setTime(d);

		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		refresh(max);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == expend) {
			dispose(); // 창 닫기
			new Expend().setVisible(true); // 수입 창 띄우기
		} else if (e.getSource() == left) {
			cal.add(Calendar.MONTH, -1);

			int month = cal.get(Calendar.MONTH) + 1;

			if (month < 1) {
				cal.add(Calendar.YEAR, -1);
			}

			int year = cal.get(Calendar.YEAR);

			String now_month;
			if (month < 10)
				now_month = "0" + month + "월";
			else
				now_month = month + "월";

			date.setText(year + "년 " + now_month);

			int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			refresh(max);
		} else if (e.getSource() == right) {
			cal.add(Calendar.MONTH, 1);

			int month = cal.get(Calendar.MONTH) + 1;

			if (month > 12) {
				cal.add(Calendar.YEAR, 1);
			}

			int year = cal.get(Calendar.YEAR);
			String now_month;
			if (month < 10)
				now_month = "0" + month + "월";
			else
				now_month = month + "월";

			date.setText(year + "년 " + now_month);

			int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			refresh(max);
		} else if (e.getSource() == date) {
			clickDate();
		} else if (e.getSource() == plus) {
			new AddData("수입", this);
			this.setEnabled(false);
		} else {
			for (int i = 0; i < 31; i++) {
				if (e.getSource() == day[i]) {
					new List(e.getActionCommand(), "수입", this);
				}
			}
		}
	}
}