import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PanelDailylogs extends JPanel {
	private JTextField txtEmployeeNumber;
	private JTable table;
	private JLabel lblClock;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now(); 
	String dateToday = dtf.format(now);

	/**
	 * Create the panel.
	 */
	public PanelDailylogs() {
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
		txtEmployeeNumber.setBounds(27, 117, 191, 30);
		add(txtEmployeeNumber);
		txtEmployeeNumber.setColumns(10);
		
		txtEmployeeNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtEmployeeNumber.getText().equals("Last Name") || txtEmployeeNumber.getText().equals("First Name") || txtEmployeeNumber.getText().equals("Employee Number")) {
					txtEmployeeNumber.setText("");
				}
				else {
					txtEmployeeNumber.selectAll();
				}
			}
		});
		
		JComboBox<String> cmbbxInfo = new JComboBox<String>();
		
		// add items to the combo box
		cmbbxInfo.addItem("Employee Number");
		cmbbxInfo.addItem("First Name");
		cmbbxInfo.addItem("Last Name");
		
		cmbbxInfo.setBounds(250, 117, 191, 31);
		add(cmbbxInfo);
		
		// check what will be chosen on combo box
		cmbbxInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEmployeeNumber.setText(cmbbxInfo.getSelectedItem().toString());
			}
		});
		
		JButton btnTimein = new JButton("TIME IN");
		btnTimein.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String employeeData=txtEmployeeNumber.getText();
				
				int countOfTimeIn = 0;
				int countOfUser = 0;
				
				try {
					//check if user exists
					pst = conn.prepareStatement("SELECT employeeNum FROM employeeInfo WHERE employeeNum = '"+employeeData+"'");
					rs = pst.executeQuery();

					while (rs.next()) {
						countOfUser++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if (countOfUser > 0) {
					try {
						//check if already timed in
						pst = conn.prepareStatement("SELECT employeeNum, dateIn FROM payrollInfo WHERE employeeNum = '"+employeeData+"' AND dateIn = '"+dateToday+"'");
						rs = pst.executeQuery();

						while (rs.next()) {
							countOfTimeIn++;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					if (countOfTimeIn == 0) {
						//Initialize SQL statement
						Statement statement = null;
						
						dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
						now = LocalDateTime.now(); 
						String timeToday = dtf.format(now);
						
						//Create SQL statement
						String query = "INSERT INTO payrollInfo ('employeeNum', 'dateIn', 'timeIn') VALUES ('"+employeeData+"','"+dateToday+"','"+timeToday+"')";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e2) {
							JOptionPane.showMessageDialog(null, e2.toString());
						}
						
						table_load();
					} else {
						JOptionPane.showMessageDialog(null, "Employee has logged in already!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Employee number is invalid!");
				}
			}
		});
		btnTimein.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimein.setBounds(464, 117, 104, 27);
		add(btnTimein);
		
		
		JButton btnTimeout = new JButton("TIME OUT");
		btnTimeout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String employeeData=txtEmployeeNumber.getText();
				
				int countOfTimeIn = 0;
				int countOfUser = 0;
				
				try {
					//check if user exists
					pst = conn.prepareStatement("SELECT employeeNum FROM employeeInfo WHERE employeeNum = '"+employeeData+"'");
					rs = pst.executeQuery();

					while (rs.next()) {
						countOfUser++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if (countOfUser > 0) {
					try {
						//check if already timed in
						pst = conn.prepareStatement("SELECT employeeNum, dateIn FROM payrollInfo WHERE employeeNum = '"+employeeData+"' AND dateIn = '"+dateToday+"'");
						rs = pst.executeQuery();

						while (rs.next()) {
							countOfTimeIn++;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					if (countOfTimeIn > 0) {
						//Initialize SQL statement
						Statement statement = null;
						
						//Get time in
						String timeIn = "";
						try {
							pst = conn.prepareStatement("SELECT timeIn FROM payrollInfo WHERE employeeNum = '"+employeeData+"' AND dateIn = '"+dateToday+"'");
							rs = pst.executeQuery();
							
							while (rs.next()) {
								timeIn = rs.getString("timeIn");
							}
						} catch (SQLException e2) {
							JOptionPane.showMessageDialog(null, e2.toString());
						}
						
						//Add time out and total hours to user's record
						//Initialize current time
						dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
						now = LocalDateTime.now(); 
						String timeToday = dtf.format(now);
						
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						Date timeTodayFormatted = null;
						try {
							timeTodayFormatted = sdf.parse(timeToday);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//Format time in
						Date timeInFormatted = null;
						try {
							timeInFormatted = sdf.parse(timeIn);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//Get difference between time out and time in in milliseconds
						double totHours = timeTodayFormatted.getTime() - timeInFormatted.getTime();
						
						//Convert milliseconds to hours
						double hours = ((totHours / (1000*60*60)) % 24);
						BigDecimal bd = new BigDecimal(hours).setScale(2, RoundingMode.HALF_UP);
						double newNum = bd.doubleValue();  
						
						//Update in database
						String query = "UPDATE payrollInfo SET dateOut= '"+dateToday+"', timeOut = '"+timeToday+"', totalHours = '"+newNum+"' WHERE employeeNum ='"+employeeData+"' AND dateIn = '"+dateToday+"'";
						
						try {
							statement = conn.createStatement();
							statement.execute(query);
						} catch (SQLException e2) {
							JOptionPane.showMessageDialog(null, e2.toString());
						}
						
						table_load();
					} else {
						JOptionPane.showMessageDialog(null, "Employee has not yet logged in!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Employee number is invalid!");
				}
			}
		});
		btnTimeout.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimeout.setBounds(592, 117, 98, 27);
		add(btnTimeout);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 220, 619, 247);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("ATTENDANCE FOR TODAY");
		lblNewLabel.setBounds(39, 189, 168, 20);
		add(lblNewLabel);
		
		lblClock = new JLabel("2012-11-14  08:02:53");
		lblClock.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblClock.setBounds(27, 32, 663, 55);
		add(lblClock);
		
		clock();
		table_load();
	}
	
	public void clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					for (;;) {
						Calendar cal = new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						
						lblClock.setText(year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second);
						
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		clock.start();
	}

	public void table_load()
	{
		//populates table
		try {
			pst = conn.prepareStatement("SELECT employeeNum, timeIn, timeOut FROM payrollInfo WHERE dateIn = '"+dateToday+"'");
			//pst = conn.prepareStatement("SELECT employeeNum, timeIn, timeOut, LASTNAME, FIRSTNAME FROM payrollInfo INNER JOIN employeeInfo on employeeInfo.EMPLOYEENUM = payrollInfo.employeeNum WHERE dateIn = '"+dateToday+"'");
			rs = pst.executeQuery();
			table.setModel (DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.addSuppressed(e);
		}
	}
}
