package mmkitproduction.view.beans;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;

import oracle.adf.view.rich.component.rich.RichDialog;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.component.rich.output.RichMessage;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.domain.Number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mmkitproduction.model.services.MMKitProductionAMImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.util.Service;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
//import org.apache.myfaces.trinidadinternal.taglib.listener.ResetActionListener;

public class kitbean {
    private RichSelectOneChoice optitmid;
    private RichSelectOneChoice whIdBind;
    private RichInputText inputQtyVal;
    private List selectedUser;
    private RichPopup mmKitPropPop;
    private RichSelectOneRadio kitActionBind;
    private RichInputText lotTransBind;
  //  private RichSelectOneChoice poPLotIdBind;
    private RichPopup inptSelectPop;
    private RichSelectOneChoice inputItmBinding;
    
    String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    String BinChk=resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
    String WhChk=resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}").toString();
    private RichPanelGroupLayout popUp1PflBInd;
    private RichInputText itmStktotalQty;
    private RichPopup pop3deassemble;
    private RichSelectOneChoice pop2lotidbind;
    private RichSelectOneChoice pop2lotIdBind;
    private RichSelectOneChoice pop2BinIdBind;
    private RichSelectManyShuttle shutlebind;
    private RichSelectOneChoice pop2srBinding;
    private RichInputText pop2ScrpBind;
    private RichInputText pop2rwQty;
    private RichCommandButton addbtnBinding;
    private RichCommandButton stkAltbinding;
    private RichCommandButton deasmblBinding;
    private RichMessage pop2msgbind;
    private RichSelectBooleanCheckbox chTransBind;
    private RichPopup delpopup;
    private RichInputListOfValues optitmBind;
    private RichTable prodStkTbBind;
    private RichMessage sfMessageBind;
    private  String b1 = "";
    private RichSelectOneChoice pop1BinBind;
    private RichInputText pop1SrBind;
    private RichInputText pop1totQtyBind;
    private RichSelectOneChoice pop2lotIdTransBinding;
    private RichPopup pop4dismental;
    private RichInputText lotChkBinding;
    private RichSelectOneChoice optItmUOM;
    private RichInputText optitmtransIdTextBind;
    private RichInputText pop2okqty;
    private RichInputText pop5binTotQtyBind;
    private RichInputText pop5lotTotBind;
    private RichOutputText pop5bintotstk;
    private RichInputText dsmntLotQtyBind;


    public kitbean() {
    }
    String optserialized="Y";
    String mode="Y";
    String Pmode="Y";
    String p2mode="Y";
    String p2srflg="";
    String opsrflg="";
    String addmode="";
    String CEmode="";
    String desmode="";
    String delDsbl="Y";
    public void createBtn(ActionEvent actionEvent) {
        //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert")
        //.execute();
    }

    public void OPTUOMChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("UOMValue");
        operationBinding.getParamsMap().put("itmname", "1");
        operationBinding.execute();
       /*  if(getInputQtyVal().getValue()!=null) {
             setInputQtyVal(null);
         } */
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setOptitmid(RichSelectOneChoice optitmid) {
        this.optitmid = optitmid;
    }

    public RichSelectOneChoice getOptitmid() {
        return optitmid;
    }

   
    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

/*  
 cldid,slocid,orgid,userid
 */
    public void AddAction(ActionEvent actionEvent) {
        Number instqty=new Number(0);
        String optid="";
        
        OperationBinding bindStk = getBindings().getOperationBinding("prodCheckWithStk");
        bindStk.execute();
        String flag="";
        if(bindStk.getErrors().isEmpty()){
            flag=bindStk.getResult().toString();
        }
        if("Y".equalsIgnoreCase(flag)){ 
        if(getInputQtyVal().getValue()!=null && getInputQtyVal().getValue()!="" && getOptitmid().getValue()!=null &&getOptitmid().getValue()!="")
        {
           instqty=(Number)getInputQtyVal().getValue();
           optid=getOptitmid().getValue().toString();
        }
        OperationBinding Obinding = getBindings().getOperationBinding("inputItemGen");
        Obinding.getParamsMap().put("optId", optid);
        Obinding.getParamsMap().put("instqty", instqty);
        Obinding.execute();
        addmode="N";
         }
        else{
            FacesMessage message = new FacesMessage("Output Quantity is not equals to Stock Quantity");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        
    }

    public void setWhIdBind(RichSelectOneChoice whIdBind) {
        this.whIdBind = whIdBind;
    }

    public RichSelectOneChoice getWhIdBind() {
        return whIdBind;
    }

    public void setInputQtyVal(RichInputText inputQtyVal) {
        this.inputQtyVal = inputQtyVal;
    }

    public RichInputText getInputQtyVal() {
        return inputQtyVal;
    }

    public void StockAdjustAction(ActionEvent actionEvent)
    {
       
        if(getKitActionBind().getValue()!=null){
            if(getKitActionBind().getValue().toString().equalsIgnoreCase("A"))
            {
                OperationBinding Obinding = getBindings().getOperationBinding("optSerialized");
                Obinding.execute();
                String flag=Obinding.getResult().toString();
                if(flag.equalsIgnoreCase("N")){
                    opsrflg="N";
                }
                else if(flag.equalsIgnoreCase("Y")){
                    opsrflg="Y";
                }
                showPopup(mmKitPropPop, true);
            }
            else if(getKitActionBind().getValue().toString().equalsIgnoreCase("D"))
            {
            }
        }
    }
    
    private void showPopup(RichPopup pop, boolean visible)
    {
      try 
      {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && pop != null) 
        {
            String popupId = pop.getClientId(context);
            if (popupId != null) 
            {
                StringBuilder script = new StringBuilder();
                script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                if (visible){
                   script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                }  
                else{
                    script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                }
            ExtendedRenderKitService erks =Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, script.toString());
        }
      }
    } 
      catch (Exception e) 
      {
           throw new RuntimeException(e);
      }
    }

    public void setMmKitPropPop(RichPopup mmKitPropPop) {
        this.mmKitPropPop = mmKitPropPop;
    }

    public RichPopup getMmKitPropPop() {
        return mmKitPropPop;
    }
    public Object resolvElDCMsg(String data) {
          FacesContext fc = FacesContext.getCurrentInstance();
          Application app = fc.getApplication();
          ExpressionFactory elFactory = app.getExpressionFactory();
          ELContext elContext = fc.getELContext();
          ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
          return valueExp.getValue(elContext);
      }
    public void QtyOptValid(FacesContext facesContext, UIComponent uIComponent, Object object) 
    {
        optItmUOM.processUpdates(facesContext.getCurrentInstance());
        getOptitmBind().processUpdates(facesContext.getCurrentInstance());
        optitmtransIdTextBind.processUpdates(facesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getOptitmBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(optItmUOM);
        AdfFacesContext.getCurrentInstance().addPartialTarget(optitmtransIdTextBind);
        if(object!=null){
            Number i=(Number)object;
           boolean flag= isPrecisionValid(26, 6, i);
            if(flag){
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
            
        }
        //inputqtyval binding of opt qty field
       
       
        if(object!=null && object !="" && getKitActionBind().getValue()!=null && getKitActionBind().getValue()!="")
        {//System.out.println("output Quantity object "+object);
            Number i=(Number)object;
            if(i.compareTo(0)==-1 || i.compareTo(0)==0)
            {
                String msg="Value Must Be Greater Then Zero";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            else{
           String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
           Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
           String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            
            String whid="";
                if(getWhIdBind().getValue()!=null && getWhIdBind().getValue()!=""){
                whid=getWhIdBind().getValue().toString();
              //getOptitmBind().processUpdates(FacesContext.getCurrentInstance());
               getOptitmid().processUpdates(FacesContext.getCurrentInstance());
               getWhIdBind().processUpdates(FacesContext.getCurrentInstance());
               AdfFacesContext.getCurrentInstance().addPartialTarget(getWhIdBind());
                 //  System.out.println("opt item id in bean ware house "+ getOptitmid().getValue()+" TRANS ITEM IS "+getOptitmBind().getValue());
               }if(getOptitmid().getValue()!=null &&getOptitmid().getValue()!="" && getOptitmBind().getValue()!=null && getOptitmBind().getValue()!=""){
                  
                   getOptitmid().processUpdates(FacesContext.getCurrentInstance());
                   getWhIdBind().processUpdates(FacesContext.getCurrentInstance());
                 //  System.out.println("opt item id in bean "+ getOptitmid().getValue()+" TRANS ITEM IS "+getOptitmBind().getValue());
               }
           
           OperationBinding Operationbind= getBindings().getOperationBinding("ItemQuantitychecked1");
           Operationbind.getParamsMap().put("CldId", p_cldId);
           Operationbind.getParamsMap().put("SlocId", p_sloc_id);
           Operationbind.getParamsMap().put("OrgId", p_org_id);
           Operationbind.getParamsMap().put("Wh_id",whid);
           Operationbind.getParamsMap().put("itm_id","1");
           Operationbind.getParamsMap().put("qty",(Number)object );
           Operationbind.getParamsMap().put("a_op", getKitActionBind().getValue().toString());
           Operationbind.execute();
            Number itemAvail=new Number(0);
                
                if(Operationbind.getResult()!=null){     
           itemAvail=(Number)Operationbind.getResult();
           if(itemAvail.compareTo(new Number(1))==0) {
               //getOptitmid().processUpdates(FacesContext.getCurrentInstance());
               /* if(getOptitmid().getValue()!=null &&getOptitmid().getValue()!=""){
               OperationBinding optsetval= getBindings().getOperationBinding("setoptItm");
               optsetval.getParamsMap().put("optItmId", getOptitmid().getValue().toString());
               optsetval.execute();
               } */
           }
           else{
             
               String msg="Stock is Not Available";
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
           }
                }
        }
       }
    }
    public void setKitActionBind(RichSelectOneRadio kitActionBind) {
        this.kitActionBind = kitActionBind;
    }

    public RichSelectOneRadio getKitActionBind() {
        return kitActionBind;
    }

    public void NewLotAction(ActionEvent actionEvent) {
       mode="N";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setLotTransBind(RichInputText lotTransBind) {
        this.lotTransBind = lotTransBind;
    }

    public RichInputText getLotTransBind() {
        return lotTransBind;
    }

    public void PopAdd(ActionEvent actionEvent) 
    {
        mode = "Y";
        // Add event code here...
        OperationBinding oplotcheck = getBindings().getOperationBinding("pop1CheckNull");
        oplotcheck.execute();
        String flag = oplotcheck.getResult().toString();
        if (flag.equalsIgnoreCase("Y")) {
            if (getInputQtyVal().getValue() != null && getInputQtyVal().getValue() != "") {
                Number param = (Number)getInputQtyVal().getValue();
                OperationBinding Obind = getBindings().getOperationBinding("rowCount");
                Obind.getParamsMap().put("optCnt", param);
                Obind.execute();
               
                if (Obind != null) {
                    b1 = Obind.getResult().toString();
                }
                if (b1.equalsIgnoreCase("Y")) {
                    OperationBinding Obind1 = getBindings().getOperationBinding("CreateInsert5");
                    Obind1.execute();
                    if (Obind1.getErrors().isEmpty()) {
                        if(lotChkBinding.getValue()!=null && lotChkBinding.getValue()!=""){
                            if("Y".equalsIgnoreCase(lotChkBinding.getValue().toString())){
                           OperationBinding chklot = getBindings().getOperationBinding("LotsetttedByFunction");
                           chklot.execute();
                       }
                        }
                        if(BinChk.equalsIgnoreCase("N")){
                            OperationBinding optot = getBindings().getOperationBinding("pop1binsetval");
                            optot.execute();
                        }
                        if (opsrflg.equalsIgnoreCase("Y")) {
                            OperationBinding optot = getBindings().getOperationBinding("optotQtySet");
                            optot.execute();
                        }else if(opsrflg.equalsIgnoreCase("N")){
                            OperationBinding optot = getBindings().getOperationBinding("pop1srsetvalue");
                            optot.execute();
                        }
                    }
                } else if(b1.equalsIgnoreCase("P")){
                    Pmode = "N";
                   // String msg="Quantity is not Equals to output item Quantity";
                   // FacesMessage message = new FacesMessage("Quantity is Equals to output item Quantity");
                    
                   
                }else if(b1.equalsIgnoreCase("N")){
                    Pmode = "N";
                    /* FacesMessage message = new FacesMessage("No More Output Item Generated");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                 */
                }
            }
        } else if (flag.equalsIgnoreCase("N")) {
            FacesMessage message = new FacesMessage("Lot Is Required");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(lotTransBind.getClientId(), message);
        }

        else {
        }
    }
  

   /*  public void setPoPLotIdBind(RichSelectOneChoice poPLotIdBind) {
        this.poPLotIdBind = poPLotIdBind;
    }

    public RichSelectOneChoice getPoPLotIdBind() {
        return poPLotIdBind;
    } */

    public void QTYVALCHANGE(ValueChangeEvent valueChangeEvent) {
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getOptitmid());
    }

    public void setPmode(String Pmode) {
        this.Pmode = Pmode;
    }

    public String getPmode() {
        return Pmode;
    }

    public void POPsave(ActionEvent actionEvent) {
    }

    public void LotTransValueChange(ValueChangeEvent valueChangeEvent) {
    if(lotChkBinding.getValue()!=null && lotChkBinding.getValue()!=""){
        if("N".equalsIgnoreCase(lotChkBinding.getValue().toString())){
            if(valueChangeEvent.getNewValue()!=null){
                  //  System.out.println("same lot value is "+valueChangeEvent.getNewValue());
             /*   if("N".equalsIgnoreCase(BinChk) && opsrflg.equalsIgnoreCase("N")){
                    System.out.println("same lot value is "+valueChangeEvent.getNewValue());
                    OperationBinding lotvalid =getBindings().getOperationBinding("pop1LotValid");
                    lotvalid.getParamsMap().put("lotName", valueChangeEvent.getNewValue().toString());
                    lotvalid.execute();
                     
                     if(lotvalid.getResult()!=null){
                       String flag=lotvalid.getResult().toString();
                       System.out.println("result of method is "+flag);
                       if(flag.equalsIgnoreCase("N")){
                           FacesMessage message = new FacesMessage("Select Different Lot");
                           message.setSeverity(FacesMessage.SEVERITY_ERROR);
                           FacesContext fc = FacesContext.getCurrentInstance();
                           fc.addMessage(lotTransBind.getClientId(), message);
                       }
                    }
                }  */
                String lotTrans=valueChangeEvent.getNewValue().toString();
                OperationBinding Obinding = getBindings().getOperationBinding("setTheLotid");
                Obinding.getParamsMap().put("LotTrans",lotTrans); 
                Obinding.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(pop1BinBind);
                
            }
         }
           // System.out.println("lot chk bind value is: "+lotChkBinding);
        }
     
    }

    public void SelectActionListen(ActionEvent actionEvent) 
    {
        
        p2mode="N";
     
        OperationBinding Obinding = getBindings().getOperationBinding("optSerialized");
        Obinding.execute();
        String flag1=Obinding.getResult().toString();
        if(flag1.equalsIgnoreCase("N")){
            opsrflg="N";
        }
        else if(flag1.equalsIgnoreCase("Y")){
            opsrflg="Y";
        }
        OperationBinding operationBinding1 =getBindings().getOperationBinding("checkProdStk");
        operationBinding1.execute();
        String flag=operationBinding1.getResult().toString();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("serialization");
        operationBinding.getParamsMap().put("inptid",getInputItmBinding());
        operationBinding.execute();
        String b= "false";
  
        if(operationBinding.getResult()!=null){
             b = operationBinding.getResult().toString();
        }
      
       
       if(b.equalsIgnoreCase("false")) {
           p2srflg="N";
         
       }
       else if(b.equalsIgnoreCase("true")){
           p2srflg="Y";
         
       }
       if(flag.equalsIgnoreCase("Y")){
           OperationBinding chkRecord =bindings.getOperationBinding("checkEmptyRecord");
           chkRecord.execute();
           String chkflag="";
           if(chkRecord.getResult()!=null) {
               chkflag=chkRecord.getResult().toString();
           }
           if("Y".equalsIgnoreCase(chkflag)){
               FacesMessage message = new FacesMessage("Record is Already Exists For This Input Item");
               message.setSeverity(FacesMessage.SEVERITY_INFO);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(null, message);
               
           }
           else if("N".equalsIgnoreCase(chkflag)){
           showPopup(inptSelectPop, true);
           addmode="N";
           }
       }
    }

    public void setInptSelectPop(RichPopup inptSelectPop) {
        this.inptSelectPop = inptSelectPop;
    }

    public RichPopup getInptSelectPop() {
        return inptSelectPop;
    }

    public void SaveAction(ActionEvent actionEvent) {
      
        String WhId="";
        String flag="";
        if(getWhIdBind().getValue()!=null)
        {
           WhId=getWhIdBind().getValue().toString();
        } 
        
       OperationBinding obind=getBindings().getOperationBinding("POPsave");
       obind.getParamsMap().put("WhId",WhId);
       obind.execute();
       if(obind.getResult()!=null){
           flag=obind.getResult().toString();
            if(flag.equalsIgnoreCase("Y"))
            {//function call this one //pstChangeForUpdate
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit1").execute();
                //commented becoz of only bin set to be zero
            
               OperationBinding obindStkUpd=getBindings().getOperationBinding("kitStockUpdate");
                obindStkUpd.execute();
                if(obindStkUpd.getErrors().isEmpty()) {
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit1").execute();
                    CEmode="S";
                    FacesMessage message = new FacesMessage("Saved Successfully");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                else{
                }
                
            }
            if(flag.equalsIgnoreCase("N"))
            {
                String error=resolvElDCMsg("some input item dsn't have value ").toString();
               // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
        }
    }

    public void LOTTransValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error=resolvElDCMsg("Special Character Not Allowed").toString();

        if (matcher.matches()) {
                           }
        else {
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
             }
       
        if(lotChkBinding.getValue()!=null && lotChkBinding.getValue()!=""){
            if("N".equalsIgnoreCase(lotChkBinding.getValue().toString())){
                if(object!=null){
                      //  System.out.println("same lot value is "+object);
                   if("N".equalsIgnoreCase(BinChk) && opsrflg.equalsIgnoreCase("N")){
                    //    System.out.println("same lot value is "+object);
                        OperationBinding lotvalid =getBindings().getOperationBinding("pop1LotValid");
                        lotvalid.getParamsMap().put("lotName", object);
                        lotvalid.execute();
                         
                         if(lotvalid.getResult()!=null){
                           String flag=lotvalid.getResult().toString();
                         //  System.out.println("result of method is "+flag);
                           if(flag.equalsIgnoreCase("N")){
                               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select Different Lot", null));
                           }
                        }
                    } 
                }
            }
        }
        
        if(object!=null){
            pop1BinBind.processUpdates(facesContext.getCurrentInstance());
            if(pop1BinBind.getValue()!=null && pop1BinBind.getValue()!=""){
            OperationBinding Obind=getBindings().getOperationBinding("islotbinduplicate");
            Obind.getParamsMap().put("lotName",object.toString());
            Obind.execute();
            String flag="";
            if(Obind.getResult()!=null){
                  flag=Obind.getResult().toString();
            }
            if(flag.equalsIgnoreCase("N")) {
            String msg=" Duplicate Record Found Select Different Lot Or Bin ";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
              
            }
             }
                
        }
    
    }

    public void PSRValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error=resolvElDCMsg("Special Character Not Allowed").toString();

        if (matcher.matches()) {
                           }
        else {
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
             }
        
        if(getOpsrflg()!=null && opsrflg.equalsIgnoreCase("Y")) {
          OperationBinding obind=getBindings().getOperationBinding("checkserialization");
            obind.getParamsMap().put("srno",object.toString());
            obind.execute(); 
            if(obind.getResult()!=null)
            {  
                lotTransBind.processUpdates(facesContext.getCurrentInstance());//
                lotTransBind.processUpdates(facesContext.getCurrentInstance());
                pop1BinBind.processUpdates(FacesContext.getCurrentInstance());
                String flag=obind.getResult().toString();
                if(flag.equalsIgnoreCase("N"))
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already Exist...!!!!", null));
                }
            }
        }
    }

    public void setInputItmBinding(RichSelectOneChoice inputItmBinding) {
        this.inputItmBinding = inputItmBinding;
    }

    public RichSelectOneChoice getInputItmBinding() {
        return inputItmBinding;
    }

    public void POP2addActionListner(ActionEvent actionEvent)
    {
        p2mode="Y";
        p2mode="P";
     //   System.out.println("Bean BHarat test 1 ----- ");
        OperationBinding opbind5 =getBindings().getOperationBinding("lovLotProdVwFilter");
        opbind5.execute();
       // ResetActionListener ral = new ResetActionListener();
         //  ral.processAction(actionEvent);//for reset the actiion vaslue on cancel action listner
    
        if(getInputQtyVal().getValue()!=null )
        {
       // System.out.println("Bean BHarat test 2 ----- ");
        String flag="Y";
            if(opsrflg.equalsIgnoreCase("Y")){
                if(p2srflg.equalsIgnoreCase("Y")){
            Number optQuantity=(Number)getInputQtyVal().getValue();
            OperationBinding opbind =getBindings().getOperationBinding("pop2addQuantitycheck");
            opbind.getParamsMap().put("optQty",optQuantity);
            opbind.execute();
            flag=opbind.getResult().toString();
                }
            }
            if(flag.equalsIgnoreCase("Y")){
                if(kitActionBind.getValue()!=null && kitActionBind.getValue()!=""){
                    if("N".equalsIgnoreCase(p2srflg)){
                    if("D".equalsIgnoreCase(kitActionBind.getValue().toString())){
                        OperationBinding tottrans =getBindings().getOperationBinding("totqtySet"); 
                        tottrans.execute();
                    }
                    }
                }
               // System.out.println("before create with param executes");
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding =bindings.getOperationBinding("Createwithparameters");
                operationBinding.execute();
               // System.out.println("create with param error is : "+operationBinding);
                if(operationBinding.getErrors().isEmpty())
                
                {if(p2srflg.equalsIgnoreCase("N")){
                     //   System.out.println("create with param is executes properly");
                     OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setserialno");
                     operationBinding2.getParamsMap().put("srTrans", "0");
                     operationBinding2.execute();
                    }
                   if(BinChk.equalsIgnoreCase("N")){
                       OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setbinck");
                       operationBinding2.getParamsMap().put("binTrans", "0");
                       operationBinding2.execute();
                   }
                pop2lotidbind.setValue("");
              //  pop2BinIdBind.setValue("");
                String WhId="";
                if(getWhIdBind().getValue()!=null){
                    WhId=getWhIdBind().getValue().toString();
                }
                OperationBinding Operationbind= getBindings().getOperationBinding("InputLotLOVFilteration");
                Operationbind.getParamsMap().put("CldId", CldId);
                Operationbind.getParamsMap().put("SlocId", SlocId);
                Operationbind.getParamsMap().put("OrgId", OrgId);
                Operationbind.getParamsMap().put("WhId", WhId);
                Operationbind.getParamsMap().put("ItmId", getInputItmBinding());
                Operationbind.execute();
                }else{
                }
            }
            else if(flag.equalsIgnoreCase("N")){
                FacesMessage message = new FacesMessage("Quantity not equals Input Item Quantity");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else if(flag.equalsIgnoreCase("P")){
                FacesMessage message = new FacesMessage("Serialize Item Contain Quantity equals 1");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
    }
      
    }

    public void setP2mode(String p2mode) {
        this.p2mode = p2mode;
    }

    public String getP2mode() {
        return p2mode;
    }

    public void pop1DialogListner(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("ok")){
             //
           OperationBinding oplotcheck=getBindings().getOperationBinding("pop1CheckNull");
           oplotcheck.execute();
           String flag=oplotcheck.getResult().toString();
           if(flag.equalsIgnoreCase("Y")){
               String chkstat="";
               OperationBinding chkzero = getBindings().getOperationBinding("prodStkchkZerorecord");
               chkzero.execute();
               if(chkzero.getResult()!=null){
                   chkstat=chkzero.getResult().toString();
                  // System.out.println("nw chk state is");
               }
               if("Y".equalsIgnoreCase(chkstat)){
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding =bindings.getOperationBinding("optitemCheckQuantityOK");
                     if(getInputQtyVal().getValue()!=null){
                        operationBinding.getParamsMap().put("optCnt", getInputQtyVal().getValue().toString());
                    }
                    operationBinding.execute();
                   /*  if(getOptitmid().getValue()!=null &&getOptitmid().getValue()!=""){
                    OperationBinding optsetval= getBindings().getOperationBinding("setoptItm");
                    optsetval.getParamsMap().put("optItmId", getOptitmid().getValue().toString());
                    optsetval.execute();
                    } */
                }
               else if("N".equalsIgnoreCase(chkstat)){
                   FacesMessage message = new FacesMessage("Some 0 Quantity record Found");
                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null, message); 
               }
           }
           
           else if(flag.equalsIgnoreCase("N")){
               FacesMessage message = new FacesMessage("Lot Is Required");
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(lotTransBind.getClientId(), message); //
           }
         }
    
    }

    public void pop1cancelListner(PopupCanceledEvent popupCanceledEvent) 
    {
     
      BindingContainer bindings = getBindings();
      OperationBinding operationBinding =bindings.getOperationBinding("Rowremove");
      operationBinding.execute();
      b1="";
      Pmode="Y";
        lotTransBind.setValue(null);//
        lotTransBind.resetValue();//
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTransBind);// 
    
        lotTransBind.setValue(null);
        lotTransBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTransBind); 
    
        pop1BinBind.setValue(null);
        pop1BinBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1BinBind); 
        
        pop1SrBind.setValue(null);
        pop1SrBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1SrBind); 
        //pop1totQtyBind
        pop1totQtyBind.setValue(null);
        pop1totQtyBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1totQtyBind); 
        
        refreshPage();
    }

    public void setPopUp1PflBInd(RichPanelGroupLayout popUp1PflBInd) {
        this.popUp1PflBInd = popUp1PflBInd;
    }

    public RichPanelGroupLayout getPopUp1PflBInd() {
        return popUp1PflBInd;
    }
    protected void refreshPage() {
    FacesContext fctx = FacesContext.getCurrentInstance();
    String refreshpage = fctx.getViewRoot().getViewId();
    ViewHandler ViewH = fctx.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
    UIV.setViewId(refreshpage);
    fctx.setViewRoot(UIV);
    }

    public void pop2CancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("pop2rowremove");
        operationBinding.execute();
        pop2lotidbind.setValue(null);
        pop2lotidbind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop2lotidbind);
        //
        pop2BinIdBind.setValue(null);
        pop2BinIdBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop2BinIdBind);
        pop2srBinding.setValue(null);
        pop2srBinding.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop2srBinding);
        refreshPage();
    }

    public void setP2srflg(String p2srflg) {
        this.p2srflg = p2srflg;
    }

    public String getP2srflg() {
        return p2srflg;
    }

    public void setItmStktotalQty(RichInputText itmStktotalQty) {
        this.itmStktotalQty = itmStktotalQty;
    }

    public RichInputText getItmStktotalQty() {
        return itmStktotalQty;
    }

    public void POP2TotQty(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Number i = (Number)object;
            if (i.compareTo(new Number(0)) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity Must be Greater then 0", null));
            } else {
                boolean flag = isPrecisionValid(26, 6, i);
                if (flag) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.57']}").toString(), null));
                }
            }
        }
        String binchk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
        if (binchk.equalsIgnoreCase("Y")) {
            if (p2srflg.equalsIgnoreCase("N")) {
                if (pop2BinIdBind.getValue() != null && pop2BinIdBind.getValue() != "" && object != null &&
                    object != "" && pop2lotidbind.getValue() != null && pop2lotidbind.getValue() != "") {
                    Number i = (Number)object;
                    if (i.compareTo(new Number(0)) == -1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Quantity Must be Greater then 0", null));
                    } else {
                        getPop2BinIdBind().processUpdates(FacesContext.getCurrentInstance());
                        getPop2lotidbind().processUpdates(FacesContext.getCurrentInstance());
                        if (kitActionBind.getValue() != null && kitActionBind.getValue() != "") {
                            if ("A".equalsIgnoreCase(kitActionBind.getValue().toString())) {
                                String binid = "1"; //getPop2BinIdBind().getValue().toString();
                                String LotId = "1"; //getPop2lotidbind().getValue().toString();
                                Number totQty = (Number)object;
                                OperationBinding operationBinding =
                                    getBindings().getOperationBinding("checkBinQtyStk");
                                operationBinding.getParamsMap().put("binName", binid);
                                operationBinding.getParamsMap().put("Qty", totQty);
                                operationBinding.getParamsMap().put("LotId", LotId);
                                operationBinding.execute();
                                String flag = "";
                                if (operationBinding.getResult() != null) {
                                    flag = operationBinding.getResult().toString();
                                }
                                if (flag.equalsIgnoreCase("Y")) {
                                } else if (flag.equalsIgnoreCase("N")) {
                                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                  "Stock not available in Selected Bin",
                                                                                  null));
                                }

                            }
                        }
                    }
                }
            }
        } else if (binchk.equalsIgnoreCase("N")) {
            if (p2srflg.equalsIgnoreCase("N")) {
                if (kitActionBind.getValue() != null && kitActionBind.getValue() != "") {
                    if ("A".equalsIgnoreCase(kitActionBind.getValue().toString())) {
                        if (getPop2lotidbind().getValue() != null && getPop2lotidbind().getValue() != "" &&
                            object != null && object != "") {
                            OperationBinding operationBinding = getBindings().getOperationBinding("pop2lotStk");
                            operationBinding.getParamsMap().put("LotId", getPop2lotidbind().getValue().toString());
                            operationBinding.getParamsMap().put("Qty", object.toString());
                            operationBinding.execute();
                            String flag = "";
                            if (operationBinding.getResult() != null) {
                                flag = operationBinding.getResult().toString();
                            }
                            if (flag.equalsIgnoreCase("Y")) {
                            } else if (flag.equalsIgnoreCase("N")) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "Stock not available in Selected Lot",
                                                                              null));
                            }
                        }
                    }
                }

            }
        }
    }

    public void POP2QtyValueChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //System.out.println("p2sr flag is "+p2srflg);
        if(p2srflg.equalsIgnoreCase("N")){
          OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setserialno");
          operationBinding2.getParamsMap().put("srTrans", "0");
          operationBinding2.execute();
         }
        if(BinChk.equalsIgnoreCase("N")){
            OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setbinck");
            operationBinding2.getParamsMap().put("binTrans", "0");
            operationBinding2.execute();
        }
        
     
        AdfFacesContext.getCurrentInstance().addPartialTarget(getItmStktotalQty());
     
    }

    public void POP2DialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (opsrflg.equalsIgnoreCase("Y")) {
                if (kitActionBind.getValue() != null) {
                    if (kitActionBind.getValue().toString().equalsIgnoreCase("A")) {
                        if (p2srflg.equalsIgnoreCase("N") || p2srflg.equalsIgnoreCase("Y")) {
                            if (getInputQtyVal().getValue() != null) {
                                Number inptqty = new Number(1);
                                Number optqty = (Number)getInputQtyVal().getValue();
                                BindingContainer bindings = getBindings();
                                OperationBinding operationBinding =
                                    bindings.getOperationBinding("inputItemcheckAllOK");
                                operationBinding.getParamsMap().put("inptQty", inptqty);
                                operationBinding.getParamsMap().put("optQty", optqty);
                                operationBinding.execute();
                                ArrayList arr = new ArrayList();
                                if (operationBinding.getResult() != null) {
                                    arr = (ArrayList)operationBinding.getResult();
                                }
                                if (arr.isEmpty()) {
                                    if (getWhIdBind().getValue() != null) {
                                    } else {
                                    }
                                } else {
                                     FacesMessage message =
                                        new FacesMessage("Quantity Must Be Equal To Input Item: " + arr);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                    //getPop2msgbind().setVisible(true);
                                    
                                }
                            }
                        }
                    } else if (kitActionBind.getValue().toString().equalsIgnoreCase("D")) {
                        if (p2srflg.equalsIgnoreCase("N") || p2srflg.equalsIgnoreCase("Y")) {
                            OperationBinding operationBinding =
                                getBindings().getOperationBinding("inputitemoutAandinptD");
                            operationBinding.execute();
                         }
                    }
                }
            } else if (opsrflg.equalsIgnoreCase("N")) {
                if (kitActionBind.getValue() != null) {
                    if (kitActionBind.getValue().toString().equalsIgnoreCase("A")) {
                        OperationBinding operationBinding =
                            getBindings().getOperationBinding("inputItemCheckQuantity");
                        operationBinding.execute();
                    }
                    if (kitActionBind.getValue().toString().equalsIgnoreCase("D")) {
                        if (p2srflg.equalsIgnoreCase("N")) {
                            OperationBinding operationBinding =
                                getBindings().getOperationBinding("inputItemCheckQuantityForD");
                            operationBinding.execute();
                        }
                    } else {
                    }
                }
            }
            
            OperationBinding chkzero=getBindings().getOperationBinding("chkZeroRecordItmStk");
            chkzero.execute();
             

        }
    }
   
    public void POP2deleteBtnAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("pop2CurrentRemove").execute();
    }


    public void setPop3deassemble(RichPopup pop3deassemble) {
        this.pop3deassemble = pop3deassemble;
    }

    public RichPopup getPop3deassemble() {
        return pop3deassemble;
    }

    public void setPop2lotidbind(RichSelectOneChoice pop2lotidbind) {
        this.pop2lotidbind = pop2lotidbind;
    }

    public RichSelectOneChoice getPop2lotidbind() {
        return pop2lotidbind;
    }

    public void setPop2lotIdBind(RichSelectOneChoice pop2lotIdBind) {
        this.pop2lotIdBind = pop2lotIdBind;
    }

    public RichSelectOneChoice getPop2lotIdBind() {
        return pop2lotIdBind;
    }

    public void setPop2BinIdBind(RichSelectOneChoice pop2BinIdBind) {
        this.pop2BinIdBind = pop2BinIdBind;
    }

    public RichSelectOneChoice getPop2BinIdBind() {
        return pop2BinIdBind;
    }

    public void deassembleBtn(ActionEvent actionEvent) {
        // Add event code here...
        String WhId="";
       
        if(getWhIdBind().getValue()!=null){
              WhId=getWhIdBind().getValue().toString();
        }
        String stkflag="";
        OperationBinding operationBinding =getBindings().getOperationBinding("getStkFlag");
        operationBinding.execute();
        if(operationBinding.getErrors()!=null){
            stkflag=operationBinding.getResult().toString();
            //System.out.println("serialized flag is "+stkflag);
        }
      
        if(stkflag.equalsIgnoreCase("Y")) {
            OperationBinding Obinding = getBindings().getOperationBinding("criteriasummsr");
           Obinding.execute();
           optserialized="Y";
        }
        else if(stkflag.equalsIgnoreCase("N") && BinChk.equalsIgnoreCase("N")) {
            optserialized="P";     
         //   System.out.println("optserialized"+optserialized);//only lot will be displays
        }
        else if(stkflag.equalsIgnoreCase("Y") && BinChk.equalsIgnoreCase("N")) {
           OperationBinding Obinding = getBindings().getOperationBinding("criteriasummsr");
           Obinding.execute();
            optserialized="Y"; //serialized will be displays
        }
        else if(stkflag.equalsIgnoreCase("N") && BinChk.equalsIgnoreCase("Y")) {
          //  System.out.println("bin will be dispalys"+optserialized);
            optserialized="Z";//only bin wil be displays
           // System.out.println("bin will be dispalys"+optserialized);
        }
        if(getKitActionBind().getValue()!=null)
        {
            if(getKitActionBind().getValue().toString().equalsIgnoreCase("D")){
                OperationBinding Obinding = getBindings().getOperationBinding("optSerialized");
                Obinding.execute();
                String flag=Obinding.getResult().toString();
                if(flag.equalsIgnoreCase("N")){
                    opsrflg="N";
                }
                else if(flag.equalsIgnoreCase("Y")){
                    opsrflg="Y";
                }//change to pop4
                  showPopup(pop4dismental,true);}
            else if(getKitActionBind().getValue().toString().equalsIgnoreCase("A")) {
            }
        }
    }

    public void setShutlebind(RichSelectManyShuttle shutlebind) {
        this.shutlebind = shutlebind;
    }

    public RichSelectManyShuttle getShutlebind() {
        return shutlebind;
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
    
    public void pop3dialoglistner(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("ok"))
        {
          if(optserialized.equalsIgnoreCase("Y")) {
              String flag=""; //""
         deasmblBinding.setDisabled(true);
              if(getChTransBind().getValue()!=null){
                // System.out.println("chk trans value: "+chTransBind);
              }
             OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtySr");
             operationBinding1.execute();
             Object flag1=operationBinding1.getResult();
             flag=flag1.toString();
              if(flag.equalsIgnoreCase("Y"))
              {
              OperationBinding operationBinding =getBindings().getOperationBinding("serializeSetStock");
              operationBinding.execute();
              desmode="N";
              //deasmblBinding.setDisabled(true);
              //serialized item will be set the value
              }
              else{
                  FacesMessage message = new FacesMessage("Quantity not equals to given Qyantity");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
              }
          }
          else if(optserialized.equalsIgnoreCase("Z")) {
              //only bin will be set the value
              String flag="";
              OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtyBin1");
               operationBinding1.execute();
              Object flag1=operationBinding1.getResult();
              flag=flag1.toString();
              if(flag.equalsIgnoreCase("Y"))
              {
                OperationBinding operationBinding =getBindings().getOperationBinding("binSetStock");
                operationBinding.execute();
                desmode="N";
                //addmode="N";
                //deasmblBinding.setDisabled(true);
              }
              else{
                  FacesMessage message = new FacesMessage("Quantity not equals to given Qyantity");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
              }
          }
          else if(optserialized.equalsIgnoreCase("P")) {
              //only lot item will be set the value 
              String flag="";
              OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtyLot");
              operationBinding1.execute();
              Object flag1=operationBinding1.getResult();
              flag=flag1.toString();
              if(flag.equalsIgnoreCase("Y"))
              {
              OperationBinding operationBinding =getBindings().getOperationBinding("lotSetStock");
              operationBinding.execute();
              desmode="N";
              }
              else{
                  FacesMessage message = new FacesMessage("Quantity not equals to given Qyantity");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
              }
          }
          
        }
            else{
            }
            
        
    }


    public void setOpsrflg(String opsrflg) {
        this.opsrflg = opsrflg;
    }

    public String getOpsrflg() {
        return opsrflg;
    }

    public void ActnvalueChangeListen(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkAltbinding);
       AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
       // AdfFacesContext.getCurrentInstance().addPartialTarget(inputQtyVal  );
       //AdfFacesContext.getCurrentInstance().addPartialTarget(optitmid);
    }

    public void setOptserialized(String optserialized) {
        this.optserialized = optserialized;
    }

    public String getOptserialized() {
        return optserialized;
    }

    public void srvaluechangelistner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
      
    }

    public void lotBinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       // System.out.println("inside bin validator : "+object);
        
        if(BinChk.equalsIgnoreCase("Y") && WhChk.equalsIgnoreCase("Y")){
            if(object!=null){
        //    System.out.println("inside if part bin validator");
        lotTransBind.processUpdates(facesContext.getCurrentInstance());//
        lotTransBind.processUpdates(facesContext.getCurrentInstance());
        OperationBinding Obind=getBindings().getOperationBinding("lotBinValidator");
        Obind.getParamsMap().put("binName",object.toString());
        Obind.execute();
        String flag="";
        if(Obind.getResult()!=null){
              flag=Obind.getResult().toString();
        }
        if(flag.equalsIgnoreCase("N")) {
        String msg=" select different bin ";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
          
        }
         }
            else{
                String msg="Bin is Required";
             //   System.out.println("else part bin validator");
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
       
    }

    public void pop2SrValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        getPop2lotidbind().processUpdates(facesContext.getCurrentInstance());
        // getPop2BinIdBind().processUpdates(facesContext.getCurrentInstance());
         pop2lotIdTransBinding.processUpdates(FacesContext.getCurrentInstance());
         pop2srBinding.processUpdates(FacesContext.getCurrentInstance());
    
    //pop2SrVCL
        /*    if(p2srflg.equalsIgnoreCase("Y")){
        OperationBinding Obind=getBindings().getOperationBinding("pop2srValidator");
        Obind.getParamsMap().put("srno",object.toString());
        Obind.execute();
        String flag="";
        if(Obind.getResult()!=null){
              flag=Obind.getResult().toString();
        }
        if(flag.equalsIgnoreCase("N")) {
            String msg=" Please select different serial Number";
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
       }
        else{
        }
        } */
    }

    public void pop2LotBinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
      if(p2srflg.equalsIgnoreCase("N")) {
           //if output item is serialize then input item may be serialize or non serialize
            if(opsrflg.equalsIgnoreCase("Y")){
               getPop2lotidbind().processUpdates(facesContext.getCurrentInstance());
              //  getPop2BinIdBind().processUpdates(facesContext.getCurrentInstance());
                pop2lotIdTransBinding.processUpdates(FacesContext.getCurrentInstance());
                pop2srBinding.processUpdates(FacesContext.getCurrentInstance());
            OperationBinding Obind=getBindings().getOperationBinding("pop2LotBinValidator");
            Obind.getParamsMap().put("binName",object.toString());
            Obind.execute();
            String flag="";
            //System.out.println("binding error are "+Obind.getErrors());
            if(Obind.getResult()!=null){
                  flag=Obind.getResult().toString();
            }
           // System.out.println("binding result is : "+flag);
            if("N".equalsIgnoreCase(flag)) {
                String msg="Please select different bin ";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            else{
               // System.out.println("get the data in else part "+flag);
                }
            }
            else if(opsrflg.equalsIgnoreCase("N")){
              // if output item is non serialize then input item is also non serialize then no need of this case
                if(p2srflg.equalsIgnoreCase("N")){ 
                    pop2lotidbind.processUpdates(facesContext.getCurrentInstance());
                    pop2lotIdTransBinding.processUpdates(FacesContext.getCurrentInstance());
                OperationBinding Obind=getBindings().getOperationBinding("pop2LotBinValidator2");
                Obind.getParamsMap().put("binName",object.toString());
                Obind.execute();
                String flag="N";
                if(Obind.getResult()!=null){
                      flag=Obind.getResult().toString();
                }
                 if(flag.equalsIgnoreCase("N")) {
                    String msg=" Please select different bin ";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                  }
                }
            }
        }
    }


    public void okQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            Number i=(Number)object;
           boolean flag= isPrecisionValid(26, 6, i);
            if(flag){
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
            
        }
        
        /*  getPop2ScrpBind().processUpdates(FacesContext.getCurrentInstance());
        getPop2rwQty().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPop2ScrpBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPop2rwQty());  */
        // Add event code here...
        String actntype="";
        if(getKitActionBind().getValue()!=null && getKitActionBind().getValue()!="") {
            actntype=getKitActionBind().getValue().toString();
        }
        if(actntype.equalsIgnoreCase("D")){
            if(object!=null && object!=""){
              Number i=(Number)object;
              if(i.compareTo(new Number(0))==-1){
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Must be Greater then 0", null)); 
              }
              else{
                if(opsrflg.equalsIgnoreCase("Y")&& p2srflg.equalsIgnoreCase("Y")){
                   Number xx=(Number)object;
                     if(object!=null && xx.compareTo(new Number(1))==0){
                     }
                     else if(xx.compareTo(new Number(1))==1){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity must be Equals to 1", null));
                     }
                     
               if(getPop2ScrpBind().getValue()!="" && getPop2rwQty().getValue()!="" && object!=null){
                   Number scrpqty=(Number)getPop2ScrpBind().getValue();
                   Number rwkqty=(Number)getPop2rwQty().getValue();
                   Number okqty=(Number)object;
                   Number zero=new Number(0);
                //from here code is commented
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPop2ScrpBind());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPop2rwQty());
                getPop2ScrpBind().processUpdates(FacesContext.getCurrentInstance());
                getPop2rwQty().processUpdates(FacesContext.getCurrentInstance());
                        
                        
                  /*  if(scrpqty.compareTo(zero)==0 && rwkqty.compareTo(zero)==0 && okqty.compareTo(zero)==0){
                       String flag="";
                     System.out.println(getPop2ScrpBind().getValue()+" deffere"+getPop2rwQty().getValue());
                     OperationBinding Obind=getBindings().getOperationBinding("SerialTotQtyCheck");
                     Obind.getParamsMap().put("okQty", object);
                     Obind.getParamsMap().put("ScrpQty",(Number)getPop2ScrpBind().getValue());
                     Obind.getParamsMap().put("RwkQty",(Number)getPop2rwQty().getValue());
                     Obind.execute();
                     if(Obind.getResult()!=null){
                           flag=Obind.getResult().toString();
                     }
                     if(flag.equalsIgnoreCase("Y")){
                          }
                      else{
                               String msg="Total Quantity is not equals ";
                               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                          } 
                   } */
                   
                    }
                 }
              }
           }
        }
    }

    public void scrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number i=(Number)object;
            if(i.compareTo(new Number(0))==-1){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Must be Greater then 0", null)); 
            }
            else{
               boolean flag= isPrecisionValid(26, 6, i);
                if(flag){
                    
                }else{
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
                }
            }
           // System.out.println("scrap qty is "+object);
            if(kitActionBind.getValue()!=null && kitActionBind.getValue()!=""){
                if("D".equalsIgnoreCase(kitActionBind.getValue().toString()) && "Y".equalsIgnoreCase(p2srflg)){
                     Number xx=(Number)object;
                     if(object!=null && xx.compareTo(new Number(1))==0){
                     }
                    else if(xx.compareTo(new Number(1))==1){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity must be Equals to 1", null));
                    }
                }
            }
    }

    public void rwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number i=(Number)object;
        if(i.compareTo(new Number(0))==-1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Must be Greater then 0", null)); 
        }
        else{
           boolean flag= isPrecisionValid(26, 6, i);
            if(flag){
                
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
            
        }
        if(kitActionBind.getValue()!=null && kitActionBind.getValue()!=""){
            if("D".equalsIgnoreCase(kitActionBind.getValue().toString()) && "Y".equalsIgnoreCase(p2srflg)){
                Number xx=(Number)object;
                 if(object!=null && xx.compareTo(new Number(1))==0){
                    //pop2okqty.setValue(new Number(0));
                    //pop2ScrpBind.setValue(new Number(0));
                   // AdfFacesContext.getCurrentInstance().addPartialTarget(pop2okqty);
                  //  AdfFacesContext.getCurrentInstance().addPartialTarget(pop2ScrpBind); 
                  }else if(xx.compareTo(new Number(1))==1){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity must be Equals to 1", null));
                  }
            }
        }
    }
    

    public void setPop2srBinding(RichSelectOneChoice pop2srBinding) {
        this.pop2srBinding = pop2srBinding;
    }

    public RichSelectOneChoice getPop2srBinding() {
        return pop2srBinding;
    }

    public void setPop2ScrpBind(RichInputText pop2ScrpBind) {
        this.pop2ScrpBind = pop2ScrpBind;
    }

    public RichInputText getPop2ScrpBind() {
        return pop2ScrpBind;
    }

    public void setPop2rwQty(RichInputText pop2rwQty) {
        this.pop2rwQty = pop2rwQty;
    }

    public RichInputText getPop2rwQty() {
        return pop2rwQty;
    }

    public void backActionLister(ActionEvent actionEvent) {
        OperationBinding operationBinding =getBindings().getOperationBinding("Rollback1");
        operationBinding.execute();
    }

    public void setimode(String addmode) {
        this.addmode = addmode;
    }

    public String getAddmode() {
        return addmode;
    }

    public void pop2SrValueChangeListen(ValueChangeEvent valueChangeEvent) {
       if(valueChangeEvent.getNewValue()!=null &&valueChangeEvent.getNewValue()!="") {
           OperationBinding operationBinding =getBindings().getOperationBinding("pop2setserialno");
           operationBinding.getParamsMap().put("srTrans", valueChangeEvent.getNewValue().toString());
           operationBinding.execute();
           }
        
    }

    public void pop2binChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(getPop2srBinding().getValue()!=null && getPop2srBinding().getValue()!=null){
            setPop2srBinding(null);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getPop2srBinding());
        }
    }

    public void editActionListner(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void cancelListner(ActionEvent actionEvent) {
        // Add event code here...
        CEmode="V";
        OperationBinding operationBinding =getBindings().getOperationBinding("Rollback1");
        operationBinding.execute();
    }

    public void createMethod(ActionEvent actionEvent) {
        // Add event code here...
        CEmode="C";
        addmode="Y";
        desmode="Y";
     // setMode("C");
       // addbtnBinding.setDisabled(false);
        //deasmblBinding.setDisabled(false);
        //stkAltbinding.setDisabled(false);
    }
    public void editModeActionListner(ActionEvent actionEvent) {
        // Add event code here...
        CEmode="E";
    }


    public void setCEmode(String CEmode) {
        this.CEmode = CEmode;
    }

    public String getCEmode() {
        return CEmode;
    }

    public void setAddbtnBinding(RichCommandButton addbtnBinding) {
        this.addbtnBinding = addbtnBinding;
    }

    public RichCommandButton getAddbtnBinding() {
        return addbtnBinding;
    }

    public void setStkAltbinding(RichCommandButton stkAltbinding) {
        this.stkAltbinding = stkAltbinding;
    }

    public RichCommandButton getStkAltbinding() {
        return stkAltbinding;
    }

    public void setDeasmblBinding(RichCommandButton deasmblBinding) {
        this.deasmblBinding = deasmblBinding;
    }

    public RichCommandButton getDeasmblBinding() {
        return deasmblBinding;
    }

    public void pop2LotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
            if(BinChk.equalsIgnoreCase("N")){
                if(object!=null){
                       if(opsrflg.equalsIgnoreCase("Y") && p2srflg.equalsIgnoreCase("N")){
                           OperationBinding operationBinding =getBindings().getOperationBinding("pop2LotvalidSrN");
                           operationBinding.getParamsMap().put("Lotname", object.toString());
                           operationBinding.execute();
                           if(operationBinding.getResult()!=null){
                              String flag=operationBinding.getResult().toString();
                             // System.out.println("falg value is "+flag);
                              if(flag.equalsIgnoreCase("N")){
                                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select Different Lot", null));
                              }
                           }
                       }
                   //Number i=(Number)object;
                  // if(i.compareTo(new Number(0))==0 || i.compareTo(new Number(0))==-1 ){
                      // String msg=" Quantity Must be Greater then 0 ";
                      // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                  // }else{
                 //  boolean flag= isPrecisionValid(26, 6, i);
                      
                     // if(flag){
                      // }else{
                              //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
                       // }
                 //  }
               /*      if(opsrflg.equalsIgnoreCase("Y") && p2srflg.equalsIgnoreCase("N")){
                        Number lotstk=new Number(0); 
                        Number obj=(Number)object;
                        if(dsmntLotQtyBind.getValue()!=null && dsmntLotQtyBind.getValue()!=""){
                           lotstk=(Number)dsmntLotQtyBind.getValue();
                        }
                        System.out.println("bin comparison is "+obj.compareTo(lotstk)+" avialable stock is"+ lotstk);
                        if(obj.compareTo(lotstk)==1){
                          throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock not Available in Selected Lot", null)); 
                        }
                        else{
                          
                        }
                    } */
               }
            }
       
    }


    public void pop3binValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null && object!=""){
            Number i=(Number)object;
            if(i.compareTo(new Number(0))==0 || i.compareTo(new Number(0))==-1 ){
                String msg=" Quantity Must be Greater then 0 ";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }else{
            boolean flag= isPrecisionValid(26, 6, i);
               
               if(flag){
                }else{
                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
                 }
            }
           /* OperationBinding operationBinding =getBindings().getOperationBinding("binQuantityChkSumm");
           operationBinding.getParamsMap().put("quantity",(Number)object);
           operationBinding.execute();
           String flag="";
           if(operationBinding.getResult()!=null){
               flag=operationBinding.getResult().toString();
               System.out.println("result of stock check is : "+flag);
           } */
             Number Binstk=new Number(0); 
             Number obj=(Number)object;
            if(pop5bintotstk.getValue()!=null && pop5bintotstk.getValue()!=""){
                Binstk=(Number)pop5bintotstk.getValue();
            }
          //  System.out.println("bin comparison is "+obj.compareTo(Binstk)+" avialable stock is"+ Binstk);
           if(obj.compareTo(Binstk)==1){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock not Available in Selected Bin", null)); 
           }
           else{
               
           }
       }
    }

    public void pop3lotvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null && object!=""){
            Number i=(Number)object;
            if(i.compareTo(new Number(0))==0 || i.compareTo(new Number(0))==-1 ){
                String msg=" Quantity Must be Greater then 0 ";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            else{
                 boolean flag= isPrecisionValid(26, 6, i);
                 
                 if(flag){
                  }else{
                         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
                       }
                 }//dsmntLotQtyBind
                 Number lotstk=new Number(0); 
                 Number obj=(Number)object;
                 if(dsmntLotQtyBind.getValue()!=null && dsmntLotQtyBind.getValue()!=""){
                    lotstk=(Number)dsmntLotQtyBind.getValue();
                 }
               //  System.out.println("bin comparison is "+obj.compareTo(lotstk)+" avialable stock is"+ lotstk);
                 if(obj.compareTo(lotstk)==1){
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock not Available in Selected Lot", null)); 
                 }
                 else{
                   
                 }
        }
    }

    public void setPop2msgbind(RichMessage pop2msgbind) {
        this.pop2msgbind = pop2msgbind;
    }

    public RichMessage getPop2msgbind() {
        return pop2msgbind;
    }

    public void okvaluechangelistner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(getKitActionBind().getValue()!=null && getKitActionBind().getValue()!=""){
            if("D".equalsIgnoreCase(getKitActionBind().getValue().toString())){
                if(p2srflg.equalsIgnoreCase("N")){
                    OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setserialno");
                    operationBinding2.getParamsMap().put("srTrans", "0");
                    operationBinding2.execute();
                }
                if(BinChk.equalsIgnoreCase("N")){
                     OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setbinck");
                     operationBinding2.getParamsMap().put("binTrans", "0");
                    operationBinding2.execute();
                }
            }
        }
    }

    public void setDesmode(String desmode) {
        this.desmode = desmode;
    }

    public String getDesmode() {
        return desmode;
    }

    public void setChTransBind(RichSelectBooleanCheckbox chTransBind) {
        this.chTransBind = chTransBind;
    }

    public RichSelectBooleanCheckbox getChTransBind() {
        return chTransBind;
    }

    public void pop1DeleteListner(ActionEvent actionEvent) {
        OperationBinding operationBinding =getBindings().getOperationBinding("pop1DeleteHandle");
        operationBinding.execute();
        b1="";
        
    }

    public void deleteActionlistner(ActionEvent actionEvent) {
        // Add event code here...
        delDsbl="N";
        showPopup(delpopup, true);
    }

    public void setDelpopup(RichPopup delpopup) {
        this.delpopup = delpopup;
    }

    public RichPopup getDelpopup() {
        return delpopup;
    }
//prodStkDeleteActio
    public void delDialogListner(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
       /*  OperationBinding operationBinding =getBindings().getOperationBinding("prodStkDeleteHandle");
        operationBinding.execute();
        String flag=operationBinding.getResult().toString();
        System.out.println("last");
        if(flag.equalsIgnoreCase("N")){
            OperationBinding operationBinding1 =getBindings().getOperationBinding("Commit1");
            operationBinding1.execute();
            goBack("last");
        }
        
            desmode="Y";
            delDsbl="Y";
            AdfFacesContext.getCurrentInstance().addPartialTarget(prodStkTbBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
            OperationBinding exec =getBindings().getOperationBinding("Execute1");
            exec.execute();
           // System.out.println("desmode"+desmode);
        }
        else if(dialogEvent.getOutcome().name().equalsIgnoreCase("no")){
            delDsbl="Y";
            AdfFacesContext.getCurrentInstance().addPartialTarget(prodStkTbBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
        } */
        }
    }

    public String goBack(String param1){
        return param1;
    }



    public void OptItmValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
        if(getOptitmBind()!=null){
            
            getOptitmBind().setValue(valueChangeEvent.getNewValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getOptitmBind());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getOptitmid());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getOptItmUOM());
            
           // System.out.println("value change value is "+getOptitmBind().getValue());
                        //getOptitmBind().processUpdates(FacesContext.getCurrentInstance());
            //AdfFacesContext.getCurrentInstance().addPartialTarget(inputQtyVal);
        }
    }
            
    }

    public void setOptitmBind(RichInputListOfValues optitmBind) {
        this.optitmBind = optitmBind;
    }

    public RichInputListOfValues getOptitmBind() {
        return optitmBind;
    }

    public void WhValueChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
       // whIdBind
            if(getWhIdBind()!=null){
                getWhIdBind().setValue(valueChangeEvent.getNewValue());
                //getWhIdBind().processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(inputQtyVal);
            }
    }

    public void setProdStkTbBind(RichTable prodStkTbBind) {
        this.prodStkTbBind = prodStkTbBind;
    }

    public RichTable getProdStkTbBind() {
        return prodStkTbBind;
    }

    public void pop1TotQtyValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null && object!=""){
        Number i=(Number)object;
        if(i.compareTo(0)==-1)
        {
            String msg="Value Must Be Greater Then Zero";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }else{
           boolean flag= isPrecisionValid(26, 6,i);
            if(flag){
                
            }
            else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
        }
    }
    }


    public void setSfMessageBind(RichMessage sfMessageBind) {
        this.sfMessageBind = sfMessageBind;
    }

    public RichMessage getSfMessageBind() {
        return sfMessageBind;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB1() {
        return b1;
    }

    public void pop2SrVCL(ValueChangeEvent valueChangeEvent) {
        if(p2srflg.equalsIgnoreCase("Y")){
            if(BinChk.equalsIgnoreCase("N")){
                OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setbinck");
                operationBinding2.getParamsMap().put("binTrans", "0");
                operationBinding2.execute();
            }
            getPop2lotidbind().processUpdates(FacesContext.getCurrentInstance());
           // getPop2BinIdBind().processUpdates(FacesContext.getCurrentInstance());
            pop2lotIdTransBinding.processUpdates(FacesContext.getCurrentInstance());
           // pop2srBinding.processUpdates(FacesContext.getCurrentInstance());
            //System.out.println("input item is serialize "+p2srflg);
            OperationBinding Obind=getBindings().getOperationBinding("pop2srValidator");
            Obind.getParamsMap().put("srno",valueChangeEvent.getNewValue().toString());
            Obind.execute();
            String flag="";
            if(Obind.getResult()!=null){
                  flag=Obind.getResult().toString();
            }
       // System.out.println("Flag in Bean="+flag);
        if(flag.equalsIgnoreCase("N")) {
            pop2srBinding.setValue(null);
            String msg=" Please select different serial Number";
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(pop2srBinding.getClientId(), message);
            
        }
        else{
        }
        }
    }

    public void setPop1BinBind(RichSelectOneChoice pop1BinBind) {
        this.pop1BinBind = pop1BinBind;
    }

    public RichSelectOneChoice getPop1BinBind() {
        return pop1BinBind;
    }

    public void setPop1SrBind(RichInputText pop1SrBind) {
        this.pop1SrBind = pop1SrBind;
    }

    public RichInputText getPop1SrBind() {
        return pop1SrBind;
    }

    public void setPop1totQtyBind(RichInputText pop1totQtyBind) {
        this.pop1totQtyBind = pop1totQtyBind;
    }

    public RichInputText getPop1totQtyBind() {
        return pop1totQtyBind;
    }
    
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
    JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
    vc.setPrecision(precision);
    vc.setScale(scale);
    return vc.validateValue(total);
    }

   
    public void lotItTransVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        if(valueChangeEvent.getNewValue()!=null){
        pop2lotidbind.setValue(valueChangeEvent.getNewValue());
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop2lotidbind);
        
        if(pop2lotidbind.getValue()!=null){
           // System.out.println("pop2lotid bind setted value is "+pop2lotidbind.getValue());
          }
       }
      /* if(getKitActionBind().getValue()!=null && getKitActionBind().getValue()!=""){
          if("D".equalsIgnoreCase(getKitActionBind().getValue().toString())){ */
              if(p2srflg.equalsIgnoreCase("N")){
                  OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setserialno");
                  operationBinding2.getParamsMap().put("srTrans", "0");
                  operationBinding2.execute();
              }
              if(BinChk.equalsIgnoreCase("N")){
                   OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setbinck");
                   operationBinding2.getParamsMap().put("binTrans", "0");
                  operationBinding2.execute();
              }
         /*  }
      } */
  }

    public void setPop2lotIdTransBinding(RichSelectOneChoice pop2lotIdTransBinding) {
        this.pop2lotIdTransBinding = pop2lotIdTransBinding;
    }

    public RichSelectOneChoice getPop2lotIdTransBinding() {
        return pop2lotIdTransBinding;
    }

    public void setPop4dismental(RichPopup pop4dismental) {
        this.pop4dismental = pop4dismental;
    }

    public RichPopup getPop4dismental() {
        return pop4dismental;
    }

    public void pop4dialogListner(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("ok"))
        {
          if(optserialized.equalsIgnoreCase("Y")) {
              String flag=""; //""
          //deasmblBinding.setDisabled(true);
              if(getChTransBind().getValue()!=null){
             //  System.out.println("chk trans value: "+getChTransBind().getValue());
              }
             OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtySr1");
             operationBinding1.execute();
             Object flag1=operationBinding1.getResult();
             flag=flag1.toString();
              if(flag.equalsIgnoreCase("Y"))
              {
              OperationBinding operationBinding =getBindings().getOperationBinding("serializeSetStock");
              operationBinding.execute();
              desmode="N";
              //deasmblBinding.setDisabled(true);
              //serialized item will be set the value
              }
              else{
                  FacesMessage message = new FacesMessage("Quantity not equals to given Quantity");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
              }
          }          else if(optserialized.equalsIgnoreCase("Z")) {
              //only bin will be set the value
              String flag="";
              OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtyBin2");
               operationBinding1.execute();
              Object flag1=operationBinding1.getResult();
              flag=flag1.toString();
              if(flag.equalsIgnoreCase("Y"))
              {
                OperationBinding operationBinding =getBindings().getOperationBinding("binSetStock");
                operationBinding.execute();
                desmode="N";
                //addmode="N";
                //deasmblBinding.setDisabled(true);
              }
              else{
                  FacesMessage message = new FacesMessage("Quantity not equals to given Quantity");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
              }
          }
            else if(optserialized.equalsIgnoreCase("P")) {
                //only lot item will be set the value 
                String flag="";
                OperationBinding operationBinding1 =getBindings().getOperationBinding("chkdeassembleQtyLot");
                operationBinding1.execute();
                Object flag1=operationBinding1.getResult();
                flag=flag1.toString();
                if(flag.equalsIgnoreCase("Y"))
                {
                OperationBinding operationBinding =getBindings().getOperationBinding("lotSetStock");
                operationBinding.execute();
                desmode="N";
                }
                else{
                    FacesMessage message = new FacesMessage("Quantity not equals to given Qyantity");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
    }

    public void setLotChkBinding(RichInputText lotChkBinding) {
        this.lotChkBinding = lotChkBinding;
    }

    public RichInputText getLotChkBinding() {
        return lotChkBinding;
    }

    public void pop2lotvalidp2srN(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(BinChk.equalsIgnoreCase("N")){
            if(object!=null){
                if(opsrflg.equalsIgnoreCase("N") && p2srflg.equalsIgnoreCase("N")){
                    OperationBinding operationBinding =getBindings().getOperationBinding("pop2LotvalidSrN");
                    operationBinding.getParamsMap().put("Lotname", object.toString());
                    operationBinding.execute();
                    if(operationBinding.getResult()!=null){
                       String flag=operationBinding.getResult().toString();
                     //  System.out.println("falg value is "+flag);
                       if(flag.equalsIgnoreCase("N")){
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select Different Lot", null));
                       }
                    }
                }
           }
        }
       else if(BinChk.equalsIgnoreCase("Y")){
            if(object!=null){
                if(p2srflg.equalsIgnoreCase("N")){
                    OperationBinding operationBinding =getBindings().getOperationBinding("pop2lotchkexcept");
                    operationBinding.getParamsMap().put("Lotname", object.toString());
                    operationBinding.execute();
                    if(operationBinding.getResult()!=null){
                       String flag=operationBinding.getResult().toString();
                     //  System.out.println("falg value is "+flag);
                       if(flag.equalsIgnoreCase("N")){
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select Different Lot", null));
                       }
                    }
                }
           }
        }
    }

    public void POP2LOTVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        /* if(p2srflg.equalsIgnoreCase("N")){
            System.out.println("value change listner executes");
            OperationBinding operationBinding2 =getBindings().getOperationBinding("pop2setserialno");
            operationBinding2.getParamsMap().put("srTrans", "0");
            operationBinding2.execute();
        } */
    }

    public void setOptItmUOM(RichSelectOneChoice optItmUOM) {
        this.optItmUOM = optItmUOM;
    }

    public RichSelectOneChoice getOptItmUOM() {
        return optItmUOM;
    }

    public void setOptitmtransIdTextBind(RichInputText optitmtransIdTextBind) {
        this.optitmtransIdTextBind = optitmtransIdTextBind;
    }

    public RichInputText getOptitmtransIdTextBind() {
        return optitmtransIdTextBind;
    }

    public void setPop2okqty(RichInputText pop2okqty) {
        this.pop2okqty = pop2okqty;
    }

    public RichInputText getPop2okqty() {
        return pop2okqty;
    }

    public void setDelDsbl(String delDsbl) {
        this.delDsbl = delDsbl;
    }

    public String getDelDsbl() {
        return delDsbl;
    }

    public void delpopcancellistner(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodStkTbBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
        delDsbl="Y";
    }

    public void setPop5binTotQtyBind(RichInputText pop5binTotQtyBind) {
        this.pop5binTotQtyBind = pop5binTotQtyBind;
    }

    public RichInputText getPop5binTotQtyBind() {
        return pop5binTotQtyBind;
    }

    public void setPop5lotTotBind(RichInputText pop5lotTotBind) {
        this.pop5lotTotBind = pop5lotTotBind;
    }

    public RichInputText getPop5lotTotBind() {
        return pop5lotTotBind;
    }

    public void pop5CancelListner(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        pop5binTotQtyBind.setValue(null);
        pop5lotTotBind.setValue(null);
        pop5binTotQtyBind.resetValue();
        pop5lotTotBind.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop5binTotQtyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop5lotTotBind);
        
    }

    public void setPop5bintotstk(RichOutputText pop5bintotstk) {
        this.pop5bintotstk = pop5bintotstk;
    }

    public RichOutputText getPop5bintotstk() {
        return pop5bintotstk;
    }

    public void setDsmntLotQtyBind(RichInputText dsmntLotQtyBind) {
        this.dsmntLotQtyBind = dsmntLotQtyBind;
    }

    public RichInputText getDsmntLotQtyBind() {
        return dsmntLotQtyBind;
    }

    public String prodStkDeleteAction() {
        // Add event code here...
        String temp="none";
        OperationBinding operationBinding =getBindings().getOperationBinding("prodStkDeleteHandle");
        operationBinding.execute();
        String flag=operationBinding.getResult().toString();
        System.out.println("last");
        if(flag.equalsIgnoreCase("N")){
            OperationBinding operationBinding1 =getBindings().getOperationBinding("Commit1");
            operationBinding1.execute();
            temp="back";
           // goBack("last");
        }else{
            temp="none";
        }
        
            desmode="Y";
            delDsbl="Y";
            AdfFacesContext.getCurrentInstance().addPartialTarget(prodStkTbBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
            OperationBinding exec =getBindings().getOperationBinding("Execute1");
            exec.execute();
           // System.out.println("desmode"+desmode);
           delpopup.hide();
        return temp;
    }

    public String NoActionDeletePopup() {
        // Add event code here...
        delDsbl="Y";
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodStkTbBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(deasmblBinding);
        delpopup.hide();
        return null;
    }
}
