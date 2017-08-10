package orange.w.http;

import io.reactivex.Observable;
import orange.w.bean.TouTiaoBean;
import orange.w.bean.TouTiaoImageBean;
import orange.w.common.BaseApi;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by zqw on 2017/7/17.
 */

public class TouTiaoApi extends BaseApi {
    private static final String TTHOST = "https://www.toutiao.com/api/article/feed/";

    public static Observable<TouTiaoBean> newsList(String url) {
        return retrofit(TTHOST)
                .create(NewsService.class)
                .newsList(url);
    }

    public static Observable<TouTiaoImageBean> imageList(String url) {
        return retrofit(TTHOST)
                .create(ImageService.class)
                .imageList(url);
    }

    public interface NewsService {
        @GET
        Observable<TouTiaoBean> newsList(@Url String url);
    }

   public interface ImageService {
        @GET
        Observable<TouTiaoImageBean> imageList(@Url String url);
    }
}
