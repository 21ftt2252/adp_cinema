package WinBuilder;
import java.sql.ResultSet;
import java.util.regex.*;


import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.Toolkit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class Registration extends JPanel implements ActionListener {

	public JTextField txtFName;
	public JTextField txtLName;
	public JTextField txtBirthday;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	static JButton btnJoin;
	static int ID = 000;
	Connection conn = null;
	Statement stmt = null;
	ResultSet resultset = null;
	static String hashedPassword = "";
	String regexPattern = "";
	private JLabel rName;
	private JLabel rLastName;
	private JLabel rBirthday;
	private JLabel rEmail;
	private JLabel rConPass;
	private JLabel rPass;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Registration frame = new Registration();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public Registration() {
		setBounds(100, 100, 986, 628);
		setLayout(null);
//---------------------------JLABELS---------------------------------------
		JLabel lblFName = new JLabel("First Name");
		lblFName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFName.setBounds(65, 112, 105, 32);
		add(lblFName);

		JLabel lblLName = new JLabel("Last Name");
		lblLName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLName.setBounds(65, 184, 105, 32);
		add(lblLName);

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBirthday.setBounds(65, 253, 105, 32);
		add(lblBirthday);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(488, 112, 105, 32);
		add(lblEmail);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(488, 184, 105, 32);
		add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm \r\nPassword");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConfirmPassword.setBounds(488, 233, 167, 71);
		add(lblConfirmPassword);
		
//------------------------FIRST NAME-----------------------------------
		txtFName = new JTextField();
		txtFName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern = "^[A-Za-z@' ]{1,50}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtFName.getText());
				if(!match.matches()) {
					btnJoin.setEnabled(false);
					rName.setText("Name must be alphabet");
				} else {
					btnJoin.setEnabled(true);
					rName.setText(null);
					}
			}
		});
		txtFName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFName.setBounds(198, 112, 256, 32);
		add(txtFName);
		txtFName.setColumns(10);

//-------------------------LAST NAME-----------------------------------
		txtLName = new JTextField();
		txtLName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern = "^[A-Za-z@' ]{1,50}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtLName.getText());
				if(!match.matches()) {
					btnJoin.setEnabled(false);
					rLastName.setText("Name must be alphabet)");
				} else {
					btnJoin.setEnabled(true);
					rLastName.setText(null);
					}
			}
		});
		txtLName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLName.setColumns(10);
		txtLName.setBounds(198, 184, 256, 32);
		add(txtLName);

//---------------------------BIRTHDAY-----------------------------------		
		txtBirthday = new JTextField();
		txtBirthday.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern = "^(?!0000)[0-9]{4}\\/(?:0[1-9]|1[0-2])\\/(?:0[1-9]|[1-2][0-9]|3[0-1])$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtBirthday.getText());
				if(!match.matches()) {
					btnJoin.setEnabled(false);
					rBirthday.setText("Must be in YYYY/MM/DD format");
				} else {
					btnJoin.setEnabled(true);
					rBirthday.setText(null);
					}
			}
		});
		txtBirthday.setFont(new Font("Arial", Font.PLAIN, 15));
		txtBirthday.setColumns(10);
		txtBirthday.setBounds(198, 253, 256, 32);
		add(txtBirthday);

//------------------------------EMAIL-----------------------------------		
		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern = "^[a-zA-Z0-9]{0,30}[@][a-zA-Z0-9]{0,10}[.][a-zA-Z]{0,5}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtEmail.getText());
				if(!match.matches()) {
					rEmail.setText("Email is in incorrect format");
					btnJoin.setEnabled(false);
				} else {
					btnJoin.setEnabled(true);
					rEmail.setText(null);
					}
			}
		});
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(654, 112, 256, 32);
		add(txtEmail);
		
//----------------------------PASSWORD-----------------------------------
		txtPassword = new JPasswordField();
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9@$!%*?&]{8,}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtPassword.getText());
				if(!match.matches()) {
					btnJoin.setEnabled(false);
					rPass.setText("Minimum of 8 characters: include uppercase, lowercase, number");
				} else {
					rPass.setText(null);
					btnJoin.setEnabled(true);
					}
			}
		});
		txtPassword.setEchoChar('*');
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPassword.setColumns(10);
		txtPassword.setBounds(654, 185, 256, 32);
		add(txtPassword);

//--------------------------CONFIRM PASSWORD-----------------------------		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				regexPattern ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9@$!%*?&]{8,}$";
				Pattern patt = Pattern.compile(regexPattern);
				Matcher match = patt.matcher(txtConfirmPassword.getText());
				if(!txtPassword.getText().equals(txtConfirmPassword.getText())) {
					rConPass.setText("Password does not match");
					btnJoin.setEnabled(false);
				} else if(!match.matches()) {
					btnJoin.setEnabled(false);
					}
				else {
					btnJoin.setEnabled(true);
					rConPass.setText(null);
					}
			}
		});
		txtConfirmPassword.setEchoChar('*');
		txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		txtConfirmPassword.setColumns(10);
		txtConfirmPassword.setBounds(654, 253, 256, 32);
		add(txtConfirmPassword);
//----------------------------REGEX LABELS-----------------------------------		
		rName = new JLabel("");
		rName.setForeground(new Color(255, 0, 0));
		rName.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rName.setBounds(65, 154, 388, 20);
		add(rName);
		
		rLastName = new JLabel("");
		rLastName.setForeground(Color.RED);
		rLastName.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rLastName.setBounds(65, 226, 388, 20);
		add(rLastName);
		
		rBirthday = new JLabel("");
		rBirthday.setForeground(Color.RED);
		rBirthday.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rBirthday.setBounds(65, 295, 388, 20);
		add(rBirthday);
		
		rEmail = new JLabel("");
		rEmail.setForeground(Color.RED);
		rEmail.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rEmail.setBounds(488, 154, 422, 20);
		add(rEmail);
		
		rConPass = new JLabel("");
		rConPass.setForeground(Color.RED);
		rConPass.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rConPass.setBounds(488, 295, 422, 20);
		add(rConPass);
		
		rPass = new JLabel("");
		rPass.setForeground(Color.RED);
		rPass.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rPass.setBounds(488, 223, 422, 20);
		add(rPass);
		
//----------------------------JOIN-----------------------------------		
		btnJoin = new JButton("JOIN");
		btnJoin.setEnabled(false);
		btnJoin.setBackground(new Color(192, 192, 192));
		btnJoin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnJoin.setBounds(488, 393, 105, 39);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID ++;
				checkPasscode();
				SendEmailGm();
			}
		});
		add(btnJoin);

		setVisible(true);
	}
	
	

//----------------------------METHODS-----------------------------------
	public void checkPasscode() {
		String FName = txtFName.getText();
		String LName = txtLName.getText();
		String Birthday = txtBirthday.getText();
		String Email = txtEmail.getText();
		String Passcode = txtPassword.getText();
		String CPasscode = txtConfirmPassword.getText();

	    if(FName.isEmpty() || LName.isEmpty() || Birthday.isEmpty() || Email.isEmpty() || Passcode.isEmpty() || CPasscode.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
	    
	    } else if(Passcode.equals(CPasscode)){
	    	btnJoin.setEnabled(true);
			try {
				String hashedPassword = SHA256.toHexString(SHA256.getSHA(CPasscode));
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "00musics");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (first_name, last_name, dob, user_email, user_passcode) " +
						"VALUES (?, ?, ?, ?, ?)"); 

				stmt.setString(1, FName); // Set the first_name parameter value
				stmt.setString(2, LName); // Set the last_name parameter value
				stmt.setString(3, Birthday); // Set the dob parameter value
				stmt.setString(4,Email); // Set the user_email parameter value
				stmt.setString (5,hashedPassword);
				stmt.executeUpdate();

				conn.setAutoCommit(false);
				int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0 ) {
	            	System.out.println(rowsAffected + " rows inserted");
//	            	ID ++;
	            	JOptionPane.showMessageDialog(null, "Registered! Check your email!", "Cinema", JOptionPane.INFORMATION_MESSAGE);
//	            	SendEmailGm();
	            }
	            else {
					JOptionPane.showMessageDialog(null, "The password does not match", "Cinema", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try { if (resultset !=null) resultset.close();} catch (Exception e ) {};
				try { if (conn !=null) conn.close();} catch (Exception e) {}}
		}

	}
	
	public boolean authenticate(String email) {
	    Connection conn = null;
	    ResultSet resultSet = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "00musics");
	        conn.setAutoCommit(false);
	        
	        preparedStatement = conn.prepareStatement("SELECT * FROM USER WHERE user_email = ?");
	        preparedStatement.setString(1, email);
	        
	        resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {

	            if (email.equals(null)) {

	                return true;
	            } else {JOptionPane.showMessageDialog(null, "Email already registered!");}
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
		String Email = txtEmail.getText();
		    
		    try {
		        boolean authenticated = authenticate(Email);
		        
		        if (authenticated) {
//		        	authenticate(Email);
		            checkPasscode();
		            ID ++;
		        } 
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
	}
	
	public void SendEmailGm() {
		String to = txtEmail.getText(); //recipient's email address
        String from = "bncineplex@gmail.com"; //sender's email address
        String password = "fuoe nhfl fpts uxgh"; //sender's APPLICATION password = fuoe nhfl fpts uxgh

        // Setup mail server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
            message.setSubject("Welcome to BN CINEPLEX");

            // Set the actual message
            message.setText("Welcome to BNCineplex!\r\n"
            		+ "\r\n"
            		+ "We are so happy to have you on board.\r\n"
            		+ "With this application, you can view and book tickets of movies of your liking!\r\n"
            		+ "\r\n"
            		+ "Don't hesitate to get in touch if you have any questions!");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }		
	}
	



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}