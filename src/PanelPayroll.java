import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class PanelPayroll extends JPanel {
	private JTextField txtEmployeeNumber;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable table;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	/**
	 * Create the panel.
	 */
	public PanelPayroll() {
		try {
			//initiate connection with database
			conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		txtEmployeeNumber = new JTextField();
		txtEmployeeNumber.setText("Employee Number");
		txtEmployeeNumber.setBounds(31, 62, 166, 22);
		add(txtEmployeeNumber);
		txtEmployeeNumber.setColumns(10);
		
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
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("MMM d y");
		dateChooser.setBounds(31, 95, 166, 20);
		add(dateChooser);

		//Default value of dateChooser
		Date date = new Date();
		dateChooser.setDate(date);
		
		JButton btnNewButton = new JButton("GENERATE");
		btnNewButton.setBounds(434, 62, 108, 23);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("BASIC");
		lblNewLabel.setBounds(10, 179, 155, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblNewLabel);
		
		JLabel lblSss = new JLabel("SSS");
		lblSss.setBounds(10, 235, 155, 14);
		lblSss.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblSss);
		
		JLabel lblPhilhealth = new JLabel("PHILHEALTH");
		lblPhilhealth.setBounds(10, 250, 155, 14);
		lblPhilhealth.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblPhilhealth);
		
		JLabel lblPagibig = new JLabel("PAG-IBIG");
		lblPagibig.setBounds(10, 265, 155, 14);
		lblPagibig.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblPagibig);
		
		JLabel lblTax = new JLabel("TAX");
		lblTax.setBounds(10, 300, 155, 14);
		lblTax.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblTax);
		
		JLabel lblAbsence = new JLabel("ABSENCE");
		lblAbsence.setBounds(10, 325, 155, 14);
		lblAbsence.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblAbsence);
		
		JLabel lblGross = new JLabel("GROSS");
		lblGross.setBounds(10, 350, 155, 14);
		lblGross.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblGross);
		
		JLabel lblNet = new JLabel("NET");
		lblNet.setBounds(10, 371, 155, 14);
		lblNet.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblNet);
		
		JLabel lblNewLabel_1 = new JLabel("STATUTORY BENEFITS");
		lblNewLabel_1.setBounds(10, 210, 130, 14);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(143, 177, 155, 15);
		textField_1.setEditable(false);
		add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String employeeNum=txtEmployeeNumber.getText();
				String basicPay = "";
				
				int countOfUser = 0;
				
				try {
					//check if user exists
					pst = conn.prepareStatement("SELECT DAILYSALARY FROM employeeInfo WHERE EMPLOYEENUM = '"+employeeNum+"'");
					rs = pst.executeQuery();

					while (rs.next()) {
						basicPay = rs.getString("DAILYSALARY");
						countOfUser++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if (countOfUser > 0) {
					//populate basic pay
					textField_1.setText(basicPay);
					
					//check range of calendar update
					Date setDate = dateChooser.getDate();
					
					//get day only
					SimpleDateFormat sdf = new SimpleDateFormat("dd", java.util.Locale.ENGLISH);
					String str = sdf.format(setDate);
					
					//get month only
					SimpleDateFormat sdfMonth = new SimpleDateFormat("MM", java.util.Locale.ENGLISH);
					String strMonth = sdfMonth.format(setDate);
					
					//get year only
					SimpleDateFormat sdfYear = new SimpleDateFormat("yyy", java.util.Locale.ENGLISH);
					String strYear = sdfYear.format(setDate);
					
					/*
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					LocalDateTime now = LocalDateTime.now(); 
					String currDate = dtf.format(now);
					dateChooser.setDateFormatString(currDate);
					*/
					
					//Get dates for user
					List<List> dates = new ArrayList<List>();
					try {
						pst = conn.prepareStatement("SELECT ID, dateIn FROM payrollInfo WHERE employeeNum = '"+employeeNum+"'");
						rs = pst.executeQuery();

						while (rs.next()) {
							List list1=new ArrayList();
							list1.add(rs.getString("ID"));
							list1.add(rs.getString("dateIn"));
							dates.add(list1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					//Get hours for specified time period
					int dateSize = dates.size();
					String getDate = "";
					for (int i = 0; dateSize > i; i++) {
						try {
							getDate = (String) dates.get(i).get(1);
							Date month = new SimpleDateFormat("mm").parse(getDate);
							DateFormat dateFormatMonth = new SimpleDateFormat("mm");  
							String monthStr = dateFormatMonth.format(month);
							Date year = new SimpleDateFormat("yyyy").parse(getDate);
							DateFormat dateFormatYear = new SimpleDateFormat("yyyy");  
							String yearStr = dateFormatYear.format(year);
							JOptionPane.showMessageDialog(null, monthStr);
							JOptionPane.showMessageDialog(null, strMonth);
							JOptionPane.showMessageDialog(null, yearStr);
							JOptionPane.showMessageDialog(null, strYear);
							if (monthStr == strMonth && yearStr == strYear) {
								JOptionPane.showMessageDialog(null, "PASOK");
								if (Integer.parseInt(str) <= 15) {
									//Get hours for 1-15
									
								} else {
									//update
								}
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
					}
					
					/*
					try {
						Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateInputted);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
					*/
				} else {
					JOptionPane.showMessageDialog(null, "Employee number is invalid!");
				}
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(143, 233, 155, 15);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(143, 248, 155, 15);
		add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(143, 263, 155, 15);
		add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(143, 300, 155, 15);
		add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setEditable(false);
		textField_6.setBounds(143, 323, 155, 15);
		add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setEditable(false);
		textField_7.setBounds(143, 348, 155, 15);
		add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setEditable(false);
		textField_8.setBounds(143, 369, 155, 15);
		add(textField_8);
		
		JButton btnNewButton_1 = new JButton("PRINT");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(572, 62, 97, 23);
		add(btnNewButton_1);
		
		table = new JTable();
		table.setBounds(342, 180, 348, 219);
		add(table);
		
		JButton btnApply = new JButton("APPLY");
		btnApply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int grossPay = Integer.parseInt(textField_1.getText()) + Integer.parseInt(textField_2.getText()) + Integer.parseInt(textField_3.getText()) + Integer.parseInt(textField_4.getText());
				textField_7.setText(String.valueOf(grossPay));
				
				int netPay = grossPay - (Integer.parseInt(textField_5.getText()) + Integer.parseInt(textField_6.getText()));
				textField_8.setText(String.valueOf(netPay));
			}
		});
		btnApply.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnApply.setBounds(190, 395, 108, 23);
		add(btnApply);
	}
}
