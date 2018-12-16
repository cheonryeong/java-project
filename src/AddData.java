import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddData extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1200L;
	JFrame js = new JFrame("가계부");
	Calendar cal = Calendar.getInstance();
	JLabel title = new JLabel("추가 내역");
	JLabel tDay = new JLabel("일자");
	JLabel tCategory = new JLabel("결제");
	JLabel tContent = new JLabel("내역");
	JLabel tMoney = new JLabel("금액");
	JTextField cont = new JTextField(100);
	JTextField money = new JTextField(20);
	JButton now = new RoundedButton("임시 날짜");
	JButton save = new RoundedButton("추가");
	JButton cancel = new RoundedButton("취소");
	JComboBox<String> cate = new JComboBox<>();

	Font font = new Font("맑은 고딕", Font.BOLD, 13);
	Font tFont = new Font("맑은 고딕", Font.BOLD, 18);

	public AddData()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimes = toolkit.getScreenSize();
		int screenWidth = (int)dimes.getWidth();
		int screenHeight = (int)dimes.getHeight();
		setLayout(null);
		setSize(400,300);
		getContentPane().setBackground(Color.white);
		setLocation(screenWidth / 2 - 200 , screenHeight / 2 - 150);
		setUndecorated(true); // 창 위에 제목줄 없애기
		add(title);
		title.setFont(tFont);
		title.setBounds(170,25,100,20);
		//=======================================================================

		add(tDay);
		add(tCategory);
		add(tContent);
		add(tMoney);
		tDay.setBounds(10, 70, 80, 30);
		tDay.setFont(font);
		tCategory.setBounds(10, 110, 80, 30);
		tCategory.setFont(font);
		tContent.setBounds(10, 150, 80, 30);
		tContent.setFont(font);
		tMoney.setBounds(10, 190, 80, 30);
		tMoney.setFont(font);

		add(now);
		add(cont);
		add(money);
		add(cate);
		cate.addItem("현금");
		cate.addItem("카드");
		cate.setBounds(120,110,100,30);
		cate.setFont(font);
		cate.setBackground(Color.white);
		now.setBounds(120,70,100,30);
		now.setFont(font);
		cont.setBounds(120,150,250,30);
		cont.setFont(font);
		money.setBounds(120,190,250,30);
		money.setFont(font);


		add(save);
		add(cancel);
		save.setFont(font);
		cancel.setFont(font);
		save.setBounds(100,240,100,30);
		cancel.setBounds(210,240,100,30);

		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) // ActionListener() 필수 메소드
			{
				dispose();
			}
		});

		setVisible(true);
	}
}