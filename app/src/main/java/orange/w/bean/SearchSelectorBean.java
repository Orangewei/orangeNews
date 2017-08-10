package orange.w.bean;

/**
 * Created by zqw on 2017/7/19.
 */

public class SearchSelectorBean {
    public SearchSelectorBean() {
    }

    public SearchSelectorBean(int dataType, int position) {
        this.dataType = dataType;
        this.position = position;
    }

    public int dataType;
    public int position;
}
