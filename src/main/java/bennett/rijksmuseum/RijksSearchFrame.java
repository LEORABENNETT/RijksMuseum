package bennett.rijksmuseum;

import bennett.rijksmuseum.json.ArtObject;
import com.andrewoid.ApiKey;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RijksSearchFrame extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel resultsPanel;
    private JLabel[] resultLabels = new JLabel[10];
    private int currentPage = 1;
    private String currentQuery = "";

    public RijksSearchFrame() {
        setTitle("Rijksmuseum Search");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(5, 2));

        for (int i = 0; i < 10; i++) {
            resultLabels[i] = new JLabel();
            resultLabels[i].setHorizontalAlignment(JLabel.CENTER);
            resultsPanel.add(resultLabels[i]);
        }

        JPanel bottomPanel = new JPanel();
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuery = searchField.getText();
                currentPage = 1;
                searchAndDisplayResults();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    searchAndDisplayResults();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                searchAndDisplayResults();
            }
        });

        searchAndDisplayResults();
    }

    private void searchAndDisplayResults() {
        List<ArtObject> artObjects = fetchArtObjects(currentQuery, currentPage);
        displayResults(artObjects);
    }

    private List<ArtObject> fetchArtObjects(String query, int page) {
        List<ArtObject> artObjects = new ArrayList<>();
        try {
            ApiKey apiKey = new ApiKey();
            String keyString = apiKey.get();
            String url = "https://www.rijksmuseum.nl/api/en/collection?key=" + keyString + "&p=" + page + "&ps=10";
            if (query != null && !query.isEmpty()) {
                url += "&q=" + URLEncoder.encode(query, "UTF-8");
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray artObjectsArray = jsonResponse.getJSONArray("artObjects");

            for (int i = 0; i < artObjectsArray.length(); i++) {
                JSONObject artObjectJson = artObjectsArray.getJSONObject(i);
                String title = artObjectJson.getString("title");
                String artist = artObjectJson.has("principalOrFirstMaker") ? artObjectJson.getString("principalOrFirstMaker") : "Unknown";
                String imageUrl = artObjectJson.getJSONObject("webImage").getString("url");

                ArtObject artObject = new ArtObject();
                artObjects.add(artObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artObjects;
    }

    private void displayResults(List<ArtObject> artObjects) {
        for (int i = 0; i < 10; i++) {
            if (i < artObjects.size()) {
                ArtObject artObject = artObjects.get(i);
                resultLabels[i].setText("");
                resultLabels[i].setIcon(downloadImage(artObject.webImage.url));
                resultLabels[i].setToolTipText("<html>" + artObject.title + "<br>" + artObject.principalOrFirstMaker + "</html>");
                resultLabels[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        new ImageFrame(artObject.webImage.url).setVisible(true);
                    }
                });
            } else {
                resultLabels[i].setIcon(null);
                resultLabels[i].setText("No image");
                resultLabels[i].setToolTipText(null);
            }
        }
    }

    private ImageIcon downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage img = ImageIO.read(url);
            return new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RijksSearchFrame().setVisible(true);
            }
        });
    }
}
