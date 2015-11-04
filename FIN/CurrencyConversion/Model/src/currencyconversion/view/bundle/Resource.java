package currencyconversion.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "currencyconversion.model.views.AppCurrConvVO.BindCurrId_LABEL", "Currency From" }, { "currencyconversion.model.views.AppCurrConvVO_LABEL", "Date To" },
        { "currencyconversion.model.views.LovOrgVO_LABEL", "Lov Org Vo" },
        { "currencyconversion.model.views.AppCurrConvVO.CcEffDate_FMT_FORMATTER",
          "oracle.jbo.format.DefaultDateFormatter" },
        { "currencyconversion.model.views.AppCurrConvVO.CcEffDate_FMT_FORMAT", "yyyy-MM-dd G 'at' hh:mm:ss" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
