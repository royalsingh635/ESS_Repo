package slssalesordapp.view.bean;

import java.sql.Date;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.model.NumberRange;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;


/**
 * SalesOrder (Search) Managed Bean.
 * Contains all the business logic for Search TaskFlow
 * @author Ashish Kumar
 * Dated -28/08/2013
 * */
public class SalesOrdSearchBean {
    private RichInputText eoIdBind;
    private RichInputDate fromDtBind;
    private RichInputDate toDtBind;
    private RichSelectOneChoice ordTypBind;
    private RichInputText usrIdBind;
    private RichSelectOneChoice ordStatusBind;
    private RichInputText fromAmtBind;
    private RichInputText toAmtBind;
    private RichInputRangeSlider itemsRangeBind = new RichInputRangeSlider();
    
    
    
    private RichInputText docIdBind;
    private RichSelectOneChoice orderModeBind;


    public SalesOrdSearchBean() {
        NumberRange sliderValue = new NumberRange(0,50);
        itemsRangeBind.setValue((Object)sliderValue);
    }

    public void setRangeSliderToDefault(){
        NumberRange sliderValue = new NumberRange(0,50);
        itemsRangeBind.setValue((Object)sliderValue);
    }
    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    public void searchAction(ActionEvent actionEvent) {
        OperationBinding searchVo = executeOperation("searchVo");
        searchVo.execute();
        Timestamp dt =  (Timestamp)toDtBind.getValue();
        if(toDtBind.getValue() != null){
            dt = (Timestamp)toDtBind.getValue();
            Date date;
            try {
                date = dt.dateValue();
                System.out.println("Date : "+date);
                System.out.println("Date is :"+date.getDate());
                date.setDate(date.getDate()+1);
                System.out.println("Date : "+date);
                dt = new Timestamp(date);
            } catch (SQLException e) {
            }    
        }
               
        if (searchVo.getResult() != null) {
            ViewObject vo = (ViewObject)searchVo.getResult();
            vo.setNamedWhereClauseParam("FromDtBind", fromDtBind.getValue());
            vo.setNamedWhereClauseParam("ToDtBind", dt);
            vo.setNamedWhereClauseParam("EoIdBind", eoIdBind.getValue());
            vo.setNamedWhereClauseParam("soTypeBind", ordTypBind.getValue());
            vo.setNamedWhereClauseParam("usrIdBind", usrIdBind.getValue());
            vo.setNamedWhereClauseParam("ordStatusBind", orderModeBind.getValue());
            vo.setNamedWhereClauseParam("amtFromBind", fromAmtBind.getValue());
            vo.setNamedWhereClauseParam("amtToBind", toAmtBind.getValue());

            NumberRange sliderValue = (NumberRange)getItemsRangeBind().getValue();
            vo.setNamedWhereClauseParam("minItemBind", sliderValue.getMinimum());
            vo.setNamedWhereClauseParam("maxItemBind", sliderValue.getMaximum());
            vo.setNamedWhereClauseParam("dispDocIdBind", docIdBind.getValue());
            
            System.out.println("So Mode : "+ orderModeBind.getValue());
            vo.setNamedWhereClauseParam("SoModeBind", ordStatusBind.getValue());
            
            vo.executeQuery();
            setRangeSliderToDefault();

        }
    }

    public void resetAction(ActionEvent actionEvent) {
        OperationBinding searchVo = executeOperation("searchVo");
        searchVo.execute();
        if (searchVo.getResult() != null) {
            ViewObject vo = (ViewObject)searchVo.getResult();
            vo.setNamedWhereClauseParam("FromDtBind", null);
            vo.setNamedWhereClauseParam("ToDtBind", null);
            vo.setNamedWhereClauseParam("EoIdBind", -1);
            vo.setNamedWhereClauseParam("soTypeBind", null);
            vo.setNamedWhereClauseParam("usrIdBind", null);
            vo.setNamedWhereClauseParam("ordStatusBind", null);
            vo.setNamedWhereClauseParam("amtFromBind", null);
            vo.setNamedWhereClauseParam("amtToBind", null);
            vo.setNamedWhereClauseParam("minItemBind", null);
            vo.setNamedWhereClauseParam("maxItemBind", null);
            vo.setNamedWhereClauseParam("dispDocIdBind", null);
            vo.setNamedWhereClauseParam("SoModeBind",null);
            
            vo.executeQuery();
            
            OperationBinding reset = executeOperation("resetAction");
            reset.execute();
            setRangeSliderToDefault();

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

    public void setEoIdBind(RichInputText eoIdBind) {
        this.eoIdBind = eoIdBind;
    }

    public RichInputText getEoIdBind() {
        return eoIdBind;
    }

    public void setFromDtBind(RichInputDate fromDtBind) {
        this.fromDtBind = fromDtBind;
    }

    public RichInputDate getFromDtBind() {
        return fromDtBind;
    }

    public void setToDtBind(RichInputDate toDtBind) {
        this.toDtBind = toDtBind;
    }

    public RichInputDate getToDtBind() {
        return toDtBind;
    }

    public void setOrdTypBind(RichSelectOneChoice ordTypBind) {
        this.ordTypBind = ordTypBind;
    }

    public RichSelectOneChoice getOrdTypBind() {
        return ordTypBind;
    }

    public void setUsrIdBind(RichInputText usrIdBind) {
        this.usrIdBind = usrIdBind;
    }

    public RichInputText getUsrIdBind() {
        return usrIdBind;
    }

    public void setOrdStatusBind(RichSelectOneChoice ordStatusBind) {
        this.ordStatusBind = ordStatusBind;
    }

    public RichSelectOneChoice getOrdStatusBind() {
        return ordStatusBind;
    }

    public void setFromAmtBind(RichInputText fromAmtBind) {
        this.fromAmtBind = fromAmtBind;
    }

    public RichInputText getFromAmtBind() {
        return fromAmtBind;
    }

    public void setToAmtBind(RichInputText toAmtBind) {
        this.toAmtBind = toAmtBind;
    }

    public RichInputText getToAmtBind() {
        return toAmtBind;
    }

    public void setItemsRangeBind(RichInputRangeSlider itemsRangeBind) {
        this.itemsRangeBind = itemsRangeBind;
    }

    public RichInputRangeSlider getItemsRangeBind() {
        return itemsRangeBind;
    }

    public void setDocIdBind(RichInputText docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichInputText getDocIdBind() {
        return docIdBind;
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void amountFromValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amtFrm = (Number)object;
            Number amtTo = new Number(0);
            if (amtFrm.compareTo(0) == -1) {
               // throw new ValidatorException(new FacesMessage("Invalid Amount", "Can not be negative"));
              String m= resolvElDCMsg("#{bundle['MSG.819']}").toString();
               throw new ValidatorException(new FacesMessage(m));
            }
            if (toAmtBind.getValue() != null) {
                amtTo = (Number)toAmtBind.getValue();
                if (amtFrm.compareTo(amtTo) == 1) {
                   // throw new ValidatorException(new FacesMessage("Invalid Amount Range",
                                                                 // "Can not be greater than higher range"));
                    throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.820']}").toString(),
                                                                 resolvElDCMsg("#{bundle['MSG.821']}").toString()));

                }
            }
        }
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void amountToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amtFrm = new Number(0);
            Number amtTo = (Number)object;
            if (amtTo.compareTo(0) == -1) {
                //throw new ValidatorException(new FacesMessage("Invalid Amount", "Can not be negative"));
                throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.819']}").toString()));
            }
            if (fromAmtBind.getValue() != null) {
                amtFrm = (Number)fromAmtBind.getValue();
                if (amtFrm.compareTo(amtTo) == 1) {
                    /*throw new ValidatorException(new FacesMessage("Invalid Amount Range",
                                                                  "Can not be less than lower range"));*/
             throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.820']}").toString(),
                                                           resolvElDCMsg("#{bundle['MSG.822']}").toString()));                                                                                     
                }
            }
        }
    }

    public void setOrderModeBind(RichSelectOneChoice orderModeBind) {
        this.orderModeBind = orderModeBind;
    }

    public RichSelectOneChoice getOrderModeBind() {
        return orderModeBind;
    }
}
