import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Phrase;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;

public class PanelPayroll extends JPanel {
	private JTextField txtEmployeeNumber;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_8;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField totalPay;
	private JTextField textOvertime;
	private JTable table_1;
	
	private String basicPay = "";
	private int x = 0;

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
		
		//Placeholder for table data
		Object[][] data = {};
		
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
					double deductHrs = 0;
					for (int i = 0; dateSize > i; i++) {
						getDate = (String) dates.get(i).get(1);
						String strGetDate[] = getDate.split("-");
						int yearStr = Integer.parseInt(strGetDate[0]);
						int monthStr = Integer.parseInt(strGetDate[1]);
						int dayStr = Integer.parseInt(strGetDate[2]);
						
						if (monthStr == Integer.parseInt(strMonth) && yearStr == Integer.parseInt(strYear)) {
							String primKey = (String) dates.get(i).get(0);
							
							SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm:ss", java.util.Locale.ENGLISH);
							Date timeInFormatted = null;
							Date timeOutFormatted = null;
							Date correctTimeIn = null;
							Date correctTimeOut = null;
							
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
												timeInFormatted = timeForm.parse(employeeIn);
												timeOutFormatted = timeForm.parse(employeeOut);
												correctTimeIn = timeForm.parse("08:00:00");
												correctTimeOut = timeForm.parse("17:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (getHrs >= 9.00) {
												if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
													getHrs = (double) (timeOutFormatted.getTime() - correctTimeIn.getTime());
													if (getHrs - 9 > 0) {
														otHrs = otHrs + (getHrs - 9);
													}

													getHrs = 8.00;
												} else {
													double howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
													if (howLate < 1) {
														deductHrs = deductHrs + 40;
													} else if (howLate <= 4 && howLate > 1) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													}
													
													getHrs = 8.00;
													
													//overtime value
													otHrs = otHrs + (timeOutFormatted.getTime() - correctTimeOut.getTime());
												}
											} else {
												//check if undertime or late
												if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
													//undertime
													double actualHrs = timeOutFormatted.getTime() - correctTimeIn.getTime();
													if (actualHrs < 8) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) * (8 - actualHrs));
													}
													getHrs = 8.00;
												} else {
													//late
													double howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
													if (howLate < 1) {
														deductHrs = deductHrs + 40;
													} else if (howLate <= 4 && howLate > 1) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													}
													
													getHrs = 8.00;
												}
											}
											
											numHours = numHours + getHrs;
											
											//Populate array of objects for table
											ArrayList<Object> newObj = new ArrayList<Object>(Arrays.asList(data));
										    newObj.add(getDate);
										    newObj.add(employeeIn);
										    newObj.add(employeeOut);
										    newObj.add(rs.getString("totalHours"));
										    data[x][0] = newObj;
										    x++;
										    JOptionPane.showMessageDialog(null, "TEST2");
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
												timeInFormatted = timeForm.parse(employeeIn);
												timeOutFormatted = timeForm.parse(employeeOut);
												correctTimeIn = timeForm.parse("08:00:00");
												correctTimeOut = timeForm.parse("17:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (getHrs >= 9.00) {
												if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
													getHrs = (double) (timeOutFormatted.getTime() - correctTimeIn.getTime());
													if (getHrs - 9 > 0) {
														otHrs = otHrs + (getHrs - 9);
													}

													getHrs = 8.00;
												} else {
													double howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
													if (howLate < 1) {
														deductHrs = deductHrs + 40;
													} else if (howLate <= 4 && howLate > 1) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													}
													
													getHrs = 8.00;
													
													//overtime value
													otHrs = otHrs + (timeOutFormatted.getTime() - correctTimeOut.getTime());
												}
											} else {
												//check if undertime or late
												if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
													//undertime
													double actualHrs = timeOutFormatted.getTime() - correctTimeIn.getTime();
													if (actualHrs < 8) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) * (8 - actualHrs));
													}
													getHrs = 8.00;
												} else {
													//late
													double howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
													if (howLate < 1) {
														deductHrs = deductHrs + 40;
													} else if (howLate <= 4 && howLate > 1) {
														deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													}
													
													getHrs = 8.00;
												}
											}
											
											numHours = numHours + getHrs;
											
											//Populate array of objects for table
											ArrayList<Object> newObj = new ArrayList<Object>(Arrays.asList(data));
										    newObj.add(getDate);
										    newObj.add(employeeIn);
										    newObj.add(employeeOut);
										    newObj.add(rs.getString("totalHours"));
										    data[x][0] = newObj;
										    x++;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
						}  
					}
					
					double perHour = Double.parseDouble(basicPay)/8;
					
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
					
					double otCharge = otHrs * perHour;
					BigDecimal bdOT = new BigDecimal(otCharge).setScale(2, RoundingMode.HALF_UP);
					double finalOT = bdOT.doubleValue();
					textOvertime.setText(Double.toString(finalOT));
					
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
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//created PDF document instance   
				Document doc = new Document();
				try  
				{  
					//generate a PDF at the specified location
					PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ardna\\OneDrive\\Documents\\Payslip.pdf")); //update payslip/user-payslip.pdf
					System.out.println("PDF created.");  
					//opens the PDF  
					doc.open();
					
					//Create Font objects
			        /*Font headerFont = new Font(Font.MONOSPACED, 20,
			                      Font.ITALIC | Font.BOLD); */
					
					Paragraph preface = new Paragraph("Sweet Dyeiz and Cupcakes"); //update
					//preface.setFont(headerFont);
					preface.setAlignment(Element.ALIGN_CENTER);
					preface.setSpacingAfter(50);
					
					Paragraph employeeNum = new Paragraph("Employee Number: ");
					Paragraph employeeName = new Paragraph("Name: ");
					Paragraph employeeDate = new Paragraph("Date Generated: ");
					employeeDate.setSpacingAfter(50);

					//adds paragraph to the PDF file
					doc.add(preface);
					doc.add(employeeNum);
					doc.add(employeeName);
					doc.add(employeeDate);
			        
					PdfPTable table = new PdfPTable(2);
					PdfPCell c1 = new PdfPCell(new Phrase(" "));
			        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

			        table.addCell("BASIC PAY");
			        table.addCell("1.1");
			        table.addCell(c1);
			        table.addCell(" ");
			        table.addCell("SSS");
			        table.addCell("2.1");
			        table.addCell("PHILHEALTH");
			        table.addCell("2.3");
			        table.addCell("PAG-IBIG");
			        table.addCell("2.3");
			        table.addCell(c1);
			        table.addCell(" ");
			        table.addCell("TAX");
			        table.addCell("2.3");
			        table.addCell(c1);
			        table.addCell(" ");
			        table.addCell("DEDUCTIONS");
			        table.addCell("2.3");
			        table.addCell("OVERTIME");
			        table.addCell("2.3");
			        table.addCell(c1);
			        table.addCell(" ");
			        table.addCell("GROSS");
			        table.addCell("2.3");
			        table.addCell("NET");
			        table.addCell("2.3");

			        doc.add(table);
					
					//close the PDF file  
					doc.close();  
					//closes the writer  
					writer.close();
					
					JOptionPane.showMessageDialog(null, "Payslip generated.");
				}   
				catch (DocumentException e1)  
				{  
					e1.printStackTrace();  
				}   
				catch (FileNotFoundException e2)  
				{  
					e2.printStackTrace();  
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(572, 62, 97, 23);
		add(btnNewButton_1);
		
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
		
		String[] columnNames = {"Date",
                "Time in",
                "Time out",
                "Total Hours"};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(342, 180, 348, 219);
		add(scrollPane);
		
		table_1 = new JTable(data, columnNames);
		scrollPane.setViewportView(table_1);
	}
}
