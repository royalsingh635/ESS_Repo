package revenueprd.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "revenueprd.model.views.RevenueMstVO.BindCoaNm_LABEL", "COA Name" }, { "revenueprd.model.views.RevenueMstVO.BindStrDt_LABEL", "Date From" }, { "revenueprd.model.views.RevenueMstVO.BindEndDt_LABEL", "Date To" }, { "revenueprd.model.views.links.RevenueMstToRevenueDtlVL_LABEL", "Revenue Mst To Revenue Dtl Vl" } };

    public Object[][] getContents() {
        return contents;
    }
}
