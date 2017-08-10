package orange.w.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zqw on 2017/7/20.
 * 头条图片浏览集合
 */

public class TouTiaoImageListBean implements Serializable{
    private String title;//标题
    private String source;//来源
    private int gallary_image_count;//图册数量
    private List<ImageList> image_list;

    @Override
    public String toString() {
        return "TouTiaoImageListBean{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", gallary_image_count=" + gallary_image_count +
                ", image_list=" + image_list +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getGallary_image_count() {
        return gallary_image_count;
    }

    public void setGallary_image_count(int gallary_image_count) {
        this.gallary_image_count = gallary_image_count;
    }

    public List<ImageList> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageList> image_list) {
        this.image_list = image_list;
    }

    public  class ImageList implements Serializable{
        private String url;
        private List<UrlList> url_list;

        public String getPc_url() {
            return url;
        }

        public void setPc_url(String url) {
            this.url = url;
        }

        public List<UrlList> getUrl_list() {
            return url_list;
        }

        public void setUrl_list(List<UrlList> url_list) {
            this.url_list = url_list;
        }

        @Override
        public String toString() {
            return "ImageList{" +
                    "pc_url='" + url + '\'' +
                    ", url_list=" + url_list +
                    '}';
        }
    }

   public class UrlList implements Serializable{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "UrlList{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}
