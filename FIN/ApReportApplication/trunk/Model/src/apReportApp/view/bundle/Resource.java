package apReportApp.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "apReportApp.model.views.EntityGoupVO_LABEL", "Entity Goup Vo" },
        { "apReportApp.model.views.LOVAppRptVO_LABEL", "Lov App Rpt Vo" }, { "apReportApp.model.views.LOVAppRptVO.RptNm_LABEL", "Report Name" },
        //
        { "apReportApp.model.views.EntityGoupVO.EoMstNm_LABEL", "20" }, { "apReportApp.model.views.CogLOV.CogNm_LABEL", "LBL.195" }, { "apReportApp.model.views.OrgLogoVO_LABEL", "Org Logo Vo" }





    };

    public Object[][] getContents() {
        return contents;
    }
}
