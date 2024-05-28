package bennett.rijksmuseum;

import bennett.rijksmuseum.json.ArtObject;
import bennett.rijksmuseum.json.CurrentCollection;
import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RijksMuseumServiceTest {

    @Test
    void getFromPageNumber() {
        // given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        // when
        CurrentCollection currentCollection = service.getFromPageNumber(
                keyString,
                2
        ).blockingGet();

        // then
        assertNotNull(currentCollection);
        assertNotNull(currentCollection.getArtObjects());
        ArtObject artObject = currentCollection.getArtObjects()[0];
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);
    }

    @Test
    void getFromQuery() {
        // given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        // when
        CurrentCollection currentCollection = service.getFromQuery(
                keyString,
                "",
                2
        ).blockingGet();

        // then
        assertNotNull(currentCollection);
        assertNotNull(currentCollection.getArtObjects());
        ArtObject artObject = currentCollection.getArtObjects()[0];
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);
    }

    @Test
    void getFromArtist() {
        // given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        // when
        CurrentCollection currentCollection = service.getFromArtist(
                keyString,
                2,
                "Michelangelo"
        ).blockingGet();

        // then
        assertNotNull(currentCollection);
        assertNotNull(currentCollection.getArtObjects());
        ArtObject artObject = currentCollection.getArtObjects()[0];
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);
    }
}
