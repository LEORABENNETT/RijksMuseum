package bennett.rijksmuseum;

import bennett.rijksmuseum.json.CurrentCollection;
import io.reactivex.rxjava3.core.Single;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RijksMuseumServiceTest {

    private static class TestRijksMuseumService implements RijksMuseumService {
        @Override
        public Single<CurrentCollection> currentCollection(String appId, String PageNumber) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }

        @Override
        public Single<CurrentCollection> getFromPageNumber(String appId, String Query) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }

        @Override
        public Single<CurrentCollection> getFromQuery(String appId, String Artist, String PageNumber) {
            // Simulate API call and return a mock response
            CurrentCollection mockCollection = new CurrentCollection();
            return Single.just(mockCollection);
        }
    }

    @Test
    public void testCurrentCollection() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.currentCollection("jb32sYqU", "1");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }

    @Test
    public void testGetFromPageNumber() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.getFromPageNumber("jb32sYqU", "Rembrandt");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }

    @Test
    public void testGetFromQuery() {
        RijksMuseumService service = new TestRijksMuseumService();

        Single<CurrentCollection> single = service.getFromQuery("jb32sYqU", "Vermeer", "1");

        // Assert that the returned Single is not null
        assertEquals(true, single != null);
    }
}
