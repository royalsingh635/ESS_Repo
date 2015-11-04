package procurmentprocess.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "procurmentprocess.model.view.LovSuppForPoVO_LABEL", "Lov Supp For Po Vo" }, { "procurmentprocess.model.view.LovSuppForPoVO.EoNm_LABEL", "LBL.951" }, { "procurmentprocess.model.views.LOVSupplierVO.EoNm_LABEL", "LBL.951" }, { "procurmentprocess.model.views.LOVSupplierNameVO.EoNm_LABEL", "LBL.951" }, { "procurmentprocess.model.view.LovforQuotationVO_LABEL", "Lovfor Quotation Vo" }, { "LOVPoNoVOCriteria_displayName", "Purchase Order No" }, { "LovSuppForPoVOCriteria_displayName", "Supplier Name" }, { "procurmentprocess.model.module.MMDRFTPOVO_LABEL", "Mmdrftpovo" }, { "procurmentprocess.model.view.MMDRFTPOVO_LABEL", "Mmdrftpovo" } };

    public Object[][] getContents() {
        return contents;
    }
}
