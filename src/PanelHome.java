import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JProgressBar;

public class PanelHome extends JPanel {
	
	private Image img_employees = new ImageIcon(Login.class.getResource("images/crowd.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_percentage = new ImageIcon(Login.class.getResource("images/data.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_ontime = new ImageIcon(Login.class.getResource("images/clock.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_late = new ImageIcon(Login.class.getResource("images/warning.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(Login.class.getResource("images/woman.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);

	/**
	 * Create the panel.
	 */
	public PanelHome() {
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(11, 86, 169, 92);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblttlemployee = new JLabel("TOTAL EMPLOYEE");
		lblttlemployee.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblttlemployee.setBounds(10, 65, 149, 20);
		panel.add(lblttlemployee);
		
		JLabel lblTotalEmployee = new JLabel("");
		lblTotalEmployee.setIcon(new ImageIcon (img_employees));
		lblTotalEmployee.setBounds(110, 11, 45, 45);
		panel.add(lblTotalEmployee);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(181, 86, 169, 92);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAbsent = new JLabel("ABSENT TODAY");
		lblAbsent.setBounds(10, 65, 134, 16);
		lblAbsent.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_1.add(lblAbsent);
		
		JLabel lblOnTIME = new JLabel("");
		lblOnTIME.setIcon(new ImageIcon(img_percentage));
		lblOnTIME.setBounds(110, 11, 45, 45);
		panel_1.add(lblOnTIME);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(351, 86, 169, 92);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblOnTime = new JLabel("ON TIME TODAY");
		lblOnTime.setBounds(10, 65, 99, 16);
		lblOnTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(lblOnTime);
		
		JLabel lblOnTODAY = new JLabel("");
		lblOnTODAY.setIcon(new ImageIcon(img_ontime));
		lblOnTODAY.setBounds(110, 11, 45, 45);
		panel_2.add(lblOnTODAY);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(521, 86, 169, 92);
		add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblLateToday = new JLabel("LATE TODAY");
		lblLateToday.setBounds(10, 65, 109, 16);
		lblLateToday.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_3.add(lblLateToday);
		
		JLabel lblLate = new JLabel("");
		lblLate.setIcon(new ImageIcon (img_late));
		lblLate.setBounds(110, 11, 45, 45);
		panel_3.add(lblLate);
		
		JLabel lblMonthlyattendance = new JLabel("MONTHLY ATTENDANCE REPORT");
		lblMonthlyattendance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMonthlyattendance.setBounds(21, 200, 329, 14);
		add(lblMonthlyattendance);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(69, 258, 563, 214);
		add(progressBar);
		
		JLabel lblUser = new JLabel("");
		lblUser.setIcon(new ImageIcon(img_user));
		lblUser.setBounds(640, 11, 46, 45);
		add(lblUser);
		
		JLabel lblNewLabel = new JLabel("Chef Jelv");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(629, 48, 46, 14);
		add(lblNewLabel);
		setVisible(true);

	}
}
