package transferbalances.view.bean;

import oracle.adf.view.rich.component.rich.RichPopup;

public class PopupBean {
    private RichPopup bindPopup;

    public PopupBean() {
    }

    public void setBindPopup(RichPopup bindPopup) {
        this.bindPopup = bindPopup;
    }

    public RichPopup getBindPopup() {
        return bindPopup;
    }
}
