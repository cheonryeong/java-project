import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddData extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3369279747649614249L;

	JLabel title = new JLabel("추가 내역");
	JLabel tDay = new JLabel("일자");
	JLabel tCategory = new JLabel("결제");
	JLabel tContent = new JLabel("내역");
	JLabel tMoney = new JLabel("금액");
	JTextField date = new JTextField(15);
	JTextField cont = new JTextField(100);
	JTextField money = new JTextField(20);
	JButton now = new RoundedButton("");
	JButton save = new RoundedButton("추가");
	JButton modify = new RoundedButton("수정");
	JButton cancel = new RoundedButton("취소");
	JComboBox<String> cate = new JComboBox<>();

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dimes = toolkit.getScreenSize();
	int screenWidth = (int) dimes.getWidth();
	int screenHeight = (int) dimes.getHeight();
	Font font = new Font("맑은 고딕", Font.BOLD, 13);
	Font tFont = new Font("맑은 고딕", Font.BOLD, 18);
	Connection conn = null;
	PreparedStatement pstmt;
	ResultSet rs;
	String sort;
	JFrame frame;
	List list;
	long pk;

	public AddData(String sort, JFrame frame) {
		this.sort = sort;
		this.frame = frame;

		setLayout(null);
		setSize(400, 300);
		getContentPane().setBackground(Color.white);
		setLocation(screenWidth / 2 - 200, screenHeight / 2 - 150);
		setUndecorated(true); // 창 위에 제목줄 없애기
		add(title);
		title.setFont(tFont);
		title.setBounds(170, 25, 100, 20);
		// =======================================================================

		tDay.setBounds(10, 70, 80, 30);
		tDay.setFont(font);
		tCategory.setBounds(10, 110, 80, 30);
		tCategory.setFont(font);
		tContent.setBounds(10, 150, 80, 30);
		tContent.setFont(font);
		tMoney.setBounds(10, 190, 80, 30);
		tMoney.setFont(font);

		cate.addItem("현금");
		cate.addItem("카드");
		cate.setBounds(120, 110, 100, 30);
		cate.setFont(font);
		cate.setBackground(Color.white);
		date.setBounds(120, 70, 100, 30);
		date.setFont(font);
		cont.setBounds(120, 150, 250, 30);
		cont.setFont(font);
		money.setBounds(120, 190, 250, 30);
		money.setFont(font);

		save.setFont(font);
		cancel.setFont(font);
		save.setBounds(100, 240, 100, 30);
		cancel.setBounds(210, 240, 100, 30);

		add(tDay);
		add(tCategory);
		add(tContent);
		add(tMoney);
		add(now);
		add(date);
		add(cont);
		add(money);
		add(cate);
		add(save);
		add(cancel);

		save.addActionListener(this);
		cancel.addActionListener(this);
		setVisible(true);
	}

	public AddData(String sort, long pk, List list, JFrame frame) {
		this.list = list;
		this.frame = frame;
		this.pk = pk;
		this.sort = sort;

		setLayout(null);
		setSize(400, 300);
		getContentPane().setBackground(Color.white);
		setLocation(screenWidth / 2 - 200, screenHeight / 2 - 150);
		setUndecorated(true); // 창 위에 제목줄 없애기
		add(title);
		title.setFont(tFont);
		title.setText("수정 내역");
		title.setBounds(170, 25, 100, 20);
		// =======================================================================

		tDay.setBounds(10, 70, 80, 30);
		tDay.setFont(font);
		tCategory.setBounds(10, 110, 80, 30);
		tCategory.setFont(font);
		tContent.setBounds(10, 150, 80, 30);
		tContent.setFont(font);
		tMoney.setBounds(10, 190, 80, 30);
		tMoney.setFont(font);

		cate.addItem("현금");
		cate.addItem("카드");
		cate.setBounds(120, 110, 100, 30);
		cate.setFont(font);
		cate.setBackground(Color.white);
		date.setBounds(120, 70, 100, 30);
		date.setFont(font);
		cont.setBounds(120, 150, 250, 30);
		cont.setFont(font);
		money.setBounds(120, 190, 250, 30);
		money.setFont(font);

		modify.setFont(font);
		cancel.setFont(font);
		modify.setBounds(100, 240, 100, 30);
		cancel.setBounds(210, 240, 100, 30);

		String sql = "select * from money1 where num = " + pk;
		try {
			conn = DbConnect.makeConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();

			int index;

			if (rs.getString(3).equals("현금"))
				index = 0;
			else
				index = 1;

			date.setText(rs.getString(2));
			cate.setSelectedIndex(index);
			cont.setText(rs.getString(5));
			money.setText(rs.getString(6));
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
			}
		}

		add(tDay);
		add(tCategory);
		add(tContent);
		add(tMoney);
		add(now);
		add(date);
		add(cont);
		add(money);
		add(cate);
		add(modify);
		add(cancel);

		modify.addActionListener(this);
		cancel.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			if (date.getText().equals("") || cont.getText().equals("") || money.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "날짜, 내역, 금액은 비울 수 없습니다.");
			} else if (money.getText().matches("^[0-9]*$") == false) {
				JOptionPane.showMessageDialog(null, "금액은 숫자여야합니다.");
			} else if (!(date.getText().replaceAll("[^0-9]", "").matches("[0-9]{6}")
					|| date.getText().replaceAll("[^0-9]", "").matches("[0-9]{8}"))) // 날짜를 받아 숫자를 제외한 문자를 없앤 후 비교
			{
				JOptionPane.showMessageDialog(null, "날짜에 숫자 6자리 혹은 8자리가 들어가야합니다.");
			} else {
				String sql = "insert into money1(date, sort, category, pay_option, pay) values('"
						+ date.getText().replaceAll("[^0-9]", "") + "','" + sort + "','"
						+ cate.getSelectedItem().toString() + "','" + cont.getText() + "','"
						+ Integer.parseInt(money.getText()) + "')";
				try {
					conn = DbConnect.makeConnection();
					pstmt = conn.prepareStatement(sql);

					pstmt.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
				}

				if (sort.equals("수입")) {
					((InCome) frame).setEnabled(true);
					((InCome) frame).clickDate();
				} else {
					((Expend) frame).setEnabled(true);
					((Expend) frame).clickDate();
				}

				dispose();
			}
		} else if (e.getSource() == modify) {
			String sql2 = "";

			if (date.getText().equals("") || cont.getText().equals("") || money.getText().equals(""))
				JOptionPane.showMessageDialog(null, "날짜, 내역, 금액은 비울 수 없습니다.");
			else if (money.getText().matches("^[0-9]*$") == false)
				JOptionPane.showMessageDialog(null, "금액은 숫자여야합니다.");
			else if (!(date.getText().replaceAll("[^0-9]", "").matches("[0-9]{6}")
					|| date.getText().replaceAll("[^0-9]", "").matches("[0-9]{8}"))) // 날짜를 받아 숫자를 제외한 문자를 없앤 후 비교
				JOptionPane.showMessageDialog(null, "날짜에 숫자 6자리 혹은 8자리가 들어가야합니다.");
			else {
				if (date.getText().replaceAll("[^0-9]", "").length() == 6)
					sql2 = "update money1 set date ='20" + date.getText().replaceAll("[^0-9]", "") + "', category = '"
							+ cate.getSelectedItem() + "', pay_option = '" + cont.getText() + "', pay = "
							+ Integer.parseInt(money.getText()) + " where num = " + pk;
				else
					sql2 = "update money1 set date ='" + date.getText().replaceAll("[^0-9]", "") + "', category = '"
							+ cate.getSelectedItem() + "', pay_option = '" + cont.getText() + "', pay = "
							+ Integer.parseInt(money.getText()) + " where num = " + pk;

				try {
					conn = DbConnect.makeConnection();
					pstmt = conn.prepareStatement(sql2);

					pstmt.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
				}

				list.change();

				if (sort.equals("수입")) {
					((InCome) frame).clickDate();
				} else {
					((Expend) frame).clickDate();
				}
				dispose();
			}
		} else if (e.getSource() == cancel) {
			if (sort.equals("수입")) {
				((InCome) frame).setEnabled(true);
			} else {
				((Expend) frame).setEnabled(true);
			}
			dispose();
		}
	}
}