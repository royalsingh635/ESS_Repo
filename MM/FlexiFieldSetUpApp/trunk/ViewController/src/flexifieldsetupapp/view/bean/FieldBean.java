package flexifieldsetupapp.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class FieldBean {
    private RichSelectOneChoice dataTypeBinding;
    private RichInputText docIdBinding;
    private RichInputText langIdBinding;
    private RichSelectOneChoice docTypeBinding;


    public FieldBean() {
    }
    private String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addNewFieldAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert").execute();
        this.mode = "F";
    }

    public void editFieldAL(ActionEvent actionEvent) {

        String res = "";
        System.out.println("in bean.... for validation");
        OperationBinding oba = getBindings().getOperationBinding("fldEditValidation");
        oba.execute();
        res = oba.getResult().toString();
        if (res != null) {
            if (res.equalsIgnoreCase("Y")) {
                FacesMessage message = new FacesMessage("Flexi Field can not be edited. Already in use.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                this.mode = "F";
            }
        }

    }

    public void saveFieldAL(ActionEvent actionEvent) {

        OperationBinding ob = getBindings().getOperationBinding("lovDataType");
        ob.execute();
        String rs = null;
        if (ob.getResult() != null) {
            rs = ob.getResult().toString();
        }
        //   rs = ob.getResult().toString();
        System.out.println("value of data type returned is" + rs);
        if (rs != null && rs.equals("Y")) {
            getBindings().getOperationBinding("Commit").execute();
            this.mode = "V";
        } else {
            FacesMessage message = new FacesMessage("LOV is not possible");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void cancelFieldAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Rollback").execute();
        this.mode = "V";
    }

    public void fieldNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("inside valid");
            String nm = object.toString().trim();
            OperationBinding op = getBindings().getOperationBinding("chkDuplicateFldNm");
            op.getParamsMap().put("FldNm", nm);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                System.out.println("name valid result is --->" + rslt);
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Duplicate Field Name Not Allowed!!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void fieldLengthValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && dataTypeBinding.getValue() != null) {
            //System.out.println("data type value-->" + dataTypeBinding.getValue());
            Integer obj = (Integer) object;
            if (obj.compareTo(0) == -1) {
                String msg = "Length can not be Negative.";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if (dataTypeBinding.getValue().equals(756) && obj.compareTo(8) > 0) {
                // System.out.println("inside date ");
                String msg = "Length can not be greater than 8 for Date!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if (dataTypeBinding.getValue().equals(757) && obj.compareTo(4000) > 0) {
                //System.out.println("inside char ");
                String msg = "Length can not be greater than 4000 for Character!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if (dataTypeBinding.getValue().equals(758) && obj.compareTo(26) > 0) {
                // System.out.println("inside Number ");
                String msg = "Length can not be greater than 26 for Number!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if (dataTypeBinding.getValue().equals(759) && obj.compareTo(1) > 0) {
                //System.out.println("inside Boolean ");
                String msg = "Length can not be greater than 1 for Boolean!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

        }
    }

    public void addDocumentAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert1").execute();
        this.mode = "D";
    }

    public void editDocumentAL(ActionEvent actionEvent) {
        this.mode = "D";
    }

    public void addLangFldAL(ActionEvent actionEvent) {

        getBindings().getOperationBinding("CreateInsert2").execute();
        this.mode = "L";
    }

    public void editLangFldAL(ActionEvent actionEvent) {
        this.mode = "L";
    }

    public void setDataTypeBinding(RichSelectOneChoice dataTypeBinding) {
        this.dataTypeBinding = dataTypeBinding;
    }

    public RichSelectOneChoice getDataTypeBinding() {
        return dataTypeBinding;
    }


    public void docNmAndTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("object-->" + object);
        // System.out.println("docId -->" + docIdBinding);
        if (object == null && docIdBinding.getValue() != null) {
            String msg = "Document Type can not be left blank!!";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            if (docIdBinding.getValue() != null) {
                OperationBinding op = getBindings().getOperationBinding("chkDuplicateDocNmAndType");
                op.getParamsMap().put("docId", docIdBinding.getValue());
                op.getParamsMap().put("docTypeId", object);
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    String rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("Y")) {
                        String msg = "Duplicate document with duplicate document type not allowed!!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }
            }
        }
    }


    public void setDocIdBinding(RichInputText docIdBinding) {
        this.docIdBinding = docIdBinding;
    }

    public RichInputText getDocIdBinding() {
        return docIdBinding;
    }

    public void setLangIdBinding(RichInputText langIdBinding) {
        this.langIdBinding = langIdBinding;
    }

    public RichInputText getLangIdBinding() {
        return langIdBinding;
    }

    public void langNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String langNm = object.toString().trim();
            OperationBinding ob = getBindings().getOperationBinding("chkDuplicateLangNameValidator");
            ob.getParamsMap().put("langNm", langNm);

            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Duplicate language not allowed!!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }

        }
    }

    public void setDocTypeBinding(RichSelectOneChoice docTypeBinding) {
        this.docTypeBinding = docTypeBinding;
    }

    public RichSelectOneChoice getDocTypeBinding() {
        return docTypeBinding;
    }


    public void docNameVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(docTypeBinding);
    }

    public void dataTypeValidation(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void lovValidation(ValueChangeEvent valueChangeEvent) {
        //        // Add event code here...
        //            String dataTypeName = valueChangeEvent.getNewValue().toString();
        //            if (dataTypeName != null) {
        //                System.out.println("in bean");
        //                OperationBinding operationBinding =
        //                    (OperationBinding) BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("lovDataType");
        //                System.out.println("value of bind var is" + dataTypeName);
        //                //        operationBinding.getParamsMap().put("data", dataTypeName);
        //                operationBinding.execute();
        //                String rs = operationBinding.getResult().toString();
        //                System.out.println("value of data type returned is" + rs);
        //                if (operationBinding.getResult() == 757 || operationBinding.getResult() == 758) {
        //                    if (res.equalsIgnoreCase("N")) {
        //                        String msg = "Boolean  and Date Types can not hav LOVs!!";
        //                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        //                    }
        //                }
        //            }
    }

    public void addField(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getOperationBinding("CreateInsert3").execute();
        this.mode = "L";
    }

    public void editField(ActionEvent actionEvent) {
        // Add event code here...
        this.mode = "L";
    }

    public void docNameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //        // Add event code here...
        //        if (object != null) {

        //        }
        //


    }

    public void resourceNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            OperationBinding ob = getBindings().getOperationBinding("chkForUniqueResNm");
            ob.getParamsMap().put("name", object.toString());
            Object res = ob.execute();
            if (res != null && res.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                                                              "Name must be Unique !!"));
            }
        }
    }

    public void LovFieldValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

    }
}
