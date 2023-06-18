import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

public class ForgetPassword extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textAns1;
	private JTextField textAns2;
	
	Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgetPassword frame = new ForgetPassword();
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
	public ForgetPassword() {
		try {
			//initiate connection with database
			conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 255));
		panel.setBounds(0, 0, 466, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lbluserName = new JLabel("Username");
		lbluserName.setBounds(43, 49, 105, 25);
		lbluserName.setHorizontalAlignment(SwingConstants.CENTER);
		lbluserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbluserName.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lbluserName);
		
		textField = new JTextField();
		textField.setBounds(165, 49, 177, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSquestion1 = new JLabel("What was the name of your first pet?");
		lblSquestion1.setBounds(22, 144, 208, 46);
		lblSquestion1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSquestion1.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblSquestion1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Question1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(lblSquestion1);
		
		JLabel lblSquestion = new JLabel("Security Question");
		lblSquestion.setBounds(43, 105, 105, 14);
		lblSquestion.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblSquestion);
		
		JLabel lblSquestion2 = new JLabel("What town was your mother born in?");
		lblSquestion2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSquestion2.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblSquestion2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Question2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblSquestion2.setBounds(22, 201, 208, 46);
		panel.add(lblSquestion2);
		
		textAns1 = new JTextField();
		textAns1.setBounds(240, 150, 197, 40);
		panel.add(textAns1);
		textAns1.setColumns(10);
		
		textAns2 = new JTextField();
		textAns2.setColumns(10);
		textAns2.setBounds(240, 205, 197, 40);
		panel.add(textAns2);
		
		JPanel panelSUBMIT = new JPanel();
		panelSUBMIT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String user = textField.getText();
				String answerOne = textAns1.getText();
				String answerTwo = textAns2.getText();
				boolean proceedFlag = false;
				
				Statement statement = null;
				String query ="SELECT * FROM userdata WHERE Username='"+user+"' AND SecAnswer2='"+answerTwo+"' AND SecAnswer1='"+answerOne+"'";
				ResultSet resultSet = null;
				
				try
				{
					statement = conn.createStatement();
					resultSet = statement.executeQuery(query);
					
					if (resultSet.next()) {
						//Result found
						//JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL!");
						
						proceedFlag = true;
					}
					else {
						//Login Failed
						JOptionPane.showMessageDialog(null, "INCORRECT DATA!");
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
					    
					    new ChangePassword(user).setVisible(true);
						ForgetPassword.this.dispose();
					}
				}
			}
		});
		panelSUBMIT.setBounds(257, 281, 129, 25);
		panel.add(panelSUBMIT);
		
		JLabel lblSUBMIT = new JLabel("SUBMIT");
		lblSUBMIT.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelSUBMIT.add(lblSUBMIT);
	}
}
