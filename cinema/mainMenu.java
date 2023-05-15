package cinema;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class mainMenu extends JPanel implements ActionListener {
	GridBagConstraints mainConstraint = new GridBagConstraints();
	
	public mainMenu() {
		setLayout(new GridBagLayout());
		int x, y;
		
		// Colors
		Color mainMenuBg= new Color(0,103,76,255);
		setBackground(mainMenuBg);
		Color buttonColor = new Color(204,186,120);
		
		// Button dimension
		Dimension d2 = new Dimension(250, 50);

		// Default font size
		Font defaultFont = new Font("SansSerif", Font.PLAIN, 30);
		
		ImageIcon imgLogoTemp = new ImageIcon("image/logo.png");
		Image imgLogoImage = imgLogoTemp.getImage().getScaledInstance(650, 500, Image.SCALE_SMOOTH); //Changing ImageIcon into image to resize it
		imgLogoTemp = new ImageIcon(imgLogoImage);
		JLabel imgLogo = new JLabel(imgLogoTemp);
		imgLogo.setBounds(117, 148, 163, 138);
		addComponent(imgLogo, x = 0, y= 0);
		
		JButton homepageBtn = new JButton("HOMEPAGE");
		homepageBtn.setBackground(buttonColor);
		homepageBtn.setFont(defaultFont);
		homepageBtn.setPreferredSize(d2);
		addComponent(homepageBtn, x = 1, y = 1);
		homepageBtn.addActionListener(this);
		
		JButton loginButton = new JButton("LOGIN");
		loginButton.setBackground(buttonColor);
		loginButton.setFont(defaultFont);
		loginButton.setPreferredSize(d2);
		addComponent(loginButton, x = 1, y = 2);
		loginButton.addActionListener(this);
		
		JButton registerButton = new JButton("REGISTER");
		registerButton.setBackground(buttonColor);
		registerButton.setFont(defaultFont);
		registerButton.setPreferredSize(d2);
		addComponent(registerButton, x = 1, y = 3);
		registerButton.addActionListener(this);
	
	}
	
	// To prevent repetitive attributes
		private void addComponent(Component component, int x, int y) {

			mainConstraint.gridx = x;
			mainConstraint.gridy = y;

			mainConstraint.weightx = 0.05;
			mainConstraint.weighty = 0.05;

			// For title & last spacing ONLY
			if((x==0 && y==0) || (x==1 && y==8)) {
				mainConstraint.weightx = 1;
				mainConstraint.weighty = 1;
				mainConstraint.gridwidth = 2;
				mainConstraint.anchor = GridBagConstraints.CENTER;
			}

			add(component, mainConstraint);

		}
	
		// Event
		public void actionPerformed(ActionEvent e) {
			String buttonText = e.getActionCommand();

			if(buttonText.equals("LOGIN")) {
				Start.pages(3);

			} else if(buttonText.equals("REGISTER")) {
				Start.pages(2);
				
			} else if(buttonText.equals("HOMEPAGE")) {
				Start.pages(4);			
			}
	}
}
