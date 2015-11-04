package bdgkraeoprfapp.view.bean;

import javax.faces.event.ActionEvent;
import bdgkraeoprfapp.view.utils.ADFUtils;
import bdgkraeoprfapp.view.utils.JSFUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.binding.OperationBinding;

public class BdgKraEOPrfBean {
    String CldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    String HoOrgId =(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    String OrgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    Integer SlocId = (Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
    Integer UsrId = (Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
    String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public BdgKraEOPrfBean() {
    }

    public void AddActionListener(ActionEvent actionEvent) { 
        OperationBinding op = ADFUtils.findOperation("CreateInsert");
        op.execute();
        if(op.getErrors().size()==0)
        {
                OperationBinding genTxn = ADFUtils.findOperation("GenDocTxnId");
                       genTxn.getParamsMap().put("cldId",CldId);
                       genTxn.getParamsMap().put("slocId",SlocId);
                genTxn.getParamsMap().put("orgId",OrgId);
                genTxn.getParamsMap().put("usrId",UsrId);
                genTxn.getParamsMap().put("docId",CldId);
                genTxn.getParamsMap().put("docTypeId",0);
                       genTxn.execute();
                       if(genTxn.getErrors().size() == 0 && genTxn.getResult()!=null)
                       {
                                           mode = "A";

                                       }
                       else
                       {
                               JSFUtils.addFacesErrorMessage("Something Went Wrong.\n Error="+genTxn.getErrors());
                           }  
            }
        else
        {
                JSFUtils.addFacesErrorMessage("Something Went Wrong.\nError Code: "+op.getErrors());
            }
       
    }


    public void EditActionListener(ActionEvent actionEvent) {
        mode = "A";
    }


    public void SaveActionListener(ActionEvent actionEvent) {
        
        OperationBinding genFy = ADFUtils.findOperation("GetFyIdOrg");
               genFy.getParamsMap().put("CldId",CldId);
               genFy.getParamsMap().put("OrgId",OrgId);
               genFy.execute();
               if(genFy.getErrors().size() == 0 && genFy.getResult()!=null)
               {
                                   OperationBinding op = ADFUtils.findOperation("Commit");
                                   op.execute();
                                   if(op.getErrors().size()==0)
                                   {
                                       mode = "V";
                                           JSFUtils.addFacesInformationMessage("Record Saved Successfully.");
                                       }
                                   else
                                   {
                                       JSFUtils.addFacesErrorMessage("Something Went Wrong.\nError Code: "+op.getErrors());
                                       }
                               }
               else
               {
                       JSFUtils.addFacesErrorMessage("Something Went Wrong.\nFinancial Year: "+genFy.getResult()+ "\n Error="+genFy.getErrors());
                   }
        
      
    }

    public void CancelActionListener(ActionEvent actionEvent) {
        OperationBinding op = ADFUtils.findOperation("Rollback");
        op.execute();
        if(op.getErrors().size()==0)
        {
            mode = "V";
            }
        else
        {
                JSFUtils.addFacesErrorMessage("Something Went Wrong.\nError Code: "+op.getErrors());
            }
    }
    
    public Object resolvEl(String data) {
           FacesContext facesContext = FacesContext.getCurrentInstance();
           ELContext elContext = facesContext.getELContext();
           ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
           ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
           return exp.getValue(elContext);
       }
}
