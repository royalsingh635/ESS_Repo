package mmpurorder.view.bean;

import java.io.Serializable;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import mmpurorder.model.service.PurOrderAMImpl;

import mmpurorder.model.views.MmDrftPoTrLinesVOImpl;
import mmpurorder.model.views.MmDrftPoTrVOImpl;
import mmpurorder.model.views.PoSearchVOImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/** This class comprises all the methods to search PO related functionalities on the Search Page.
 * @author SM
 * @HandOver NG
 * Dated-10 Dec 2012
 **/

public class PurOrderSearchBean {
    private RichInputText docIdEdit;
    private RichPanelFormLayout searchForm;
    private String visb = "true";
    private RichTable searchTable;
    private int pageNumber;
    private int maxPages;
    private static final int RANGE_SIZE = 10;
    private int toNoOfRows;
   // private RichCommandButton searchButton;
    private RichCommandButton showSearchButton;
    private RichCommandImageLink searchButton;
    private RichPopup deletePoPopup;
    private Integer index=null;
    private RichInputText amtToBinding;
    private RichInputText amtFrmBinding;

    public PurOrderSearchBean() {
    }

    public String createPoAction() {
        // Add event code here...
        return "createNew";
    }

    public String editPoAction() {
        // Add event code here...
        return "gotoPurchase";
    }

    public void setDocIdEdit(RichInputText docIdEdit) {
        this.docIdEdit = docIdEdit;
    }


    public String getDocIdForEdit() {
        return (String)docIdEdit.getValue();
    }

    public RichInputText getDocIdEdit() {
        return docIdEdit;
    }

    /**
     * For Search PO header button functionality
     * */

    public void searchPoVisAction(ActionEvent actionEvent) {
        if (visb.equalsIgnoreCase("true")) {
            searchForm.setVisible(false);

            visb = "false";
        } else if (visb.equalsIgnoreCase("false")) {
            searchForm.setVisible(true);

            visb = "true";
        }

    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setVisb(String visb) {
        this.visb = visb;
    }

    public String getVisb() {
        return visb;
    }

    /**
     *  Method for sorting the PO by date
     * */

    public String sortByDate() {
        //attrib name,isAscending --params for sortCriterion
        List sortList = new ArrayList();
        SortCriterion sc2 = new SortCriterion("UsrIdCreateDt", false);
        sortList.add(sc2);


        RichTable ct = getSearchTable();
        ct.setSortCriteria(sortList);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);

        return null;
    }

    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
    }

    /**
     * Selection event for the paginated table.
     * */
    public void rowSelected(SelectionEvent se) {
        JUCtrlRangeBinding searchView = getSearchView();
        searchView.getIteratorBinding().setCurrentRowIndexInRange((Integer)se.getAddedSet().toArray()[0]);
        System.out.println("Current row selected="+se.getAddedSet().toArray()[0]);
        index = (Integer)se.getAddedSet().toArray()[0];
    }

    /**
     * Method used to go to the last page of searched table.
     * */
    public String goLastPage() {
        JUCtrlRangeBinding searchView = this.getSearchView();
        long lastPageNum = Math.max(1, (searchView.getIteratorBinding().getEstimatedRowCount() - 1) / RANGE_SIZE + 1);
        if (lastPageNum <= 1)
            return null;
        int prePage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        searchView.getIteratorBinding().getNavigatableRowIterator().scrollRange((int)(RANGE_SIZE *
                                                                                      (lastPageNum - prePage)));
        return null;
    }

    /**
     * Method to check if Go to last page button should be enabled.
     * */
    public boolean isLastEnabled() {
        JUCtrlRangeBinding searchView = this.getSearchView();
        long lastPageNum = Math.max(1, (searchView.getIteratorBinding().getEstimatedRowCount() - 1) / RANGE_SIZE + 1);
        long currentPage =
            searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        return currentPage < lastPageNum;
    }


    private JUCtrlRangeBinding getSearchView() {
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
        return (JUCtrlRangeBinding)bindings.getControlBinding("PoSearch");
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Method to calculate the total no of pages present in
     * accordance to the Range Size.
     * */
    public int getPageNumber() {
        int val = 0;
        try {
            val = getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        } catch (NullPointerException np) {
            val = 1;
        }
        return pageNumber = val;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    /**
     * To get the max no. of pages present
     * */
    public int getMaxPages() {
        //Page #{PurOrderSearchBean.pageNumber} of #{(bindings.PoSearch.estimatedRowCount/ bindings.PoSearch.rangeSize)}
        // Long x= ((getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)+1);
        // System.out.println("MAX PAGES:"+Math.floor(((Long)(getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)).doubleValue()));
        /*   Double u= Math.floor(((Long)(getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)).doubleValue());
        return maxPages= u.intValue(); */
        Double x = new Double(getSearchView().getIteratorBinding().getEstimatedRowCount());
        if (getSearchView().getIteratorBinding().getEstimatedRowCount() != 0) {
            Double y = (Double)x / RANGE_SIZE;
            Double u = Math.ceil(y.doubleValue());
            return maxPages = u.intValue();
        } else {
            return 1;
        }

    }

    public void setToNoOfRows(int toNoOfRows) {
        this.toNoOfRows = toNoOfRows;
    }

    public int getToNoOfRows() {
        int y = getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart();
        if (y == -1) {
            y = 0;
        }
        int x = y + (getSearchView().getIteratorBinding().getAllRowsInRange().length);
        return toNoOfRows = x;
    }

    /**
     * To clear the searched query fields
     * */
    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();

   //     ViewObject poSrch = getAm().getPoSearch();
        //  poSrch.resetExecuted();
        /*  poSrch.setWhereClause(" ");
          poSrch.executeQuery();   */
        resetValueInputItems(fctx, searchForm);
        PoSearchVOImpl vo = (PoSearchVOImpl)getAm().getPoSearch();

        /*  vo.setNamedWhereClauseParam("BindEoId", null);
        vo.setNamedWhereClauseParam("BindPoId", null);
        vo.setNamedWhereClauseParam("BindITM_ID", null);
        vo.setNamedWhereClauseParam("BindPOtype", null);
        vo.setNamedWhereClauseParam("BindToDate", null);
        vo.setNamedWhereClauseParam("BindFromDate", null);
        vo.setNamedWhereClauseParam("BindOrgId", null);
        vo.setNamedWhereClauseParam("BindAmtFrom", null);
        vo.setNamedWhereClauseParam("BindAmtTo",null);
        vo.executeQuery();  */

        /*
        OperationBinding operationBinding = getBindings().getOperationBinding("ExecuteWithParams");
        operationBinding.execute();

      /*
        DCBindingContainer dcBindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        dcBindings.resetInputState();
        dcBindings.refreshControl();  */

        //  vo.executeEmptyRowSet();
        // resetValueInputItems1(fctx, searchForm);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(searchForm);
        //   vo.executeQuery();
        getAm().getDBTransaction().rollback();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        //cb1 is the fully qualified name of the button
        System.out.println("---" + searchButton.getId());
        RichCommandButton button = (RichCommandButton)root.findComponent("cil5"); // cb1 will be id of invisible button
        ActionEvent actionEvent = new ActionEvent(this.getSearchButton());
        actionEvent.queue();
       
        return null;
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public PurOrderAMImpl getAm() {
        return (PurOrderAMImpl)resolvElDC("PurOrderAMDataControl");
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

  /*  public void setSearchButton(RichCommandButton searchButton) {
        this.searchButton = searchButton;
    }

    public RichCommandButton getSearchButton() {
        return searchButton;
    }*/

    public void setShowSearchButton(RichCommandButton showSearchButton) {
        this.showSearchButton = showSearchButton;
    }

    public RichCommandButton getShowSearchButton() {
        return showSearchButton;
    }

    public void setSearchButton(RichCommandImageLink searchButton) {
        this.searchButton = searchButton;
    }

    public RichCommandImageLink getSearchButton() {
        return searchButton;
    }
    private static int NUMBER = Types.NUMERIC;
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public void deletePOAL(ActionEvent actionEvent) {
        // Add event code here...
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
       Row currPO=getAm().getPoSearch().getCurrentRow();
       String docIdTxn= currPO.getAttribute("DocId").toString();
       System.out.println("DOCIDTXN--"+docIdTxn);
       Object obj= callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_DOC_WF_CUR_USR(?,?,?,?,?)", new Object[] { sloc_id,cld_id,org_id,18504,docIdTxn });
       Integer usrIdPend=null; 
       if(obj!=null){
           usrIdPend=Integer.parseInt(obj.toString());
       }
       System.out.println("User id pending in impl--"+usrIdPend);
        if(usrIdPend.compareTo(new Integer(-1))==0 || usrIdPend.compareTo(usr_id)==0) {
            Integer createdby = (Integer)currPO.getAttribute("UsrIdCreate");
           if(usr_id.equals(createdby))
           {
                 System.out.println("Show Popup");
                   showPopup(deletePoPopup,true);
               }
           else
           {
                   Row[] usrRows=getAm().getLovUsrId().getFilteredRows("UsrId", createdby);
                   StringBuffer usrName=new StringBuffer("");
                   if(usrRows.length>0){
                       usrName=new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                       usrName=new StringBuffer("[").append(usrName).append("]");
                   } 
                   String msg2 =  resolvElDCMsg("#{bundle['MSG.1720']}").toString()+usrName+ resolvElDCMsg("#{bundle['MSG.1721']}").toString();//"You can not Delete this PO. Only "+usrName+" can Delete this Purchase Order"
                   FacesMessage message2 = new FacesMessage(msg2);
                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null, message2);
               }
        }
        else
        {
                Row[] usrRows=getAm().getLovUsrId().getFilteredRows("UsrId", usrIdPend);
                StringBuffer usrName=new StringBuffer("");
                if(usrRows.length>0){
                    usrName=new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                    usrName=new StringBuffer("[").append(usrName).append("]");
                }
                String msg2 =  resolvElDCMsg("#{bundle['MSG.1722']}").toString()+usrName+ resolvElDCMsg("#{bundle['MSG.1723']}").toString();//"This Purchase Order is pending at ["+usrName+"] for approval, You can not Delete this.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
      
    }
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);

            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
          //  System.out.println("Function return " + st.getObject(1));
            return st.getObject(1);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            //  throw new JboException(e.getMessage().substring(11, end));
            String msg = e.getMessage().substring(11, end);
           
            FacesMessage ermsg = new FacesMessage(msg);
            ermsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, ermsg);
            return null;


        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }

    public void setDeletePoPopup(RichPopup deletePoPopup) {
        this.deletePoPopup = deletePoPopup;
    }

    public RichPopup getDeletePoPopup() {
        return deletePoPopup;
    }


    public void delPopDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
                   Row currPO=getAm().getPoSearch().getCurrentRow();
                   String docIdTxn= currPO.getAttribute("DocId").toString();
                   System.out.println("DOCIDTXN--"+docIdTxn);
                   //Revert status of quotation and rfq.
                   String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                   String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                   Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                   Integer ret=(Integer)(callStoredFunction(Types.INTEGER, "MM.MM_RVRT_TXN_DOC_STAT (?,?,?,?)", new Object[] {cld_id,sloc_id,org_id,docIdTxn}));
                  System.out.println("Return="+ret);
                  if(ret.equals(1))
                  {
//                   Row trlines[]= getAm().getMmDrftPoTrLinesVO1().getFilteredRows("DocId",docIdTxn);
//                   for(Row rw : trlines)
//                       rw.remove();
//                   
//                    Row tr[]= getAm().getMmDrftPoTr2().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : tr)
//                        rw.remove();
//                    
//                    Row dlv[]= getAm().getMmDrftPoDlvSchdl1().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : dlv)
//                        rw.remove();
//                   
//                    Row oc[]= getAm().getMmDrftPoOc1().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : oc)
//                        rw.remove();
//                    
//                    Row tnc[]= getAm().getMmDrftPoTnc1().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : tnc)
//                        rw.remove();
//                    
//                    Row pmt[]= getAm().getMmDrftPoPmtSchdl1().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : pmt)
//                        rw.remove();
//                    
//                    Row itm[]= getAm().getMmDrftPoItm1().getFilteredRows("DocId",docIdTxn);
//                    for(Row rw : itm)
//                        rw.remove();
//                    
//                    Row po[]= getAm().getMmDrftPo().getFilteredRows("DocId",docIdTxn);
//                   if(po.length>0)
//                   {
//                       if(po[0].getAttribute("OrigDocId")!=null)
//                       {
//                           System.out.println("this PO is amendment of->"+po[0].getAttribute("OrigDocId"));
//                               Row amendRow[]= getAm().getMmDrftPo().getFilteredRows("DocId",po[0].getAttribute("OrigDocId"));
//                               if(amendRow.length>0)
//                                amendRow[0].setAttribute("PoMode", 231);
//                           } 
//                       }
//                    for(Row rw : po)
//                        rw.remove();
//                   
//                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
//                    operationBinding.execute();
//                    getAm().getPoSearch().executeQuery();
//                    System.out.println("Delete Done");
//                   AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
                      
                      try{
                      Object ob = (callStoredFunction(Types.INTEGER, "MM.MM_DELETE_PUR_ORDER (?,?,?,?)", new Object[] {cld_id,sloc_id,org_id,docIdTxn}));
                          System.out.println(" return value +"+ob);
                          if(ob!=null){
                              if(((Integer)ob).compareTo(new Integer(1))==0){
                            getAm().getMmDrftPoTrLinesVO1().executeQuery();
                           getAm().getMmDrftPoTr2().executeQuery();
                            getAm().getMmDrftPoDlvSchdl1().executeQuery();
                            getAm().getMmDrftPoOc1().executeQuery();
                            getAm().getMmDrftPoTnc1().executeQuery();
                            getAm().getMmDrftPoCalc().executeQuery();
                            getAm().getMmDrftPoContnr().executeQuery();
                            getAm().getMmDrftPoPorts().executeQuery();
                            getAm().getMmDrftPoFlx1().executeQuery();
                            getAm().getMmDrftPoPmtSchdl1().executeQuery();
                            getAm().getMmDrftPoItm1().executeQuery();
                            getAm().getMmDrftPo().executeQuery();
                            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                            operationBinding.execute();
                            getAm().getPoSearch().executeQuery();
                            System.out.println("Delete Done");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
                            
                        }
                          }
                          
                      }catch(Exception e){
                          e.printStackTrace();
                          FacesMessage message2 = new FacesMessage( resolvElDCMsg("#{bundle['MSG.1724']}").toString());//Something went wrong.Please Contact to ESS!
                          message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                          FacesContext fc = FacesContext.getCurrentInstance();
                          fc.addMessage(null, message2);
                      }
                      
                   
               /*   JUCtrlRangeBinding searchView = getSearchView();
                   searchView.getCurrentRow().remove();
                   if(index.equals(new Integer(0)))
                 searchView.getIteratorBinding().setCurrentRowIndexInRange(index+1);
                   else
                       searchView.getIteratorBinding().setCurrentRowIndexInRange(index-1); */
                  }
                  else
                  {
                          FacesMessage message2 = new FacesMessage( resolvElDCMsg("#{bundle['MSG.1724']}").toString());//Something went wrong.Please Contact to ESS!
                          message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                          FacesContext fc = FacesContext.getCurrentInstance();
                          fc.addMessage(null, message2);
                      }
               } 
    }
    
    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public Object resolvElDCMsg(String data) {
               FacesContext fc = FacesContext.getCurrentInstance();
               Application app = fc.getApplication();
               ExpressionFactory elFactory = app.getExpressionFactory();
               ELContext elContext = fc.getELContext();
               ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
               return valueExp.getValue(elContext);
           } 


    public void fromValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
        if(object!=null)
        {
                oracle.jbo.domain.Number amtTo= (oracle.jbo.domain.Number)object;
                if(amtTo.compareTo(new oracle.jbo.domain.Number(0))<0)
                {
                        String msg2 =  resolvElDCMsg("#{bundle['MSG.1665']}").toString();//Amout can not be less than Zero.
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
            }
        System.out.println("Validating FromValue Amount, object="+object);
        amtToBinding.processUpdates(FacesContext.getCurrentInstance());
        if(object!=null && amtToBinding.getValue()!=null)
        {
        oracle.jbo.domain.Number amtfrom = (oracle.jbo.domain.Number)object;
            oracle.jbo.domain.Number amtTo = (oracle.jbo.domain.Number)amtToBinding.getValue();
            if(amtfrom.compareTo(amtTo)>0)
            {
                    String msg2 =  resolvElDCMsg("#{bundle['MSG.1106']}").toString();//Invalid amount range.
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            } 
    }

    public void toValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(object!=null)
        {
                oracle.jbo.domain.Number amtTo= (oracle.jbo.domain.Number)object;
                if(amtTo.compareTo(new oracle.jbo.domain.Number(0))<0)
                {
                        String msg2 =  resolvElDCMsg("#{bundle['MSG.1665']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
            }
        System.out.println("Validating ToValue Amount, object="+object);
        amtFrmBinding.processUpdates(FacesContext.getCurrentInstance());
        if(object!=null && amtFrmBinding.getValue()!=null)
        {
        oracle.jbo.domain.Number amtTo= (oracle.jbo.domain.Number)object;
            oracle.jbo.domain.Number amtFrom = (oracle.jbo.domain.Number)amtFrmBinding.getValue();
            if(amtFrom.compareTo(amtTo)>0)
            {
                    String msg2 =resolvElDCMsg("#{bundle['MSG.1106']}").toString();//Invalid amount range.
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            } 
    }

    public void setAmtToBinding(RichInputText amtToBinding) {
        this.amtToBinding = amtToBinding;
    }

    public RichInputText getAmtToBinding() {
        return amtToBinding;
    }

    public void setAmtFrmBinding(RichInputText amtFrmBinding) {
        this.amtFrmBinding = amtFrmBinding;
    }

    public RichInputText getAmtFrmBinding() {
        return amtFrmBinding;
    }
}

