import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class PanelSettings extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelSettings() {
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		JLabel lblSetting = new JLabel("SETTING");
		lblSetting.setBounds(92, 191, 238, 81);
		lblSetting.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblSetting);

	}

}
