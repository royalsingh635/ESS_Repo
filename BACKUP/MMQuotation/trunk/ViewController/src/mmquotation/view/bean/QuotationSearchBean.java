package mmquotation.view.bean;

import oracle.adf.view.rich.component.rich.data.RichTable;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mmquotation.model.service.QuotationAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.SortCriterion;

public class QuotationSearchBean implements Serializable{


    private RichTable searchTable;
    private static final int RANGE_SIZE=10;
    private int pageNumber;
    private int maxPages;
    private int toNoOfRows;
    private String visb="true";
    private RichPanelFormLayout searchForm;
    private RichPanelGroupLayout searchPanel;
    private RichPanelFormLayout searchFormInBox;

    public QuotationSearchBean() {
    }
    
    public String createQuot() {
        QuotationBean quot=new QuotationBean();
        quot.setForm_readOnly(false);
        //quot.callToSearchBean();
        return "Create";
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
    public String sortByDate() {
           //attrib name,isAscending --params for sortCriterion
          List sortList=new ArrayList();
          SortCriterion sc2=new SortCriterion("QuotDt",false);
          sortList.add(sc2);
          
          
          RichTable ct=getSearchTable();
          ct.setSortCriteria(sortList);
          AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
           
           return null;
       }
    public void rowSelected(SelectionEvent se) {
                 JUCtrlRangeBinding searchView = getSearchView();
                 System.out.println("Row selected");
                 searchView.getIteratorBinding().setCurrentRowIndexInRange((Integer)se.getAddedSet().toArray()[0]);
                 
        }
       
        public String goLastPage(){
              JUCtrlRangeBinding searchView = this.getSearchView();
              long lastPageNum = Math.max(1,(searchView.getIteratorBinding().getEstimatedRowCount()-1)/RANGE_SIZE+1);
              if(lastPageNum <= 1) return null;
              int prePage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
              searchView.getIteratorBinding().getNavigatableRowIterator().scrollRange((int)(RANGE_SIZE*(lastPageNum-prePage)));
              return null;
            }
        
        public boolean isLastEnabled(){
              JUCtrlRangeBinding searchView = this.getSearchView();
              long lastPageNum = Math.max(1,(searchView.getIteratorBinding().getEstimatedRowCount()-1)/RANGE_SIZE+1);
              long currentPage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart()/RANGE_SIZE+1;
              return currentPage<lastPageNum;
            }
        
         private JUCtrlRangeBinding getSearchView(){
              BindingContext bindingCtx = BindingContext.getCurrent();
              BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
              return (JUCtrlRangeBinding)bindings.getControlBinding("QuotSearch1");
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
            return pageNumber = val;
        }

        public void setMaxPages(int maxPages) {
            this.maxPages = maxPages;
        }

        public int getMaxPages() {
           Double x= new Double (getSearchView().getIteratorBinding().getEstimatedRowCount());
           if(getSearchView().getIteratorBinding().getEstimatedRowCount()!=0){
           Double y =(Double)x/RANGE_SIZE;
            Double u= Math.ceil(y.doubleValue());
            return maxPages= u.intValue();
           }else{
               return 1;
           }
            
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
        /* oracle.sql.DATE n= oracle.jbo.domain.Date.getCurrentDate();
         */
            return toNoOfRows=x;
        }

    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
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
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void resetAction(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchFormInBox);
        QuotationAMImpl am = (QuotationAMImpl)resolvElDC("QuotationAMDataControl");
        ViewObject itemSearch = am.getQuotSearch1();
        itemSearch.setWhereClause(" ");
        itemSearch.executeQuery();
       /*  BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();  */
        
       
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
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }


    public String goToViewPage() {
        QuotationBean quot=new QuotationBean();
        //quot.setForm_readOnly(false);
        quot.callFromViewSearchBean();
        return "goToView";
    }
    
    public String searchAction() {
        // Add event code here...
        OperationBinding op=  getBindings().getOperationBinding("searchAction");
        op.execute();
         return null;
    }

    public void setSearchFormInBox(RichPanelFormLayout searchFormInBox) {
        this.searchFormInBox = searchFormInBox;
    }

    public RichPanelFormLayout getSearchFormInBox() {
        return searchFormInBox;
    }

    public void toDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }
}
