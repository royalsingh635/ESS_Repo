package suggestedorder.view.bean;

import java.io.Serializable;

import java.math.BigDecimal;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.jbo.domain.Number;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

import org.apache.myfaces.trinidad.util.Service;

import suggestedorder.model.services.SuggestedOrderAMImpl;
import suggestedorder.model.views.MmSoItmVORowImpl;

public class SuggestedOrderBean {

    private String amName = "SuggestedOrderAMDataControl";
    private RichSelectOneChoice suggOrdBasis;
    private RichInputDate soFromDate;
    private RichInputDate soToDate;
    private RichPopup poPopup;
    private RichPopup soPopUp;
    private RichTable soItemTable;
    private RichPopup manualPopUp;
    private String mode = modeGet();
    private RichSelectBooleanCheckbox checkRow;
    private BigDecimal avlStk;
    private BigDecimal reqStk;
    private BigDecimal ordStk;
    private RichPopup itemStkPopup;
    private Number Zero=new Number(0);
    private RichTable soSuggestTable;
    private RichCommandButton getItemButton;
    private RichPopup prPopUp;
    private Date fromDate;

    public SuggestedOrderBean() {
    }

    public static String modeGet() {
        String strVal = (String)resolveElExp("#{pageFlowScope.PARAM_SO_MODE}");
       // System.out.println("Str : "+strVal);
        return strVal;
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

    /**
     * Function to open a popup.
     */
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

    public BindingContainer getBindings() {
        BindingContext bc = BindingContext.getCurrent();
        return bc.getCurrentBindingsEntry();
    }

    public void generateSuggOrder(ActionEvent actionEvent) {
        
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObject itemQ = am.getItemQuery();  //reorderview
        ViewObjectImpl soItem = am.getMmSoItm();
        ViewObjectImpl so=am.getMmSo();
        String soid=null;
        if(so.getCurrentRow().getAttribute("SoId")!=null){
            soid=so.getCurrentRow().getAttribute("SoId").toString();
        }
     //   System.out.println("before  " + soItem.getRowCount());
        if (soItem.getRowCount() > 0) {
            Row[] allRowsInRange = soItem.getAllRowsInRange();
            for (int i = 0; i < allRowsInRange.length; i++) {
                allRowsInRange[i].remove();
            }
        }
        soItem.executeQuery();
        
   //   System.out.println("after " + soItem.getRowCount());
    if(suggOrdBasis.getValue()!=null){
        if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 166) {
          //  System.out.println("Inside reorder level");
          String cldId = (String)resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID1}");
          String orgId = (String)resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG1}");
          Integer slocId = Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC1}").toString());
          //  System.out.println("Inside reorder level");
          soFromDate.setRequired(false);
          soToDate.setRequired(false);
          itemQ.setWhereClause("CURRENT_STK < REORDER_LVL and CLD_ID='"+ cldId +"' and ORG_ID= '"+orgId +"' and SLOC_ID ="+slocId);
          itemQ.executeQuery();
            RowSetIterator rowSetIterator = itemQ.createRowSetIterator(null);
            while (rowSetIterator.hasNext()) {
                Row next = rowSetIterator.next();
                MmSoItmVORowImpl createRow = (MmSoItmVORowImpl)soItem.createRow();
                createRow.setAttribute("ItmId", next.getAttribute("ItmId"));
             //   System.out.println("Itm id reorder ---->"+next.getAttribute("ItmId"));
              //  createRow.setTranItmName((String)createRow.getItemDesc(createRow.getItmId()));
                createRow.setAttribute("ReorderLvl", next.getAttribute("ReorderLvl"));
                createRow.setAttribute("SafetyQty", next.getAttribute("SafeQty"));
                createRow.setAttribute("CurrentStk", next.getAttribute("CurrentStk"));
                createRow.setAttribute("ItmUom", next.getAttribute("ItmUom"));
                soItem.insertRow(createRow);
            }
            rowSetIterator.closeRowSetIterator();
            setMode("E");
         /*    itemQ.setWhereClause(" ");
            itemQ.executeQuery(); */
          //  setMode("E");
        } 
        
        
        else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 167) {
            String cldId = (String)resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID1}");
            String orgId = (String)resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            Integer slocId = Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC1}").toString());
            
            itemQ.setWhereClause("CURRENT_STK < SAFE_QTY and CLD_ID='"+ cldId +"' and ORG_ID= '"+orgId +"' and SLOC_ID ="+slocId);
            itemQ.executeQuery();
            soFromDate.setRequired(false);
            soToDate.setRequired(false);
            RowSetIterator rowSetIterator = itemQ.createRowSetIterator(null);
            while (rowSetIterator.hasNext()) {
                Row next = rowSetIterator.next();
                MmSoItmVORowImpl createRow = (MmSoItmVORowImpl)soItem.createRow();
                createRow.setAttribute("ItmId", next.getAttribute("ItmId"));
                createRow.setTranItmName((String)createRow.getItemDesc(createRow.getItmId()));
                createRow.setAttribute("ReorderLvl", next.getAttribute("ReorderLvl"));
                createRow.setAttribute("SafetyQty", next.getAttribute("SafeQty"));
                createRow.setAttribute("CurrentStk", next.getAttribute("CurrentStk"));
                createRow.setAttribute("ItmUom", next.getAttribute("ItmUom"));
                soItem.insertRow(createRow);
            }
            rowSetIterator.closeRowSetIterator();
            setMode("E");
          /*   itemQ.setWhereClause(" ");
            itemQ.executeQuery();
            setMode("E"); */
        } 
        
        
        else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 165) {//po
            ViewObjectImpl poView = am.getViewPOQuery1();
            if(soFromDate.getValue()==null){
                String mass = resolvElDCMsg("#{bundle['MSG.468']}").toString();
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(soFromDate.getClientId(), msg);
            }else if(soToDate.getValue()==null){
                String mass =resolvElDCMsg("#{bundle['MSG.469']}").toString();
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(soToDate.getClientId(), msg);
            }else{
                String cldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
                Integer slocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                String orgId= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
                
                poView.setNamedWhereClauseParam("bindCldId", cldId);  
                poView.setNamedWhereClauseParam("bindSlocId", slocId);
                poView.setNamedWhereClauseParam("bindOrgId", orgId);
                poView.setNamedWhereClauseParam("bindFrmDt", soFromDate.getValue());
                poView.setNamedWhereClauseParam("bindToDt", soToDate.getValue());
               /*  poView.setWhereClause("trunc(DOC_DT) between to_Date('" + soFromDate.getValue() +
                                      "','yyyy-mm-dd') and to_date('" + soToDate.getValue() + "','yyyy-mm-dd')");
            */
            poView.executeQuery();
              //  System.out.println("Query : "+poView.getQuery());
            showPopup(poPopup, true);
                soItem.executeQuery();
                setMode("E");
               /*  itemQ.setWhereClause(" ");
                itemQ.executeQuery();
                setMode("E"); */
            }
        } 
        
        else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 164) {  //so
            ViewObjectImpl poView = am.getSoQueryVO1();
            if(soFromDate.getValue()==null){
                String mass = resolvElDCMsg("#{bundle['MSG.468']}").toString();
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(soFromDate.getClientId(), msg);
            }else if(soToDate.getValue()==null){
                String mass =resolvElDCMsg("#{bundle['MSG.469']}").toString();
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(soToDate.getClientId(), msg);
            }else{
                String cldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
                Integer slocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                String orgId= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
                
                poView.setNamedWhereClauseParam("bindCldId", cldId);  
                poView.setNamedWhereClauseParam("bindSlocId", slocId);
                poView.setNamedWhereClauseParam("bindOrgId", orgId);
                poView.setNamedWhereClauseParam("bindFrmDt", soFromDate.getValue());
                poView.setNamedWhereClauseParam("bindToDt", soToDate.getValue());
                
           /*  poView.setWhereClause("trunc(DOC_DT) between to_Date('" + soFromDate.getValue() +
                                  "','yyyy-mm-dd') and to_date('" + soToDate.getValue() + "','yyyy-mm-dd') and so_id != '"+soid+"'"); */
            poView.executeQuery();
            showPopup(soPopUp, true);
                soItem.executeQuery();
                setMode("E");
              /*  
                */
            }
          } 
        
            else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 277) {  //PR
          
                        ViewObjectImpl prView = am.getPurReqQueyVO1();
                        if(soFromDate.getValue()==null){
                            String mass = resolvElDCMsg("#{bundle['MSG.468']}").toString();
                            FacesMessage msg = new FacesMessage(mass);
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(soFromDate.getClientId(), msg);
                        }else if(soToDate.getValue()==null){
                            String mass =resolvElDCMsg("#{bundle['MSG.469']}").toString();
                            FacesMessage msg = new FacesMessage(mass);
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(soToDate.getClientId(), msg);
                        }else{
                            String cldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
                            Integer slocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                            String orgId= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
                            
                            
                        prView.setNamedWhereClauseParam("bindFyId", null);
                        prView.setNamedWhereClauseParam("bindCldId", cldId);
                        prView.setNamedWhereClauseParam("bindSlocId", slocId);
                        prView.setNamedWhereClauseParam("bindOrgId", orgId);
                        prView.setNamedWhereClauseParam("bindFrmDt", soFromDate.getValue());
                        prView.setNamedWhereClauseParam("bindToDt", soToDate.getValue());
                        prView.executeQuery();
                        showPopup(prPopUp, true);
                            setMode("E");
                        //soItem.executeQuery();
                          /*  
                            */
                        }
                      }
        
         
                           itemQ.setWhereClause(" ");
                           itemQ.executeQuery();
                          
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
        }else{
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.470']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(suggOrdBasis.getClientId(), message);
        }
      
    }

    public void suggOrdCriteria(ValueChangeEvent valueChangeEvent) {

    }

    public void createSuggOrder(ActionEvent actionEvent) {
        OperationBinding createOrder = getBindings().getOperationBinding("Createwithparameters");
        createOrder.execute();
        setMode("A");
    }

    public void setSuggOrdBasis(RichSelectOneChoice suggOrdBasis) {
        this.suggOrdBasis = suggOrdBasis;
    }

    public RichSelectOneChoice getSuggOrdBasis() {
        return suggOrdBasis;
    }
    /*
     * For Save
     * */
    public String saveSuggestedOrder() {
        
        Boolean flg = saveAndUpdate();
        if(flg){return "BackTosearch";}
        
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl soItem = am.getMmSoItm();
        RowSetIterator createRowSetIterator = soItem.createRowSetIterator(null);
        ArrayList removeRow = new ArrayList();
        String ok="Y";
        Number zero=new Number(0);
        int j = 0;
        int noOfRows=createRowSetIterator.getRowCount();
        while (createRowSetIterator.hasNext()) {
            Row next = createRowSetIterator.next();
            if (next.getAttribute("TranCheck") != null && next.getAttribute("TranCheck").equals(false)) {
                //   System.out.println(next.getAttribute("SafetyQty"));
                removeRow.add(next.getKey());
                // soItem.removeCurrentRow();
            } else {
                j++;
             //   System.out.println("sorgbasis------"+suggOrdBasis.getValue());
                //check if soQty is 0 or null 
                if(Integer.parseInt(suggOrdBasis.getValue().toString()) == 164 || Integer.parseInt(suggOrdBasis.getValue().toString())==165 || Integer.parseInt(suggOrdBasis.getValue().toString()) == 167 || Integer.parseInt(suggOrdBasis.getValue().toString()) == 166 || Integer.parseInt(suggOrdBasis.getValue().toString()) == 168){
                    if(next.getAttribute("SoQty")==null || ((Number)next.getAttribute("SoQty")).compareTo(zero) ==0){
                             ok="N";
                    }else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 167) {
                     
                          next.setAttribute("ReqQty", next.getAttribute("tranReqSaftyQty"));
                     
                   } else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 166) {
                   
                          next.setAttribute("ReqQty", next.getAttribute("tranReqReordQty"));
                     }
                }
            }
        }
        createRowSetIterator.closeRowSetIterator();
      //  System.out.println("ok:"+ok);
        if (j == 0) {
          //  System.out.println("----noofRows----"+noOfRows);
            if(noOfRows>0){
               String mass = resolvElDCMsg("#{bundle['MSG.471']}").toString();
               FacesMessage msg = new FacesMessage(mass);
               msg.setSeverity(FacesMessage.SEVERITY_INFO);
               FacesContext fctx = FacesContext.getCurrentInstance();
               fctx.addMessage(null, msg);
               return null;
            }else if(noOfRows==0){
                String mass =resolvElDCMsg("#{bundle['MSG.472']}").toString();
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(null, msg);
                return null;
            }
            return null;
        }else if(ok.equals("N")){
            String mass = resolvElDCMsg("#{bundle['MSG.473']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            return null;
            
        } else {

        //    System.out.println("unchecked rows.........." + removeRow.size());
            for (int i = 0; i < removeRow.size(); i++) {
             //   System.out.println(i);
                Row r = soItem.getRow((Key)removeRow.get(i));
                r.remove();
            }
            soItem.executeQuery();
            
            OperationBinding genOp = getBindings().getOperationBinding("genSoNumber");
            genOp.execute();
            Object obj=genOp.getResult();
            OperationBinding createOrder = getBindings().getOperationBinding("Commit");
            createOrder.execute();
            if(createOrder.getErrors().isEmpty()){
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.1456']}").toString()+" "+obj+" "+resolvElDCMsg("#{bundle['MSG.227']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, message);
                setMode("V");
            }
           
            return "";
        }
    }


    public void selAllRows(ValueChangeEvent valueChangeEvent) {
        Boolean isSelected = (Boolean)valueChangeEvent.getNewValue();
      //  System.out.println(valueChangeEvent.getNewValue());
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl soItem = am.getMmSoItm();
        RowSetIterator createRowSetIterator = soItem.createRowSetIterator(null);

        while (createRowSetIterator.hasNext()) {
            Row next = createRowSetIterator.next();
            if (isSelected) {
                next.setAttribute("TranCheck", Boolean.TRUE);
            } else
                next.setAttribute("TranCheck", Boolean.FALSE);
        }
        createRowSetIterator.closeRowSetIterator();
        soItem.executeQuery();
    }

    public void selAllPo(ValueChangeEvent valueChangeEvent) {
        Boolean isSelected = (Boolean)valueChangeEvent.getNewValue();

      //  System.out.println(valueChangeEvent.getNewValue());
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl poView = am.getViewPOQuery1();
        RowSetIterator createRowSetIterator = poView.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {
            Row next = createRowSetIterator.next();
            if (isSelected) {
                next.setAttribute("selectPO", Boolean.TRUE);
            } else
                next.setAttribute("selectPO", Boolean.FALSE);
        }
        createRowSetIterator.closeRowSetIterator();
    }

    public void getItemFromPo(ActionEvent actionEvent) {

    }

    public void setSoFromDate(RichInputDate soFromDate) {
        this.soFromDate = soFromDate;
    }

    public RichInputDate getSoFromDate() {
        return soFromDate;
    }

    public void setSoToDate(RichInputDate soToDate) {
        this.soToDate = soToDate;
    }

    public RichInputDate getSoToDate() {
        return soToDate;
    }
    /*
     * For Suggested order using PO
     * */
    public void poDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
            ViewObjectImpl impl = am.getMmSo();

            Object DocId = impl.getCurrentRow().getAttribute("DocId");
         //   System.out.println("docid  po     "+impl.getCurrentRow().getAttribute("DocId"));
            ViewObjectImpl poView = am.getViewPOQuery1();
            ViewObjectImpl poItems = am.getViewPoItemQuery();
            ViewObjectImpl mmSoItemPo = am.getMmSoItmPoVO();
            RowSetIterator createRowSetIterator = poView.createRowSetIterator(null);
            while (createRowSetIterator.hasNext()) {
                Row next = createRowSetIterator.next();
                if (next.getAttribute("selectPO") != null && next.getAttribute("selectPO").equals(true)) {
                  
                    poItems.setWhereClause("DOC_ID = '" + next.getAttribute("DocId") + "'");
                    poItems.executeQuery();
                    RowSetIterator iterator = poItems.createRowSetIterator(null);
                    
                    while (iterator.hasNext()) {
                        Row next_2 = iterator.next();
                        
                         Row createRow = mmSoItemPo.createRow();
                        createRow.setAttribute("SlocId", next_2.getAttribute("SlocId"));
                        createRow.setAttribute("OrgId", next_2.getAttribute("OrgId"));
                        createRow.setAttribute("CldId", next_2.getAttribute("CldId"));
                        createRow.setAttribute("DocId", DocId);
                        createRow.setAttribute("PoDocId", next_2.getAttribute("DocId"));
                        createRow.setAttribute("ItmId", next_2.getAttribute("ItmId"));
                        createRow.setAttribute("ItmUom", next_2.getAttribute("ItmUom"));
                        createRow.setAttribute("OrdQty", next_2.getAttribute("OrdQty"));
                        createRow.setAttribute("TlrncQtyType", next_2.getAttribute("TlrncQtyType"));
                        createRow.setAttribute("TlrncQtyVal", next_2.getAttribute("TlrncQtyVal"));
                        createRow.setAttribute("ItmAmtBs", next_2.getAttribute("ItmAmtBs"));
                        createRow.setAttribute("ItmAmtSp", next_2.getAttribute("ItmAmtSp"));
                    }
                    iterator.closeRowSetIterator(); //SM-10/5
                }
            }
            createRowSetIterator.closeRowSetIterator(); //SM-10/5
            am.getDBTransaction().postChanges();
                        
            ViewObjectImpl ItemQuery = am.getViewItemWiseQty();
            ViewObjectImpl soItem = am.getMmSoItm();
            ItemQuery.executeQuery();
            
            
            RowSetIterator iterator = ItemQuery.createRowSetIterator(null);
            while (iterator.hasNext()) {
                Row itms = iterator.next();
                //   Row createRow = soItem.createRow();
                MmSoItmVORowImpl createRow = (MmSoItmVORowImpl)soItem.createRow();
                createRow.setAttribute("ItmId", itms.getAttribute("ItmId"));
           //    System.out.println(createRow.getTranItmName());
                createRow.setTranItmName((String)createRow.getItemDesc(createRow.getItmId()));
                //  createRow.setAttribute("ReorderLvl", itms.getAttribute("ReorderLvl"));
                //  createRow.setAttribute("SafetyQty", itms.getAttribute("SafeQty"));
                createRow.setAttribute("CurrentStk", itms.getAttribute("CrntStk"));
                createRow.setAttribute("ReqQty", itms.getAttribute("OrdQty"));
                createRow.setAttribute("ItmUom", itms.getAttribute("ItmUom"));
                soItem.insertRow(createRow);
            }
            iterator.closeRowSetIterator(); //SM-10/5
            soItem.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
            
        }
    }

    public void setPoPopup(RichPopup poPopup) {
        this.poPopup = poPopup;
    }

    public RichPopup getPoPopup() {
        return poPopup;
    }

    public Number getReqQty() {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        //  ViewObject itemQ = am.getItemQuery();
        ViewObjectImpl soItem = am.getMmSoItm();
        if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 166) {
            Row currentRow = soItem.getCurrentRow();

            Number reorderStk = (Number)(currentRow.getAttribute("ReorderLvl"));
            Number currentStk = (Number)(currentRow.getAttribute("CurrentStk"));
            return (reorderStk.subtract(currentStk));
        } else if (Integer.parseInt(suggOrdBasis.getValue().toString()) == 167) {
            Row currentRow = soItem.getCurrentRow();

            Number SafetyQty = (Number)(currentRow.getAttribute("SafetyQty"));
            Number currentStk = (Number)(currentRow.getAttribute("CurrentStk"));
            return (SafetyQty.subtract(currentStk));
        } else
            return null;
    }

    public void setSoPopUp(RichPopup soPopUp) {
        this.soPopUp = soPopUp;
    }

    public RichPopup getSoPopUp() {
        return soPopUp;
    }

    public void selectAllSo(ValueChangeEvent valueChangeEvent) {

          DCBindingContainer bc = (DCBindingContainer)getBindings();
           DCIteratorBinding dcit = bc.findIteratorBinding("MmSoForQueryVOIterator");
    //    System.out.println("CheckBox--valueChangeEvent.getNewValue()"+valueChangeEvent.getNewValue());
        Boolean isSelected = (Boolean)valueChangeEvent.getNewValue();
     //   System.out.println(isSelected);
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl soView = am.getMmSoForQueryVO();
        RowSetIterator rit = soView.createRowSetIterator(null);
        //    RowSetIterator rit = dcit.getRowSetIterator();
     //   System.out.println(rit);
       // System.out.println(rit.getRowCount());

        while (rit.hasNext()) {
            Row next = rit.next();
            if (isSelected) {
               // System.out.println("--val of chkbx--"+next.getAttribute("TranSelectSO"));
                next.setAttribute("TranSelectSO", Boolean.valueOf(true));
             //   System.out.println("--val of chkbx--"+next.getAttribute("TranSelectSO"));
             //   System.out.println(next.getAttribute("TranSelectSO"));
            } else
                next.setAttribute("TranSelectSO", Boolean.valueOf(false));
        }

        soView.executeQuery();
        rit.closeRowSetIterator();
        dcit.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(checkRow);
        AdfFacesContext.getCurrentInstance().addPartialTarget(soSuggestTable);
        
    }
/*
 * For Suggested order using SO
 * */
    public void soDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
            ViewObjectImpl impl = am.getMmSo();
            ViewObjectImpl soItem = am.getMmSoItm();
            Object DocId = impl.getCurrentRow().getAttribute("DocId");
            ViewObjectImpl soView = am.getSoQueryVO1();   //MMSO
            ViewObjectImpl soItems = am.getMmSoItmForQueryVO(); //MMSOITM
            ViewObjectImpl mmSoItemPo = am.getMmSoItmPoVO();
          
            /**
             * delete from mmsoitmPo
             * */
             
           
            
            /**
             * First create row in soitmPo, i.e all items from previous SOs.
             * */
            
            RowSetIterator createRowSetIterator = soView.createRowSetIterator(null);
            while (createRowSetIterator.hasNext()) {
                Row next = createRowSetIterator.next();
            //    System.out.println("1");
                if (next.getAttribute("TranSelectSO") != null && next.getAttribute("TranSelectSO").equals(true)) {  //for selected  SO
                    soItems.setWhereClause("DOC_ID = '" + next.getAttribute("DocId") + "'");
                    soItems.executeQuery();
           //         System.out.println("2");
                    RowSetIterator iterator = soItems.createRowSetIterator(null);
                    while (iterator.hasNext()) {
                        Row next_2 = iterator.next();
                       if( next_2.getAttribute("ItmId")!=null){  //changed by shanto--remove null row
                        Row createRow = mmSoItemPo.createRow();   //insert into mmSoItmPO from mmSoItm after filtering (doc_id)
                        createRow.setAttribute("SlocId", next_2.getAttribute("SlocId"));
                        createRow.setAttribute("OrgId", next_2.getAttribute("OrgId"));
                        createRow.setAttribute("CldId", next_2.getAttribute("CldId"));
                        createRow.setAttribute("DocId", DocId);
                        createRow.setAttribute("PoDocId", next_2.getAttribute("DocId"));
                        createRow.setAttribute("ItmId", next_2.getAttribute("ItmId"));

                        createRow.setAttribute("ItmUom", next_2.getAttribute("ItmUom"));
                        createRow.setAttribute("OrdQty", next_2.getAttribute("SoQty"));
                        //  createRow.setAttribute("TlrncQtyType", next_2.getAttribute("TlrncQtyType"));
                        //  createRow.setAttribute("TlrncQtyVal", next_2.getAttribute("TlrncQtyVal"));
                        // createRow.setAttribute("ItmAmtBs", next_2.getAttribute("ItmAmtBs"));
                        //  createRow.setAttribute("ItmAmtSp", next_2.getAttribute("ItmAmtSp"));
              //          soItems.insertRow(createRow);
                     }
                    }
                }
            }
            
            mmSoItemPo.executeQuery();
       //     System.out.println("3");
            am.getDBTransaction().postChanges();
         //   System.out.println("4");
            
            /**
             * Use the viewItemWiseQty to group items on the basis of items put in soitempo.
             * Then add the rows in mmsoItm
             * */
            
            //from ViewItemWiseQty query to soitm
            ViewObjectImpl ItemQuery = am.getViewItemWiseQty();
           
            ItemQuery.executeQuery();
      //      System.out.println("5");
            
            RowSetIterator iterator = ItemQuery.createRowSetIterator(null);
            while (iterator.hasNext()) {
                Row itms = iterator.next();
                // Row createRow = soItem.createRow();
             if(itms.getAttribute("ItmId")!=null){
                MmSoItmVORowImpl createRow = (MmSoItmVORowImpl)soItem.createRow();
                createRow.setAttribute("ItmId", itms.getAttribute("ItmId"));
             //  System.out.println("-createRow.getTranItmName()-"+createRow.getTranItmName()); 
             ///     Row[] r=am.getViewItemLov().getFilteredRows("ItmId", itms.getAttribute("ItmId"));
            
           //     createRow.setTranItmName((String)createRow.getItemDesc(createRow.getItmId()));
                //  createRow.setAttribute("ReorderLvl", itms.getAttribute("ReorderLvl"));
             ///     if(r.length>0){
             ///         createRow.setTranItmName(r[0].getAttribute("ItmDesc").toString());
             ///     }
                //  createRow.setAttribute("SafetyQty", itms.getAttribute("SafeQty"));
                createRow.setAttribute("CurrentStk", itms.getAttribute("CrntStk"));
                createRow.setAttribute("ReqQty", itms.getAttribute("OrdQty"));
                createRow.setAttribute("ItmUom", itms.getAttribute("ItmUom"));
                soItem.insertRow(createRow);
             }
            }
        //    System.out.println("6");
            
            Row[] soitmPoRows=mmSoItemPo.getFilteredRows("DocId", DocId.toString());
          //  System.out.println("soitmpo rows -"+soitmPoRows.length);
            if(soitmPoRows.length>0){
                for(Row r:soitmPoRows){
                    r.remove();
                }
             // am.getDBTransaction().postChanges();   
            }
            
            mmSoItemPo.executeQuery();
            //am.getDBTransaction().postChanges();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
            
            soItem.executeQuery();
        }
    }

    public void setSoItemTable(RichTable soItemTable) {
        this.soItemTable = soItemTable;
    }

    public RichTable getSoItemTable() {
        return soItemTable;
    }

    public void addManualItem(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("CreateInsert");
        binding.execute();
        setMode("E");
        showPopup(manualPopUp, true);
    }

    public void setManualPopUp(RichPopup manualPopUp) {
        this.manualPopUp = manualPopUp;
    }

    public RichPopup getManualPopUp() {
        return manualPopUp;
    }

    public void backFromPopup(ReturnEvent returnEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
    }

    public void cancelManualPopup(PopupCanceledEvent popupCanceledEvent) {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObject soItem = am.getMmSoItm();
        MmSoItmVORowImpl r = (MmSoItmVORowImpl)soItem.getCurrentRow();
        EntityImpl[] entities = r.getEntities();
        entities[0].getEntityState();
      //  System.out.println(entities[0].getEntityState() + "...........state");
        if (entities[0].getEntityState() == 0) {
            r.remove();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
    }

    public void manualDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
            SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
             am.getMmSoItm().executeQuery();
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public String cancelSO() {
        setMode("");
        return "BackTosearch";
    }

    public void setCheckRow(RichSelectBooleanCheckbox checkRow) {
        this.checkRow = checkRow;
    }

    public RichSelectBooleanCheckbox getCheckRow() {
        return checkRow;
    }

    public void getStockHistory(ActionEvent actionEvent) {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl impl = am.getMmSoItm();
        Row currRow = impl.getCurrentRow();
        
        OperationBinding getStock = getBindings().getOperationBinding("getStock");
        getStock.getParamsMap().put("slocid", resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        getStock.getParamsMap().put("cldid", resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID1}"));
        getStock.getParamsMap().put("itmId", currRow.getAttribute("ItmId"));
        getStock.getParamsMap().put("orgid", resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG1}"));
        getStock.getParamsMap().put("date", currRow.getAttribute("UsrIdCreateDt"));

        getStock.execute();
        System.out.println(am.getAvl_stk() + " ::  " + am.getReq_stk() + " ::: " + am.getOrd_stk());
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

    public void setItemStkPopup(RichPopup itemStkPopup) {
        this.itemStkPopup = itemStkPopup;
    }

    public RichPopup getItemStkPopup() {
        return itemStkPopup;
    }
    
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void validateQty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
       /*  if (object != null && Integer.parseInt(object.toString()) < 0) {
            FacesMessage message = new FacesMessage("Quantity should be greater than 0");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);

        } */
     /*  SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
       ViewObjectImpl impl = am.getMmSoItm();
       Object chk=impl.getCurrentRow().getAttribute("TranCheck");
       System.out.println("--"+chk);
       if(chk!=null){ */
          
        if(object != null){
           Number orderQty = (Number)object;
           Number zero = new Number(0);
           if(orderQty.compareTo(zero) < 0)
           {
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.474']}").toString(), null)); 

           }
        
        if(!isPrecisionValid(26, 6, orderQty))
            {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.57']}").toString(), null)); }
       }                                                                
     }
      
     
    public void soBasisDtVCE(ValueChangeEvent valueChangeEvent) {
       AdfFacesContext.getCurrentInstance().addPartialTarget(soToDate);
    }

    public void soBasisVCE(ValueChangeEvent valueChangeEvent) {
        //#{bindings.SoBasis.inputValue == 164 or bindings.SoBasis.inputValue == 165}
        Object o=valueChangeEvent.getNewValue();
        if(valueChangeEvent.getNewValue()!=null){
            if(o.toString().equals("164") || o.toString().equals("165") || o.toString().equals("277")){
       // if(Integer.parseInt(o.toString())==164 || Integer.parseInt(o.toString())==165){
          //  System.out.println("here");
            soFromDate.setDisabled(false);
            soToDate.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
            soFromDate.setVisible(true);
            soToDate.setVisible(true);
        }else{
            soFromDate.setRequired(false);
            soToDate.setRequired(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soToDate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soFromDate);
            soFromDate.setDisabled(true);
            soToDate.setDisabled(true);     
            soFromDate.setVisible(false);
            soToDate.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soToDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(soFromDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
    }
        
    }

    public void setZero(Number Zero) {
        this.Zero = Zero;
    }

    public Number getZero() {
        return Zero;
    }

    public void tranItmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
                String itmName= object.toString();
                SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
                ViewObjectImpl impl = am.getMmSoItm();
                int count=0;
          //        String itmId= impl.getCurrentRow().getAttribute("ItmId").toString();
                ViewObject vv=am.getViewItemLov();
                String itmId="";
                Row[] r=vv.getFilteredRows("ItmDesc", itmName);
                if(r.length>0){
                   itmId= r[0].getAttribute("ItmId").toString();
                }
                RowSetIterator rsi=impl.createRowSetIterator(null);
                while(rsi.hasNext()){
                    Row rw=rsi.next();
                    if(rw.getAttribute("ItmId")!=null){
                    String name=rw.getAttribute("ItmId").toString();
                  if(name.equalsIgnoreCase(itmId)){
                        count=count+1;
                     }
                    }
                }
                            //System.out.println("count--"+count);
                if(count>1){
                      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.427']}").toString(), null)); 

                } 
        }
    }

    public void setSoSuggestTable(RichTable soSuggestTable) {
        this.soSuggestTable = soSuggestTable;
    }

    public RichTable getSoSuggestTable() {
        return soSuggestTable;
    }

    public void setGetItemButton(RichCommandButton getItemButton) {
        this.getItemButton = getItemButton;
    }

    public RichCommandButton getGetItemButton() {
        return getItemButton;
    }
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
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

    public void setPrPopUp(RichPopup prPopUp) {
        this.prPopUp = prPopUp;
    }

    public RichPopup getPrPopUp() {
        return prPopUp;
    }

    public void prDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding opr = getBindings().getOperationBinding("setPrItmToSoItm");
            opr.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItemButton);
        }
        
    }

    public void frmDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object != null)
        {
           Timestamp tm = new Timestamp((Date)object);
            OperationBinding binding = getBindings().getOperationBinding("getFyId");
            binding.getParamsMap().put("cld",resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}"));
            binding.getParamsMap().put("org", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}"));
            binding.getParamsMap().put("ts", tm);
            binding.execute();
            Object obj = binding.getResult();
            
      //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.474']}").toString(), null));       
        }
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        OperationBinding binding = getBindings().getOperationBinding("getLastFyDate");
        binding.getParamsMap().put("cldId",resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}"));
        binding.getParamsMap().put("orgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}"));
        binding.getParamsMap().put("slocId", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        binding.execute();
        Date date=(Date)binding.getResult();
        fromDate=date;
        return fromDate;
    }

    public void editAction(ActionEvent actionEvent) {
        // Add event code here...
        setMode("En");
    }

    public void closeSoAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("Commit");
        binding.execute();
    }
    
    
    private boolean saveAndUpdate()
    {
        SuggestedOrderAMImpl am = (SuggestedOrderAMImpl)resolvElDC(amName);
        ViewObjectImpl soVo = am.getMmSo();
        Row row=soVo.getCurrentRow();
        Object obj = row.getAttribute("SoId");
        
        if(obj == null)
            {return Boolean.FALSE;}
        
        else
        {
              Integer soBasis = (Integer)row.getAttribute("SoBasis");
               if(soBasis != 168 && mode.equals("En"))
               {
                    OperationBinding binding = getBindings().getOperationBinding("Commit");
                    binding.execute();
                    setMode("");
                    return Boolean.TRUE;
                }
            else if(soBasis == 168 && mode.equals("En"))
               {
                   String soStatus = "N";
                   if(row.getAttribute("SoClose")!=null)
                       soStatus = (String)row.getAttribute("SoClose");
                    if(soStatus.equals("Y"))
                     {
                         OperationBinding binding = getBindings().getOperationBinding("Commit");
                         binding.execute();
                         setMode("");
                        return Boolean.TRUE;
                     }
                }
        }
        return Boolean.FALSE;
    }
    
    public List getSuggestions(String input) {
     
        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>)object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }

}
