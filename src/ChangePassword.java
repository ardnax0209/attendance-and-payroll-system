import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textCPpassword;
	private JTextField textCPpass;
	Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword("");
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
	public ChangePassword(String username) {
		try {
			//initiate connection with database
			conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 255));
		panel.setForeground(new Color(255, 204, 255));
		panel.setBounds(0, 0, 468, 322);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCPuser = new JLabel("Username");
		lblCPuser.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCPuser.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPuser.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCPuser.setBounds(48, 58, 117, 25);
		panel.add(lblCPuser);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(199, 58, 171, 25);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText(username);
		
		JLabel lblCPpassword = new JLabel("New Password");
		lblCPpassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPpassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCPpassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCPpassword.setBounds(48, 107, 117, 25);
		panel.add(lblCPpassword);
		
		textCPpassword = new JTextField();
		textCPpassword.setColumns(10);
		textCPpassword.setBounds(199, 108, 171, 25);
		panel.add(textCPpassword);
		
		textCPpass = new JTextField();
		textCPpass.setColumns(10);
		textCPpass.setBounds(199, 157, 171, 25);
		panel.add(textCPpass);
		
		JLabel lblCPpass = new JLabel("Confirm Password");
		lblCPpass.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPpass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCPpass.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCPpass.setBounds(48, 157, 117, 25);
		panel.add(lblCPpass);
		
		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newPass = textCPpassword.getText();
				if (newPass.equals(textCPpass.getText())) {
					//Initialize SQL statement
					Statement statement = null;
					String query = "UPDATE userdata SET Password= '"+newPass+"' WHERE Username='"+username+"'";
					
					try {
						statement = conn.createStatement();
						statement.execute(query);
						JOptionPane.showMessageDialog(null, "Password Updated!");
					} catch (SQLException e3) {
						JOptionPane.showMessageDialog(null, e3.toString());
					} finally {
						if (conn != null) {
					        try {
					            conn.close();
					        } catch (SQLException e1) { /* ignored */}
					    }
					}

					//Return to log in
				
					Login loginPage = new Login();
					loginPage.setVisible(true);
					
					ChangePassword.this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Password must have the same value!");
				}
				
				
			}
		});
		panel_1.setBounds(259, 231, 111, 25);
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("SAVE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNewLabel);
	}

}
