package WinBuilder;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionEvent;
import WinBuilder.SHA256;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class Login extends JPanel implements ActionListener {

	public static JTextField txtEAddress;
	public static JPasswordField txtPassword;
	
	public Login() {
		setBounds(100, 100, 1210, 677);
		setLayout(null);
		
		JLabel lblEmail = new JLabel("Email Address");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblEmail.setBounds(356, 191, 167, 61);
		add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblPassword.setBounds(356, 270, 167, 61);
		add(lblPassword);
		
		txtEAddress = new JTextField();
		txtEAddress.setFont(new Font("Arial", Font.PLAIN, 18));
		txtEAddress.setBounds(569, 207, 261, 35);
		add(txtEAddress);
		txtEAddress.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
		txtPassword.setColumns(10);
		txtPassword.setBounds(569, 286, 261, 35);
		add(txtPassword);
		
// -----------------------------REGISTER-------------------------------------		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Registration registration = new Registration();
				Login.this.setVisible(false);
				registration.setVisible(true);
			}

			private void setVisible(boolean b) {
				// TODO Auto-generated method stub
				
			}
		});
		lblRegister.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRegister.setBounds(360, 408, 68, 29);
		add(lblRegister);
		
// ---------------------------FORGOT PASSWORD--------------------------------				
		JLabel lblForgetPassword = new JLabel("Forgot Password?");
		lblForgetPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SendCode sc = new SendCode();
				this.setVisible(false);
				sc.setVisible(true);
			}

			private void setVisible(boolean b) {
				// TODO Auto-generated method stub
				
			}
		});
		lblForgetPassword.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblForgetPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblForgetPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblForgetPassword.setBounds(690, 408, 140, 29);
		add(lblForgetPassword);

// -------------------------------LOG IN-------------------------------------				
        JButton btnLogin = new JButton("LOG IN");
        btnLogin.addActionListener(this); 
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setBounds(534, 502, 105, 39);
		add(btnLogin);
		
		setVisible(true);
	}
//---------------------------------METHODS-------------------------------------	
	public boolean authenticate(String email, String hashedPassword) {          
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
	            String storedHashedPassword = resultSet.getString("user_passcode");
	            if (hashedPassword.equals(storedHashedPassword)) {
	                return true;
	            }
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
	
	public void actionPerformed(ActionEvent e) {
		String email = txtEAddress.getText();
		String password = new String(((JPasswordField) txtPassword).getPassword());

		try {
			// Hash the password using SHA256
			String hashedPassword = SHA256.toHexString(SHA256.getSHA(password));

			// Authenticate the user with the hashed password
			boolean authenticated = authenticate(email, hashedPassword);

			if (authenticated) {
//------------------EDIT HERE; IF AUTHENTICATED, GO TO HOMEPAGE		        	
	            System.out.println("LOGIN SUCCESSFUL");
			} else {
				JOptionPane.showMessageDialog(this, "Invalid email or password");
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	}
	
//	public void name() {
//	    Connection conn = null;
//	    ResultSet resultSet = null;
//	    PreparedStatement preparedStatement = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//	        conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "00musics");
//	        preparedStatement = conn.prepareStatement("select first_name, last_name from user where user_email = ?");
//	        resultSet = preparedStatement.executeQuery();
//	        
//	        if (resultSet.next()) {
//	        	resultSet.getString("first_name");
//	        	resultSet.getString("last_name");
//	        }
//	        
//	        int n = rsmd.getColumnCount();
//	        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//	        dtm.setRowCount(0);
//	        while(resultSet.next()) {
//	        	Vector v = new Vector();
//	        	for(int i=1; i<n; i++) {
//	        		v.add(resultSet.getString("admin_id"));
//	        		v.add(resultSet.getString("admin_name"));
//	        		v.add(resultSet.getString("admin_email"));
//	        		v.add(resultSet.getString("admin_status"));
//	        	}
//	        	dtm.addRow(v);
//	        }
//	        
//			
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
