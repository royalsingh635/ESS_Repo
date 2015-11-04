package slsdailycallapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.OperationBinding;

import oracle.binding.BindingContainer;

public class DailyCallSearchBean {
    /**
     * Detail mode:
     * A : Add Mode
     * V : View Mode
     */
    private StringBuffer detailMode = new StringBuffer("A");
    private StringBuffer docId = new StringBuffer("");
    public DailyCallSearchBean() {
    }

    public String addNewVisitACTION() {
        oracle.binding.OperationBinding ob = this.getBindings().getOperationBinding("getFyIdFromDocIdAndDate");
        ob.execute();
       Object obres= ob.getResult();
       if(obres!=null){
           if(obres.equals("Y")){
               detailMode = new StringBuffer("A");
               return "goToDtl";
           }
           
       }
       return "S";
    }

    public void setDetailMode(StringBuffer detailMode) {
        this.detailMode = detailMode;
    }

    public StringBuffer getDetailMode() {
        return detailMode;
    }

    public String viewVisitACTION() {
        
        detailMode = new StringBuffer("V");
        return "goToDtl";
    }

    public void viewACTION(ActionEvent actionEvent) {
        Object object = actionEvent.getComponent().getAttributes().get("docId");
        if(object != null){
            docId = new StringBuffer(object.toString());
        }
    }

    public void setDocId(StringBuffer docId) {
        this.docId = docId;
    }

    public StringBuffer getDocId() {
        System.out.println("DocId : "+docId);
        return docId;
    }

    public void searchACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("search").execute();
    }

    public void resetACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("reset").execute();
    }
    
    /**  method to get Bindings
    */
    public BindingContainer getBindings() {
       return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
}
