package naturalaccounts.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "naturalaccounts.model.views.YesNoLOV_LABEL", "Yes No Lov" }, { "naturalaccounts.model.views.CoaVO_LABEL", "Coa Vo" },
        //APP.DuplicateCheck.message
        { "naturalaccounts.model.views.AccNameLOVVO.NaEntNm_LABEL", "#{bundle['NaturalAccount.Name']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccNmTransnt_LABEL", "#{bundle['NaturalAccount.Name']}" },
        //APP.DuplicateCheck.message
        { "APP.DuplicateCheck.message", "MSG.375" }, { "naturalaccounts.model.entities.FinAccNaEO.AccNm_LABEL", "MSG.375" }


        //APP.DuplicateCheck.message

        //APP.DuplicateCheck.message
        };

    public Object[][] getContents() {
        return contents;
    }
}
