package currencyconversion.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "currencyconversion.model.views.AppCurrConvVO.BindCurrId_LABEL", "Currency From" }, { "currencyconversion.model.views.AppCurrConvVO_LABEL", "Date To" } };

    public Object[][] getContents() {
        return contents;
    }
}
