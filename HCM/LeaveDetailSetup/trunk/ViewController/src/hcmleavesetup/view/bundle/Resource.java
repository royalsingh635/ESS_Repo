package hcmleavesetup.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "hcmleavesetup.model.views.OrgHcmLeaveVO_LABEL", "Org Hcm Leave Vo" },
        { "hcmleavesetup.model.views.LOVLeaveNameVO_LABEL", "LOVLeaveNameVO" },
        { "hcmleavesetup.model.views.OrgHcmLeaveSubVO_LABEL", "Org Hcm Leave Sub Vo" },
        { "hcmleavesetup.model.views.viewlink.HcmLeaveToOrgHcmLeaveSubVL_LABEL", "Hcm Leave To Org Hcm Leave Sub Vl" },
        { "hcmleavesetup.model.entities.OrgHcmLeaveSubEO_Rule_0", "Duplicate Record Exists." },
        { "hcmleavesetup.model.views.viewlink.HcmLeaveVOToOrgHcmLeaveSubVL_LABEL",
          "Hcm Leave Vo To Org Hcm Leave Sub Vl" },
        { "hcmleavesetup.model.views.viewlink.HcmLeaveGrpToOrgHcmLeaveSubVL_LABEL",
          "Hcm Leave Grp To Org Hcm Leave Sub Vl" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
