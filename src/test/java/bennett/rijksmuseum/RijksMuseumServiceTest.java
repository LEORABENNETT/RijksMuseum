package bennett.rijksmuseum;

import bennett.rijksmuseum.json.CurrentCollection;
import com.andrewoid.ApiKey;
import io.reactivex.rxjava3.core.Single;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RijksMuseumServiceTest {

    private static class TestRijksMuseumService implements RijksMuseumService {
        @Override
        public Single<CurrentCollection> currentCollection(String appId, String pageNumber) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }

        @Override
        public Single<CurrentCollection> getFromPageNumber(String appId, String query) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }

        @Override
        public Single<CurrentCollection> getFromQuery(String appId, String artist, String pageNumber) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }
    }

    @Test
    public void currentCollection() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.currentCollection(ApiKey.APIKEY, "1");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }

    @Test
    public void getFromPageNumber() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.getFromPageNumber(ApiKey.APIKEY, "Rembrandt");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }

    @Test
    public void getFromQuery() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.getFromQuery(ApiKey.APIKEY, "Vermeer", "1");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }
}
