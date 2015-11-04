package bdgfinancebudgetapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichLink;

public class RegionMonthWiseBean {
    public RegionMonthWiseBean() {
    }

    public void detailAmountAL(ActionEvent actionEvent) {
        System.out.println("_-----------------");
        RichLink ob = (RichLink) actionEvent.getSource();
        System.out.println("Selecter row is " + ob.getAttributes().get("selectedMonth"));
    }
}
