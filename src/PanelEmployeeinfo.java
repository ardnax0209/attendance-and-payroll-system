import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class PanelEmployeeinfo extends JPanel {
	
	private Image img_user = new ImageIcon(Login.class.getResource("images/woman.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	
	private JTextField txtEmployeeNumber;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtMiddleName;
	private JTextField txtAddress;
	private JTextField txtContactNumber;
	private JTextField txtAge;
	private JTextField txtGender;
	private JTextField txtEmail;
	private JTextField txtCivilStatus;
	private JTextField dateOfBirth;
	private JTextField txtDateHire;
	private JTextField txtSearchEID;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	private JTable employeeTable;


	/**
	 * Create the panel.
	 */
	public PanelEmployeeinfo() {
		
		try {
			//initiate connection with database
			conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		JPanel panelLastName = new JPanel();
		panelLastName.setBounds(26, 119, 200, 25);
		panelLastName.setBackground(new Color(255, 255, 255));
		panelLastName.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelLastName);
		panelLastName.setLayout(null);
		
		txtLastName = new JTextField();
		txtLastName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtLastName.getText().equals("Last Name")) {
					txtLastName.setText("");
				}
				else {
					txtLastName.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtLastName.getText().equals("")) {
					txtLastName.setText("Last Name");
				}
			}
		});
		txtLastName.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtLastName.setBorder(null);
		txtLastName.setText("Last Name");
		txtLastName.setBounds(10, 5, 180, 15);
		panelLastName.add(txtLastName);
		txtLastName.setColumns(10);
		
		JPanel panelFirstName = new JPanel();
		panelFirstName.setBounds(255, 119, 200, 25);
		panelFirstName.setBackground(new Color(255, 255, 255));
		panelFirstName.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelFirstName);
		panelFirstName.setLayout(null);
		
		txtFirstName = new JTextField();
		txtFirstName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtFirstName.getText().equals("First Name")) {
					txtFirstName.setText("");
				}
				else {
					txtFirstName.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtFirstName.getText().equals("")) {
					txtFirstName.setText("First Name");
				}
			}
		});
		txtFirstName.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtFirstName.setBounds(10, 6, 180, 14);
		txtFirstName.setText("First Name");
		txtFirstName.setColumns(10);
		txtFirstName.setBorder(null);
		panelFirstName.add(txtFirstName);
		
		JPanel panelMiddleName = new JPanel();
		panelMiddleName.setBounds(480, 119, 200, 25);
		panelMiddleName.setBackground(new Color(255, 255, 255));
		panelMiddleName.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelMiddleName);
		panelMiddleName.setLayout(null);
		
		txtMiddleName = new JTextField();
		txtMiddleName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtMiddleName.getText().equals("Middle Name")) {
					txtMiddleName.setText("");
				}
				else {
					txtMiddleName.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtMiddleName.getText().equals("")) {
					txtMiddleName.setText("Middle Name");
				}
			}
		});
		txtMiddleName.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtMiddleName.setBounds(10, 6, 180, 14);
		txtMiddleName.setText("Middle Name");
		txtMiddleName.setColumns(10);
		txtMiddleName.setBorder(null);
		panelMiddleName.add(txtMiddleName);
		
		JPanel panelAddress = new JPanel();
		panelAddress.setBounds(26, 148, 429, 25);
		panelAddress.setBackground(new Color(255, 255, 255));
		panelAddress.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelAddress);
		panelAddress.setLayout(null);
		
		txtAddress = new JTextField();
		txtAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtAddress.getText().equals("Address")) {
					txtAddress.setText("");
				}
				else {
					txtAddress.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtAddress.getText().equals("")) {
					txtAddress.setText("Address");
				}
			}
		});
		txtAddress.setBounds(10, 6, 409, 14);
		txtAddress.setText("Address");
		txtAddress.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtAddress.setColumns(10);
		txtAddress.setBorder(null);
		panelAddress.add(txtAddress);
		
		JPanel panelContactNumber = new JPanel();
		panelContactNumber.setBounds(480, 148, 200, 25);
		panelContactNumber.setBackground(new Color(255, 255, 255));
		panelContactNumber.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelContactNumber);
		panelContactNumber.setLayout(null);
		
		txtContactNumber = new JTextField();
		txtContactNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtContactNumber.getText().equals("Contact Number")) {
					txtContactNumber.setText("");
				}
				else {
					txtContactNumber.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtContactNumber.getText().equals("")) {
					txtContactNumber.setText("Contact Number");
				}
			}
		});
		txtContactNumber.setBounds(10, 6, 180, 14);
		txtContactNumber.setText("Contact Number");
		txtContactNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtContactNumber.setColumns(10);
		txtContactNumber.setBorder(null);
		panelContactNumber.add(txtContactNumber);
		
		JPanel panelAge = new JPanel();
		panelAge.setBounds(26, 177, 200, 25);
		panelAge.setBackground(new Color(255, 255, 255));
		panelAge.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelAge);
		panelAge.setLayout(null);
		
		txtAge = new JTextField();
		txtAge.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtAge.getText().equals("Age")) {
					txtAge.setText("");
				}
				else {
					txtAge.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtAge.getText().equals("")) {
					txtAge.setText("Age");
				}
			}
		});
		txtAge.setBounds(10, 6, 180, 14);
		txtAge.setText("Age");
		txtAge.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtAge.setColumns(10);
		txtAge.setBorder(null);
		panelAge.add(txtAge);
		
		JPanel panelGender = new JPanel();
		panelGender.setBounds(255, 177, 200, 25);
		panelGender.setBackground(new Color(255, 255, 255));
		panelGender.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelGender);
		panelGender.setLayout(null);
		
		txtGender = new JTextField();
		txtGender.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtGender.getText().equals("Gender")) {
					txtGender.setText("");
				}
				else {
					txtGender.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtGender.getText().equals("")) {
					txtGender.setText("Gender");
				}
			}
		});
		txtGender.setBounds(10, 6, 180, 14);
		txtGender.setText("Gender");
		txtGender.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtGender.setColumns(10);
		txtGender.setBorder(null);
		panelGender.add(txtGender);
		
		JPanel panelEmail = new JPanel();
		panelEmail.setBounds(480, 177, 200, 25);
		panelEmail.setBackground(new Color(255, 255, 255));
		panelEmail.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelEmail);
		panelEmail.setLayout(null);
		
		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtEmail.getText().equals("Email")) {
					txtEmail.setText("");
				}
				else {
					txtEmail.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtEmail.getText().equals("")) {
					txtEmail.setText("Email");
				}
			}
		});
		txtEmail.setBounds(10, 6, 180, 14);
		txtEmail.setText("Email");
		txtEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEmail.setColumns(10);
		txtEmail.setBorder(null);
		panelEmail.add(txtEmail);
		
		JPanel panelCivilStatus = new JPanel();
		panelCivilStatus.setBounds(26, 206, 200, 25);
		panelCivilStatus.setBackground(new Color(255, 255, 255));
		panelCivilStatus.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelCivilStatus);
		panelCivilStatus.setLayout(null);
		
		txtCivilStatus = new JTextField();
		txtCivilStatus.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtCivilStatus.getText().equals("Civil Status")) {
					txtCivilStatus.setText("");
				}
				else {
					txtCivilStatus.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtCivilStatus.getText().equals("")) {
					txtCivilStatus.setText("Civil Status");
				}
			}
		});
		txtCivilStatus.setBounds(10, 6, 180, 14);
		txtCivilStatus.setText("Civil Status");
		txtCivilStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCivilStatus.setColumns(10);
		txtCivilStatus.setBorder(null);
		panelCivilStatus.add(txtCivilStatus);
		
		JPanel panelNationality = new JPanel();
		panelNationality.setBounds(255, 206, 200, 25);
		panelNationality.setBackground(new Color(255, 255, 255));
		panelNationality.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelNationality);
		panelNationality.setLayout(null);
		
		dateOfBirth = new JTextField();
		dateOfBirth.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (dateOfBirth.getText().equals("Birthday")) {
					dateOfBirth.setText("");
				}
				else {
					dateOfBirth.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (dateOfBirth.getText().equals("")) {
					dateOfBirth.setText("Birthday");
				}
			}
		});
		dateOfBirth.setBounds(10, 6, 180, 14);
		dateOfBirth.setText("Birthday");
		dateOfBirth.setFont(new Font("Tahoma", Font.BOLD, 11));
		dateOfBirth.setColumns(10);
		dateOfBirth.setBorder(null);
		panelNationality.add(dateOfBirth);
		
		JPanel panelDateHired = new JPanel();
		panelDateHired.setBounds(480, 206, 200, 25);
		panelDateHired.setBackground(new Color(255, 255, 255));
		panelDateHired.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelDateHired);
		panelDateHired.setLayout(null);
		
		txtDateHire = new JTextField();
		txtDateHire.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtDateHire.getText().equals("Date Hire")) {
					txtDateHire.setText("");
				}
				else {
					txtDateHire.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtDateHire.getText().equals("")) {
					txtDateHire.setText("Date Hire");
				}
			}
		});
		txtDateHire.setBounds(10, 6, 180, 14);
		txtDateHire.setText("Date Hire");
		txtDateHire.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtDateHire.setColumns(10);
		txtDateHire.setBorder(null);
		panelDateHired.add(txtDateHire);
		
		JPanel panelEmployeeNumber = new JPanel();
		panelEmployeeNumber.setBounds(396, 83, 284, 25);
		panelEmployeeNumber.setBackground(new Color(255, 255, 255));
		panelEmployeeNumber.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelEmployeeNumber);
		panelEmployeeNumber.setLayout(null);
		
		txtEmployeeNumber = new JTextField();
		txtEmployeeNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtEmployeeNumber.getText().equals("Employee Number")) {
					txtEmployeeNumber.setText("");
				}
				else {
					txtEmployeeNumber.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtEmployeeNumber.getText().equals("")) {
					txtEmployeeNumber.setText("Employee Number");
				}
			}
		});
		txtEmployeeNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEmployeeNumber.setBackground(new Color(255, 255, 255));
		txtEmployeeNumber.setBorder(null);
		txtEmployeeNumber.setText("Employee Number");
		txtEmployeeNumber.setEditable(true);
		txtEmployeeNumber.setBounds(10, 5, 264, 15);
		panelEmployeeNumber.add(txtEmployeeNumber);
		txtEmployeeNumber.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(79, 242, 89, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lastName = txtLastName.getText();
				String firstName = txtFirstName.getText();
				String middleName = txtMiddleName.getText();
				String empAddress = txtAddress.getText();
				String contactNum = txtContactNumber.getText();
				String empAge = txtAge.getText();
				String empGender = txtGender.getText();
				String empEmail = txtEmail.getText();
				String civilStatus = txtCivilStatus.getText();
				String empBirthday = dateOfBirth.getText();
				String employeeNum=txtEmployeeNumber.getText();
				
				//Initialize SQL statement
				Statement statement = null;
				//Create SQL statement
				//String query = "INSERT INTO employeeInfo VALUES ('"+employeeNum+"','"+lastName+"','"+firstName+"','"+middleName+"','"+empAddress+"','"+contactNum+"','"+empAge+"','"+empGender+"','"+empEmail+"','"+civilStatus+"','"+empNationality+"','"+dateHired+"')";
				String query = "INSERT INTO employeeInfo ('employeeNum', 'LASTNAME', 'FIRSTNAME', 'MIDDLENAME', 'ADDRESS', 'CONTACTNUM', 'AGE', 'GENDER', 'EMAIL', 'CIVILSTATUS', 'BIRTHDAY') VALUES ('"+employeeNum+"','"+lastName+"','"+firstName+"','"+middleName+"','"+empAddress+"','"+contactNum+"','"+empAge+"','"+empGender+"','"+empEmail+"','"+civilStatus+"','"+empBirthday+"')";
				
				try {
					statement = conn.createStatement();
					statement.execute(query);
					JOptionPane.showMessageDialog(null, "Employee created!");
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, e2.toString());
				}
				
				table_load();
			}
		});
		btnAdd.setBackground(new Color(255, 255, 255));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					int row1 = employeeTable.getSelectedRow();
				
				String employeeNum= employeeTable.getValueAt(row1,0).toString();
				String lastName = employeeTable.getValueAt(row1,1).toString();
				String firstName = employeeTable.getValueAt(row1,2).toString();
				String middleName = employeeTable.getValueAt(row1,3).toString();
				String empAddress = employeeTable.getValueAt(row1,4).toString();
				String contactNum = employeeTable.getValueAt(row1,5).toString();
				String empAge = employeeTable.getValueAt(row1,6).toString();
				String empGender = employeeTable.getValueAt(row1,7).toString();
				String empEmail = employeeTable.getValueAt(row1,8).toString();
				String civilStatus = employeeTable.getValueAt(row1,9).toString();
				String empBirthday = employeeTable.getValueAt(row1,10).toString();
				
				
				//Initialize SQL statement
				Statement statement = null;
				String query = "UPDATE employeeInfo SET lastName= '"+lastName+"',firstName='"+firstName+"',middleName='"+middleName+"',ADDRESS='"+empAddress+"',contactNum='"+contactNum+"',AGE='"+empAge+"',GENDER='"+empGender+"',EMAIL='"+empEmail+"',CIVILSTATUS='"+civilStatus+"',BIRTHDAY='"+empBirthday+"' WHERE employeeNum='"+employeeNum+"'";
				
				try {
					statement = conn.createStatement();
					statement.execute(query);
					JOptionPane.showMessageDialog(null, "Employee Updated!");
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null, e3.toString());
				}
				
				table_load();
				
			}
		});
		btnUpdate.setBounds(309, 242, 89, 23);
		btnUpdate.setBackground(new Color(255, 255, 255));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	int row1 = employeeTable.getSelectedRow();
				
				String employeeNum= employeeTable.getValueAt(row1,0).toString();

				
				
				//Initialize SQL statement
				Statement statement = null;
				String query = "UPDATE employeeInfo SET STATUS= 'INACTIVE'  WHERE employeeNum='"+employeeNum+"'";
				
				try {
					statement = conn.createStatement();
					statement.execute(query);
					JOptionPane.showMessageDialog(null, "Employee Deleted!");
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null, e3.toString());
				}
				
				table_load();
			
				
			}

			
		});
		btnDelete.setBounds(533, 242, 89, 23);
		btnDelete.setBackground(new Color(255, 255, 255));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnDelete);
		
		JPanel panelUser = new JPanel();
		panelUser.setBounds(628, 0, 62, 50);
		panelUser.setBackground(new Color(255, 182, 193));
		add(panelUser);
		panelUser.setLayout(null);
		
		JLabel lblUserIcon = new JLabel("");
		lblUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserIcon.setIcon(new ImageIcon(img_user));
		lblUserIcon.setBounds(8, 5, 46, 45);
		panelUser.add(lblUserIcon);
		
		JLabel lblNewLabel = new JLabel("Chef Jelv");
		lblNewLabel.setBounds(638, 47, 46, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		add(lblNewLabel);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSearch.setBounds(32, 62, 46, 14);
		add(lblSearch);
		
		txtSearchEID = new JTextField();
		txtSearchEID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchField = "%" + txtSearchEID.getText() + "%";
				
				//populates table
				try {
					pst = conn.prepareStatement("SELECT * FROM employeeInfo WHERE status = 'ACTIVE' AND EMPLOYEENUM LIKE '"+searchField+"'");
					rs = pst.executeQuery();
					employeeTable.setModel (DbUtils.resultSetToTableModel(rs));
				}
				catch (SQLException e2)
				{
					e2.addSuppressed(e2);
				}
			}
			
		});
		txtSearchEID.setBounds(82, 54, 144, 25);
		add(txtSearchEID);
		txtSearchEID.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 276, 680, 237);
		add(scrollPane);
		
		employeeTable = new JTable();
		scrollPane.setViewportView(employeeTable);
		
		table_load();
	}
	
	public void table_load()
	{
		//populates table
		try {
			pst = conn.prepareStatement("select * from employeeInfo WHERE status = 'ACTIVE'");
			rs = pst.executeQuery();
			employeeTable.setModel (DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.addSuppressed(e);
		}
		
		//populate employee number
		try {
			pst = conn.prepareStatement("select employeeNum from employeeInfo");
			rs = pst.executeQuery();
			String empNum = "";
			int number = 0;

			while (rs.next()) {
				empNum = rs.getString("employeeNum");
			}
			
			if (empNum == "") {
				System.out.println("Employee number has no value");
			} else {
				number = Integer.parseInt(empNum);
			}
			
			number++;
			txtEmployeeNumber.setText(String.valueOf(number));
		}
		catch (SQLException e)
		{
			e.addSuppressed(e);
		}
	}
}

