package appitemprofile.view.bean;


import appitemprofile.model.services.ItemProfileAMImpl;

import appitemprofile.model.views.ViewItemGrpLOVImpl;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.domain.Number;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.model.NumberRange;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;
import oracle.jbo.domain.Date;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;


public class ItemSearchBean {
    private RichInputRangeSlider amountSlider;
    private String amName = "ItemProfileAMDataControl";
    private RichInputListOfValues itemUOM;
    private RichPanelGroupLayout searchPanel;
    private RichInputText itemDesc;
    private RichSelectBooleanCheckbox stockableFlag;
    private RichSelectBooleanCheckbox serviceItem;
    private RichSelectBooleanCheckbox capitalGdFlag;
    private RichInputText itemGrpName;
    private RichSelectBooleanCheckbox purItemFlag;
    private RichSelectBooleanCheckbox slsItemFlg;
    private RichSelectBooleanCheckbox taxExmptFlg;
    private RichInputDate fromDate;
    private RichInputDate toDate;
    private RichTable searchTable;
    private Number maxNo;
    //private RichSelectBooleanCheckbox actv;
    private RichPopup searchGrpPopup;
   private Integer count=-1;
    private RichInputListOfValues itemGrupList;
    private RichSelectOneRadio actv;
    private RichPopup popbindVar;
    private RichInputText itmAttrBind;
    private RichSelectBooleanCheckbox sampleItmFlg;

    public ItemSearchBean() {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        count=(Integer) bindings.getOperationBinding("on_load_page").execute();
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

    public BigDecimal getMaxNo()  {
       ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        String query = "select max(PRICE_BASIC) from  APP$SEARCH$ITM_VW";
        DBTransaction dbt = am.getDBTransaction();
        BigDecimal max = new BigDecimal(0);
       // BigInteger max=BigInteger.ZERO;
        BigDecimal b=new BigDecimal(0);

        try {
            ResultSet rs = dbt.createStatement(0).executeQuery(query);
            if (rs.next()) {
               //  max = (rs.getLong(1));
                if(rs.getObject(1)!=null){
                 b=((BigDecimal)(rs.getObject(1)));
                 max=b;
                }else{
                  max=b;
                }
            }
            rs.close();
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        }
        
        if(max.compareTo(new BigDecimal(Integer.MAX_VALUE))==1){
            max=new BigDecimal(Integer.MAX_VALUE);
        } 
      // System.out.println(max);
      
        return  max; 
       /*  ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        String query = "select max(PRICE_BASIC) from  APP$SEARCH$ITM_VW";
        DBTransaction dbt = am.getDBTransaction();
        Number max = new Number(0);
        BigDecimal max1=new BigDecimal(0);

        try {
            ResultSet rs = dbt.createStatement(0).executeQuery(query);
            if (rs.next()) {
                
                max1 = (BigDecimal)rs.getObject(1);
            }
            rs.close();
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        }
        
        
        System.out.println(max1);
        //2,147,483,647
        if(max1.intValue() > 2147483646){
            max1=new BigDecimal(2147483646);
        }
          
        return max1; */
        
    }

    public Number getMaxIncreament() {
        //return getMaxNo().intValue() / 4;
        return null;
       /*  Number fr=new Number(4);
        return getMaxNo().divide(fr); */
    }

    public Number getMinIncreament() {
        return null;
        //return getMaxIncreament().divide(new Number(5));
    }

    public void setAmountSlider(RichInputRangeSlider amountSlider) {
            this.amountSlider = amountSlider;
    }

    public RichInputRangeSlider getAmountSlider() {
        return amountSlider;
    }

    public void searchItem(ActionEvent actionEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObject itemSearch = am.getItemSearch();
        NumberRange minMax = (NumberRange)getAmountSlider().getValue();
         String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
         String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
         String P_CLDID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
         Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
      /*   Number min;
        Number max;
        try {
            min = new Number(minMax.getMinimum());
            max= new Number(minMax.getMaximum());
        } catch (SQLException e) {
            System.out.println("sqlecxpn");
        } */
          ViewObjectImpl orgVo = am.getItemSearch();
          ViewCriteria vc = orgVo.getViewCriteria("ItemSearchVOCriteria");
          orgVo.applyViewCriteria(vc);
          
         // System.out.println("itemGrupList : "+itemGrupList.getValue());
          
        itemSearch.setNamedWhereClauseParam("BindAmtFrom", minMax.getMinimum());
        itemSearch.setNamedWhereClauseParam("BindAmtTo", minMax.getMaximum());
        itemSearch.setNamedWhereClauseParam("BindUomDesc", itemUOM.getValue());
        itemSearch.setNamedWhereClauseParam("BindGrpNm", itemGrupList.getValue());
        itemSearch.setNamedWhereClauseParam("BindItmDesc", itemDesc.getValue());
        itemSearch.setNamedWhereClauseParam("BindFromDate", fromDate.getValue());
        itemSearch.setNamedWhereClauseParam("BindToDate", toDate.getValue());
        itemSearch.setNamedWhereClauseParam("BindItmId", null);
         itemSearch.setNamedWhereClauseParam("BindGrpId", null);

        if (capitalGdFlag.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindCapitalGd", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindCapitalGd", null);
        if (serviceItem.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindServiceItem", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindServiceItem", null);
        if (stockableFlag.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindStokable", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindStokable", null);
        if (purItemFlag.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindPurItem", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindPurItem", null);
        if (slsItemFlg.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindSaleItem", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindSaleItem", null);
        if (taxExmptFlg.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindTaxExempt", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindTaxExempt", null);
      /*  if (actv.getValue().equals(true)) {
            itemSearch.setNamedWhereClauseParam("BindActv", "Y");
        } else
            itemSearch.setNamedWhereClauseParam("BindActv", "N");  */
        System.out.println(" active radio button value  "+actv.getValue());
        
        itemSearch.setNamedWhereClauseParam("BindActv", actv.getValue());
       itemSearch.setNamedWhereClauseParam("bindHoOrgId", P_HOORGID);
         itemSearch.setNamedWhereClauseParam("bindCldId", P_CLDID);
         itemSearch.setNamedWhereClauseParam("bindSlocId", P_SLOCID);
        itemSearch.executeQuery();

     }


    public void setItemUOM(
RichInputListOfValues itemUOM) {
        this.itemUOM = itemUOM;
    }

    public RichInputListOfValues getItemUOM() {
        return itemUOM;
    }

    public void resetAction(ActionEvent actionEvent) {
        String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        OperationBinding resetOp = (OperationBinding)bindings.get("defaultItmSearchView");
        resetOp.execute(); 
        amountSlider.setMinimum(0);
       /*
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObject itemSearch = am.getItemSearch();
        itemSearch.setNamedWhereClauseParam("BindAmtFrom", null);
        itemSearch.setNamedWhereClauseParam("BindAmtTo", null);
        itemSearch.setNamedWhereClauseParam("BindUomDesc", null);
        itemSearch.setNamedWhereClauseParam("BindGrpNm", null);
        itemSearch.setNamedWhereClauseParam("BindItmDesc", null);
        itemSearch.setNamedWhereClauseParam("BindFromDate", null);
        itemSearch.setNamedWhereClauseParam("BindToDate", null);
        itemSearch.setNamedWhereClauseParam("BindCapitalGd", null);
        itemSearch.setNamedWhereClauseParam("BindServiceItem", null);
        itemSearch.setNamedWhereClauseParam("BindStokable", null);
        itemSearch.setNamedWhereClauseParam("BindPurItem", null);
        itemSearch.setNamedWhereClauseParam("BindSaleItem", null);
        itemSearch.setNamedWhereClauseParam("BindTaxExempt", null);
        itemSearch.setNamedWhereClauseParam("BindActv", "Y");
        itemSearch.setNamedWhereClauseParam("bindHoOrgId", P_HOORGID); 
        itemSearch.executeQuery();
        */
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
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


    public List itemAutoSuggest(String string) {
        String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        ////query of the suggest list. The View Object instance has a View
        ////Criteria assigned
        OperationBinding setVariable = (OperationBinding)bindings.get("setSuggestItemDesc");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();
        
        setVariable = (OperationBinding)bindings.get("setBindSlocId");
                setVariable.getParamsMap().put("value", P_SLOCID);
                setVariable.execute();
                
        setVariable = (OperationBinding)bindings.get("setBindCldId");
                setVariable.getParamsMap().put("value", P_CLDID);
                setVariable.execute();
          
        setVariable = (OperationBinding)bindings.get("setBindHoOrgId");
                setVariable.getParamsMap().put("value", P_HOORGID);
                setVariable.execute();
                
        setVariable = (OperationBinding)bindings.get("setBindActv");
                setVariable.getParamsMap().put("value", "Y");
                setVariable.execute();       
                
    
        
        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding)bindings.get("ViewItmLOV");
        //re-query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet, the list of queries entries, is of type        JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            selectItems.add(new SelectItem((String)rw.getAttribute("ItmDesc"), (String)rw.getAttribute("ItmDesc")));
        }
        return selectItems;

    }

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }

    public void setItemDesc(RichInputText itemDesc) {
        this.itemDesc = itemDesc;
    }

    public RichInputText getItemDesc() {
        return itemDesc;
    }

    public void setStockableFlag(RichSelectBooleanCheckbox stockableFlag) {
        this.stockableFlag = stockableFlag;
    }

    public RichSelectBooleanCheckbox getStockableFlag() {
        return stockableFlag;
    }

    public void setServiceItem(RichSelectBooleanCheckbox serviceItem) {
        this.serviceItem = serviceItem;
    }

    public RichSelectBooleanCheckbox getServiceItem() {
        return serviceItem;
    }

    public void setCapitalGdFlag(RichSelectBooleanCheckbox capitalGdFlag) {
        this.capitalGdFlag = capitalGdFlag;
    }

    public RichSelectBooleanCheckbox getCapitalGdFlag() {
        return capitalGdFlag;
    }

    public void setGroupName(String grpId) {
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewItemGrpLOVImpl grpLOV = am.getViewItemGrpLOV();
        grpLOV.setWhereClause("GRP_ID = '" + grpId + "'");
        grpLOV.executeQuery();
        if (grpLOV.getRowCount() > 0) {
            Object attribute = grpLOV.first().getAttribute("GrpNm");
            itemGrpName.setValue(attribute);
        } else {
            String msg = resolveElExp("#{bundle['MSG.360']}").toString();
            FacesMessage messg = new FacesMessage(msg);
            messg.setSeverity(messg.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, messg);
        }
    }

    public void setItemGrpName(RichInputText itemGrpName) {
        this.itemGrpName = itemGrpName;
    }

    public RichInputText getItemGrpName() {
        return itemGrpName;
    }

    public void setPurItemFlag(RichSelectBooleanCheckbox purItemFlag) {
        this.purItemFlag = purItemFlag;
    }

    public RichSelectBooleanCheckbox getPurItemFlag() {
        return purItemFlag;
    }

    public void setSlsItemFlg(RichSelectBooleanCheckbox slsItemFlg) {
        this.slsItemFlg = slsItemFlg;
    }

    public RichSelectBooleanCheckbox getSlsItemFlg() {
        return slsItemFlg;
    }

    public void setTaxExmptFlg(RichSelectBooleanCheckbox taxExmptFlg) {
        this.taxExmptFlg = taxExmptFlg;
    }

    public RichSelectBooleanCheckbox getTaxExmptFlg() {
        return taxExmptFlg;
    }

    public void setFromDate(RichInputDate fromDate) {
        this.fromDate = fromDate;
    }

    public RichInputDate getFromDate() {
        return fromDate;
    }

    public void setToDate(RichInputDate toDate) {
        this.toDate = toDate;
    }

    public RichInputDate getToDate() {
        return toDate;
    }

    public Date changeDate(Object dt) {
        Date returnDate = null;
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");

        String dateStr = dateFormat.format(dt);
        try {
            java.util.Date date2 = dateFormat.parse(dateStr);
            java.sql.Date sqldate = new java.sql.Date(date2.getTime());
            returnDate = new oracle.jbo.domain.Date(sqldate);
           // System.out.println("Current Date Time : jbo " + returnDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return returnDate;
    }

    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
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
    public void deleteItem(ActionEvent actionEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObject impl = am.getItemSearch();
        Row currRow = impl.getCurrentRow();
        String itmId = (String)currRow.getAttribute("ItmId");
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        BindingContext bc = BindingContext.getCurrent();
        BindingContainer bindings = bc.getCurrentBindingsEntry();
        OperationBinding check = bindings.getOperationBinding("isItemDeletable");
        check.getParamsMap().put("itemId", itmId);
        String isDeletable = (String)check.execute();
        if (isDeletable.equals("Y")) {
            OperationBinding delete = bindings.getOperationBinding("deleteItem");
            delete.getParamsMap().put("itemId", itmId);
            delete.getParamsMap().put("SlocId", slocid);
            delete.getParamsMap().put("CldId", cldId);
            delete.getParamsMap().put("hoOrgId", hoOrgId);
            delete.execute();
            am.getDBTransaction().commit();
            impl.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
            String msg = resolveElExp("#{bundle['MSG.365']}").toString();
            FacesMessage messg = new FacesMessage(msg);
            messg.setSeverity(messg.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, messg);
        } else {
            String msg = resolveElExp("#{bundle['MSG.366']}").toString();
            FacesMessage messg = new FacesMessage(msg);
            messg.setSeverity(messg.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, messg);
        }

    }

    public void setMaxNo(Number maxNo) {
        this.maxNo = maxNo;
    }

   /*  public void setActv(RichSelectBooleanCheckbox actv) {
        this.actv = actv;
    }

    public RichSelectBooleanCheckbox getActv() {
        return actv;
    } */

    public void groupSerachOkAction(ActionEvent actionEvent) {
        getSearchGrpPopup().hide();
    }

    public void setSearchGrpPopup(RichPopup searchGrpPopup) {
        this.searchGrpPopup = searchGrpPopup;
    }

    public RichPopup getSearchGrpPopup() {
        return searchGrpPopup;
    }

    public String goToCreateItem() {
       ItemProfile itmPrf = new ItemProfile();
            itmPrf.callToSearchBean();
            itmPrf.setDisablePartial(true); 
        return "AddNewItem";
    }

    public String goToItmView() {
     ItemProfile itmPrf = new ItemProfile();
            itmPrf.callToSearchBean(); 
        return "View";
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setItemGrupList(RichInputListOfValues itemGrupList) {
        this.itemGrupList = itemGrupList;
    }

    public RichInputListOfValues getItemGrupList() {
        return itemGrupList;
    }
    
    
    public String setAmountSilider()
    {
        //System.out.println("Call ...Sliders..");    
    amountSlider.setMinimum(0);
   // System.out.println("Call Sliders..");
    return "moveForward";
    }

    public void setActv(RichSelectOneRadio actv) {
        this.actv = actv;
    }

    public RichSelectOneRadio getActv() {
        return actv;
    }

    public void deleteListenerPopUp(DialogEvent dialogEvent) {
        // Add event code here...
      //  System.out.println(dialogEvent.getOutcome().name());
        if(dialogEvent.getOutcome().name().equals("ok"))
        {deleteItem(null);}
        
    }
    
    private void showPopup(RichPopup pop, boolean visible) {
    try {
    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null && pop != null) {
    String popupId = pop.getClientId(context);
    if (popupId != null) {
    StringBuilder script = new StringBuilder();
    script.append("var popup =  AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
    if (visible) {
    script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
    } else {
    script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
    }
    ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());
    }
    }
    } catch (Exception e) {
    throw new RuntimeException(e);
    }
}

    public String deleteItemAction() {
        // Add event code here...
        showPopup(popbindVar, true);
        return null;
    }

    public void setPopbindVar(RichPopup popbindVar) {
        this.popbindVar = popbindVar;
    }

    public RichPopup getPopbindVar() {
        return popbindVar;
    }
    
    
    public String getDisablebeforeApproved(){
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row itemCurr = itemPrf.getCurrentRow();
        String actVal = "N";
        if(itemCurr!=null){
        if(itemCurr.getAttribute("Actv")!=null){
            String actv = itemCurr.getAttribute("Actv").toString();
            if(actv.equalsIgnoreCase("Y") || actv.equalsIgnoreCase("N")){
                actVal="Y";
            }
        }
        
        }
        
        return actVal;
    }

    public void setItmAttrBind(RichInputText itmAttrBind) {
        this.itmAttrBind = itmAttrBind;
    }

    public RichInputText getItmAttrBind() {
        return itmAttrBind;
    }

    public void setSampleItmFlg(RichSelectBooleanCheckbox sampleItmFlg) {
        this.sampleItmFlg = sampleItmFlg;
    }

    public RichSelectBooleanCheckbox getSampleItmFlg() {
        return sampleItmFlg;
    }
}
