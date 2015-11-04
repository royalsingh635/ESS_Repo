package mmcashpo.view.bean;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

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

import mmcashpo.model.module.MmCashPOAMImpl;

import mmcashpo.model.views.MmCpoItmVORowImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetail;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
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

import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;



import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MmCashPoAddEditBean implements Serializable{
    private RichShowDetail disclosedItem;
    private RichPanelFormLayout itmFormbind;
    private RichPopup ocPopup;
    private RichPopup taxPopup;
    private static int NUMBER = Types.NUMERIC;
    private RichTable trLineTable;
    private RichInputText itmSumtaxAmtBind;
    private RichPopup discountPopupBind;
    private RichInputText tranDiscOnPoBind;
    private static String resetflg="P";
     Integer taxruleid = 0;
    private  Integer taxRule=null;
    private Integer TaxRuleId = 0;
    private Number taxableAmount = new Number(0);
    private  Integer taxRuleitem=null;
    private String taxruleflg="I";
    private Number itmtaxamt1=new Number(0);
    private String  taxExmpt="N";
    private Number taxAbleAmoutForChange = new Number(0);
    static Integer count = 0;
    private BigDecimal Avl_stk;
    private BigDecimal Req_stk;
    private BigDecimal Ord_stk;
    private RichPopup historyPopupBind;
    private RichSelectOneChoice itmUomBind;
    private RichSelectOneChoice itmIdBind;
    private RichInputText discValbind;
    private RichTable itmTableBind;
    private RichSelectOneChoice coaidBind;
    private RichSelectOneChoice currIdBind;
    private RichInputText discValonPopup;
    private RichInputText totaldiscountBind;
    private RichSelectOneChoice coaIdOcBind;
    private RichInputText orderQty;
    private RichInputText itmPrice;
    private RichInputText transSumSp;
    private RichTable ocTableBind;
    private RichInputText ocAmtSpBind;
    private RichSelectOneRadio disctypeCpoBind;
    private RichSelectOneRadio itmDicsTypeBind;
    private RichPanelFormLayout discountFormBind;
    private RichSelectOneChoice transTypeBind;
    private RichPanelCollection panelColletnBind;
    private static ADFLogger adfLog = ADFLogger.createADFLogger(MmCashPoAddEditBean.class);
    private RichPopup powiseSelectTaxPop;
    private RichSelectOneRadio applyTaxRadioBind;
    private RichSelectOneChoice selectTaxPoBind;
    private RichPopup resettaxPop;
    private Integer CPO_DOC_NO=18534;
    private RichInputListOfValues currencyTransBind;

    public MmCashPoAddEditBean() {
    }

    public Boolean isPrecisionValid(Integer precision,Integer scale,Number total){
                      JboPrecisionScaleValidator vc=new JboPrecisionScaleValidator();
                      vc.setPrecision(precision);
                      vc.setScale(scale);
                      return vc.validateValue(total);
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
    public void saveButton(ActionEvent actionEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObject po = am.getMmCpo1();
       // ViewObject cpoItm =am.getMmCpoItm1();
        adfLog.info("Inside save --------------");
        ViewObjectImpl cpoitm11 = am.getMmCpoItm1();
        Row currRow = po.getCurrentRow();
        Number totalcost = (Number)currRow.getAttribute("TotalCostSp");
            System.out.println("TotalCostSp----:"+totalcost);
        Row r[]=getAm().getOrgMmPrf().getFilteredRows("OrgId",currRow.getAttribute("OrgId"));
         
            Number orgCpoLimit = (Number)r[0].getAttribute("CpoAmtLimit");
            if(orgCpoLimit!=null && totalcost.compareTo(orgCpoLimit)>0)
            {
                    FacesMessage message2 = new FacesMessage("CPO Amount can not be Greater than "+orgCpoLimit+".");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null,message2);   
                }
            else
            {
            Number zeros = new Number(0);
        /** check for item wise taxable amount change or no
         * */
          RowSetIterator rsi=cpoitm11.createRowSetIterator(null);
          String appTax="N";
          String itmNameConcat="";
          while(rsi.hasNext()){
              Row rw=rsi.next();
              if(rw.getAttribute("TranReapplyTax")!=null){
                  if("Y".equalsIgnoreCase(rw.getAttribute("TranReapplyTax").toString())){
                      appTax="Y";
                      if(rw.getAttribute("transItemName")!=null){
                           itmNameConcat=itmNameConcat + rw.getAttribute("transItemName").toString()+",";
                      }
                  }
              }
          }
          rsi.closeRowSetIterator();
        if("Y".equalsIgnoreCase(appTax)){
                            System.out.println("inside tax color");
                                FacesMessage message =
                                    new FacesMessage(resolvEl("#{bundle['MSG.292']}").toString()+itmNameConcat.substring(0, itmNameConcat.length()-1)+resolvEl("#{bundle['MSG.293']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);

        }else if(zeros.compareTo(totalcost)>0){
                    String msg2 = resolvEl("#{bundle['MSG.294']}").toString();
                                                     FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                     FacesContext fc = FacesContext.getCurrentInstance();
                                                     fc.addMessage(null,message2);  
        }else{
        Integer commitCount = 0;


        if (currRow.getAttribute("CpoId") == null) {
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String cpoId =(String)(callStoredFunction(Types.VARCHAR, "MM.FN_MM_GEN_ID (?,?,?,?)", new Object[] {SlocId , cldId , p_org_id , "MM$CPO" }));
                  
                  System.out.println("--DocId new created--"+cpoId);
            
            
            currRow.setAttribute("CpoId", cpoId);
            /* oracle.jbo.domain.Date dateCurr = (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();
            System.out.println("Current date--" + dateCurr);
            currRow.setAttribute("CpoDt", dateCurr); */
        }
        Number amtBs=(Number)currRow.getAttribute("TotalCostBs");
        Number amtSp=(Number)currRow.getAttribute("TotalCostSp"); 
        // System.out.println("Value in savePo--"+amtBs+"---"+amtSp);
        currRow.setAttribute("CpoAmountBs", amtBs);
        currRow.setAttribute("CpoAmountSp", amtSp);
        
       
        RowSetIterator rti =  cpoitm11.createRowSetIterator(null);
        if(rti.getRowCount() > 0)
        {
        while(rti.hasNext())
        {
          Row row = rti.next();
            Number amtbs = (Number)row.getAttribute("TranItmAmtBs");
            Number amtsp = (Number)row.getAttribute("TranItmAmtSp");

           row.setAttribute("ItmAmountBs", amtbs);
            row.setAttribute("ItmAmountSp", amtsp);
            
            }
        
        
        
        
        ViewObjectImpl tr = am.getMmCpoTr2();
          tr.executeQuery();
        Number taxableInTr = new Number(0);
        Number matchAmt = new Number(0);
        RowSetIterator rit = tr.createRowSetIterator(null);
        if(rit.getRowCount()>0)
        {
        if(currRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
        {
        while(rit.hasNext())
        {
          Row newRow = rit.next();
            if(newRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
            {
              taxableInTr = (Number)newRow.getAttribute("TaxableAmt");
                }

            }
        
         if(currRow.getAttribute("TaxAfterDiscFlg").toString().equalsIgnoreCase("Y"))
         {
                 Number itmTotal = (Number)currRow.getAttribute("TotalItmPriceQty");
                  Number disc = (Number)currRow.getAttribute("TotalDiscountItmPO");
                  matchAmt =  itmTotal.subtract(disc);
             
             }
        if(currRow.getAttribute("TaxAfterDiscFlg").toString().equalsIgnoreCase("N"))
        {
            
                 matchAmt =  (Number)currRow.getAttribute("TotalItmPriceQty");
            
            }
             
          /* if(taxableInTr.compareTo(matchAmt) != 0)
          {
              
                  String msg2 = resolvEl("#{bundle['MSG.295']}").toString();//Amount has been changed , please re-apply tax
                                                   FacesMessage message2 = new FacesMessage(msg2);
                  message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                   FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(null,message2);  
                                                   commitCount = 1;
              
              } */
        }
        }
        else if(currRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("I"))
        {
        Integer count = 0;
       ViewObjectImpl cpoitm = am.getMmCpoItm1();
            RowSetIterator rst = cpoitm.createRowSetIterator(null);
            if(rst.getRowCount() > 0)
            {
            while(rst.hasNext())
            {
              Row itmRow = rst.next();
              if(itmRow.getAttribute("TranReapplyTax").toString().equalsIgnoreCase("Y"))
                  count = count+1;
                
                }
                   
            }
            else
            {
                    String msg2 =resolvEl("#{bundle['MSG.297']}").toString();
                                                     FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                     FacesContext fc = FacesContext.getCurrentInstance();
                                                     fc.addMessage(null,message2);  
                
                
                
                }


        }
       
       if(commitCount == 0)
       {
         ViewObjectImpl trline = am.getMmCpoTrLines2();
        trline.executeQuery();
        am.getDBTransaction().commit();
        
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
           OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
           operationBinding1.execute();
           
           RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
           disclosedItem.setDisclosed(false);
           
        //Apply Wf Functions.
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String org_Id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId1=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                   WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                                   WfnoOp.getParamsMap().put("cld_id", cld_id);
                                   WfnoOp.getParamsMap().put("org_id", org_Id);
                                   WfnoOp.getParamsMap().put("doc_no", CPO_DOC_NO);
                                   WfnoOp.execute();
                                    String WfNum=null;  
                                     if(WfnoOp.getResult()!=null){
                                        WfNum= WfnoOp.getResult().toString();
                                     }
                                     if(WfNum!=null){                     
        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                                   usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                                   usrLevelOp.getParamsMap().put("CldId", cld_id);
                                   usrLevelOp.getParamsMap().put("OrgId", org_Id);
                                   usrLevelOp.getParamsMap().put("usr_id", usr_Id);
                                   usrLevelOp.getParamsMap().put("WfNum", WfNum);
                                   usrLevelOp.getParamsMap().put("rcptDocId", CPO_DOC_NO);
                                   usrLevelOp.execute();
                                   Integer level=-1;
                                     if(usrLevelOp.getResult()!=null && usrLevelOp.getResult()!=null){
                                    level= Integer.parseInt(usrLevelOp.getResult().toString());
                                     }
                                     if(level>-1){
        String action = "I";
        String remark = "A";                       
        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                      insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                      insTxnOp.getParamsMap().put("cld_id", cld_id);
                                      insTxnOp.getParamsMap().put("pOrgId", org_Id);
                                      insTxnOp.getParamsMap().put("rcpt_doc_no", CPO_DOC_NO);
                                      insTxnOp.getParamsMap().put("WfNum", WfNum);
                       insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                       insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                       insTxnOp.getParamsMap().put("levelFrm", level);
                       insTxnOp.getParamsMap().put("levelTo", level);
                       insTxnOp.getParamsMap().put("action", action);
                       insTxnOp.getParamsMap().put("remark", remark);
                       insTxnOp.getParamsMap().put("amount", new Number(0));
                       insTxnOp.getParamsMap().put("post", "S");
                       insTxnOp.execute();
                        Integer res =-1;  
                         if(insTxnOp.getResult()!=null){
                           res= Integer.parseInt(insTxnOp.getResult().toString());
                        } 
                        
                     if(res==1){ 
                         OperationBinding operationBindingCommit = getBindings().getOperationBinding("Commit");
                         operationBindingCommit.execute();
                     }
        
      
        StringBuilder msg=new StringBuilder("<html><body><p>"+resolvEl("#{bundle['MSG.298']}").toString()+"</p>");
        msg.append("<p>"+resolvEl("#{bundle['MSG.299']}").toString()+"<b>"+amtSp+"</b></p>");
        msg.append("<p>"+resolvEl("#{bundle['MSG.300']}").toString()+"<b>"+amtBs+"</b></p>");
        msg.append("</body></html>");
           
                                            FacesMessage message2 = new FacesMessage(msg.toString());
           message2.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null,message2); 
            }
              else
              {
                   FacesMessage message2 = new FacesMessage("Something went wrong(User Level in Workflow).Please contact to ESS.");
                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null,message2);                                  
               }
       }
       else
       {
               String msg2 = "Workflow not created for Cash Purchase.Please add new Workflow to Cash Purchase.";
                                                FacesMessage message2 = new FacesMessage(msg2);
               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null,message2);  
           }
        }
        }
        else
        {
                String msg2 = resolvEl("#{bundle['MSG.297']}").toString();
                                                 FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                 FacesContext fc = FacesContext.getCurrentInstance();
                                                 fc.addMessage(null,message2);   
            
            }
        }
            }
           
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    
    public void addItemAction(ActionEvent actionEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl cpo = am.getMmCpo1();
        Row cporow = cpo.getCurrentRow();
        if(cporow.getAttribute("CoaId") != null && cporow.getAttribute("CurrIdSp") !=null )
        {
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
        operationBinding.execute();
            disclosedItem.setDisclosed(true);
        }
        else
        {
         if(cporow.getAttribute("CoaId") == null)
         {
                 String msg2 = resolvEl("#{bundle['MSG.301']}").toString();
                                                  FacesMessage message2 = new FacesMessage(msg2);
                 message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                  FacesContext fc = FacesContext.getCurrentInstance();
                                                  fc.addMessage(getCoaidBind().getClientId(fc),message2);
             
             }
            if(cporow.getAttribute("CurrIdSp") == null)
            {
                
                    String msg2 = resolvEl("#{bundle['MSG.301']}").toString();
                                                     FacesMessage message2 = new FacesMessage(msg2);
                                                     message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                     FacesContext fc = FacesContext.getCurrentInstance();
                                                     fc.addMessage(currencyTransBind.getClientId(fc) ,message2);
                      
                
                }
            
            
            
            }
      

    }

    public void setDisclosedItem(RichShowDetail disclosedItem) {
        this.disclosedItem = disclosedItem;
    }

    public RichShowDetail getDisclosedItem() {
        return disclosedItem;
    }

    public void deleteSelectedItem(ActionEvent actionEvent) {
      MmCashPOAMImpl  am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
      ViewObjectImpl v = am.getMmCpoItm1();
      ViewObjectImpl cpoTr = am.getMmCpoTr2();
      ViewObjectImpl cpoTrLine =am.getMmCpoTrLines2();
        RowQualifier rqTax = new RowQualifier(cpoTr);
        RowQualifier rqTaxLine = new RowQualifier(cpoTrLine); 
                           
      RowSetIterator rit = v.createRowSetIterator(null);
      while(rit.hasNext())
      {
         Row row = rit.next();
        if(row.getAttribute("MarkedForDelete")!= null && row.getAttribute("MarkedForDelete").toString().equalsIgnoreCase("Y"))
        {
                if(row.getAttribute("ItmId")!=null){
                    rqTax.setWhereClause("DocId= '"+row.getAttribute("DocId").toString()+"' and ItmId= '"+row.getAttribute("ItmId").toString()+"'");
                    Row[] rTax=cpoTr.getFilteredRows(rqTax);
                    System.out.println("rTax.length---"+rTax.length);
                    if(rTax.length>0){
                        System.out.println("del tax line on item del");
                        System.out.println("taxrule idmmmmmmm"+rTax[0].getAttribute("TaxRuleId"));
                        rqTaxLine.setWhereClause("DocId= '"+row.getAttribute("DocId").toString()+"' and ItmId= '"+row.getAttribute("ItmId").toString()+"' and TaxRuleId= "+rTax[0].getAttribute("TaxRuleId")+"");
                        System.out.println(""+rqTaxLine.getExprStr());
                        Row[] rTaxLine=cpoTrLine.getFilteredRows(rqTaxLine);
                        System.out.println("rTaxLine.length---"+rTaxLine.length);
                        if(rTaxLine.length>0){
                            for(Row rT1 : rTaxLine){
                                rT1.remove();
                            }
                        }
                        rTax[0].remove();
                    }
                }
            row.remove();
            }
          }
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
        operationBinding1.execute();
        
       rit.closeRowSetIterator();
    
      v.setWhereClause(null);
       v.executeQuery();
        cpoTr.executeQuery();
       cpoTrLine.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormbind);
    }

    public void tranClassValueChange(ValueChangeEvent valueChangeEvent) {
     
    }

    public void itmIdValueChnge(ValueChangeEvent valueChangeEvent) {
        
        String itm = valueChangeEvent.getNewValue().toString();
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
      ViewObjectImpl e =   am.getLovItmIdForadd1();
       Row [] rws = e.getFilteredRows("ItmId", itm);
     Integer uom = Integer.parseInt(rws[0].getAttribute("UomClass").toString());
        ViewObjectImpl v = am.getMmCpoItm1();
        MmCpoItmVORowImpl r1 = (MmCpoItmVORowImpl)v.getCurrentRow();
        r1.refreshLov(uom);
        
    }
    
    
    public String exitButton() {
        
                          
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
        operationBinding1.execute();
        
      /*  setShowFields(true); */
        return "back";
    }

    public void editButton(ActionEvent actionEvent) {
        Integer pending=0;
                String retVal="Y";
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
      Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String org_Id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                OperationBinding chkUsr = getBindings().getOperationBinding("pendingUsrCheck");
                      chkUsr.getParamsMap().put("SlocId", sloc_id);
                      chkUsr.getParamsMap().put("CldId", cld_id);
                      chkUsr.getParamsMap().put("OrgId", org_Id);
                      chkUsr.getParamsMap().put("RcptDocNo", CPO_DOC_NO);
                      chkUsr.execute();
                                        
                      if(chkUsr.getResult()!=null){
                             pending= Integer.parseInt(chkUsr.getResult().toString());
                      }
                      if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(usr_Id)==0)
                      {
                          RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "E");
                          disclosedItem.setDisclosed(true);
                      }
                      else{               
                           String usrPend="Anonymous";
                        Row r[]=getAm().getLovUsrIdCreate().getFilteredRows("UsrId",pending);
                          if(r.length>0)
                              if(r[0].getAttribute("UsrName")!=null)
                        usrPend = (String)r[0].getAttribute("UsrName");
                        String msg2 = "This Order is pending at ["+usrPend+"] for approval, You cannot edit this.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null,message2);
                    }    
                
               
       
    }

    public void setItmFormbind(RichPanelFormLayout itmFormbind) {
        this.itmFormbind = itmFormbind;
    }

    public RichPanelFormLayout getItmFormbind() {
        return itmFormbind;
    }

    public void otherChargesLink(ActionEvent actionEvent) {
    showPopup(ocPopup, true);
    }

    public void setOcPopup(RichPopup ocPopup) {
        this.ocPopup = ocPopup;
    }

    public RichPopup getOcPopup() {
        return ocPopup;
    }

    public void ocPopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObject oc =am.getMmCpoOc2();
        Row currRw = oc.getCurrentRow();
        if(currRw.getAttribute("CoaId") == null){
            currRw.remove();
        }
        oc.executeQuery();
    }

    public void ocDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
        
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl v = am.getMmCpoOc2();
        Row rowOc =v.getCurrentRow();
        
            RowSetIterator rit = v.createRowSetIterator(null);
        Number num = new Number(0);
            while(rit.hasNext())
            {
               Row row = rit.next(); 
                if(row.getAttribute("TranType") != null)
                {
                   if(row.getAttribute("TranType").toString().equalsIgnoreCase("A"))
                   {   num = num.add((Number)row.getAttribute("OcAmtSp"));
                       }
                   else if(row.getAttribute("TranType").toString().equalsIgnoreCase("S"))
                   {
                       num = num.subtract((Number)row.getAttribute("OcAmtSp"));
                       }
                    
                    }
                }
                System.out.println("oc total amt-----a:"+num);
            Number totalCpoSpAmt = new Number(0);
            Number zero = new Number(0);
            if(zero.compareTo((Number)transSumSp.getValue()) == 0){
                totalCpoSpAmt = new Number(0);
            }else{
                totalCpoSpAmt = (Number)((Number)transSumSp.getValue()).minus((Number)tranDiscOnPoBind.getValue());
            }
            System.out.println("oc amt-----"+totalCpoSpAmt);
            Number ocAmtScore = num.add(totalCpoSpAmt);
            System.out.println("ocAmtScore    "+ocAmtScore);
           
            if(zero.compareTo(ocAmtScore) > 0){
                String msg2 = resolvEl("#{bundle['MSG.302']}").toString();
                                                               FacesMessage message2 = new FacesMessage(msg2);
                                                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                               FacesContext fc = FacesContext.getCurrentInstance();
                                                       fc.addMessage(null,message2);  
            }else{
           ViewObjectImpl cpo = am.getMmCpo1();
           Row rw = cpo.getCurrentRow();
           rw.setAttribute("Ocamt", num);

        }
        }
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

    public void addButtonOnOcPopup(ActionEvent actionEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl v = am.getMmCpoOc2();
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters1");
        operationBinding.execute();
        //   Row row = (Row)operationBinding.execute();
              
                     if (operationBinding.getErrors().size() == 0) {
                         ArrayList lst = new ArrayList(1);
                         lst.add(v.getCurrentRow().getKey());
                         getOcTableBind().setActiveRowKey(lst);
                     }
                     AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    }

    public void OcAmtSpValueChangeList(ValueChangeEvent valueChangeEvent) {
       Number num = (Number)valueChangeEvent.getNewValue();
       MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
       ViewObjectImpl v = am.getMmCpo1();
       Row row = v.getCurrentRow();
        ViewObjectImpl cpoOc = am.getMmCpoOc2();
        Row copOcrow = cpoOc.getCurrentRow();
       // Row row = cpo.getCurrentRow();
        String tranType = copOcrow.getAttribute("TranType").toString();
        Number totalCpoSpAmt = (Number)((Number)transSumSp.getValue()).minus((Number)tranDiscOnPoBind.getValue());
       Number conv = (Number)row.getAttribute("CurrConvFctr");
       Number amtBs = (Number)conv.mul(num);
        
      /* 
            ArrayList lst = new ArrayList(1);
            lst.add(v.getCurrentRow().getKey());
            getOcTableBind().setActiveRowKey(lst);
        */
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
      
       ViewObjectImpl e = am.getMmCpoOc2();
        Row currentRow = e.getCurrentRow();
        currentRow.setAttribute("OcAmtBs", amtBs);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
        

    }

    public void deleteOnOcPopup(ActionEvent actionEvent) {
    /*    
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
        operationBinding.execute(); */
    MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
    ViewObject oc =am.getMmCpoOc2();
    Row currRw = oc.getCurrentRow();
    if(currRw != null){
        currRw.remove();
    }
    oc.executeQuery();
    }

    public void applyTaxLinkOnitem(ActionEvent actionEvent) {
       // Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
          
        ViewObject vo = am.getMmCpo1();
        ViewObject poVo = am.getMmCpoItm1();
        
        Row itmCurr = poVo.getCurrentRow();
        if (itmCurr != null) {
            if (itmCurr.getAttribute("ItmPrice") != null || itmCurr.getAttribute("ItmPrice") != new Number(0) ||
                itmCurr.getAttribute("OrdQty") != null || itmCurr.getAttribute("OrdQty") != new Number(0)) {
               
               String flg = (String)itmCurr.getAttribute("TransTaxExmptFlg");
               if(flg.equals("N"))
               {
        String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
        ViewObjectImpl trVo = am.getMmCpoTr2();
        RowQualifier rqtr=new RowQualifier(trVo);
        String itmId = itmCurr.getAttribute("ItmId").toString();
        String itmuom = itmCurr.getAttribute("ItmUom").toString();
        rqtr.setWhereClause("DocId='"+docId+"' AND ItmId='"+itmId+"' and ItmUom='"+itmuom+"'");
        System.out.println("DocId="+docId+" and  ItmId="+itmId+"' and ItmUom='"+itmuom+"'");
        Row[] r=trVo.getFilteredRows(rqtr);
        System.out.println("No. of rows in tr of above ids="+r.length);
        String tflg= poVo.getCurrentRow().getAttribute("TranReapplyTax").toString();
        // System.out.println("Flag is="+tflg);
         if(r.length>0)
        {              
            itmtaxamt1=new Number(Integer.parseInt(r[0].getAttribute("TaxableAmt").toString()));
            taxRuleitem=(Integer)r[0].getAttribute("TaxRuleId");     
            taxExmpt=r[0].getAttribute("TaxExmptFlg").toString();
            taxruleflg=r[0].getAttribute("TaxRuleFlg").toString();
        }   
        if(r.length > 0 &&  tflg.equalsIgnoreCase("N"))
        {
            showPopup(taxPopup, true);
        }
        else{
            
            //remove tax if r.length>0 and tflg is "Y" 
            if(r.length > 0 &&  tflg.equalsIgnoreCase("Y"))
        {
             RowSetIterator rstr=trVo.createRowSetIterator(null);
             while(rstr.hasNext())
             {
                 Row rtr=rstr.next();
                 if(rtr.getAttribute("DocId").toString().equalsIgnoreCase(docId) && rtr.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) && rtr.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom))
                 {
                  //   System.out.println("Removing TR="+rtr.getKey());
                     rtr.remove();
                     }
                 }
             rstr.closeRowSetIterator();
            trVo.executeQuery();
        
         }
            
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
        operationBinding.execute();
            
            
            String docid = itmCurr.getAttribute("DocId").toString();
            String exmpt=itmCurr.getAttribute("TransTaxExmptFlg").toString();
            Number taxableamt =   (Number)itmCurr.getAttribute("TranItmAmtSp");
            Number taxableamtwd = (Number)itmCurr.getAttribute("TranPriceQty");
            
         //       ViewObjectImpl trVo = am.getMmCpoTr2();
                Row trRow = trVo.getCurrentRow();
                trRow.setAttribute("ItmId", itmId);
                trRow.setAttribute("TaxRuleFlg", "I");
                trRow.setAttribute("TaxExmptFlg",exmpt);
            String TaxAfterDiscFlg = vo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                if(TaxAfterDiscFlg.equalsIgnoreCase("Y"))
                {
                trRow.setAttribute("TaxableAmt", taxableamt);
                }
                else
                {
                 trRow.setAttribute("TaxableAmt", taxableamtwd);   
                    }
            
            
        showPopup(taxPopup, true);
        } 
         
        /* ViewObjectImpl cpo = am.getMmCpo1();
        Row cporow = cpo.getCurrentRow();
        String TaxAfterDiscFlg = cporow.getAttribute("TaxAfterDiscFlg").toString();
        ViewObjectImpl cpoitm = am.getMmCpoItm1();
        Row itmRow = cpoitm.getCurrentRow();
        String itmId = itmRow.getAttribute("ItmId").toString();
        
        ViewObjectImpl trTemp = am.getMmCpoTr2();
        RowSetIterator rit = trTemp.createRowSetIterator(null);
        while(rit.hasNext())
        {
        Row rw = rit.next();
            if(rw.getAttribute("ItmId") != null)
            {
            if(rw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("I") && rw.getAttribute("ItmId").toString().equalsIgnoreCase(itmId))
            {
                if(rw.getAttribute("TaxRuleId")!= null){
                      taxruleid = Integer.parseInt(rw.getAttribute("TaxRuleId").toString());
                    taxAbleAmoutForChange = (Number)rw.getAttribute("TaxableAmt"); // tax cancel check 18/04
                }
                rw.remove();
            }
            }
            }
        System.out.println("TaxRuleId   -- "+taxruleid+"taxAbleAmout     --"+taxAbleAmoutForChange);
   OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
        operationBinding.execute();
        
    String docid = itmRow.getAttribute("DocId").toString();
  
    Number taxableamt =   (Number)itmRow.getAttribute("TranItmAmtSp");
    Number taxableamtwd = (Number)itmRow.getAttribute("TranPriceQty");
    
        ViewObjectImpl trVo = am.getMmCpoTr2();
        Row trRow = trVo.getCurrentRow();
        trRow.setAttribute("ItmId", itmId);
        trRow.setAttribute("TaxRuleFlg", "I");
        if(TaxAfterDiscFlg.equalsIgnoreCase("Y"))
        {
        trRow.setAttribute("TaxableAmt", taxableamt);
        }
        else
        {
         trRow.setAttribute("TaxableAmt", taxableamtwd);   
            }
        showPopup(taxPopup, true); */
        } 
           else
           {
                   FacesMessage message =
                       new FacesMessage("Tax is not Applicable on this Item");
                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null, message); 
               }
           
            } else {
            FacesMessage message =
                new FacesMessage(resolvEl("#{bundle['MSG.408']}"));
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        }
    }

    public void setTaxPopup(RichPopup taxPopup) {
        this.taxPopup = taxPopup;
    }

    public RichPopup getTaxPopup() {
        return taxPopup;
    }

    public void taxPopupCancelList(PopupCanceledEvent popupCanceledEvent) throws SQLException {
        //if no tax was applied than delete current applied tax here.. and do not create new. else insert into tr rule line function will delete those rows itself.
    
        if(taxRuleitem==null)   { 
          System.out.println("No tax was applied before");
        ViewObjectImpl tr = getAm().getMmCpoTr2();
        String docId=tr.getCurrentRow().getAttribute("DocId").toString();
        ViewObjectImpl trline = getAm().getMmCpoTrLines2();
            ViewObjectImpl poitm = getAm().getMmCpoItm1();   
        String itm=null;
        String itmuom=null;
        itm = (String)poitm.getCurrentRow().getAttribute("ItmId");
        itmuom= (String)poitm.getCurrentRow().getAttribute("ItmUom");
                trline.executeQuery();
            getAm().getMmCpoTrLines1().executeQuery();
                getAm().getMmCpoTrLines2().executeQuery();
             
                
                
        RowSetIterator rtr=tr.createRowSetIterator(null);
        while(rtr.hasNext())
        {
        Row trow=rtr.next();
        if(trow.getAttribute("DocId").toString().equalsIgnoreCase(docId) && trow.getAttribute("ItmId").toString().equalsIgnoreCase(itm) && trow.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom))
        {
            trow.remove();
            }
        }
        
        rtr.closeRowSetIterator();
            tr.executeQuery();
            getAm().getMmCpoTr2().executeQuery();
        
        }
        TaxRuleId = 0;
        taxableAmount = new Number(0);
        Integer taxid1 = null;
        Number itmtaxamt=null;
        String taxflg="N";
        String taxexmpt="N";
        
        
       
                if(taxRuleitem!=null){  
                     System.out.println("TaxRuleItem not null="+taxRuleitem);
                                 //click on Select tax Itemwise
                         ViewObjectImpl po2 = getAm().getMmCpo1();
                         ViewObject poVo2 = getAm().getMmCpoItm1();
                         Row itmCurr2 = poVo2.getCurrentRow();
                         ViewObjectImpl trVo2 = getAm().getMmCpoTr2();
                         String itmIds = itmCurr2.getAttribute("ItmId").toString();
              if (itmCurr2 != null)
              {
               if (itmCurr2.getAttribute("ItmPrice") != null || itmCurr2.getAttribute("ItmPrice") != new Number(0) ||
             itmCurr2.getAttribute("OrdQty") != null || itmCurr2.getAttribute("OrdQty") != new Number(0)) 
               {     
             ViewObject vo2 = getAm().getMmCpo1();
             String docId2 = (vo2.getCurrentRow().getAttribute("DocId")).toString();   
             RowQualifier rqtr2=new RowQualifier(trVo2);
             ViewObject trlineVo=getAm().getMmCpoTrLines2();
             String itmId2 = itmCurr2.getAttribute("ItmId").toString();
             String itmuom2=itmCurr2.getAttribute("ItmUom").toString();
             rqtr2.setWhereClause("DocId='"+docId2+"' AND ItmId='"+itmId2+"' and ItmUom='"+itmuom2+"'");
             Row[] r2=trVo2.getFilteredRows(rqtr2);
           //  System.out.println("No of row in TR for this item and doc="+r2.length);
             if(r2.length>0)
             {
                 trlineVo.executeQuery();
                 getAm().getMmCpoTrLines1().executeQuery();
                 getAm().getMmCpoTrLines2().executeQuery();
                
                 RowSetIterator rstritm=trVo2.createRowSetIterator(null);
                 while(rstritm.hasNext())
                 {
                  Row rtrs=rstritm.next();
                     if(rtrs.getAttribute("DocId").toString().equalsIgnoreCase(docId2) && rtrs.getAttribute("ItmId").toString().equalsIgnoreCase(itmId2)
                     && rtrs.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom2))
                     {
                       System.out.println("Removing row from Tr="+rtrs.getKey());
                         rtrs.remove();
                         }
                     }
                 rstritm.closeRowSetIterator();
                 trVo2.executeQuery();
                 getAm().getMmCpoTr2().executeQuery();
             }    
                       
            
             //    System.out.println("Now row in TR for this item and docid is not avaliabale");   
             OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
             operationBinding.execute();
             
         } 
        }
         Row curr1 = trVo2.getCurrentRow();
         curr1.setAttribute("DocId", po2.getCurrentRow().getAttribute("DocId"));
         curr1.setAttribute("TaxRuleFlg", "I");
         curr1.setAttribute("ItmId",itmIds);
         curr1.setAttribute("TaxableAmt",itmtaxamt1);
         curr1.setAttribute("TaxExmptFlg", taxExmpt);
        //   System.out.println("New row created in tr and doc set");
         taxid1=taxRuleitem;
         itmtaxamt=itmtaxamt1;
         itmtaxamt1=null;
         taxRuleitem=null;
         taxflg="N";
         taxexmpt=taxExmpt;
         taxExmpt="N";
        }
         
        
        
        //value change listener--------
        if(taxid1!=null){
            System.out.println("Taxis not null="+taxid1);
        Row trcurr1 = getAm().getMmCpoTr2().getCurrentRow();
        trcurr1.setAttribute("TaxRuleId", taxid1);
        trcurr1.setAttribute("TaxRuleFlg", taxruleflg);
        Integer p_sloc_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer p_user_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String p_org_id1 = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId1=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId1=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        
        String p_doc_id1 = trcurr1.getAttribute("DocId").toString();
        String p_po_item_id1 = null;
        String p_po_item_uom1=null;
        Number p_curr_fctr1 = new Number(1);
        if (trcurr1.getAttribute("ItmId") != null) {
         p_po_item_id1 = trcurr1.getAttribute("ItmId").toString();
         p_po_item_uom1 = trcurr1.getAttribute("ItmUom").toString();
        }
        //  System.out.println("Tax id at last="+taxid1+" and taxable amt="+itmtaxamt+" and item id="+p_po_item_id1);
        //Number p_taxable_amount1 = (Number)trcurr1.getAttribute("TaxableAmt");
        Number p_taxable_amount1=itmtaxamt;
        String p_tax_rule_flg1 = taxruleflg;
        if (getAm().getMmCpo1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
         p_curr_fctr1 = (Number)getAm().getMmCpo1().getCurrentRow().getAttribute("CurrConvFctr");
        }
        
        //code for check duplicate record
        ViewObjectImpl lineimpl=getAm().getMmCpoTrLines2();
        RowQualifier rq=new RowQualifier(lineimpl);
        rq.setWhereClause("CldId='"+p_cldId1+"' and SlocId="+p_sloc_id1+" and OrgId='"+p_org_id1+"' and DocId='"+p_doc_id1+"' and ItmId='"+p_po_item_id1+"' and TaxRuleId="+taxid1);
        //  System.out.println("Querry executed to count row in trline");
        //  System.out.println("CldId='"+p_cldId1+"' and SlocId="+p_sloc_id1+" and OrgId='"+p_org_id1+"' and DocId='"+p_doc_id1+"' and ItmId='"+p_po_item_id1+"' and TaxRuleId="+taxid1);
        Row[] linerowcount=lineimpl.getFilteredRows(rq);
        System.out.println("no. of rows in trline for same data="+linerowcount.length);
        
        lineimpl.executeQuery();
        System.out.println("Tax rule flg="+p_tax_rule_flg1);
            BigDecimal ret1 =(BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_CPO.ins_cpo_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { p_sloc_id1, p_cldId1, p_hoOrgId1 , p_org_id1,p_doc_id1,p_po_item_id1,p_po_item_uom1,taxid1,p_user_id1,p_taxable_amount1,p_tax_rule_flg1,p_curr_fctr1,taxexmpt});

        /*  (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        new Object[] { p_sloc_id1,p_cldId1,p_hoOrgId1, p_org_id1, p_doc_id1, p_po_item_id1,p_po_item_uom1,taxid1,
                                                       p_user_id1, p_taxable_amount1, p_tax_rule_flg1,
                                                       p_curr_fctr1,taxexmpt }); */
        System.out.println("tax amt="+ret1);
        Number retVal1 = new Number(ret1);
        Number zero=new Number(0);
        trcurr1.setAttribute("TaxExmptFlg", taxflg);
        if(taxflg.equals("Y")){
         trcurr1.setAttribute("TaxAmt", zero);
         getAm().getMmCpo1().getCurrentRow().setAttribute("TranTaxOnPoSp", zero);   
        }
        else
        {
             trcurr1.setAttribute("TaxAmt", retVal1);
             if (p_tax_rule_flg1.equalsIgnoreCase("I")) {
                 getAm().getMmCpoItm1().getCurrentRow().setAttribute("NewTaxAmt", retVal1);
             } else if (p_tax_rule_flg1.equalsIgnoreCase("P")) {
                 getAm().getMmCpo1().getCurrentRow().setAttribute("TranTaxOnPoSp", retVal1);
             } 
         }
        
        }
        
        //getAm().getMmCpo1().executeQuery();
        getAm().getMmCpoTr2().executeQuery();
        getAm().getMmCpoTrLines1().executeQuery();
        getAm().getMmCpoTr1().executeQuery();
        getAm().getMmCpoTrLines2().executeQuery();
        System.out.println("Exit from cancel");
        //-------------
        
        
        
        
      // AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
      //  AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
      //  AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
      //  AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBsBind); 
        
        
     
        
    }

    public void taxDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
      /*   MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl tr = am.getMmCpoTr2();
       Row trRow = tr.getCurrentRow();
      // Integer taxRule = Integer.parseInt(trRow.getAttribute("TaxRuleId").toString());
      tr.executeQuery();
        taxruleid = 0;
       // am.getDBTransaction().postChanges(); */
        }
    }

    public void calculateTax(ActionEvent actionEvent) throws SQLException {
        
        
    }
    
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if(valueExp != null)
        {
         msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
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

    public void setTrLineTable(RichTable trLineTable) {
        this.trLineTable = trLineTable;
    }

    public RichTable getTrLineTable() {
        return trLineTable;
    }

    public void setItmSumtaxAmtBind(RichInputText itmSumtaxAmtBind) {
        this.itmSumtaxAmtBind = itmSumtaxAmtBind;
    }

    public RichInputText getItmSumtaxAmtBind() {
       
        return itmSumtaxAmtBind;
    }

    public void addDiscountLink(ActionEvent actionEvent) {
        showPopup(discountPopupBind, true);
    }

    public void discountPopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl v = am.getMmCpo1();
        Row row = v.getCurrentRow();
        Number n = new Number(0);
        row.setAttribute("DiscVal", n);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tranDiscOnPoBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totaldiscountBind);

    }

    public void setDiscountPopupBind(RichPopup discountPopupBind) {
        this.discountPopupBind = discountPopupBind;
    }

    public RichPopup getDiscountPopupBind() {
        return discountPopupBind;
    }

    public void DiscountPopupDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
       AdfFacesContext.getCurrentInstance().addPartialTarget(tranDiscOnPoBind);
     
       
        }
    }

    public void setTranDiscOnPoBind(RichInputText tranDiscOnPoBind) {
        this.tranDiscOnPoBind = tranDiscOnPoBind;
    }

    public RichInputText getTranDiscOnPoBind() {
        return tranDiscOnPoBind;
    }

  

    public void appyTaxOnPONew(ActionEvent actionEvent) {
       /*  Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        
        ViewObjectImpl trTemp = am.getMmCpoTr2();
        RowSetIterator rit = trTemp.createRowSetIterator(null);
        while(rit.hasNext())
        {
        Row rw = rit.next();
            if(rw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
            {
                if(rw.getAttribute("TaxRuleId")!=null){
            taxruleid = Integer.parseInt(rw.getAttribute("TaxRuleId").toString());
                    taxAbleAmoutForChange = (Number)rw.getAttribute("TaxableAmt"); // tax cancel check 18/04
                }
              rw.remove();
            }
            
            }
        rit.closeRowSetIterator();
        trTemp.executeQuery();
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
        operationBinding.execute();
        
        ViewObjectImpl cpoitm = am.getMmCpoItm1();
        Row itmRow = cpoitm.getCurrentRow();
        String docid = itmRow.getAttribute("DocId").toString();
        String itmId = itmRow.getAttribute("ItmId").toString();
        
        
        ViewObjectImpl cpo = am.getMmCpo1();
        Row cpoRow = cpo.getCurrentRow();
        Number itmTotal = (Number)cpoRow.getAttribute("TotalItmPriceQty");
         Number disc = (Number)cpoRow.getAttribute("TotalDiscountItmPO");
        Number taxableamt =  itmTotal.subtract(disc);
        String TaxAfterDiscFlg = cpoRow.getAttribute("TaxAfterDiscFlg").toString();
            ViewObjectImpl trVo = am.getMmCpoTr2();
            Row trRow = trVo.getCurrentRow();
            trRow.setAttribute("ItmId", "ALL");
            trRow.setAttribute("TaxRuleFlg", "P");
            if(TaxAfterDiscFlg.equalsIgnoreCase("Y"))
            {
            trRow.setAttribute("TaxableAmt", taxableamt);
            }
            else
            {
                    trRow.setAttribute("TaxableAmt", itmTotal);
                }
         showPopup(taxPopup, true); */
       ViewObject vo = getAm().getMmCpo1();
       String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
       ViewObjectImpl trVo = getAm().getMmCpoTr();
       RowQualifier rqtr=new RowQualifier(trVo);
       rqtr.setWhereClause("DocId='"+docId+"' and TaxRuleFlg='P'");
      // ViewObject trlinevo=getAm().getMmCpoTrLines();
       Row[] r=trVo.getFilteredRows(rqtr);
       ViewObjectImpl itmvo=getAm().getMmCpoItm1();
       String transflg= itmvo.getCurrentRow().getAttribute("TranReapplyTax").toString();
       vo.getCurrentRow().setAttribute("TransRadio","All");
       //check flag of yellow
       if(r.length>0)
       {
            taxRule=(Integer)r[0].getAttribute("TaxRuleId");
            vo.getCurrentRow().setAttribute("transTaxRuleId", taxRule);
            
            RowQualifier radiotr=new RowQualifier(trVo);
            radiotr.setWhereClause("DocId='"+docId+"' and TaxRuleFlg='I'");
            Row[] radiorw=trVo.getFilteredRows(radiotr);
            if(radiorw.length>0)
                vo.getCurrentRow().setAttribute("TransRadio","Exclude");
            else
            vo.getCurrentRow().setAttribute("TransRadio","All");
        }
       
       if(r.length>0 && transflg.equalsIgnoreCase("N"))
       {
           taxRule=(Integer)r[0].getAttribute("TaxRuleId");
           showPopup(powiseSelectTaxPop, true);
       }
       else    
       {   
           //Remove all rows from tr
           RowSetIterator rsi = trVo.createRowSetIterator(null);
                     while (rsi.hasNext()) {
                       Row rw = rsi.next();
                       if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId) && rw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P") )
                       {
                       }
                     }
           rsi.closeRowSetIterator();
           trVo.executeQuery();
       showPopup(powiseSelectTaxPop, true);
           }
    }

    public  void setTaxruleid(Integer taxruleid) {
        this.taxruleid = taxruleid;
    }

    public  Integer getTaxruleid() {
        return taxruleid;
    }

    public  void setCount(Integer count) {
        MmCashPoAddEditBean.count = count;
    }

    public  Integer getCount() {
        return count;
    }

    public void discountValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       Number discValue = (Number)object;
       Number zero = new Number(0);
       Number hund = new Number(100);
       MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
       ViewObjectImpl cpoItm = am.getMmCpoItm1();
       Row itmRow = cpoItm.getCurrentRow();
      /*  Number itmQty = (Number)itmRow.getAttribute("OrdQty");
       Number itmPrice = (Number)itmRow.getAttribute("ItmPrice");
       Number amt = (Number)itmQty.mul(itmPrice); */
       Number amt = (Number)((Number)orderQty.getValue()).multiply((Number)itmPrice.getValue());
      // String discType = itmRow.getAttribute("DiscType").toString();
       if(itmDicsTypeBind.getValue().equals("P"))
       {
         if(discValue.compareTo(hund) > 0)
         {
                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.303']}").toString(), null));           
             }
           }
       if(itmDicsTypeBind.getValue().equals("A"))
       {
           if(((Number)orderQty.getValue()).compareTo(zero) == 1 && ((Number)itmPrice.getValue()).compareTo(zero) == 1){
         if(discValue.compareTo(amt) > 0)
         {
                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.304']}").toString()+amt, null)); 
             }
           }
           }
       if(discValue.compareTo(zero) == -1)
       {
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.305']}").toString(), null)); 

           }
       
       
    }

    public void orderQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       Number orderQtyVal = (Number)object;
       Number zero = new Number(0);
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl cpo = am.getMmCpo1();
        ViewObjectImpl cpoItm = am.getMmCpoItm1();
        Row rowItm = cpoItm.getCurrentRow();
        Row row = cpo.getCurrentRow();
        Number currFactor = (Number)row.getAttribute("CurrConvFctr");
        Number tot = orderQtyVal.multiply((Number)itmPrice.getValue()).multiply(currFactor);
        /* Number transSumSp = (Number)row.getAttribute("TranSumItmAmtWTaxSp");
        System.out.println("transSumSp1-"+transSumSp); */
       // Number tot = currFactor.multiply((Number)transSumSp.getValue());
        Boolean qtyFlag = isPrecisionValid(26, 6, orderQtyVal);
        Boolean totFlag = isPrecisionValid(26, 6, tot);
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String P_ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String p_quot_item_id = null;
        if(rowItm.getAttribute("ItmId")!=null){
            p_quot_item_id = rowItm.getAttribute("ItmId").toString();
        }
        String flag = "Y";
        if(p_quot_item_id !=null && orderQtyVal != null){
                Date dt = (Date)row.getAttribute("CpoDt");
            Integer fy=(Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {cldId,p_org_id,dt}));
            flag=(String)callStoredFunction(Types.VARCHAR, "MM.PKG_MM_STK.IS_ORD_QTY_VALID(?,?,?,?,?,?,?)", new Object[]{p_sloc_id, cldId, P_ho_org_id, p_org_id,p_quot_item_id,fy,orderQtyVal});   
            }
            if(flag.equalsIgnoreCase("N")){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.306']}").toString(), null));                    
            }
        
       if(orderQtyVal.compareTo(zero) <= 0)
       {
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.307']}").toString(), null)); 

           }
        if(totFlag.equals(false)){
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.95']}").toString(),
                                                           null));
        }
    }

    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number itmPriceVal = (Number)object;
        Number zero = new Number(0);
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl cpo = am.getMmCpo1();
        Row row = cpo.getCurrentRow();
        Number currFactor = (Number)row.getAttribute("CurrConvFctr");
        Number tot = itmPriceVal.multiply((Number)orderQty.getValue()).multiply(currFactor);
      /*   Number transSumSp = (Number)row.getAttribute("TranSumItmAmtWTaxSp");
        System.out.println("transSumSp-"+transSumSp);
        Number tot = transSumSp.multiply(currFactor); */
        Boolean itmFlag = isPrecisionValid(26, 6, itmPriceVal);
        Boolean totFlag = isPrecisionValid(26, 6, tot);
        if(itmPriceVal.compareTo(zero) <= 0)
        {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.309']}").toString(), null)); 

            }
        if(totFlag.equals(false)){
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.95']}").toString(),
                                                           null));
        }
    }
    
    
    protected Object callStoredFunctionHistory(String stmt, Object[] bindVars) {

           CallableStatement st = null;
           try {
               /** 1. Create a JDBC CallabledStatement */
               MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
               st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
               /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
               st.setObject(1, bindVars[0]);
               st.setObject(2, bindVars[1]);
               st.setObject(3, bindVars[2]);
               st.setObject(4, bindVars[3]);
               st.registerOutParameter(5, NUMBER);
               st.registerOutParameter(6, NUMBER);
               st.registerOutParameter(7, NUMBER);
               st.setObject(8, bindVars[4]);
               st.setObject(9, bindVars[5]);
               st.setObject(10, bindVars[6]);
              
               /** 5. Set the value of user-supplied bind vars in the stmt */
               st.executeUpdate();

               try {
                   setAvl_stk((BigDecimal)st.getObject(5));
                   setReq_stk((BigDecimal)(st.getObject(6)));
                   setOrd_stk((BigDecimal)st.getObject(7));
               } catch (NullPointerException e) {
                   System.out.println(e);
                   e.printStackTrace();
               }

               return null;
           } catch (SQLException e) {
               int end = e.getMessage().indexOf("\n");

               throw new JboException(e.getMessage());
           } finally {
               if (st != null) {
                   try {
                       /** 7. Close the statement */
                       st.close();
                   } catch (SQLException e) {

                   }
               }
           }
       }


    public void setAvl_stk(BigDecimal Avl_stk) {
        this.Avl_stk = Avl_stk;
    }

    public BigDecimal getAvl_stk() {
        return Avl_stk;
    }

    public void setReq_stk(BigDecimal Req_stk) {
        this.Req_stk = Req_stk;
    }

    public BigDecimal getReq_stk() {
        return Req_stk;
    }

    public void setOrd_stk(BigDecimal Ord_stk) {
        this.Ord_stk = Ord_stk;
    }

    public BigDecimal getOrd_stk() {
        return Ord_stk;
    }

    public void historyIcon(ActionEvent actionEvent) {
       
        //showPopup(historyPopupBind, true);
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
              Date date = new Date();
              date = (Date)date.getCurrentDate();
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl cpoItm = am.getMmCpoItm1();
        Row itmRow = cpoItm.getCurrentRow();
        if(itmRow.getAttribute("ItmId") != null){
            String itmId=itmRow.getAttribute("ItmId").toString();
            Date dt = (Date)getAm().getMmCpo1().getCurrentRow().getAttribute("CpoDt");
            Integer fy=(Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {cldId,p_org_id,dt}));
              callStoredFunctionHistory("MM.PKG_MM_STK.GET_STK(?,?,?,?,?,?,?,?,?,?)",
                                        new Object[] { SlocId, cldId, itmId,fy,p_org_id, date, null });
             
        showPopup(historyPopupBind, true);
        }else{
            String msg2 =resolvEl("#{bundle['MSG.310']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }


    public void setHistoryPopupBind(RichPopup historyPopupBind) {
        this.historyPopupBind = historyPopupBind;
    }

    public RichPopup getHistoryPopupBind() {
        return historyPopupBind;
    }

    public void tranItmNameValueChange(ValueChangeEvent valueChangeEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl e =   am.getLovItmIdForadd1();
        Row [] rws = e.getFilteredRows("ItmDesc", valueChangeEvent.getNewValue().toString());
        String itm =  rws[0].getAttribute("ItmId").toString();
        String uom = rws[0].getAttribute("UomPur").toString();
        Integer uomclass = Integer.parseInt(rws[0].getAttribute("UomClass").toString());
        itmUomBind.setValue(uom);
        itmIdBind.setValue(itm);
        ViewObjectImpl v = am.getMmCpoItm1();
        MmCpoItmVORowImpl r1 = (MmCpoItmVORowImpl)v.getCurrentRow();
        r1.refreshLov(uomclass);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public void setItmUomBind(RichSelectOneChoice itmUomBind) {
        this.itmUomBind = itmUomBind;
    }

    public RichSelectOneChoice getItmUomBind() {
        return itmUomBind;
    }

    public void setItmIdBind(RichSelectOneChoice itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichSelectOneChoice getItmIdBind() {
        return itmIdBind;
    }

    public void discountValueChange(ValueChangeEvent valueChangeEvent) {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl itmVo = am.getMmCpoItm1();
        Row row = itmVo.getCurrentRow();
        String discType = row.getAttribute("DiscType").toString();
        Number discValue = (Number)valueChangeEvent.getNewValue();
        Number zero = new Number(0);
              Number hund = new Number(100);
        Number itmQty = (Number)row.getAttribute("OrdQty");
               Number itmPrice = (Number)row.getAttribute("ItmPrice");
        Number amt = (Number)itmQty.mul(itmPrice);
        if(discType.equalsIgnoreCase("P"))
               {
                 if(discValue.compareTo(hund) >= 0)
                 {              String msg2 =resolvEl("#{bundle['MSG.308']}").toString();
                                 FacesMessage message2 = new FacesMessage(msg2);
                                 message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                 FacesContext fc = FacesContext.getCurrentInstance();
                         fc.addMessage(getDiscValbind().getClientId(fc),message2);
                     }
                   }
        
        if(discType.equalsIgnoreCase("A"))
              {
                if(discValue.compareTo(amt) >= 0)
                {        String msg2 =resolvEl("#{bundle['MSG.262']}").toString()+amt;
                                 FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                 FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(getDiscValbind().getClientId(fc),message2);
                    }
                  }
        
        if(discValue.compareTo(zero) < 0)
               {
                       String msg2 =resolvEl("#{bundle['MSG.305']}").toString();
                                                        FacesMessage message2 = new FacesMessage(msg2);
                       message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                        FacesContext fc = FacesContext.getCurrentInstance();
                                                        fc.addMessage(getDiscValbind().getClientId(fc),message2);

                   }
        
    }

    public void disctypeValueChange(ValueChangeEvent valueChangeEvent) {
       /*  MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl itmVo = am.getMmCpoItm1();
         Row row = itmVo.getCurrentRow();
       Number discVal = (Number)row.getAttribute("DiscVal");
        String discType = valueChangeEvent.getNewValue().toString(); */
        
    }

    public void setDiscValbind(RichInputText discValbind) {
        this.discValbind = discValbind;
    }

    public RichInputText getDiscValbind() {
        return discValbind;
    }

    public void setItmTableBind(RichTable itmTableBind) {
        this.itmTableBind = itmTableBind;
    }

    public RichTable getItmTableBind() {
        return itmTableBind;
    }

    public void setCoaidBind(RichSelectOneChoice coaidBind) {
        this.coaidBind = coaidBind;
    }

    public RichSelectOneChoice getCoaidBind() {
        return coaidBind;
    }

    public void setCurrIdBind(RichSelectOneChoice currIdBind) {
        this.currIdBind = currIdBind;
    }

    public RichSelectOneChoice getCurrIdBind() {
        return currIdBind;
    }

    public void setDiscValonPopup(RichInputText discValonPopup) {
        this.discValonPopup = discValonPopup;
    }

    public RichInputText getDiscValonPopup() {
        return discValonPopup;
    }

    public void discValonPopupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       Number discval = (Number)object;
       Number zero = new Number(0);
       Number hundred = new Number(100);
       MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
       ViewObjectImpl cpo = am.getMmCpo1();
       Row cporow = cpo.getCurrentRow();
      // String disctype = cporow.getAttribute("DiscType").toString();
                /* Number totalamt = (Number)cporow.getAttribute("TotalItmPriceQty");
                Number totaldisc1 = (Number)cporow.getAttribute("TotalDiscountItmPO");
                Number totaldisc = totaldisc1.add(discval); */
       Number transSpAmt = (Number)transSumSp.getValue();
                if(disctypeCpoBind.getValue().equals("P"))
                {
                  if(discval.compareTo(hundred) > 0)
                  {
                          throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.303']}").toString(), null));           
                      }
                    }
                if(disctypeCpoBind.getValue().equals("A"))
                {
                  if(discval.compareTo(transSpAmt) > 0)
                  {
                          throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.262']}").toString()+transSpAmt, null)); 
                      }
                    }
                if(discval.compareTo(zero) == -1)
                {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.305']}").toString(), null)); 

                    }
    
       
    }

    public void disctypeOnPopupValueChange(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(discValonPopup);
       MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
       ViewObjectImpl cpo = am.getMmCpo1();
       Row row = cpo.getCurrentRow();
       Number zero = new Number(0);
       row.setAttribute("DiscVal", zero);
       discValonPopup.setValue(new Number(0));
       AdfFacesContext.getCurrentInstance().addPartialTarget(discountFormBind);
       AdfFacesContext.getCurrentInstance().addPartialTarget(discValonPopup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountPopupBind);
       
    }

    public void setTotaldiscountBind(RichInputText totaldiscountBind) {
        this.totaldiscountBind = totaldiscountBind;
    }

    public RichInputText getTotaldiscountBind() {
        return totaldiscountBind;
    }

    public void ocSpValuevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number orderQtyVal = (Number)object;
        Number zero = new Number(0);
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl cpo = am.getMmCpo1();
        ViewObjectImpl cpoOc = am.getMmCpoOc2();
        Row copOcrow = cpoOc.getCurrentRow();
        Row row = cpo.getCurrentRow();
        String tranType = copOcrow.getAttribute("TranType").toString();
        Number totalCpoSpAmt = (Number)((Number)transSumSp.getValue()).minus((Number)tranDiscOnPoBind.getValue());
        Number currFactor = (Number)row.getAttribute("CurrConvFctr");
        
         Number tot = orderQtyVal.multiply(currFactor);
         Boolean qtyFlag = isPrecisionValid(26, 6, orderQtyVal);
         Boolean totFlag = isPrecisionValid(26, 6, tot);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
        System.out.println("trns type----"+tranType);
        System.out.println("trns bind type----"+transTypeBind.getValue());
        System.out.println("toto--------"+totalCpoSpAmt);
         if(transTypeBind.getValue().equals(1) && orderQtyVal.compareTo(totalCpoSpAmt) >= 0){
             System.out.println("trns type--inside--"+tranType);
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.302']}").toString(), null)); 
         }
        if(orderQtyVal.compareTo(zero) <= 0)
        {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.265']}").toString(), null)); 

            }
         if(totFlag.equals(false)){
              throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.95']}").toString(),
                                                            null));
         }
    }

    public void setCoaIdOcBind(RichSelectOneChoice coaIdOcBind) {
        this.coaIdOcBind = coaIdOcBind;
    }

    public RichSelectOneChoice getCoaIdOcBind() {
        return coaIdOcBind;
    }

    public void setOrderQty(RichInputText orderQty) {
        this.orderQty = orderQty;
    }

    public RichInputText getOrderQty() {
        return orderQty;
    }

    public void setItmPrice(RichInputText itmPrice) {
        this.itmPrice = itmPrice;
    }

    public RichInputText getItmPrice() {
        return itmPrice;
    }

    public void setTransSumSp(RichInputText transSumSp) {
        this.transSumSp = transSumSp;
    }

    public RichInputText getTransSumSp() {
        return transSumSp;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void setOcAmtSpBind(RichInputText ocAmtSpBind) {
        this.ocAmtSpBind = ocAmtSpBind;
    }

    public RichInputText getOcAmtSpBind() {
        return ocAmtSpBind;
    }

    public void setDisctypeCpoBind(RichSelectOneRadio disctypeCpoBind) {
        this.disctypeCpoBind = disctypeCpoBind;
    }

    public RichSelectOneRadio getDisctypeCpoBind() {
        return disctypeCpoBind;
    }

    public void setItmDicsTypeBind(RichSelectOneRadio itmDicsTypeBind) {
        this.itmDicsTypeBind = itmDicsTypeBind;
    }

    public RichSelectOneRadio getItmDicsTypeBind() {
        return itmDicsTypeBind;
    }

    public void applyTaxValueChangeListener(ValueChangeEvent vce) throws SQLException{
        if (vce.getNewValue() != null) {
            Integer taxid = Integer.parseInt(vce.getNewValue().toString());

            Row trcurr = getAm().getMmCpoTr2().getCurrentRow();
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

            
            String p_doc_id = trcurr.getAttribute("DocId").toString();
            String p_po_item_id = null;
            String p_po_item_uom = null;
            Number p_curr_fctr = new Number(1);
            String taxexm="N";
            if (trcurr.getAttribute("ItmId") != null) {
                p_po_item_id = trcurr.getAttribute("ItmId").toString();
            }
            if(trcurr.getAttribute("TaxExmptFlg") != null)
            {
                taxexm=trcurr.getAttribute("TaxExmptFlg").toString();
                }
            if (trcurr.getAttribute("ItmUom") != null) {
                p_po_item_uom = trcurr.getAttribute("ItmUom").toString();
            }
            Number p_taxable_amount = (Number)trcurr.getAttribute("TaxableAmt");
            trcurr.setAttribute("TaxRuleFlg","I");
            if (getAm().getMmCpo1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr = (Number)getAm().getMmCpo1().getCurrentRow().getAttribute("CurrConvFctr");
            }
            System.out.println("Tax rule flg=I");
            BigDecimal ret =(BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_CPO.ins_cpo_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { p_sloc_id, p_cldId, p_hoOrgId , p_org_id,p_doc_id,p_po_item_id,p_po_item_uom,taxid,p_user_id,p_taxable_amount,"I",p_curr_fctr,"N"});
                /* (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                               new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,p_po_item_uom,taxid,
                                                              p_user_id, p_taxable_amount, "I",
                                                              p_curr_fctr,taxexm }); */
            System.out.println("Tax ret="+ret);
            Number retVal = new Number(ret);
            String flg="N";
            Number zero=new Number(0);
            getAm().getMmCpoTrLines2().executeQuery();
            if(trcurr.getAttribute("TaxExmptFlg")!=null)
           flg=trcurr.getAttribute("TaxExmptFlg").toString();
            System.out.println("trlines current TaxExmptFlg ="+flg);
            if(flg.equals("Y"))
            {
                trcurr.setAttribute("TaxAmt",zero);
                getAm().getMmCpo1().getCurrentRow().setAttribute("TranTaxOnPoSp", zero);
            }
            else
            {
            trcurr.setAttribute("TaxAmt", retVal);
                getAm().getMmCpoItm1().getCurrentRow().setAttribute("NewTaxAmt", retVal);
          
            }
        }
         AdfFacesContext.getCurrentInstance().addPartialTarget(trLineTable);
       /*  MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        if(vce.getNewValue()!=null){
        System.out.println("inside tax vcl");
            Integer taxruleId = Integer.parseInt(vce.getNewValue().toString());
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String porgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        
        
        ViewObjectImpl itmVo = am.getMmCpoItm1();
        Row itmRow = itmVo.getCurrentRow();
        ViewObjectImpl mmcpo =am.getMmCpo1();
        Row mmcopRow = mmcpo.getCurrentRow();
        
        ViewObjectImpl trVo = am.getMmCpoTr2();
        Row trRow = trVo.getCurrentRow();
        Number taxableamt =   (Number)trRow.getAttribute("TaxableAmt");
        Number currfactr = (Number)mmcopRow.getAttribute("CurrConvFctr");
        String taxRuleFlg =   trRow.getAttribute("TaxRuleFlg").toString();
        String docId = trRow.getAttribute("DocId").toString();
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String p_ho_org_id= (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String itmId = null;
        if(trRow.getAttribute("ItmId") != null)
        {
          itmId =   trRow.getAttribute("ItmId").toString();    
         
         }
            System.out.println("-----"+pusrId+"--------"+pslocId+"-------"+porgId+"------"+taxableamt+"-----"+currfactr+"-----"+taxRuleFlg+"--------"+docId+"---------"+itmId+"-------");
      
            System.out.println("-----"+pusrId+"--------"+pslocId+"-------"+porgId+"------"+taxableamt+"-----"+currfactr+"-----"+taxRuleFlg+"--------"+docId+"---------"+itmId+"-------"+taxruleId);
        BigDecimal tax = (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_CPO.ins_cpo_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { pslocId, cldId, p_ho_org_id , porgId,docId,itmId,taxruleId,pusrId,taxableamt,taxRuleFlg,currfactr,"N"});
        System.out.println("tax"+tax);
        Number taxnum = new Number(tax);
        trRow.setAttribute("TaxAmt", taxnum);
       // }
        }
        ViewObjectImpl trLine = am.getMmCpoTrLines2();
        trLine.executeQuery();
         AdfFacesContext.getCurrentInstance().addPartialTarget(trLineTable); */
        
    }
    public MmCashPOAMImpl getAm() {
        return (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
    }
    public void setDiscountFormBind(RichPanelFormLayout discountFormBind) {
        this.discountFormBind = discountFormBind;
    }

    public RichPanelFormLayout getDiscountFormBind() {
        return discountFormBind;
    }

    public void setTransTypeBind(RichSelectOneChoice transTypeBind) {
        this.transTypeBind = transTypeBind;
    }

    public RichSelectOneChoice getTransTypeBind() {
        return transTypeBind;
    }

    public void tranTypeVCL(ValueChangeEvent valueChangeEvent) {
    AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
    AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    AdfFacesContext.getCurrentInstance().addPartialTarget(ocPopup);
    AdfFacesContext.getCurrentInstance().addPartialTarget(panelColletnBind);
    }
    /* public void cpoDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
          Date currDt= (Date)Date.getCurrentDate();
          Date cpoDate=(Date)object;
          String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
          
         java.sql.Date s=(java.sql.Date)callStoredFunction(Types.DATE,"APP.PKG_APP.GET_ORG_FY_START_DATE(?,?,?)", new Object[]{currDt,pOrgId,"FY"});
         Date strtDate=new Date(s);
         System.out.println("Start Date of FY:"+strtDate );
         if(strtDate!=null){
          if(cpoDate.compareTo(strtDate)==-1){
             String msg2 =resolvEl("#{bundle['MSG.311']}").toString();
             FacesMessage message2 = new FacesMessage(msg2);
             message2.setDetail(resolvEl("#{bundle['MSG.312']}").toString());
             message2.setSeverity(FacesMessage.SEVERITY_ERROR);
             throw new ValidatorException(message2);
          }
         }
    } */
    
    
    public void cpoDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
         Date currDt = (Date)Date.getCurrentDate();
         Date formDate = (Date)object;
         String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
         String pSloc = (String)resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
         Object oDt= callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { pSloc,pOrgId,formDate });
         if(oDt==null){
             String msg2 = "Financial Year is not defined.";
             FacesMessage message2 = new FacesMessage(msg2);
             message2.setSeverity(FacesMessage.SEVERITY_ERROR);
             throw new ValidatorException(message2);
         }else if("Y".equalsIgnoreCase(oDt.toString())){
       //  java.sql.Date s =(java.sql.Date)oDt;
           //  Date strtDate = new Date(s);
             //System.out.println(s + "Start Date of FY:" + (Date)strtDate);
             SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

          /*    if (strtDate != null) {
                 if (poDate.compareTo(strtDate) == -1) {
           */           String msg2 = resolvEl("#{bundle['MSG.311']}");
                     FacesMessage message2 = new FacesMessage(msg2);
                    // message2.setDetail(resolvEl("#{bundle['MSG.463']}") + s);
                     message2.setDetail(msg2);
                     message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                     throw new ValidatorException(message2);
                 }
         }

    public void setTaxAbleAmoutForChange(Number taxAbleAmoutForChange) {
        this.taxAbleAmoutForChange = taxAbleAmoutForChange;
    }

    public Number getTaxAbleAmoutForChange() {
        return taxAbleAmoutForChange;
    }

    public void setPanelColletnBind(RichPanelCollection panelColletnBind) {
        this.panelColletnBind = panelColletnBind;
    }

    public RichPanelCollection getPanelColletnBind() {
        return panelColletnBind;
    }

    public void setPowiseSelectTaxPop(RichPopup powiseSelectTaxPop) {
        this.powiseSelectTaxPop = powiseSelectTaxPop;
    }

    public RichPopup getPowiseSelectTaxPop() {
        return powiseSelectTaxPop;
    }

    public void applyTaxPoWiseDL(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void powiseCancelButton(ActionEvent actionEvent) {
        powiseSelectTaxPop.hide();
    }

    public void setApplyTaxRadioBind(RichSelectOneRadio applyTaxRadioBind) {
        this.applyTaxRadioBind = applyTaxRadioBind;
    }

    public RichSelectOneRadio getApplyTaxRadioBind() {
        return applyTaxRadioBind;
    }

    public void setSelectTaxPoBind(RichSelectOneChoice selectTaxPoBind) {
        this.selectTaxPoBind = selectTaxPoBind;
    }

    public RichSelectOneChoice getSelectTaxPoBind() {
        return selectTaxPoBind;
    }

    public void poWiseSelectTaxVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void applyTaxButtonAL(ActionEvent actionEvent) throws SQLException {  
        // System.out.println("new Taxrule="+selectTaxPoBind.getValue());
         ViewObjectImpl povo = getAm().getMmCpo1();
        //   System.out.println("Tax rule according to po="+povo.getCurrentRow().getAttribute("transTaxRuleId"));
         ViewObjectImpl itmvo = getAm().getMmCpoItm1();
         ViewObjectImpl trVo = getAm().getMmCpoTr();
        ViewObjectImpl cpoTr1 = getAm().getMmCpoTr1();
         ViewObjectImpl trLine = getAm().getMmCpoTrLines();
         ViewObjectImpl trVonew = getAm().getMmCpoTr2();
         ViewObjectImpl trLinenew = getAm().getMmCpoTrLines2();
         ViewObjectImpl trLinevo1 = getAm().getMmCpoTrLines1();
         Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
         Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
         String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
         String p_hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
         String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
             String taxid=null;
           if(selectTaxPoBind.getValue()!=null && selectTaxPoBind.getValue().toString().length()>0)
           {
           taxid= selectTaxPoBind.getValue().toString();
           String radioflg=povo.getCurrentRow().getAttribute("TransRadio").toString();
               String docId=povo.getCurrentRow().getAttribute("DocId").toString();
             System.out.println("docid="+docId);
               if(radioflg.equals("All"))
               {
            System.out.println("Condition for radio=all");
               String removedItmid=null;
               String removedItmUom=null;
               String removedExmptflg=null;
                  //add rows in tr according to itemvo
                   RowSetIterator rsitm = itmvo.createRowSetIterator(null);
                             while (rsitm.hasNext()) {
                               Row rw = rsitm.next(); 
                               if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId))
                               {
                               System.out.println("Row matched in itm with docid="+rw.getAttribute("DocId"));
                                   String itmdocId=rw.getAttribute("DocId").toString();
                                   String itmId=rw.getAttribute("ItmId").toString();
                                   String itmUom=rw.getAttribute("ItmUom").toString();
                                   removedItmid=null;
                                   removedItmUom=null;
                                   removedExmptflg=null;
                                 
                                   //remove this row from tr
                                   RowSetIterator rsitr = trVo.createRowSetIterator(null);
                                             while (rsitr.hasNext()) {
                                               Row trrw = rsitr.next();
                                               if (trrw.getAttribute("DocId").toString().equalsIgnoreCase(docId))
                                               {
                                              //     System.out.println("in tr row matched to doc");
                                                   if(trrw.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) && trrw.getAttribute("ItmUom").toString().equalsIgnoreCase(itmUom))
                                                   {
                                                   removedItmid=trrw.getAttribute("ItmId").toString();
                                                   removedItmUom=trrw.getAttribute("ItmUom").toString();
                                                   removedExmptflg=trrw.getAttribute("TaxExmptFlg").toString(); 
                                                 RowSetIterator itrline=trLinevo1.createRowSetIterator(null);
                                                 while(itrline.hasNext())
                                                 {
                                                     Row trlinerow=itrline.next();
                                                  if(trlinerow.getAttribute("DocId").toString().equals(docId)
                                                      && trlinerow.getAttribute("ItmId").toString().equals(itmId)
                                                      && trlinerow.getAttribute("ItmUom").toString().equals(itmUom)
                                                      /* && trlinerow.getAttribute("TaxRuleId").toString().equals(trrw.getAttribute("TaxRuleId") )*/)
                                                  {
                                                         // trlinerow.remove();
                                                      }
                                                     }
                                                 
                                                       itrline.closeRowSetIterator();
                                                       trLinevo1.executeQuery();
                                                   trLine.executeQuery();
                                                       trLinenew.executeQuery();
                                                   
                                                   trrw.remove();
                                                   }
                                                  
                                               }
                                             }
                                   rsitr.closeRowSetIterator();
                                  trVo.executeQuery();
                                  trVonew.executeQuery();
                                   //check if tax should be applied on this item or not.
                                   String ex="Y";
                                   if(removedExmptflg!=null)
                                       ex=removedExmptflg;
                                   else if(rw.getAttribute("TransTaxExmptFlg")!=null)
                                   ex = (String)rw.getAttribute("TransTaxExmptFlg");
                                   if(ex.equals("Y"))
                                   {}
                                   else
                                   {
                              Row newrow= trVo.createRow();
                              System.out.println("new row created in tr");
                                 String taxruleflg="P";
                                Number p_curr_fctr=new Number(1);
                                   if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                       p_curr_fctr = (Number)povo.getCurrentRow().getAttribute("CurrConvFctr");
                                   }
                                   newrow.setAttribute("CldId",p_cldId);
                                   newrow.setAttribute("SlocId",p_sloc_id);
                                   newrow.setAttribute("OrgId",p_org_id);
                                   newrow.setAttribute("DocId",docId);
                                   newrow.setAttribute("ItmId",itmId);
                                   newrow.setAttribute("ItmUom",itmUom);
                                   newrow.setAttribute("TaxRuleId",selectTaxPoBind.getValue());
                                   newrow.setAttribute("TaxRuleFlg",taxruleflg);
                                   
                                   if(removedExmptflg!=null)
                                       newrow.setAttribute("TaxExmptFlg",removedExmptflg);
                                   else
                                   newrow.setAttribute("TaxExmptFlg",rw.getAttribute("TransTaxExmptFlg"));
                                  
                                   newrow.setAttribute("UsrIdCreate",p_user_id);   
                                   //TaxableAmt
                                   String afterdisc=povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                                   if(afterdisc.equals("N"))
                                           newrow.setAttribute("TaxableAmt",rw.getAttribute("TranPriceQty"));  
                                   else
                                       newrow.setAttribute("TaxableAmt",rw.getAttribute("TranItmAmtSp"));  

                                   Number p_taxable_amount = (Number)newrow.getAttribute("TaxableAmt");
                                   System.out.println("Tax rule flg="+taxruleflg);
                                    //create trlines
                                       Number taxAmt = new Number(0);
                                BigDecimal ret =(BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_CPO.ins_cpo_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { p_sloc_id, p_cldId, p_hoOrgId , p_org_id,docId,itmId,itmUom,taxid,p_user_id,p_taxable_amount,taxruleflg,p_curr_fctr,newrow.getAttribute("TaxExmptFlg")});
                                       /*  (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                       new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, docId, itmId,itmUom,taxid,
                                                                                      p_user_id, p_taxable_amount, taxruleflg,
                                                                                      p_curr_fctr,removedExmptflg}); */
                                   
                                   
                                   
                                   
                                       if(ret!=null){
                                           try{
                                         taxAmt = new Number(ret);
                                             }catch(Exception e){
                                                 e.printStackTrace();
                                             }
                                           
                                       }
                                   
                                   adfLog.info(":::::::   "+taxAmt);
                                   
                                   newrow.setAttribute("TaxAmt", taxAmt);    
                                   getAm().getMmCpo1().getCurrentRow().setAttribute("TranTaxOnPoSp",taxAmt);    
                        trVo.insertRow(newrow);   
                     System.out.println("Row inserted");
                                   }
                               }
                             }
                   rsitm.closeRowSetIterator();
                 trVo.executeQuery();
                       cpoTr1.executeQuery();
                trLine.executeQuery();               
                 trVonew.executeQuery();
                 trLinenew.executeQuery();
                       trLinevo1.executeQuery();
                //  System.out.println("mission Successfull");
                   }
               else
               {
                   //Tax for Exclude condition 
                   String removedItmid=null;
                   String removedItmUom=null;
                   String removedExmptflg=null;
                      //add rows in tr according to itemvo
                       RowSetIterator rsitm = itmvo.createRowSetIterator(null);
                                 while (rsitm.hasNext()) {
                                   Row rw = rsitm.next(); 
                                   if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId))
                                   {
                                       String itmdocId=rw.getAttribute("DocId").toString();
                                       String itmId=rw.getAttribute("ItmId").toString();
                                       String itmUom=rw.getAttribute("ItmUom").toString();
                                       removedItmid=null;
                                       removedItmUom=null;
                                       removedExmptflg=null;
                                     
                                       //remove this row from tr
                                       RowSetIterator rsitr = trVo.createRowSetIterator(null);
                                                 while (rsitr.hasNext()) {
                                                   Row trrw = rsitr.next();
                                                   if (trrw.getAttribute("DocId").toString().equalsIgnoreCase(docId))
                                                   {
                                                       if(trrw.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) && trrw.getAttribute("ItmUom").toString().equalsIgnoreCase(itmUom) && trrw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
                                                       {
                                                       removedItmid=trrw.getAttribute("ItmId").toString();
                                                       removedItmUom=trrw.getAttribute("ItmUom").toString();
                                                       removedExmptflg=trrw.getAttribute("TaxExmptFlg").toString();
                                                       
                                                       
                                                                 trLinevo1.executeQuery();
                                                             trLine.executeQuery();
                                                                 trLinenew.executeQuery();
                                                       
                                                       trrw.remove();
                                                       }
                                                      
                                                   }
                                                 }
                                       rsitr.closeRowSetIterator();
                                       trVo.executeQuery();
                                       trVonew.executeQuery();
                                       cpoTr1.executeQuery();
                                       RowQualifier rqtr=new RowQualifier(trVo);
                                       rqtr.setWhereClause("DocId='"+itmdocId+"' and ItmId='"+itmId+"' and ItmUom='"+itmUom+"'");
                                       Row[] fritm=trVo.getFilteredRows(rqtr);
                                       if(fritm.length==0)
                                       {
                                           //check if tax should be applied on this item or not.
                                           String ex="Y";
                                           if(removedExmptflg!=null)
                                               ex=removedExmptflg;
                                           else if(rw.getAttribute("TransTaxExmptFlg")!=null)
                                           ex = (String)rw.getAttribute("TransTaxExmptFlg");
                                           if(ex.equals("Y"))
                                           {}
                                           else
                                           {
                                  Row newrow= trVo.createRow();
                                    System.out.println("row created in TrVo");
                                     String taxruleflg= povo.getCurrentRow().getAttribute("TaxRuleFlg").toString();
                                    Number p_curr_fctr=new Number(1);
                                       if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                           p_curr_fctr = (Number)povo.getCurrentRow().getAttribute("CurrConvFctr");
                                       }
                                           newrow.setAttribute("CldId",p_cldId);
                                           newrow.setAttribute("SlocId",p_sloc_id);
                                           newrow.setAttribute("OrgId",p_org_id);
                                           newrow.setAttribute("DocId",docId);
                                       newrow.setAttribute("ItmId",itmId);
                                       newrow.setAttribute("ItmUom",itmUom);
                                       newrow.setAttribute("TaxRuleId",selectTaxPoBind.getValue());
                                       newrow.setAttribute("TaxRuleFlg",taxruleflg);
                                       
                                       if(removedExmptflg!=null)
                                           newrow.setAttribute("TaxExmptFlg",removedExmptflg);
                                       else
                                       newrow.setAttribute("TaxExmptFlg",rw.getAttribute("TransTaxExmptFlg"));
                                      String taxexm = (String)newrow.getAttribute("TaxExmptFlg");
                                       newrow.setAttribute("UsrIdCreate",p_user_id);   
                                       //TaxableAmt
                                       String afterdisc=povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                                       if(afterdisc.equals("N"))
                                               newrow.setAttribute("TaxableAmt",rw.getAttribute("TranPriceQty"));  
                                       else
                                           newrow.setAttribute("TaxableAmt",rw.getAttribute("TranItmAmtSp"));  

                                       Number p_taxable_amount = (Number)newrow.getAttribute("TaxableAmt");
                                       System.out.println("Tax rule flg="+taxruleflg);
                                        //create trlines
BigDecimal ret =(BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_CPO.ins_cpo_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { p_sloc_id, p_cldId, p_hoOrgId , p_org_id,docId,itmId,itmUom,taxid,p_user_id,p_taxable_amount,taxruleflg,p_curr_fctr,taxexm});
                                         /*    (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                           new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, docId, itmId,itmUom,taxid,
                                                                                          p_user_id, p_taxable_amount, taxruleflg,
                                                                                          p_curr_fctr,taxexm }); */
                                       
                                       newrow.setAttribute("TaxAmt", new Number(ret));    
                                           trVo.insertRow(newrow);    
                                           }
                                       }
                                   }
                                 }
                       rsitm.closeRowSetIterator();
                       trVo.executeQuery();
                       cpoTr1.executeQuery();
                       trLine.executeQuery();
                       trVonew.executeQuery();
                       trLinenew.executeQuery();
                       trLinevo1.executeQuery();
                   }
               powiseSelectTaxPop.hide();
           }
           else
           {
                   FacesMessage message = new FacesMessage("Select TaxRule !!!");
                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(selectTaxPoBind.getClientId(), message); 
               }   
        
         
         
    }
    
    
    public void taxResetonItem(ActionEvent actionEvent) {
        System.out.println("Setting reset flg to I");
        resetflg="I";
        showPopup(resettaxPop, true);
    }

    public void resetTaxonPo(ActionEvent actionEvent) {
        System.out.println("Setting reset flg to P");
        resetflg="P";
        showPopup(resettaxPop, true);
    }

    public void setResettaxPop(RichPopup resettaxPop) {
        this.resettaxPop = resettaxPop;
    }

    public RichPopup getResettaxPop() {
        return resettaxPop;
    }

    public void resetTaxDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("ok")){
            ViewObjectImpl poItm = getAm().getMmCpoItm1();
            ViewObjectImpl poTaxVo = getAm().getMmCpoTr();
            ViewObjectImpl poTaxLineVo = getAm().getMmCpoTrLines1();
            poTaxLineVo.executeQuery();
            RowQualifier rqTax = new RowQualifier(poTaxVo);
            Row[] rsi1=null;
           if(resetflg.equals("P"))
           {
               System.out.println("Reset PO Wise Tax");
           // rsi1 = poItm.getFilteredRows("TransTaxRuleFlg","P");
               rsi1=poItm.getAllRowsInRange();
               }
           else
               if(resetflg.equals("I"))
           {
                   System.out.println("Reset Item Wise Tax");
             // rsi1=  poItm.getFilteredRows("TransTaxRuleFlg","I");
                   if(poItm.getCurrentRow()!=null)
                   {
                       System.out.println("Current row is not null");
                   String itm = (String)poItm.getCurrentRow().getAttribute("ItmId");
                   String uom = (String)poItm.getCurrentRow().getAttribute("ItmUom");
                   RowQualifier rq=new RowQualifier(poItm);
                   rq.setWhereClause("ItmId='"+itm+"' and ItmUom='"+uom+"'");
                       rsi1=  poItm.getFilteredRows(rq);
                   }
                   else
                   System.out.println("Current row is  null");
               }
           Integer leng=-1;
          
           if(rsi1!=null && rsi1.length>0)
           {
               leng=rsi1.length-1;
                   System.out.println("Length="+rsi1.length);
               }
                      while (leng >= 0) {
                          System.out.println("for leng="+leng);
                             Row nxt = rsi1[leng];                   
                            if (nxt.getAttribute("ItmId") != null && nxt.getAttribute("ItmUom")!=null) {
                            
                                RowQualifier rq=new RowQualifier(poItm);
                                rq.setWhereClause("ItmId='"+nxt.getAttribute("ItmId")+"' and ItmUom='"+nxt.getAttribute("ItmUom")+"'");
                                Row[] itmrow=poItm.getFilteredRows(rq);
                                if(itmrow.length > 0)
                                {
                                rqTax.setWhereClause("DocId= '" + nxt.getAttribute("DocId").toString() + "' and ItmId= '" +
                                                     nxt.getAttribute("ItmId").toString() + "' and ItmUom= '"+nxt.getAttribute("ItmUom").toString()+"'");
                                Row[] rTax = poTaxVo.getFilteredRows(rqTax);
                        
                                if (rTax.length > 0) {
                              
                                    RowQualifier rqTaxLine = new RowQualifier(poTaxLineVo);
                                    rqTaxLine.setWhereClause("DocId='"+nxt.getAttribute("DocId").toString()+"' and ItmId='"+ nxt.getAttribute("ItmId").toString()+"' and TaxRuleId="+rTax[0].getAttribute("TaxRuleId")+" and ItmUom='"+nxt.getAttribute("ItmUom").toString()+"'");
                         
                                    Row[] rTaxLine = poTaxLineVo.getFilteredRows(rqTaxLine);
                         
                                    if (rTaxLine.length > 0) {
                                        for (Row rTl : rTaxLine) {
                                      
                                            rTl.remove();
                                        }
                                    }
                                   
                                    rTax[0].remove();
                                }
                            }
                            }
                 leng--;      
                }
               // poItm.executeQuery();
                poTaxVo.executeQuery();
                poTaxLineVo.executeQuery();
                getAm().getMmCpoTrLines().executeQuery();
                getAm().getMmCpoTrLines1().executeQuery();
              // OperationBinding operationBinding = getBindings().getOperationBinding("Execute");
               // operationBinding.execute();   
       AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
       getAm().getDBTransaction().postChanges();
        } 
        
    }

    public void cpoTaxExemptedVCE(ValueChangeEvent valueChangeEvent) {
        Row trcurr = getAm().getMmCpoTr2().getCurrentRow();
        ViewObject trline=getAm().getMmCpoTrLines2();
        Number tax=new Number(0);
        
        if(trcurr.getAttribute("TaxAmt")!=null){   
        if(valueChangeEvent.getNewValue().equals(true))
        {
         trcurr.setAttribute("TaxAmt",0);    
       // getAm().getMmCpo1().getCurrentRow().setAttribute("TransTotalTax",0);
        }
        else
        {
             RowSetIterator rsi=trline.createRowSetIterator(null);
         while(rsi.hasNext())
         {
             Row r=rsi.next();
            if(r.getAttribute("TaxAmtSp") !=null)
               tax=tax.add((Number)(r.getAttribute("TaxAmtSp")));
             }
             trcurr.setAttribute("TaxAmt",tax);    
         //    getAm().getMmCpo1().getCurrentRow().setAttribute("TransTotalTax",tax);
         
         }       
        }
        else
        {
            //taxamt is null mean tax is not applied hence this checkbox must be disable
            }
    }

    public String saveAndFwdButtonAction() {
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObject po = am.getMmCpo1();
        // ViewObject cpoItm =am.getMmCpoItm1();
        adfLog.info("Inside save --------------");
        ViewObjectImpl cpoitm11 = am.getMmCpoItm1();
        Row currRow = po.getCurrentRow();
        Number totalcost = (Number)currRow.getAttribute("TotalCostSp");
            System.out.println("TotalCostSp----:"+totalcost);
        Row r[]=getAm().getOrgMmPrf().getFilteredRows("OrgId",currRow.getAttribute("OrgId"));
        
            Number orgCpoLimit = (Number)r[0].getAttribute("CpoAmtLimit");
            if(orgCpoLimit!=null && totalcost.compareTo(orgCpoLimit)>0)
            {
                    FacesMessage message2 = new FacesMessage("CPO Amount can not be Greater than "+orgCpoLimit+".");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null,message2);   
                }
            else
            {
            Number zeros = new Number(0);
        /** check for item wise taxable amount change or no
         * */
          RowSetIterator rsi=cpoitm11.createRowSetIterator(null);
          String appTax="N";
          String itmNameConcat="";
          while(rsi.hasNext()){
              Row rw=rsi.next();
              if(rw.getAttribute("TranReapplyTax")!=null){
                  if("Y".equalsIgnoreCase(rw.getAttribute("TranReapplyTax").toString())){
                      appTax="Y";
                      if(rw.getAttribute("transItemName")!=null){
                           itmNameConcat=itmNameConcat + rw.getAttribute("transItemName").toString()+",";
                      }
                  }
              }
          }
          rsi.closeRowSetIterator();
        if("Y".equalsIgnoreCase(appTax)){
                            System.out.println("inside tax color");
                                FacesMessage message =
                                    new FacesMessage(resolvEl("#{bundle['MSG.292']}").toString()+itmNameConcat.substring(0, itmNameConcat.length()-1)+resolvEl("#{bundle['MSG.293']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);

        }else if(zeros.compareTo(totalcost)>0){
                    String msg2 = resolvEl("#{bundle['MSG.294']}").toString();
                                                     FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                     FacesContext fc = FacesContext.getCurrentInstance();
                                                     fc.addMessage(null,message2);  
        }else{
        Integer commitCount = 0;


        if (currRow.getAttribute("CpoId") == null) {
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String cpoId =(String)(callStoredFunction(Types.VARCHAR, "MM.FN_MM_GEN_ID (?,?,?,?)", new Object[] {SlocId , cldId , p_org_id , "MM$CPO" }));
                  
                  System.out.println("--DocId new created--"+cpoId);
            
            
            currRow.setAttribute("CpoId", cpoId);
            /* oracle.jbo.domain.Date dateCurr = (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();
            System.out.println("Current date--" + dateCurr);
            currRow.setAttribute("CpoDt", dateCurr); */
        }
        Number amtBs=(Number)currRow.getAttribute("TotalCostBs");
        Number amtSp=(Number)currRow.getAttribute("TotalCostSp"); 
        // System.out.println("Value in savePo--"+amtBs+"---"+amtSp);
        currRow.setAttribute("CpoAmountBs", amtBs);
        currRow.setAttribute("CpoAmountSp", amtSp);
        
        
        RowSetIterator rti =  cpoitm11.createRowSetIterator(null);
        if(rti.getRowCount() > 0)
        {
        while(rti.hasNext())
        {
          Row row = rti.next();
            Number amtbs = (Number)row.getAttribute("TranItmAmtBs");
            Number amtsp = (Number)row.getAttribute("TranItmAmtSp");

           row.setAttribute("ItmAmountBs", amtbs);
            row.setAttribute("ItmAmountSp", amtsp);
            
            }
        
        
        
        
        ViewObjectImpl tr = am.getMmCpoTr2();
          tr.executeQuery();
        Number taxableInTr = new Number(0);
        Number matchAmt = new Number(0);
        RowSetIterator rit = tr.createRowSetIterator(null);
        if(rit.getRowCount()>0)
        {
        if(currRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
        {
        while(rit.hasNext())
        {
          Row newRow = rit.next();
            if(newRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P"))
            {
              taxableInTr = (Number)newRow.getAttribute("TaxableAmt");
                }

            }
        
         if(currRow.getAttribute("TaxAfterDiscFlg").toString().equalsIgnoreCase("Y"))
         {
                 Number itmTotal = (Number)currRow.getAttribute("TotalItmPriceQty");
                  Number disc = (Number)currRow.getAttribute("TotalDiscountItmPO");
                  matchAmt =  itmTotal.subtract(disc);
             
             }
        if(currRow.getAttribute("TaxAfterDiscFlg").toString().equalsIgnoreCase("N"))
        {
            
                 matchAmt =  (Number)currRow.getAttribute("TotalItmPriceQty");
            
            }
             
          /* if(taxableInTr.compareTo(matchAmt) != 0)
          {
              
                  String msg2 = resolvEl("#{bundle['MSG.295']}").toString();//Amount has been changed , please re-apply tax
                                                   FacesMessage message2 = new FacesMessage(msg2);
                  message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                   FacesContext fc = FacesContext.getCurrentInstance();
                                                   fc.addMessage(null,message2);  
                                                   commitCount = 1;
              
              } */
        }
        }
        else if(currRow.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("I"))
        {
        Integer count = 0;
        ViewObjectImpl cpoitm = am.getMmCpoItm1();
            RowSetIterator rst = cpoitm.createRowSetIterator(null);
            if(rst.getRowCount() > 0)
            {
            while(rst.hasNext())
            {
              Row itmRow = rst.next();
              if(itmRow.getAttribute("TranReapplyTax").toString().equalsIgnoreCase("Y"))
                  count = count+1;
                
                }
                   
            }
            else
            {
                    String msg2 =resolvEl("#{bundle['MSG.297']}").toString();
                                                     FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                     FacesContext fc = FacesContext.getCurrentInstance();
                                                     fc.addMessage(null,message2);  
                
                
                
                }


        }
        
        if(commitCount == 0)
        {
         ViewObjectImpl trline = am.getMmCpoTrLines2();
        trline.executeQuery();
        am.getDBTransaction().commit();
        
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
           OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
           operationBinding1.execute();
           
           RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
           disclosedItem.setDisclosed(false);
           
        //Apply Wf Functions.
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String org_Id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId1=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                   WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                                   WfnoOp.getParamsMap().put("cld_id", cld_id);
                                   WfnoOp.getParamsMap().put("org_id", org_Id);
                                   WfnoOp.getParamsMap().put("doc_no", CPO_DOC_NO);
                                   WfnoOp.execute();
                                    String WfNum=null;  
                                     if(WfnoOp.getResult()!=null){
                                        WfNum= WfnoOp.getResult().toString();
                                     }
                                     if(WfNum!=null && !"0".equalsIgnoreCase(WfNum)){                     
        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                                   usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                                   usrLevelOp.getParamsMap().put("CldId", cld_id);
                                   usrLevelOp.getParamsMap().put("OrgId", org_Id);
                                   usrLevelOp.getParamsMap().put("usr_id", usr_Id);
                                   usrLevelOp.getParamsMap().put("WfNum", WfNum);
                                   usrLevelOp.getParamsMap().put("rcptDocId", CPO_DOC_NO);
                                   usrLevelOp.execute();
                                   Integer level=-1;
                                     if(usrLevelOp.getResult()!=null && usrLevelOp.getResult()!=null){
                                    level= Integer.parseInt(usrLevelOp.getResult().toString());
                                     }
                                     if(level>-1){
        String action = "I";
        String remark = "A";                       
        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                      insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                      insTxnOp.getParamsMap().put("cld_id", cld_id);
                                      insTxnOp.getParamsMap().put("pOrgId", org_Id);
                                      insTxnOp.getParamsMap().put("rcpt_doc_no", CPO_DOC_NO);
                                      insTxnOp.getParamsMap().put("WfNum", WfNum);
                       insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                       insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                       insTxnOp.getParamsMap().put("levelFrm", level);
                       insTxnOp.getParamsMap().put("levelTo", level);
                       insTxnOp.getParamsMap().put("action", action);
                       insTxnOp.getParamsMap().put("remark", remark);
                       insTxnOp.getParamsMap().put("amount", new Number(0));
                       insTxnOp.getParamsMap().put("post", "S");
                       insTxnOp.execute();
                        Integer res =-1;  
                         if(insTxnOp.getResult()!=null){
                           res= Integer.parseInt(insTxnOp.getResult().toString());
                        } 
                        
                     if(res==1){ 
                         OperationBinding operationBindingCommit = getBindings().getOperationBinding("Commit");
                         operationBindingCommit.execute();
                     }
        
        return "GotoWf";
      /*   StringBuilder msg=new StringBuilder("<html><body><p>"+resolvEl("#{bundle['MSG.298']}").toString()+"</p>");
        msg.append("<p>"+resolvEl("#{bundle['MSG.299']}").toString()+"<b>"+amtSp+"</b></p>");
        msg.append("<p>"+resolvEl("#{bundle['MSG.300']}").toString()+"<b>"+amtBs+"</b></p>");
        msg.append("</body></html>");
           
                                            FacesMessage message2 = new FacesMessage(msg.toString());
           message2.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null,message2);  */
            }
              else
              {
                   FacesMessage message2 = new FacesMessage("Something went wrong(User Level in Workflow).Please contact to ESS.");
                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null,message2);                                  
               }
        }
        else
        {
               String msg2 = "Workflow not created for Cash Purchase.Please add new Workflow to Cash Purchase.";
                                                FacesMessage message2 = new FacesMessage(msg2);
               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null,message2);  
           }
        }
        }
        else
        {
                String msg2 = resolvEl("#{bundle['MSG.297']}").toString();
                                                 FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                 FacesContext fc = FacesContext.getCurrentInstance();
                                                 fc.addMessage(null,message2);   
            
            }
        }
            }
        
        return null;
    }

    public void setCurrencyTransBind(RichInputListOfValues currencyTransBind) {
        this.currencyTransBind = currencyTransBind;
    }

    public RichInputListOfValues getCurrencyTransBind() {
        return currencyTransBind;
    }
}
