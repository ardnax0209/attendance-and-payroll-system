import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	
	DefaultTableModel tableModel = new DefaultTableModel();
	
	private String basicPay = "";
	private String fName = "";
	private String lName = "";
	private double numHrs = 0.00;
	private double otHours = 0.00;
	private double totalLate = 0.00;
	private int x = 0;

	/**
	 * Create the panel.
	 */
	public PanelPayroll() {
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
			public void mouseReleased(MouseEvent e) {
				try {
					//initiate connection with database
					conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				String employeeNum = txtEmployeeNumber.getText();
				totalLate = 0.00;
				
				int countOfUser = 0;
				
				try {
					//check if user exists
					pst = conn.prepareStatement("SELECT DAILYSALARY, FIRSTNAME, LASTNAME FROM employeeInfo WHERE EMPLOYEENUM = '"+employeeNum+"'");
					rs = pst.executeQuery();

					while (rs.next()) {
						basicPay = rs.getString("DAILYSALARY");
						fName = rs.getString("FIRSTNAME");
						lName = rs.getString("LASTNAME");
						countOfUser++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if (countOfUser > 0) {
					tableModel.setRowCount(0);
					
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
					double numHours = 0.00;
					double getHrs = 0.00;
					double otHrs = 0.00;
					double deductHrs = 0.00;
					Long howLate = (long) 0;
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
										int tblRow = 0;
										
										pst = conn.prepareStatement("SELECT timeIn, timeOut, totalHours FROM payrollInfo WHERE ID = '"+primKey+"'");
										rs = pst.executeQuery();
										
										while (rs.next()) {
											String employeeIn = rs.getString("timeIn");
											String employeeOut = rs.getString("timeOut");
											getHrs = Double.parseDouble(rs.getString("totalHours"));
											boolean undertimeChckr = false;
											
											try {
												timeInFormatted = timeForm.parse(employeeIn);
												timeOutFormatted = timeForm.parse(employeeOut);
												correctTimeIn = timeForm.parse("08:00:00");
												correctTimeOut = timeForm.parse("17:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
												//not late
												getHrs = 8.00;
											} else if (correctTimeIn.getTime() - timeInFormatted.getTime() < 0) {
												//late
												howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
												if (howLate < 3600000) {
													deductHrs = deductHrs + 40.00;
													getHrs = 8.00;
												} else if (howLate <= 14400000 && howLate >= 3600000) {
													deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													getHrs = 8.00;
												} else if (howLate >= 14400000 && getHrs <= 4.5) {
													getHrs = getHrs;
													System.out.println("Undertime, so will take from how many hours until 5 pm.");
													undertimeChckr = true;
												}
											}
											
											if (timeOutFormatted.getTime() - correctTimeOut.getTime() > 0) {
												otHrs = otHrs + ((((timeOutFormatted.getTime() - correctTimeOut.getTime()) / (1000*60*60)) % 24));
											}
											
											if (timeOutFormatted.getTime() - correctTimeOut.getTime() < 0 && undertimeChckr == false) {
												long hrsVal = timeOutFormatted.getTime() - correctTimeOut.getTime();
												double convertedUnder = Math.abs(hrsVal) / 3600000.0;
												deductHrs = deductHrs + (convertedUnder * (Double.parseDouble(basicPay)/8));
											}
											
											numHours = numHours + getHrs;
											totalLate = totalLate + (howLate / 3600000.0);
											
											//Populate table
											tableModel.insertRow(tblRow, new Object[] { (String) dates.get(i).get(1), employeeIn, employeeOut, rs.getString("totalHours")});
											tblRow++;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									} finally {
										if (rs != null) {
									        try {
									        	rs.close();
									        } catch (SQLException e1) { /* ignored */}
									    }
									    if (conn != null) {
									        try {
									            conn.close();
									        } catch (SQLException e1) { /* ignored */}
									    }
									}
								}
							} else {
								if (dayStr > 15) {
									try {
										int tblRow = 0;
										
										pst = conn.prepareStatement("SELECT totalHours, timeIn, timeOut FROM payrollInfo WHERE ID = '"+primKey+"'");
										rs = pst.executeQuery();
										
										while (rs.next()) {
											String employeeIn = rs.getString("timeIn");
											String employeeOut = rs.getString("timeOut");
											getHrs = Double.parseDouble(rs.getString("totalHours"));
											boolean undertimeChckr = false;
											
											try {
												timeInFormatted = timeForm.parse(employeeIn);
												timeOutFormatted = timeForm.parse(employeeOut);
												correctTimeIn = timeForm.parse("08:00:00");
												correctTimeOut = timeForm.parse("17:00:00");
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											if (correctTimeIn.getTime() - timeInFormatted.getTime() >= 0) {
												//not late
												getHrs = 8.00;
											} else if (correctTimeIn.getTime() - timeInFormatted.getTime() < 0) {
												//late
												howLate = timeInFormatted.getTime() - correctTimeIn.getTime();
												if (howLate < 3600000) {
													deductHrs = deductHrs + 40.00;
													getHrs = 8.00;
												} else if (howLate <= 14400000 && howLate >= 3600000) {
													deductHrs = deductHrs + (Double.parseDouble(basicPay) / 2);
													getHrs = 8.00;
												} else if (howLate >= 14400000 && getHrs <= 4.5) {
													getHrs = getHrs;
													System.out.println("Undertime, so will take from how many hours until 5 pm.");
													undertimeChckr = true;
												}
											}
											
											if (timeOutFormatted.getTime() - correctTimeOut.getTime() > 0) {
												otHrs = otHrs + ((((timeOutFormatted.getTime() - correctTimeOut.getTime()) / (1000*60*60)) % 24));
											}
											
											if (timeOutFormatted.getTime() - correctTimeOut.getTime() < 0 && undertimeChckr == false) {
												long hrsVal = timeOutFormatted.getTime() - correctTimeOut.getTime();
												double convertedUnder = Math.abs(hrsVal) / 3600000.0;
												deductHrs = deductHrs + (convertedUnder * (Double.parseDouble(basicPay)/8));
											}
											
											numHours = numHours + getHrs;
											totalLate = totalLate + (howLate / 3600000.0);
											
											//Populate table
											tableModel.insertRow(tblRow, new Object[] { (String) dates.get(i).get(1), employeeIn, employeeOut, rs.getString("totalHours")});
											tblRow++;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}
					
					tableModel.fireTableDataChanged();
					
					double perHour = Double.parseDouble(basicPay)/8;
					
					numHrs = numHours;
					//Convert hours into days (8 hour/day)
					double hrsToDay = numHours/8;
					
					//Get total pay for that period
					double salary = hrsToDay * Double.parseDouble(basicPay);
					
					BigDecimal bdPagibig = new BigDecimal(salary * 0.06).setScale(2, RoundingMode.HALF_UP);
					double numPagibig = bdPagibig.doubleValue();
					
					BigDecimal bdSss = new BigDecimal(salary * 0.045).setScale(2, RoundingMode.HALF_UP);
					double numSss = bdSss.doubleValue();
					
					BigDecimal bdGross = new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP);
					double numGross = bdGross.doubleValue();
					
					totalPay.setText(Double.toString(numGross));
					textField_2.setText(Double.toString(numSss));
					textField_3.setText(Double.toString(numSss)); //philhealth
					textField_4.setText(Double.toString(numPagibig));
					
					otHours = otHrs;
					double otCharge = otHrs * perHour;
					BigDecimal bdOT = new BigDecimal(otCharge).setScale(2, RoundingMode.HALF_UP);
					double finalOT = bdOT.doubleValue();
					textOvertime.setText(Double.toString(finalOT));
					
					BigDecimal bdDeduct = new BigDecimal(deductHrs).setScale(2, RoundingMode.HALF_UP);
					textField_6.setText(Double.toString(bdDeduct.doubleValue()));
					
					if (rs != null) {
				        try {
				        	rs.close();
				        } catch (SQLException e1) { /* ignored */}
				    }
				    if (conn != null) {
				        try {
				            conn.close();
				        } catch (SQLException e1) { /* ignored */}
				    }
				} else {
					JOptionPane.showMessageDialog(null, "Employee number is invalid!");
				}
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setText("0.00");
		textField_2.setColumns(10);
		textField_2.setBounds(143, 218, 155, 15);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("0.00");
		textField_3.setColumns(10);
		textField_3.setBounds(143, 233, 155, 15);
		add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("0.00");
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
			public void mouseReleased(MouseEvent e) {
				//created PDF document instance   
				Document doc = new Document();
				try  
				{  
					String currentDir = "./";

			        String folderName = "Payslip";
			        Path path = Paths.get(currentDir + folderName);
					
					if (Files.exists(path)) {
			            if (Files.isDirectory(path)) {
			                System.out.println("It is a directory");
			            }
			        } else {
			            System.out.println("File not found.");
			            
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						LocalDateTime now = LocalDateTime.now(); 
						String currDate = dtf.format(now);
						//dateChooser.setDateFormatString(currDate);
			            
			            //Instantiate the File class   
						File f1 = new File(folderName);  
						
						//Creating a folder using mkdir() method  
						boolean bool = f1.mkdir();  
						if(bool){  
							System.out.println("Folder is created successfully"); 

							//generate a PDF at the specified location
							String fileName = "Payslip\\" + fName + lName + "_" + currDate + ".pdf";
							PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileName.replaceAll("\\s", "")));

							//opens the PDF  
							doc.open();
							
							//Create Font objects
					        /*Font headerFont = new Font(Font.MONOSPACED, 20,
					                      Font.ITALIC | Font.BOLD); */
							
							Paragraph preface = new Paragraph("Sweet Dyeiz and Cupcakes"); //update
							//preface.setFont(headerFont);
							preface.setAlignment(Element.ALIGN_CENTER);
							preface.setSpacingAfter(50);
							doc.add(preface);
							
							/*
							Paragraph employeeNum = new Paragraph("Employee Number: " + txtEmployeeNumber.getText());
							Paragraph employeeName = new Paragraph("Name: " + fName + " " + lName);
							Paragraph employeeDate = new Paragraph("Date Generated: " + currDate);
							employeeDate.setSpacingAfter(50);

							//adds paragraph to the PDF file
							doc.add(employeeNum);
							doc.add(employeeName);
							doc.add(employeeDate);
							*/
							
							PdfPTable tableHeader = new PdfPTable(2);
							tableHeader.setWidthPercentage(100);
							tableHeader.addCell(getCell("Employee Number: " + txtEmployeeNumber.getText(), PdfPCell.ALIGN_LEFT));
							tableHeader.addCell(getCell("Total Days: " + Double.toString(Double.parseDouble(totalPay.getText()) / Double.parseDouble(textField_1.getText())), PdfPCell.ALIGN_RIGHT));
							PdfPTable tableHeader2 = new PdfPTable(2);
							tableHeader2.setWidthPercentage(100);
							tableHeader2.addCell(getCell("Name: " + fName + " " + lName, PdfPCell.ALIGN_LEFT));
							tableHeader2.addCell(getCell("Total Hours: " + Double.toString(numHrs + otHours), PdfPCell.ALIGN_RIGHT));
							PdfPTable tableHeader3 = new PdfPTable(2);
							tableHeader3.setWidthPercentage(100);
							tableHeader3.addCell(getCell("Date Generated: " + currDate, PdfPCell.ALIGN_LEFT));
							tableHeader3.addCell(getCell("Total Late Hours: " + Double.toString(totalLate), PdfPCell.ALIGN_RIGHT));
							tableHeader3.setSpacingAfter(50);
							doc.add(tableHeader);
							doc.add(tableHeader2);
							doc.add(tableHeader3);
					        
							PdfPTable table = new PdfPTable(2);
							PdfPCell c1 = new PdfPCell(new Phrase(" "));
					        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

					        table.addCell("BASIC PAY");
					        table.addCell(textField_1.getText());
					        table.addCell(c1);
					        table.addCell(" ");
					        table.addCell("SSS");
					        table.addCell(textField_2.getText());
					        table.addCell("PHILHEALTH");
					        table.addCell(textField_3.getText());
					        table.addCell("PAG-IBIG");
					        table.addCell(textField_4.getText());
					        table.addCell(c1);
					        table.addCell(" ");
					        table.addCell("TAX");
					        table.addCell(textField_5.getText());
					        table.addCell(c1);
					        table.addCell(" ");
					        table.addCell("DEDUCTIONS");
					        table.addCell(textField_6.getText());
					        table.addCell("OVERTIME");
					        table.addCell(textOvertime.getText());
					        table.addCell(c1);
					        table.addCell(" ");
					        table.addCell("GROSS");
					        table.addCell(totalPay.getText());
					        table.addCell("NET");
					        table.addCell(textField_8.getText());

					        doc.add(table);
							
							//close the PDF file  
							doc.close();  
							//closes the writer  
							writer.close();
							
							JOptionPane.showMessageDialog(null, "Payslip generated.");
						}else{  
							//System.out.println("Error Found!");
							JOptionPane.showMessageDialog(null, "Error Found!");
						}  
			        }
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
			public void mouseReleased(MouseEvent e) {
				double grossPay = Double.parseDouble(totalPay.getText());
				double overtime = Double.parseDouble(textOvertime.getText());
				double sss = Double.parseDouble(textField_2.getText());
				double philhealth = Double.parseDouble(textField_3.getText());
				double pagibig = Double.parseDouble(textField_4.getText());
				double tax = Double.parseDouble(textField_5.getText());
				double deductions = Double.parseDouble(textField_6.getText());
				
				double netPay = (grossPay + overtime) - (deductions + tax + pagibig + philhealth + sss);
				//textField_8.setText(String.valueOf(netPay));
				
				BigDecimal netPayConverted = new BigDecimal(netPay).setScale(2, RoundingMode.HALF_UP);
				textField_8.setText(String.valueOf(netPayConverted.doubleValue()));
				 
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
		
		tableModel.addColumn("Date");
		tableModel.addColumn("Time in");
		tableModel.addColumn("Time out");
		tableModel.addColumn("Total Hours");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(342, 180, 348, 219);
		add(scrollPane);
		
		table_1 = new JTable(tableModel);
		scrollPane.setViewportView(table_1);
	}
	
	public PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
}
