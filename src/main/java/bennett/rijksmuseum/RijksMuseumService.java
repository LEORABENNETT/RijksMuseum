package bennett.rijksmuseum;

import bennett.rijksmuseum.json.CurrentCollection;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://www.rijksmuseum.nl/en
 */
public interface RijksMuseumService {

    @GET("/api/en/collection")
    Single<CurrentCollection> getFromPageNumber(
            @Query("key") String apiKey,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<CurrentCollection> getFromQuery(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<CurrentCollection> getFromArtist(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("p") int pageNumber,
            @Query("involvedMaker") String artist
            );
}
