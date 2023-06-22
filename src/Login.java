import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import org.sqlite.SQLiteDataSource;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;

public class Login extends JFrame {
	
	private Image img_username = new ImageIcon(Login.class.getResource("images/programmer.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_password = new ImageIcon(Login.class.getResource("images/padlock.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_login = new ImageIcon(Login.class.getResource("images/log-in.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);

	Connection conn;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JFrame frame;
	private JTextField txtUserfield;
	private JPasswordField txtPasswordfield;
	private JLabel lblLoginmessage = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		try {
			//initiate connection with database
			String url = "jdbc:sqlite:sjDatabase.db";
			//SQLiteDataSource ds = new SQLiteDataSource();
			//ds.setUrl(url);
			conn = DriverManager.getConnection(url);
			
			//check if there's already a table
			Statement statement =null;
			ResultSet resultSet = null; //update
			String query ="CREATE TABLE IF NOT EXISTS \"userdata\" (\r\n"
					+ "	\"Username\"	TEXT,\r\n"
					+ "	\"Password\"	TEXT,\r\n"
					+ "	\"SecAnswer1\"	TEXT,\r\n"
					+ "	\"SecAnswer2\"	TEXT,\r\n"
					+ "	\"employeeNum\"	TEXT,\r\n"
					+ "	PRIMARY KEY(\"employeeNum\")\r\n"
					+ ");";
			
			try
			{
				statement = conn.createStatement();
				statement.executeQuery(query);
			}
			catch (Exception e1){
				e1.printStackTrace();
				//JOptionPane.showMessageDialog(null,e1); update or check
			}
			
			//create employeeInfo table
			query ="CREATE TABLE IF NOT EXISTS \"employeeInfo\" (\r\n"
					+ "	\"EMPLOYEENUM\"	TEXT,\r\n"
					+ "	\"LASTNAME\"	TEXT NOT NULL,\r\n"
					+ "	\"FIRSTNAME\"	TEXT NOT NULL,\r\n"
					+ "	\"MIDDLENAME\"	TEXT,\r\n"
					+ "	\"ADDRESS\"	TEXT NOT NULL,\r\n"
					+ "	\"CONTACTNUM\"	INTEGER NOT NULL UNIQUE,\r\n"
					+ "	\"AGE\"	TEXT NOT NULL,\r\n"
					+ "	\"GENDER\"	TEXT NOT NULL,\r\n"
					+ "	\"EMAIL\"	TEXT,\r\n"
					+ "	\"CIVILSTATUS\"	TEXT NOT NULL,\r\n"
					+ "	\"BIRTHDAY\"	TEXT,\r\n"
					+ "	\"DATEHIRED\"	TEXT DEFAULT CURRENT_TIMESTAMP,\r\n"
					+ "	\"STATUS\"	TEXT NOT NULL DEFAULT 'ACTIVE',\r\n"
					+ "	\"DAILYSALARY\"	TEXT DEFAULT 375,\r\n"
					+ "	PRIMARY KEY(\"EMPLOYEENUM\")\r\n"
					+ ");";
			
			try
			{
				statement = conn.createStatement();
				statement.executeQuery(query);
			}
			catch (Exception e1){
				e1.printStackTrace();
			}
			
			//create payroll table
			query ="CREATE TABLE IF NOT EXISTS \"payrollInfo\" (\r\n"
					+ "	\"employeeNum\"	TEXT NOT NULL,\r\n"
					+ "	\"dateIn\"	TEXT,\r\n"
					+ "	\"timeIn\"	TEXT,\r\n"
					+ "	\"dateOut\"	TEXT,\r\n"
					+ "	\"timeOut\"	TEXT,\r\n"
					+ "	\"totalHours\"	REAL,\r\n"
					+ "	\"ID\"	INTEGER,\r\n"
					+ "	PRIMARY KEY(\"ID\")\r\n"
					+ ");";
			
			try
			{
				statement = conn.createStatement();
				statement.executeQuery(query);
			}
			catch (Exception e1){
				e1.printStackTrace();
			}
			
			//check if there's already row in table
			PreparedStatement pst = conn.prepareStatement("SELECT employeeNum FROM userdata");
			resultSet = pst.executeQuery();
			String empNum = null;

			while (resultSet.next()) {
				empNum = resultSet.getString("employeeNum");
			}
			
			if (empNum != "0" || empNum == null || empNum == "" || empNum == " ") {
				//no data yet. insert initial data
				query = "INSERT INTO userdata ('Username', 'Password', 'SecAnswer1', 'SecAnswer2', 'employeeNum') VALUES ('admin','admin123','Simba','Bohol','0')";
				
				try
				{
					statement = conn.createStatement();
					statement.executeQuery(query);
				}
				catch (Exception e1){
					e1.printStackTrace();
					//JOptionPane.showMessageDialog(null,e1); update or check
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 428);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 342, 427);
		panel.setBackground(new Color(255, 204, 255));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(71, 104, 200, 200);
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/images/payrolllogo.png")));
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(381, 103, 232, 46);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtUserfield = new JTextField();
		txtUserfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUserfield.getText().equals("Username")) {
					txtUserfield.setText("");
				}
				else {
					txtUserfield.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtUserfield.getText().equals(""))
					txtUserfield.setText("Username");
			}
		});
		txtUserfield.setBorder(null);
		txtUserfield.setFont(new Font("Arial", Font.PLAIN, 12));
		txtUserfield.setText("Username");
		txtUserfield.setBounds(10, 10, 165, 25);
		panel_1.add(txtUserfield);
		txtUserfield.setColumns(10);
		
		JLabel lblIconusername = new JLabel("");
		lblIconusername.setBounds(185, 7, 37, 31);
		lblIconusername.setIcon(new ImageIcon(img_username));
		panel_1.add(lblIconusername);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(381, 172, 232, 46);
		contentPane.add(panel_1_1);
		panel_1_1.setLayout(null);
		
		txtPasswordfield = new JPasswordField();
		txtPasswordfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPasswordfield.getText().equals("Password")) {
				txtPasswordfield.setEchoChar('●');
				txtPasswordfield.setText("");
			}
				else {
					txtPasswordfield.selectAll();
				}}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtPasswordfield.getText().equals("")) {
					txtPasswordfield.setText("Password");
					txtPasswordfield.setEchoChar((char)0);
				}
			}
		});
		txtPasswordfield.setFont(new Font("Arial", Font.PLAIN, 12));
		txtPasswordfield.setBorder(null);
		txtPasswordfield.setEchoChar((char)0);
		txtPasswordfield.setText("Password");
		txtPasswordfield.setBounds(10, 10, 165, 25);
		panel_1_1.add(txtPasswordfield);
		
		JLabel lblIconpassword = new JLabel("");
		lblIconpassword.setBounds(185, 7, 37, 31);
		lblIconpassword.setIcon(new ImageIcon(img_password));
		panel_1_1.add(lblIconpassword);
		
		JPanel pnlBttnLOGIN = new JPanel();
		pnlBttnLOGIN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pnlBttnLOGIN.setBackground(new Color(200, 130, 200));
				
					String user = txtUserfield.getText();
					String pass = String.valueOf(txtPasswordfield.getPassword());
					boolean proceedFlag = false;
					
					Statement statement =null;
					ResultSet resultSet = null;
					String query ="SELECT * FROM userdata WHERE Username='"+user+"' AND Password='"+pass+"'";
					
					try
					{
						statement = conn.createStatement();
						resultSet = statement.executeQuery(query);
						
						if (resultSet.next()) {
							//Login Success
							
							JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL!");
							proceedFlag = true;
						}
						else {
							//Login Failed
							JOptionPane.showMessageDialog(null, "INCORRECT USERNAME OR PASSWORD!");
						}
						
					}
					catch (Exception e1){
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,e1);
					} finally {
						if (proceedFlag == true) {
							if (resultSet != null) {
						        try {
						        	resultSet.close();
						        } catch (SQLException e1) { /* ignored */}
						    }
						    if (conn != null) {
						        try {
						            conn.close();
						        } catch (SQLException e1) { /* ignored */}
						    }
						    
						    new Dashboard().setVisible(true);
							Login.this.dispose();
						}
					}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				pnlBttnLOGIN.setBackground(new Color(200, 130, 200));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pnlBttnLOGIN.setBackground(new Color(221, 160, 221));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				pnlBttnLOGIN.setBackground(new Color(231, 170, 231));
			}
		});
		pnlBttnLOGIN.setBounds(381, 287, 232, 46);
		pnlBttnLOGIN.setBackground(new Color(221, 160, 221));
		contentPane.add(pnlBttnLOGIN);
		pnlBttnLOGIN.setLayout(null);
		
		JLabel lblLOGIN = new JLabel("LOG IN");
		lblLOGIN.setFont(new Font("Arial", Font.BOLD, 15));
		lblLOGIN.setBounds(63, 16, 114, 23);
		pnlBttnLOGIN.add(lblLOGIN);
		
		JLabel lblIconlogin = new JLabel("");
		lblIconlogin.setBounds(150, 9, 37, 30);
		pnlBttnLOGIN.add(lblIconlogin);
		lblIconlogin.setIcon(new ImageIcon(img_login));
		
		
		lblLoginmessage.setForeground(Color.RED);
		lblLoginmessage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLoginmessage.setBounds(381, 261, 232, 15);
		contentPane.add(lblLoginmessage);
		
		JCheckBox chkbxpassword = new JCheckBox("Show password");
		chkbxpassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkbxpassword.isSelected()) {
					txtPasswordfield.setEchoChar((char)0);
				}
				else {
					txtPasswordfield.setEchoChar('●');
				}
			}
		});
		chkbxpassword.setFont(new Font("Arial", Font.BOLD, 12));
		chkbxpassword.setBounds(496, 225, 117, 15);
		contentPane.add(chkbxpassword);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(500, 340, 110, 15);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblForgotPass = new JLabel("Forget Password");
		lblForgotPass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					
					Login.this.dispose();
					
					//openForgetPassword
				
					ForgetPassword openAdmin = new ForgetPassword();
					openAdmin.setVisible(true);
				
			}
		});
		lblForgotPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblForgotPass.setBounds(5, 3, 100, 10);
		panel_2.add(lblForgotPass);
		
	}
}