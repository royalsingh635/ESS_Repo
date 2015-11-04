package mmbarcodeapp.view.bean;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import mmbarcodeapp.model.services.barcodeAMImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;

import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class BarcodeAppBean  {
   // private RichCommandButton editButtonBind;
    private RichTable barCodeTableBind;
    private RichInputText inactiveRsnBind;
    private RichInputDate inactiveDtBind;
    private RichInputText itmNameBind;
    private RichPanelGroupLayout searchPanel;
    private RichLink editButtonBind;
    private Integer count=-1;

    public BarcodeAppBean() {
    }
    private boolean form_readOnly = true;
    private boolean editButton = false;
    private boolean saveButton = true;
    
    public Object resolvElDCMsg(String data) 
    {
          FacesContext fc = FacesContext.getCurrentInstance();
          Application app = fc.getApplication();
          ExpressionFactory elFactory = app.getExpressionFactory();
          ELContext elContext = fc.getELContext();
          ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
          return valueExp.getValue(elContext);
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

    public void itemTableSelectionListener(SelectionEvent selectionEvent) {
      
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        ADFUtil.invokeEL("#{bindings.ItemPrf1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
        Row selectedRow = (Row)ADFUtil.evaluateEL("#{bindings.ItemPrf1Iterator.currentRow}");
        String itmId = (String)selectedRow.getAttribute("ItmId");
        

        ViewObject barCode = am.getAppItmBarcode2();

        barCode.setRangeSize(barCode.getRowCount());
        Row row[] = barCode.getAllRowsInRange();

        String itmIdBC = "";
        int count = 0;

        for (Row r : row) {
            itmIdBC = (String)r.getAttribute("ItmId");
            if (itmId.equalsIgnoreCase(itmIdBC)) {
                count++;
            }
        }
       
        if (count > 0) {
            //editButtonBind.setText("Edit");
        editButtonBind.setText(resolvElDCMsg("#{bundle['MSG.399']}").toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
          /*   ADFUtil.invokeEL("#{bindings.ItemPrf1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                             new Object[] { selectionEvent }); */
        } else {
            //editButtonBind.setText("Add");
            editButtonBind.setText(resolvElDCMsg("#{bundle['MSG.21']}").toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
           /*  Row crRow = barCode.createRow();
            crRow.setAttribute("ItmId", itmId);
            crRow.setAttribute("BcNo", "NotAvailable");
            barCode.insertRow(crRow);
            barCode.executeQuery(); */
        }
    }

    public void activeValueChangeListener(ValueChangeEvent vce) {
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();
            String newVal = vce.getNewValue().toString();

            ViewObject v1 = am.getAppItmBarcode2();
            Row row = v1.getCurrentRow();
           
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
                inactiveRsnBind.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDtBind);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date)Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDtBind);
            }

        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void saveButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            this.setForm_readOnly(true);
            this.editButton = false;
            this.saveButton = true;
            FacesMessage saveMsg=new FacesMessage(resolvElDCMsg("#{bundle['MSG.257']}").toString());
            saveMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, saveMsg);
        }
        barCodeTableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
    }

    public void cancelButton(ActionEvent actionEvent) {
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        Row row=am.getAppItmBarcode2().getCurrentRow();
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("ItemPrf1Iterator");
                        Key parentKey = am.getItemPrf1().getCurrentRow().getKey();
                        
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        parentIter.executeQuery();
        this.setForm_readOnly(true);
        this.editButton = false;
        this.saveButton = true;
        if(parentKey!=null){
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        am.getAppItmBarcode2().executeQuery();
        am.getAppItmBarcode2().setCurrentRow(row);
        barCodeTableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
    }

    public void editButton(ActionEvent actionEvent) {
                
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
       
        Row selectedRow = (Row)ADFUtil.evaluateEL("#{bindings.ItemPrf1Iterator.currentRow}");
        String itmId = (String)selectedRow.getAttribute("ItmId");
      
        ViewObject barCode = am.getAppItmBarcode2();

        barCode.setRangeSize(barCode.getRowCount());
        Row row[] = barCode.getAllRowsInRange();

        String itmIdBC = "";
        int count = 0;

        for (Row r : row) {
            itmIdBC = (String)r.getAttribute("ItmId");
            if (itmId.equalsIgnoreCase(itmIdBC)) {
                count++;
            }
        }
      
        if (count > 0) {
            this.setForm_readOnly(false);
            this.editButton = true;
            this.saveButton = false;
        } else {
         
            Row crRow = barCode.createRow();
            crRow.setAttribute("ItmId", itmId);
          //  crRow.setAttribute("BcNo", "NotAvailable");
            barCode.insertRow(crRow);
           //barCode.executeQuery();
            this.setForm_readOnly(false);
            this.editButton = true;
            this.saveButton = false;
        }
        barCodeTableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);
    }

    public void setForm_readOnly(boolean form_readOnly) {
        this.form_readOnly = form_readOnly;
    }

    public boolean isForm_readOnly() {
        return form_readOnly;
    }

    public void setEditButton(boolean editButton) {
        this.editButton = editButton;
    }

    public boolean isEditButton() {
        return editButton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void barcodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String barcodeNm = object.toString();
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        ViewObjectImpl v= am.getAppItmBarcode();
       // ViewObjectImpl v1= am.getAppItmBarcode2();
        int totalCount=v.getRowCount();  //get ALL rows
        int rangeSize=v.getRangeSize(); //all in range
        v.setRangeSize(totalCount);
        Row[] rArray=v.getAllRowsInRange();
        Row cRow=v.getCurrentRow();
        int count=0;
        String barCode = "";
        for(Row r:rArray){
            if(!r.equals(cRow)){
                try{
                        barCode=r.getAttribute("BcNo").toString(); 
                
                   }
                catch(NullPointerException npe){
                        System.out.println("NPE:"+npe);
                   barCode="";
                   }
        if(barCode!=null && !barCode.equalsIgnoreCase("NOTAVAILABLE")){
        if(barcodeNm.equalsIgnoreCase(barCode)){
            count=count+1;
        }
    }
    }
}
     
        v.setRangeSize(rangeSize);  //set to original range
        if(count>1){
              String msg2=resolvElDCMsg("#{bundle['MSG.46']}").toString();
              FacesMessage message2 = new FacesMessage(msg2);
              message2.setSeverity(FacesMessage.SEVERITY_ERROR);
              throw new ValidatorException(message2);
        }
        
        String expression = "^[a-zA-Z0-9]*$";
        String inputStr = barcodeNm;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.258']}").toString(),
                                                         resolvElDCMsg("#{bundle['MSG.259']}").toString()));
        }
    }

  /*  public void setEditButtonBind(RichCommandButton editButtonBind) {
        this.editButtonBind = editButtonBind;
    }

    public RichCommandButton getEditButtonBind() {
        return editButtonBind;
    }
*/
    public void setBarCodeTableBind(RichTable barCodeTableBind) {
        this.barCodeTableBind = barCodeTableBind;
    }

    public RichTable getBarCodeTableBind() {
        return barCodeTableBind;
    }

    public void setInactiveRsnBind(RichInputText inactiveRsnBind) {
        this.inactiveRsnBind = inactiveRsnBind;
    }

    public RichInputText getInactiveRsnBind() {
        return inactiveRsnBind;
    }

    public void setInactiveDtBind(RichInputDate inactiveDtBind) {
        this.inactiveDtBind = inactiveDtBind;
    }

    public RichInputDate getInactiveDtBind() {
        return inactiveDtBind;
    }

    public void setItmNameBind(RichInputText itmNameBind) {
        this.itmNameBind = itmNameBind;
    }

    public RichInputText getItmNameBind() {
        return itmNameBind;
    }
    public void searchButtonOnItm(ActionEvent actionEvent) {
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        ViewObjectImpl v = am.getItemPrf1();
        v.setWhereClause("");
        v.executeQuery();
        if (getItmNameBind().getValue() != null) {
        
            String search = getItmNameBind().getValue().toString().toUpperCase();
           
            v.setWhereClause("upper(ITM_DESC) like '%" + search + "%'");
            v.executeQuery();
        }
    }

    public void resetButtonOnItm(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        barcodeAMImpl am = (barcodeAMImpl)resolvElDC("barcodeAMDataControl");
        
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        
        resetValueInputItems(fctx, searchPanel);
       
        ViewObjectImpl v = am.getItemPrf1();
        v.setWhereClause(null);
        v.executeQuery();
        
        am.getItemPrf1().setWhereClause("CLD_ID= '"+cldId+"' AND HO_ORG_ID='"+hoOrgId+"' AND SLOC_ID ="+slocid);   
        am.getItemPrf1().executeQuery();
        am.getAppItmBarcode2().setWhereClause("CLD_ID= '"+cldId+"' AND HO_ORG_ID='"+hoOrgId+"' AND SLOC_ID ="+slocid); 
        am.getAppItmBarcode2().executeQuery();
    }
    
    public String resolvEl(String data){
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           String Message=valueExp.getValue(elContext).toString();
           return Message;
         } 
    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }

    public void setEditButtonBind(RichLink editButtonBind) {
        this.editButtonBind = editButtonBind;
    }

    public RichLink getEditButtonBind() {
        return editButtonBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }
}
