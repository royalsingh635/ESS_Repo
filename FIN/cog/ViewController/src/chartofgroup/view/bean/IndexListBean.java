package chartofgroup.view.bean;


import chartofgroup.model.entity.help.IndexCharacterObject;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


public class IndexListBean {
    List<IndexCharacterObject> list = null;
    
    public IndexListBean() {
        super();
    }

    public void setList(List<IndexCharacterObject> list) {
        this.list = list;
    }

    public List<IndexCharacterObject> getList() {

        if (list == null || list.size() == 0) {
            BindingContext bctx = BindingContext.getCurrent();
            BindingContainer bindings = bctx.getCurrentBindingsEntry();

            OperationBinding createIndexList =
                (OperationBinding)bindings.get("getCharacterIndexList");
            list = (List<IndexCharacterObject>)createIndexList.execute();

            if (createIndexList.getErrors().size() != 0) {
                //create empty list in case of error
                list = new ArrayList<IndexCharacterObject>();
            }
        }
        return list;
    }
    }
