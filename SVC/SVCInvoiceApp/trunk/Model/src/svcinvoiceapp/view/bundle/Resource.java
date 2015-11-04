package svcinvoiceapp.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "svcinvoiceapp.model.views.LovUsrVO_LABEL", "Lov Usr Vo" },
        { "svcinvoiceapp.model.views.LovWoDispDocIdVO_LABEL", "Lov Wo Disp Doc Id Vo" },
        { "svcinvoiceapp.model.views.LovCustSearchVO.EoNm_LABEL", "Customer Name" },
        { "svcinvoiceapp.model.views.LovCustomerVO.CurrDescTxn_LABEL", "Currency" },
        { "svcinvoiceapp.model.views.LovCustomerVO.EoNm_LABEL", "Customer Name" },
        { "svcinvoiceapp.model.views.LovCOAOCVO_LABEL", "Lov Coaocvo" }, { "svcinvoiceapp.model.views.ViewSCNoVO_LABEL", "View Sc No Vo" }




    };

    public Object[][] getContents() {
        return contents;
    }
}
