package appCostCenter.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "appCostCenter.model.View.SearchCcPrfVO_LABEL", "Search Cc Prf Vo" }, { "APP.Duplicaterecord", "MSG.46" },
        { "appCostCenter.model.View.views.AppCcDocDefaultDtlVO_LABEL", "App Cc Doc Default Dtl Vo" },
        { "appCostCenter.model.View.Link.views.link.AppCcDocToAppCcDocDtlVL_LABEL", "App Cc Doc To App Cc Doc Dtl Vl" },
        { "appCostCenter.model.View.views.LOVDbobColIdVO_LABEL", "Lov Dbob Col Id Vo" },
        { "appCostCenter.model.View.views.LOVDocCompVO_LABEL", "Lov Doc Comp Vo" },
        { "appCostCenter.model.View.Link.views.link.AppCcDocToAppCcDocDefaultDtlVL_LABEL",
          "App Cc Doc To App Cc Doc Default Dtl Vl" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
