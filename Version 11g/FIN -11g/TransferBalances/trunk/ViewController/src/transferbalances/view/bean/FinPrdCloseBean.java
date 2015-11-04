package transferbalances.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import transferbalances.model.services.TransferBalancesAMImpl;


public class FinPrdCloseBean {
    private RichPopup bindpopup;

    public FinPrdCloseBean() {
    }
    private String amName = "TransferBalancesAMDataControl";
    private int NUMBER = Types.NUMERIC;
    private int VARCHAR = Types.VARCHAR;
    private RichPopup addPopup;

    public void getNextPrdButton(ActionEvent actionEvent) {
        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObjectImpl v = am.getFinPrdClose1();
       Row currRow = v.getCurrentRow();
        Date strt_date = (Date)currRow.getAttribute("NxtStrtDt");
        Date end_date = (Date)currRow.getAttribute("NxtEndDt");   
        System.out.println(strt_date + "-----------" +end_date);
        currRow.setAttribute("Tran_strt_date", strt_date);
        currRow.setAttribute("Tran_end_date", end_date);
   
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
    
    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public void run(ActionEvent actionEvent) {
        Object slocid = resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        Object orgid = resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Object userid = resolveElExp("#{pageFlowScope.GLBL_APP_USR}");
        Object ho_org_id = resolveElExp("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Object cld_id = resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}");
        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObjectImpl v = am.getFinPrdClose1();
       Row currRow = v.getCurrentRow();
        Date strt_date = (Date)currRow.getAttribute("Tran_strt_date");
        Date end_date = (Date)currRow.getAttribute("Tran_end_date");   
        String type = currRow.getAttribute("TranCloseType").toString();
        Integer fy_id = Integer.parseInt(currRow.getAttribute("FyId").toString()); 
        Date curr_end_date = (Date)currRow.getAttribute("CurrEndDate"); 
        
       String str =  callStoredFunction(VARCHAR, "PKG_FIN.COUNT_COA_IE_CL_BAL_NOT_ZERO(?,?,?,?)", new Object[] {slocid , orgid , curr_end_date ,fy_id}).toString();
    Integer cnt = Integer.parseInt(callStoredFunction(NUMBER, "PKG_FIN.GET_COA_RR_COUNT", new Object[] {}).toString());
    
    System.out.println(cnt +"-------------");
    System.out.println(str+"--------------");
    if(cnt == 0)
    {
      System.out.println("in counter ---------");
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg =
            new FacesMessage(resolvEl("#{bundle['MSG.377']}"));
        msg.setSeverity(msg.SEVERITY_ERROR);
        fc.addMessage(null, msg);
    }
    else
    {
    
    if(cnt == 1 || str.equalsIgnoreCase("Y"))
    {
       System.out.println(slocid +"---"+orgid +"-----"+curr_end_date +"-----"+fy_id +"-----"+userid+"-----"+ho_org_id+"---"+cld_id+"---"+strt_date+"----"+end_date);
        Integer ret = Integer.parseInt(callStoredFunction(NUMBER, "PKG_FIN.FIN_PRD_CLOSE(?,?,?,?,?,?,?,?,?)", new Object[] {slocid , orgid , curr_end_date , fy_id , userid ,ho_org_id , cld_id ,
                                                                                          strt_date , end_date}).toString());
        
        FacesContext fc = FacesContext.getCurrentInstance();

        FacesMessage msg =
            new FacesMessage(resolvEl("#{bundle['MSG.378']}"));
        msg.setSeverity(msg.SEVERITY_INFO);
        fc.addMessage(null, msg);
           
    
    }
    else
    {
       showPopup(bindpopup, true); 
        }
    }
    }
    
    
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage().substring(11, end));
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {

                }
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

    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }

    public void setBindpopup(RichPopup bindpopup) {
        this.bindpopup = bindpopup;
    }

    public RichPopup getBindpopup() {
        return bindpopup;
    }

    public void popsetCancelList(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void popsetDialogList(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public String okOnPopup() {
        // Add event code here...
        return "trf";
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
