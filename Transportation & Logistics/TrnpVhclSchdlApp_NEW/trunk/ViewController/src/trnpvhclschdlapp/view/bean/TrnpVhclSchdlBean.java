package trnpvhclschdlapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.binding.OperationBinding;

import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Timestamp;

public class TrnpVhclSchdlBean {
    private RichInputListOfValues bookingNoParamBinding;
    String mode = "V";
    private RichInputListOfValues routeNmBinding;
    private RichInputDate reqStrtDtBind;
    private RichInputDate reqEndDtBind;
    private RichInputDate jrnyStrtDtBind;
    private RichInputDate jrnyEndDtBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public TrnpVhclSchdlBean() {
    }

    public String searchOnParametersACTION() {
        if (mode.equals("V") || mode.equals("E")) {
            if (bookingNoParamBinding.getValue() == null || bookingNoParamBinding.getValue().toString().length() == 0) {
                ADFModelUtils.showFacesMessage("Please select Booking No.", " ", FacesMessage.SEVERITY_WARN,
                                               bookingNoParamBinding.getClientId());
                return null;
            }
        }
        OperationBinding searchOp = ADFBeanUtils.getOperationBinding("searchRecordsFrmParams");
        searchOp.getParamsMap().put("mode", mode);
        searchOp.execute();
        return null;
    }

    public String resetSearchParamsACTION() {
        OperationBinding resetOp = ADFBeanUtils.getOperationBinding("resetRecordsFrmParams");
        resetOp.execute();
        return null;
    }

    public void selectVehicleCheckBoxVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Value=" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null &&
            (valueChangeEvent.getNewValue().toString().equals("true") ||
             valueChangeEvent.getNewValue().toString().equals("Y"))) {
            OperationBinding unchkOp = ADFBeanUtils.getOperationBinding("UncheckOtherVehicle");
            unchkOp.execute();
        }
    }

    public String bookSchduleACTION() {

        return null;
    }

    public String esitExistingScheduleACTION() {

        return null;
    }

    public void setBookingNoParamBinding(RichInputListOfValues bookingNoParamBinding) {
        this.bookingNoParamBinding = bookingNoParamBinding;
    }

    public RichInputListOfValues getBookingNoParamBinding() {
        return bookingNoParamBinding;
    }

    public String addScheduleACTION() {
        if (routeNmBinding.getValue() == null || routeNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Route Name.", " ", FacesMessage.SEVERITY_WARN,
                                           routeNmBinding.getClientId());
            return null;
        }

            OperationBinding searchOp = ADFBeanUtils.getOperationBinding("searchRecordsFrmParams");
            searchOp.getParamsMap().put("mode", "A");
            searchOp.execute();
            
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();  
            DCIteratorBinding lrDcIterBind = (DCIteratorBinding) bindings.get("LoadingRequestVw1Iterator"); 
            DCIteratorBinding vhclDcIterBind = (DCIteratorBinding) bindings.get("TrnpVhclPrfVw1Iterator");
            
            RowSetIterator lrRowSet = lrDcIterBind.getRowSetIterator();  
            RowSetIterator vhclRowSet = vhclDcIterBind.getRowSetIterator();  
            
            if(lrRowSet.getAllRowsInRange().length > 0 && vhclRowSet.getAllRowsInRange().length > 0){
                mode = "A";
            }
            else
            {
                ADFModelUtils.showFacesMessage("No data found !! ", "No Loading Request(s) or Vehicle(s) available for selected period !! ", FacesMessage.SEVERITY_ERROR, null);    
            }
        
        return null;
    }

    public String editScheduleACTION() {
        if (routeNmBinding.getValue() == null || routeNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Route Name.", " ", FacesMessage.SEVERITY_WARN,
                                           routeNmBinding.getClientId());
            return null;
        }
        if (bookingNoParamBinding.getValue() == null || bookingNoParamBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Booking No.", " ", FacesMessage.SEVERITY_WARN,
                                           bookingNoParamBinding.getClientId());
            return null;
        }
        mode = "E";
        OperationBinding searchOp = ADFBeanUtils.getOperationBinding("searchRecordsFrmParams");
        searchOp.getParamsMap().put("mode", mode);
        searchOp.execute();
        return null;
    }

    public String saveScheduleACTION() {
        OperationBinding bookOp = ADFBeanUtils.getOperationBinding("bookVehicleSchdule");
        bookOp.getParamsMap().put("mode", mode);
        bookOp.execute();
        String schdlNo = null;
        if (bookOp.getResult() != null && bookOp.getResult().toString().length() > 0) {
            if(bookOp.getResult().toString().equalsIgnoreCase("NoRowSelected"))
            {
                ADFModelUtils.showFacesMessage("No Rows Selected !!", "Please select atleast 1 Loading Request & Vehicle !!", FacesMessage.SEVERITY_INFO, null);
                return null;
            }
            else
                schdlNo = "Schedule No. is:  " + bookOp.getResult().toString();
        }
        mode = "V";
        ADFModelUtils.showFacesMessage("Schedule Saved Successfully.", schdlNo, FacesMessage.SEVERITY_INFO, null);
        OperationBinding searchOp = ADFBeanUtils.getOperationBinding("searchRecordsFrmParams");
        searchOp.getParamsMap().put("mode", mode);
        searchOp.execute();
        return null;
    }

    public String cancelScheduleACTION() {
        mode = "V";
        OperationBinding searchOp = ADFBeanUtils.getOperationBinding("searchRecordsFrmParams");
        searchOp.getParamsMap().put("mode", mode);
        searchOp.execute();
        return null;
    }

    public void setRouteNmBinding(RichInputListOfValues routeNmBinding) {
        this.routeNmBinding = routeNmBinding;
    }

    public RichInputListOfValues getRouteNmBinding() {
        return routeNmBinding;
    }

    public void setReqStrtDtBind(RichInputDate reqStrtDtBind) {
        this.reqStrtDtBind = reqStrtDtBind;
    }

    public RichInputDate getReqStrtDtBind() {
        return reqStrtDtBind;
    }

    public void setReqEndDtBind(RichInputDate reqEndDtBind) {
        this.reqEndDtBind = reqEndDtBind;
    }

    public RichInputDate getReqEndDtBind() {
        return reqEndDtBind;
    }

    public void setJrnyStrtDtBind(RichInputDate jrnyStrtDtBind) {
        this.jrnyStrtDtBind = jrnyStrtDtBind;
    }

    public RichInputDate getJrnyStrtDtBind() {
        return jrnyStrtDtBind;
    }

    public void setJrnyEndDtBind(RichInputDate jrnyEndDtBind) {
        this.jrnyEndDtBind = jrnyEndDtBind;
    }

    public RichInputDate getJrnyEndDtBind() {
        return jrnyEndDtBind;
    }

    public void jrnyEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if (object != null) {
                   if (jrnyStrtDtBind.getValue() != null) {
                       Timestamp t1 = (Timestamp) jrnyStrtDtBind.getValue();
                       Timestamp t2 = (Timestamp) object;

                       Date d1 = new Date(System.currentTimeMillis());
                       Date d2 = new Date(System.currentTimeMillis());
                       try {
                           d1 = t1.dateValue();
                           d2 = t2.dateValue();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }

                       //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                       if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Request End Date should be equal or after Request Start Date.",
                                                                          null));
                       }
                   }
               }

    }

    public void reqEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if (object != null) {
                   if (reqStrtDtBind.getValue() != null) {
                       Timestamp t1 = (Timestamp) reqStrtDtBind.getValue();
                       Timestamp t2 = (Timestamp) object;

                       Date d1 = new Date(System.currentTimeMillis());
                       Date d2 = new Date(System.currentTimeMillis());
                       try {
                           d1 = t1.dateValue();
                           d2 = t2.dateValue();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }

                       //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                       if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Journey End Date should be equal or after Journey Start Date.",
                                                                         null));
                       }
                   }
         }
    }

}
