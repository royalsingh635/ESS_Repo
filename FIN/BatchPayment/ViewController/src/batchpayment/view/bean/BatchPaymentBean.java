package batchpayment.view.bean;

import batchpayment.model.module.BatchpayAMImpl;
import batchpayment.model.views.ApPayDtlVOImpl;
import batchpayment.model.views.ApPaySumVOImpl;
import batchpayment.model.views.ApPaySumVORowImpl;
import batchpayment.model.views.ApPayVOImpl;
import batchpayment.model.views.ApPayVORowImpl;
import batchpayment.model.views.FinApPayViewVOImpl;
import batchpayment.model.views.FinApPayViewVORowImpl;
import batchpayment.model.views.SystemDateVORowImpl;

import java.io.Serializable;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class BatchPaymentBean implements Serializable {
    
    Number zero=new Number(0);
    String mode = "edit";
    private RichPopup generatePopup;
    String osdays1 = null;
    String osdays2 = null;
    String osdays3 = null;
    String osdays4 = null;
    private Integer billDetails = 0;
    private RichInputText adjAmt;
    Number totalAmt = new Number(0);
    private RichInputText totalAmtAdjust;
    Number osamt1 = new Number(0);
    Number osamt2 = new Number(0);
    Number osamt3 = new Number(0);
    Number osamt4 = new Number(0);
    //amandeep
    

    Number adjamt1 = new Number(0);
    Number adjamt2 = new Number(0);
    Number adjamt3 = new Number(0);
    Number adjamt4 = new Number(0);

    Number totalAdjustedAmt = new Number(0);
    Number diff = new Number(0);
    boolean flag = false;
    Number totalOsAmt = new Number(0);

    Number TransAdjAmt = new Number(0);
    Number NewTotalAmount = new Number(0);
    Number TotalInstAmount = new Number(0);
    Number TotalEXFAmount = new Number(0);

    Number TransNewTotalAmount = new Number(0);
    Number TransTotalInstAmount = new Number(0);
    Number TransTotalEXFAmount = new Number(0);

    private static int NUMBER = Types.NUMERIC;
    private RichColumn tdsAmtsp;
    private RichInputText instAmtBind;
    private RichPopup parameterPopup;
    private RichTable suntabOnPayPage;
    Number sumInstAmt = new Number(0);
    private RichInputText totalInstAmt;
    Number testAmount = new Number(0);
    private RichPopup validatePopup;
    private RichPopup validateForProcess;
    String processDisb = "N";
    private RichCommandLink processLink;
    private RichCommandButton generateButtonBind;

    private final Integer inst_id = 1;

    private RichPopup generateNewBatchPopUp;
    private RichPopup newPopBinForBatch;
    private RichCommandImageLink newBacthAddLinkBind;
    private RichSelectOneChoice newPageBatchIdBind;
    private RichTable newBatchPaymentTableBind;
    private RichInputDate apPayPagePopDateBind;
    private RichInputListOfValues coaNameBind;

    private Number OsOrgAmt1 = new Number(0);
    private Number OsOrgAmt2 = new Number(0);
    private Number OsOrgAmt3 = new Number(0);
    private Number OsOrgAmt4 = new Number(0);

    private RichOutputText specificAmount;
    private RichTable apPayDtlBind;
    private RichSelectBooleanCheckbox checkBind;
    private RichCommandImageLink selectAllBind;
    private RichCommandImageLink deSelectAllBind;
    private RichTable apPaysumPageTableBind;
    private RichInputText transAdjAmtBind;
    private RichSelectBooleanCheckbox popCheckBind;
    private RichInputText diffBind;
    private RichTable apPayTableBind;

    Number totalSumAmtAdj = new Number(0);
    Number totalTdsAmt = new Number(0);

    boolean link1 = false;
    boolean link2 = false;
    boolean link3 = false;
    boolean link4 = false;
    String state = "edit";
    String saveState = "view";

    String linkState1 = "edit";
    String linkState2 = "edit";
    String linkState3 = "edit";
    String linkState4 = "edit";
    Date currDate = (Date)Date.getCurrentDate();
    private RichOutputText warningBind;
    private boolean isSaved = false;
    private RichCommandButton forwardbuttonBind;
    private RichInputListOfValues batchIdBind;
    private RichCommandImageLink resetButtonBind;
    private RichCommandImageLink saveButtonBind;
    private RichSelectOneChoice currIdBind;
    private RichOutputText txnId;
    private Boolean isInWf=true;
    
    private Integer userId=null;
    private Integer slocId=null;
    private String orgId=null;
    private String hoOrgId=null;
    private String cldId=null;
    private RichPopup tdsPopUp;
    private RichInputText totAdjNew;
    private RichInputText totPayAmt;
    private RichInputText totFluAmt;

    public BatchPaymentBean() {

    }
    
    
    
    // Methods to get global Parameters
    
    public Integer getCurrentUser(){
        if(userId==null)
            userId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
         
         return  userId;
    }

    public Integer getCurrentServerLoc(){
          if(slocId==null)
              slocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        return slocId;
    }
    
    public String getCurrentCld(){
        if(cldId==null)
            cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        return cldId;
    }
    public String getCurrentOrg(){
        if(orgId==null)
            orgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        return orgId;
    }
    
    public String getCurrentHO(){
        if(hoOrgId==null)
            hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        return hoOrgId;
    }
    


    public void genarateNewBatch(ActionEvent actionEvent) {

        System.out.println("\n--------Came in generate New Batch-----------\n");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();

        /* Integer inst_id = 1; */
        /* Integer count = Integer.parseInt(callStoredFunction(NUMBER, "FIN.pkg_fin_gl.chk_ap_row(?,?,?)",
                            new Object[] {sloc_id , org_id , cld_id}).toString());  */
        // System.out.println("values in create function:"+cld_id, sloc_id ,ho_org_id, org_id,inst_id  );
        Integer count =
            Integer.parseInt(callStoredFunction(NUMBER, "FIN.fn_chk_ap_row(?,?,?,?,?)", new Object[] { cld_id, sloc_id,
                                                                                                       ho_org_id,
                                                                                                       org_id,
                                                                                                       inst_id }).toString());

        if (count == 0) {
            System.out.println("\n------Came in If Statement--------\n");
            String msg2 = "No Outstading amount yet.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            showPopup(generatePopup, true);
            System.out.println("\n------Came in else--------\n");
            //showPopup(newPopBinForBatch, true);
        }
    }


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }

    public void setGeneratePopup(RichPopup generatePopup) {
        this.generatePopup = generatePopup;
    }

    public RichPopup getGeneratePopup() {
        return generatePopup;
    }

    public void generatePopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
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


    public void generateDialogList(DialogEvent dialogEvent) {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getDummy();
        Row row = v.getCurrentRow();
        String org = row.getAttribute("Tran_org_id").toString();
        Integer days1 = Integer.parseInt(row.getAttribute("Param1").toString());
        Integer days2 = Integer.parseInt(row.getAttribute("Param2").toString());
        Integer days3 = Integer.parseInt(row.getAttribute("Param3").toString());
        Integer days4 = Integer.parseInt(row.getAttribute("Param4").toString());
        if (days1 < 0) {

            String message = resolvElDCMsg("#{bundle['MSG.1109']}").toString();
            //  String msg2 = "OS days must be greater than zero.";
            String msg2 = message;
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);


        }
        if (days1 >= days2 || days2 >= days3 || days3 >= days4) {


            String message = resolvElDCMsg("#{bundle['MSG.1108']}").toString();

            //  String msg2 = "OS days should be in increasing order";
            String msg2 = message;
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);


        } else {


            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("generate");
            operationBinding.execute();
            System.out.println("generate");


            BindingContainer bindingEXE = getBindings();
            OperationBinding operationBindingexe = bindingEXE.getOperationBinding("Execute1");
            operationBindingexe.execute();

            try {
                OperationBinding operationBindingxx = bindings.getOperationBinding("refreshBatchid");
                operationBindingxx.execute();
            } catch (Exception e) {
                System.out.println("in catch block");
            }

            operationBindingexe.execute();

            System.out.println("execute");
            System.out.println(usr_id + "---" + org_id + "----" + sloc_id + "---" + org + "---" + osdays1 + "---" +
                               osdays2 + "----" + osdays3 + "---" + osdays4);

            AdfFacesContext.getCurrentInstance().addPartialTarget(generateButtonBind);
            //   AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);

        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public String goToPayLink() {

        String result = "N";
        BindingContainer binding = getBindings();
       
        OperationBinding operationBindingVarify = binding.getOperationBinding("varifyUser");
        operationBindingVarify.getParamsMap().put("mode", "E");
        operationBindingVarify.execute();
        result = (String)operationBindingVarify.getResult();
        System.out.println("reult is" + result);


        if (!result.equals("Y")) {
            String msg1;
            String reason = result.substring(0, 1);
            String user = result.substring(1, result.length());

            if (reason.equals("P")) {
                reason = "Pending At ";
                msg1 = "Payment is pending for Approval.You Cant go to Pay";
            } else {
                reason = "Created By ";
                msg1 = "Payment is created by other user.You Cant go to Pay";

            }

            StringBuilder msg = new StringBuilder("<html>");
            msg.append("<body>");
            msg.append(msg1);
            msg.append("<br>");
            msg.append("<br>");

            msg.append(reason);
            msg.append(": ");
            msg.append(user);
            msg.append("</body>");
            msg.append("</html>");
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.toString(),
                                                                          null));
            return null;
        } else {
            checkMode();
            getDaysList();
            isSaved = false;
            return "pay";
        }
    }

    public void checkMode() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        String str = "edit";
        if (am.getFinApPayView().getCurrentRow() != null) {
            FinApPayViewVORowImpl fin = (FinApPayViewVORowImpl)am.getFinApPayView().getCurrentRow();
            Integer no = fin.getApPayStat();

            if (no == 460)
                str = "pend";
            else if (no == 193)
                str = "edit";
        }
        setMode(str);
    }

    public void getDaysList() {
        
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        
        ViewObjectImpl v = am.getFinApPayView();
        FinApPayViewVORowImpl r1 = (FinApPayViewVORowImpl)v.getCurrentRow();
        String payId = r1.getApPayId();
        Integer instId = r1.getApApplInstId();

        ViewObjectImpl payVO = am.getApPay1();
        Row[] row = payVO.getFilteredRows("ApPayId", payId);
        payVO.executeQuery();
                                        

        if (row.length > 0) {
            System.out.println(row[0].getAttribute("ApParam1").toString() + "----------");
            String param1 = row[0].getAttribute("ApParam1").toString();
            String param2 = row[0].getAttribute("ApParam2").toString();
            String param3 = row[0].getAttribute("ApParam3").toString();
            String param4 = row[0].getAttribute("ApParam4").toString();

            System.out.println(payId + "---------------payId");


            String message = resolvElDCMsg("#{bundle['MSG.1149']}").toString();

            osdays1 = param1.concat(message);
            osdays2 = param2.concat(message);
            osdays3 = param3.concat(message);
            osdays4 = param4.concat(message);

            String osam1 = row[0].getAttribute("ApSumOrgAmtOs1").toString();
            if (row[0].getAttribute("ApSumOrgAmtOs1") != null)
                OsOrgAmt1 = (Number)row[0].getAttribute("ApSumOrgAmtOs1");

            String osam2 = row[0].getAttribute("ApSumOrgAmtOs2").toString();
            if (row[0].getAttribute("ApSumOrgAmtOs2") != null)
                OsOrgAmt2 = (Number)row[0].getAttribute("ApSumOrgAmtOs2");

            String osam3 = row[0].getAttribute("ApSumOrgAmtOs3").toString();
            if (row[0].getAttribute("ApSumOrgAmtOs3") != null)
                OsOrgAmt3 = (Number)row[0].getAttribute("ApSumOrgAmtOs3");

            String osam4 = row[0].getAttribute("ApSumOrgAmtOs4").toString();
            if (row[0].getAttribute("ApSumOrgAmtOs4") != null)
                OsOrgAmt4 = (Number)row[0].getAttribute("ApSumOrgAmtOs4");

            System.out.println(osdays1 + "----" + osdays2 + "----" + osdays3 + "------" + osdays4);
            System.out.println("Os amount: " + OsOrgAmt1 + "----" + OsOrgAmt2 + "---" + OsOrgAmt3 + "----" +
                               OsOrgAmt4);
        }
    }

    public void setOsdays1(String osdays1) {
        this.osdays1 = osdays1;
    }

    public String getOsdays1() {
        return osdays1;
    }

    public void setOsdays2(String osdays2) {
        this.osdays2 = osdays2;
    }

    public String getOsdays2() {
        return osdays2;
    }

    public void setOsdays3(String osdays3) {
        this.osdays3 = osdays3;
    }

    public String getOsdays3() {
        return osdays3;
    }

    public void setOsdays4(String osdays4) {
        this.osdays4 = osdays4;
    }

    public String getOsdays4() {
        return osdays4;
    }

    public String billDetailLink1() {
        billDetails = 1;
        filterCOALOV(1);
        System.out.println("---------------------1");
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl payVO = am.getApPay1();
        ApPayVORowImpl r1 = (ApPayVORowImpl)payVO.getCurrentRow();
        Integer i = r1.getApParam1();
        System.out.println("---------------------2");
        ViewObjectImpl v = am.getApPayDtl1();
        v.setWhereClause("trunc(sysdate) - AP_VOU_DT between 0 and " + i);
        v.executeQuery();

        System.out.println("---------------------3");

        ViewObjectImpl paysumVo = am.getApPaySum1();
        paysumVo.setWhereClause(null);
        System.out.println("---------------------4");
        try {
            paysumVo.executeQuery();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        System.out.println("---------------------5");
        paysumVo.setWhereClause("ap_pay_sum_amt1 != 0");
        paysumVo.executeQuery();


        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();

        filterDetail2();

        return "paysum";
    }

    public void filterCOALOV(Integer val) {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl a = am.getSearchCOADual1();
        switch (val) {
        case 1:
            a.setNamedWhereClauseParam("Amt1bind1", new Number(0));
            a.setNamedWhereClauseParam("Amt2bind2", new Number(-1));
            a.setNamedWhereClauseParam("Amt3bind3", new Number(-1));
            a.setNamedWhereClauseParam("Amt4bind4", new Number(-1));
            break;

        case 2:
            a.setNamedWhereClauseParam("Amt1bind1", new Number(-1));
            a.setNamedWhereClauseParam("Amt2bind2", new Number(0));
            a.setNamedWhereClauseParam("Amt3bind3", new Number(-1));
            a.setNamedWhereClauseParam("Amt4bind4", new Number(-1));
            break;

        case 3:
            a.setNamedWhereClauseParam("Amt1bind1", new Number(-1));
            a.setNamedWhereClauseParam("Amt2bind2", new Number(-1));
            a.setNamedWhereClauseParam("Amt3bind3", new Number(0));
            a.setNamedWhereClauseParam("Amt4bind4", new Number(-1));
            break;

        case 4:
            a.setNamedWhereClauseParam("Amt1bind1", new Number(-1));
            a.setNamedWhereClauseParam("Amt2bind2", new Number(-1));
            a.setNamedWhereClauseParam("Amt3bind3", new Number(-1));
            a.setNamedWhereClauseParam("Amt4bind4", new Number(0));
            break;
        }
        a.executeQuery();
    }

    public String billDetailLink2() {
        billDetails = 2;
        filterCOALOV(2);
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl payVO = am.getApPay1();
        ApPayVORowImpl r1 = (ApPayVORowImpl)payVO.getCurrentRow();
        Integer i = r1.getApParam1() + 1;
        Integer j = r1.getApParam2();
        ViewObjectImpl v = am.getApPayDtl1();
        v.setWhereClause("trunc(sysdate) - AP_VOU_DT between " + i + "and " + j);
        v.executeQuery();
        filterDetail2();

        ViewObjectImpl paysumVo = am.getApPaySum1();
        paysumVo.setWhereClause(null);

        try {
            paysumVo.executeQuery();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        paysumVo.setWhereClause("ap_pay_sum_amt2 != 0");
        paysumVo.executeQuery();

        return "paysum";
    }

    public String billDetailLink3() {
        billDetails = 3;
        filterCOALOV(3);
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl payVO = am.getApPay1();
        ApPayVORowImpl r1 = (ApPayVORowImpl)payVO.getCurrentRow();
        if (r1 != null) {
            Integer i = r1.getApParam2() + 1;
            Integer j = r1.getApParam3();
            ViewObjectImpl v = am.getApPayDtl1();
            v.setWhereClause("trunc(sysdate) - AP_VOU_DT between " + i + "and " + j);
            v.executeQuery();
        }
        ViewObjectImpl paysumVo = am.getApPaySum1();
        paysumVo.setWhereClause(null);
        try {
            paysumVo.executeQuery();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        paysumVo.setWhereClause("ap_pay_sum_amt3 != 0");
        paysumVo.executeQuery();

        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        filterDetail2();

        return "paysum";
    }

    public String billDetailLink4() {
        billDetails = 4;
        filterCOALOV(4);
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl payVO = am.getApPay1();
        ApPayVORowImpl r1 = (ApPayVORowImpl)payVO.getCurrentRow();
        Integer i = r1.getApParam3() + 1;
        Integer j = r1.getApParam4();
        ViewObjectImpl v = am.getApPayDtl1();
        v.setWhereClause("trunc(sysdate) - AP_VOU_DT between " + i + "and " + j);
        v.executeQuery();

        ViewObjectImpl paysumVo = am.getApPaySum1();
        paysumVo.setWhereClause(null);
        try {
            paysumVo.executeQuery();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        paysumVo.setWhereClause("ap_pay_sum_amt4 != 0");
        paysumVo.executeQuery();


        filterDetail2();
        return "paysum";
    }


    public void setBillDetails(Integer billDetails) {
        this.billDetails = billDetails;
    }

    public Integer getBillDetails() {
        return billDetails;
    }

    public void apPayFlgValueChange(ValueChangeEvent valueChangeEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl payDtlVo = am.getApPayDtl1();
        //valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Row row = payDtlVo.getCurrentRow();
        boolean val = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        if (val) {
            Number apAmtSp = (Number)row.getAttribute("ApAmtSp");
            System.out.println(apAmtSp + "---------------amount----sp");
            row.setAttribute("ApAmtAdj", apAmtSp);

        } else if (!val) {
            row.setAttribute("ApAmtAdj", 0);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(adjAmt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtAdjust);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTotAdjNew());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTotPayAmt());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTotFluAmt());
    }

    public void setAdjAmt(RichInputText adjAmt) {
        this.adjAmt = adjAmt;
    }

    public RichInputText getAdjAmt() {
        return adjAmt;
    }

    public void setTotalAmt(Number totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Number getTotalAmt() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPayDtl1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApFlag").toString().equalsIgnoreCase("Y")) {
                //  Number amt = (Number)currentRow.getAttribute("ApAmtAdj");

                if (currentRow.getAttribute("ApAmtAdjBsOld") != null) {
                    Number amt = (Number)currentRow.getAttribute("ApAmtAdjBsOld");
                    n = n.add(amt);
                }

            }
        }
        rit.closeRowSetIterator();
        return n;
    }

    public void setTotalAmtAdjust(RichInputText totalAmtAdjust) {
        this.totalAmtAdjust = totalAmtAdjust;
    }

    public RichInputText getTotalAmtAdjust() {
        return totalAmtAdjust;
    }

    public void confirmOnDtl(ActionEvent actionEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPaySum1();
        ApPaySumVORowImpl r1 = (ApPaySumVORowImpl)v.getCurrentRow();

        if (billDetails == 1) {
            r1.setAttribute("ApPaySumAdj1", getTotalAmt());
        }
        if (billDetails == 2) {
            r1.setAttribute("ApPaySumAdj2", getTotalAmt());
        }
        if (billDetails == 3) {
            r1.setAttribute("ApPaySumAdj3", getTotalAmt());
        }
        if (billDetails == 4) {
            r1.setAttribute("ApPaySumAdj4", getTotalAmt());
        }


        BindingContainer bindingsave = getBindings();
        OperationBinding operationBindingsave = bindingsave.getOperationBinding("Commit");
        operationBindingsave.execute();

        BindingContainer bindingEXE = getBindings();
        OperationBinding operationBindingexe = bindingEXE.getOperationBinding("Execute");
        operationBindingexe.execute();

    }

    public void setOsamt1(Number osamt1) {
        this.osamt1 = osamt1;
    }

    public Number getOsamt1() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();

            Number amt = (Number)currentRow.getAttribute("ApPaySumAmt1");
            n = n.add(amt);

        }
        return n;

    }

    public void setOsamt2(Number osamt2) {
        this.osamt2 = osamt2;
    }

    public Number getOsamt2() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();

            Number amt = (Number)currentRow.getAttribute("ApPaySumAmt2");
            n = n.add(amt);

        }
        return n;

    }

    public void setOsamt3(Number osamt3) {
        this.osamt3 = osamt3;
    }

    public Number getOsamt3() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();

            Number amt = (Number)currentRow.getAttribute("ApPaySumAmt3");
            n = n.add(amt);

        }
        return n;
    }

    public void setOsamt4(Number osamt4) {
        this.osamt4 = osamt4;
    }

    public Number getOsamt4() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();

            Number amt = (Number)currentRow.getAttribute("ApPaySumAmt4");
            n = n.add(amt);

        }
        return n;
    }

    public void setAdjamt1(Number adjamt1) {
        this.adjamt1 = adjamt1;
    }

    public Number getAdjamt1() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApPaySumAdj1") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj1");
                n = n.add(amt);
            }
        }
        return n;

    }

    public void setAdjamt2(Number adjamt2) {
        this.adjamt2 = adjamt2;
    }

    public Number getAdjamt2() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApPaySumAdj2") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj2");
                n = n.add(amt);
            }

        }
        return n;
    }

    public void setAdjamt3(Number adjamt3) {
        this.adjamt3 = adjamt3;
    }

    public Number getAdjamt3() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApPaySumAdj3") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj3");
                n = n.add(amt);
            }
            /* Number amt = (Number)currentRow.getAttribute("ApPaySumAdj3");
              n = n.add(amt); */

        }
        return n;
    }

    public void setAdjamt4(Number adjamt4) {
        this.adjamt4 = adjamt4;
    }

    public Number getAdjamt4() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApPaySumAdj4") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj4");
                n = n.add(amt);
            }
        }
        return n;
    }

    public void confirmOnSumPage(ActionEvent actionEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSetIterator rit = v1.createRowSetIterator(null);
        while (rit.hasNext()) {
            Row row = rit.next();
            Number adj1 = (Number)row.getAttribute("ApPaySumAdj1");
            Number adj2 = (Number)row.getAttribute("ApPaySumAdj2");
            Number adj3 = (Number)row.getAttribute("ApPaySumAdj3");
            Number adj4 = (Number)row.getAttribute("ApPaySumAdj4");

            Number totajAdj = adj1.add(adj2).add(adj3).add(adj4);
            row.setAttribute("ApSumAmtAdj", totajAdj);
            row.setAttribute("ApPayInstrmntAmt", totajAdj);
        }
        rit.closeRowSetIterator();
        v1.executeQuery();
        getBindings().getOperationBinding("Commit").execute();
        getBindings().getOperationBinding("Execute").execute();
    }

    public String backOnApPaySum() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        v1.setWhereClause(null);
        v1.executeQuery();


        //   AdfFacesContext.getCurrentInstance().addPartialTarget(generateButtonBind);
        //   AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);
        return "back";
    }

    public void tdsRuleIdValueChangeList(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        getBindings().getOperationBinding("applyTDS").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tdsAmtsp);
        AdfFacesContext.getCurrentInstance().addPartialTarget(instAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalInstAmt);
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void setTdsAmtsp(RichColumn tdsAmtsp) {
        this.tdsAmtsp = tdsAmtsp;
    }

    public RichColumn getTdsAmtsp() {
        return tdsAmtsp;
    }

    public void setInstAmtBind(RichInputText instAmtBind) {
        this.instAmtBind = instAmtBind;
    }

    public RichInputText getInstAmtBind() {
        return instAmtBind;
    }

    public void setParameterPopup(RichPopup parameterPopup) {
        this.parameterPopup = parameterPopup;
    }

    public RichPopup getParameterPopup() {
        return parameterPopup;
    }

    public void paramPopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public String forwardBatch() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getParameter();
        if (v.getCurrentRow() == null)
            return null;

        Row row = v.getCurrentRow();

        Integer coa_id = Integer.parseInt(row.getAttribute("coa_id").toString());
        Integer inst_no = Integer.parseInt(row.getAttribute("inst_no").toString());
        Date inst_dt = (Date)row.getAttribute("inst_dt");

        ViewObjectImpl sumvo = am.getApPaySum1();
        RowSetIterator rit = sumvo.createRowSetIterator(null);
       
        am.setApPayId(); 
        //  This code update coa id and instrument detail in apPaySum table

        while (rit.hasNext()) {
            Row currrow = rit.next();
            if (currrow.getAttribute("ApSumAmtAdj") != null) {
                if (((Number)currrow.getAttribute("ApSumAmtAdj")).compareTo(Number.zero()) > 0) {

                    System.out.println(currrow.getAttribute("ApCoaId").toString() + "---------coaid");
                    currrow.setAttribute("ApPayInstrmntDt", inst_dt);
                    currrow.setAttribute("ApTmpSelBnkCoaId", coa_id);
                    currrow.setAttribute("ApPayInstrmntNo", inst_no);
                    inst_no++;
                    
                }
            }
        }
        rit.closeRowSetIterator();
        getBindings().getOperationBinding("Commit").execute();
        sumvo.executeQuery();
 
        AdfFacesContext.getCurrentInstance().addPartialTarget(suntabOnPayPage);
        String a = getProcessDisb();

        if (!(a.equalsIgnoreCase("Y"))) {

          // callProcessFunction();

        }
        
        parameterPopup.hide();
        return "Forward";
    }

    public void cancleBatch(ActionEvent actionEvent) {
        parameterPopup.hide();
    }

//    public void callProcessFunction() {
//
//        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_SERV_LOC}").toString());
//        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
//        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_USR}").toString());
//        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
//        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
//
//        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
//        ViewObjectImpl sum = am.getApPaySum1();
//        ViewObjectImpl impl = am.getParameter();
//
//        ParameterVORowImpl par = null;
//        if (impl.getCurrentRow() != null) {
//            Integer flag = saveBatch();
//            if (flag == 0) {
//                FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1115']}").toString());
//                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//                FacesContext.getCurrentInstance().addMessage(null, msg);
//                return;
//            }
//
//            par = (ParameterVORowImpl)impl.getCurrentRow();
//            Row row1 = sum.getCurrentRow();
//            String pay_id = row1.getAttribute("ApPayId").toString();
//            Date dt = (Date)row1.getAttribute("ApPayInstrmntDt");
//            System.out.println(pay_id + "---" + SlocId + "----" + orgId + "-----" + ho_org_id + "-----" + cld_id +
//                               "---" + usrId + "----" + dt);
//
//        }
//        // return 0;
//    }

    public void parameterButton(ActionEvent actionEvent) {

        Number totalInst = getSumInstAmt();
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPay1();
        Row currrow = v.getCurrentRow();
        Number budget = new Number(0);

        if (currrow.getAttribute("ApAmtBudget") != null) {
            budget = (Number)currrow.getAttribute("ApAmtBudget");
        }

        System.out.println(totalInst + "------" + budget);

        if (totalInst.compareTo(budget) > 0) {
            showPopup(validatePopup, true);

        } else {
            showPopup(parameterPopup, true);
            getBindings().getOperationBinding("setDate").execute();
        }


    }

    public void setSuntabOnPayPage(RichTable suntabOnPayPage) {
        this.suntabOnPayPage = suntabOnPayPage;
    }

    public RichTable getSuntabOnPayPage() {
        return suntabOnPayPage;
    }

  public void resetTdsLink(ActionEvent actionEvent) {
   
        OperationBinding op= this.getBindings().getOperationBinding("resetTDS"); 
        op.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tdsAmtsp);
        AdfFacesContext.getCurrentInstance().addPartialTarget(instAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalInstAmt);
         


    }

    public void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");

        try {
            st = am.getDBTransaction().createPreparedStatement("begin " + stmt + "; end;", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println("in procedure.....");
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void deleteOnfirstPage(ActionEvent actionEvent) {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getFinApPayView();
        Row row = v.getCurrentRow();
        Integer sloc_id = Integer.parseInt(row.getAttribute("ApSlocId").toString());
        String org_id = row.getAttribute("ApOrgId").toString();
        String pay_id = row.getAttribute("ApPayId").toString();

        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        // Integer inst_id = 1;
        /*  callStoredProcedure("FIN.pkg_fin.DEL_BATCH(?,?,?)",
                            new Object[] {sloc_id,org_id,pay_id}); */

        callStoredProcedure("FIN.proc_del_batch(?,?,?,?,?,?)",
                            new Object[] { cld_id, sloc_id, ho_org_id, org_id, inst_id, pay_id });

        BindingContainer bindingsave = getBindings();
        OperationBinding operationBindingsave = bindingsave.getOperationBinding("Commit");
        operationBindingsave.execute();
        System.out.println("save");

        BindingContainer bindingEXE = getBindings();
        OperationBinding operationBindingexe = bindingEXE.getOperationBinding("Execute1");
        operationBindingexe.execute();
        System.out.println("execute");


        AdfFacesContext.getCurrentInstance().addPartialTarget(generateButtonBind);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);

    }

    public void setSumInstAmt(Number sumInstAmt) {
        this.sumInstAmt = sumInstAmt;
    }

    public Number getSumInstAmt() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPaySum1();
        RowSetIterator rit = v.createRowSetIterator(null);
        Number tot = new Number(0);
        while (rit.hasNext()) {
            Row row = rit.next();
            if (row.getAttribute("ApPayInstrmntAmt") != null) {
                Number n = (Number)row.getAttribute("ApPayInstrmntAmt");

                tot = tot.add(n);
            }
        }
        return tot;
    }

    public void setTotalInstAmt(RichInputText totalInstAmt) {
        this.totalInstAmt = totalInstAmt;
    }

    public RichInputText getTotalInstAmt() {
        return totalInstAmt;
    }

    public void setTestAmount(Number testAmount) {
        this.testAmount = testAmount;
    }

    public Number getTestAmount() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("ApPaySum1Iterator");


        return testAmount;
    }

    public void setValidatePopup(RichPopup validatePopup) {
        this.validatePopup = validatePopup;
    }

    public RichPopup getValidatePopup() {
        return validatePopup;
    }

    public void validateDialogList(DialogEvent dialogEvent) {
        showPopup(parameterPopup, true);
    }

    public void setValidateForProcess(RichPopup validateForProcess) {
        this.validateForProcess = validateForProcess;
    }

    public RichPopup getValidateForProcess() {
        return validateForProcess;
    }

    public void validateForProcessDialogLIst(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
          //  callProcessFunction();
        }

    }

    public void refershButton(ActionEvent actionEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPay1();
        Row row = v.getCurrentRow();
        String pay_id = row.getAttribute("ApPayId").toString();
        Integer param1 = Integer.parseInt(row.getAttribute("ApParam1").toString());
        Integer param2 = Integer.parseInt(row.getAttribute("ApParam2").toString());
        Integer param3 = Integer.parseInt(row.getAttribute("ApParam3").toString());
        Integer param4 = Integer.parseInt(row.getAttribute("ApParam4").toString());

        Date dt = (Date)Date.getCurrentDate();

        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_SERV_LOC}").toString());
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_USR}").toString());
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();


        /* callStoredProcedure("FIN.pkg_fin_gl.PROC_REFRESH_BATCH(?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[] {pay_id, SlocId, 1,
                            orgId,ho_org_id,cld_id,usrId,
                            dt, param1, param2,param3,param4}); */

        callStoredProcedure("FIN.proc_refresh_batch(?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[] { cld_id, SlocId, ho_org_id, orgId, inst_id, pay_id, usrId, dt, param1,
                                           param2, param3, param4 });


        v.executeQuery();
    }

    public void setProcessDisb(String processDisb) {
        this.processDisb = processDisb;
    }

    public String getProcessDisb() {
        
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPaySum1();
        RowSetIterator rit = v.createRowSetIterator(null);
        String disb = "N";

        while (rit.hasNext()) {
            Row row = rit.next();
            //Integer adj = Integer.parseInt(row.getAttribute("ApSumAmtAdj").toString());
            Number adj = (Number)row.getAttribute("ApSumAmtAdj");
            Integer inst = Integer.parseInt(row.getAttribute("ApPayInstrmntNo").toString());
            
            if (adj.compareTo(new Number(0)) > 0 ) {
                disb = "Y";
                break;
            }
        }
        return disb;
    }

    public void setProcessLink(RichCommandLink processLink) {
        this.processLink = processLink;
    }

    public RichCommandLink getProcessLink() {
        return processLink;
    }

    public void setGenerateButtonBind(RichCommandButton generateButtonBind) {
        this.generateButtonBind = generateButtonBind;
    }

    public RichCommandButton getGenerateButtonBind() {
        return generateButtonBind;
    }

    public String backOkApPayPage() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");

        //   AdfFacesContext.getCurrentInstance().addPartialTarget(generateButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);
        return "back";
    }


    public void newBatchPageDelete(ActionEvent actionEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getFinApPayView();
        Row row = v.getCurrentRow();
        Integer sloc_id = Integer.parseInt(row.getAttribute("ApSlocId").toString());
        String org_id = row.getAttribute("ApOrgId").toString();
        String pay_id = row.getAttribute("ApPayId").toString();

        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        // Integer inst_id = 1;
        /*  callStoredProcedure("FIN.pkg_fin.DEL_BATCH(?,?,?)",
                    new Object[] {sloc_id,org_id,pay_id}); */
        BindingContainer binding = getBindings();


        OperationBinding operationBindingVarify = binding.getOperationBinding("varifyUser");
        operationBindingVarify.getParamsMap().put("mode", "D");
        operationBindingVarify.execute();
        String result = (String)operationBindingVarify.getResult();
        if (result.equalsIgnoreCase("Y")) {
            callStoredProcedure("FIN.proc_del_batch(?,?,?,?,?,?)",
                                new Object[] { cld_id, sloc_id, ho_org_id, org_id, inst_id, pay_id });


            OperationBinding operationBindingsave = binding.getOperationBinding("Commit");
            operationBindingsave.execute();
            System.out.println("save");


            OperationBinding operationBindingexe = binding.getOperationBinding("Execute1");
            operationBindingexe.execute();
            System.out.println("execute");

            //   AdfFacesContext.getCurrentInstance().addPartialTarget(generateButtonBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);

            /* Commeneted By MS. Will test later.*/
            //                    ViewObjectImpl dual1 = am.getDual1();
            //                    DualVORowImpl ipl = (DualVORowImpl)dual1.getCurrentRow();
            //                    ipl.getLovBatchIdVO1().executeQuery();
        } else {

            String msg1;
            String reason = result.substring(0, 1);
            String user = result.substring(1, result.length());

            if (reason.equals("P")) {
                reason = "Pending At ";
                msg1 = "Payment is pending for Approval.You Cant delete It";
            } else {
                reason = "Created By ";
                msg1 = "Payment is created by other user.You Cant delete It";

            }

            StringBuilder msg = new StringBuilder("<html>");
            msg.append("<body>");
            msg.append(msg1);
            msg.append("<br>");
            msg.append("<br>");

            msg.append(reason);
            msg.append(": ");
            msg.append(user);
            msg.append("</body>");
            msg.append("</html>");
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.toString(),
                                                                          null));
        }
    }

    public void setGenerateNewBatchPopUp(RichPopup generateNewBatchPopUp) {
        this.generateNewBatchPopUp = generateNewBatchPopUp;
    }

    public RichPopup getGenerateNewBatchPopUp() {
        return generateNewBatchPopUp;
    }

    public void setNewPopBinForBatch(RichPopup newPopBinForBatch) {
        this.newPopBinForBatch = newPopBinForBatch;
    }

    public RichPopup getNewPopBinForBatch() {
        return newPopBinForBatch;
    }

    public void newBatchPaymentAddAction(ActionEvent actionEvent) {
        Object id;
        OperationBinding op=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getWfId");
        op.execute();
        id=op.getResult();
        if (id!=null) {
            System.out.println("-----------------generate New Batch-----------");


            // To check wheter any outstanding is left or not

            String check = callStoredFunction(Types.VARCHAR, "FIN.FN_IS_CR_EXIST_FOR_BATCH(?,?,?,?,?)", new Object[] {
                                              this.getCurrentCld(), this.getCurrentServerLoc(), this.getCurrentHO(),
                                              this.getCurrentOrg(), inst_id
            }).toString();

            BatchpayAMImpl am = (BatchpayAMImpl) resolvElDC("BatchpayAMDataControl");


            System.out.println(" Is there any Cr amount  " + check);

            if (check.equalsIgnoreCase("N")) {
                System.out.println("------Came in If Statement");
                /* String msg2 = "No Outstading amount yet."; */
                String msg2 = resolvElDCMsg("#{bundle['MSG.1110']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return;
            }

            String result = am.addAllow();

            if (result.equals("N")) {

                /* String msg2 = "No Outstading amount yet."; */
                String msg2 = "Process All Voucher First"; //resolvElDCMsg("#{bundle['MSG.1110']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return;
            }

            System.out.println("\n------Came in else--------\n");
            getBindings().getOperationBinding("setValueOfOsDays").execute();
            getBindings().getOperationBinding("setBudgetAmt").execute();
            showPopup(newPopBinForBatch, true);
        }else{
            String msg2 = "Workflow is not defined for Document";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    public void setNewBacthAddLinkBind(RichCommandImageLink newBacthAddLinkBind) {
        this.newBacthAddLinkBind = newBacthAddLinkBind;
    }

    public RichCommandImageLink getNewBacthAddLinkBind() {
        return newBacthAddLinkBind;
    }

    public void newBatchPayDialogAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

            BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
            Integer rows=am.getApPay1().getRowCount();
            ViewObjectImpl v = am.getDummy();
            Row row = v.getCurrentRow();
            String org = row.getAttribute("Tran_org_id").toString();
            Integer days1 = Integer.parseInt(row.getAttribute("Param1").toString());
            Integer days2 = Integer.parseInt(row.getAttribute("Param2").toString());
            Integer days3 = Integer.parseInt(row.getAttribute("Param3").toString());
            Integer days4 = Integer.parseInt(row.getAttribute("Param4").toString());

            if (days1 < 0) {

                /* String msg2 = "OS days must be greater than zero."; */
                String msg2 = resolvElDCMsg("#{bundle['MSG.1109']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);


            }
            if (days1 >= days2 || days2 >= days3 || days3 >= days4) {
                /* String msg2 = "OS days should be in increasing order"; */

                String msg2 = resolvElDCMsg("#{bundle['MSG.1108']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);


            } else {
                try{
                      System.out.println("am has code is "+am);
                      BindingContainer bindings = getBindings();
                      OperationBinding operationBinding = bindings.getOperationBinding("generate");
    
                      operationBinding.execute();
            
                        Integer newRows=am.getApPay1().getRowCount() - rows;
                    
//                    if(newRows==0){
//                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"There is not any voucher with this specific currency.So Batch can not be created",null));
//                    }
                    
                      System.out.println("generate");
                }catch(Exception e){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Batch Payment setup is not proper.Batch can't be created!!",null));
                }

                BindingContainer bindingsave = getBindings();


                getBindings().getOperationBinding("Execute2").execute();
                System.out.println("save");

                BindingContainer bindingEXE = getBindings();
                OperationBinding operationBindingexe = bindingEXE.getOperationBinding("Execute1");
                operationBindingexe.execute();

                System.out.println(this.txnId.getValue());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.txnId);
                System.out.println(this.txnId.getValue());

            /**a user ,that is not workflowdefined user, is a user at level 0;
             * */
                try{
                    OperationBinding wfbinding = bindingsave.getOperationBinding("callWfFunctions");
                    wfbinding.execute();
                    operationBindingexe.execute();
                }catch(Exception e){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Batch Payment setup is not proper.Batch can't be created!!",null));
                }
                System.out.println("execute");
                System.out.println(usr_id + "---" + org_id + "----" + sloc_id + "---" + org + "---" + osdays1 + "---" +
                                   osdays2 + "----" + osdays3 + "---" + osdays4);


                getBindings().getOperationBinding("batchrefresh").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(batchIdBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(newBatchPaymentTableBind);
            }
        }

    }

    public void newBatchPageSearchAction(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("batchPageSearch");
        binding.execute();
        //  getBindings().getOperationBinding("Execute2").execute();
    }

    public void newBatchPageResetAction(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("batchReset");
        binding.execute();
    }

    public void setNewPageBatchIdBind(RichSelectOneChoice newPageBatchIdBind) {
        this.newPageBatchIdBind = newPageBatchIdBind;
    }

    public RichSelectOneChoice getNewPageBatchIdBind() {
        return newPageBatchIdBind;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setNewBatchPaymentTableBind(RichTable newBatchPaymentTableBind) {
        this.newBatchPaymentTableBind = newBatchPaymentTableBind;
    }

    public RichTable getNewBatchPaymentTableBind() {
        return newBatchPaymentTableBind;
    }


    public String apPayBackAction() {
        // Add event code here...
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
//        if (isSaved) {
//            am.getFinApPayView().executeQuery();
//        }
        
        
        // to filter FinVO with null Pay Id;
        
        am.getFinApPayView().setBindBatchId(null);
        am.getFinApPayView().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(newBacthAddLinkBind);
        return "back";
    }

    public String apPayRefreshAction() {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl v = am.getApPay1();

        Row row = v.getCurrentRow();
        String pay_id = row.getAttribute("ApPayId").toString();
        Integer param1 = Integer.parseInt(row.getAttribute("ApParam1").toString());
        Integer param2 = Integer.parseInt(row.getAttribute("ApParam2").toString());
        Integer param3 = Integer.parseInt(row.getAttribute("ApParam3").toString());
        Integer param4 = Integer.parseInt(row.getAttribute("ApParam4").toString());
        Integer coa_id = Integer.parseInt(row.getAttribute("ApPayBnkCoa").toString());
        Number amt = new Number(0);
        if (row.getAttribute("ApAmtBudget") != null) {
            amt = (Number)row.getAttribute("ApAmtBudget");
        }
        // Date dt = (Date)Date.getCurrentDate();

        ViewObjectImpl date = am.getSystemDate();
        RowSetIterator itr = date.createRowSetIterator(null);
        Date dt = (Date)Date.getCurrentDate();
        if (itr.hasNext()) {
            SystemDateVORowImpl d = (SystemDateVORowImpl)itr.next();
            dt = (Date)d.getSysdate();
        }

        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_SERV_LOC}").toString());
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_USR}").toString());
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();


        callStoredProcedure("FIN.proc_refresh_batch(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[] { cld_id, SlocId, ho_org_id, orgId, inst_id, pay_id, usrId, dt, coa_id, amt,
                                           param1, param2, param3, param4 });


        v.executeQuery();
        //     getBindings().getOperationBinding("setBudgetAmt").execute();
        am.getApPay1().executeQuery();
        am.getApPaySum1().executeQuery();
        am.getApPayDtl1().executeQuery();
        am.getApPayDtl2().executeQuery();
        setMode("edit");
        AdfFacesContext.getCurrentInstance().addPartialTarget(apPayTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(suntabOnPayPage);
        return null;
    }

    public void setApPayPagePopDateBind(RichInputDate apPayPagePopDateBind) {
        this.apPayPagePopDateBind = apPayPagePopDateBind;
    }

    public RichInputDate getApPayPagePopDateBind() {
        return apPayPagePopDateBind;
    }

    public String apPaySumSaveAndBackAction() {

        getBindings().getOperationBinding("ApPayConfirm").execute();
        getBindings().getOperationBinding("Execute").execute();

        return "back";
    }

    public String apPaySumCancelAction() {
        getBindings().getOperationBinding("apPaySumCancel").execute();
        getBindings().getOperationBinding("Execute1").execute();

        return "back";

    }

    public void apPaySumResetCOA(ActionEvent actionEvent) {

        OperationBinding binding = getBindings().getOperationBinding("searchCOA");
        binding.getParamsMap().put("Str", "false");
        binding.execute();
        coaNameBind.setValue("");
        getBindings().getOperationBinding("Execute").execute();
    }

    public void apPaySumCOASearch(ActionEvent actionEvent) {

        OperationBinding binding = getBindings().getOperationBinding("searchCOA");
        binding.getParamsMap().put("Str", "true");
        binding.execute();
        getBindings().getOperationBinding("Execute").execute();
    }

    public void setCoaNameBind(RichInputListOfValues coaNameBind) {
        this.coaNameBind = coaNameBind;
    }

    public RichInputListOfValues getCoaNameBind() {
        return coaNameBind;
    }

    public void setOsOrgAmt1(Number OsOrgAmt1) {
        this.OsOrgAmt1 = OsOrgAmt1;
    }

    public Number getOsOrgAmt1() {
        return OsOrgAmt1;
    }

    public void setOsOrgAmt2(Number OsOrgAmt2) {
        this.OsOrgAmt2 = OsOrgAmt2;
    }

    public Number getOsOrgAmt2() {
        return OsOrgAmt2;
    }

    public void setOsOrgAmt3(Number OsOrgAmt3) {
        this.OsOrgAmt3 = OsOrgAmt3;
    }

    public Number getOsOrgAmt3() {
        return OsOrgAmt3;
    }

    public void setOsOrgAmt4(Number OsOrgAmt4) {
        this.OsOrgAmt4 = OsOrgAmt4;
    }

    public Number getOsOrgAmt4() {
        return OsOrgAmt4;
    }


    public void adjustAmountValidateAction(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (specificAmount.getValue() != null && object != null) {
           
            float old = Float.valueOf(specificAmount.getValue().toString()).floatValue();
            float New = Float.valueOf(object.toString()).floatValue();

            if ((New > old) || (New < 0)) {
                System.out.println("Condition false came in avlidator");

                String message = resolvElDCMsg("#{bundle['MSG.1150']}").toString();
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "value must be less than or equal to specific amount and grater than or equal to zero",
                                                              null)); */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
    }

    public void setSpecificAmount(RichOutputText specificAmount) {
        this.specificAmount = specificAmount;
    }

    public RichOutputText getSpecificAmount() {
        return specificAmount;
    }

    public void adjAmountChangeAction(ValueChangeEvent ve) {
     
        checkBind.setSelected(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(checkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transAdjAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(apPaysumPageTableBind);
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ve.getComponent().processUpdates(FacesContext.getCurrentInstance());
        
        OperationBinding op= this.getBindings().getOperationBinding("refereshTDS"); 
        op.execute();
        
        
    }

    public void setApPayDtlBind(RichTable apPayDtlBind) {
        this.apPayDtlBind = apPayDtlBind;
    }

    public RichTable getApPayDtlBind() {
        return apPayDtlBind;
    }

    public void setCheckBind(RichSelectBooleanCheckbox checkBind) {
        this.checkBind = checkBind;
    }

    public RichSelectBooleanCheckbox getCheckBind() {
        return checkBind;
    }

    public void selectCheckAction(ActionEvent actionEvent) {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl apPayDtl1 = am.getApPayDtl1();
        System.out.println("-------------------2");
        RowSetIterator iterator = apPayDtl1.createRowSetIterator(null);
        System.out.println("-------------------3");
        iterator.reset();
        System.out.println("-------------------4");

        while (iterator.hasNext()) {
            Row r = iterator.next();
            System.out.println(r.getAttribute("ApFlag"));
            r.setAttribute("ApFlag", "Y");
            System.out.println(r.getAttribute("ApFlag"));
            Number apAmtSp = (Number)r.getAttribute("ApAmtSp");
            System.out.println(apAmtSp + "---------------amount----sp");
            r.setAttribute("ApAmtAdj", apAmtSp);
            AdfFacesContext.getCurrentInstance().addPartialTarget(checkBind);
        }
        iterator.closeRowSetIterator();
    }

    public void deSelectAllAction(ActionEvent actionEvent) {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObjectImpl apPayDtl1 = am.getApPayDtl1();
        System.out.println("-------------------2");
        RowSetIterator iterator = apPayDtl1.createRowSetIterator(null);
        System.out.println("-------------------3");
        iterator.reset();
        System.out.println("-------------------4");

        while (iterator.hasNext()) {
            Row r = iterator.next();
            System.out.println(r.getAttribute("ApFlag"));
            r.setAttribute("ApFlag", "N");
            System.out.println(r.getAttribute("ApFlag"));
            r.setAttribute("ApAmtAdj", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(checkBind);
        }
        iterator.closeRowSetIterator();
    }

    public void setSelectAllBind(RichCommandImageLink selectAllBind) {
        this.selectAllBind = selectAllBind;
    }

    public RichCommandImageLink getSelectAllBind() {
        return selectAllBind;
    }

    public void setDeSelectAllBind(RichCommandImageLink deSelectAllBind) {
        this.deSelectAllBind = deSelectAllBind;
    }

    public RichCommandImageLink getDeSelectAllBind() {
        return deSelectAllBind;
    }

    public void setApPaysumPageTableBind(RichTable apPaysumPageTableBind) {
        this.apPaysumPageTableBind = apPaysumPageTableBind;
    }

    public RichTable getApPaysumPageTableBind() {
        return apPaysumPageTableBind;
    }

    public void setTransAdjAmt(Number TransAdjAmt) {
        this.TransAdjAmt = TransAdjAmt;
    }

    public Number getTransAdjAmt() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number n = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            Object attribute = next.getAttribute("TransAdjmtAmt");
            if (attribute != null) {
                n = n.add((Number)attribute);
            }
        }
        return n;
    }

    public void setTransAdjAmtBind(RichInputText transAdjAmtBind) {
        this.transAdjAmtBind = transAdjAmtBind;
    }

    public RichInputText getTransAdjAmtBind() {
        return transAdjAmtBind;
    }

    public void setPopCheckBind(RichSelectBooleanCheckbox popCheckBind) {
        this.popCheckBind = popCheckBind;
    }

    public RichSelectBooleanCheckbox getPopCheckBind() {
        return popCheckBind;
    }

    public void setTotalEXFAmount(Number TotalEXFAmount) {
        this.TotalEXFAmount = TotalEXFAmount;
    }

    public Number getTotalEXFAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPayDtl1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApFlag").toString().equalsIgnoreCase("Y")) {
                //  Number amt = (Number)currentRow.getAttribute("ApAmtAdj");

                if (currentRow.getAttribute("ApAmtExFluBs") != null) {
                    Number amt = (Number)currentRow.getAttribute("ApAmtExFluBs");
                    n = n.add(amt);
                }

            }
        }
        rit.closeRowSetIterator();
        return n;
        //return TotalEXFAmount;
    }

    public void setNewTotalAmount(Number NewTotalAmount) {
        this.NewTotalAmount = NewTotalAmount;
    }

    public Number getNewTotalAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPayDtl1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApFlag").toString().equalsIgnoreCase("Y")) {
                //  Number amt = (Number)currentRow.getAttribute("ApAmtAdj");

                if (currentRow.getAttribute("ApAmtAdjBs") != null) {
                    Number amt = (Number)currentRow.getAttribute("ApAmtAdjBs");
                    n = n.add(amt);
                }

            }
        }
        rit.closeRowSetIterator();
        return n;
        //return NewTotalAmount;
    }

    public void setTotalInstAmount(Number TotalInstAmount) {
        this.TotalInstAmount = TotalInstAmount;
    }

    public Number getTotalInstAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPayDtl1();
        RowSet rowset = v1.getRowSet();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApFlag").toString().equalsIgnoreCase("Y")) {
                //  Number amt = (Number)currentRow.getAttribute("ApAmtAdj");

                if (currentRow.getAttribute("ApAmtBnk") != null) {
                    Number amt = (Number)currentRow.getAttribute("ApAmtBnk");
                    n = n.add(amt);
                }

            }
        }
        rit.closeRowSetIterator();
        return n;
        //return TotalInstAmount;
    }

    public void setTransNewTotalAmount(Number TransNewTotalAmount) {
        this.TransNewTotalAmount = TransNewTotalAmount;
    }

    public Number getTransNewTotalAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number n = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            Object attribute = next.getAttribute("TransPaySumCrAdj");
            if (attribute != null) {
                n = n.add((Number)attribute);
            }
        }
        return n;
        //return TransNewTotalAmount;
    }

    public void setTransTotalInstAmount(Number TransTotalInstAmount) {
        this.TransTotalInstAmount = TransTotalInstAmount;
    }

    public Number getTransTotalInstAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number n = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            Object attribute = next.getAttribute("TransPaySumInmtAdj");
            if (attribute != null) {
                n = n.add((Number)attribute);
            }
        }
        return n;
        //  return TransTotalInstAmount;
    }

    public void setTransTotalEXFAmount(Number TransTotalEXFAmount) {
        this.TransTotalEXFAmount = TransTotalEXFAmount;
    }

    public Number getTransTotalEXFAmount() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number n = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            Object attribute = next.getAttribute("TransPaySumExfAdj");
            if (attribute != null) {
                n = n.add((Number)attribute);
            }
        }
        return n;
        //return TransTotalEXFAmount;
    }

    public void apPayPageSaveAction(ActionEvent actionEvent) {

        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope. GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");

        String flag = "N";

        ApPayVOImpl apPaySum1 = am.getApPay1();
        Row currentRow = apPaySum1.getCurrentRow();
        if (currentRow != null) {
            if (currentRow.getAttribute("ApPayId") != null) {
                String pay_id = currentRow.getAttribute("ApPayId").toString();
                try {
                    flag =
callStoredFunction(Types.VARCHAR, "FIN.FN_SAVE_BATCH(?,?,?,?,?,?)", new Object[] { cld_id, sloc_id, ho_org_id, org_id,
                                                                                   inst_id, pay_id }).toString();
                } catch (Exception e) {
                    System.out.println("--------------------------Error occur during the calling fo fn save batch function---------------------------------");
                }

            }
        }
        System.out.println("flag value------" + flag);
        String msg;
        if (flag.equals("Y")) {
            getBindings().getOperationBinding("Commit").execute();

            String message = resolvElDCMsg("#{bundle['MSG.818']}").toString();
            //  msg = "Record Saved Successfully";
            msg = message;

            FacesMessage message2 = new FacesMessage(msg);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            setMode("pend");
            getBindings().getOperationBinding("Execute").execute();
            isSaved = true;
        } else {


            String message = resolvElDCMsg("#{bundle['MSG.1151']}").toString();

            //      msg = "Record did not save successfully";
            msg = message;
            FacesMessage message2 = new FacesMessage(msg);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
        // am.getApPaySum1().executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(apPayTableBind);
    }

    public void filterDetail2() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayDtlVOImpl dtl = am.getApPayDtl2();
        ApPayVOImpl apPay1 = am.getApPay1();
        ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
        dtl.setNamedWhereClauseParam("CLD_ID_BIND", pay.getApCldId());
        dtl.setNamedWhereClauseParam("HO_ORG_ID_BIND", pay.getApHoOrgId());
        dtl.setNamedWhereClauseParam("ORG_ID_BIND", pay.getApOrgId());
        dtl.setNamedWhereClauseParam("SLOC_ID_BIND", pay.getApSlocId());
        dtl.setNamedWhereClauseParam("PAY_ID_BIND", pay.getApPayId());
        dtl.setNamedWhereClauseParam("INST_ID_BIND", pay.getApApplInstId());
        //  dtl.executeQuery();

        Integer days[] = getDays();
        dtl.setWhereClause("trunc(Sysdate) - AP_VOU_DT between " + days[0] + " and " + days[1]);
        dtl.executeQuery();
    }

    public Integer[] getDays() {

        Integer link = 0;

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVOImpl apPay1 = am.getApPay1();
        ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
        Integer a[] = new Integer[2];
        switch (billDetails) {
        case 1:
            a[0] = 0;
            a[1] = pay.getApParam1();
            break;
        case 2:
            a[0] = pay.getApParam1() + 1;
            a[1] = pay.getApParam2();
            break;
        case 3:
            a[0] = pay.getApParam2() + 1;
            a[1] = pay.getApParam3();
            break;
        case 4:
            a[0] = pay.getApParam3() + 1;
            a[1] = pay.getApParam4();
            break;
        }
        return a;
    }

    public void setTotalAdjustedAmt(Number totalAdjustedAmt) {
        this.totalAdjustedAmt = totalAdjustedAmt;
    }

    public Number getTotalAdjustedAmt() {
        //return adjamt4.add(adjamt3).add(adjamt2).add(adjamt1);
        //return totalAdjustedAmt;

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ViewObject v1 = am.getApPaySum1();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number n = new Number(0);
        while (rit.hasNext()) {
            Row currentRow = rit.next();
            if (currentRow.getAttribute("ApPaySumAdj1") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj1");
                n = n.add(amt);
            }
            if (currentRow.getAttribute("ApPaySumAdj2") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj2");
                n = n.add(amt);
            }
            if (currentRow.getAttribute("ApPaySumAdj3") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj3");
                n = n.add(amt);
            }
            if (currentRow.getAttribute("ApPaySumAdj4") != null) {
                Number amt = (Number)currentRow.getAttribute("ApPaySumAdj4");
                n = n.add(amt);
            }
        }
        return n;
    }

    public void setDiff(Number diff) {
        this.diff = diff;
    }

    public Number getDiff() {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVORowImpl pay = (ApPayVORowImpl)am.getApPay1().getCurrentRow();
        Number amtBudget = pay.getApAmtBudget();
        Number n = new Number(0);
        if (pay.getApAmtBudget() != null && getTotalAdjustedAmt() != null) {

            n = (Number)pay.getApAmtBudget().minus(getTotalAdjustedAmt());
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(diffBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(apPayTableBind);

        return (Number)n.abs();
    }

    public void setDiffBind(RichInputText diffBind) {
        this.diffBind = diffBind;
    }

    public RichInputText getDiffBind() {
        return diffBind;
    }

    public void setApPayTableBind(RichTable apPayTableBind) {
        this.apPayTableBind = apPayTableBind;
    }

    public RichTable getApPayTableBind() {
        return apPayTableBind;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {


        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVORowImpl pay = (ApPayVORowImpl)am.getApPay1().getCurrentRow();
        Number amtBudget = pay.getApAmtBudget();
        Number n = new Number(0);
        if (pay.getApAmtBudget() != null && getTotalAdjustedAmt() != null) {

            n = (Number)pay.getApAmtBudget().minus(getTotalAdjustedAmt());
        }

        if (n.intValue() > 0 || n.intValue() == 0)
            return true;
        else
            return false;
    }

    public void setTotalOsAmt(Number totalOsAmt) {
        this.totalOsAmt = totalOsAmt;
    }

    public Number getTotalOsAmt() {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number amt = new Number(0);
        int counter = 0, check = 0;
        while (itr.hasNext()) {
            check++;
            Row next = itr.next();
            if (next.getAttribute("ApSumAmtOs") != null) {

                Number n = (Number)next.getAttribute("ApSumAmtOs");
                if (n.intValue() > 0)
                    counter++;
                amt = amt.add(n);
            }
        }
        System.out.println("Total outstanding amount is: " + amt + "-----------" + counter + " ------rows" + check +
                           "----");
        return amt;
        //return totalOsAmt;
    }

    public void setTotalSumAmtAdj(Number totalSumAmtAdj) {
        this.totalSumAmtAdj = totalSumAmtAdj;
    }

    public Number getTotalSumAmtAdj() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number amt = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            if (next.getAttribute("ApSumAmtAdj") != null) {

                Number n = (Number)next.getAttribute("ApSumAmtAdj");
                amt = amt.add(n);
            }
        }
        return amt;
        //return totalSumAmtAdj;
    }

    public void setTotalTdsAmt(Number totalTdsAmt) {
        this.totalTdsAmt = totalTdsAmt;
    }

    public Number getTotalTdsAmt() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPaySumVOImpl apPaySum1 = am.getApPaySum1();
        RowSetIterator itr = apPaySum1.createRowSetIterator(null);
        Number amt = new Number(0);
        while (itr.hasNext()) {
            Row next = itr.next();
            if (next.getAttribute("ApPayTdsAmt") != null) {

                Number n = (Number)next.getAttribute("ApPayTdsAmt");
                amt = amt.add(n);
            }
        }
        return amt;
        // return totalTdsAmt;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String viewAction() {
        setMode("view");
        getDaysList();
        return "pay";
    }

    public void setLink1(boolean link1) {
        this.link1 = link1;
    }

    public boolean isLink1() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVOImpl apPay1 = am.getApPay1();

        if (apPay1.getCurrentRow() != null) {
            ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
            Number amt = pay.getApSumOrgAmtOs1();
            if (amt.intValue() > 0)
                return false;
        }
        return true;
        //return link1;
    }

    public void setLink2(boolean link2) {
        this.link2 = link2;
    }

    public boolean isLink2() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVOImpl apPay1 = am.getApPay1();

        if (apPay1.getCurrentRow() != null) {
            ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
            Number amt = pay.getApSumOrgAmtOs2();
            if (amt.intValue() > 0)
                return false;
        }
        return true;
        //  return link2;
    }

    public void setLink3(boolean link3) {
        this.link3 = link3;
    }

    public boolean isLink3() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVOImpl apPay1 = am.getApPay1();

        if (apPay1.getCurrentRow() != null) {
            ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
            Number amt = pay.getApSumOrgAmtOs3();
            if (amt.intValue() > 0)
                return false;
        }
        return true;
        //   return link3;
    }

    public void setLink4(boolean link4) {
        this.link4 = link4;
    }

    public boolean isLink4() {
        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        ApPayVOImpl apPay1 = am.getApPay1();

        if (apPay1.getCurrentRow() != null) {
            ApPayVORowImpl pay = (ApPayVORowImpl)apPay1.getCurrentRow();
            Number amt = pay.getApSumOrgAmtOs4();
            if (amt.intValue() > 0)
                return false;
        }
        return true;
        //  return link4;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getState() {

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        FinApPayViewVOImpl apPayView = am.getFinApPayView();
        Row[] filteredRows = apPayView.getFilteredRows("ApPayStat", new Integer(193));
        System.out.println("length of rows are: " + filteredRows.length + "-----------");
        if (filteredRows.length > 0)
            return "view";
        else
            return "edit";
    }

    public void setSaveState(String saveState) {
        this.saveState = saveState;
    }

    public String getSaveState() {
        Number adjustedAmt = getTotalAdjustedAmt();
        int val = adjustedAmt.intValue();
        if (val > 0)
            return "edit";
        else
            return "view";
    }


    public void setLinkState1(String linkState1) {
        this.linkState1 = linkState1;
    }

    public String getLinkState1() {
        int val = getAdjamt1().intValue();
        if (val > 0)
            return "edit";
        else
            return "view";
        //  return linkState1;
    }

    public void setLinkState2(String linkState2) {
        this.linkState2 = linkState2;
    }

    public String getLinkState2() {
        int val = getAdjamt2().intValue();
        if (val > 0)
            return "edit";
        else
            return "view";
        //   return linkState2;
    }

    public void setLinkState3(String linkState3) {
        this.linkState3 = linkState3;
    }

    public String getLinkState3() {
        int val = getAdjamt3().intValue();
        if (val > 0)
            return "edit";
        else
            return "view";
        //  return linkState3;
    }

    public void setLinkState4(String linkState4) {
        this.linkState4 = linkState4;
    }

    public String getLinkState4() {
        int val = getAdjamt4().intValue();
        if (val > 0)
            return "edit";
        else
            return "view";

    }

    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    public Date getCurrDate() {
        return currDate;
    }

    public String processBatch() {

        Number totalInst = getSumInstAmt();

        BatchpayAMImpl am = (BatchpayAMImpl)resolvElDC("BatchpayAMDataControl");
        am.getDBTransaction().commit();
        ViewObjectImpl v = am.getApPay1();
        Row currrow = v.getCurrentRow();
        Number budget = new Number(0);

        if (currrow.getAttribute("ApAmtBudget") != null) {
            budget = (Number)currrow.getAttribute("ApAmtBudget");
        }

        System.out.println(totalInst + "------" + budget);

        showPopup(parameterPopup, true);
        RowSetIterator itr = am.getParameter().createRowSetIterator(null);
        if (itr.hasNext()) {
            Row next = itr.next();
            next.setAttribute("inst_no", null);
        }
        getBindings().getOperationBinding("setDate").execute();
        return null;
    }

    public void setWarningBind(RichOutputText warningBind) {
        this.warningBind = warningBind;
    }

    public RichOutputText getWarningBind() {
        return warningBind;
    }

    public void setForwardbuttonBind(RichCommandButton forwardbuttonBind) {
        this.forwardbuttonBind = forwardbuttonBind;
    }

    public RichCommandButton getForwardbuttonBind() {
        return forwardbuttonBind;
    }

    public void setBatchIdBind(RichInputListOfValues batchIdBind) {
        this.batchIdBind = batchIdBind;
    }

    public RichInputListOfValues getBatchIdBind() {
        return batchIdBind;
    }

    public void setResetButtonBind(RichCommandImageLink resetButtonBind) {
        this.resetButtonBind = resetButtonBind;
    }

    public RichCommandImageLink getResetButtonBind() {
        return resetButtonBind;
    }

    public void getCurrIdAction(ValueChangeEvent ve) {
        ve.getComponent().processUpdates(FacesContext.getCurrentInstance());
        getBindings().getOperationBinding("selectCurrId").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(currIdBind);
    }

    public void setSaveButtonBind(RichCommandImageLink saveButtonBind) {
        this.saveButtonBind = saveButtonBind;
    }

    public RichCommandImageLink getSaveButtonBind() {
        return saveButtonBind;
    }
    public void setCurrIdBind(RichSelectOneChoice currIdBind) {
        this.currIdBind = currIdBind;
    }

    public RichSelectOneChoice getCurrIdBind() {
        return currIdBind;
    }

    public void setTxnId(RichOutputText txnId) {
        this.txnId = txnId;
    }

    public RichOutputText getTxnId() {
        return txnId;
    }

    public void setIsInWf(Boolean isInWf) {
        this.isInWf = isInWf;
    }

    public Boolean getIsInWf() {
        return isInWf;
    }

    public void viewTDS(ActionEvent actionEvent) {
        UIComponent component = (UIComponent) actionEvent.getSource();
        
        System.out.println("Component is "+component);
        System.out.println("CoaId="+ component.getAttributes().get("coaId"));
        if(component!=null && component.getAttributes().get("coaId")!=null){
            Integer coaId=(Integer) component.getAttributes().get("coaId");
            System.out.println("Coa Id "+coaId);
            OperationBinding op =getBindings().getOperationBinding("ExecuteWithParams");
            op.getParamsMap().put("BindCrCoa", coaId);
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsPopUp);
        }
       
        this.showPopup(tdsPopUp, true);
        
    }

    public void setTdsPopUp(RichPopup tdsPopUp) {
        this.tdsPopUp = tdsPopUp;
    }

    public RichPopup getTdsPopUp() {
        return tdsPopUp;
    }
    
    
    public static Object invokeEL(String el, Class[] paramTypes,
        Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory =
        facesContext.getApplication().getExpressionFactory();
    
        MethodExpression exp =
        expressionFactory.createMethodExpression(elContext, el,
        Object.class, paramTypes);
    
        return exp.invoke(elContext, params);
    }

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void validateInstrumentNumber(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //instrumentNumber
        
        if(object  != null && object.toString() !=null && (Integer.parseInt(object.toString())) == 0 ){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Instrument number should have should be greater than 0",null)); 
        }
        
        String result="N";
        if(result.equalsIgnoreCase("Y")){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Instrument numbers in Series have duplicate.Can Not Proceed",null));
        }
    }

    public void dateChangeListener(ValueChangeEvent valueChangeEvent) {

         
                  System.out.println("Differece of date"+ (Date)valueChangeEvent.getNewValue() +" AND "+  (new Date()).getCurrentDate()+" IS"+((Date)valueChangeEvent.getNewValue()).compareTo((new Date()).getCurrentDate()));
                      if(((Date)valueChangeEvent.getNewValue()).dateValue().after((new java.util.Date())))
                               FacesContext.getCurrentInstance().addMessage(apPayPagePopDateBind.getClientId(),new FacesMessage(FacesMessage.SEVERITY_WARN,"Warining","Selected Date is not current System Date"));
             

    }

    public void validateDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
        if(object != null){
         
            String fydt=object.toString();
            oracle.jbo.domain.Date dt=new oracle.jbo.domain.Date();
            String sysdt=dt.getCurrentDate().dateValue().toString();
            if(sysdt.compareTo(fydt)==1) {
            FacesMessage errMsg = new FacesMessage("Invalid Date !");
            errMsg.setDetail(" Date should be greater than or equal to current date");
            throw new ValidatorException(errMsg);
            }
        }else{
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL,"Required","This cant not be null"));
        }

    }

    public void setTotAdjNew(RichInputText totAdjNew) {
        this.totAdjNew = totAdjNew;
    }

    public RichInputText getTotAdjNew() {
        return totAdjNew;
    }

    public void setTotPayAmt(RichInputText totPayAmt) {
        this.totPayAmt = totPayAmt;
    }

    public RichInputText getTotPayAmt() {
        return totPayAmt;
    }

    public void setTotFluAmt(RichInputText totFluAmt) {
        this.totFluAmt = totFluAmt;
    }

    public RichInputText getTotFluAmt() {
        return totFluAmt;
    }

    public void projectWiseVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if(valueChangeEvent.getNewValue()!=null){
            OperationBinding op =getBindings().getOperationBinding("projectWise");
            op.execute();
        }
    }
}
