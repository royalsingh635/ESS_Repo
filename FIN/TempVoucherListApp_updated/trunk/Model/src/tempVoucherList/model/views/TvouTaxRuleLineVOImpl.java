package tempVoucherList.model.views;

import oracle.jbo.server.TransactionEvent;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 24 11:49:51 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TvouTaxRuleLineVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public TvouTaxRuleLineVOImpl() {
    }
    
    public void afterCommit(TransactionEvent e){

    executeQuery();
    }
}
