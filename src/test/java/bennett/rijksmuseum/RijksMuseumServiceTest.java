package bennett.rijksmuseum;

import bennett.rijksmuseum.json.ArtObjects;
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
        ArtObjects artObject = currentCollection.getArtObjects()[0];
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
        ArtObjects artObject = currentCollection.getArtObjects()[0];
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
                "Michel angelo"
        ).blockingGet();

        // then
        assertNotNull(currentCollection);
        assertNotNull(currentCollection.getArtObjects());
        ArtObjects artObject = currentCollection.getArtObjects()[0];
    }
}
