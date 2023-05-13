package admin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.swing.*;


public class ImageUploader extends JFrame {
    
    private Connection conn;
    private PreparedStatement pstmt;
    private JFileChooser fileChooser;
    private JLabel imageLabel;
    private JButton uploadButton;
    private JTextField movieIdField;
 
    public ImageUploader() {
        try {

            Properties props = new Properties();
            try (InputStream in = new FileInputStream("src/resource/database.properties")) {
                props.load(in);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            conn = DriverManager.getConnection(url, username, password);

            pstmt = conn.prepareStatement("UPDATE movie SET poster = ?, img_content = ? WHERE movie_id = ?");
            
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            JPanel panel = new JPanel();
            JLabel movieIdLabel = new JLabel("Movie ID:");
            movieIdField = new JTextField(10);
            JButton button = new JButton("Choose Image");
            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            uploadButton = new JButton("Upload");
            uploadButton.setEnabled(false);
            panel.add(movieIdLabel);
            panel.add(movieIdField);
            panel.add(button);
            panel.add(uploadButton);
            add(panel, BorderLayout.NORTH);
            add(imageLabel, BorderLayout.CENTER);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int result = fileChooser.showOpenDialog(ImageUploader.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();

                        ImageIcon icon = new ImageIcon(file.getPath());
                        imageLabel.setIcon(icon);

                        uploadButton.setEnabled(true);
                        
                        try {
                            String id = movieIdField.getText();
                            pstmt.setString(1, file.getName());
                            pstmt.setBytes(2, readFile(file));
                            pstmt.setString(3, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            uploadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        pstmt.executeUpdate();
                        
                        uploadButton.setEnabled(false);
                        
                        String movieId = movieIdField.getText();
                        JOptionPane.showMessageDialog(ImageUploader.this, "Image for movie " + movieId + " uploaded successfully!");
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private byte[] readFile(File file) {
        try (InputStream in = new FileInputStream(file); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        new ImageUploader();
    }
}
