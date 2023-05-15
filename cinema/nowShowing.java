package cinema;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JScrollPane;  
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class nowShowing extends JPanel implements ActionListener {
	private String username = "root";
	private String password = "cmaeerica";

	Color mainMenuBG= new Color(0,103,76,255);
	Color panelBG = new Color (236,236,235);
	
    Connection connection = null;
	ResultSet resultSet = null;
    PreparedStatement pstmt = null;
    Statement statement = null;
	
	JPanel nsPanel = new JPanel();
	JLabel imageLabel;
	private MovieDetails movieDetails;
	
	public nowShowing(MovieDetails movieDetails) {
		this.movieDetails = movieDetails;
		
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

        nsPanel.setBounds(10, 140, 1260, 590);
        nsPanel.setPreferredSize(new Dimension(1260, 1000));
        nsPanel.setLayout(new GridLayout(0, 3)); // display 3 movie panels per row
        JScrollPane nsScroll = new JScrollPane(nsPanel);
        nsScroll.setBounds(10, 140, 1260, 590);
        add(nsScroll);
        
        JButton btnNS = new JButton("NOW SHOWING");
        btnNS.setBounds(560, 66, 125, 23);
        btnNS.setEnabled(false);
        btnNS.addActionListener(this);
        add(btnNS);	
        
        JButton btnCS = new JButton("COMING SOON");
        btnCS.setBounds(710, 66, 125, 23);
        btnCS.setEnabled(true);
        btnCS.addActionListener(this);
        add(btnCS);       
        
        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(10, 106, 95, 23);
        btnBack.addActionListener(this);
        add(btnBack);
       
        loadNowShowing(movieDetails);
	}

	public void loadNowShowing(MovieDetails movieDetails) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost/cinema", username, password);

	        statement = connection.createStatement();
	        String query = "select movie_id, movie_title, movie_description, runtime "
	            + "from movie "
	            + "where movie_status='NS'";

	        resultSet = statement.executeQuery(query);
	        
	        int size = 1400;
	        while (resultSet.next()) {
	            JPanel moviePanel = new JPanel();
	            moviePanel.setPreferredSize(new Dimension(400, 210));
	            nsPanel.add(moviePanel);

	            JPanel imagePanel = new JPanel();
	            imagePanel.setPreferredSize(new Dimension(250, 250));
	            moviePanel.add(imagePanel);

	            JLabel imageLabel = new JLabel();
	            imagePanel.add(imageLabel);

	            JLabel titleLabel = new JLabel(resultSet.getString("movie_title")
	                    + " (" + resultSet.getString("runtime") + " min)");
	            titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	            titleLabel.setHorizontalAlignment(JLabel.CENTER);
	            titleLabel.setPreferredSize(new Dimension (350,20));
	            moviePanel.add(titleLabel);

	            JTextArea description = new JTextArea(resultSet.getString("movie_description"));
	            description.setFont(new Font("Tahoma", Font.PLAIN, 14));
	            description.setLineWrap(true);
	            description.setWrapStyleWord(true);
	            description.setEditable(false);
	            description.setPreferredSize(new Dimension (350,200));
	            description.setBackground(panelBG);
	            moviePanel.add(description);

	            String movieId = resultSet.getString("movie_id");
	            loadImage(connection, pstmt, resultSet, imagePanel, movieId, imageLabel);
	            
	            moviePanel.addMouseListener(new MouseAdapter() {
        			@Override
        			 public void mouseClicked(MouseEvent e) {
        		        Movie movie = new Movie();   
        		        movie.setID(movieId);
    		            movie.setTitle(titleLabel.getText());
    		            movie.setDescription(description.getText());
    		            
        		        try {
							movieDetails.setMovie(movie);
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
        		        Start.pages(6);      		        
        		    }

        			@Override
        			public void mouseEntered(MouseEvent e) {
        				imagePanel.setBackground(Color.LIGHT_GRAY);
        				moviePanel.setBackground(Color.LIGHT_GRAY);
        				description.setBackground(Color.LIGHT_GRAY);
        			}
        			@Override
        			public void mouseExited(MouseEvent e) {
        				imagePanel.setBackground(panelBG);
        				moviePanel.setBackground(panelBG);
        				description.setBackground(panelBG);
        			}
        		});
	        }
	        
	        nsPanel.setPreferredSize(new Dimension (1260, size));
	        size+=700;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (pstmt != null) pstmt.close();
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	//Reusing the connection in the constructor instead of creating a new one inside the method to avoid resource leaks
	public void loadImage(Connection connection, PreparedStatement pstmt, ResultSet resultSet, JPanel imagePanel, String movieId, JLabel imageLabel) {
	    try {
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
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = e.getActionCommand();
		
		if(buttonText.equals("NOW SHOWING")) {
			Start.pages(4);

		} else if(buttonText.equals("COMING SOON")) {
			Start.pages(5);
			
		} else if(buttonText.equals("BACK")) {
			Start.pages(1);			
		}		

	}

		
}