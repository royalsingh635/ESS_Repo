package mnfPlanningBoardApp.view.bean;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

import javax.faces.event.ActionEvent;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichListView;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class PlanningBoardBean {

    Integer currYear = 0;
    Integer currMonth = 0;
    Calendar currDate = Calendar.getInstance();
    OperationBinding ob = null;
    private Boolean refresh = true;

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public Boolean getRefresh() {
        if (refresh == true) {
            refresh = false;
            return true;
        }
        return refresh;
    }
    /** Product Task flow = 1  "H"
         * Customer = 2         "C"
         * BOM = 3            "B"
         * Raw = 4           "M"
         * Suplier = 5      "S"
         * */
    StringBuffer switcMode = new StringBuffer("H");
    StringBuffer homeMode = new StringBuffer("OV");
    StringBuffer prevPage = new StringBuffer("");
    StringBuffer prodDetails = new StringBuffer("");
    
    private String taskFlowId = "/WEB-INF/productTF.xml#productTF";

    public String goToCustomer() {
        
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        System.out.println("Current id is " + currId);
        if (currId.compareTo(2) != 0) {
            System.out.println("Customer task flow");
            taskFlowId = "/WEB-INF/customerTF.xml#customerTF";
            setSwitcMode(new StringBuffer("C"));
        }
     
        return null;

    }

    public String goToSupplier() {
        
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        System.out.println("Current id is " + currId);
        
        if (currId.compareTo(5) != 0) {
            System.out.println("supplier task flow");
            setSwitcMode(new StringBuffer("fetchCurrenttfId"));
            taskFlowId = "/WEB-INF/supplierTF.xml#supplierTF";
        }
        
       
        refresh = true;
        return null;
    }

    public String goToBOM() {
        
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        System.out.println("Current id is " + currId);
        if (currId.compareTo(3) != 0) {
            System.out.println("bom task flow");
            setSwitcMode(new StringBuffer("B"));
            taskFlowId = "/WEB-INF/bomTF.xml#bomTF";
        }
        
        
        return null;
    }

    public String goToRM() {
        
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        System.out.println("Current id is " + currId);
        if (currId.compareTo(4) != 0) {
            System.out.println("RM task flow");
            setSwitcMode(new StringBuffer("M"));
            taskFlowId = "/WEB-INF/rawMaterialTF.xml#rawMaterialTF";
        }
        
       
        refresh = true;
        return null;
    }

    public String goToProduct() { 
        
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        System.out.println("Current id is " + currId);
        if (currId.compareTo(1) != 0) {
            System.out.println("Product task flow");
            setSwitcMode(new StringBuffer("H"));
            taskFlowId = "/WEB-INF/productTF.xml#productTF";
        }

        
        refresh = true;
        return null;
    }
    
    public String backAction() {
        ob = ADFUtils.findOperation("fetchCurrenttfId");
        ob.execute();
        Integer currId = (Integer) ob.getResult();
        switch (currId) {
        case 1:
            setSwitcMode(new StringBuffer("H"));
            taskFlowId = "/WEB-INF/productTF.xml#productTF";
            break;
        case 2:
            setSwitcMode(new StringBuffer("C"));
            taskFlowId = "/WEB-INF/customerTF.xml#customerTF";
            break;
        case 3:
            setSwitcMode(new StringBuffer("B"));
            taskFlowId = "/WEB-INF/bomTF.xml#bomTF";
            break;
        case 4:
            setSwitcMode(new StringBuffer("M"));
            taskFlowId = "/WEB-INF/rawMaterialTF.xml#rawMaterialTF";
            break;
        case 5:
            setSwitcMode(new StringBuffer("SU"));
            taskFlowId = "/WEB-INF/supplierTF.xml#supplierTF";
            break;
        default:
            System.out.println("Invalid grade");
        }
        return null;
    }

    public void setProdDetails(StringBuffer prodDetails) {
        this.prodDetails = prodDetails;
    }

    public StringBuffer getProdDetails() {
        return prodDetails;
    }
    private RichTable inputSrcTablBinding;

    public void setPrevPage(StringBuffer prevPage) {
        this.prevPage = prevPage;
    }

    public StringBuffer getPrevPage() {
        return prevPage;
    }

    public void setHomeMode(StringBuffer homeMode) {
        this.homeMode = homeMode;
    }

    public StringBuffer getHomeMode() {
        return homeMode;
    }

    private RichListView customerNameListBinding;

    public void setInputSrcTablBinding(RichTable inputSrcTablBinding) {
        this.inputSrcTablBinding = inputSrcTablBinding;
    }

    public RichTable getInputSrcTablBinding() {
        return inputSrcTablBinding;
    }

    public PlanningBoardBean() {
        //Default Constructor
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


    public void setSwitcMode(StringBuffer switcMode) {
        this.switcMode = switcMode;
    }

    public StringBuffer getSwitcMode() {
        return switcMode;
    }

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

    public void homeNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("H"));
    }

    public void planningNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("P"));
    }

    public void scheduleNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("SC"));
    }

    public void mrpNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("M"));
    }

    public void bomNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("B"));
    }

    public void supplierNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("SU"));
    }

    public void customerNavACE(ActionEvent actionEvent) {
        setSwitcMode(new StringBuffer("C"));
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
        System.out.println(date);
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("cur_dt", date);
        ob.execute();

    }

    public void getCurrDateDetail() {

        Integer year = currDate.get(Calendar.YEAR);
        System.out.println(" Year = " + year);

    }

    protected String monthName(Integer mnt) {
        String month = "";
        switch (mnt) {
        case 0:
            month = "January";
            break;
        case 1:
            month = "February";
            break;
        case 2:
            month = "March";
            break;
        case 3:
            month = "April";
            break;
        case 4:
            month = "May";
            break;
        case 5:
            month = "June";
            break;
        case 6:
            month = "July";
            break;
        case 7:
            month = "August";
            break;
        case 8:
            month = "September";
            break;
        case 9:
            month = "October";
            break;
        case 10:
            month = "November";
            break;
        case 11:
            month = "December";
            break;
        }

        return month;
    }

    public void janACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("viewChangeCustProd");
        ob.getParamsMap().put("mont", 01);
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

    public void augACE(ActionEvent actionEvent) {
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

    public void setCustomerNameListBinding(RichListView customerNameListBinding) {
        this.customerNameListBinding = customerNameListBinding;
    }

    public RichListView getCustomerNameListBinding() {
        return customerNameListBinding;
    }

    public void getSelectedCustomer() {

    }

    public void CustomerNameSelectionListener(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.LOVCustomerNameList.treeModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                          selectionEvent });

        ADFUtils.findOperation("custPrdViwByCustomer").execute();
    }


    public void inputSourceACE(ActionEvent actionEvent) {

        setHomeMode(new StringBuffer("IS"));
    }

    public void InSourceProcesACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("callreserveResourceFun").execute();

    }

    public void prevPageFrmPrdDetACE(ActionEvent actionEvent) {
        setHomeMode(getPrevPage());
    }

    public void InSourceBackACE(ActionEvent actionEvent) {
        setHomeMode(new StringBuffer("OV"));
    }

    public void refreshOverviewACE(ActionEvent actionEvent) {

    }

    public void refreshInputSourceACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("callSoInsertFun").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputSrcTablBinding);
    }

    public void overToPrdDetailsACE(ActionEvent actionEvent) {
        String item = getAttrsVal("MnfPlnPrdVOIterator", "OutptItmId").toString();
        ob = ADFUtils.findOperation("setViewCustomerListBind");
        ob.getParamsMap().put("out_itm_id", item);
        ob.execute();

        ob = ADFUtils.findOperation("setViewProductOverviewBind");
        ob.getParamsMap().put("out_itm_id", item);
        ob.execute();

        setHomeMode(new StringBuffer("PD"));
        setProdDetails(new StringBuffer("FPR"));
        setPrevPage(new StringBuffer("OV"));
    }

    public void txnToPrdDetailsACE(ActionEvent actionEvent) {
        String item = getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId").toString();
        String srcDocId = getAttrsVal("MnfPlnTxnVOIterator", "DocIdSrc").toString();
        ob = ADFUtils.findOperation("setTxnCustomerListBind");
        ob.getParamsMap().put("out_itm_id", item);
        ob.getParamsMap().put("src_doc_id", srcDocId);
        ob.execute();

        ob = ADFUtils.findOperation("setViewProductTxnBind");
        ob.getParamsMap().put("out_itm_id", item);
        ob.getParamsMap().put("src_doc_id", srcDocId);
        ob.execute();

        setHomeMode(new StringBuffer("PD"));
        setProdDetails(new StringBuffer("FTX"));
        setPrevPage(new StringBuffer("IS"));

    }

    public void backToSrcACE(ActionEvent actionEvent) {
        setHomeMode(getPrevPage());
    }

    public void prdToCustomerDetailsACE(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("currentCustomer");
        Integer customer = selectCustomer(key, "LOVOverviewPrdCustListIterator");
        System.out.println(customer + "   This si current customer");

        ob = ADFUtils.findOperation("setCustomerList");
        ob.getParamsMap().put("eo_id", customer);
        ob.execute();
        setSwitcMode(new StringBuffer("C"));
    }

    public Integer selectCustomer(Key key, String iterName) {
        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);
            if (row != null)
                System.out.println(getAttrsVal("LOVOverviewPrdCustListIterator", "CustEoId"));
            return (Integer) getAttrsVal("LOVOverviewPrdCustListIterator", "CustEoId");
        }
        return 0;

    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public void getCurrentTF() {

        Integer curTF = (Integer) ADFUtils.findOperation("initCurrentTfId").execute();
        switch (curTF) {
        case 1:
            setSwitcMode(new StringBuffer("H"));
            System.out.println(getSwitcMode() + " Current Mode");
            break;
        case 2:
            setSwitcMode(new StringBuffer("C"));
            System.out.println(getSwitcMode() + " Current Mode");
            break;
        case 3:
            setSwitcMode(new StringBuffer("B"));
            System.out.println(getSwitcMode() + " Current Mode");
            break;
        case 4:
            setSwitcMode(new StringBuffer("M"));
            System.out.println(getSwitcMode() + " Current Mode");
            break;
        case 5:
            setSwitcMode(new StringBuffer("SU"));
            System.out.println(getSwitcMode() + " Current Mode");
            break;
        default:
            System.out.println("Invalid grade");
        }
    }

    
}
