import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class Dashboard extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private Image img_Logo = new ImageIcon(Login.class.getResource("images/payrolllogo.png")).getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
	private Image img_home = new ImageIcon(Login.class.getResource("images/home.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_Daily = new ImageIcon(Login.class.getResource("images/daily logs.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_Info = new ImageIcon(Login.class.getResource("images/employee info.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_Payroll = new ImageIcon(Login.class.getResource("images/payroll.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_Settings = new ImageIcon(Login.class.getResource("images/settings.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_Logout = new ImageIcon(Login.class.getResource("images/sign out.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
 
	private PanelHome panelHome;
	private PanelDailylogs panelDailylogs;
	private PanelEmployeeinfo panelEmployeeinfo;
	private PanelPayroll panelPayroll;
	private PanelSettings panelSettings;
	private PanelSignout panelSignout;
	
	private String uName = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Create new object for dashboard
					Dashboard frame = new Dashboard("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Dashboard(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1023, 583);
		
		uName = username;
		
		panelHome = new PanelHome();
		panelHome.setBounds(-12, -11, 712, 546);
		panelHome.setBackground(new Color(255, 182, 193));
		
		panelDailylogs = new PanelDailylogs();
		
		panelEmployeeinfo = new PanelEmployeeinfo();
		
		panelPayroll = new PanelPayroll();
		
		panelSettings = new PanelSettings(uName);
	
		panelSignout = new PanelSignout();
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBounds(0, 0, 289, 546);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbllogo = new JLabel("");
		lbllogo.setHorizontalAlignment(SwingConstants.CENTER);
		lbllogo.setIcon(new ImageIcon(img_Logo));
		lbllogo.setBounds(27, -29, 222, 192);
		contentPane.add(lbllogo);
		
		JPanel paneHome = new JPanel();
		paneHome.addMouseListener(new PanelButtonMouseAdapter(paneHome) {
			@Override
			public void mouseReleased(MouseEvent e) {
				menuClicked(panelHome);
			
			}
		});
		paneHome.setBorder(new LineBorder(new Color(255, 182, 193)));
		paneHome.setBackground(new Color(221, 160, 221));
		paneHome.setBounds(0, 173, 289, 42);
		contentPane.add(paneHome);
		paneHome.setLayout(null);
		
		JLabel lblHome = new JLabel("HOME");
		lblHome.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHome.setBounds(109, 11, 190, 20);
		paneHome.add(lblHome);
		
		JLabel imgHome = new JLabel("");
		imgHome.setIcon(new ImageIcon(img_home));
		imgHome.setBounds(28, 0, 60, 42);
		paneHome.add(imgHome);
		
		JPanel paneDailylogs = new JPanel();
		paneDailylogs.addMouseListener(new PanelButtonMouseAdapter(paneDailylogs){
			@Override
			public void mouseReleased(MouseEvent e) {
			menuClicked(panelDailylogs);
			
			}
		});
		paneDailylogs.setBorder(new LineBorder(new Color(255, 182, 193)));
		paneDailylogs.setBackground(new Color(221, 160, 221));
		paneDailylogs.setBounds(0, 215, 289, 42);
		contentPane.add(paneDailylogs);
		paneDailylogs.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DAILY LOGS");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(104, 11, 204, 19);
		paneDailylogs.add(lblNewLabel);
		
		JLabel imgDaily = new JLabel("");
		imgDaily.setIcon(new ImageIcon(img_Daily));
		imgDaily.setBounds(28, 0, 60, 42);
		paneDailylogs.add(imgDaily);
		
		JPanel paneEmployeeinfo = new JPanel();
		paneEmployeeinfo.addMouseListener(new PanelButtonMouseAdapter(paneEmployeeinfo){
			@Override
			public void mouseReleased(MouseEvent e) {
			menuClicked(panelEmployeeinfo);
			
			}
		});
		paneEmployeeinfo.setBorder(new LineBorder(new Color(255, 182, 193)));
		paneEmployeeinfo.setBackground(new Color(221, 160, 221));
		paneEmployeeinfo.setBounds(0, 257, 289, 42);
		contentPane.add(paneEmployeeinfo);
		paneEmployeeinfo.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("EMPLOYEE INFO");
		lblNewLabel_1.setBounds(104, 11, 175, 20);
		paneEmployeeinfo.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		JLabel imgInfo = new JLabel("");
		imgInfo.setIcon(new ImageIcon(img_Info));
		imgInfo.setBounds(30, 0, 45, 42);
		paneEmployeeinfo.add(imgInfo);
		
		JPanel panePayroll = new JPanel();
		panePayroll.addMouseListener(new PanelButtonMouseAdapter(panePayroll){
			@Override
			public void mouseReleased(MouseEvent e) {
			menuClicked(panelPayroll);
			
			}
		});
		panePayroll.setBorder(new LineBorder(new Color(255, 182, 193)));
		panePayroll.setBackground(new Color(221, 160, 221));
		panePayroll.setBounds(0, 299, 289, 42);
		contentPane.add(panePayroll);
		panePayroll.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("PAYROLL");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2.setBounds(87, 13, 137, 20);
		panePayroll.add(lblNewLabel_2);
		
		JLabel imgPayroll = new JLabel("New label");
		imgPayroll.setIcon(new ImageIcon(img_Payroll));
		imgPayroll.setBounds(26, 5, 34, 31);
		panePayroll.add(imgPayroll);
		
		JPanel paneSettings = new JPanel();
		paneSettings.addMouseListener(new PanelButtonMouseAdapter(paneSettings){
			@Override
			public void mouseReleased(MouseEvent e) {
			menuClicked(panelSettings);
			
			}
		});
		paneSettings.setBorder(new LineBorder(new Color(255, 182, 193)));
		paneSettings.setBackground(new Color(221, 160, 221));
		paneSettings.setBounds(0, 341, 289, 42);
		contentPane.add(paneSettings);
		paneSettings.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("SETTINGS");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_3.setBounds(99, 13, 121, 20);
		paneSettings.add(lblNewLabel_3);
		
		JLabel imgSettings = new JLabel("New label");
		imgSettings.setIcon(new ImageIcon(img_Settings));
		imgSettings.setBounds(26, 4, 34, 42);
		paneSettings.add(imgSettings);
		
		JPanel paneSignout = new JPanel();
		paneSignout.addMouseListener(new PanelButtonMouseAdapter(paneSignout){
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Sign Out" , "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
					new Login().setVisible(true);
					
					Dashboard.this.dispose();
				}
			}
		});
		paneSignout.setBackground(new Color(218, 112, 214));
		paneSignout.setBounds(50, 410, 177, 48);
		contentPane.add(paneSignout);
		paneSignout.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("LOG OUT");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_4.setBounds(54, 11, 147, 26);
		paneSignout.add(lblNewLabel_4);
		
		JLabel imgLogout = new JLabel("");
		imgLogout.setIcon(new ImageIcon(img_Logout));
		imgLogout.setBounds(10, 0, 63, 48);
		paneSignout.add(imgLogout);
		
		JPanel paneMainContent = new JPanel();
		paneMainContent.setBounds(299, 11, 700, 524);
		contentPane.add(paneMainContent);
		paneMainContent.setLayout(null);
		
		paneMainContent.add(panelHome);
		paneMainContent.add(panelDailylogs);
		paneMainContent.add(panelEmployeeinfo);
		paneMainContent.add(panelPayroll);
		paneMainContent.add(panelSettings);
		paneMainContent.add(panelSignout);
		
		panelDailylogs.setVisible(false);
		panelEmployeeinfo.setVisible(false);
		panelPayroll.setVisible(false);
		panelSettings.setVisible(false);
		panelSignout.setVisible(false);
	}
		
		public void  menuClicked(JPanel panel	) {
			panelHome.setVisible(false);
			panelDailylogs.setVisible(false);
			panelEmployeeinfo.setVisible(false);
			panelPayroll.setVisible(false);
			panelSettings.setVisible(false);
			panelSignout.setVisible(false);
			
			panel.setVisible(true);
		}
		private class PanelButtonMouseAdapter extends MouseAdapter{
			
			JPanel panel;
			public PanelButtonMouseAdapter(JPanel panel) {
				this.panel = panel;
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(new Color(200, 100, 100));
				
			}
	public void mouseExited(MouseEvent e) {
		panel.setBackground(new Color(221, 160, 221));
				
			}
	public void mousePressed(MouseEvent e) {
		panel.setBackground(new Color(200, 100, 200));
		
	}
	public void mouseReleased(MouseEvent e) {
		panel.setBackground(new Color(221, 160, 221));
		
	}
	}

}
