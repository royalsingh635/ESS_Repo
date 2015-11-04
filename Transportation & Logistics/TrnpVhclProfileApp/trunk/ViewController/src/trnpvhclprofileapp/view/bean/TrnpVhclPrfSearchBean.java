package trnpvhclprofileapp.view.bean;

import javax.faces.event.ActionEvent;

import adf.utils.bean.ADFBeanUtils;

public class TrnpVhclPrfSearchBean {
    public TrnpVhclPrfSearchBean() {
    }

    public void searchVhclProfileAL(ActionEvent actionEvent) {

        System.out.println("Inside the search method in bean !");
        ADFBeanUtils.findOperation("searchVhclProfile").execute();
    }

    public void resetVhclProfileAL(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("resetVhclProfile").execute();
    }

    public void addVhclProfileAL(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("addVhclProfile").execute();
    }
}
