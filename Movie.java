package cinema;

import javax.swing.JLabel;

public class Movie {
	
	private String id;
    private String title;
    private String description;
    public JLabel imgLabel;
    
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
    
    public JLabel getImage() {
    	return imgLabel;
    }
    
    public void setImage(JLabel image) {
    	this.imgLabel = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
