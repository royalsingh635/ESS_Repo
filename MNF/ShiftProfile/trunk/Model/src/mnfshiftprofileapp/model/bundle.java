package mnfshiftprofileapp.model;

import java.util.ListResourceBundle;

public class bundle extends ListResourceBundle {
    private static final Object[][] contents = { { "mnfshiftprofileapp.model.views.LOVSearchAppShiftVO.ShiftNm_LABEL", "Shift Name" },
        { "mnfshiftprofileapp.model.views.LOVSearchAppShiftVO.ShiftId_LABEL", "Shift Id" },
        { "mnfshiftprofileapp.model.views.LOVOrganizationVO.OrgDesc_LABEL", "Organization" },
        { "mnfshiftprofileapp.model.entities.OrgAppShiftEO_Rule_0", "Duplicate Record Exists." }


    };

    public Object[][] getContents() {
        return contents;
    }
}
