package mmdashboardapp.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "mmdashboardapp.model.views.MmTkrVO_LABEL", "Mm Tkr Vo" }, { "mmdashboardapp.model.views.WfViewStockTakeVO_LABEL", "Wf View Stock Take Vo" }, { "mmdashboardapp.model.views.WfViewStockAdjVO_LABEL", "Wf View Stock Adj Vo" }, { "mmdashboardapp.model.views.WfViewQuotAnaVO_LABEL", "Wf View Quot Ana Vo" }, { "mmdashboardapp.model.views.WfViewPurReturnVO_LABEL", "Wf View Pur Return Vo" }, { "mmdashboardapp.model.views.TopNProductVO_LABEL", "Top N Product Vo" }, { "mmdashboardapp.model.views.TopNProductGrpVO_LABEL", "Top N Product Grp Vo" } };

    public Object[][] getContents() {
        return contents;
    }
}
