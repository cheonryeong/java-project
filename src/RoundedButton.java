import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class RoundedButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2545671134184483741L;

	public RoundedButton(String text) {
		super(text);
		decorate();
	}

	protected void decorate() {
		setBorderPainted(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		Graphics2D graphics = (Graphics2D) g;

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (getModel().isArmed())
			graphics.setColor(getBackground().darker());
		else if (getModel().isRollover()) {
			graphics.setColor(new Color(220, 220, 220));
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else
			graphics.setColor(new Color(240, 240, 240));

		graphics.fillRoundRect(0, 0, width, height, 30, 30); // 둥근 정도

		FontMetrics fontMetrics = graphics.getFontMetrics();

		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();

		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

		graphics.setColor(getForeground());

		graphics.setFont(getFont());

		graphics.drawString(getText(), textX, textY);

		graphics.dispose();

		super.paintComponent(g);
	}
}