package chkbook.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "chkbook.model.view.BankLOV_LABEL", "Bank Lov" }, { "chkbook.model.view.BankTypLOV.SL_0_0", "P" }, { "chkbook.model.view.BankTypLOV.SL_0_1", "PRI PRINTED/PRINTED ON" }, { "chkbook.model.view.BankTypLOV.SL_1_0", "T" }, { "chkbook.model.view.BankTypLOV.SL_1_1", "TXN/PRE PRINTED WITH" }, { "chkbook.model.view.BankTypLOV.SL_2_0", "B" }, { "chkbook.model.view.BankTypLOV.SL_2_1", "BILL" }, { "chkbook.model.view.BankTypLOV_LABEL", "Bank Typ Lov" }, { "chkbook.model.view.BankTypeLOV_LABEL", "Bank Type Lov" }, { "chkbook.model.view.BankExtLOV_LABEL", "Bank Ext Lov" }, { "chkbook.model.view.ChqBKSlPadLOV_LABEL", "Chq Bk Sl Pad Lov" }, { "chkbook.model.view.ChqBkPrinVO_LABEL", "Chq Bk Prin Vo" }, { "chkbook.model.view.link.ChqBkSlNoToChqBkPrinVL_LABEL", "Chq Bk Sl No To Chq Bk Prin Vl" },
        //
        { "CHKBOOKCHQBKBNKID", "ChkBook.ChqBkBnkId" },
        //ChkBook.ChqBkStrtSl
        { "CHKBOOKCHQBKSTRTSL", "ChkBook.ChqBkStrtSl" },
        //ChkBook.ChqBkTyp
        { "CHKBOOKCHQBKTYP", "ChkBook.ChqBkTyp" },
        //
        { "CHKBOOKCHQBKSLPAD", "ChkBook.ChqBkSlpad" },
        //
        { "CHKBOOKCHQBKSTRTSLLPAD", "ChkBook.ChqBkStrtSlLpad" },
        //
        { "CHKBOOKCHQBKSLLPADCHAR", "ChkBook.ChqBkSlLpadchar" },
        //
        { "CHKBOOKCHQBKSTRTSLRPAD", "ChkBook.ChqBkStrtSlRpad" },
        //
        { "CHKBOOKCHQBKSLRPADCHAR", "ChkBook.ChqBkSlRpadChar" },
        //
        { "CHKBOOKCHQBKSLEXT", "ChkBook.ChqBkSlExt" },
        //
        { "CHKBOOKCHQBKSLEXTCHAR", "ChkBook.ChqBkSlExtChar" },
        //
        { "CHKBOOKCREATEDBY", "ChkBook.CreatedBy" },
        //
        { "CHKBOOKCREATEDATE", "ChkBook.CreateDate" },
        //
        { "CHKBOOKMODIFIEDBY", "ChkBook.ModifiedBy" },
        //
        { "CHKBOOKMODIFIEDDATE", "ChkBook.ModifiedDate" },
        //
        { "CHKBOOKCHQBKSLLPADLEN", "ChkBook.ChqBkSlLpadLen" },
        //
        { "CHKBOOKCHQBKSLRPADLEN", "ChkBook.ChqBkSlRpadLen" },
        //
        { "CHKBOOKDISPLAY", "ChkBook.Display" } };

    public Object[][] getContents() {
        return contents;
    }
}
