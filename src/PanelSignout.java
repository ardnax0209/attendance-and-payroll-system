import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class PanelSignout extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelSignout() {
		setBackground(new Color(255, 182, 193));
		setBounds(0 ,0 ,700,524);
		setLayout(null);
		
		JLabel lblSignOut = new JLabel("SIGN OUT");
		lblSignOut.setBounds(87, 208, 312, 79);
		lblSignOut.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblSignOut);

	}

}
