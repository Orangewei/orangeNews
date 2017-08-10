package orange.w.bean;

import java.util.List;

/**
 * Created by zqw on 2017/7/17.
 */

public class TouTiaoBean {
    private String message;//success
    private List<TouTiaoListBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TouTiaoListBean> getData() {
        return data;
    }

    public void setData(List<TouTiaoListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TouTiaoBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
