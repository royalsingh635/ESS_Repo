package hmcdesgstrct.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = {
        { "hmcdesgstrct.model.views.HcmDesgReffDtlVO_LABEL", "Hcm Desg Reff Dtl Vo" },
        { "hmcdesgstrct.model.views.links.views.link.HcmDesigToOrgDesigReffDtl_LABEL",
          "Hcm Desig To Org Desig Reff Dtl" },
        { "hmcdesgstrct.model.views.ViewObjVO_LABEL", "OrgHcmPrfVo" },
        //

        { "hmcdesgstrct.model.views.OrgHcmPrfVO_LABEL", "Org Hcm Prf Vo" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
