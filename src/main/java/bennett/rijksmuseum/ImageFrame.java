package bennett.rijksmuseum;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageFrame extends JFrame {
    private JLabel imageLabel;

    public ImageFrame(String imageUrl, String artist, String title) {
        setTitle(title + " by " + artist);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        loadImage(imageUrl);

        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage img = ImageIO.read(url);
            Image scaledImage = img.getScaledInstance(800, -1, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            imageLabel.setText("Failed to load image");
        }
    }
}
