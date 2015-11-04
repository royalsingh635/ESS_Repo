package naturalaccounts.view.bean;



import java.util.ArrayList;
import java.util.List;

import naturalaccounts.model.helper.IndexCharacter;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class NaIndex {
    private Boolean isRenderPage;
    private static Integer count;
    
    /**
     *
     **/
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    static {
        count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
        //count=1;
        System.out.println(count);
        System.out.println("static block");
    }

   
    

    public void setIsRenderPage(Boolean isRenderPage) {
        this.isRenderPage = isRenderPage;
    }

    public Boolean getIsRenderPage() {
        if (count == 1) {
            return false;
        } else {
            return true;
        }
    }
    
    
    
    public NaIndex() {
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
