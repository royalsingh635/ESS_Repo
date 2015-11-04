package glApp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;


public class GlSearchBean {
    private Integer count;

    public GlSearchBean() {
    }

    public void searchAction(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("glSearchAction", null, null);
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public Integer getCount() {
        if (count == null) {
            try {
                count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
            } catch (Exception e) {
                System.out.println("error in on_load_page");
                e.printStackTrace();
            }
        }
        return count;
    }

    public void glResetActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("glSrchpageResetAction", null, null);
    }
}
