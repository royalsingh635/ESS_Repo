package mmissuematerial.view.bean;

import java.sql.SQLException;

import java.util.ArrayList;

import oracle.jbo.domain.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.domain.Number;
import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
/**
 * Material Issue Managed Bean.
 * Contains all the business logic for Issue Items functionalities.
 * @author BL
 * Dated -25/08/2013
 * */

public class MaterialIssueAddEditBean {
    private RichInputDate issueDateBind;
    private RichSelectOneChoice docSourceBind;
    private RichInputListOfValues srcDocNoBind;
    private RichInputText pendingQtyBind;
    private RichPopup itmSrPopup;
    private RichPopup itmBinPopup;
    private RichPopup itmLotPopup;
    private RichInputText issuQtyBind;
    Number zero = new Number(0);
    private RichInputText avlQtyBind;
    private RichTable srTableBind;
    private String mode=modeGet();
    private RichPopup docNoDeletePopup;
    private RichInputText docIdSrcDispBind;
    private RichTable srcTableBind;
    private RichSelectOneChoice whIdPageBind;
    private RichSelectOneChoice reqAreaBind;
    private RichSelectOneChoice srcOrgBind;
    private RichSelectOneChoice srcWhBind;
    private RichTable lotBinTableBind;
    private RichTable lotTableBind;
    private RichInputDate rturnDtBind;
    private RichInputText totalAvlQty;

    public MaterialIssueAddEditBean() {
    }
    private static ADFLogger adflog= ADFLogger.createADFLogger(MaterialIssueAddEditBean.class);
    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
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
    public String createAction() {
      setMode("A");
        return "createMtlIss";
    }

    public String editAction() {
        // Add event code here...
        return null;
    }

    public String saveAction() {
        OperationBinding checkitmParm = getBindings().getOperationBinding("chkIssueQtyForSave");
        checkitmParm.execute();
        ArrayList itmList = new ArrayList();
        if(checkitmParm.getResult()!=null){
            itmList = (ArrayList)checkitmParm.getResult();
        }
        ArrayList itm = new ArrayList();
        if(itmList.size()>0){
        
        }else{
        OperationBinding checkitmLot = getBindings().getOperationBinding("checkItmQtyIssInTable");
        checkitmLot.execute();
       
        if(checkitmLot.getResult()!=null){
            itm = (ArrayList)checkitmLot.getResult();
        }
        }
       adflog.info("array--------------"+itmList+"item -------"+itm);
        if(itmList.size()>0){
            String msg=resolvElDCMsg("#{bundle['MSG.708']}").toString();
                
                   showFacesMessage( msg+itmList, "E", false, "F", null);
                   
        }else if(itm.size()>0){
               //  showFacesMessage("No Item Isuue for Item  "+itm, "E", false, "F", null);
         }
         else if(itmList.isEmpty() && itm.isEmpty()){
                Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                String tableNm = "MM$MTL$ISSU";
                // for FyId
                if(issueDateBind.getValue()!=null){
                Integer fyNo =0;
                OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
                 fyIdOp.getParamsMap().put("CldId", p_cldId);
                 fyIdOp.getParamsMap().put("OrgId", p_org_id);
                 fyIdOp.getParamsMap().put("geDate", (Timestamp)issueDateBind.getValue());
                 fyIdOp.getParamsMap().put("Mode", "A");
                 fyIdOp.execute();
                 if(fyIdOp.getResult()!=null){
                     fyNo = Integer.parseInt(fyIdOp.getResult().toString());
                 }
               adflog.info("Fy id in bean ---------"+fyNo);
              OperationBinding issuIdOp = getBindings().getOperationBinding("genIssueNo");
               issuIdOp.getParamsMap().put("SlocId", p_sloc_id);
               issuIdOp.getParamsMap().put("CldId", p_cldId);
               issuIdOp.getParamsMap().put("OrgId", p_org_id);
               issuIdOp.getParamsMap().put("TableName", tableNm);
               issuIdOp.getParamsMap().put("WhId", whIdPageBind.getValue().toString());
               issuIdOp.getParamsMap().put("fyId", fyNo);
               issuIdOp.execute();
            String ids =null;
               if(issuIdOp.getResult()!=null){
                    ids = issuIdOp.getResult().toString();
                   adflog.info("new generated issue id "+ids);
               } 
               OperationBinding postDB = getBindings().getOperationBinding("postChangeDB");
               postDB.execute(); 
            OperationBinding updateStock = getBindings().getOperationBinding("updateStockFromIssue");
            updateStock.execute();
            String isStockUpdate = "N";  
            if(updateStock.getResult()!=null){
                isStockUpdate = updateStock.getResult().toString();
             }
             if("N".equalsIgnoreCase(isStockUpdate)){
                 showFacesMessage(resolvElDCMsg("#{bundle['MSG.709']}").toString(), "E", false, "F", null);//"Error For updating Stock Table ! "
                // showFacesMessage("MSG.709", "E", true, "F", null);//"Error For updating Stock Table ! "
             }else if("Y".equalsIgnoreCase(isStockUpdate)){
                 OperationBinding updateIssuStat = getBindings().getOperationBinding("updateIssueStatus");
                     updateIssuStat.execute();
               OperationBinding saveOp = getBindings().getOperationBinding("Commit");
               saveOp.execute();
                setMode("V");
                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.711']}").toString()+ids+resolvElDCMsg("#{bundle['MSG.710']}").toString(), "I", false, "F", null);//1."Issue No "   2. " Save Successfully"
                }
               }else{
                   showFacesMessage(resolvElDCMsg("#{bundle['MSG.712']}").toString(), "E", false, "F", null);  //Select the Issue Date is required. 
               }
        }
        return null;
    }
    public String cancelAction() {
        
       
       OperationBinding rollbk = getBindings().getOperationBinding("Rollback");
       rollbk.execute();
       setMode(" ");
        return "back";
    }

    public String createItmAction() {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        adflog.info("doc sourc id    "+docSourceBind.getValue()+"--------"+srcDocNoBind.getValue());
        String chk ="N";
        OperationBinding reqchk = getBindings().getOperationBinding("checkWhOrgRequird");
        reqchk.execute();
        if(reqchk.getResult()!=null){
            chk= reqchk.getResult().toString();
        }
        if("R".equalsIgnoreCase(chk)){
            showFacesMessage(resolvElDCMsg("#{bundle['MSG.573']}").toString(), "E", false, "F", reqAreaBind.getClientId());//Requirement Area required.
        }else if("W".equalsIgnoreCase(chk)){
            showFacesMessage(resolvElDCMsg("#{bundle['MSG.713']}").toString(), "E", false, "F", srcWhBind.getClientId());//Warehouse  required.
        }else if("O".equalsIgnoreCase(chk)){
            showFacesMessage(resolvElDCMsg("#{bundle['MSG.714']}").toString(), "E", false, "F", srcOrgBind.getClientId()); //Organisation required. 
        }else if("S".equalsIgnoreCase(chk)){
            showFacesMessage(resolvElDCMsg("#{bundle['MSG.713']}").toString(), "E", false, "F", srcWhBind.getClientId());//Warehouse  required.
        }else if ("D".equalsIgnoreCase(chk)){
            showFacesMessage("Return Date required", "E", false, "F", rturnDtBind.getClientId());//Return Date required.
        }
        else if("N".equalsIgnoreCase(chk)){
        if(docSourceBind.getValue()!=null){
            if(srcDocNoBind.getValue()!=null){
              OperationBinding obInsrt = getBindings().getOperationBinding("chkIssueType");
                 obInsrt.getParamsMap().put("cldId", p_cldId);
                 obInsrt.getParamsMap().put("slocId", p_sloc_id);
                 obInsrt.getParamsMap().put("orgId", p_org_id);
                 obInsrt.getParamsMap().put("issuSrcType", docSourceBind.getValue());
                 obInsrt.execute();
            }
        }
        }
        return null;
    }
    /**
    *      Method to show validation message(I,E,W) 
    *      mesg:Message to display
    *      sev:Severity(I,E,W)
    *      chk:true=if resource bundle is used
    *      typFlg: 'F' for FM , 'V' for VE
    *      clientId : client id for UI component
    * */
    public void showFacesMessage(String mesg,String sev,Boolean chk,String typFlg, String clientId){
        FacesMessage message = new FacesMessage(mesg);
        if(chk==true){
            String msg = resolvEl("#{bundle['"+mesg+"']}");
            message = new FacesMessage(msg);
        }        
        if(sev.equalsIgnoreCase("E")){
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }else if(sev.equalsIgnoreCase("W")){
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }else if(sev.equalsIgnoreCase("I")){
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }else{
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if(typFlg.equals("F")){
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        }else if(typFlg.equals("V")){
            throw new ValidatorException(message);
        }
    }

   

    public void setIssueDateBind(RichInputDate issueDateBind) {
        this.issueDateBind = issueDateBind;
    }

    public RichInputDate getIssueDateBind() {
        return issueDateBind;
    }

    public void setDocSourceBind(RichSelectOneChoice docSourceBind) {
        this.docSourceBind = docSourceBind;
    }

    public RichSelectOneChoice getDocSourceBind() {
        return docSourceBind;
    }

    public void setSrcDocNoBind(RichInputListOfValues srcDocNoBind) {
        this.srcDocNoBind = srcDocNoBind;
    }

    public RichInputListOfValues getSrcDocNoBind() {
        return srcDocNoBind;
    }

    public void issueQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       Number value = (Number)object;
       Number pendQty = (Number)pendingQtyBind.getValue();
       Number avlQty = (Number)avlQtyBind.getValue();
    
       if(value.compareTo(zero)==-1){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                         resolvElDCMsg("#{bundle['MSG.715']}").toString(), null)); //Issue Quantity must be positive value.
           
       }else if(value!=null && avlQty!=null){
           if(value.compareTo(avlQty)==1){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             resolvElDCMsg("#{bundle['MSG.716']}").toString(), null)); //Issue Quantity must be less than or equals to Available Quantity.
           }
           
       } 
       if(value!=null && pendQty !=null){
           if(value.compareTo(pendQty)>0){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             resolvElDCMsg("#{bundle['MSG.717']}").toString(), null)); //Issue Quantity must be less than or equals to Pending Quantity
           }
       }
    }

    public void setPendingQtyBind(RichInputText pendingQtyBind) {
        this.pendingQtyBind = pendingQtyBind;
    }

    public RichInputText getPendingQtyBind() {
        return pendingQtyBind;
    }

    public String seleectSrItmAction() {
        if(zero.compareTo((Number)issuQtyBind.getValue())>=0){
            String msg2 = resolvElDCMsg("#{bundle['MSG.718']}").toString();//Issue Quantity Must be greater than Zero (0).
                                                           FacesMessage message2 = new FacesMessage(msg2);
                                                           message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                           FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(issuQtyBind.getClientId(),message2);
        }else{
    OperationBinding filterSr = getBindings().getOperationBinding("srNoFilterRow");
    filterSr.execute();
    showPopup(itmSrPopup, true);
        }
        return null;
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

    public void setItmSrPopup(RichPopup itmSrPopup) {
        this.itmSrPopup = itmSrPopup;
    }

    public RichPopup getItmSrPopup() {
        return itmSrPopup;
    }

    public void srNoSelectDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("ok")){
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            Number issQtyBal = getIssuQty();
            adflog.info("balance   : "+issQtyBal);
            if(issuQtyBind.getValue()!=null){
              // Method for check total issue quantity from  Summ$SR table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = getBindings().getOperationBinding("getTotalSRIssuQty");
                  totIssu.execute();
                  if(totIssu.getResult()!=null){
                      retTotVal=(Number)totIssu.getResult();
                  }
            }
            // Check conditon zero quantity issue 
            if(retTotVal.compareTo(zero)==0){
                adflog.info("inside popup -- condotion zero");
                String msg2 = resolvElDCMsg("#{bundle['MSG.719']}").toString();//Insert Issue Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
          //  }else if((retTotVal.compareTo(issuQtyBind.getValue())>0) || (retTotVal.compareTo(issuQtyBind.getValue()) < 0)){  // check conditon more or less quantity issue 
          }else if((retTotVal.compareTo(issQtyBal)>0) || (retTotVal.compareTo(issQtyBal) < 0)){  // check conditon more or less quantity issue 
                adflog.info("inside popup -- condition mis match");
                String msg2 = resolvElDCMsg("#{bundle['MSG.720']}").toString();//"Insert Issue Quantity not equals required Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
          //  }else  if(retTotVal.compareTo(issuQtyBind.getValue())==0){  // if Issue quantity and required quantity same            
          }else  if(retTotVal.compareTo(issQtyBal)==0){  // if Issue quantity and required quantity same            
            adflog.info("inside popup");
            // Method for inserting row from SRView to main issue SR table method define in AmImplclass
            OperationBinding srNoOp = getBindings().getOperationBinding("insrtSelectSrFrmView");
            srNoOp.execute();
        }
        }
    }


    public void setItmBinPopup(RichPopup itmBinPopup) {
        this.itmBinPopup = itmBinPopup;
    }

    public RichPopup getItmBinPopup() {
        return itmBinPopup;
    }

    public String selectBinQtyForItm() {
        Integer lotBinR = lotBinTableBind.getRowCount();
        adflog.info("table count lot       "+lotBinR);
        if(lotBinR==0){
           
           OperationBinding resetLotBinView = getBindings().getOperationBinding("resetViewLotBinValue");
             resetLotBinView.execute();
        }
        if(zero.compareTo((Number)issuQtyBind.getValue())>=0){
            String msg2 =resolvElDCMsg("#{bundle['MSG.718']}").toString();//Issue Quantity Must be greater than Zero (0).
                                                           FacesMessage message2 = new FacesMessage(msg2);
                                                           message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                           FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(issuQtyBind.getClientId(),message2);
        }else{
       showPopup(itmBinPopup, true);
        }
        return null;
    }

    public void BinNoSelectDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("ok")){
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if(issuQtyBind.getValue()!=null){
              // Method for check total issue quantity from  Summ$Bin table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = getBindings().getOperationBinding("getTotalBinIssuQty");
                  totIssu.execute();
                  if(totIssu.getResult()!=null){
                      retTotVal=(Number)totIssu.getResult();
                  }
            }
            // Check conditon zero quantity issue 
            if(retTotVal.compareTo(zero)==0){
                adflog.info("inside popup -- condotion zero");
                String msg2 = resolvElDCMsg("#{bundle['MSG.719']}").toString();//Insert Issue Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
            }else if((retTotVal.compareTo(issuQtyBind.getValue())>0) || (retTotVal.compareTo(issuQtyBind.getValue()) < 0)){  // check conditon more or less quantity issue 
                adflog.info("inside popup -- condition mis match");
                String msg2 = resolvElDCMsg("#{bundle['MSG.720']}").toString();//Insert Issue Quantity not equals required Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
            }else  if(retTotVal.compareTo(issuQtyBind.getValue())==0){  // if Issue quantity and required quantity same 
            adflog.info("inside -- popup");
            // Method for inserting row from BinView to main issue Bin table method define in AmImplclass
            OperationBinding binNoOp = getBindings().getOperationBinding("insrtSelectedBinForView");
            binNoOp.execute();
        }
        }
    }

    public String selectLotForItm() {
        Integer lotR = lotTableBind.getRowCount();
        if(lotR==0){
           
            OperationBinding resetLotView = getBindings().getOperationBinding("resetViewLotValue");
              resetLotView.execute();
        }else{
            OperationBinding setLotView = getBindings().getOperationBinding("setViewLotValue");
              setLotView.execute();
        }
        if(zero.compareTo((Number)issuQtyBind.getValue())>=0){
            String msg2 = resolvElDCMsg("#{bundle['MSG.718']}").toString();//Issue Quantity Must be greater than Zero (0).
                                                           FacesMessage message2 = new FacesMessage(msg2);
                                                           message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                           FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(issuQtyBind.getClientId(),message2);
        }else{
        showPopup(itmLotPopup, true);
        }
        return null;
    }

    public void setItmLotPopup(RichPopup itmLotPopup) {
        this.itmLotPopup = itmLotPopup;
    }

    public RichPopup getItmLotPopup() {
        return itmLotPopup;
    }

    public void lotNoSelectDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("ok")){
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if(issuQtyBind.getValue()!=null){
              // Method for check total issue quantity from  MM$Stk$Summ$lot table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = getBindings().getOperationBinding("getTotalIssuQty");
                  totIssu.execute();
                  if(totIssu.getResult()!=null){
                      retTotVal=(Number)totIssu.getResult();
                  }
            }
            // Check conditon zero quantity issue 
            if(retTotVal.compareTo(zero)==0){
                adflog.info("inside popup condotion zero");
                String msg2 = resolvElDCMsg("#{bundle['MSG.719']}").toString();//Insert Issue Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
            }else if((retTotVal.compareTo(issuQtyBind.getValue())>0) || (retTotVal.compareTo(issuQtyBind.getValue()) < 0)){  // check conditon more or less quantity issue 
                adflog.info("inside popup condition mis match");
                String msg2 = resolvElDCMsg("#{bundle['MSG.720']}").toString();//Insert Issue Quantity not equals required Quantity
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
            }else  if(retTotVal.compareTo(issuQtyBind.getValue())==0){  // if Issue quantity and required quantity same 
           
            adflog.info("inside popup");
            // Method for inserting row from lotView to main issue lot table method define in AmImplclass
          OperationBinding binNoOp = getBindings().getOperationBinding("insrtSelectedLotForView");
            binNoOp.execute(); 
            }
        }
    }

    public void setIssuQtyBind(RichInputText issuQtyBind) {
        this.issuQtyBind = issuQtyBind;
    }

    public RichInputText getIssuQtyBind() {
        return issuQtyBind;
    }
    public Object resolvElDCMsg(String data) {
          FacesContext fc = FacesContext.getCurrentInstance();
          Application app = fc.getApplication();
          ExpressionFactory elFactory = app.getExpressionFactory();
          ELContext elContext = fc.getELContext();
          ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
          return valueExp.getValue(elContext);
      }

    public void DocumentNoVCL(ValueChangeEvent vce) {
        if(vce.getNewValue()!=null){
        }
    }


    public void documentNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            String flagVal = "N";
            String docNo = (String)object;
            OperationBinding issuIdOp = getBindings().getOperationBinding("isSrcDocIdDuplicate");
             issuIdOp.getParamsMap().put("value", docNo);
             issuIdOp.execute();
             if(issuIdOp.getResult()!=null){
                 flagVal = issuIdOp.getResult().toString();
             }
             if("Y".equalsIgnoreCase(flagVal)){
                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 resolvElDCMsg("#{bundle['MSG.721']}").toString(), null)); 
             }
        }
    }

    public void setAvlQtyBind(RichInputText avlQtyBind) {
        this.avlQtyBind = avlQtyBind;
    }

    public RichInputText getAvlQtyBind() {
        return avlQtyBind;
    }
    public String getBinChk(){
      String binChk =resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
      if("Y".equalsIgnoreCase(binChk)){
          return "Y";
    }else{
            return "N";
    }
    }
    
    public String getDisableSrNoLink(){
        String chk ="N";
        try{
        if(issuQtyBind.getValue()!=null){
            Number issuQty = (Number)issuQtyBind.getValue();
           Integer tableCount = srTableBind.getRowCount();
        
           Integer issue = Integer.parseInt(issuQtyBind.getValue().toString());
               adflog.info("issuQty------  "+issuQty+"tableCount-----  "+tableCount+"issue----  "+issue);
               if(tableCount==issue){
                  chk = "Y";
               }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return chk;
    }

    public Number getIssuQty(){
        Number issQtyBal = zero;
        if(issuQtyBind.getValue()!=null){
            Number issuQty = (Number)issuQtyBind.getValue();
            Integer tableCount = srTableBind.getRowCount();
            Number tableValue= zero;

            try {
                tableValue = new Number(tableCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adflog.info("issuQty------  "+issuQty+"tableCount-----  "+tableCount+"tableValue----  "+tableValue);
            if(tableValue!=null){
                issQtyBal = (Number)issuQty.minus(tableValue);
            }
        }
        return issQtyBal;
    }

    public void setSrTableBind(RichTable srTableBind) {
        this.srTableBind = srTableBind;
    }

    public RichTable getSrTableBind() {
        return srTableBind;
    }

    public String autoIssueItmAction() {
        if(zero.compareTo((Number)issuQtyBind.getValue())>=0){
            String msg2 =resolvElDCMsg("#{bundle['MSG.718']}").toString();//Issue Quantity Must be greater than Zero (0).
                                                           FacesMessage message2 = new FacesMessage(msg2);
                                                           message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                           FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(issuQtyBind.getClientId(),message2);
        }else{
        OperationBinding autoIssu = getBindings().getOperationBinding("autoIssueItemfromSystem");
        autoIssu.execute();
            if(autoIssu.getResult()!=null){
                Integer retrnVal = Integer.parseInt(autoIssu.getResult().toString());
                adflog.info("ret--------"+retrnVal);
                if(retrnVal==1){
                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.722']}").toString(), "I", false, "F", null);
                }else{
                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.723']}").toString()+retrnVal, "W", false, "F", null);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
        }
        return null;
    }
    public String modeGet() {
        return resolvEl("#{pageFlowScope.Add_Edit_Mode}").toString();
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }


    public void sourceDocTypeVCL(ValueChangeEvent vce) {
       adflog.info("new value doc ---------"+vce.getNewValue());
       if(vce.getNewValue()!=null){
       Integer docIdtype = Integer.parseInt(vce.getNewValue().toString());
       if(docIdtype==330){
           OperationBinding autoIssu = getBindings().getOperationBinding("orgIdSet");
           autoIssu.execute();
       }
    }
    }

    public String deleteSrNoAction() {
        adflog.info("delete Sr no. bean");
        OperationBinding deleteSrNo = getBindings().getOperationBinding("deleteSerialNo");
        deleteSrNo.execute();
        return null;
    }

    public void deleteDocumentNo(ActionEvent actionEvent) {
       
       
           }

    public void deleleDocNoDialogListener(DialogEvent dialogEvent) {
        if(docIdSrcDispBind.getValue()!=null){
        String docSrcDisp = docIdSrcDispBind.getValue().toString();
        adflog.info("docSrcDisp ----> "+docSrcDisp);
        }
        adflog.info("deleleDocNoDialogListener---------");
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
            OperationBinding deleteDocNo = getBindings().getOperationBinding("deleteDocumentNo");
            deleteDocNo.execute();
        }
    }

    public void setDocNoDeletePopup(RichPopup docNoDeletePopup) {
        this.docNoDeletePopup = docNoDeletePopup;
    }

    public RichPopup getDocNoDeletePopup() {
        return docNoDeletePopup;
    }

    public void setDocIdSrcDispBind(RichInputText docIdSrcDispBind) {
        this.docIdSrcDispBind = docIdSrcDispBind;
    }

    public RichInputText getDocIdSrcDispBind() {
        return docIdSrcDispBind;
    }

    public void setSrcTableBind(RichTable srcTableBind) {
        this.srcTableBind = srcTableBind;
    }

    public RichTable getSrcTableBind() {
        return srcTableBind;
    }

    public String deleteDocNoAction() {
        showPopup(docNoDeletePopup, true);
        Integer tableCount = srcTableBind.getRowCount();
        adflog.info("table count ----->  "+tableCount);
        return null;
    }

    public String deleteDocNoPopup() {
        String ret = null;
        OperationBinding deleteDocNo = getBindings().getOperationBinding("deleteDocumentNo");
        deleteDocNo.execute();
        Integer tableCount = srcTableBind.getRowCount();
        adflog.info("table count ----->  "+tableCount);
        docNoDeletePopup.hide();
        /* if(tableCount==0){
        OperationBinding rollbk = getBindings().getOperationBinding("Rollback");
        rollbk.execute();
        setMode(" ");
        ret = "back";
        } */
       
        return ret;
    }

    public String deleteNoButtonPopAction() {
        Integer tableCount = srcTableBind.getRowCount();
        adflog.info("table count ----->  "+tableCount);
        docNoDeletePopup.hide();
        
        return null;
    }

    public void setWhIdPageBind(RichSelectOneChoice whIdPageBind) {
        this.whIdPageBind = whIdPageBind;
    }

    public RichSelectOneChoice getWhIdPageBind() {
        return whIdPageBind;
    }

    public void setReqAreaBind(RichSelectOneChoice reqAreaBind) {
        this.reqAreaBind = reqAreaBind;
    }

    public RichSelectOneChoice getReqAreaBind() {
        return reqAreaBind;
    }

    public void setSrcOrgBind(RichSelectOneChoice srcOrgBind) {
        this.srcOrgBind = srcOrgBind;
    }

    public RichSelectOneChoice getSrcOrgBind() {
        return srcOrgBind;
    }

    public void setSrcWhBind(RichSelectOneChoice srcWhBind) {
        this.srcWhBind = srcWhBind;
    }

    public RichSelectOneChoice getSrcWhBind() {
        return srcWhBind;
    }
/**
     *
     * @param precision
     * @param scale
     * @param total
     * @return
     */

    public Boolean isPrecisionValid(Integer precision,Integer scale,Number total){
                      JboPrecisionScaleValidator vc=new JboPrecisionScaleValidator();
                      vc.setPrecision(precision);
                      vc.setScale(scale);
                      return vc.validateValue(total);
           }
    public void issuBinQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number)object;
        if(value!=null){
        Boolean flag = isPrecisionValid(26, 6, value);
        adflog.info("Bin Validator inside "+value);
        adflog.info("Total Available Qty ---"+totalAvlQty.getValue());
        Number totalAvl = (Number)totalAvlQty.getValue();
        if(value.compareTo(zero)==-1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.715']}").toString(), null)); //Issue Quantity must be positive value.
            
        }else if(value.compareTo(totalAvl)==1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Issue Quantity can not more than Total Available Quantity ",null));
            
        }else if(flag.equals(false)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.405']}").toString(),
                                                          null)); // Invalid Precision scale (26,6)
        }
        }
            
    }

    public void issueLotQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number)object;
        if(value!=null){
        Boolean flag = isPrecisionValid(26, 6, value);
        if(value.compareTo(zero)==-1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.715']}").toString(), null)); //Issue Quantity must be positive value.
            
        }else if(flag.equals(false)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                          null)); // Invalid Precision scale (26,6)
        }
        }
    }

    public void setLotBinTableBind(RichTable lotBinTableBind) {
        this.lotBinTableBind = lotBinTableBind;
    }

    public RichTable getLotBinTableBind() {
        return lotBinTableBind;
    }

    public void setLotTableBind(RichTable lotTableBind) {
        this.lotTableBind = lotTableBind;
    }

    public RichTable getLotTableBind() {
        return lotTableBind;
    }

    public void setRturnDtBind(RichInputDate rturnDtBind) {
        this.rturnDtBind = rturnDtBind;
    }

    public RichInputDate getRturnDtBind() {
        return rturnDtBind;
    }
    public Date getIssueDate(){
        //oracle.jbo.domain.Date dt = (oracle.jbo.domain.Date)issueDateBind.getValue();
       Date Dt =null;
       //Timestamp dt =(Timestamp)issueDateBind.getValue();
       
       try{
           Timestamp dtt =(Timestamp)issueDateBind.getValue();
          Dt= new Date(dtt.dateValue());
          
       } catch (SQLException e) {
           e.printStackTrace();
        }
        adflog.info("Date ------  "+Dt);
        return Dt;
    }

    public void setTotalAvlQty(RichInputText totalAvlQty) {
        this.totalAvlQty = totalAvlQty;
    }

    public RichInputText getTotalAvlQty() {
        return totalAvlQty;
    }
}
