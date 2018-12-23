import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class List extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4843022422695129767L;

	JFrame frame = null;
	JPanel list[];
	Calendar cal = Calendar.getInstance();
	JButton date = new CircleButton("");
	Font font = new Font("맑은 고딕", Font.BOLD, 15);
	Font subFont = new Font("맑은 고딕", Font.BOLD, 13);
	JLabel content[];
	String[] option;
	String sort = "";
	long pk[] = new long[10];
	int column;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dimes = toolkit.getScreenSize();
	int screenWidth = (int) dimes.getWidth();
	int screenHeight = (int) dimes.getHeight();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List(String day, String sort, JFrame frame) {
		this.frame = frame;
		this.sort = sort;

		setTitle("내역");
		setLayout(null);
		setSize(500, 600);
		setLocation(screenWidth / 2 - 250, screenHeight / 2 - 300);
		getContentPane().setBackground(Color.white);
		setResizable(false); // 창 크기 고정
		setVisible(true);

		option = new String[3];
		option[0] = "수정";
		option[1] = "삭제";
		option[2] = "취소";

		date.setText(day + " " + sort + " 내역");
		date.setBounds(7, 10, 480, 40);
		date.setFont(font);
		add(date);

		executeDB(day);
		locationResult();
	}

	private void locationResult() {
		int y = 60;
		for (int i = 0; i < column; i++) {
			list[i] = new JPanel();
			list[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
			add(list[i]);
			list[i].setBounds(0, y, 500, 30);
			list[i].add(content[i]);
			list[i].setBackground(Color.white);
			list[i].addMouseListener(this);
			content[i].setBounds(0, 0, 500, 60);
			content[i].setFont(subFont);
			y += 31;
		}
	}

	private void executeDB(String day) {
		String sql = "select * from money1 where sort like '" + sort + "' and " + "date = "
				+ day.replaceAll("[^0-9]", "");
		try {
			conn = DbConnect.makeConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			rs.last();
			column = rs.getRow();

			list = new JPanel[column];
			content = new JLabel[column];

			rs.beforeFirst();

			int j = 0;

			while (rs.next()) {
				content[j] = new JLabel();
				pk[j] = rs.getInt(1);
				content[j].setText(rs.getString(4) + "            |            " + rs.getString(5)
						+ "            |            \\" + rs.getString(6));
				j++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	}

	public void change() {
		int i = 0;
		String sql = "select * from money1 where sort like '" + sort + "' and " + "date = "
				+ date.getText().replaceAll("[^0-9]", "");

		try {
			conn = DbConnect.makeConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pk[i] = rs.getInt(1);
				content[i].setText("종류: " + rs.getString(4) + " | " + "내역: " + rs.getString(5) + " | " + "금액: ₩"
						+ rs.getString(6));
				i++;
			}

			for (int j = i; j < column; j++) {
				pk[j] = 0;
				content[j].setText("");
				list[j].setVisible(false);
			}
		} catch (SQLException e) {
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
	}

	public void delete(int c) {
		if (list.length == 1) {
			list[c].setVisible(false);
		} else if (list.length > 0) {
			int y = list[c].getY();
			list[c].setVisible(false);
			c++;
			for (; c < list.length; c++) {
				list[c].setBounds(0, y, 500, 30);
				y += 31;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < list.length; i++) {
			if (e.getSource() == list[i]) {
				int job = JOptionPane.showOptionDialog(this, "작업을 선택하세요.", "작업", JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				Connection con = DbConnect.makeConnection();
				if (job == 0) {
					new AddData(sort, pk[i], this, frame);
				} else if (job == 1) {
					String sql = "delete from money1 where num =" + pk[i];

					try {
						PreparedStatement pstmt = con.prepareStatement(sql);
						if (pstmt.executeUpdate(sql) == 1) {
							JOptionPane.showMessageDialog(null, "삭제 성공");
							delete(i);
						} else {
							JOptionPane.showMessageDialog(null, "삭제 실패");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					if (sort.equals("수입")) {
						((InCome) frame).clickDate();
					} else {
						((Expend) frame).clickDate();
					}

				}

				break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < list.length; i++) {
			if (e.getSource() == list[i]) {
				list[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
				break;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < list.length; i++) {
			if (e.getSource() == list[i]) {
				list[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}