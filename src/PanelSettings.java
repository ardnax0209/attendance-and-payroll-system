import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PanelSettings extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	Connection conn;

	/**
	 * Create the panel.
	 */
	public PanelSettings(String username) {
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(55, 98, 183, 32);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(292, 100, 199, 32);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("What do you want to change?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(55, 167, 183, 14);
		add(lblNewLabel_1);
		
JComboBox<String> comboBox = new JComboBox<String>();
		
		// add items to the combo box
		comboBox.addItem("");
		comboBox.addItem("PASSWORD");
		comboBox.addItem("USERNAME");
		comboBox.addItem("SECURITY QUESTION1");
		comboBox.addItem("SECURITY QUESTION2");
		comboBox.setBounds(55, 218, 183, 39);
		add(comboBox);
		
JComboBox<String> comboBox_1 = new JComboBox<String>();
		
		// add items to the combo box
		comboBox_1.addItem("");
		comboBox_1.addItem("PASSWORD");
		comboBox_1.addItem("USERNAME");
		comboBox_1.addItem("SECURITY QUESTION1");
		comboBox_1.addItem("SECURITY QUESTION2");
		comboBox_1.setBounds(55, 218, 183, 39);
		add(comboBox_1);
		
		
		comboBox_1.setBounds(55, 284, 183, 39);
		add(comboBox_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(292, 218, 199, 39);
		add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(292, 284, 199, 39);
		add(textField_2);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (textField.getText().equals(username)) {
					Statement statement = null;
					String query = "";
					String txtBox1 = textField_1.getText();
					String txtBox2 = textField_2.getText();
					
					try {
						//initiate connection with database
						conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					//First combo
					if (comboBox.getSelectedItem().toString() == "PASSWORD" && txtBox1.isBlank() == false) {
						//Initialize SQL statement
						statement = null;
						query = "UPDATE userdata SET Password= '"+txtBox1+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox.getSelectedItem().toString() == "USERNAME" && txtBox1.isBlank() == false) {
						//Initialize SQL statement
						statement = null;
						query = "UPDATE userdata SET Username= '"+txtBox1+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox.getSelectedItem().toString() == "SECURITY QUESTION1" && txtBox1.isBlank() == false) {
						//Initialize SQL statement
						statement = null;
						query = "UPDATE userdata SET SecAnswer1= '"+txtBox1+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox.getSelectedItem().toString() == "SECURITY QUESTION2" && txtBox1.isBlank() == false) {
						//Initialize SQL statement
						statement = null;
						query = "UPDATE userdata SET SecAnswer2= '"+txtBox1+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please fill out what you want changed.");
					}
					
					//Second combo
					if (comboBox_1.getSelectedItem().toString() == "PASSWORD" && txtBox2.isBlank() == false) {
						//Initialize SQL statement
						statement = null;
						query = "UPDATE userdata SET Password= '"+txtBox2+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
							JOptionPane.showMessageDialog(null, "Updated successfully.");
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox_1.getSelectedItem().toString() == "USERNAME" && txtBox2.isBlank() == false) {
						//Initialize SQL statement
						query = "UPDATE userdata SET Username= '"+txtBox2+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
							JOptionPane.showMessageDialog(null, "Updated successfully.");
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox_1.getSelectedItem().toString() == "SECURITY QUESTION1" && txtBox2.isBlank() == false) {
						//Initialize SQL statement
						query = "UPDATE userdata SET SecAnswer1= '"+txtBox2+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
							JOptionPane.showMessageDialog(null, "Updated successfully.");
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					} else if (comboBox_1.getSelectedItem().toString() == "SECURITY QUESTION2" && txtBox2.isBlank() == false) {
						//Initialize SQL statement
						query = "UPDATE userdata SET SecAnswer2= '"+txtBox2+"' WHERE Username='"+username+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
							JOptionPane.showMessageDialog(null, "Updated successfully.");
						} catch (SQLException e3) {
							JOptionPane.showMessageDialog(null, e3.toString());
						}
					}
					
					if (conn != null) {
				        try {
				            conn.close();
				        } catch (SQLException e1) { /* ignored */}
				    }
					
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username.");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(375, 386, 116, 32);
		add(btnNewButton);

	}
}
