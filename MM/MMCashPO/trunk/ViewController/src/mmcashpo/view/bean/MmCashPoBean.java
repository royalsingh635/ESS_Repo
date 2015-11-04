package mmcashpo.view.bean;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import mmcashpo.model.module.MmCashPOAMImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.SortCriterion;

public class MmCashPoBean {
    private RichTable searchTable;
    private RichPanelFormLayout searchForm;
    private RichSelectOneChoice itmIdBind;
    private RichInputText cpoIdBind;
    private RichInputDate fromDateBind;
    private RichInputDate todateBind;
    private RichInputListOfValues tranItemValueChange;


    public MmCashPoBean() {
    }
    private static final int RANGE_SIZE=10;
    private int toNoOfRows;
    private int pageNumber;
         private int maxPages;
    private static String visb="true";
    public void lastSet(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String goLastpage() {
        JUCtrlRangeBinding searchView = this.getSearchView();
        long lastPageNum = Math.max(1,(searchView.getIteratorBinding().getEstimatedRowCount()-1)/RANGE_SIZE+1);
        if(lastPageNum <= 1) return null;
        int prePage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
        searchView.getIteratorBinding().getNavigatableRowIterator().scrollRange((int)(RANGE_SIZE*(lastPageNum-prePage)));
        return null;
    }
    
    private JUCtrlRangeBinding getSearchView(){
         BindingContext bindingCtx = BindingContext.getCurrent();
         BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
         return (JUCtrlRangeBinding)bindings.getControlBinding("CpoSearch1");
       }

    public void setToNoOfRows(int toNoOfRows) {
        this.toNoOfRows = toNoOfRows;
    }

    public int getToNoOfRows() {
        int y=getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart();
        if(y==-1){
            y=0;
        }
        int x= y + (getSearchView().getIteratorBinding().getAllRowsInRange().length);
        return x;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        int val=0;
               try{
                val=getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
               }catch(NullPointerException np){
                   val=1;
               }
        return val;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getMaxPages() {
        
        //Page #{PurOrderSearchBean.pageNumber} of #{(bindings.PoSearch.estimatedRowCount/ bindings.PoSearch.rangeSize)}
            // Long x= ((getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)+1);
            // System.out.println("MAX PAGES:"+Math.floor(((Long)(getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)).doubleValue()));
            /*   Double u= Math.floor(((Long)(getSearchView().getIteratorBinding().getEstimatedRowCount()/RANGE_SIZE)).doubleValue());
              return maxPages= u.intValue(); */
                    /*  Double x= new Double (getSearchView().getIteratorBinding().getEstimatedRowCount());
                     Double y =(Double)x/RANGE_SIZE;
                     Double u= Math.ceil(y.doubleValue());
                     return maxPages= u.intValue(); */
                    Double x= new Double (getSearchView().getIteratorBinding().getEstimatedRowCount());
                    if(getSearchView().getIteratorBinding().getEstimatedRowCount()!=0){
                    Double y =(Double)x/RANGE_SIZE;
                    Double u= Math.ceil(y.doubleValue());
                        return maxPages= u.intValue();
                    }else{
                        return 1;
                    }
       
    }
    
    public boolean isLastEnabled(){
              JUCtrlRangeBinding searchView = this.getSearchView();
              long lastPageNum = Math.max(1,(searchView.getIteratorBinding().getEstimatedRowCount()-1)/RANGE_SIZE+1);
              long currentPage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
              return currentPage<lastPageNum;
            }
    
    public String sortByDate() {
        //attrib name,isAscending --params for sortCriterion
       List sortList=new ArrayList();
       SortCriterion sc2=new SortCriterion("UsrIdCreateDt",false);
       sortList.add(sc2);
       
       
       RichTable ct=getSearchTable();
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

    public void setVisb(String visb) {
        this.visb = visb;
    }

    public String getVisb() {
        return visb;
    }
    
    public void searchPoVisAction(ActionEvent actionEvent) {
        if(visb.equalsIgnoreCase("true")){
            searchForm.setVisible(false);
            visb="false";
        }else if(visb.equalsIgnoreCase("false")){
            searchForm.setVisible(true);
            visb="true";
        }
        
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }
    
    public void rowSelected(SelectionEvent se) {
             JUCtrlRangeBinding searchView = getSearchView();
             searchView.getIteratorBinding().setCurrentRowIndexInRange((Integer)se.getAddedSet().toArray()[0]);
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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
    public void resetButton(ActionEvent actionEvent) {
      itmIdBind.setValue(null);
      cpoIdBind.setValue(null);
      fromDateBind.setValue(null);
      todateBind.setValue(null);
      
      AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cpoIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromDateBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(todateBind);
        
    MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
    ViewObjectImpl v = am.getCpoSearch1();

        VariableValueManager vm = v.ensureVariableManager();
        vm.setVariableValue("BindItemId", "");
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
               resetValueInputItems(fctx, searchForm);
        am.getDBTransaction().rollback();
           v.executeQuery();
       
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
    public void setItmIdBind(RichSelectOneChoice itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichSelectOneChoice getItmIdBind() {
        return itmIdBind;
    }

    public void setCpoIdBind(RichInputText cpoIdBind) {
        this.cpoIdBind = cpoIdBind;
    }

    public RichInputText getCpoIdBind() {
        return cpoIdBind;
    }

    public void setFromDateBind(RichInputDate fromDateBind) {
        this.fromDateBind = fromDateBind;
    }

    public RichInputDate getFromDateBind() {
        return fromDateBind;
    }

    public void setTodateBind(RichInputDate todateBind) {
        this.todateBind = todateBind;
    }

    public RichInputDate getTodateBind() {
        return todateBind;
    }

    public String resetButton() {
        itmIdBind.setValue(null);
        cpoIdBind.setValue(null);
        fromDateBind.setValue(null);
        todateBind.setValue(null);
        return "ghumake";
    }

    public void setTranItemValueChange(RichInputListOfValues tranItemValueChange) {
        this.tranItemValueChange = tranItemValueChange;
    }

    public RichInputListOfValues getTranItemValueChange() {
        return tranItemValueChange;
    }

     public void tranItmLovValueChange(ValueChangeEvent valueChangeEvent) {
        String itmdesc = valueChangeEvent.getNewValue().toString();
        MmCashPOAMImpl am = (MmCashPOAMImpl)resolvElDC("MmCashPOAMDataControl");
        ViewObjectImpl lovitm = am.getLovItmId1();
   Row [] rws =  lovitm.getFilteredRows("ItmDesc", itmdesc);
   if(rws.length>0){
  String itmId = rws[0].getAttribute("ItmId").toString();
  
              itmIdBind.setValue(itmId);
   }else{
       itmIdBind.setValue(null);
   }
              AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind);
       
    } 
     
    

}
