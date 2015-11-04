package mmappwhprf.view.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Timestamp;

public class WarehouseBean {
    private RichInputDate inctvRsnDtBinding;
    String mode="D";
    Key key = null;
    private RichPanelFormLayout panelFormBinding;

    public WarehouseBean() {
    }
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void searchWarehouseAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchWarehouse").execute();
    }

    public void resetWarehouseAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetWarehouse").execute();
    }

    public void activeVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            String value=valueChangeEvent.getNewValue().toString();
           System.out.println("actv value is "+value);
            if(value.equalsIgnoreCase("false")){
                Timestamp dt = new Timestamp(System.currentTimeMillis());
                inctvRsnDtBinding.setValue(dt);
            }else if(value.equalsIgnoreCase("true")){
                inctvRsnDtBinding.setValue(null);
            }
        }
    }

    public void setInctvRsnDtBinding(RichInputDate inctvRsnDtBinding) {
        this.inctvRsnDtBinding = inctvRsnDtBinding;
    }

    public RichInputDate getInctvRsnDtBinding() {
        return inctvRsnDtBinding;
    }

    public void wareHouseNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null && object.toString().length()>0)
        {//(\.|\-(?!\.|\-|$)
         //     |       | 
        String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\:(?!\\.|\\:|$))*" +
            "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
         CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = "Special Character Not Allowed";

        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }  
        
        //check for duplicate warehouse name
        OperationBinding chkDupli=getBindings().getOperationBinding("CheckDuplicateName");
        chkDupli.getParamsMap().put("whNm", object);
        chkDupli.execute();
        String isdupli=null;
        if(chkDupli.getErrors().size()==0 && chkDupli.getResult()!=null)
        {
            isdupli = (String)chkDupli.getResult();
            }
        if(isdupli.equals("Y"))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Warehouse Name not allowed.", null));
 
    }
}

    public void addBtnAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert").execute();
        this.mode="E";
    }

    public void editBtnAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("getCurrentRowKey");
        op.execute();

        if (op.getErrors().size() == 0 && op.getResult() != null) {
            key = (Key)op.getResult();
            System.out.println("Key is=" + key);
        }
        this.mode="E";
    }

    public void saveBtnAL(ActionEvent actionEvent) {
        String chk="N";
        OperationBinding chkWhAdds=getBindings().getOperationBinding("ChkAdds");
        chkWhAdds.execute();
        if(chkWhAdds.getErrors().size()==0 && chkWhAdds.getResult()!=null)
            chk = (String)chkWhAdds.getResult();
        else
            System.out.println("Error in add call="+chkWhAdds.getErrors()+" and return="+chkWhAdds.getResult());
        System.out.println("Chk in bean="+chk);
        if(chk.equals("N"))
        {
            FacesMessage message2 = new FacesMessage("Please select Address.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
        else
        {
        OperationBinding oBind =getBindings().getOperationBinding("Commit");
        oBind.execute();
        this.mode="D";
        }
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        OperationBinding oBind =getBindings().getOperationBinding("Rollback");
        oBind.execute();
        getBindings().getOperationBinding("Execute").execute();
        OperationBinding op = getBindings().getOperationBinding("setCurrentRow");
        op.getParamsMap().put("key", key);
        op.execute();
       
        this.mode="D";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    
}
