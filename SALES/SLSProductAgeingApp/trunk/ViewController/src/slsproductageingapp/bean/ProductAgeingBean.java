package slsproductageingapp.bean;

import java.sql.Date;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;

import oracle.adf.view.faces.bi.event.TimeSelectorEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class ProductAgeingBean {
    
    private Date startDate = Date.valueOf("2013-03-12");
    private Date endDate = Date.valueOf("2014-03-02");
    
    public ProductAgeingBean() {
    }

    public void productSelectionList(SelectionEvent selectionEvent) {
        invokeEL("#{bindings.ProductAgeingVO.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},new Object[] { selectionEvent });
        this.getBindings().getOperationBinding("setAttributesForTempVo").execute();
    }
    
    /**
     * Method to getBindings
     * @return
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
     /**
     * Programmatic evaluation of EL.
     *
     * @param el EL to evaluate
     * @return Result of the evaluation
     */
    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }
    /**
    * Programmatic invocation of a method that an EL evaluates to.
    * @param el EL of the method to invoke
    * @param paramTypes Array of Class defining the types of the parameters
    * @param params Array of Object defining the values of the parametrs
    * @return Object that the method returns
    */
    public static Object invokeEL(String el, Class[] paramTypes,
    Object[] params) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    MethodExpression exp =
    expressionFactory.createMethodExpression(elContext, el,
    Object.class, paramTypes);

    return exp.invoke(elContext, params);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        //return Date.valueOf("2013-02-06");
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        //return Date.valueOf("2014-02-06");
        return endDate;
    }

    public void TimeSelectorLIST(TimeSelectorEvent timeSelectorEvent) {
        Date sdate = new Date(timeSelectorEvent.getStartDate().getTime());
        Date edate = new Date(timeSelectorEvent.getEndDate().getTime());

        // System.out.println("sdate--->" + sdate + " edate---->" + edate);
        try {
            startDate = (sdate);
            endDate = (edate);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        System.out.print("Start date : "+startDate);
        System.out.println(" End date : "+endDate);
        OperationBinding binding = this.getBindings().getOperationBinding("filterProductAgeingVoOnDate");
        binding.getParamsMap().put("fromDt", new Timestamp(startDate));
        binding.getParamsMap().put("toDt", new Timestamp(endDate));
        binding.execute();

    }
}
