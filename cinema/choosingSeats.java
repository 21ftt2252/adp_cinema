package cinema;

import javax.swing.JPanel;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

public class choosingSeats extends JPanel implements ActionListener {

	private static String username = "root";
	private static String password = "cmaeerica";
	static JButton next;

	//seats
	public static JButton twin1;
	public static JButton twin2;

	public static JButton A01;
	public static JButton A03;
	public static JButton A02;
	public static JButton A04;

	public static JButton B01;
	public static JButton B02;
	public static JButton B03;
	public static JButton B04;

	public static JButton C01;
	public static JButton C02;
	public static JButton C03;
	public static JButton C04;

	public static ArrayList<String> seatingsPosition = new ArrayList<String>();
	public static String SP;
	public static StringBuffer sb;
	public static String columnData;

	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement pstmt = null;
	Statement statement = null;

	//ticket
	static int sp = 0;
	private JLabel lblNewLabel_2;
	private Panel yourSeat;
	private Panel Available;
	private Panel Sold;
	private Panel Twin;
	private JLabel lblNewLabel_3;

	//timer
	public static Timer timer;
	public static int countdown;
	public static JLabel Timer;
	
	private static Movie movie;
	private static String title;
	private static String date;
	private static String time;

	JPanel csPanel = new JPanel();
	private static JLabel movieName;
	private static JLabel dateSched;
	private static JLabel timeSched;

	/**
	 * Create the panel.
	 */
	public choosingSeats() {
		setBounds(100, 100, 1260, 628);
		setLayout(null);

		twin1 = new JButton("Twin1");
		twin1.setName("seats");
		twin1.setBackground(new Color(255, 255, 0));
		twin1.setBounds(470, 538, 89, 23);
		twin1.addActionListener(this);
		add(twin1);

		twin2 = new JButton("Twin2");
		twin2.setName("seats");
		twin2.setBackground(new Color(255, 255, 0));
		twin2.setBounds(667, 538, 89, 23);
		twin2.addActionListener(this);
		add(twin2);

		A01 = new JButton("A01");
		A01.setName("seats");
		A01.setBackground(new Color(192, 192, 192));
		A01.setBounds(470, 494, 64, 32);
		A01.addActionListener(this);
		add(A01);

		A02 = new JButton("A02");
		A02.setName("seats");
		A02.setBackground(new Color(192, 192, 192));
		A02.setBounds(544, 494, 64, 33);
		A02.addActionListener(this);
		add(A02);

		A04 = new JButton("A04");
		A04.setName("seats");
		A04.setBackground(new Color(192, 192, 192));
		A04.setBounds(692, 494, 64, 33);
		A04.addActionListener(this);
		add(A04);

		B01 = new JButton("B01");
		B01.setName("seats");
		B01.setBackground(new Color(192, 192, 192));
		B01.setBounds(470, 451, 64, 32);
		B01.addActionListener(this);
		add(B01);

		C01 = new JButton("C01");
		C01.setName("seats");
		C01.setBackground(new Color(192, 192, 192));
		C01.setBounds(470, 408, 64, 32);
		C01.addActionListener(this);
		add(C01);

		B02 = new JButton("B02");
		B02.setName("seats");
		B02.setBackground(new Color(192, 192, 192));
		B02.setBounds(544, 451, 64, 32);
		B02.addActionListener(this);
		add(B02);

		B03 = new JButton("B03");
		B03.setName("seats");
		B03.setBackground(new Color(192, 192, 192));
		B03.setBounds(618, 451, 64, 33);
		B03.addActionListener(this);
		add(B03);

		B04 = new JButton("B04");
		B04.setName("seats");
		B04.setBackground(new Color(192, 192, 192));
		B04.setBounds(692, 451, 64, 33);
		B04.addActionListener(this);
		add(B04);

		C02 = new JButton("C02");
		C02.setName("seats");
		C02.setBackground(new Color(192, 192, 192));
		C02.setBounds(544, 408, 64, 32);
		C02.addActionListener(this);
		add(C02);

		C03 = new JButton("C03");
		C03.setName("seats");
		C03.setBackground(new Color(192, 192, 192));
		C03.setBounds(618, 408, 64, 32);
		C03.addActionListener(this);
		add(C03);

		C04 = new JButton("C04");
		C04.setName("seats");
		C04.setBackground(new Color(192, 192, 192));
		C04.setBounds(692, 408, 64, 32);
		C04.addActionListener(this);
		add(C04);

		A03 = new JButton("A03");
		A03.setName("seats");
		A03.setBackground(new Color(192, 192, 192));
		A03.setBounds(618, 494, 64, 33);
		A03.addActionListener(this);
		add(A03);

		Panel panel = new Panel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(439, 348, 353, 43);
		add(panel);
		panel.setLayout(null);

		JLabel screen = new JLabel("Screen");
		screen.setHorizontalAlignment(SwingConstants.CENTER);
		screen.setFont(new Font("Arial", Font.PLAIN, 18));
		screen.setBounds(146, 11, 57, 21);
		panel.add(screen);

		JLabel lblNewLabel = new JLabel("Your Seat");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(470, 328, 64, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Available");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(576, 328, 70, 14);
		add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Sold");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(684, 328, 35, 14);
		add(lblNewLabel_2);

		yourSeat = new Panel();
		yourSeat.setBackground(new Color(255, 0, 0));
		yourSeat.setBounds(439, 328, 26, 14);
		add(yourSeat);

		Available = new Panel();
		Available.setBackground(new Color(192, 192, 192));
		Available.setBounds(544, 328, 26, 14);
		add(Available);

		Sold = new Panel();
		Sold.setBackground(new Color(0, 0, 0));
		Sold.setBounds(652, 328, 26, 14);
		add(Sold);

		Twin = new Panel();
		Twin.setBackground(new Color(255, 255, 0));
		Twin.setBounds(730, 328, 26, 14);
		add(Twin);

		lblNewLabel_3 = new JLabel("Twin");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(762, 329, 35, 14);
		add(lblNewLabel_3);

		JPanel TopPanel = new JPanel();
		TopPanel.setBackground(new Color(0,103,76,255));
		TopPanel.setBounds(0, 0, 1281, 110);
		TopPanel.setPreferredSize(new Dimension(1260,1000));
		add(TopPanel);
		TopPanel.setLayout(null);
		
        ImageIcon imgLogoTemp = new ImageIcon("image/logo.png");
        Image imgLogoImage = imgLogoTemp.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); 
        imgLogoTemp = new ImageIcon(imgLogoImage);
        JLabel imgLogo = new JLabel(imgLogoTemp);
        imgLogo.setPreferredSize(new Dimension(350,200));
        imgLogo.setBounds(537, 11, 315, 88);
        TopPanel.add(imgLogo);
		
		JButton backBtn = new JButton("BACK");
		backBtn.addActionListener(this);
		backBtn.setBounds(10, 76, 89, 23);
		TopPanel.add(backBtn);
		
		next = new JButton("NEXT");
		next.setBounds(1180, 76, 77, 23);
		TopPanel.add(next);
		next.setName("Next");
		next.addActionListener(this);

		Panel SelectTickets = new Panel();
		SelectTickets.setBackground(new Color(187, 187, 187));
		SelectTickets.setBounds(0, 110, 324, 48);
		add(SelectTickets);
		SelectTickets.setLayout(null);

		JLabel TicketPage = new JLabel("Select Tickets");
		TicketPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TicketPage.setBounds(121, 11, 82, 26);
		SelectTickets.add(TicketPage);

		Panel SelectSeats = new Panel();
		SelectSeats.setBackground(new Color(180, 180, 180));
		SelectSeats.setBounds(322, 110, 307, 48);
		add(SelectSeats);
		SelectSeats.setLayout(null);

		JLabel SeatsPage = new JLabel("Select Seats");
		SeatsPage.setBackground(new Color(170, 170, 170));
		SeatsPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		SeatsPage.setHorizontalAlignment(SwingConstants.CENTER);
		SeatsPage.setBounds(103, 11, 118, 26);
		SelectSeats.add(SeatsPage);

		Panel Confirm = new Panel();
		Confirm.setBackground(new Color(187, 187, 187));
		Confirm.setBounds(628, 110, 300, 48);
		add(Confirm);
		Confirm.setLayout(null);

		JLabel ConfirmPage = new JLabel("Confirm");
		ConfirmPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ConfirmPage.setHorizontalAlignment(SwingConstants.CENTER);
		ConfirmPage.setBounds(102, 11, 99, 26);
		Confirm.add(ConfirmPage);

		Panel BookingSuccessful = new Panel();
		BookingSuccessful.setBackground(new Color(187, 187, 187));
		BookingSuccessful.setBounds(928, 110, 353, 48);
		add(BookingSuccessful);
		BookingSuccessful.setLayout(null);

		JLabel BookingPage = new JLabel("Booking Successful");
		BookingPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BookingPage.setBounds(117, 5, 118, 32);
		BookingSuccessful.add(BookingPage);

		Timer = new JLabel("Time Left: 10:00");
		Timer.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		Timer.setBounds(1048, 165, 172, 33);
		add(Timer);
		
		movieName = new JLabel("name");
		movieName.setBounds(40, 195, 669, 32);
		add(movieName);
		
		dateSched = new JLabel("Schedule");
		dateSched.setBounds(40, 246, 172, 14);
		add(dateSched);
		
		timeSched = new JLabel("");
		timeSched.setBounds(212, 246, 81, 14);
		add(timeSched);


		setVisible(true);

	}
	
	public void setMovie(Movie movie) throws SQLException {
		this.movie = movie;
		movieName.setText(movie.getTitle());
		dateSched.setText(movie.getDate() + " at ");
		timeSched.setText(movie.getTime());	
	    repaint(); // Refresh the UI with the new movie details		
	}			
	
	public static void booked() {
		String movie = movieName.getText();	
		String timeString = timeSched.getText();
		    
	    sb = new StringBuffer();
	 
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);
	        String query = "SELECT position FROM cinema WHERE cinema_id = "
	        		+ "	 (SELECT ms.cinema_id "
	        		+ "	 FROM movie_schedule ms "
	        		+ "	 JOIN movie m ON ms.movie_id = m.movie_id "
	        		+ "	 WHERE m.movie_title = ? "
	        		+ "	 AND ms.movie_time = ? )";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, movie);
	        stmt.setString(2, timeString);
	        
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String columnData = rs.getString("position");
	            String[] SP = columnData.split(",");
	            for (String s : SP) {
	                if (s.equals("A01")) {
	                    A01.setBackground(Color.BLACK);
	                    A01.setEnabled(false);
	                } else if (s.equals("A02")) {
	                    A02.setBackground(Color.BLACK);
	                    A02.setEnabled(false);
	                } else if (s.equals("A03")) {
	                    A03.setBackground(Color.BLACK);
	                    A03.setEnabled(false);
	                } else if (s.equals("A04")) {
	                    A04.setBackground(Color.BLACK);
	                    A04.setEnabled(false);
	                } else if (s.equals("B01")) {
	                    B01.setBackground(Color.BLACK);
	                    B01.setEnabled(false);
	                } else if (s.equals("B02")) {
	                    B02.setBackground(Color.BLACK);
	                    B02.setEnabled(false);
	                } else if (s.equals("B03")) {
	                    B03.setBackground(Color.BLACK);
	                    B03.setEnabled(false);
	                } else if (s.equals("B04")) {
	                    B04.setBackground(Color.BLACK);
	                    B04.setEnabled(false);
	                } else if (s.equals("C01")) {
	                    C01.setBackground(Color.BLACK);
	                    C01.setEnabled(false);
	                } else if (s.equals("C02")) {
	                    C02.setBackground(Color.BLACK);
	                    C02.setEnabled(false);
	                } else if (s.equals("C03")) {
	                    C03.setBackground(Color.BLACK);
	                    C03.setEnabled(false);
	                } else if (s.equals("C04")) {
	                    C04.setBackground(Color.BLACK);
	                    C04.setEnabled(false);
	                } else if (s.equals("twin1")) {
	                    twin1.setBackground(Color.BLACK);
	                    twin1.setEnabled(false);
	                } else if (s.equals("twin2")) {
	                    twin2.setBackground(Color.BLACK);
	                    twin2.setEnabled(false);
	                }
	            }
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton o = (JButton)e.getSource();
		String buttonName = o.getName();
		String code = o.getText();
		List<JButton> nonRedButtons = new ArrayList<>();

		nonRedButtons.add(A01);
		nonRedButtons.add(A02);
		nonRedButtons.add(A03);
		nonRedButtons.add(A04);
		
		nonRedButtons.add(B01);
		nonRedButtons.add(B02);
		nonRedButtons.add(B03);
		nonRedButtons.add(B04);
		
		nonRedButtons.add(C01);
		nonRedButtons.add(C02);
		nonRedButtons.add(C03);
		nonRedButtons.add(C04);
		
		nonRedButtons.add(twin1);
		nonRedButtons.add(twin2);


		if(buttonName.equals("seats")) {
			if(o.getBackground() != Color.red) {
				o.setBackground(Color.red);
				seatingsPosition.add(code);
				nonRedButtons.remove(o);
				sp++;
			} 
			else if(o.getBackground() == Color.red) {
				o.setBackground(new Color(192, 192, 192));
				seatingsPosition.remove(code);
				sp--;
				
				if (code.equals("Twin1")) {
					twin1.setBackground(new Color(255, 255, 0));
					seatingsPosition.remove(code);
					sp--;
				}
				 if(code.equals("Twin2")){
					twin2.setBackground(new Color(255, 255, 0));
					seatingsPosition.remove(code);
					sp--;
				 }
			} 
			
		}
		
		//need the instance of the totalQuantityTicket
		if (sp == ticket.totalQuantityTicket) {
		    for (JButton button : nonRedButtons) {
		        button.setEnabled(false);
		    }
		}

		if(code.equals("Back")) {
			Start.pages(7);
		}

		if (code.equals("Next")) {
			Start.pages(9);
			toDatabase();
			return;
		}
	}

	public void toDatabase() {		
		String userEmail = Login.getTxtEAddress().getText();

	    String movie = movieName.getText();
	    String time = timeSched.getText(); 
	   
	    sb = new StringBuffer();
	    for (String s : seatingsPosition) {
	        sb.append(s);
	        sb.append(",");
	    }
	    SP = sb.toString();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);
	        connection.setAutoCommit(false);
	        String sql = "UPDATE cinema SET position = ? WHERE purchase_history_id = (SELECT purchase_history_id FROM purchase_history "
	                +     " WHERE user_id = (SELECT user_id FROM user WHERE user_email = ?)) AND cinema_id = "
	                + " (SELECT ms.cinema_id "
	                + " FROM movie_schedule ms "
	                + " JOIN movie m ON ms.movie_id = m.movie_id "
	                + " WHERE m.movie_title = ? "
	                + " AND ms.movie_time = ? )";
	        PreparedStatement stmt = connection.prepareStatement(sql);

	        stmt.setString(1, SP);
	        stmt.setString(2, userEmail);
	        stmt.setString(3, movie);
	        stmt.setString(4, time);
	        stmt.executeUpdate();
	        connection.commit(); // Commit changes to the database

	        System.out.println("Elements inserted successfully");
	    } catch (SQLException e) {
	        if (connection != null) {
	            try {
	                connection.rollback(); // Rollback changes if an exception is thrown
	            } catch (SQLException ex) {
	                System.out.println("Rollback failed: " + ex.getMessage());
	            }
	        }
	        System.out.println("Database error: " + e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to close connection: " + e.getMessage());
	        }
	    }
	}
}