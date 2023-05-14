package cinema;

import javax.swing.JLabel;

public class Movie {
	
	private String id;
    private String title;
    private String description;
    public JLabel imgLabel;
    private String date;   
    private String time;
    
    public String getID() {
    	return id;
    }
    
    public void setID(String id) {
    	this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public JLabel getImage() {
    	return imgLabel;
    }
    
    public void setImage(JLabel image) {
    	this.imgLabel = image;
    }
    
    public String getDate() {
    	return date;
    }
    
    public void setDate(String date) {
    	this.date = date;
    }
    
    public String getTime() {
    	return time;
    }
    
    public void setTime(String time) {
    	this.time = time;
    }
 

}
