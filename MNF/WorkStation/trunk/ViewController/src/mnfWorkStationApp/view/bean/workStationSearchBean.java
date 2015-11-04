package mnfWorkStationApp.view.bean;

import java.sql.SQLException;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import mnfWorkStationApp.view.Utils.ADFUtils;

import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

public class workStationSearchBean {
    OperationBinding ob = null;
    String wsActcFlag = "B";
    private RichInputDate wsFromDateBind;
    private RichInputDate wsToDateBind;
    Object wsFrmDt ;
    Object wsToDt ;
    Object wsPurchaseDt;
    private RichInputDate wsPurchaseDateBind;
    private RichSelectOneChoice wsPersonInchargeBind;
   
    
    private RichInputListOfValues wcenterNmBind;
    private RichInputListOfValues wstationNameBind;

    public workStationSearchBean() {
    }


    public void SearchBtnActionListener(ActionEvent actionEvent) {
        System.out.println("Inside of searchaction listner !!");
        wsFrmDt = getWsFromDateBind().getValue();
        wsToDt = getWsToDateBind().getValue();
        wsPurchaseDt = getWsPurchaseDateBind().getValue();
        System.out.println(this.wsPersonInchargeBind.getValue());
        
        Timestamp dtfrm = (Timestamp)getWsFromDateBind().getValue();
        Timestamp todt = (Timestamp)getWsToDateBind().getValue();
        if(dtfrm != null && todt != null)
        {
            Date date1, date2;
            try {
                date1 = dtfrm.dateValue();
                date2 = todt.dateValue();
                if (date1.compareTo(date2) == 1)
                {
                    FacesMessage message = new FacesMessage("To Date Can't Less than of From Date!");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(this.getWsFromDateBind().getClientId(), message);

                }
            } catch (SQLException e) {
            }
          
        }
        
        ob = ADFUtils.findOperation("setWsSearchParams");
        ob.getParamsMap().put("workStationId",this.wstationNameBind.getValue()== null ? null : this.wstationNameBind.getValue().toString());
        ob.getParamsMap().put("workActv" ,this.wsActcFlag == null ? null : this.wsActcFlag); 
        ob.getParamsMap().put("workCenterNme", this.wcenterNmBind.getValue() == null ? null : this.wcenterNmBind.getValue().toString());
         ob.execute();
    }

    public void ResetBtnActionListener(ActionEvent actionEvent) {
        
         ob = ADFUtils.findOperation("resetBtnAction");
        ob.execute();
    }

    public void statusVCL(ValueChangeEvent vchngeEvt) {
       if(vchngeEvt.getNewValue().equals("Y"))
           wsActcFlag = "Y";
       else if(vchngeEvt.getNewValue().equals("N"))
            wsActcFlag = "N";
       else 
        wsActcFlag = "B";
    }
    
    public void setWsActcFlag(String wsActcFlag) {
        this.wsActcFlag = wsActcFlag;
    }

    public String getWsActcFlag() {
        return wsActcFlag;
    }

    public void setWsFromDateBind(RichInputDate wsFromDateBind) {
        this.wsFromDateBind = wsFromDateBind;
    }

    public RichInputDate getWsFromDateBind() {
        return wsFromDateBind;
    }

    public void setWsToDateBind(RichInputDate wsToDateBind) {
        this.wsToDateBind = wsToDateBind;
    }

    public RichInputDate getWsToDateBind() {
        return wsToDateBind;
    }

    public void setWsPurchaseDateBind(RichInputDate wsPurchaseDateBind) {
        this.wsPurchaseDateBind = wsPurchaseDateBind;
    }

    public RichInputDate getWsPurchaseDateBind() {
        return wsPurchaseDateBind;
    }

    public void setWsPersonInchargeBind(RichSelectOneChoice wsPersonInchargeBind) {
        this.wsPersonInchargeBind = wsPersonInchargeBind;
    }

    public RichSelectOneChoice getWsPersonInchargeBind() {
        return wsPersonInchargeBind;
    }

    public void setWcenterNmBind(RichInputListOfValues wcenterNmBind) {
        this.wcenterNmBind = wcenterNmBind;
    }

    public RichInputListOfValues getWcenterNmBind() {
        return wcenterNmBind;
    }

    public void setWstationNameBind(RichInputListOfValues wstationNameBind) {
        this.wstationNameBind = wstationNameBind;
    }

    public RichInputListOfValues getWstationNameBind() {
        return wstationNameBind;
    }
}
