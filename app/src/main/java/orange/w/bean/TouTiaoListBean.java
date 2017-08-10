package orange.w.bean;

import java.io.Serializable;

/**
 * Created by zqw on 2017/7/17.
 * 头条条目bean
 */

public class TouTiaoListBean implements Serializable{
    private String title;//标题
    private String source;//媒体
    private String image_url;//图片地址
    private String group_id;//新闻ID 跳转相关

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "TouTiaoListBean{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
