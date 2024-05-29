package bennett.rijksmuseum;

import bennett.rijksmuseum.json.ArtObject;
import bennett.rijksmuseum.json.CurrentCollection;
import com.andrewoid.ApiKey;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.net.URL;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RijksSearchFrame extends JFrame {
    private ApiKey apiKey = new ApiKey();
    private String keyString = apiKey.get();
    private JTextField searchBar;
    private JButton searchButton;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel resultsPanel;
    private int currentPage = 1;

    public RijksMuseumService rijksMuseumService;

    public RijksSearchFrame(RijksMuseumService rijksMuseumService) {
        this.rijksMuseumService = rijksMuseumService;

        setTitle("Rijksmuseum Search");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchBar = new JTextField(20);
        searchButton = new JButton("Search");
        topPanel.add(searchBar);
        topPanel.add(searchButton);

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        topPanel.add(prevButton);
        topPanel.add(nextButton);
        add(topPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(5, 2));

        // Action listeners: previous, next, search
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    imageSearch();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                imageSearch();
            }
        });

        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage = 1;
                imageSearch();
            }
        });

        add(resultsPanel, BorderLayout.CENTER);
    }

    // Image search method
    private void imageSearch() {
        String query = searchBar.getText().trim();

        if (query.isEmpty()) {
            rijksMuseumService.getFromPageNumber(keyString, currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(SwingSchedulers.edt())
                    .subscribe(
                            this::handleResponse,
                            Throwable::printStackTrace);
        } else {
            rijksMuseumService.getFromQuery(keyString, query, currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(SwingSchedulers.edt())
                    .subscribe(
                            this::handleResponse,
                            Throwable::printStackTrace);
        }
    }

    // Handle response method
    private void handleResponse(CurrentCollection currentCollection) {
        resultsPanel.removeAll();

        for (ArtObject artObject : currentCollection.getArtObject()) {
            JLabel label = new JLabel();
            downloadAndSetImage(label, artObject.getImageUrl());
            label.setToolTipText("<html>" + artObject.getTitle() + "<br>"
                    + artObject.getArtist() + "</html>");
            resultsPanel.add(label);
        }

        revalidate();
        repaint();
    }

    // Download and set image method
    private void downloadAndSetImage(JLabel label, String imageUrl) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                URL url = new URL(imageUrl);
                ImageIcon imageIcon = new ImageIcon(url);
                Image image = imageIcon.getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                return new ImageIcon(image);
            }

            @Override
            protected void done() {
                try {
                    label.setIcon(get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        RijksMuseumService rijksMuseumService = new RijksMuseumServiceFactory().getService();
        new RijksSearchFrame(rijksMuseumService).setVisible(true);
    }
}