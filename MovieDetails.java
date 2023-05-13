package cinema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MovieDetails extends JPanel implements ActionListener {
	private String username = "root";
	private String password = "cmaeerica";

	Color mainMenuBG= new Color(0,103,76,255);
	Color panelBG = new Color (236,236,235);
	
    Connection connection = null;
	ResultSet resultSet = null;
    PreparedStatement pstmt = null;
    Statement statement = null;
	
	JPanel contentPanel = new JPanel();
	
	private JLabel titleLabel;
	private JTextArea description;
	private JLabel imgLabel;

	private Movie movie;
	
    JPanel imagePanel = new JPanel();
    String movieId;

	public MovieDetails() {
		setBounds(100, 100, 986, 628);
        setLayout(null);
        setBackground(mainMenuBG);
        
        ImageIcon imgLogoTemp = new ImageIcon("image/logo.png");
        Image imgLogoImage = imgLogoTemp.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); 
        imgLogoTemp = new ImageIcon(imgLogoImage);
        JLabel imgLogo = new JLabel(imgLogoTemp);
        imgLogo.setPreferredSize(new Dimension(350,200));
        imgLogo.setBounds(545, 11, 304, 55);
        add(imgLogo);
        
        contentPanel.setBounds(10, 77, 1260, 653); //1100
        contentPanel.setPreferredSize(new Dimension(1260, 1000));
//        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setLayout(new GridLayout(3,3));
        add(contentPanel);
//      contentPanel.setLayout(new BorderLayout());
//        JScrollPane scroll = new JScrollPane(contentPanel);
//        scroll.setBounds(10, 77, 1260, 653);
//        add(scroll);
           
        imgLabel = new JLabel();
        imagePanel.setPreferredSize(new Dimension(250, 250));
        imagePanel.add(imgLabel);
        contentPanel.add(imagePanel);
        
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension (350,20));
        contentPanel.add(titleLabel); 
       
        description = new JTextArea();
        description.setFont(new Font("Tahoma", Font.PLAIN, 16));     
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(panelBG);    
        description.setPreferredSize(new Dimension (350,200));  
        contentPanel.add(description);
     
	}
	
	public void setMovie(Movie movie) throws SQLException {
		 this.movie = movie;
		 movieId = movie.getID();
	     loadImage(connection, pstmt, resultSet, imagePanel, movieId, imgLabel);	
	     loadMSlots();
	     
	     repaint(); // Refresh the UI with the new movie details		
	}
	
	public void loadMSlots() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);

	        pstmt = connection.prepareStatement("SELECT movie_date from movie_schedule where movie_id = ? ");
	        pstmt.setString(1, movieId);
	        
	        resultSet = pstmt.executeQuery();  
            
	        if (resultSet.next()) {
	   	     	JPanel datePanel = new JPanel();
	   	     	datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
	   	     	contentPanel.add(datePanel);
	   	     	
	            String movieDate = resultSet.getString("movie_date");
	            JLabel dateLabel = new JLabel(movieDate);
	            dateLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
	            datePanel.add(dateLabel);           
            
	            JPanel timePanel = new JPanel();
	            timePanel.setLayout(new FlowLayout());
	            datePanel.add(timePanel);

	            pstmt = connection.prepareStatement("SELECT movie_time from movie_schedule where movie_id = ? and movie_date = ?");
	            pstmt.setString(1, movieId);
	            pstmt.setString(2, movieDate);
	            
	            ResultSet resultSet2 = pstmt.executeQuery();
	            while (resultSet2.next()) {
	            	String movieTime = resultSet2.getString("movie_time");
                    JButton timeButton = new JButton(movieTime);
                    Color buttonColor = new Color(204,186,120);
                    Dimension d2 = new Dimension(250, 50);
                    timeButton.setBackground(buttonColor);
                    timeButton.setPreferredSize(d2);
                    timePanel.add(timeButton);
	                timeButton.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	Movie movie = new Movie();   
	        		        movie.setID(movieId);
	    		            movie.setTitle(titleLabel.getText());
	    		            movie.setDate(movieDate);
	    		            movie.setTime(movieTime);
	                    	
	                    	ticket ticket = new ticket();
	                    	try {
								ticket.setMovie(movie);								
							} catch (SQLException e1) {								
								e1.printStackTrace();
							}
	                    	
	                    	getParent().add(ticket); 
	                        contentPanel.setVisible(false);
	                        ticket.setVisible(true);	                        
	                        getParent().remove(MovieDetails.this); 

	                    }
	                });
	            }
	            resultSet2.close();
	            pstmt.close();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
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
		
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (movie != null) {
            titleLabel.setText(movie.getTitle());
            description.setText(movie.getDescription());
        }
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
