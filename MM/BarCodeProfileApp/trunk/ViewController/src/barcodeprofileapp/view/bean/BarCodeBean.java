package barcodeprofileapp.view.bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.binding.BindingContainer;

import static adf.utils.bean.ADFBeanUtils.*;

import adf.utils.model.ADFModelUtils;

import barcodeprofileapp.model.service.Attribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.ListIterator;
import java.util.Map;

import javax.faces.application.FacesMessage;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.AttributeBinding;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSet;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class BarCodeBean {
    private String mode = "V";
    private RichPanelFormLayout docFormBind;
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private int check = 0;
    private RichTable table;
    private RichSelectOneChoice docTypIdBind;
    private RichSelectOneChoice docIdBind;
    private RichSelectOneChoice attIdBind;

    public BarCodeBean() {
    }


    public void addProcessAction(ActionEvent actionEvent) {
        // Add event code here...
        getOperationBinding("CreateInsert").execute();
        mode = "A";
        //getBinding().getOperationBinding("CreateInsert").execute();
    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
        //getBinding().getOperationBinding("Commit").execute();

        Object res = getOperationBinding("isRecordExists").execute();
        if (res != null) {
            if ((Byte) res == 0) {
//                MSG.2218
//                Please enter atleast one record in Document
                String s=ADFModelUtils.resolvRsrc("MSG.2218");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            } else if (attributes.size() == 0) {
//                MSG.2222
//                Please enter atleast one record in Attribute
                String s2=ADFModelUtils.resolvRsrc("MSG.2222");
                FacesMessage message = new FacesMessage(s2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
        }
        OperationBinding binding = getOperationBinding("insertAttInTable");
        binding.getParamsMap().put("list", attributes);
        binding.execute();

        getOperationBinding("Commit").execute();
        getOperationBinding("Execute").execute();
        getOperationBinding("Execute1").execute();
        getOperationBinding("Execute2").execute();
        mode = "V";
    }

    public void addAttAction(ActionEvent actionEvent) {
        // Add event code here...
        //    getOperationBinding("CreateInsert1").execute();
//        MSG.2219
//        Duplicate Document Attribute!!
        if (attIdBind.getValue() != null && (!attIdBind.getValue().toString().equals(""))) {
            Attribute a = new Attribute(Integer.parseInt(attIdBind.getValue().toString()));
            if (attributes.contains(a)) {
//                Duplicate Document Attribute
//MSG.2219 
                String s=ADFModelUtils.resolvRsrc("MSG.2219").toString();
               FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(attIdBind.getClientId(), message);
                return;
            }
            Object obj = getOperationBinding("addAttributeInList").execute();
            if (obj != null) {
                Map map = (Map) obj;
                Attribute att =
                    new Attribute(Integer.parseInt(map.get("AttId").toString()), map.get("AttNm").toString(),
                                  Integer.parseInt(map.get("AttLen").toString()));
                att.setNo(attributes.size() + 1);
                attributes.add(att);
            }
        }
    }

    public void docAddAction(ActionEvent actionEvent) {
        // Add event code here...

        if (docIdBind.getValue() != null && (!docIdBind.getValue().toString().equals("")) &&
            docTypIdBind.getValue() != null && (!docTypIdBind.getValue().toString().equals(""))) {
            OperationBinding binding = getOperationBinding("isDocDuplicateDocId");
            binding.getParamsMap().put("docId", Integer.parseInt(docIdBind.getValue().toString()));
            binding.getParamsMap().put("docTypId", Integer.parseInt(docTypIdBind.getValue().toString()));
            Object obj = binding.execute();
            if (obj != null && (Boolean) obj) {
//                MSG.2221
//                Duplicate Document
                String msg = ADFModelUtils.resolvRsrc("MSG.2221");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(docTypIdBind.getClientId(), message);
                return;
            }
            //getOperationBinding("CreateInsert2").execute();
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        // Add event code here...
        getOperationBinding("Rollback").execute();
        mode = "V";
    }

    public void editAction(ActionEvent actionEvent) {
        // Add event code here...
        mode = "E";
        getOperationBinding("setPrcsIdOnChange").execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void attributeDeleteAction(ActionEvent actionEvent) {
        getOperationBinding("Delete").execute();
        getOperationBinding("Execute1").execute();
    }

    public void documentDeleteAction(ActionEvent actionEvent) {
        getOperationBinding("Delete1").execute();
        getOperationBinding("Execute2").execute();
    }

    public void prcsNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            int x = (Integer) object;
            OperationBinding binding = getOperationBinding("isPrcsExists");
            binding.getParamsMap().put("id", x);
            binding.getParamsMap().put("mode", mode);
            Object execute = binding.execute();

            if (execute != null && execute.equals(true)) {
//                Duplicate Process
//                    MSG.2220
                String s=ADFModelUtils.resolvRsrc("MSG.2220");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void setDocFormBind(RichPanelFormLayout docFormBind) {
        this.docFormBind = docFormBind;
    }

    public RichPanelFormLayout getDocFormBind() {
        return docFormBind;
    }

    public void docNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            OperationBinding binding = getOperationBinding("isDocDuplicateDocId");
            binding.getParamsMap().put("docId", Integer.parseInt(object.toString()));
            Object obj = binding.execute();
            if (obj != null && (Boolean) obj) {
//                Duplicate Document
//                MSG.2221
                String s=ADFModelUtils.resolvRsrc("MSG.2221");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getOperationBinding("filterByPrcId");
        binding.getParamsMap().put("typ", "s");
        Object obj = binding.execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getOperationBinding("filterByPrcId");
        binding.getParamsMap().put("typ", "e");
        Object obj = binding.execute();
    }

    public void rowUpAction(ActionEvent actionEvent) {
        // Add event code here...
        getOperationBinding("moveUp").execute();

        //   getOperationBinding("Execute1").execute();
    }

    public void rowDownAction(ActionEvent actionEvent) {
        // Add event code here...
        getOperationBinding("moveDown").execute();

        //     getOperationBinding("Execute1").execute();
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        System.out.println("come here");
        if (check == 0) {
            System.out.println("come here 1");
            Object obj = getOperationBinding("getaAttData").execute();
            if (obj != null) {
                System.out.println("while");
                attributes = (List<Attribute>) obj;
                check++;
            }
        }
        return attributes;
    }

    public void test(ActionEvent actionEvent) {
        // Add event code here..
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public void rowMoveUpAction(ActionEvent actionEvent) {

        Object data = table.getSelectedRowData();
        Attribute ab = (Attribute) data;
        System.out.println(ab.getAttName());
        ListIterator<Attribute> itr = attributes.listIterator();
        while (itr.hasNext()) {
            Attribute attribute = itr.next();
            boolean chk = attribute.equals(new Integer(ab.getNo() - 1));
            System.out.println("chk : " + chk);
            if (chk) {
                System.out.println("Found !!");
                int p = attribute.getNo();
                attribute.setNo(ab.getNo());
                ab.setNo(p);
                break;
            }
        }
        Collections.sort(attributes);
    }

    public void rowMoveDownAction(ActionEvent actionEvent) {
        Object data = table.getSelectedRowData();
        Attribute ab = (Attribute) data;
        System.out.println(ab.getAttName());
        ListIterator<Attribute> itr = attributes.listIterator();
        while (itr.hasNext()) {
            Attribute attribute = itr.next();
            boolean chk = attribute.equals(new Integer(ab.getNo() + 1));
            System.out.println("chk : " + chk);
            if (chk) {
                System.out.println("Found !!");
                int p = attribute.getNo();
                attribute.setNo(ab.getNo());
                ab.setNo(p);
                break;
            }
        }
        Collections.sort(attributes);
    }

    public void attDeleteAction(ActionEvent actionEvent) {
        Object data = table.getSelectedRowData();
        Attribute ab = (Attribute) data;
        int pos = ab.getNo();
        attributes.remove(ab);
        ListIterator<Attribute> itr = attributes.listIterator(pos - 1);
        while (itr.hasNext()) {
            Attribute attribute = itr.next();
            attribute.setNo(pos);
            pos++;
        }
    }

    //    ADFUtil.invokeEL("#{bindings.StorageTypLOV1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
    //                     selectionEvent });
    //    Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.StorageTypLOV1Iterator.currentRow}");

    public void processSelctionAction(SelectionEvent selectionEvent) {
        // Add event code here...

        ADFUtil.invokeEL("#{bindings.AppBcPrf1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        selectionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        check = 0;
        //    getAttributes();
    }

    public void PrcsChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        getOperationBinding("setPrcsIdOnChange").execute();

    }

    public void docChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(docTypIdBind);
    }

    public void setDocTypIdBind(RichSelectOneChoice docTypIdBind) {
        this.docTypIdBind = docTypIdBind;
    }

    public RichSelectOneChoice getDocTypIdBind() {
        return docTypIdBind;
    }

    public void setDocIdBind(RichSelectOneChoice docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichSelectOneChoice getDocIdBind() {
        return docIdBind;
    }

    public void setAttIdBind(RichSelectOneChoice attIdBind) {
        this.attIdBind = attIdBind;
    }

    public RichSelectOneChoice getAttIdBind() {
        return attIdBind;
    }
}
