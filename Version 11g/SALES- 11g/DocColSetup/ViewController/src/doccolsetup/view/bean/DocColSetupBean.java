package doccolsetup.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class DocColSetupBean {
    private StringBuffer mode = new StringBuffer("V");
    public DocColSetupBean() {
    }

    public void saveACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("Commit");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            FacesMessage msg = new FacesMessage("Records Saved Sucessfully !");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            mode = new StringBuffer("V");
        }else{
            FacesMessage msg = new FacesMessage("Records not saved ! Please try again !");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void cancelACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("Rollback");
        binding.execute();
        mode = new StringBuffer("V");
        
    }
    
    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("search").execute();
    }

    public void resetACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("reset").execute();
    }

    public void editACTION(ActionEvent actionEvent) {
       mode =  new StringBuffer("E");
    }

    public StringBuffer getMode() {
        return mode;
    }
}
