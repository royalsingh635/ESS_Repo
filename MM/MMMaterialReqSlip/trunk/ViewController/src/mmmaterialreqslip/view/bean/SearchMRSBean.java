package mmmaterialreqslip.view.bean;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchMRSBean {
    private RichInputText mrsNo_TextBind;
    private RichSelectOneChoice reqIdBind;
    private RichSelectOneChoice whIdBind;
    public String passtxnid=null;
    private Integer linkclick =0;
    private  Integer on_load =(Integer)getBindings().getOperationBinding("on_load_page").execute();

    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public SearchMRSBean() {

    }
    static{
        }
    public void searchButtonActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchMrs").execute();            
    }

    public void setMrsNo_TextBind(RichInputText mrsNo_TextBind) {
        this.mrsNo_TextBind = mrsNo_TextBind;
    }

    public RichInputText getMrsNo_TextBind() {
        return mrsNo_TextBind;
    }

    public void setReqIdBind(RichSelectOneChoice reqIdBind) {
        this.reqIdBind = reqIdBind;
    }

    public RichSelectOneChoice getReqIdBind() {
        return reqIdBind;
    }

    public void setWhIdBind(RichSelectOneChoice whIdBind) {
        this.whIdBind = whIdBind;
    }

    public RichSelectOneChoice getWhIdBind() {
        return whIdBind;
    }

    public void resetActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("resetbind").execute();    

    }

    public void linkClick(ActionEvent actionEvent) {
     
       passtxnid=(String)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("gettxnvalue").execute();
  
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
                paramMap.put("Mrs_Txn_id", passtxnid);
    }

    public void setPasstxnid(String passtxnid) {
        this.passtxnid = passtxnid;
    }

    public String getPasstxnid() {
        return passtxnid;
    }

    public void setOn_load(Integer on_load) {
        this.on_load = on_load;
    }

    public Integer getOn_load() {
        return on_load;
    }

    public void deleteMrsAL(ActionEvent actionEvent) {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending =null;
         OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                          chkUsr.getParamsMap().put("SlocId", sloc_id);
                          chkUsr.getParamsMap().put("CldId", cld_id);
                          chkUsr.getParamsMap().put("OrgId", pOrgId);
                          chkUsr.getParamsMap().put("RfqDocNo", 18513);
                          chkUsr.execute();
                         
                         if(chkUsr.getResult()!=null){
                            pending= Integer.parseInt(chkUsr.getResult().toString());
                         }
            System.out.println("Pending="+pending);              
        if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(usr_id)==0)
        {
            OperationBinding op=getBindings().getOperationBinding("deleteMRS");
            op.execute();
            if(op.getErrors().size()==0)
                System.out.println("Delete successfull");
            else
                System.out.println("error on delete="+op.getErrors());
        }
        else
        {
                String msg2 = "This Slip is pending at other user for approval, You can not Delete this.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
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
}
