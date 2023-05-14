package admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminForgot extends JFrame {

	private JPanel contentPane;
	private JFrame Reset;
	public JTextField email;
	private JTextField code;
	int randomCode; 
	private JPasswordField pw;
	private JPasswordField confirm;
	private JButton btnReset;
	static String hashedPassword = "";
	String regexPattern = "";
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminForgot frame = new AdminForgot();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminForgot() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 659, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(97, 50, 123, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Verify Code");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(97, 182, 123, 33);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewPass = new JLabel("New Password");
		lblNewPass.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewPass.setBounds(97, 308, 154, 33);
		lblNewPass.setVisible(false);
		contentPane.add(lblNewPass);
		
		JLabel lblConfirm = new JLabel("Confirm Password");
		lblConfirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblConfirm.setBounds(69, 375, 160, 33);
		lblConfirm.setVisible(false);
		contentPane.add(lblConfirm);
		
		JLabel rPass = new JLabel("");
		rPass.setForeground(Color.RED);
		rPass.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rPass.setBounds(97, 351, 422, 20);
		contentPane.add(rPass);
		
		JLabel rConPass = new JLabel("");
		rConPass.setForeground(Color.RED);
		rConPass.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rConPass.setBounds(97, 418, 422, 20);
		contentPane.add(rConPass);
		
		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		email.setBounds(230, 52, 322, 33);
		contentPane.add(email);
		email.setColumns(10);
		
		code = new JTextField();
		code.setFont(new Font("Tahoma", Font.PLAIN, 16));
		code.setColumns(10);
		code.setBounds(230, 184, 322, 33);
		contentPane.add(code);	

//-----------------------PASSWORDS REGEX-------------------------------		
		pw = new JPasswordField();
		pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9@$!%*?&]{8,}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(pw.getText());
				if(!match.matches()) {
					rPass.setText("Minimum of 8 characters: include uppercase, lowercase, number");
					btnReset.setEnabled(false);
				} else {
					rPass.setText(null);
					btnReset.setEnabled(true);
					}
			}
		});
		pw.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pw.setColumns(10);
		pw.setBounds(261, 308, 291, 33);
		pw.setVisible(false);
		contentPane.add(pw);

//-------------------------CONFIRM REGEX-----------------------------------		
		confirm = new JPasswordField();
		confirm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!pw.getText().equals(confirm.getText())) {
					rConPass.setText("Password does not match");
					btnReset.setEnabled(false);
				} else {
					rConPass.setText(null);
					btnReset.setEnabled(true);
					}
			}
		});
		confirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		confirm.setColumns(10);
		confirm.setBounds(261, 377, 291, 33);
		confirm.setVisible(false);
		contentPane.add(confirm);

//------------------------SEND CODE BUTTON-----------------------------------		
		JButton btnNewButton = new JButton("Send Code");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(435, 121, 117, 33);
		contentPane.add(btnNewButton);

//--------------------------RESET BUTTON-------------------------------------		
		btnReset = new JButton("Reset");
		btnReset.setEnabled(false);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Email = email.getText();
				String Passcode = pw.getText();
				String CPasscode = confirm.getText();
				
				if(Passcode.equals(CPasscode)) {
					try {
						String hashedPassword = SHA256.toHexString(SHA256.getSHA(CPasscode));
						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost/cinema","root","00musics");
						
						String sql = "UPDATE user SET admin_passcode = ? WHERE admin_email = ?";
						pst = con.prepareStatement(sql);
						pst.setString(1, hashedPassword);
						pst.setString(2, Email);
						pst.executeUpdate();
						con.setAutoCommit(false);
						JOptionPane.showMessageDialog(null, "Reset Successfully");
						
					} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, ex);
					}
				} else {JOptionPane.showMessageDialog(null, "Password do not match");}
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReset.setBounds(435, 456, 117, 33);
		btnReset.setVisible(false);
		contentPane.add(btnReset);
		
//-------------------------VERIFY CODE BUTTON---------------------------------		
		JButton btnVerifyCode = new JButton("Verify Code");
		btnVerifyCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.valueOf(code.getText())==randomCode){
					lblNewPass.setVisible(true);
					pw.setVisible(true);
					lblConfirm.setVisible(true);
					confirm.setVisible(true);
					btnReset.setVisible(true);
				} else {JOptionPane.showMessageDialog(null, "Code do not match");}
			}

			private void setVisible(boolean b) {
				// TODO Auto-generated method stub
				
			}
		});
		btnVerifyCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVerifyCode.setBounds(435, 252, 117, 33);
		contentPane.add(btnVerifyCode);

	}
//----------------------------METHODS-----------------------------------	
	public boolean authenticate(String email) {
	    Connection conn = null;
	    ResultSet resultSet = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "00musics");
	        conn.setAutoCommit(false);
	        
	        preparedStatement = conn.prepareStatement("SELECT * FROM admin WHERE admin_email = ?");
	        preparedStatement.setString(1, email);
	        
	        resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {
//	            String storedHashedPassword = resultSet.getString("user_passcode");
	            if (!email.equals(null)) {
	                return true;
	            } else {JOptionPane.showMessageDialog(null, "Email not found");}
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
	        try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
	        try { if (conn != null) conn.close(); } catch (Exception e) {};
	    }
	    return false;
	}
	
	public void check() {
		String Email = email.getText();
		    
		    try {
		        // Authenticate the user with the hashed password
		        boolean authenticated = authenticate(Email);
		        
		        if (authenticated) {
		            SendEmail();
		        } else {
		            JOptionPane.showMessageDialog(this, "Invalid email");
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
	}
	
	public void SendEmail() {
		Random rand = new Random();
		randomCode=rand.nextInt(999999);
		String host = "smtp.gmail.com";
		String user = email.getText();
		String to = email.getText();
		String from = "bncineplex@gmail.com"; //sender's email address
        String password = "fuoe nhfl fpts uxgh"; 
        
     // Setup mail server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        // Get the Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });
        
        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Reset Password");

            // Set the actual message
            message.setText("Your reset code is " +randomCode);

            // Send message
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Code has been sent to your email");

            System.out.println("Email sent successfully.");
        } catch (MessagingException e1) {
            throw new RuntimeException(e1);
        }	
	}
	
}
