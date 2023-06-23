import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
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
import java.awt.Color;
import javax.swing.border.LineBorder;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import java.time.LocalDateTime;

public class PanelHome extends JPanel {
	
	private Image img_employees = new ImageIcon(Login.class.getResource("images/crowd.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_percentage = new ImageIcon(Login.class.getResource("images/data.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_ontime = new ImageIcon(Login.class.getResource("images/clock.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_late = new ImageIcon(Login.class.getResource("images/warning.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(Login.class.getResource("images/woman.png")).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
	private Image img_refresh = new ImageIcon(Login.class.getResource("images/refresh.png")).getImage().getScaledInstance(17,17, Image.SCALE_SMOOTH);
	
	private JLabel lblTotalemp;
	private JLabel lblAbsenttod;
	private JLabel lblOntime;
	private JLabel lblLatetod;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	DefaultTableModel tableModel = new DefaultTableModel();
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now(); 
	String dateToday = dtf.format(now);
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelHome() {
		setBackground(new Color(255, 182, 193));
		setBounds(0, 0, 700,524);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(11, 86, 169, 92);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblttlemployee = new JLabel("TOTAL EMPLOYEE");
		lblttlemployee.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblttlemployee.setBounds(10, 65, 149, 20);
		panel.add(lblttlemployee);
		
		JLabel lblTotalEmployee = new JLabel("");
		lblTotalEmployee.setIcon(new ImageIcon (img_employees));
		lblTotalEmployee.setBounds(110, 11, 45, 45);
		panel.add(lblTotalEmployee);
		
		lblTotalemp = new JLabel("0");
		lblTotalemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTotalemp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalemp.setBounds(23, 11, 55, 43);
		panel.add(lblTotalemp);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(181, 86, 169, 92);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAbsent = new JLabel("ABSENT TODAY");
		lblAbsent.setBounds(10, 65, 134, 16);
		lblAbsent.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_1.add(lblAbsent);
		
		JLabel lblOnTIME = new JLabel("");
		lblOnTIME.setIcon(new ImageIcon(img_percentage));
		lblOnTIME.setBounds(110, 11, 45, 45);
		panel_1.add(lblOnTIME);
		
		lblAbsenttod = new JLabel("0");
		lblAbsenttod.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbsenttod.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAbsenttod.setBounds(25, 11, 55, 43);
		panel_1.add(lblAbsenttod);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(351, 86, 169, 92);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblOnTime = new JLabel("ON TIME TODAY");
		lblOnTime.setBounds(10, 65, 99, 16);
		lblOnTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(lblOnTime);
		
		JLabel lblOnTODAY = new JLabel("");
		lblOnTODAY.setIcon(new ImageIcon(img_ontime));
		lblOnTODAY.setBounds(110, 11, 45, 45);
		panel_2.add(lblOnTODAY);
		
		lblOntime = new JLabel("0");
		lblOntime.setHorizontalAlignment(SwingConstants.CENTER);
		lblOntime.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOntime.setBounds(27, 11, 55, 43);
		panel_2.add(lblOntime);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(521, 86, 169, 92);
		add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblLateToday = new JLabel("LATE TODAY");
		lblLateToday.setBounds(10, 65, 109, 16);
		lblLateToday.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_3.add(lblLateToday);
		
		JLabel lblLate = new JLabel("");
		lblLate.setIcon(new ImageIcon (img_late));
		lblLate.setBounds(110, 11, 45, 45);
		panel_3.add(lblLate);
		
		lblLatetod = new JLabel("0");
		lblLatetod.setHorizontalAlignment(SwingConstants.CENTER);
		lblLatetod.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLatetod.setBounds(25, 11, 55, 43);
		panel_3.add(lblLatetod);
		
		JLabel lblMonthlyattendance = new JLabel("MONTHLY ATTENDANCE REPORT");
		lblMonthlyattendance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMonthlyattendance.setBounds(21, 200, 329, 14);
		add(lblMonthlyattendance);
		
		JLabel lblUser = new JLabel("");
		lblUser.setIcon(new ImageIcon(img_user));
		lblUser.setBounds(640, 11, 46, 45);
		add(lblUser);
		
		JLabel lblNewLabel = new JLabel("Chef Jelv");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(629, 48, 46, 14);
		add(lblNewLabel);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		panel_4.setBackground(new Color(255, 182, 193));
		panel_4.setBounds(25, 48, 35, 25);
		add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				updateDshbrd();
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon (img_refresh));
		lblNewLabel_1.setBounds(5, 4, 17, 17);
		panel_4.add(lblNewLabel_1);
		
		tableModel.addColumn("Rank");
		tableModel.addColumn("Employee ID");
		tableModel.addColumn("Name");
		tableModel.addColumn("Total Days");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 258, 563, 214);
		add(scrollPane);
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		setVisible(true);

		updateDshbrdAuto();
	}
	
	public void updateDshbrd () {
		try {
			//initiate connection with database
			conn = DriverManager.getConnection("jdbc:sqlite:sjDatabase.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int numberEmp = 0;
		int numberAbs = 0;
		int onTime = 0;
		int numberLate = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", java.util.Locale.ENGLISH);
		Date dateFrmttd = null;
		Date correctTimeIn = null;
		
		//populates total employee
		try {
			pst = conn.prepareStatement("select * from employeeInfo WHERE status = 'ACTIVE'");
			rs = pst.executeQuery();

			while (rs.next()) {
				numberEmp++;
			}
		}
		catch (SQLException e)
		{
			e.addSuppressed(e);
		}
		
		lblTotalemp.setText(String.valueOf(numberEmp));
		
		//populates absent today
		try {
			pst = conn.prepareStatement("SELECT timeIn FROM payrollInfo WHERE dateIn = '"+dateToday+"'");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				numberAbs++;
				
				try {
					dateFrmttd = sdf.parse(rs.getString("timeIn"));
					correctTimeIn = sdf.parse("08:00:00");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (correctTimeIn.getTime() - dateFrmttd.getTime() >= 0) {
					//not late
					onTime++;
				} else {
					numberLate++;
				}
			}
		}
		catch (SQLException e)
		{
			e.addSuppressed(e);
		}
		
		lblAbsenttod.setText(String.valueOf(numberEmp - numberAbs));
		lblOntime.setText(String.valueOf(onTime));
		lblLatetod.setText(String.valueOf(numberLate));
		
		//Update data in table
		List<List> dates = new ArrayList<List>();
		try {
			pst = conn.prepareStatement("SELECT ID, employeeNum, dateIn FROM payrollInfo");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				List list1=new ArrayList();
				list1.add(rs.getString("ID"));
				list1.add(rs.getString("employeeNum"));
				list1.add(rs.getString("dateIn"));
				dates.add(list1);
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
		
		LocalDateTime now = LocalDateTime.now(); 
		
		//get month only
		DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("MM");
		String monthToday = dtfMonth.format(now);
		
		//get year only
		DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("yyyy");
		String yearToday = dtfYear.format(now);
		
		int dateSize = dates.size();
		
		for (int i = 0; dateSize > i; i++) {
			String getDate = (String) dates.get(i).get(2);
			String strGetDate[] = getDate.split("-");
			int yearStr = Integer.parseInt(strGetDate[0]);
			int monthStr = Integer.parseInt(strGetDate[1]);
			
			if (monthStr == Integer.parseInt(monthToday) && yearStr == Integer.parseInt(yearToday)) {
				String list2[] = new String[3];
				list2[0] = (String) dates.get(i).get(0); //Add to rank - update
			}
		}
	}
	
	public void updateDshbrdAuto () {
		Thread updateDshbrdAuto = new Thread() {
			public void run() {
				try {
					for (;;) {
						updateDshbrd();
						
						sleep(1800000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		updateDshbrdAuto.start();
	}
}
