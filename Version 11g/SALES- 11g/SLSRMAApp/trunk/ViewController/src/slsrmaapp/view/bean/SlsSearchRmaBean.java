package slsrmaapp.view.bean;


import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SlsSearchRmaBean {


    private RichPanelFormLayout _searchFormBVar;


    public SlsSearchRmaBean() {
        System.out.println("in search bean construct");
        OperationBinding executeSearchRma = getBindings().getOperationBinding("executesearchVo");
          executeSearchRma.execute();
                
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void searchRma_ActionListener(ActionEvent actionEvent) {
        
        System.out.println("Enter in search listern");
        OperationBinding _searchRma=  getBindings().getOperationBinding("searchRma");
        _searchRma.execute();
        
    }

//    public void resetRma_ActionListener(ActionEvent actionEvent) {
//        System.out.println("Enter in reset listener");
//        OperationBinding _resetRma=  getBindings().getOperationBinding("restetRma");
//        _resetRma.execute();
//        
//    }
    
    
    
    public void resetRma_ActionListener(ActionEvent actionEvent) {
       
       UIComponent ui = (UIComponent)_searchFormBVar;
       
       AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
       
        List<UIComponent> childUi = ui.getChildren();
        
        for(UIComponent obj:childUi)
        {
            if(obj instanceof RichInputDate)
            {
                RichInputDate date = (RichInputDate)obj;
                 if(!date.isDisabled())
                 {
                   date.resetValue();
                   date.setValue("");
                 }
                ctx.addPartialTarget(date);
            }
            
            if(obj instanceof RichInputText){
                
                RichInputText inputtext=(RichInputText)obj;
                if(!inputtext.isDisabled()){
                    inputtext.resetValue();
                    inputtext.setValue("");
                }
                ctx.addPartialTarget(inputtext);
            }
            if(obj instanceof RichSelectOneChoice){
                RichSelectOneChoice selectonechoice=(RichSelectOneChoice)obj;
                if(!selectonechoice.isDisabled()){
                    selectonechoice.resetValue();
                    selectonechoice.setValue("");
                }
                ctx.addPartialTarget(selectonechoice);
                
            }
            if(obj instanceof RichInputListOfValues){
                RichInputListOfValues lov=(RichInputListOfValues)obj;
                if(!lov.isDisabled()){
                    lov.resetValue();
                    lov.setValue("");
                }
                ctx.addPartialTarget(lov);
                 
            }
          
        }
        
        System.out.println("Enter in reset listener");
             OperationBinding _resetRma=  getBindings().getOperationBinding("restetRma");
             _resetRma.execute();
           
        
    }


  
    public String createRma_Action() {
        
        return "Create";
    }

    public String view_Action() {
       /*  OperationBinding _resetRma=  getBindings().getOperationBinding("executeSearchedRma");
        _resetRma.execute();
         */
        return "View";
    }

    public void set_searchFormBVar(RichPanelFormLayout _searchFormBVar) {
        this._searchFormBVar = _searchFormBVar;
    }

    public RichPanelFormLayout get_searchFormBVar() {
        return _searchFormBVar;
    }


}
