package bennett.rijksmuseum;

import bennett.rijksmuseum.json.CurrentCollection;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://www.rijksmuseum.nl/en
 */
public interface RijksMuseumService {

    // api key = jb32sYqU

    @GET("/api/en/collection")
    Single<CurrentCollection> currentCollection(
            @Query("appid") String appId,
            @Query("p") String PageNumber
    );

    @GET("/api/en/collection")
    Single<CurrentCollection> getFromPageNumber(
            @Query("appid") String appId,
            @Query("q") String Query
    );

    @GET("/api/en/collection")
    Single<CurrentCollection> getFromQuery(
            @Query("appid") String appId,
            @Query("involvedMaker") String Artist,
            @Query("p") String PageNumber
            );
}
