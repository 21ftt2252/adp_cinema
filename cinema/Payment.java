package cinema;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;

public class Payment extends JPanel implements ActionListener {
	private Movie movie;
	
	private JTextField txtCardNum;
	private JTextField txtExpDate;
	private JTextField txtCVV;
	private JTextField txtTotal;
	
	String username = "root";
	String password = "cmaeerica";

	public Payment() {	
		setBounds(100, 100, 1210, 677);
		setLayout(null);
		
		txtCardNum = new JTextField();
		txtCardNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCardNum.setColumns(10);
		txtCardNum.setBounds(518, 144, 272, 41);
		add(txtCardNum);
		
		txtExpDate = new JTextField();
		txtExpDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtExpDate.setColumns(10);
		txtExpDate.setBounds(518, 220, 272, 41);
		add(txtExpDate);
		
		txtCVV = new JTextField();
		txtCVV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCVV.setColumns(10);
		txtCVV.setBounds(518, 300, 272, 41);
		add(txtCVV);
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTotal.setColumns(10);
		txtTotal.setBounds(518, 376, 272, 41);
		add(txtTotal);
		
		//HAS TO BE OF THE RIGHT FORMAT AND STARTS WITH 5516
		JLabel CardNumber = new JLabel("Card Number");
		CardNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CardNumber.setBounds(366, 157, 131, 19);
		add(CardNumber);
		
		//FORMAT
		JLabel lblExpirationDate = new JLabel("Expiration Date");
		lblExpirationDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblExpirationDate.setBounds(366, 233, 131, 19);
		add(lblExpirationDate);
		
		//FORMAT
		JLabel lblCvv = new JLabel("CVV");
		lblCvv.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCvv.setBounds(366, 313, 131, 19);
		add(lblCvv);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTotal.setBounds(366, 389, 131, 19);
		add(lblTotal);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(330, 540, 114, 35);
		btnNewButton.addActionListener(this);
		add(btnNewButton);
		
		JButton btnPay = new JButton("Pay");
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPay.setBounds(796, 540, 114, 35);
		add(btnPay);
		
		JLabel lblNewLabel = new JLabel("PAYMENT");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel.setBounds(323, 34, 49, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PAYMENT");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(498, 34, 178, 72);
		add(lblNewLabel_1);
		btnPay.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {	    	
	            if (checkDetails()) {
	                JDialog paymentDialog = new JDialog();
	                paymentDialog.setPreferredSize(new Dimension(300, 200)); 
	                
	                EReceipt eReceipt = new EReceipt();
	                eReceipt.receiptDetails();

	                storeDetails(movie);
	                SendEmailGM.sendEmail();
	                
	                
	                //RESEND EMAIL
	                JButton confirmButton = new JButton("Resend Email");
	                confirmButton.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	SendEmailGM.sendEmail();
	            	        JOptionPane.showMessageDialog(null, "Your email has been successfully resent. Please check your email.", "Resend Email", JOptionPane.INFORMATION_MESSAGE);
	                    }
	                });

	                //LOG OUT
	                JButton cancelButton = new JButton("Log Out");
	                cancelButton.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {

	                    }
	                });

	                JPanel buttonPanel = new JPanel();
	                buttonPanel.add(confirmButton);
	                buttonPanel.add(cancelButton);

	                JLabel message = new JLabel("<html>PAYMENT SUCCESSFUL!<br>Check your email.</html>");
	                message.setHorizontalAlignment(JLabel.CENTER);
	                paymentDialog.getContentPane().add(message, BorderLayout.CENTER);
	                paymentDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	                paymentDialog.pack();
	                //paymentDialog.setLocationRelativeTo(frame);
	                paymentDialog.setVisible(true);
	            } else {}
	        }
	    });
		}

	int totalTrial = 10;
	
	public boolean checkDetails() {
	    String CardNum = txtCardNum.getText();
	    String ExpDate = txtExpDate.getText();
	    String CVV = txtCVV.getText();
	    String Total = txtTotal.getText();
	    String CardNumPattern = "^5516\\s\\d{4}\\s\\d{4}\\s\\d{4}";
	    String ExpDatePattern = "^(0[1-9]|1[0-2])\\/\\d{2}$";
	    String CVVPattern = "^[0-9]{3}$";

	    // CARD NUMBER
	    if (!CardNum.matches(CardNumPattern)) {
	        JOptionPane.showMessageDialog(null, "The card number format is incorrect. Please re-enter your card number. \nPlease follow the format '5516 **** **** ****'", "Incorrect Card Number Format", JOptionPane.INFORMATION_MESSAGE);
	        return false;
	    }

	    // EXP DATE
	    if (!ExpDate.matches(ExpDatePattern)) {
	        JOptionPane.showMessageDialog(null, "The expiration date format is incorrect. Please re-enter your expiration date. \nPlease follow the format 'MM/YY'", "Incorrect Expiration Date Format", JOptionPane.INFORMATION_MESSAGE);
	        return false;
	    }

	    // CVV
	    if (!CVV.matches(CVVPattern)) {
	        JOptionPane.showMessageDialog(null, "The CVV format is incorrect. Please re-enter your CVV. \nPlease follow the format '***'", "Incorrect CVV Format", JOptionPane.INFORMATION_MESSAGE);
	        return false;
	    }
	    
	    // TOTAL
	    if(Integer.parseInt(Total) != totalTrial) {
	        JOptionPane.showMessageDialog(null, "Insufficient amount. Please enter the right amount.", "Incorrect Amount", JOptionPane.INFORMATION_MESSAGE);
	        return false;
	    }

	    //once payment successful then store the details and generate the confirmation id 
	    
	    return true;
	}
	
	
	//DATABASE
	public void storeDetails(Movie movie) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
				
		//EXAMPLE OF MOVIE
//		String movie_title = "Puss in Boots: The Last Wish"; 
//		String movie_id = "";
		
		this.movie = movie;
		String movie_id = movie.getID();
	    String movie_title = movie.getTitle());
	    dateSched.setText("Showing on " + movie.getDate() + " at " );
	    timeSched.setText(movie.getTime());	    	
		
		//EXAMPLE OF USER 
		String user_email = Login.getTxtEAddress().getText();
		int user_id = 0;
		
		//EXAMPLE OF MOVIE SCHEDULE
		String movie_schedule_id = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);
			
			//RETRIEVING THE MOVIE ID BASED FROM THE CHOSEN MOVIE TITLE
			String movid = "select movie_id from movie where movie_title = ?";
			stmt = connection.prepareStatement(movid);
			stmt.setString(1, movie_title);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				movie_id = resultSet.getString("movie_id");
			}
			
			//RETRIEVING THE USER ID BASED FROM THEIR EMAIL
			String usid = "select user_id from user where user_email = ?";
			stmt = connection.prepareStatement(usid);
			stmt.setString(1, user_email);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				user_id = resultSet.getInt("user_id");
			}
			
			//RETRIEVING MOVIE SCHED ID BASED ON SELECTED MOVIE
			String movsched = "SELECT movie_schedule_id FROM movie_schedule WHERE movie_id = (SELECT movie_id FROM movie WHERE movie_title = ?)";
			stmt = connection.prepareStatement(movsched);
			stmt.setString(1, movie_title);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				movie_schedule_id = resultSet.getString("movie_schedule_id");
			}
			
			//RETRIEVE THE CURRENT DATE
    		LocalDate currentDate = LocalDate.now();
    		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			stmt = connection.prepareStatement("INSERT INTO purchase_history (date_of_purchase, total_amount, kid, adult, twin, movie_id, user_id, movie_schedule_id) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); 

			Date date_of_purchase = Date.valueOf(currentDate);
			stmt.setDate(1, date_of_purchase); 
			stmt.setInt(2, totalAmt); 
			stmt.setInt(3, kid); 
			stmt.setInt(4,adult);
			stmt.setInt (5,twin);
			stmt.setString(6, movie_id);
			stmt.setInt(7, user_id);
			stmt.setString(8, movie_schedule_id);
			stmt.executeUpdate();

			connection.setAutoCommit(false);
			int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0 ) {
            	System.out.println(rowsAffected + " rows inserted");
            	JOptionPane.showMessageDialog(null, "Registered", "Cinema", JOptionPane.INFORMATION_MESSAGE);
            } else 
			{
			}
            
            
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if (resultSet !=null) resultSet.close();} catch (Exception e ) {};
			try { if (stmt !=null) stmt.close();} catch (Exception e ) {};
			try { if (connection !=null) connection.close();} catch (Exception e) {}}
	
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = e.getActionCommand();
		
		if(buttonText.equals("CANCEL")) {
			Start.pages(4);

		} 
		
	}
}