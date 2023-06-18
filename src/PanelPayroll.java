import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private JTextField textField_8;
	private JTable table;
	private String basicPay = "";
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField totalPay;
	private JTextField textOvertime;

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
		dateChooser.setBounds(225, 62, 166, 20);
		add(dateChooser);

		//Default value of dateChooser
		Date date = new Date();
		dateChooser.setDate(date);
		
		JButton btnNewButton = new JButton("GENERATE");
		btnNewButton.setBounds(434, 62, 108, 23);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnNewButton);
		
		JLabel lblSss = new JLabel("SSS");
		lblSss.setBounds(10, 220, 155, 14);
		lblSss.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblSss);
		
		JLabel lblPhilhealth = new JLabel("PHILHEALTH");
		lblPhilhealth.setBounds(10, 235, 155, 14);
		lblPhilhealth.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblPhilhealth);
		
		JLabel lblPagibig = new JLabel("PAG-IBIG");
		lblPagibig.setBounds(10, 250, 155, 14);
		lblPagibig.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblPagibig);
		
		JLabel lblTax = new JLabel("TAX");
		lblTax.setBounds(10, 285, 155, 14);
		lblTax.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblTax);
		
		JLabel lblAbsence = new JLabel("DEDUCTIONS");
		lblAbsence.setBounds(10, 310, 155, 14);
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
		lblNewLabel_1.setBounds(10, 195, 130, 14);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(143, 164, 155, 15);
		textField_1.setEditable(false);
		add(textField_1);
		textField_1.setColumns(10);
		
		totalPay = new JTextField();
		totalPay.setEditable(false);
		totalPay.setColumns(10);
		totalPay.setBounds(143, 350, 155, 15);
		add(totalPay);
		
		textOvertime = new JTextField();
		textOvertime.setEditable(false);
		textOvertime.setColumns(10);
		textOvertime.setBounds(143, 323, 155, 15);
		add(textOvertime);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String employeeNum=txtEmployeeNumber.getText();
				
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
					double numHours = 0;
					double otHrs = 0;
					for (int i = 0; dateSize > i; i++) {
						getDate = (String) dates.get(i).get(1);
						String strGetDate[] = getDate.split("-");
						int yearStr = Integer.parseInt(strGetDate[0]);
						int monthStr = Integer.parseInt(strGetDate[1]);
						int dayStr = Integer.parseInt(strGetDate[2]);
						
						if (monthStr == Integer.parseInt(strMonth) && yearStr == Integer.parseInt(strYear)) {
							String primKey = (String) dates.get(i).get(0);
							
							Date timeInFormatted = null;
							Date timeOutFormatted = null;
							Date correctTimeIn = null;
							
							if (Integer.parseInt(str) <= 15) {
								//Get hours for 1-15
								if (dayStr <= 15) {
									//Get total hours using primary key
									try {
										pst = conn.prepareStatement("SELECT totalHours, timeIn, timeOut FROM payrollInfo WHERE ID = '"+primKey+"'");
										rs = pst.executeQuery();

										while (rs.next()) {
											Double getHrs = Double.parseDouble(rs.getString("totalHours"));
											String employeeIn = rs.getString("timeIn");
											String employeeOut = rs.getString("timeOut");
											
											//Format time in
											try {
												timeInFormatted = sdf.parse(employeeIn);
												timeOutFormatted = sdf.parse(employeeOut);
												correctTimeIn = sdf.parse("08:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (getHrs == 9.00) {
												getHrs = 8.00;
											} else if (getHrs > 9.00) {
												if (correctTimeIn.getTime() - timeInFormatted.getTime() > 0) {
													getHrs = (double) (timeOutFormatted.getTime() - correctTimeIn.getTime());
													if (getHrs - 9 > 0) {
														otHrs = getHrs - 9;
														getHrs = 8.00;
													} else {
														//Get how late
														double howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
														//if (howLate )
														//undertime value
													}
												} else {
													//update
													//late
												}
											} else {
												//update
												//undertime
											}
											
											numHours = numHours + getHrs;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							} else {
								if (dayStr > 15) {
									try {
										pst = conn.prepareStatement("SELECT totalHours, timeIn, timeOut FROM payrollInfo WHERE ID = '"+primKey+"'");
										rs = pst.executeQuery();

										while (rs.next()) {
											Double getHrs = Double.parseDouble(rs.getString("totalHours"));
											String employeeIn = rs.getString("timeIn");
											String employeeOut = rs.getString("timeOut");
											
											try {
												timeInFormatted = sdf.parse(employeeIn);
												timeOutFormatted = sdf.parse(employeeOut);
												correctTimeIn = sdf.parse("08:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (getHrs == 9.00) {
												getHrs = 8.00;
											} else if (getHrs > 9.00) {
												if (correctTimeIn.getTime() - timeInFormatted.getTime() > 0) {
													getHrs = (double) (timeOutFormatted.getTime() - correctTimeIn.getTime());
													if (getHrs - 9 > 0) {
														otHrs = getHrs - 9;
														getHrs = 8.00;
													} else {
														//update
														//undertime
													}
												} else {
													//update
													//late
												}
											} else {
												//update
												//undertime
											}
											
											numHours = numHours + getHrs;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
						}  
					}
					
					//Convert hours into days (8 hour/day)
					double hrsToDay = numHours/8;
					
					//Get total pay for that period
					double salary = hrsToDay * Double.parseDouble(basicPay);
					
					BigDecimal bdPagibig = new BigDecimal(salary * 0.06).setScale(2, RoundingMode.HALF_UP);
					double numPagibig = bdPagibig.doubleValue();
					
					BigDecimal bdGross = new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP);
					double numGross = bdGross.doubleValue();
					
					totalPay.setText(Double.toString(numGross));
					textField_4.setText(Double.toString(numPagibig));
					
					//update double perHour = basicPay/8;
					textOvertime.setText(Double.toString(otHrs * perHour));
					
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
		textField_2.setText("570.00");
		textField_2.setColumns(10);
		textField_2.setBounds(143, 218, 155, 15);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("450.00");
		textField_3.setColumns(10);
		textField_3.setBounds(143, 233, 155, 15);
		add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(143, 248, 155, 15);
		add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setText("0.00");
		textField_5.setColumns(10);
		textField_5.setBounds(143, 285, 155, 15);
		add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setEditable(false);
		textField_6.setBounds(143, 308, 155, 15);
		add(textField_6);
		
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
				double grossPay = Double.parseDouble(totalPay.getText());
				
				double netPay = (grossPay + Double.parseDouble(textOvertime.getText())) - (Double.parseDouble(textField_5.getText()) + Double.parseDouble(textField_4.getText()) + Double.parseDouble(textField_3.getText()) + Double.parseDouble(textField_2.getText()));
				textField_8.setText(String.valueOf(netPay));
			}
		});
		btnApply.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnApply.setBounds(190, 395, 108, 23);
		add(btnApply);
		
		JLabel lblNewLabel_2 = new JLabel("BASIC");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 164, 155, 14);
		add(lblNewLabel_2);
		
		JLabel lblOvertime = new JLabel("OVERTIME");
		lblOvertime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOvertime.setBounds(10, 325, 155, 14);
		add(lblOvertime);
	}
}
