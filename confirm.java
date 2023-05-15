package cinema;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class confirm extends JPanel implements ActionListener{
	private String username = "root";
	private String password = "cmaeerica";
	//timer
	public static Timer timer;
	public static int countdown;
	public static JLabel Timer;
	
	public static JLabel AdultStt;
	public static JLabel kidStt;
	public static JLabel twinStt;
	
	public static JLabel AdultQ;
	public static JLabel KidQ;
	public static JLabel TwinQ;
	public static JLabel Subtotal;
	public static JLabel totalPrice;
	
	JPanel cPanel = new JPanel();
	/**
	 * Create the panel.
	 */
	public confirm() {
		setBounds(100, 100, 1210, 677);
		setLayout(null);
		
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
        
        JButton next = new JButton("NEXT");
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
		Confirm.setBounds(628, 110, 315, 48);
		add(Confirm);
		Confirm.setLayout(null);
		
		JLabel ConfirmPage = new JLabel("Confirm");
		ConfirmPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ConfirmPage.setHorizontalAlignment(SwingConstants.CENTER);
		ConfirmPage.setBounds(102, 11, 99, 26);
		Confirm.add(ConfirmPage);
		
		Panel BookingSuccessful = new Panel();
		BookingSuccessful.setBackground(new Color(187, 187, 187));
		BookingSuccessful.setBounds(941, 110, 340, 48);
		add(BookingSuccessful);
		BookingSuccessful.setLayout(null);
		
		JLabel BookingPage = new JLabel("Booking Successful");
		BookingPage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BookingPage.setBounds(117, 5, 118, 32);
		BookingSuccessful.add(BookingPage);
		
		JLabel CheckOut = new JLabel("CHECKOUT");
		CheckOut.setFont(new Font("Arial", Font.BOLD, 20));
		CheckOut.setBounds(39, 165, 132, 48);
		add(CheckOut);
		
		Timer = new JLabel("Time Left: 10:00");
		Timer.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		Timer.setBounds(1048, 165, 172, 33);
		add(Timer);
		
		JLabel lblNewLabel_1 = new JLabel("Item");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(93, 344, 49, 14);
		add(lblNewLabel_1);
		
		JLabel Adult = new JLabel("Adult");
		Adult.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Adult.setBounds(93, 377, 49, 14);
		add(Adult);
		
		JLabel Kid = new JLabel("Kid");
		Kid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Kid.setBounds(93, 413, 49, 14);
		add(Kid);
		
		JLabel Twin = new JLabel("Twin");
		Twin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Twin.setBounds(93, 450, 49, 14);
		add(Twin);
		
		JLabel Cost = new JLabel("Cost");
		Cost.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Cost.setBounds(322, 344, 49, 14);
		add(Cost);
		
		JLabel lblNewLabel_2 = new JLabel("$4.00");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(322, 377, 49, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("$3.00");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(322, 413, 49, 14);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("$10.00");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(322, 450, 49, 14);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Quantity");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(628, 344, 63, 14);
		add(lblNewLabel_5);
		
		AdultQ = new JLabel("0");
		AdultQ.setHorizontalAlignment(SwingConstants.CENTER);
		AdultQ.setBounds(628, 378, 49, 14);
		add(AdultQ);
		
		KidQ = new JLabel("0");
		KidQ.setHorizontalAlignment(SwingConstants.CENTER);
		KidQ.setFont(new Font("Tahoma", Font.PLAIN, 13));
		KidQ.setBounds(628, 413, 49, 14);
		add(KidQ);
		
		TwinQ = new JLabel("0");
		TwinQ.setHorizontalAlignment(SwingConstants.CENTER);
		TwinQ.setBounds(628, 451, 49, 14);
		add(TwinQ);
		
		Subtotal = new JLabel("Subtotal");
		Subtotal.setHorizontalAlignment(SwingConstants.CENTER);
		Subtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Subtotal.setBounds(865, 344, 63, 14);
		add(Subtotal);
		
		JLabel Total = new JLabel("Total:");
		Total.setFont(new Font("Tahoma", Font.BOLD, 14));
		Total.setBounds(769, 497, 49, 14);
		add(Total);
		
		totalPrice = new JLabel("0");
		totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		totalPrice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		totalPrice.setBounds(865, 498, 49, 14);
		add(totalPrice);
		
		AdultStt = new JLabel("0");
		AdultStt.setHorizontalAlignment(SwingConstants.CENTER);
		AdultStt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		AdultStt.setBounds(865, 377, 49, 14);
		add(AdultStt);
		
		kidStt = new JLabel("0");
		kidStt.setHorizontalAlignment(SwingConstants.CENTER);
		kidStt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		kidStt.setBounds(865, 413, 49, 14);
		add(kidStt);
		
		twinStt = new JLabel("0");
		twinStt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		twinStt.setHorizontalAlignment(SwingConstants.CENTER);
		twinStt.setBounds(865, 450, 49, 14);
		add(twinStt);
		
		JButton Cancel = new JButton("Cancel Order");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSeats();
			}
		});
		Cancel.setBounds(39, 575, 132, 33);
		add(Cancel);
		
		setVisible(true);
	}
	
	public void deleteSeats() {
		String userEmail = Login.getTxtEAddress().getText();
		
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);
	        String sql = "UPDATE cinema SET position = NULL WHERE purchase_history_id = (SELECT purchase_history_id FROM purchase_history "
	        		+ " WHERE user_id = (SELECT user_id FROM user WHERE user_email = ?))";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, userEmail);
	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void qts() {
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = e.getActionCommand();		
		if(buttonText.equals("NEXT")) {				
			Start.pages(10);
		
		} else if(buttonText.equals("BACK")) {
			Start.pages(8);			
		}		
		
	}

}
