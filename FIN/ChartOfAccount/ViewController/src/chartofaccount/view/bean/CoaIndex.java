package chartofaccount.view.bean;

import chartofaccount.model.helper.IndexCharacter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class CoaIndex {
    
    
    public CoaIndex() {
        super();
 
    }
    List<IndexCharacter> list = null;
    
    

    public void setList(List<IndexCharacter> list) {
        this.list = list;
    }

    public List<IndexCharacter> getList() {

        if (list == null || list.size() == 0) {
            BindingContext bctx = BindingContext.getCurrent();
            BindingContainer bindings = bctx.getCurrentBindingsEntry();

            OperationBinding createIndexList =
                (OperationBinding)bindings.get("getCharacterIndexList");
            list = (List<IndexCharacter>)createIndexList.execute();

            if (createIndexList.getErrors().size() != 0) {
                //create empty list in case of error
                list = new ArrayList<IndexCharacter>();
            }
        }
        return list;
    }

  
}
