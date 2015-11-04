package svcserviceexecutive.view.bean;

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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class ServiceBean {
    private RichInputListOfValues usrNameTrans;
    private RichInputListOfValues bindManagerNm;
    private RichInputText empNmBindVar;
    private RichInputText bindempId;
    private RichInputListOfValues bindDeptName;
    private RichInputListOfValues bindSpclNm;
    private RichInputListOfValues bindMngNm;
    private String mode = "V";
    private RichInputListOfValues bindEmpNm;

    public ServiceBean() {
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public String saveSvcExeAction() {
        // Add event code here...

        if (bindMngNm.getValue() != null && empNmBindVar.getValue() != null) {
            String mgn = bindMngNm.getValue().toString();
            String emp = empNmBindVar.getValue().toString();
            System.out.println(mgn + " " + emp);

            if (mgn.equals(emp)) {
                // String message = "Manager Name and Employee Name should not be same ";
                String msg = resolvElDCMsg("#{bundle['MSG.2417']}").toString();

                showFacesMessage(msg, "E", false, "F", null);
                return null;
            }
        }
        if (bindDeptName.getValue() != null && bindDeptName.getValue().toString().length() > 0) {
            OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
            ob.execute();
            // String message = "Record Save Successfully ! ";
            String msg = resolvElDCMsg("#{bundle['MSG.1599']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, message);
            this.mode = "V";
        } else {
            // String message = "Department Name is Required";
            String msg = resolvElDCMsg("#{bundle['MSG.2418']}").toString();
            showFacesMessage(msg, "E", false, "F", bindDeptName.getClientId());
        }
        return null;
    }

    public String addSvcExeSplAction() {
        // Add event code here...
        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert");
        op.execute();
        return null;

    }

    public String saveSvcExeSplACTION() {
        // Add event code here...
        OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
        ob.execute();
        return null;
    }

    public String deleteSvcSplAction() {
        // Add event code here...
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
        ob.execute();
        return null;
    }

    public void setUsrNameTrans(RichInputListOfValues usrNameTrans) {
        this.usrNameTrans = usrNameTrans;
    }

    public RichInputListOfValues getUsrNameTrans() {
        return usrNameTrans;
    }

    public String searchACTION() {
        // Add event code here..
        System.out.println("Value of Employee Name" + usrNameTrans.getValue());

        OperationBinding ob =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("SearchEmployee");
        ob.getParamsMap().put("name", usrNameTrans.getValue() == null ? null : usrNameTrans.getValue().toString());
        ob.execute();

        return null;
    }

    public String resetACTION() {
        // Add event code here...
        usrNameTrans.setValue("");
        OperationBinding ob =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("SearchEmployee");
        ob.getParamsMap().put("name", null);
        ob.execute();

        return null;
    }

    public void editSvcExeAction(ActionEvent actionEvent) {
        // Add event code here...
        // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("").execute();
        this.mode = "E";
    }

    public void empIdVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString() != "") {
            SpecialCharValidator(valueChangeEvent.getNewValue().toString(), empNmBindVar.getClientId());
        }
    }

    public void empNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString() != "") {
            SpecialCharValidator(valueChangeEvent.getNewValue().toString(), empNmBindVar.getClientId());
        }
        //        if(empNmBindVar.getValue()!=null && empNmBindVar.getValue()!=null) {
        //            ManagerNmValdator();
        //        }
    }

    public void ManagerNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString() == "") {
            System.out.println(valueChangeEvent.getNewValue().toString());
            SpecialCharValidator(valueChangeEvent.getNewValue().toString(), bindempId.getClientId());
        }
        if (empNmBindVar.getValue() != null && empNmBindVar.getValue() != null) {
            ManagerNmValdator();
        }
    }

    public void DeptNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString() != "") {
            System.out.println(valueChangeEvent.getNewValue().toString());
            SpecialCharValidator(valueChangeEvent.getNewValue().toString(), bindDeptName.getClientId());
        }
    }

    public void SpecialisationNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString() != "") {
            System.out.println(valueChangeEvent.getNewValue().toString());
            SpecialCharValidator(valueChangeEvent.getNewValue().toString(), bindSpclNm.getClientId());
        }
    }

    /** method for validation of special Charater for different field**/

    public void SpecialCharValidator(String str, String ClientId) {
        int flag = 0;

        String iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";

        for (int i = 0; i < iChars.length(); i++) {
            if (str.indexOf(iChars.charAt(i)) >= 0) {
                flag = 1;
            }
        }
        if (flag == 1) {
            // String message = "Special Character not allowed";
            String msg = resolvElDCMsg("#{bundle['MSG.1056']}").toString();
            showFacesMessage(msg, "E", false, "V", ClientId);
            //                         FacesMessage message = new FacesMessage("Special Character are not allowed");
            //                         message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //                         FacesContext fc = FacesContext.getCurrentInstance();
            //                         fc.addMessage(ClientId, message);
        }
    }

    public void ManagerNmValdator() {

    }

    public void setBindManagerNm(RichInputListOfValues bindManagerNm) {
        this.bindManagerNm = bindManagerNm;
    }

    public RichInputListOfValues getBindManagerNm() {
        return bindManagerNm;
    }

    public void setEmpNmBindVar(RichInputText empNmBindVar) {
        this.empNmBindVar = empNmBindVar;
    }

    public RichInputText getEmpNmBindVar() {
        return empNmBindVar;
    }

    public void setBindempId(RichInputText bindempId) {
        this.bindempId = bindempId;
    }

    public RichInputText getBindempId() {
        return bindempId;
    }

    public void setBindDeptName(RichInputListOfValues bindDeptName) {
        this.bindDeptName = bindDeptName;
    }

    public RichInputListOfValues getBindDeptName() {
        return bindDeptName;
    }

    public void setBindSpclNm(RichInputListOfValues bindSpclNm) {
        this.bindSpclNm = bindSpclNm;
    }

    public RichInputListOfValues getBindSpclNm() {
        return bindSpclNm;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void empNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object.toString() != null && object.toString() != "") {
            SpecialCharValidator(object.toString(), empNmBindVar.getClientId());
        }

    }

    public void empIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object.toString() != null && object.toString() != "") {
            SpecialCharValidator(object.toString(), empNmBindVar.getClientId());
        }

    }

    public void setBindMngNm(RichInputListOfValues bindMngNm) {
        this.bindMngNm = bindMngNm;
    }

    public RichInputListOfValues getBindMngNm() {
        return bindMngNm;
    }

    public String AddSpclAction() {
        // Add event code here...
        String Spclnm = bindSpclNm.getValue().toString();
        if (Spclnm != null) {
            OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("AddSpcl");
            ob.getParamsMap().put("spcl", Spclnm);
            ob.execute();
        }
        return null;
    }

    public String createACTION() {
        // Add event code here...
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
        this.mode = "C";
        return null;
    }

    public String cancelACTION() {
        // Add event code here...
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        this.mode = "V";
        return null;
    }

    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        DCBindingContainer bc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding dcIter = (DCIteratorBinding) bc.findIteratorBinding(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            //            System.out.println("attrsNm " + r.getAttribute(attrsNm) + " count " + countVal);

            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
                //                System.out.println(" inside " + val.equals(r.getAttribute(attrsNm)) + " count " + countVal);
            }
            //            System.out.println("attrsNm " + r.getAttribute(attrsNm) + " count " + countVal);

        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();
        if (currentRow != null) {
            if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
                countVal = countVal - 1;
            }
        }

        return countVal == 1 ? true : false;

    }

    public void duplicateEmpChkVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // SvcExec1Iterator
        if (object != null) {

            /*   if (duplicateValue("SvcExec1Iterator", "EmpNm", object)) {
                //System.out.println("Inside of orgchk validator !!");
              // showFacesMessage("Duplicate Employee is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record Found",
                                                              null));
            } */

            // if (bindEmpNm.getValue() != null) {
            OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("dupEmp");

            ob.getParamsMap().put("empNm", object.toString());
            ob.execute();

            // String retval = "N";
            //     System.out.println(object.toString()+" reuslet " + ob.getResult() + " eror " + ob.getErrors());
            if (ob.getResult().equals("Y")) {
                // String message = "Duplicate Record Found";
                String msg = resolvElDCMsg("#{bundle['MSG.46']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            }
        }


    }

    public void AddSpclACE(ActionEvent actionEvent) {

        if (bindSpclNm.getValue() != null) {

            String Spclnm = bindSpclNm.getValue().toString();
            System.out.println("spcl name ---" + Spclnm);
            OperationBinding dpchk =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("duplicatechk");
            dpchk.execute();

            String dplChk = null;
            if (dpchk.getErrors().isEmpty()) {
                dplChk = dpchk.getResult().toString();
            }
            if ("Y".equalsIgnoreCase(dplChk)) {
                if (Spclnm != null) {
                    OperationBinding ob =
                        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("AddSpcl");
                    ob.getParamsMap().put("spcl", Spclnm);
                    ob.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindSpclNm);
                }
            } else if ("N".equalsIgnoreCase(dplChk)) {
                // String message = "Duplicate Record Found";
                String msg = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                showFacesMessage(msg, "E", false, "F", bindSpclNm.getClientId());
            }
        } else {
            // String message = "Please enter Specialisation !";
            String msg = resolvElDCMsg("#{bundle['MSG.2419']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        //return null;
    }

    public void spclChkVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            if (duplicateValue("SvcExecSpl2Iterator", "TransSpclName", object)) {
                //System.out.println("Inside of orgchk validator !!");
                // showFacesMessage("Duplicate Employee is Selected !");
                // String message =  "Duplicate Specialisation Selected";
                String msg = resolvElDCMsg("#{bundle['MSG.2420']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }


    }

    public void setBindEmpNm(RichInputListOfValues bindEmpNm) {
        this.bindEmpNm = bindEmpNm;
    }

    public RichInputListOfValues getBindEmpNm() {
        return bindEmpNm;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }
}
