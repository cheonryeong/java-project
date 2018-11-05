import java.awt.Container;
import java.awt.Font;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InCome extends JFrame { // JFrame 상속

	JPanel jp = new JPanel();
	
	JButton left = new JButton("←");
	JLabel date = new JLabel("2018년 11월");
	JButton right = new JButton("→");
	JLabel day[] = new JLabel[31];

	Font dayFont = new Font("돋움",Font.BOLD,15);
	
	public InCome() // 생성자
	{
		setSize(500,800);
		setVisible(true);

		setTitle("수입목록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container d = getContentPane();
		
		int x = 30, y=70, i;
		
		for(i=0;i<31;i++)
		{
			day[i] = new JLabel(Objects.toString("11월 " + (i+1) + "일"));
			day[i].setFont(dayFont);
		}
			
		d.setLayout(null);
		d.add(left);
		d.add(date);
		d.add(right);

		left.setBounds(100,20,50,50);
		date.setBounds(170,20,120,50);
		date.setVerticalAlignment(date.CENTER);
		date.setHorizontalAlignment(date.CENTER);
		right.setBounds(310,20,50,50);
		
		for(i=0;i<31;i++)
		{
			d.add(day[i]);
			day[i].setBounds(x,y,80,50);
			y += 25;
		}
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InCome();
	}
}