package slsbiapp.view.dc;

public class OrgDC {
    private StringBuffer orgDesc;
    private StringBuffer amount;
    private StringBuffer orgId;
    public OrgDC() {
        super();
    }


    public void setOrgDesc(StringBuffer orgDesc) {
        this.orgDesc = orgDesc;
    }

    public StringBuffer getOrgDesc() {
        return orgDesc;
    }

    public void setAmount(StringBuffer amount) {
        this.amount = amount;
    }

    public StringBuffer getAmount() {
        return amount;
    }

    public void setOrgId(StringBuffer orgId) {
        this.orgId = orgId;
    }

    public StringBuffer getOrgId() {
        return orgId;
    }
}
