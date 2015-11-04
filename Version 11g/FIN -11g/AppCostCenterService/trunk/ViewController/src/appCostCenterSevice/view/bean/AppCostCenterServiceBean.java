package appCostCenterSevice.view.bean;


import appCostCenterSevice.model.service.AppCostCenterServiceAMImpl;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DecimalFormat;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class AppCostCenterServiceBean  {


    public AppCostCenterServiceBean() {
        callStoredProc_For_Level("FIN.PROC_GET_CC_TYPE_NM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                 new Object[] { DOC_ID, SLOC_ID, CLD_ID, ORG_ID, HO_ORG_ID });

    }


    private RichPopup tvoupopup;
    private RichTable table;
    private String mode = "V";
    private RichPopup tvoudeletePopup;
    
    private String level1 = "";
    private String level2 = "";
    private String level3 = "";
    private String level4 = "";
    private String level5 = "";
    private String level1_man = "N";
    private String level2_man = "N";
    private String level3_man = "N";
    private String level4_man = "N";
    private String level5_man = "N";
    
    private String ProfileName = "";
    private Integer countval = 0;
    private String P_DOC_NAME = "";
    private Integer DOC_ID = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.DOC_ID}"));
    private Integer SLOC_ID = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    private String CLD_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
    private String HO_ORG_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));
    private String ORG_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_USR_ORG}"));
   // private String p_mode = (resolvElDCBindVar("#{pageFlowScope.PARAM_MODE}"));
    
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 
    
    
    private AppCostCenterServiceAMImpl getAm(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.AppCostCenterServiceAMDataControl.dataProvider}", Object.class);
        return (AppCostCenterServiceAMImpl)valueExp.getValue(elContext);
        
    }
    public String Create() {
        Integer SlNo = 0;
        String AmtType = "E";
        BigDecimal amt = new BigDecimal(0);
        
        ViewObjectImpl v = getAm().getTempCostCenter();


        String Src = (resolvElDCBindVar("#{pageFlowScope.TVOU_SRC}"));
        if (Src.equalsIgnoreCase("H")) {
            SlNo = 0;
            AmtType = "H";

        } else {
            SlNo = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.TVOU_SL_NO}"));
            int count = v.getRowCount();
            if (count > 0) {
                RowSetIterator rsi = v.createRowSetIterator(null);
                Row rw = rsi.first();
                if (rw != null) {
                    if (rw.getAttribute("TempAmountType") != null) {
                        AmtType = rw.getAttribute("TempAmountType").toString();

                    }
                }
                rsi.closeRowSetIterator();
            } else {
                AmtType = "E";
            }
        }
        Integer maxSlNo = getMaxSlNo();

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();
        Row currrw = v.getCurrentRow();


        currrw.setAttribute("TempCcSlNo", maxSlNo);
        currrw.setAttribute("TempSlNo", SlNo);
        currrw.setAttribute("TempAmountType", AmtType);
        currrw.setAttribute("TempCcAmount", amt);
        showPopup(tvoupopup, true);
        mode = "A";
        return null;
    }

    public String Delete() {

        showPopup(tvoudeletePopup, true);
        mode = "D";
        return null;
    }

    public String Edit() {
        showPopup(tvoupopup, true);
        mode = "E";
        return null;
    }

    public void setTvoupopup(RichPopup tvoupopup) {
        this.tvoupopup = tvoupopup;
    }

    public RichPopup getTvoupopup() {
        return tvoupopup;
    }

    public void tvouPopupCancelListner(PopupCanceledEvent pce) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
        operationBindingexe.execute();
        tvoudeletePopup.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);

    }
    
    // For Manual Check of blank entries------------
    String check="null";
    public String chekBlank(){
         check="docommit";
        ViewObjectImpl v = getAm().getTempCostCenter();
        Row curr = v.getCurrentRow();
        if(curr.getAttribute("TempCcidLvl1")==null&& curr.getAttribute("TempCcidLvl2")==null&&
           curr.getAttribute("TempCcidLvl3")==null&&curr.getAttribute("TempCcidLvl4")==null&&curr.getAttribute("TempCcidLvl5")==null){
               check="dontcommit";
               System.out.println("all null--"+check);
               return check;
           }
        System.out.println("not all null--"+check);
            return check;
    }

    public void tvouDialogListner(DialogEvent de) {
        if (de.getOutcome().name().equals("ok")) {
           
            ViewObjectImpl v = getAm().getTempCostCenter();
            Row curr = v.getCurrentRow();
            int count = v.getRowCount();
            String Src = (resolvElDCBindVar("#{pageFlowScope.TVOU_SRC}"));
            BigDecimal Amount = new BigDecimal(0);

            if (Src.equals("H")) {
                chekBlank();
                if(check.equalsIgnoreCase("docommit")){
                RowSetIterator rsi = v.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row rw = rsi.next();
                    rw.setAttribute("TempAmountType", "H");
                    rw.setAttribute("TempCcAmount", new BigDecimal(0));
                }
                rsi.closeRowSetIterator();
                System.out.println("level name in H  "+level1);
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                if (operationBinding.getErrors().isEmpty()) {
                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                    operationBindingexe.execute();
                    mode = "V";
                    tvoupopup.hide();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                    //String msg1 = "Record saved successfully.";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {

                    OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                    operationBindingrollback.execute();

                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                    operationBindingexe.execute();
                    mode = "V";
                    tvoupopup.hide();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                    String msg1 = "Something went wrong.Contact Ess.";
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            }
                else{
                   // String msg1 = "A selection is required!";
                   String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
        }

            else if (Src.equals("L")) {
                chekBlank();
                if(check.equalsIgnoreCase("docommit")){
                Amount = new BigDecimal(resolvElDCBindVar("#{pageFlowScope.AMOUNT_VALUE}"));
                if (curr.getAttribute("TempAmountType") != null) {
                    String amtType = curr.getAttribute("TempAmountType").toString();
                    if (amtType.equals("E")) {
                        //BigDecimal equalval =Amount.divide(new BigDecimal(count),new MathContext(2));
                        Double db = Amount.doubleValue() / count;

                        DecimalFormat df = new DecimalFormat(".000000");
                        String outStr = df.format(db);
                        BigDecimal equalval = new BigDecimal(outStr);

                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();
                            rw.setAttribute("TempAmountType", amtType);
                            rw.setAttribute("TempCcAmount", equalval);
                        }
                        rsi.closeRowSetIterator();
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        if (operationBinding.getErrors().isEmpty()) {


                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            //String msg1 = "Record saved successfully.";
                            String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                        } else {
                            OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                            operationBindingrollback.execute();

                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            String msg1 = "Something went wrong.Contact Ess.";
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }

                    }

                    else if (amtType.equals("N")) {
                        BigDecimal sum = new BigDecimal(0);
                        BigDecimal amountTot = Amount;
                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();
                            rw.setAttribute("TempAmountType", amtType);
                            sum = sum.add(new BigDecimal(rw.getAttribute("TempCcAmount").toString()));

                        }
                        rsi.closeRowSetIterator();
                        DecimalFormat df = new DecimalFormat(".00");
                        String outStr = df.format(sum);
                        String totAmt = df.format(amountTot);
                        sum = new BigDecimal(outStr);
                        amountTot = new BigDecimal(totAmt);
                        if (sum.compareTo(amountTot) == 1) {
                           // String msg1 =
                              //  "Total Amount ( " + sum.toString() + " ) should be less than or equal to " + Amount.toString();
                            String msg1 =
                               resolvElDCMsg("#{bundle['LBL.1036']}").toString()+ "( " + sum.toString() + " )"+resolvElDCMsg("#{bundle['MSG.1182']}").toString() + Amount.toString();
                            
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            //  throw new ValidatorException(message);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else if (sum.compareTo(amountTot) == -1) {
                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();

                            if (operationBinding.getErrors().isEmpty()) {
                                OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                operationBindingexe.execute();
                                mode = "V";
                                tvoupopup.hide();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                               // String msg1 ="Record saved successfully. Total Amount( " + sum.toString() + " ) should equal to " + Amount.toString();
                                String msg1 =resolvElDCMsg("#{bundle['MSG.91']}").toString()+ resolvElDCMsg("#{bundle['LBL.1036']}").toString()+"( " + sum.toString() + " )"+ resolvElDCMsg("#{bundle['MSG.1183']}").toString() + Amount.toString();
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_WARN);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else {
                                OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                                operationBindingrollback.execute();

                                OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                operationBindingexe.execute();
                                mode = "V";
                                tvoupopup.hide();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                String msg1 = "Something went wrong.Contact Ess.";
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }


                        } else {
                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();

                            if (operationBinding.getErrors().isEmpty()) {
                                OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                operationBindingexe.execute();
                                mode = "V";
                                tvoupopup.hide();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                               // String msg1 = "Record saved successfully.";
                               String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else {
                                OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                                operationBindingrollback.execute();

                                OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                operationBindingexe.execute();
                                mode = "V";
                                tvoupopup.hide();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                String msg1 = "Something went wrong.Contact Ess.";
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }


                        }
                    }
                    //
                }

            }
                else{
           // String msg1 = "A selection is required!";
           String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        }

        else if (de.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
            operationBindingexe.execute();
            mode = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        }


        else if (de.getOutcome().name().equals("yes")) {


            BindingContainer bindings = getBindings();
            OperationBinding operationBindingdel = bindings.getOperationBinding("Delete");
            operationBindingdel.execute();

            if (operationBindingdel.getErrors().isEmpty()) {
               
                ViewObjectImpl v = getAm().getTempCostCenter();

                String Src = (resolvElDCBindVar("#{pageFlowScope.TVOU_SRC}"));
                BigDecimal Amount = new BigDecimal(0);

                if (Src.equals("H")) {
                    RowSetIterator rsi = v.createRowSetIterator(null);
                    while (rsi.hasNext()) {
                        Row rw = rsi.next();
                        rw.setAttribute("TempAmountType", "H");
                        rw.setAttribute("TempCcAmount", new BigDecimal(0));
                    }
                    rsi.closeRowSetIterator();

                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();


                    if (operationBinding.getErrors().isEmpty()) {
                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                        operationBindingexe.execute();
                        mode = "V";
                        tvoupopup.hide();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                        //String msg1 = "Record deleted successfully.";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                        operationBindingrollback.execute();

                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                        operationBindingexe.execute();
                        mode = "V";
                        tvoupopup.hide();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                        String msg1 = "Something went wrong.Contact Ess.";
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }


                } else if (Src.equals("L")) {

                    Amount = new BigDecimal(resolvElDCBindVar("#{pageFlowScope.AMOUNT_VALUE}"));
                    int count = v.getRowCount();

                    if (count > 0) {
                        RowSetIterator rsi = v.createRowSetIterator(null);

                        Row curr = rsi.first();
                        if (curr.getAttribute("TempAmountType") != null) {
                            String amtType = curr.getAttribute("TempAmountType").toString();
                            if (amtType.equals("E")) {
                                //BigDecimal equalval =Amount.divide(new BigDecimal(count),new MathContext(2));

                                Double db = Amount.doubleValue() / count;

                                DecimalFormat df = new DecimalFormat(".000000");
                                String outStr = df.format(db);
                                BigDecimal equalval = new BigDecimal(outStr);

                                RowSetIterator rsiEN = v.createRowSetIterator(null);

                                while (rsiEN.hasNext()) {
                                    Row rw = rsiEN.next();

                                    rw.setAttribute("TempCcAmount", equalval);
                                }

                                rsiEN.closeRowSetIterator();
                                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                operationBinding.execute();

                                if (operationBinding.getErrors().isEmpty()) {
                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();
                                    mode = "V";
                                    tvoupopup.hide();
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                   // String msg1 = "Record deleted successfully.";
                                   String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else {
                                    OperationBinding operationBindingrollback =
                                        bindings.getOperationBinding("Rollback");
                                    operationBindingrollback.execute();

                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();
                                    mode = "V";
                                    tvoupopup.hide();
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                    String msg1 = "Something went wrong.Contact Ess.";
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }


                            }

                            else if (amtType.equals("N")) {
                                BigDecimal sum = new BigDecimal(0);
                                BigDecimal amountTot = Amount;
                                RowSetIterator rsiEN = v.createRowSetIterator(null);
                                while (rsiEN.hasNext()) {
                                    Row rw = rsiEN.next();

                                    sum = sum.add(new BigDecimal(rw.getAttribute("TempCcAmount").toString()));

                                }
                                rsiEN.closeRowSetIterator();
                                DecimalFormat df = new DecimalFormat(".00");
                                String outStr = df.format(sum);
                                String totAmt = df.format(amountTot);
                                sum = new BigDecimal(outStr);
                                amountTot = new BigDecimal(totAmt);
                                if (sum.compareTo(amountTot) == 1) {
                                    //String msg1 =
                                    //    "Total Amount( " + sum.toString() + " )should be less equal to " + Amount.toString();
                                    
                                    String msg1 =
                                    resolvElDCMsg("#{bundle['LBL.1036']}").toString()+ "( " + sum.toString() + " )"+resolvElDCMsg("#{bundle['MSG.1182']}").toString() + Amount.toString();
                                    
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);                                    
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else if (sum.compareTo(amountTot) == -1) {
                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();

                                    if (operationBinding.getErrors().isEmpty()) {
                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                      //  String msg1 =
                                         //   "Record deleted successfully. Total Amount( " + sum.toString() + " )should equal to " + Amount.toString();
                                        
                                        String msg1 =
                                           resolvElDCMsg("#{bundle['MSG.142']}").toString()+resolvElDCMsg("#{bundle['LBL.1036']}").toString()+ "( " + sum.toString() + " )"+resolvElDCMsg("#{bundle['MSG.1183']}").toString() + Amount.toString();
                                        
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                    else{
                                        OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                                        operationBindingrollback.execute();

                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        String msg1 = "Something went wrong.Contact Ess.";
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message); 
                                    }


                                    
                                    

                                } else {

                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();

                                    if (operationBinding.getErrors().isEmpty()) {
                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                       // String msg1 = "Record deleted successfully.";
                                       String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                    else{
                                        OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                                        operationBindingrollback.execute();

                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        String msg1 = "Something went wrong.Contact Ess.";
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message); 
                                    }

                                    

                                }

                            }
                            //
                        }
                        rsi.closeRowSetIterator();

                    }
                }
            }
            mode = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        } else if (de.getOutcome().name().equals("no")) {
            /* BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
            operationBindingexe.execute();
            mode = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(table); */
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(table);

    }

    private Integer getMaxSlNo() {
        
        ViewObjectImpl v = getAm().getTempCostCenter();
        int count = v.getRowCount();

        Integer maxNumber = 0;
        Integer val = 0;
        if (count > 0) {


            RowSetIterator rsi = v.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                val = (Integer)rw.getAttribute("TempCcSlNo");

                if (val > maxNumber) {

                    maxNumber = val;
                }
            }

            rsi.closeRowSetIterator();
        }

        return maxNumber + 1;
    }

    public String setToDocCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                             String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID, Integer P_TEMP_SL_NO,
                             Integer P_TEMP_CC_SL_NO, String P_MODE) {

        String ret = null;
        ret =
(String)callStoredFunction(VARCHAR, "FIN.fn_ins_cost_center(?,?,?,?,?,?,?,?,?)", new Object[] { P_TEMP_SLOC_ID,
                                                                                                P_TEMP_CLD_ID,
                                                                                                P_TEMP_HO_ORG_ID,
                                                                                                P_TEMP_ORG_ID,
                                                                                                P_TEMP_DOC_ID,
                                                                                                P_TEMP_ID,
                                                                                                P_TEMP_SL_NO,
                                                                                                P_TEMP_CC_SL_NO,
                                                                                                P_MODE });

        return ret;
    }

    private static int VARCHAR = Types.VARCHAR;
    private static int NUMBER = Types.NUMERIC;


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            // 1. Create a JDBC CallabledStatement
            
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }

    protected void callStoredProc_For_Level(String stmt, Object[] bindVars) {

        CallableStatement st = null;

        try {
            /** 1. Create a JDBC CallabledStatement */
            
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */


            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);


            st.registerOutParameter(6, VARCHAR);
            st.registerOutParameter(7, VARCHAR);
            st.registerOutParameter(8, VARCHAR);
            st.registerOutParameter(9, VARCHAR);
            st.registerOutParameter(10, VARCHAR);
            st.registerOutParameter(11, VARCHAR);
            st.registerOutParameter(12, VARCHAR);
            st.registerOutParameter(13, VARCHAR);

            st.registerOutParameter(14, VARCHAR);
            st.registerOutParameter(15, VARCHAR);
            st.registerOutParameter(16, VARCHAR);
            st.registerOutParameter(17, VARCHAR);
            st.registerOutParameter(18, NUMBER);
            /** 5. Set the value of user-supplied bind vars in the stmt */

            st.executeUpdate();

            try {


                if (st.getObject(6) != null) {

                    setLevel1(st.getObject(6).toString());

                }


                if (st.getObject(7) != null) {

                    setLevel2(st.getObject(7).toString());

                }

                if (st.getObject(8) != null) {

                    setLevel3(st.getObject(8).toString());

                }

                if (st.getObject(9) != null) {

                    setLevel4(st.getObject(9).toString());

                }

                if (st.getObject(10) != null) {

                    setLevel5(st.getObject(10).toString());

                }

                if (st.getObject(11) != null) {

                    level1_man=(st.getObject(11).toString());

                }

                if (st.getObject(12) != null) {

                    level2_man=(st.getObject(12).toString());

                }

                if (st.getObject(13) != null) {

                    level3_man=(st.getObject(13).toString());

                }

                if (st.getObject(14) != null) {

                    level4_man=(st.getObject(14).toString());

                }

                if (st.getObject(15) != null) {

                    level5_man=(st.getObject(15).toString());

                }

                if (st.getObject(16) != null) {


                    setProfileName(st.getObject(16).toString());

                    ProfileName = ProfileName + " Cost Center";
                }
                if (st.getObject(17) != null) {

                    setP_DOC_NAME(st.getObject(17).toString());
                    P_DOC_NAME = P_DOC_NAME + " Cost Center";


                }
                if (st.getObject(18) != null) {

                    java.math.BigDecimal tempval = (BigDecimal)st.getObject(18);

                    setCountval(tempval.intValue());


                }


            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                throw new JboException(e);

            }

        } catch (SQLException e) {

            throw new JboException(e);

        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);

                }
            }
        }


    }

    public String resolvElDCBindVar(String data) {
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

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTvoudeletePopup(RichPopup tvoudeletePopup) {
        this.tvoudeletePopup = tvoudeletePopup;
    }

    public RichPopup getTvoudeletePopup() {
        return tvoudeletePopup;
    }


    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getLevel4() {
        return level4;
    }

    public void setLevel5(String level5) {
        this.level5 = level5;
    }

    public String getLevel5() {
        return level5;
    }

    public void setCountval(Integer countval) {
        this.countval = countval;
    }

    public Integer getCountval() {
        return countval;
    }


    public void setProfileName(String ProfileName) {
        this.ProfileName = ProfileName;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setP_DOC_NAME(String P_DOC_NAME) {
        this.P_DOC_NAME = P_DOC_NAME;
    }

    public String getP_DOC_NAME() {
        return P_DOC_NAME;
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public void setLevel1_man(String level1_man) {
        this.level1_man = level1_man;
    }

    public String getLevel1_man() {
        return level1_man;
    }

    public void setLevel2_man(String level2_man) {
        this.level2_man = level2_man;
    }

    public String getLevel2_man() {
        return level2_man;
    }

    public void setLevel3_man(String level3_man) {
        this.level3_man = level3_man;
    }

    public String getLevel3_man() {
        return level3_man;
    }

    public void setLevel4_man(String level4_man) {
        this.level4_man = level4_man;
    }

    public String getLevel4_man() {
        return level4_man;
    }

    public void setLevel5_man(String level5_man) {
        this.level5_man = level5_man;
    }

    public String getLevel5_man() {
        return level5_man;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void AmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            BigDecimal val=(BigDecimal)object;
            if(val.compareTo(new BigDecimal(0))==-1){
               // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Amount must be greater than equal to Zero.",null)); 
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.1184']}").toString(),null)); 
            }
            
        }
    }
}
