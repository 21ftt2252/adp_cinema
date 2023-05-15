package cinema;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Start extends JFrame{
	
	static JFrame frame;
	static CardLayout cardlayout;
	static JPanel contentPane;
	
	public static void main(String[] args) {
		cardlayout = new CardLayout();
		contentPane = new JPanel(cardlayout);

		frame = new JFrame("Cinema");
		frame.setLocation(50, 20);
		frame.setSize(1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cardlayout = new CardLayout();
		contentPane = new JPanel(cardlayout);
		frame.add(contentPane);
		
		//MAIN MENU
		JPanel mainMenu = new mainMenu();
		contentPane.add(mainMenu,"1");
		
		//REGISTRATION
		JPanel registration = new Registration();
		contentPane.add(registration, "2");
		
		//LOGIN
		JPanel login = new Login();
		contentPane.add(login, "3");
		
		//HOMEPAGE (COMING SOON)
		JPanel homepage2 = new comingSoon();
		contentPane.add(homepage2, "5");	
		
		//PAYMENT
		JPanel payment = new Payment();
		contentPane.add(payment,"10");
		
		//CONFIRM
		JPanel confirm = new confirm();
		contentPane.add(confirm,"9");
		
		//SEAT
		JPanel seat = new choosingSeats();
		contentPane.add(seat,"8");
		
		//TICKET PAGE
		JPanel ticket = new ticket((choosingSeats)seat);
		contentPane.add(ticket, "7");
		
		//MOVIE DETAILS
		JPanel movieDetails = new MovieDetails((ticket)ticket);
		contentPane.add(movieDetails,"6");
		contentPane.setVisible(true);
		
		//HOMEPAGE (NOW SHOWING)
		JPanel homepage = new nowShowing((MovieDetails)movieDetails);
		contentPane.add(homepage,"4");

		pages(1);		
		frame.setVisible(true);
		
	}
	
	public static void pages(int pageNumber) {
		switch(pageNumber) {
		case 1: cardlayout.show(contentPane, "1"); break;
		case 2: cardlayout.show(contentPane, "2"); break;
		case 3: cardlayout.show(contentPane, "3"); break;
		case 4: cardlayout.show(contentPane, "4"); break;
		case 5: cardlayout.show(contentPane, "5"); break;
		case 6: cardlayout.show(contentPane, "6"); break;
		case 7: cardlayout.show(contentPane, "7"); break;
		case 8: cardlayout.show(contentPane, "8"); break;
		case 9: cardlayout.show(contentPane, "9"); break;
		case 10: cardlayout.show(contentPane, "10"); break;
		}

	}

}
