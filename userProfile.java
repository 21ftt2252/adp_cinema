package cinema;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class userProfile extends JPanel implements ActionListener {
	public JTextField txtFName;
	public JTextField txtLName;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	static JButton btnJoin;
	private JTextField txtBirthday;
	
	public userProfile() {
		setBounds(100, 100, 986, 628);
		setLayout(null);

		txtFName = new JTextField();
		txtFName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFName.setBounds(198, 153, 256, 32);
		add(txtFName);
		txtFName.setColumns(10);

		JLabel lblFName = new JLabel("First Name");
		lblFName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFName.setBounds(65, 152, 105, 32);
		add(lblFName);

		JLabel lblLName = new JLabel("Last Name");
		lblLName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLName.setBounds(540, 152, 105, 32); 
		add(lblLName);

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBirthday.setBounds(65, 210, 105, 32);
		add(lblBirthday);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(540, 210, 105, 32);
		add(lblEmail);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(65, 271, 105, 32);
		add(lblPassword);

		txtLName = new JTextField();
		txtLName.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLName.setColumns(10);
		txtLName.setBounds(654, 152, 256, 32);
		add(txtLName);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(654, 211, 256, 32);
		add(txtEmail);

		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('*');
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPassword.setColumns(10);
		txtPassword.setBounds(198, 272, 256, 32);
		add(txtPassword);
		txtPassword.setEchoChar('*');
		
		txtBirthday = new JTextField();
		txtBirthday.setFont(new Font("Arial", Font.PLAIN, 15));
		txtBirthday.setColumns(10);
		txtBirthday.setBounds(198, 210, 256, 32);
		add(txtBirthday);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.setBounds(375, 515, 105, 39);
		add(btnBack);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnEdit.setBackground(Color.LIGHT_GRAY);
		btnEdit.setBounds(548, 515, 105, 39);
		add(btnEdit);
		
		JLabel lblUserProfile = new JLabel("USER PROFILE");
		lblUserProfile.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblUserProfile.setBounds(375, 11, 278, 54);
		add(lblUserProfile);

		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
