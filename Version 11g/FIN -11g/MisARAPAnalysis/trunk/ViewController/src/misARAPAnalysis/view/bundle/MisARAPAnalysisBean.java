package misARAPAnalysis.view.bundle;

import java.math.BigInteger;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Calendar;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.event.TimeSelectorEvent;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.model.NumberRange;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class MisARAPAnalysisBean {

    private String Header;
    private RichTable tableBind;
    private String mode = "N";
    private RichInputText coaIdParam;
    private java.lang.Number coaId;
    //Get These @ date by function.

    private oracle.jbo.domain.Number startRange;
    private oracle.jbo.domain.Number endRange;
    private Integer coaIdForDetailTF;
    private NumberRange numRange;
    
   
    private Date startDateforTS=java.sql.Date.valueOf("2013-01-01");
    private Date endDateforTS=java.sql.Date.valueOf("2014-01-01");
    private RichInputText mainTableCoa;
    private RichInputText apCoaForRange;
    private RichTable coaDetailBind;
    private RichTable mainTableBind;

    public MisARAPAnalysisBean() {
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       //DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println("date is" +format.format(date));
        
        String eDate=format.format(date);
        String sDate;
        String month=eDate.substring(5,7);
        Integer year=Integer.parseInt(eDate.substring(0,4));
        Integer inMonth=Integer.parseInt(month);
        String day=eDate.substring(8,10);
       // System.out.println("Year :"+year +"inMonth "+inMonth + " day"+day);
        if(inMonth==1){
           year=year-1;
           inMonth=12;
           month="12";
           }else{
               if(inMonth<11)
                   month="0"+(inMonth-1);
               else
               month=(new Integer(inMonth-1)).toString(); 
           }         
        year=year-1;
        sDate=year.toString()+"-"+month+"-"+day;
        System.out.println("satrt date is "+sDate);
        startDateforTS=java.sql.Date.valueOf(sDate);
        endDateforTS=java.sql.Date.valueOf(eDate);
        
    }


    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void apRangeChangeListener(ValueChangeEvent valueChangeEvent) {

        NumberRange sliderValue = (NumberRange)valueChangeEvent.getNewValue();

        Number e1 = sliderValue.getMaximum();
        Number e2 = sliderValue.getMinimum();
        
        System.out.println("Starting Reange "+ e1 + "End Ramge "+e2);
       
         startRange=new oracle.jbo.domain.Number(e2.intValue());
         endRange=new oracle.jbo.domain.Number(e1.intValue());
         
        OperationBinding op = getBindings().getOperationBinding("setApRangeParameters");
        op.getParamsMap().put("p_range1", e2); // CORRECTED BY MS--CHANGED FROM E1 TO E2
        op.getParamsMap().put("p_range2", e1); // CHANGED FROM MS--CHANGED FROM  p_range1 TO p_range2
        op.execute();
    }
    public void arRangeChangeListener(ValueChangeEvent valueChangeEvent) {

        NumberRange sliderValue = (NumberRange)valueChangeEvent.getNewValue();

        Number e1 = sliderValue.getMaximum();
        Number e2 = sliderValue.getMinimum();
       
       
        OperationBinding op = getBindings().getOperationBinding("setArRangeParameters");
        op.getParamsMap().put("p_range1", e2);// CORRECTED BY MS--CHANGED FROM E1 TO E2
        op.getParamsMap().put("p_range2", e1); // CHANGED FROM MS--CHANGED FROM  p_range1 TO p_range2
        op.execute();
    }

    public void ButtonAction(ActionEvent actionEvent) {

        RichTable bind = getTableBind();
        bind.setVisible(true);
        mode = "Y";
    }

    public void setHeader(String Header) {
        this.Header = Header;
    }

    public String getHeader() {
        return Header;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void mainTableSelectionListener(SelectionEvent selectionEvent) {

        // set the selected row as current row
        invokeEL("#{bindings.MainTable11.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                 new Object[] { selectionEvent });

        coaId = Integer.parseInt(getCurrentRowAttr("MainTable1", "CoaId"));

        OperationBinding OpExe = getBindings().getOperationBinding("setCurrencyWiseOutstandingParameters");
        OpExe.getParamsMap().put("p_coa_id", coaId);
        OpExe.execute();

        OperationBinding OpExe1 = getBindings().getOperationBinding("setCoaVsOutstandingParameters");
        OpExe1.getParamsMap().put("p_coa_id", coaId);
        OpExe1.execute();

    }

    public String getCurrentRowAttr(String voNm, String attrNm) {

        OperationBinding op =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getCurrRowAttribute");
        op.getParamsMap().put("voNm", voNm);
        op.getParamsMap().put("attrNm", attrNm);
        op.execute();

        String retVal = op.getResult().toString();

        return retVal;
    }

    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    /**
     * Programmatic evaluation of EL.
     *
     * @param el EL to evaluate
     * @return Result of the evaluation
     */
    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        return exp.getValue(elContext);
    }

    public void aRDetailsSelectionListener(SelectionEvent selectionEvent) {
        //#{bindings.ARDetailsVO.collectionModel.makeCurrent}

        // set the selected row as current row
        invokeEL("#{bindings.ARDetailsVO.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                 new Object[] { selectionEvent });

        coaId = Integer.parseInt(getCurrentRowAttr("ARDetailsVO", "CoaId"));

        OperationBinding OpExe = getBindings().getOperationBinding("setARCurrencyWiseOutstandingParameters");
        OpExe.getParamsMap().put("p_coa_id", coaId);
        OpExe.execute();

        OperationBinding OpExe1 = getBindings().getOperationBinding("setARCoaVsOutstandingParameters");
        OpExe1.getParamsMap().put("p_coa_id", coaId);
        OpExe1.execute();

    }

    public String apCoaDetails() {

        coaId = null;
      //  this.setParamsForCoaDetailTf(null, null, null);
        
      this.setStartRange(null);
      this.setEndRange(null);
      this.setCoaIdForDetailTF(null);
        return "showDetail";
    }

    public String arCoaDetails() {

        coaId = null;

        return "ViewCoaDetails";
    }

    public String apCoaDetailsAction() {

        coaId = Integer.parseInt(getCurrentRowAttr("MainTable1", "CoaId"));
        System.out.println("coaId" + coaId);
        return "ViewCoaDetails";
    }

    public String arCoaDetailsAction() {

        coaId = Integer.parseInt(getCurrentRowAttr("ARDetailsVO", "CoaId"));
        System.out.println("coaId" + coaId);
        return "ViewCoaDetails";
    }

    public void setCoaIdParam(RichInputText coaIdParam) {
        this.coaIdParam = coaIdParam;
    }

    public RichInputText getCoaIdParam() {
        return coaIdParam;
    }

    public void setCoaId(Number coaId) {
        this.coaId = coaId;
    }

    public Number getCoaId() {
        return coaId;
    }

    public void processTimeSelector(TimeSelectorEvent event) {
        // System.out.println("inside time selector");
        java.sql.Date sdate = new java.sql.Date(event.getStartDate().getTime());
        java.sql.Date edate = new java.sql.Date(event.getEndDate().getTime());

        System.out.println("sdate--->" + sdate + " edate---->" + edate +"class is "+event.getEndDate().getTime());
        try {
            OperationBinding actionBinding = getBindings().getOperationBinding("filterCoaDEtailByDate");
            actionBinding.getParamsMap().put("start", (sdate));
            actionBinding.getParamsMap().put("end", edate);
            actionBinding.execute();

        } catch (Exception e) {
            System.out.println("Error executing the binding with new dates: " + e);

        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaDetailBind);
    }

    public void arBktDialogListener(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        // System.out.println("ar dialog");
        if (dialogEvent.getOutcome().name().equals("ok")) {
            //  System.out.println("ar dialog ok");
            OperationBinding opCommit = getBindings().getOperationBinding("Commit");
            opCommit.execute();

            OperationBinding opExe = getBindings().getOperationBinding("setGlobalParameter");
            opExe.execute();
            
           // setCoaId(null);
            
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            //  System.out.println("ar dialog cancel");
            OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
            opCommit.execute();

        } else {
            //    System.out.println("ar dialog else");
            
            OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
            opCommit.execute();
        }
    }

    public void apBktDialogListener(DialogEvent dialogEvent) {
      
        Outcome outcome = dialogEvent.getOutcome();
        //   System.out.println("ap dialog");

        if (dialogEvent.getOutcome().name().equals("ok")) {
            //  System.out.println("ap dialog ok");
            OperationBinding opCommit = getBindings().getOperationBinding("Commit");
            opCommit.execute();
            
            OperationBinding opExe = getBindings().getOperationBinding("setMainTableParameters");
            opExe.execute();
            
          //  setCoaId(null);

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            /// System.out.println("ap dialog cancel");
            OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
            opCommit.execute();
        } else {
            System.out.println("ar dialog else");
            OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
            opCommit.execute();
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.mainTableBind);
    }

    public void apBktCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // System.out.println("ap popup cancel");
        OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
        opCommit.execute();
    }

    public void arBktCancelListener(PopupCanceledEvent popupCanceledEvent) {
        //  System.out.println("ar popup cancel");
        OperationBinding opCommit = getBindings().getOperationBinding("Rollback");
        opCommit.execute();
    }

    public String showCoaDetail() {
        System.out.println("Performing Ation");
        
        //this.setParamsForCoaDetailTf((Integer)this.getApCoaForRange().getValue(),this.getStartRange(), this.getEndRange());
        //this.setStartRange(null);
        //this.setEndRange(null);
        this.setCoaIdForDetailTF((Integer)this.getApCoaForRange().getValue());
        return "showDetail";
    }

    public void setNumRange(NumberRange numRange) {
        this.numRange = numRange;
    }

    public NumberRange getNumRange() {
        return numRange;
    }


    public void setStartRange(oracle.jbo.domain.Number startRange) {
        this.startRange = startRange;
    }

    public oracle.jbo.domain.Number getStartRange() {
        return startRange;
    }

    public void setEndRange(oracle.jbo.domain.Number endRange) {
        this.endRange = endRange;
    }

    public oracle.jbo.domain.Number getEndRange() {
        return endRange;
    }

    public void setStartDateforTS(Date startDateforTS) {
        this.startDateforTS = startDateforTS;
    }

    public Date getStartDateforTS() {
        return startDateforTS;
    }

    public void setEndDateforTS(Date endDateforTS) {
        this.endDateforTS = endDateforTS;
    }

    public Date getEndDateforTS() {
        return endDateforTS;
    }
    
    
    //code is written by MS
    
//    public void setParamsForCoaDetailTf(Integer coaId,oracle.jbo.domain.Number startRage,oracle.jbo.domain.Number endRange){
//         AdfFacesContext.getCurrentInstance().getPageFlowScope().put("PARAM_COA_ID", coaId);
//         AdfFacesContext.getCurrentInstance().getPageFlowScope().put("PARAM_START_RANGE", startRage);
//         AdfFacesContext.getCurrentInstance().getPageFlowScope().put("PARAM_END_RANGE", endRange);
//    }

    public void setMainTableCoa(RichInputText mainTableCoa) {
        this.mainTableCoa = mainTableCoa;
    }

    public RichInputText getMainTableCoa() {
        return mainTableCoa;
    }

    public String showMainTableCoaDetail() {
      //  this.setParamsForCoaDetailTf((Integer)this.getMainTableCoa().getValue(), null, null);
        
        this.setStartRange(null);
        this.setEndRange(null);
        this.setCoaIdForDetailTF((Integer)this.getMainTableCoa().getValue());
        return "showDetail";
    }

    public void setApCoaForRange(RichInputText apCoaForRange) {
        this.apCoaForRange = apCoaForRange;
    }

    public RichInputText getApCoaForRange() {
        return apCoaForRange;
    }

    public void setCoaIdForDetailTF(Integer coaIdForDetailTF) {
        this.coaIdForDetailTF = coaIdForDetailTF;
    }

    public Integer getCoaIdForDetailTF() {
        return coaIdForDetailTF;
    }

    public void ArDisclosuer(DisclosureEvent disclosureEvent) {
      OperationBinding op=this.getBindings().getOperationBinding("setBindForAr");
      op.execute(); 
    }

    public void createDefautBucket(ActionEvent actionEvent) {
       OperationBinding op=this.getBindings().getOperationBinding("Createwithparameters");
       op.execute();
        OperationBinding op1=this.getBindings().getOperationBinding("Commit");
        op1.execute();
       
    }

    public void setCoaDetailBind(RichTable coaDetailBind) {
        this.coaDetailBind = coaDetailBind;
    }

    public RichTable getCoaDetailBind() {
        return coaDetailBind;
    }

    public void setMainTableBind(RichTable mainTableBind) {
        this.mainTableBind = mainTableBind;
    }

    public RichTable getMainTableBind() {
        return mainTableBind;
    }
}
