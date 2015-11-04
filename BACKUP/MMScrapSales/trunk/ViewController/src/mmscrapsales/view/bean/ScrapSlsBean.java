package mmscrapsales.view.bean;

import java.math.BigDecimal;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

public class ScrapSlsBean {
    private RichInputListOfValues itmNameBind;
    private RichInputText scrapQtyBind;
    private RichPopup stkPopupBind;
    private RichInputText avlScpStkbind;
    private RichTable lotTblBind;
    private RichTable srTblBind;
    private UIXSwitcher afSwitcherBind;
    private RichTable itmTabBind;
    private RichSelectBooleanCheckbox selectAllBind;
    private RichCommandImageLink tranItmNameBind;
    private String mode1="D";
    private RichCommandImageLink addItemBtnBind;
    private RichSelectBooleanCheckbox selectSrBind;

    public ScrapSlsBean() {
    }
    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
    }
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
        RequestContext.getCurrentInstance().getPageFlowScope().    put("mode", "C");
       System.out.println("now the mode value us "+resolvEl("mode"));
        // Add event code here...
       
        return "createscrp";
    }

    public void editActionListner(ActionEvent actionEvent) {
        // Add event code here...
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer usr_Id=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer userId=0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId",sloc_Id);
        check.getParamsMap().put("CldId",cld_Id);
        check.getParamsMap().put("OrgId",org_Id);
        check.getParamsMap().put("DocNo",18508);
        check.execute();
        
        if(check.getErrors().isEmpty()){
            userId=Integer.parseInt(check.getResult().toString());
            System.out.println("userId function "+userId+" "+userId.compareTo(usr_Id));
        }
        if(userId.compareTo(new Integer(-1))==0 || userId.compareTo(usr_Id)==0){
        System.out.println("user_ id" +userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
            System.out.println("now the mode value us "+resolvEl("mode"));   
        }
        else{
            String name="Anonymous";
            OperationBinding getusrname=getBindings().getOperationBinding("getUsrNm");
                 getusrname.getParamsMap().put("usrId", userId);
                 getusrname.execute();
                 if(getusrname.getErrors().size()==0 && getusrname.getResult()!=null)
                     name = (String)getusrname.getResult();
            System.out.println("now the mode value us "+resolvEl("mode")); 
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                 String msg="This Scrap Sales is Pending at ["+name+"]. You can't Edit this Scrap Sales.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        System.out.println("now the mode value us "+resolvEl("mode"));   
    }

    public void saveActionListner(ActionEvent actionEvent) {
        // Add event code here...
        // Add event code here...
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
   
            //  setMode("A");
            OperationBinding saveOp = getBindings().getOperationBinding("checkSaveRecord");
            saveOp.execute();
            String result = null;
            if (saveOp.getErrors().isEmpty()) {
                result = saveOp.getResult().toString();
            }
            if ("Y".equalsIgnoreCase(result)) {
                System.out.println("save and fwd scrps sls id");
                OperationBinding Obind1 = getBindings().getOperationBinding("genscrapId");
                Obind1.execute();

                String action = "I";
                String remark = "A";
                String WfNum = null;
                Integer level = 0;
                Integer res = -1;
                Integer pending = 0;

                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("SlocId", sloc_Id);
                WfnoOp.getParamsMap().put("CldId", cld_Id);
                WfnoOp.getParamsMap().put("OrgId", org_Id);
                WfnoOp.getParamsMap().put("DocNo", 18508);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    if (WfnoOp.getResult() != null)
                        WfNum = WfnoOp.getResult().toString();
                    System.out.println("WfnoOp : " + WfNum);
                }

                if (WfNum != null) {
                    //get user level
                    OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                    usrLevelOp.getParamsMap().put("CldId", cld_Id);
                    usrLevelOp.getParamsMap().put("OrgId", org_Id);
                    usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                    usrLevelOp.getParamsMap().put("WfNo", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 18508);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                        System.out.println("user level " + level);
                    }

                    if (level >= 0) {
                        //insert into txn
                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                        insTxnOp.getParamsMap().put("CldId", cld_Id);
                        insTxnOp.getParamsMap().put("OrgId", org_Id);
                        insTxnOp.getParamsMap().put("DocNo", 18508);
                        insTxnOp.getParamsMap().put("WfNo", WfNum);
                        insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                        insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                            System.out.println("ins ito txn" + res);
                            OperationBinding saveOp1 = getBindings().getOperationBinding("Commit");
                            saveOp1.execute();
                            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                        }

                    } else {
                        showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                         false, "F", null);
                        //       return null;
                    }
                } else {
                    showFacesMessage("Workflow not Created for Scrap Sales", "E", false, "F", null);
                    //         return null;
                }
            }

      

    }

    public void saveNFwdActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
        
        
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        // Add event code here...
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
        OperationBinding cncl = getBindings().getOperationBinding("Rollback");
        cncl.execute();
    }

    public void addItemActionListener(ActionEvent actionEvent) {
        // Add event code here...
        String flag=null;
        OperationBinding obItmchk = getBindings().getOperationBinding("itmValidChk");
        obItmchk.execute();
        System.out.println("itm get erros "+obItmchk.getErrors());
        if(obItmchk.getErrors().isEmpty()){
           flag= obItmchk.getResult().toString();
           }
        System.out.println("flag value in bean is "+flag);
        if(flag.equalsIgnoreCase("Y")){
        OperationBinding saveOp = getBindings().getOperationBinding("addScrpItmRcrd");
        saveOp.execute();
           }else if(flag.equalsIgnoreCase("N")) {
            FacesMessage message = new FacesMessage("Item Already Exists");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmNameBind.getClientId(), message);
        }
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ResetFieldValues").execute();

        itmNameBind.resetValue();
        itmNameBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBind);
        
        scrapQtyBind.resetValue();
        scrapQtyBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(scrapQtyBind);


    }

    public void setItmNameBind(RichInputListOfValues itmNameBind) {
        this.itmNameBind = itmNameBind;
    }

    public RichInputListOfValues getItmNameBind() {
        return itmNameBind;
    }

    public void setScrapQtyBind(RichInputText scrapQtyBind) {
        this.scrapQtyBind = scrapQtyBind;
    }

    public RichInputText getScrapQtyBind() {
        return scrapQtyBind;
    }

    public void deleteItmActionListner(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding obindDelte = getBindings().getOperationBinding("deleteItmWithRecord");
        obindDelte.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTabBind);
        
        
    }

    public void itmQtyBsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null){
            Number i1=(Number)object;
            Number zero=new Number(0);
            if(i1.compareTo(zero)==0 || i1.compareTo(zero)==-1){
                String error="Value must be greater than zero";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            Number i=(Number)object;
            boolean flag= isPrecisionValid(26, 6, i);
            if(flag){
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
            
            OperationBinding inptvalid = getBindings().getOperationBinding("itmQtyBaseValid");
            inptvalid.getParamsMap().put("itmBsQty", i1);
            inptvalid.execute();
            String temp=null;
            if(inptvalid.getErrors()!=null){
               temp=inptvalid.getResult().toString();
            }
            if("Y".equalsIgnoreCase(temp)){
            }else if("N".equalsIgnoreCase(temp)){
                String error1="Quantity must be less than or equal to available Quantity";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error1, null));
                
            }
        }
        
    }
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
    JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
    vc.setPrecision(precision);
    vc.setScale(scale);
    return vc.validateValue(total);
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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

    public void lotSerialActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding saveOp = getBindings().getOperationBinding("filterLotorSr");
        saveOp.execute();
        
        OperationBinding obindsetQty = getBindings().getOperationBinding("setTransQuantity");
        obindsetQty.execute();
        
        OperationBinding obindsrQty = getBindings().getOperationBinding("setsrQuantity");
        obindsrQty.execute();
        
        //stkPopupBind
        
        showPopup(stkPopupBind, true);
        
    }

    public void setStkPopupBind(RichPopup stkPopupBind) {
        this.stkPopupBind = stkPopupBind;
    }

    public RichPopup getStkPopupBind() {
        return stkPopupBind;
    }

    public void lotQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null){
            Number i1=(Number)object;
            Number zero=new Number(0);
            if(i1.compareTo(zero)==-1){
                String error="Value must be greater than zero";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            boolean flag= isPrecisionValid(26, 6, i1);
            if(flag){
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6)", null));
            }
            Number avlStk=new Number(0);
            System.out.println("avilable stk quantity is "+avlScpStkbind.getValue());
            if(avlScpStkbind.getValue()!=null && avlScpStkbind.getValue()!=""){
                avlStk=(Number)avlScpStkbind.getValue();
            }
            if(i1.compareTo(avlStk)==0 || i1.compareTo(avlStk)==-1){
            }else if(i1.compareTo(avlStk)==1){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock Not Available in selected lot", null));
                   
            }
            
            
        }
    }

    public void setAvlScpStkbind(RichInputText avlScpStkbind) {
        this.avlScpStkbind = avlScpStkbind;
    }

    public RichInputText getAvlScpStkbind() {
        return avlScpStkbind;
    }

    public void lotSrDialogListner(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding obind = getBindings().getOperationBinding("validQuantityCheck");
            obind.execute();
            
           
            
            String temp = null;
            if (obind.getErrors().isEmpty()) {
                temp = obind.getResult().toString();
            }
            if ("Y".equalsIgnoreCase(temp)) {
                OperationBinding obind1 = getBindings().getOperationBinding("inserSrStock");
                obind1.execute();
                
                OperationBinding obindIsrt = getBindings().getOperationBinding("insrtScrpStk");
                obindIsrt.execute();
            
//                OperationBinding obind2 = getBindings().getOperationBinding("insrtScrpStk");
//         obind2.execute();
               
               //postchanges
               OperationBinding postchanges = getBindings().getOperationBinding("postchanges");
               postchanges.execute();
                
                
                         
            } else if ("N".equalsIgnoreCase(temp)) {
                FacesMessage message = new FacesMessage("Total Quantity must be less than given quantity");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }

    public void lotDeleteActionlistener(ActionEvent actionEvent) {
        // Add event code here...
        //Deletelotrecord
        OperationBinding obind = getBindings().getOperationBinding("Deletelotrecord");
        obind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(afSwitcherBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTabBind);
         
    }

    public void srDeleteActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding obind = getBindings().getOperationBinding("DeleteSrno");
        obind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(afSwitcherBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTabBind);
        
    }

    public void setLotTblBind(RichTable lotTblBind) {
        this.lotTblBind = lotTblBind;
    }

    public RichTable getLotTblBind() {
        return lotTblBind;
    }

    public void setSrTblBind(RichTable srTblBind) {
        this.srTblBind = srTblBind;
    }

    public RichTable getSrTblBind() {
        return srTblBind;
    }

    public void setAfSwitcherBind(UIXSwitcher afSwitcherBind) {
        this.afSwitcherBind = afSwitcherBind;
    }

    public UIXSwitcher getAfSwitcherBind() {
        return afSwitcherBind;
    }

    public void setItmTabBind(RichTable itmTabBind) {
        this.itmTabBind = itmTabBind;
    }

    public RichTable getItmTabBind() {
        return itmTabBind;
    }

    public void allItemAddAction(ActionEvent actionEvent) {
        // Add event code here...
        if(selectAllBind.getValue()!=null && selectAllBind.getValue()!=""){
            String chck=selectAllBind.getValue().toString();
            if(chck.equalsIgnoreCase("true")){
                OperationBinding obind = getBindings().getOperationBinding("selectAllItem");
                obind.execute();
            }
        }
    }

    public void allChkvaluechange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //itmNameBind
        //scrapQtyBind
        if(valueChangeEvent.getNewValue()!=null){
            String value=valueChangeEvent.getNewValue().toString();
            if("true".equalsIgnoreCase(value)){
                itmNameBind.setValue(null);
                scrapQtyBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(scrapQtyBind);
             }
        }
    }

    public void setSelectAllBind(RichSelectBooleanCheckbox selectAllBind) {
        this.selectAllBind = selectAllBind;
    }

    public RichSelectBooleanCheckbox getSelectAllBind() {
        return selectAllBind;
    }

    public void autoStkActionListener(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("autoStkAllotment");
        obind.execute();
        //postchanges
       /*  OperationBinding obind1 = getBindings().getOperationBinding("postchanges");
        obind1.execute(); */
        // Add event code here...
    }

    public void setTranItmNameBind(RichCommandImageLink tranItmNameBind) {
        this.tranItmNameBind = tranItmNameBind;
    }

    public RichCommandImageLink getTranItmNameBind() {
        return tranItmNameBind;
    }

    public String saveNFwdAction() {
        // Add event code here...
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer usr_Id=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            Integer userId=0;
            OperationBinding check = getBindings().getOperationBinding("pendingCheck");
            check.getParamsMap().put("SlocId",sloc_Id);
            check.getParamsMap().put("CldId",cld_Id);
            check.getParamsMap().put("OrgId",org_Id);
            check.getParamsMap().put("DocNo",18508);
            check.execute();
            
            if(check.getErrors().isEmpty()){
                userId=Integer.parseInt(check.getResult().toString());
                System.out.println("userId function "+userId);
            }
        if(userId.compareTo(new Integer(-1))==0 || userId.compareTo(usr_Id)==0){
            System.out.println("user_ id" +userId);
              //  setMode("A");     
            OperationBinding saveOp = getBindings().getOperationBinding("checkSaveRecord");
            saveOp.execute();
            String result=null;
            if(saveOp.getErrors().isEmpty()){
            result=saveOp.getResult().toString();
            }
            if("Y".equalsIgnoreCase(result)){
            System.out.println("save and fwd scrps sls id");
                OperationBinding Obind1 = getBindings().getOperationBinding("genscrapId");
                Obind1.execute();
               
            String action = "I";
            String remark = "A";
            String WfNum=null;
            Integer level =0;
            Integer res =-1;
            Integer pending=0;
            
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                        WfnoOp.getParamsMap().put("SlocId", sloc_Id);
                                        WfnoOp.getParamsMap().put("CldId", cld_Id);
                                        WfnoOp.getParamsMap().put("OrgId", org_Id);
                                        WfnoOp.getParamsMap().put("DocNo", 18508);
                                        WfnoOp.execute();
                       if(WfnoOp.getResult()!=null){
                           if(WfnoOp.getResult()!=null)
                          WfNum= WfnoOp.getResult().toString();
                          System.out.println("WfnoOp : "+WfNum);
                       }

                       if(WfNum!=null&&(!WfNum.equals("0"))){
                  //get user level
                       OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                                        usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                                        usrLevelOp.getParamsMap().put("CldId", cld_Id);
                                        usrLevelOp.getParamsMap().put("OrgId", org_Id);
                                        usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                                        usrLevelOp.getParamsMap().put("WfNo", WfNum);
                                        usrLevelOp.getParamsMap().put("DocNo", 18508);
                                        usrLevelOp.execute();
                                        level=-1;
                       if(usrLevelOp.getResult()!=null){
                          level= Integer.parseInt(usrLevelOp.getResult().toString());
                          System.out.println("user level "+level);
                       }

                       if(level>=0){
                        //insert into txn
                       OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                      insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                                      insTxnOp.getParamsMap().put("CldId", cld_Id);
                                      insTxnOp.getParamsMap().put("OrgId", org_Id);
                                      insTxnOp.getParamsMap().put("DocNo", 18508);
                                      insTxnOp.getParamsMap().put("WfNo", WfNum);
                       insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                       insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                       insTxnOp.getParamsMap().put("levelFrm", level);
                       insTxnOp.getParamsMap().put("levelTo", level);
                       insTxnOp.getParamsMap().put("action", action);
                       insTxnOp.getParamsMap().put("remark", remark);
                       insTxnOp.getParamsMap().put("amount", 0);
                       insTxnOp.execute();
                       
                       if(insTxnOp.getResult()!=null){
                          res= Integer.parseInt(insTxnOp.getResult().toString());
                          System.out.println("ins ito txn"+res);
                       }      

        
                    //Check Pending
                    OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                                              chkUsr.getParamsMap().put("SlocId", sloc_Id);
                                              chkUsr.getParamsMap().put("CldId", cld_Id);
                                              chkUsr.getParamsMap().put("OrgId", org_Id);
                                              chkUsr.getParamsMap().put("DocNo", 18508);
                                              chkUsr.execute();
                                             
                                             if(chkUsr.getResult()!=null){
                                                pending= Integer.parseInt(chkUsr.getResult().toString());
                                                System.out.println("pending check"+pending);
                                             } 
                                            if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(usr_Id)==0) 
                                            {
                                                OperationBinding saveOp1 = getBindings().getOperationBinding("Commit");
                                                saveOp1.execute();
                                                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                                //showFacesMessage("MRN No "+ids+" Save Successfully", "I", false, "F", null);
                                            //no pending
                                            return "towf";
                                            }
                                             else {//pending
                                             return null;
                                             }
                       }
                       else
                       {
                               showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E", false, "F", null);
                               return null;
                           }
             }
                       else
                       {
                              showFacesMessage("Workflow not Created for Scrap Sales", "E", false, "F", null);
                               return null;
                           }
            }else{
                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
                return null;
            }
        
        }
        else{
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String)getusrname.getResult();
            String msg = "This Scrap Sales is Pending at [" + name + "]. You can't forward this Scrap Sales.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        // return null;
        

    }

    public void ScrpPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        System.out.println("Value of Scrap price:"+object);
        if(new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Price Must be Greater than Zero",null));
        }
        
    }

    public void CustomerNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("In CustomerNmVCL");
           this.mode1="E";
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectAllBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addItemBtnBind);
    }
    
    

//    public void setMode(String mode) {
//        this.mode = mode;
//    }
//
//    public String getMode() {
//        return mode;
//    }

    public void setMode1(String mode1) {
        this.mode1 = mode1;
    }

    public String getMode1() {
        return mode1;
    }

    public void setAddItemBtnBind(RichCommandImageLink addItemBtnBind) {
        this.addItemBtnBind = addItemBtnBind;
    }

    public RichCommandImageLink getAddItemBtnBind() {
        return addItemBtnBind;
    }

    public void setSelectSrBind(RichSelectBooleanCheckbox selectSrBind) {
        this.selectSrBind = selectSrBind;
    }

    public RichSelectBooleanCheckbox getSelectSrBind() {
        return selectSrBind;
    }
}
