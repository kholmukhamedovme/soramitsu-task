package me.kholmukhamedov.soramitsutest.data.remote;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import retrofit2.http.GET;

/**
 * Retrofit interface of Flickr API
 */
public interface ApiService {

    /**
     * Base URL
     */
    String HOST = "https://api.flickr.com";

    /**
     * Get public feed
     *
     * @return Single RxJava source with {@link FeedBean} bean
     * @see <a href="https://www.flickr.com/services/feeds/docs/photos_public/">Documentation</a>
     */
    @GET("/services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    Single<FeedBean> getFeed();

}
