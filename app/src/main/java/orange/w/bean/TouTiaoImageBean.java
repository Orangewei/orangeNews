package orange.w.bean;

import java.util.List;

/**
 * Created by zqw on 2017/7/20.
 * 头条图片浏览bean
 */

public class TouTiaoImageBean {
    private String message;
    private List<TouTiaoImageListBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TouTiaoImageListBean> getData() {
        return data;
    }

    public void setData(List<TouTiaoImageListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TouTiaoImageBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
