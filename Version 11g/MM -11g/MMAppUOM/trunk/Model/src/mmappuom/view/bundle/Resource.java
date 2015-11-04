package mmappuom.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "mmappuom.model.views.AppDsAttVO.AttNm_LABEL", "Unit Class" },
        //App.mmappuom.UomClass.Label
        { "mmappuom.model.views.AppDsAttVO.BindUnitName_LABEL", "Class" },
        //App.mmappuom.UomClass.Label
        { "APPMMAPPUOMUOMCLASSLABEL", "App.mmappuom.UomClass.Label" }, { "mmappuom.model.views.AppUomClsVO.UomClassNm_LABEL", "LBL.1076" }


        //App.mmappuom.UomClass.Label
        };

    public Object[][] getContents() {
        return contents;
    }
}
