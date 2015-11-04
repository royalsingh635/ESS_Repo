package suggestedorder.view.bean;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import suggestedorder.model.services.SuggestedOrderAMImpl;


public class SuggestedOrderView {

    private String amName = "SuggestedOrderAMDataControl";
    private RichTable soItemTable;
    private RichColumn detailColumn;
    private RichTreeTable suppGrpTable;
    private String soGrpId;
    private RichSelectOneRadio poType;
    private RichPopup poTypePopUp;
    private RichSelectOneChoice soBasis;
    private RichInputDate fromDate;
    private RichInputDate toDate;
    private RichPanelFormLayout searchPanel;
    private RichPopup itemStkPopup;
    private BigDecimal avlStk;
    private BigDecimal reqStk;
    private BigDecimal ordStk;
    private RichCommandImageLink poRfqGrp;
    private RichCommandImageLink rfqgenbtn;
    private RichSelectOneRadio porfqradio;
    private RichPopup rfqTypePopUp;
    private RichSelectBooleanCheckbox soStatus;


    public SuggestedOrderView() {
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

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public BindingContainer getBindings() {
        BindingContext bc = BindingContext.getCurrent();
        return bc.getCurrentBindingsEntry();
    }

    public DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindings();
    }

    public DCIteratorBinding findIterator(String name) {
        DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");
        }
        return iter;
    }

    public String getSelectedItem() {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl so = am.getViewSoSearchVO();
        ViewObjectImpl grpVO = am.getMmSoGrpVO();
        ViewObjectImpl soVo = am.getMmSo();
        OperationBinding grpIdOperation = getBindings().getOperationBinding("getGroupId");
       /*  grpIdOperation.getParamsMap().put("sloc", value);
        grpIdOperation.getParamsMap().put("hoOrgId", value);
        grpIdOperation.getParamsMap().put("cldId", value); */
        Object grpId = grpIdOperation.execute();
        //System.out.println("Group Id generated="+grpId);
        if(grpId!=null)   {
        System.out.println("NOT NULL Group Id generated="+grpId);
        setSoGrpId(grpId.toString());
        setExpressionValue("#{pageFlowScope.APP_GRP_ID}", grpId);
     //   RequestContext.getCurrentInstance().getPageFlowScope().put("APP_GRP_ID", grpId);
        RowSetIterator iterator = so.createRowSetIterator(null);
        int i = 0;
        while (iterator.hasNext()) {
            Row soRow = iterator.next();
            so.setCurrentRow(soRow);
            iterator.setCurrentRow(soRow);
            ViewObjectImpl soItem = am.getMmSoItmVO1();
            soItem.executeQuery();
            RowSetIterator rsit = soItem.createRowSetIterator(null);
            while (rsit.hasNext()) {
                Row next_2 = rsit.next();
                if (next_2.getAttribute("TranCheck") != null && next_2.getAttribute("TranCheck").equals(true)) {
                    i++;
                    Row createRow = grpVO.createRow();
                    
                               Row rows[] = soVo.getFilteredRows("DocId",soRow.getAttribute("DocId"));
                               if(rows.length>0)
                               {rows[0].setAttribute("SoClose", "Y");}
                     
                    createRow.setAttribute("SlocId", next_2.getAttribute("SlocId"));
                    createRow.setAttribute("OrgId", next_2.getAttribute("OrgId"));
                    createRow.setAttribute("CldId", next_2.getAttribute("CldId"));
                    createRow.setAttribute("SoGrpId", grpId);
                    createRow.setAttribute("EoId", next_2.getAttribute("EoId"));
                    createRow.setAttribute("ItmId", next_2.getAttribute("ItmId"));
                    createRow.setAttribute("ItmUom", next_2.getAttribute("ItmUom"));
                    createRow.setAttribute("TotSoQty", next_2.getAttribute("SoQty"));
                    createRow.setAttribute("UsrIdCreate", next_2.getAttribute("UsrIdCreate"));            
                    grpVO.insertRow(createRow);
                }
            }

            rsit.closeRowSetIterator();
        }
        iterator.closeRowSetIterator();
        
        if (i == 0) {
            String mass =resolveElExp("#{bundle['MSG.465']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);

            return null;
        } else {
            am.getDBTransaction().postChanges();
               return "group";
        }
      }else{
          System.out.println("Group Id generation returned NULL");      
            return null;
     
      }
    }

    public void setSoItemTable(RichTable soItemTable) {
        this.soItemTable = soItemTable;
    }

    public RichTable getSoItemTable() {
        return soItemTable;
    }

    public void setSupplier(ActionEvent actionEvent) {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl so = am.getMmSo();
        so.getCurrentRow().getAttribute("SoId");
    }


    public void linkClick(ActionEvent actionEvent) {
        Class cls = actionEvent.getComponent().getClass();
        //System.out.println(cls);
    }

    public void returnpopup(ReturnPopupEvent returnPopupEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(returnPopupEvent.getComponent().getParent().getParent());
    }

    public void setDetailColumn(RichColumn detailColumn) {
        this.detailColumn = detailColumn;
    }

    public RichColumn getDetailColumn() {
        return detailColumn;
    }

    public String createSuggestedOrd() {
        // Add event code here...
        return "createSO";
    }

    public void setSuppGrpTable(RichTreeTable suppGrpTable) {
        this.suppGrpTable = suppGrpTable;
    }

    public RichTreeTable getSuppGrpTable() {
        return suppGrpTable;
    }

    public String getTempValue() {
        UIXTree tree = getSuppGrpTable();
        RowKeySet _disclosedRowKeys = new RowKeySetTreeImpl(true);
        _disclosedRowKeys.setCollectionModel(ModelUtils.toTreeModel(tree.getValue()));
        tree.setDisclosedRowKeys(_disclosedRowKeys);

        return null;
    }

    public void setSoGrpId(String soGrpId) {
        this.soGrpId = soGrpId;
    }

    public String getSoGrpId() {
        return soGrpId;
    }

    public static void setExpressionValue(String expression, Object newValue) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        Class bindClass = valueExp.getType(elContext);
        if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
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


    public String createPurchaseOrd() {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl eoTempVO = am.getViewGrpEoTempVO();
        //  ViewObjectImpl grpVO = am.getMmSoGrpVO();
        OperationBinding insItmGrp = getBindings().getOperationBinding("insSuppItmGrp");
        OperationBinding generateDraftPO = getBindings().getOperationBinding("generatePO");
        RowSetIterator rsIt = eoTempVO.createRowSetIterator(null);
        //System.out.println(rsIt.getRowCount());
        Integer returnVal=0;
        ArrayList al = new ArrayList();
        int i = 0;
        while (rsIt.hasNext()) {
            Row next = rsIt.next();
            al.add(next.getAttribute("EoId"));
            //System.out.println(next.getAttribute("EoId"));
           // System.out.println(next.getAttribute("TranSelectSupp"));
            if (next.getAttribute("TranSelectSupp") != null && next.getAttribute("TranSelectSupp").equals(true)) {
                i++;
                Map map = insItmGrp.getParamsMap();
                map.put("p_SLOCID", next.getAttribute("SlocId"));
                map.put("p_CLDID", next.getAttribute("CldId"));
                map.put("p_ORGID", next.getAttribute("OrgId"));
                map.put("p_SOGRPID", next.getAttribute("SoGrpId"));
                map.put("p_EOID", next.getAttribute("EoId"));
                map.put("p_USRID", ADFContext.getCurrent().getSecurityContext().getUserName());
               
                returnVal=(Integer) insItmGrp.execute();
               // System.out.println("RETURN VAL---->"+returnVal);
              //  if(returnVal.equals(1))
                    
            }
        }
        if (i == 0) {
            String mass = resolveElExp("#{bundle['MSG.466']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
        } else {
            if(returnVal!=null || returnVal!=-1){
                
            OperationBinding save = getBindings().getOperationBinding("Commit");
            save.execute();
            // System.out.println(poType.getValue());
            Map map = generateDraftPO.getParamsMap();
            map.put("p_SLOCID", resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            map.put("p_CLDID", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            map.put("p_ORGID", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}"));
            map.put("p_SOGRPID", resolveElExp("#{pageFlowScope.APP_GRP_ID}"));
            map.put("p_POTYPE", 170);
            map.put("p_USRID", ADFContext.getCurrent().getSecurityContext().getUserName());
           
            generateDraftPO.execute();
            save.execute();

                OperationBinding getfy = getBindings().getOperationBinding("getFyId");
                getfy.getParamsMap().put("cld", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}"));
                getfy.getParamsMap().put("org", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}"));
                getfy.getParamsMap().put("ts", new Timestamp(System.currentTimeMillis()));
                getfy.execute();
                
            ViewObjectImpl draftPoVO = am.getViewDraftPoVO();
            draftPoVO.executeQuery();
            draftPoVO.setWhereClause("so_grp_id = '" + resolveElExp("#{pageFlowScope.APP_GRP_ID}") + "'  and FY_ID="+(Integer)getfy.getResult());
            draftPoVO.executeQuery();
            showPopup(poTypePopUp, true);
            
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,resolveElExp("#{bundle['MSG.467']}").toString(),null));
            }
        }

        return null;
    }

    public void setPoType(RichSelectOneRadio poType) {
        this.poType = poType;
    }

    public RichSelectOneRadio getPoType() {
        return poType;
    }

    public void poTypeDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "viewSuggOrd");

        }

    }

    public void setPoTypePopUp(RichPopup poTypePopUp) {
        this.poTypePopUp = poTypePopUp;
    }

    public RichPopup getPoTypePopUp() {
        return poTypePopUp;
    }

    public void executeWithParam(ActionEvent actionEvent) {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObject itemSearch = am.getViewSoSearchVO();

        itemSearch.setNamedWhereClauseParam("BindSOBasis", soBasis.getValue());
        itemSearch.setNamedWhereClauseParam("BindFromDate", fromDate.getValue());
        itemSearch.setNamedWhereClauseParam("BindToDate", toDate.getValue());
       
        if(soStatus.getValue()!=null){
            
            if(soStatus.getValue().toString().equals("true"))
            {itemSearch.setNamedWhereClauseParam("BindStatus", "Y");}
           
            if(soStatus.getValue().toString().equals("false"))
            {itemSearch.setNamedWhereClauseParam("BindStatus", "N");}
            
        }

        itemSearch.executeQuery();

    }

    public void setSoBasis(RichSelectOneChoice soBasis) {
        this.soBasis = soBasis;
    }

    public RichSelectOneChoice getSoBasis() {
        return soBasis;
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

    public void resetAction(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObject itemSearch = am.getViewSoSearchVO();
        itemSearch.setNamedWhereClauseParam("BindSOBasis", "");
        itemSearch.setNamedWhereClauseParam("BindFromDate", "");
        itemSearch.setNamedWhereClauseParam("BindToDate", "");
        itemSearch.executeQuery();
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
            }
            else if(item instanceof RichSelectBooleanCheckbox) {
               RichSelectBooleanCheckbox  input=(RichSelectBooleanCheckbox)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
            }

        };
            }
    }

    public void setSearchPanel(RichPanelFormLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelFormLayout getSearchPanel() {
        return searchPanel;
    }

    public void viewSO(ActionEvent actionEvent) {

        RichShowDetailItem soDetailitem = (RichShowDetailItem)actionEvent.getComponent().getParent().getParent();

        String title = soDetailitem.getText();
        int i = title.indexOf(".");
        String soId = title.substring(title.length() - 14, title.length()).trim();
        Integer p_SLOCID = Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String p_CLDID = resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_ORGID = resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        //System.out.println(soId);

        //  setExpressionValue("#{pageFlowScope.APP_SO_ID}", soId);
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObject clause = am.getMmSo();
        clause.setWhereClause("SO_ID ='" + soId + "' AND SLOC_ID = "+p_SLOCID+" AND ORG_ID = '"+p_ORGID+"' AND CLD_ID = '"+p_CLDID+"'");
        clause.executeQuery();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().getNavigationHandler().handleNavigation(context, null, "viewSO");

    }


    public void fromDateVCE(ValueChangeEvent valueChangeEvent) {
      
        AdfFacesContext.getCurrentInstance().addPartialTarget(toDate);
    }

    public void getItemHistory(ActionEvent actionEvent) {
        // Add event code here...
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl impl = am.getMmSoItm();
        Row currRow = impl.getCurrentRow();
        /* if(currRow != null)
        {System.out.println("curr : "+currRow.getAttribute("ItmId"));}
        System.out.println("curr : "+currRow);
         */OperationBinding getStock = getBindings().getOperationBinding("getStock");
        getStock.getParamsMap().put("slocid", resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        getStock.getParamsMap().put("cldid", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        getStock.getParamsMap().put("itmId", "ITEM001");
        //getStock.getParamsMap().put("itmId", currRow.getAttribute("ItmId"));
        getStock.getParamsMap().put("orgid", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        getStock.getParamsMap().put("date", new Timestamp());
      //  getStock.getParamsMap().put("date", currRow.getAttribute("UsrIdCreateDt"));
        getStock.execute();
        //   System.out.println(am.getAvl_stk() + " " + am.getReq_stk() + " " + am.getOrd_stk());
        if (am.getAvl_stk() != null) {
            setAvlStk(am.getAvl_stk());
        } else {
            setAvlStk(new BigDecimal(0));
        }
        if (am.getReq_stk() != null) {
            setReqStk(am.getReq_stk());
        } else {
            setReqStk(new BigDecimal(0));
        }
        if (am.getOrd_stk() != null) {
            setOrdStk(am.getOrd_stk());
        } else {
            setOrdStk(new BigDecimal(0));
        }
      showPopup(itemStkPopup, true);
    }

    public void setItemStkPopup(RichPopup itemStkPopup) {
        this.itemStkPopup = itemStkPopup;
    }

    public RichPopup getItemStkPopup() {
        return itemStkPopup;
    }


    public void setAmName(String amName) {
        this.amName = amName;
    }

    public String getAmName() {
        return amName;
    }

    public void setAvlStk(BigDecimal avlStk) {
        this.avlStk = avlStk;
    }

    public BigDecimal getAvlStk() {
        return avlStk;
    }

    public void setReqStk(BigDecimal reqStk) {
        this.reqStk = reqStk;
    }

    public BigDecimal getReqStk() {
        return reqStk;
    }

    public void setOrdStk(BigDecimal ordStk) {
        this.ordStk = ordStk;
    }

    public BigDecimal getOrdStk() {
        return ordStk;
    }

    public String createRFQ() {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl eoTempVO = am.getViewGrpEoTempVO();
        ViewObjectImpl soVO = am.getMmSo();
        
        Row curRow = soVO.getCurrentRow();
        //  ViewObjectImpl grpVO = am.getMmSoGrpVO();
        //OperationBinding insItmGrp = getBindings().getOperationBinding("insSuppItmGrp");
        
        OperationBinding generateDraftPO = getBindings().getOperationBinding("generateRFQ");
        RowSetIterator rsIt = eoTempVO.createRowSetIterator(null);
        
        //System.out.println(rsIt.getRowCount());
        Integer returnVal=0;
        ArrayList al = new ArrayList();
        
        int i = 0;
        /* 
         * comment on 12-Apr-2014
         * while (rsIt.hasNext()) {
            Row next = rsIt.next();
            al.add(next.getAttribute("EoId"));
            //System.out.println(next.getAttribute("EoId"));
           // System.out.println(next.getAttribute("TranSelectSupp"));
            if (next.getAttribute("TranSelectSupp") != null && next.getAttribute("TranSelectSupp").equals(true)) {
                i++;
                Map map = insItmGrp.getParamsMap();
                map.put("p_SLOCID", next.getAttribute("SlocId"));
                map.put("p_CLDID", next.getAttribute("CldId"));
                map.put("p_ORGID", next.getAttribute("OrgId"));
                map.put("p_SOGRPID", next.getAttribute("SoGrpId"));
                map.put("p_EOID", next.getAttribute("EoId"));
                map.put("p_USRID", ADFContext.getCurrent().getSecurityContext().getUserName());
               
                returnVal=(Integer) insItmGrp.execute();
               // System.out.println("RETURN VAL---->"+returnVal);
            }
        } 
        
        if (i == 0) {
            String mass = resolveElExp("#{bundle['MSG.466']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
        } else {
            if(returnVal!=null || returnVal!=-1){
       
          
        */     
            OperationBinding save = getBindings().getOperationBinding("Commit");
            save.execute();
        
            // System.out.println(poType.getValue());
            Map map = generateDraftPO.getParamsMap();
            map.put("p_SLOCID", resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            map.put("p_CLDID", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            map.put("p_ORGID", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}"));
            map.put("p_SOGRPID", resolveElExp("#{pageFlowScope.APP_GRP_ID}"));
            map.put("p_DOCID", null);
            map.put("p_USRID", ADFContext.getCurrent().getSecurityContext().getUserName());
           
            generateDraftPO.execute();
            save.execute();

                OperationBinding getfy = getBindings().getOperationBinding("getFyId");
                getfy.getParamsMap().put("cld", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}"));
                getfy.getParamsMap().put("org", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}"));
                getfy.getParamsMap().put("ts", new Timestamp(System.currentTimeMillis()));
                getfy.execute();
                
            ViewObjectImpl rfqVo = am.getViewRFQVO1();
            rfqVo.executeQuery();
            rfqVo.setWhereClause("DOC_ID_SRC = '" +resolveElExp("#{pageFlowScope.APP_GRP_ID}")+ "' and FY_ID="+(Integer)getfy.getResult());
            rfqVo.executeQuery();
            showPopup(rfqTypePopUp, true);
            
          /*   }
          else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,resolveElExp("#{bundle['MSG.467']}").toString(),null));
            } 
        }
        */
        return null;
    }

    public void onSelectionAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Object obj = valueChangeEvent.getNewValue();
        if(obj != null)
        { 
             String flg = (String)obj;
             if(flg.equals("rfq"))
             {
            rfqgenbtn.setVisible(true);
            poRfqGrp.setVisible(false);
             }
             else if(flg.equals("po"))
             {
                 rfqgenbtn.setVisible(false);
                 poRfqGrp.setVisible(true);
                 
             }
            OperationBinding clrOpr = getBindings().getOperationBinding("clearAll");
            clrOpr.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(rfqgenbtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poRfqGrp);
    }

    public void setPoRfqGrp(RichCommandImageLink poRfqGrp) {
        this.poRfqGrp = poRfqGrp;
    }

    public RichCommandImageLink getPoRfqGrp() {
        return poRfqGrp;
    }

    public void setRfqgenbtn(RichCommandImageLink rfqgenbtn) {
        this.rfqgenbtn = rfqgenbtn;
    }

    public RichCommandImageLink getRfqgenbtn() {
        return rfqgenbtn;
    }

    public void setPorfqradio(RichSelectOneRadio porfqradio) {
        this.porfqradio = porfqradio;
    }

    public RichSelectOneRadio getPorfqradio() {
        return porfqradio;
    }

    public String createRFQAction() {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl so = am.getViewSoSearchVO();
        ViewObjectImpl grpVO = am.getMmSoGrpVO();
        ViewObjectImpl soVo = am.getMmSo();
        Set<SoBean> set = new HashSet<SoBean>();
        OperationBinding grpIdOperation = getBindings().getOperationBinding("getGroupId");
        Object grpId = grpIdOperation.execute();
        if(grpId!=null)   {
        setSoGrpId(grpId.toString());
        setExpressionValue("#{pageFlowScope.APP_GRP_ID}", grpId);
        
        RowSetIterator iterator = so.createRowSetIterator(null);
        int i = 0;
        while (iterator.hasNext()) {
            Row soRow = iterator.next();
            so.setCurrentRow(soRow);
            iterator.setCurrentRow(soRow);
            ViewObjectImpl soItem = am.getMmSoItmVO1();
            soItem.executeQuery();
            RowSetIterator rsit = soItem.createRowSetIterator(null);
            while (rsit.hasNext()) {
                Row next_2 = rsit.next();
                if (next_2.getAttribute("TranCheck") != null && next_2.getAttribute("TranCheck").equals(true)) {
                    i++;
                         
                    
                               Row rows[] = soVo.getFilteredRows("DocId",soRow.getAttribute("DocId"));
                               if(rows.length>0)
                               {rows[0].setAttribute("SoClose", "Y");}
                    
     /*               Row createRow = grpVO.createRow(); 
                    createRow.setAttribute("SlocId", next_2.getAttribute("SlocId"));
                    createRow.setAttribute("OrgId", next_2.getAttribute("OrgId"));
                    createRow.setAttribute("CldId", next_2.getAttribute("CldId"));
                    createRow.setAttribute("SoGrpId", grpId);
                    // createRow.setAttribute("EoId", next_2.getAttribute("EoId"));
                    createRow.setAttribute("ItmId", next_2.getAttribute("ItmId"));
                    createRow.setAttribute("ItmUom", next_2.getAttribute("ItmUom"));
                    createRow.setAttribute("TotSoQty", next_2.getAttribute("SoQty"));
                    createRow.setAttribute("UsrIdCreate", next_2.getAttribute("UsrIdCreate"));
                    grpVO.insertRow(createRow);
       */              
                    
                    Integer slocId = (Integer)next_2.getAttribute("SlocId");
                    String orgId = (String)next_2.getAttribute("OrgId");
                    String cldId = (String)next_2.getAttribute("CldId");
                    String itmId = (String)next_2.getAttribute("ItmId");
                    String itmUom = (String)next_2.getAttribute("ItmUom");
                    oracle.jbo.domain.Number soQty = (oracle.jbo.domain.Number)next_2.getAttribute("SoQty");
                    Integer usrId = (Integer)next_2.getAttribute("UsrIdCreate");
                    set.add(new SoBean(slocId,cldId,orgId,itmId,itmUom,String.valueOf(grpId),usrId,soQty));    //add Item into HashSet<SoBean>
                    
                } //End IF
            } rsit.closeRowSetIterator();
        }iterator.closeRowSetIterator();
        
        
        // Iterate Set and Add Elements into SoItm ViewObject
         for(SoBean obj:set)
         {
            Row createRow = grpVO.createRow();
            createRow.setAttribute("SlocId", obj.getSlocId());
            createRow.setAttribute("OrgId", obj.getOrgId());
            createRow.setAttribute("CldId", obj.getCldId());
            createRow.setAttribute("SoGrpId", obj.getGroupId());
            createRow.setAttribute("ItmId", obj.getItmId());
            createRow.setAttribute("ItmUom", obj.getItmUom());
            createRow.setAttribute("TotSoQty", obj.getTotQty());
            createRow.setAttribute("UsrIdCreate", obj.getUserId());
            grpVO.insertRow(createRow);
        }
        
        
       if (i == 0) {
            String mass =resolveElExp("#{bundle['MSG.465']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
           return null;
        } else {
            am.getDBTransaction().postChanges();
            grpVO.setWhereClause("SO_GRP_ID = '"+grpId+"'");
            grpVO.executeQuery();
            return "towardsRfq";
        } 
        }
        
        else{
          System.out.println("Group Id generation returned NULL");      
            return null;
        }
         
     }

    public void setRfqTypePopUp(RichPopup rfqTypePopUp) {
        this.rfqTypePopUp = rfqTypePopUp;
    }

    public RichPopup getRfqTypePopUp() {
        return rfqTypePopUp;
    }

    public void rfqTypeDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
                  FacesContext context = FacesContext.getCurrentInstance();
                  context.getApplication().getNavigationHandler().handleNavigation(context, null, "backSO");
      }
    }
    
    
    public String resetRadioGroup()
    {
      RichSelectOneRadio obj =this.porfqradio;
      obj.setValue("");
      return "backToRollBack";
    }

    public void SupplierVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl soVo = am.getViewSoSearchVO();
        Row soVoRow = soVo.getCurrentRow();
        
        ViewObjectImpl soItmVo = am.getMmSoItmVO1();
        Row row = soItmVo.getCurrentRow();
   
          System.out.println(row);
        
        Row selectedRow = (Row)ADFUtil.evaluateEL("#{bindings.MmSoItmIterator.currentRow}");
        
        if(row != null)
        {
            Integer eoId = (Integer)row.getAttribute("EoId");
            String itmId = (String)row.getAttribute("ItmId");
            
            if(eoId != null && itmId != null)
             {
                  OperationBinding opr = getBindings().getOperationBinding("getPricePolcy");
                  opr.getParamsMap().put("eoId", eoId);
                  opr.getParamsMap().put("itmId", itmId);
                  opr.execute();
                  Integer obj =(Integer)opr.getResult();
                  System.out.println("Objct : "+obj);
              }
            else
            { //Select Supplier
              }
        }
    }


    public void setSoStatus(RichSelectBooleanCheckbox soStatus) {
        this.soStatus = soStatus;
    }

    public RichSelectBooleanCheckbox getSoStatus() {
        return soStatus;
    }
}
