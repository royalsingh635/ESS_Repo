package bdgfinancebudgetapp.view.bean;

import bdgfinancebudgetapp.view.utils.ADFUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class SearchEmpDtlBean {
    private RichPopup empDetailPopUpPgBind;

    public SearchEmpDtlBean() {
    }

    public void setEmpDetailPopUpPgBind(RichPopup empDetailPopUpPgBind) {
        this.empDetailPopUpPgBind = empDetailPopUpPgBind;
    }

    public RichPopup getEmpDetailPopUpPgBind() {
        return empDetailPopUpPgBind;
    }

    public void showEmployeeDetailsAL(ActionEvent actionEvent) {
        RichLink rl = (RichLink) actionEvent.getSource();
        System.out.println("EoId from bean is " + rl.getAttributes().get("eoIdAttr"));
        OperationBinding ob = ADFUtils.findOperation("filterEmpDetailInSales");
        ob.getParamsMap().put("eoId", rl.getAttributes().get("eoIdAttr"));
        ob.execute();

        showPopup(empDetailPopUpPgBind, true);
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
