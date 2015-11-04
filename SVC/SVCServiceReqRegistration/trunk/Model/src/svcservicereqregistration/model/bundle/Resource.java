package svcservicereqregistration.model.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "svcservicereqregistration.model.views.LovScLocationVO_LABEL", "Lov Sc Location Vo" },
        { "svcservicereqregistration.model.views.LovScContainSrchVO_LABEL", "Lov Sc Contain Srch Vo" },
        { "svcservicereqregistration.model.views.LovItmCheckVO_LABEL", "Lov Itm Check Vo" },
        { "svcservicereqregistration.model.views.LovScItmSrVO_LABEL", "Lov Sc Itm Sr Vo" },
        { "svcservicereqregistration.model.views.links.FkSvcCmItmToSrVL_LABEL", "Fk Svc Cm Itm To Sr Vl" },
        //
        { "svcservicereqregistration.model.views.LovCustomerIdVO.EoNm_LABEL", "LBL.2736" },
        { "svcservicereqregistration.model.views.LovItmIdVO.ItmDesc_LABEL", "LBL.2452" },
        { "svcservicereqregistration.model.views.LovScItmSrVO.SrNo_LABEL", "LBL.909" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
