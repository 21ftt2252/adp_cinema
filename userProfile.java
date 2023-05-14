package WinBuilder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class userProfile extends JPanel implements ActionListener {
	public JTextField txtFName;
	public JTextField txtLName;
	private JTextField txtEmail;
	static JButton btnJoin;
	private JTextField txtBirthday;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userProfile frame = new userProfile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public userProfile() {
		
		setBounds(100, 100, 986, 628);
		setLayout(null);
		
		JLabel lblUserProfile = new JLabel("USER PROFILE");
		lblUserProfile.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblUserProfile.setBounds(332, 10, 278, 54);
		add(lblUserProfile);

		JLabel lblFName = new JLabel("First Name");
		lblFName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFName.setBounds(284, 112, 105, 32);
		add(lblFName);

		JLabel lblLName = new JLabel("Last Name");
		lblLName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLName.setBounds(284, 165, 105, 32); 
		add(lblLName);

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBirthday.setBounds(284, 288, 105, 32);
		add(lblBirthday);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(284, 224, 105, 32);
		add(lblEmail);
		
		txtFName = new JTextField();
		txtFName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFName.setBounds(417, 113, 256, 32);
		add(txtFName);
		txtFName.setColumns(10);

		txtLName = new JTextField();
		txtLName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLName.setColumns(10);
		txtLName.setBounds(417, 166, 256, 32);
		add(txtLName);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(417, 225, 256, 32);
		add(txtEmail);
		
		txtBirthday = new JTextField();
		txtBirthday.setFont(new Font("Arial", Font.PLAIN, 15));
		txtBirthday.setColumns(10);
		txtBirthday.setBounds(417, 288, 256, 32);
		add(txtBirthday);
//---------------------GO BACK TO HOMEPAGE-------------------------		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.setBounds(429, 481, 105, 39);
		add(btnBack);	

//---------------------CHANGE PASSWORD---------------------		
		JButton btnChange = new JButton("Change Password");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetCode r = new ResetCode();
				r.setVisible(true);
			}
		});
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnChange.setBounds(495, 351, 178, 32);
		add(btnChange);

		setVisible(true);
//----------------DISPLAY USER PROFILE DETAILS---------------		
		Connection connection;
		ResultSet resultSet;
		PreparedStatement pstmt;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "00musics");
			pstmt = connection.prepareStatement("select first_name, last_name, user_email, dob from user where user_email = '"+Login.txtEAddress.getText()+"'");
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				txtFName.setText(resultSet.getString(1));
				txtLName.setText(resultSet.getString(2));
				txtEmail.setText(resultSet.getString(3));
				txtBirthday.setText(resultSet.getString(4));
			}

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}


