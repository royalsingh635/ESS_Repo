package mmrequsetforquotation.model.views;

import oracle.jbo.server.TransactionEvent;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 10 12:52:19 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmRfqTncVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public MmRfqTncVOImpl() {
    }
    
    public void afterCommit(TransactionEvent e){
        executeQuery();
    }
}
