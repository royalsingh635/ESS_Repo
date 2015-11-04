package daybookandvoucherprintapp.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.binding.BindingContainer;

public class DayBookNPrintVoucherBean {
    private RichGoLink printVoucher_Link;
    private RichGoLink dayBook_Link;
    private RichGoLink chequePrint_Link;
    private int count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichSelectOneChoice bookType;
    private RichSelectBooleanCheckbox dayBooksChkBox;

    public DayBookNPrintVoucherBean() {
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setPrintVoucher_Link(RichGoLink printVoucher_Link) {
        this.printVoucher_Link = printVoucher_Link;
    }

    public RichGoLink getPrintVoucher_Link() {
        return printVoucher_Link;
    }

    public void setDayBook_Link(RichGoLink dayBook_Link) {
        this.dayBook_Link = dayBook_Link;
    }

    public RichGoLink getDayBook_Link() {
        return dayBook_Link;
    }

    public void setChequePrint_Link(RichGoLink chequePrint_Link) {
        this.chequePrint_Link = chequePrint_Link;
    }

    public RichGoLink getChequePrint_Link() {
        return chequePrint_Link;
    }

    public void printVoucher_ValuechangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        System.out.println("printr voucher check box value------>" + value);
        if (value)
            printVoucher_Link.setVisible(true);
        else
            printVoucher_Link.setVisible(false);
    }

    public void dayBook_ValuechangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        System.out.println("dayBook_ValuechangeListener check box value------>" + value);
        if (value)
            dayBook_Link.setVisible(true);
        else
            dayBook_Link.setVisible(false);
    }

    public void chequePrint_ValuechangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        System.out.println("chequePrint_ValuechangeListener check box value------>" + value);
        if (value)
            chequePrint_Link.setVisible(true);
        else
            chequePrint_Link.setVisible(false);
    }

    public void generateReport_ActionListener(ActionEvent actionEvent) {
        if (dayBooksChkBox.getValue() != null) {
            if (bookType.getValue() == null && dayBooksChkBox.getValue().equals("true")) {

                FacesMessage Message = new FacesMessage("Please select Book Type");
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, Message);
                
            }
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setBookType(RichSelectOneChoice bookType) {
        this.bookType = bookType;
    }

    public RichSelectOneChoice getBookType() {
        return bookType;
    }

    public void setDayBooksChkBox(RichSelectBooleanCheckbox dayBooksChkBox) {
        this.dayBooksChkBox = dayBooksChkBox;
    }

    public RichSelectBooleanCheckbox getDayBooksChkBox() {
        return dayBooksChkBox;
    }
}
