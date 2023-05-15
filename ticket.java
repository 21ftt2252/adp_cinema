package cinema;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;

public class ticket extends JPanel implements ActionListener{

	public JTextField adult;
	public JTextField child;
	public JTextField twin;
	public JLabel Subtotal;
	static public JLabel adultPrice;
	static public JLabel kidPrice;
	static public JLabel twinPrice;

	static JButton adultInc;
	static JButton adultDec;
	static JButton kidInc;
	static JButton kidDec;
	static JButton twinInc;
	static JButton twinDec;

	static int a = 0;
	static int k = 0;
	static int t = 0;
	static int aPrice = 4;
	static int kPrice = 3;
	static int tPrice = 10;
	static int tp = 0;
	public static int adultSubTotal;
	public static int kidSubTotal;
	public static int twinSubTotal;
	public static int totalQuantityTicket;

	public JButton next;
	static JButton o;
	static String button;
	 
//	public static JLabel title;
	public static JLabel dateSched;
	public static JLabel timeSched;
	
	private String username = "root";
	private String password = "cmaeerica";
	
    Connection connection = null;
	ResultSet resultSet = null;
    PreparedStatement pstmt = null;
    Statement statement = null;
	
	Color mainMenuBG= new Color(0,103,76,255);
	Color panelBG = new Color (236,236,235);
	
	private JLabel titleLabel;
	private JTextArea description;
	private JLabel imgLabel;

	private Movie movie;
	private String title;
	private String date;
	private String time;
	
	private choosingSeats choosingSeats;
	
    JPanel imagePanel = new JPanel();
    String movieId;
    
	//for timer
	public JButton start;
	public static Timer timer;
	public static int countdown;
	public static JLabel Timer;
	
		public ticket(choosingSeats choosingSeats) {
			this.choosingSeats = choosingSeats;
			
			setBounds(100, 100, 1260, 628);
			setLayout(null);

			adultDec = new JButton("-");
			adultDec.setBackground(new Color(122, 122, 122));
			adultDec.setFont(new Font("Tahoma", Font.PLAIN, 16));
			adultDec.setName("adultDec");
			adultDec.setBounds(414, 550, 48, 33);
			adultDec.addActionListener(this);
			add(adultDec);

			adult = new JTextField();
			adult.setHorizontalAlignment(SwingConstants.CENTER);
			adult.setBounds(472, 556, 39, 20);
			adult.setEditable(false);
			add(adult);
			adult.setColumns(10);

			adultInc = new JButton("+");
			adultInc.setBackground(new Color(122, 122, 122));
			adultInc.setFont(new Font("Tahoma", Font.PLAIN, 16));
			adultInc.setName("adultInc");
			adultInc.setBounds(521, 550, 48, 33);
			adultInc.addActionListener(this);
			add(adultInc);

			kidDec = new JButton("-");
			kidDec.setBackground(new Color(122, 122, 122));
			kidDec.setFont(new Font("Tahoma", Font.PLAIN, 16));
			kidDec.setName("kidDec");
			kidDec.setBounds(414, 594, 48, 33);
			kidDec.addActionListener(this);
			add(kidDec);

			child = new JTextField();
			child.setHorizontalAlignment(SwingConstants.CENTER);
			child.setColumns(10);
			child.setEditable(false);
			child.setBounds(472, 600, 39, 20);
			add(child);

			kidInc = new JButton("+");
			kidInc.setBackground(new Color(122, 122, 122));
			kidInc.setFont(new Font("Tahoma", Font.PLAIN, 16));
			kidInc.setName("kidInc");
			kidInc.setBounds(521, 594, 48, 33);
			kidInc.addActionListener(this);
			add(kidInc);

			twinDec = new JButton("-");
			twinDec.setBackground(new Color(122, 122, 122));
			twinDec.setFont(new Font("Tahoma", Font.PLAIN, 16));
			twinDec.setName("twinDec");
			twinDec.setBounds(414, 638, 48, 33);
			twinDec.addActionListener(this);
			add(twinDec);

			twin = new JTextField();
			twin.setHorizontalAlignment(SwingConstants.CENTER);
			twin.setColumns(10);
			twin.setEditable(false);
			twin.setBounds(472, 644, 39, 20);
			add(twin);

			twinInc = new JButton("+");
			twinInc.setBackground(new Color(122, 122, 122));
			twinInc.setFont(new Font("Tahoma", Font.PLAIN, 16));
			twinInc.setName("twinInc");
			twinInc.setBounds(521, 638, 48, 33);
			twinInc.addActionListener(this);
			add(twinInc);

			Subtotal = new JLabel("Subtotal");
			Subtotal.setHorizontalAlignment(SwingConstants.CENTER);
			Subtotal.setBounds(598, 520, 49, 14);
			add(Subtotal);

			adultPrice = new JLabel("0");
			adultPrice.setHorizontalAlignment(SwingConstants.CENTER);
			adultPrice.setBounds(598, 559, 49, 14);
			add(adultPrice);

			kidPrice = new JLabel("0");
			kidPrice.setHorizontalAlignment(SwingConstants.CENTER);
			kidPrice.setBounds(598, 603, 49, 14);
			add(kidPrice);

			twinPrice = new JLabel("0");
			twinPrice.setHorizontalAlignment(SwingConstants.CENTER);
			twinPrice.setBounds(598, 647, 49, 14);
			add(twinPrice);

			//topDisplay
			JLabel Quantity = new JLabel("Quantity");
			Quantity.setHorizontalAlignment(SwingConstants.CENTER);
			Quantity.setBounds(462, 520, 62, 14);
			add(Quantity);

			JLabel kidCost = new JLabel("$3.00");
			kidCost.setHorizontalAlignment(SwingConstants.CENTER);
			kidCost.setBounds(297, 603, 62, 14);
			add(kidCost);

			JLabel Cost = new JLabel("Cost");
			Cost.setHorizontalAlignment(SwingConstants.CENTER);
			Cost.setBounds(297, 520, 62, 14);
			add(Cost);

			JLabel adultCost = new JLabel("$4.00");
			adultCost.setHorizontalAlignment(SwingConstants.CENTER);
			adultCost.setBounds(297, 559, 62, 14);
			add(adultCost);

			JLabel twinCost = new JLabel("$10.00");
			twinCost.setHorizontalAlignment(SwingConstants.CENTER);
			twinCost.setBounds(297, 647, 62, 14);
			add(twinCost);

			JLabel Tickets = new JLabel("Tickets");
			Tickets.setHorizontalAlignment(SwingConstants.LEFT);
			Tickets.setBounds(101, 520, 62, 14);
			add(Tickets);

			JLabel Adult = new JLabel("Adult");
			Adult.setHorizontalAlignment(SwingConstants.LEFT);
			Adult.setBounds(101, 559, 62, 14);
			add(Adult);

			JLabel Kid = new JLabel("Kid");
			Kid.setHorizontalAlignment(SwingConstants.LEFT);
			Kid.setBounds(101, 603, 62, 14);
			add(Kid);

			JLabel Twin = new JLabel("Twin");
			Twin.setHorizontalAlignment(SwingConstants.LEFT);
			Twin.setBounds(101, 647, 62, 14);
			add(Twin);

			JPanel panel = new JPanel();
			panel.setBackground(new Color(192, 192, 192));
			panel.setBounds(77, 509, 590, 30);
			add(panel);
			panel.setLayout(null);

			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(150, 150, 150));
			panel_1.setBounds(77, 539, 590, 149);
			add(panel_1);

			JLabel SelectTicketsLabel = new JLabel("SELECT TICKETS");
			SelectTicketsLabel.setFont(new Font("Arial", Font.BOLD, 16));
			SelectTicketsLabel.setBounds(77, 450, 145, 33);
			add(SelectTicketsLabel);

			JLabel Description = new JLabel("Select the number and type of tickets you wish to buy. Please note seats are reserved on a best available basis.");
			Description.setFont(new Font("Tahoma", Font.PLAIN, 12));
			Description.setBounds(77, 484, 621, 14);
			add(Description);

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
			SelectTickets.setBackground(new Color(180, 180, 180));
			SelectTickets.setBounds(0, 110, 324, 48);
			add(SelectTickets);
			SelectTickets.setLayout(null);

			JLabel TicketPage = new JLabel("Select Tickets");
			TicketPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
			TicketPage.setBounds(121, 11, 82, 26);
			SelectTickets.add(TicketPage);

			Panel SelectSeats = new Panel();
			SelectSeats.setBackground(new Color(187, 187, 187));
			SelectSeats.setBounds(322, 110, 307, 48);
			add(SelectSeats);
			SelectSeats.setLayout(null);

			JLabel SeatsPage = new JLabel("Select Seats");
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

			start = new JButton("Timer");
			start.setName("start Timer");
			start.addActionListener(this);
			start.setBounds(1048, 209, 89, 23);
			add(start);
			
			JPanel movieD = new JPanel();
			movieD.setBounds(77, 175, 590, 280);
			movieD.setLayout(null);
			add(movieD);
			
	        imgLabel = new JLabel();
	        imagePanel.setBounds(0, 0, 196, 280);	 
	        imagePanel.add(imgLabel);
	        movieD.add(imagePanel);
			
			titleLabel = new JLabel("Title");
			titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
			titleLabel.setBounds(206, 11, 402, 33);
			movieD.add(titleLabel);
					
			dateSched = new JLabel("Showing on");
			dateSched.setFont(new Font("Tahoma", Font.PLAIN, 12));
			dateSched.setBounds(206, 55, 165, 14);
			movieD.add(dateSched);
			
			timeSched = new JLabel(" ");
			timeSched.setFont(new Font("Tahoma", Font.PLAIN, 12));
			timeSched.setBounds(381, 55, 98, 14);
			movieD.add(timeSched);
			
		setVisible(true);
	}

		public void setMovie(Movie movie) throws SQLException {
			this.movie = movie;
			movieId = movie.getID();
		    loadImage(connection, pstmt, resultSet, imagePanel, movieId, imgLabel);	
		    titleLabel.setText(movie.getTitle());
		    dateSched.setText("Showing on " + movie.getDate() + " at " );
		    timeSched.setText(movie.getTime());	    
		    repaint(); // Refresh the UI with the new movie details		
		}			
			
		public void loadImage(Connection connection, PreparedStatement pstmt, ResultSet resultSet, JPanel imagePanel, String movieId, JLabel imageLabel) {
		    try {
		    	Class.forName("com.mysql.cj.jdbc.Driver");
			 	connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);
		    	
		        pstmt = connection.prepareStatement("SELECT img_content FROM movie WHERE movie_id = ? AND img_content IS NOT NULL");
		        pstmt.setString(1, movieId);
		        resultSet = pstmt.executeQuery();
		        if (resultSet.next()) {
		            byte[] content = resultSet.getBytes("img_content");
		            if (content != null) {

		                ByteArrayInputStream bis = new ByteArrayInputStream(content);
		                BufferedImage image = ImageIO.read(bis);

		                Image scaledImage = image.getScaledInstance(150, 250, Image.SCALE_SMOOTH);

		                ImageIcon icon = new ImageIcon(scaledImage);
		                imageLabel.setIcon(icon);
		            }
		        } else {
		            System.out.println("Image not found for movie " + movieId);
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonText = e.getActionCommand();		
			if(buttonText.equals("NEXT")) {
				confirm.AdultStt.setText("$" + Integer.toString(adultSubTotal));
				confirm.kidStt.setText("$" + Integer.toString(kidSubTotal));
				confirm.twinStt.setText("$" + Integer.toString(twinSubTotal));
				
				confirm.AdultQ.setText(Integer.toString(a));
				confirm.KidQ.setText(Integer.toString(k));
				confirm.TwinQ.setText(Integer.toString(t));
				
				confirm.totalPrice.setText("$" + Integer.toString(tp));
				choosingSeats.booked();
			
				Movie movie = new Movie();   
		        movie.setID(movieId);
	            movie.setTitle(titleLabel.getText());
	            movie.setDate(dateSched.getText()); 
	            movie.setTime(timeSched.getText());	            
	                     	
				try {
					choosingSeats.setMovie(movie);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				Start.pages(8);
			
			} else if(buttonText.equals("BACK")) {
				Start.pages(6);			
			}		

			o = (JButton)e.getSource();
			button = o.getName();
			totalQuantityTicket = a+k+t;
			tp = adultSubTotal+kidSubTotal+twinSubTotal;

			//adult ticket
			if(button.equals("adultDec")) {
				if(a>0){
					a--;
					adultSubTotal = (a*aPrice);
					adult.setText(Integer.toString(a));
					adultPrice.setText("$"  +  Integer.toString(adultSubTotal));
				}

			}if(button.equals("adultInc")) {
				if(a<=10) {
					a++;
					adultSubTotal = (a*aPrice);
					adult.setText(Integer.toString(a));
					adultPrice.setText("$"  + Integer.toString(adultSubTotal));
					System.out.println(a);
				}
			}

			//		kid ticket
			if(button.equals("kidDec")) {
				if(k>0){
					k--;
					kidSubTotal = (k*kPrice);
					child.setText(Integer.toString(k));
					kidPrice.setText("$"  + Integer.toString(kidSubTotal));
				}
			}if(button.equals("kidInc")) {
				if(k<=10) {
					k++;
					kidSubTotal = (k*kPrice);
					child.setText(Integer.toString(k));
					kidPrice.setText("$"  + Integer.toString(kidSubTotal));
				}
			}

			//	twin
			if(button.equals("twinDec")) {
				if(t>0){
					t--;
					twinSubTotal = (t*tPrice);
					twin.setText(Integer.toString(t));
					twinPrice.setText("$"  + Integer.toString(twinSubTotal));
				}
			}if(button.equals("twinInc")) {
				if(t<=10) {
					t++;
					twinSubTotal = (t*tPrice);
					twin.setText(Integer.toString(t));
					twinPrice.setText("$"  + Integer.toString(twinSubTotal));
				}
			}

			if(totalQuantityTicket==4) {
				adultInc.setEnabled(false);
				kidInc.setEnabled(false);
				twinInc.setEnabled(false);
			}if(totalQuantityTicket<4) {
				adultInc.setEnabled(true);
				kidInc.setEnabled(true);
				twinInc.setEnabled(true);
			}
			
//			if(button.equals("NEXT")) {
//				confirm.AdultStt.setText("$" + Integer.toString(adultSubTotal));
//				confirm.kidStt.setText("$" + Integer.toString(kidSubTotal));
//				confirm.twinStt.setText("$" + Integer.toString(twinSubTotal));
//				
//				confirm.AdultQ.setText(Integer.toString(a));
//				confirm.KidQ.setText(Integer.toString(k));
//				confirm.TwinQ.setText(Integer.toString(t));
//				
//				confirm.totalPrice.setText("$" + Integer.toString(tp));
//				choosingSeats.booked();
//				Start.pages(8);
//				return;
//			}

		}


}



