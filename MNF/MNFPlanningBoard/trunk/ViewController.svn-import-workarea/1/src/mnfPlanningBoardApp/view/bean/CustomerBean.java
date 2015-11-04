package mnfPlanningBoardApp.view.bean;

import java.util.Calendar;

import java.util.Date;

import javax.faces.event.ActionEvent;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import mnfPlanningBoardApp.view.utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichListView;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class CustomerBean {
    private RichListView customerNameListViewBinding;

    public CustomerBean() {
    }

    Integer pageNavId = 1;

    public void setPageNavId(Integer pageNavId) {
        this.pageNavId = pageNavId;
    }

    public Integer getPageNavId() {
        return pageNavId;
    }
    Integer currYear = 0;
    Integer currMonth = 0;
    Calendar currDate = Calendar.getInstance();
    OperationBinding ob = null;

    public void setCurrYear(Integer currYear) {

        Integer year = currDate.get(Calendar.YEAR);
        this.currYear = year;
    }

    public Integer getCurrYear() {
        Integer year = currDate.get(Calendar.YEAR);
        return year;
    }

    public void setCurrMonth(Integer currMonth) {
        Integer month = currDate.get(Calendar.MONTH);
        this.currMonth = month;
    }

    public Integer getCurrMonth() {
        Integer month = currDate.get(Calendar.MONTH);
        return month;
    }

    public void setCurrDate(Calendar currDate) {
        this.currDate = currDate;
    }

    public Calendar getCurrDate() {
        return currDate;
    }


    public void todayACE(ActionEvent actionEvent) {
        Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Put it back in the Date object
        date = cal.getTime();

        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("cur_dt", date);
        ob.execute();

    }

    public void prevYearACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("yr", getCurrYear() - 1);
        ob.execute();
    }

    public void curYearACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("yr", getCurrYear());
        ob.execute();
    }

    public void nextYearACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("yr", getCurrYear() + 1);
        ob.execute();
    }

    public void janACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 01);
        ob.execute();
    }

    public void febACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 02);
        ob.execute();
    }

    public void marACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 03);
        ob.execute();
    }

    public void aprACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 04);
        ob.execute();
    }

    public void mayACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 05);
        ob.execute();
    }

    public void junACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 06);
        ob.execute();
    }

    public void julACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 07);
        ob.execute();
    }

    public void augACfilterCustomerPerPlanIdE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 8);
        ob.execute();
    }

    public void sepACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 9);
        ob.execute();
    }

    public void octACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 10);
        ob.execute();
    }

    public void novACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 11);
        ob.execute();
    }

    public void decACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 12);
        ob.execute();
    }

    public void allProductACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("resetViewChangeCustProd").execute();
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void customerListSL(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.LOVMainCustomerNameList.treeModel.makeCurrent}", new Class[] {
                          SelectionEvent.class }, new Object[] { selectionEvent });
        ADFUtils.findOperation("custPrdViwByCustomer").execute();
    }

    public void setCustomerNameListViewBinding(RichListView customerNameListViewBinding) {
        this.customerNameListViewBinding = customerNameListViewBinding;
    }

    public RichListView getCustomerNameListViewBinding() {
        return customerNameListViewBinding;
    }

    public void customerSelectACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("ListProductOnCustomerSelection");
        binding.getParamsMap().put("eo_id", actionEvent.getComponent().getAttributes().get("custId"));
        binding.execute();

    }

    public void custPrdSelecACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("setProductDetailsBindPerCustomer");
        binding.getParamsMap().put("eo_id", actionEvent.getComponent().getAttributes().get("cust_id"));
        binding.getParamsMap().put("prd_id", actionEvent.getComponent().getAttributes().get("prd_id"));
        binding.execute();
    }

    public void filterEvolutionACE(ActionEvent actionEvent) {   
        
        ADFUtils.findOperation("filterCustomerPerPlanId").execute(); 
    }
            
    public void resetFilterCustomerACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("resetFilterCustomerPerPlan").execute();
    }

    public void resetEvolutionACE(ActionEvent actionEvent) {
        // Add event code here...resetFilterCustomerPerPlan
    }

    public void updateCustProfileACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("updateCustomer");
        binding.execute();
        Integer retrn = (Integer)binding.getResult();
        if (retrn.compareTo(1)==0) {
            JSFUtils.addFacesErrorMessage("Please Select Evaluation Parameter and Plan ID");
        } else
            JSFUtils.addFacesInformationMessage("Customer Evaluation is Updated");
    }

  
}
