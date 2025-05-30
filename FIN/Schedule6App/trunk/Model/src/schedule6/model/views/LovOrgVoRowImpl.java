package schedule6.model.views;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 04 17:11:46 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovOrgVoRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrgId,
        OrgIdParent,
        OrgDesc;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int ORGIDPARENT = AttributesEnum.OrgIdParent.index();
    public static final int ORGDESC = AttributesEnum.OrgDesc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LovOrgVoRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgIdParent.
     * @return the OrgIdParent
     */
    public String getOrgIdParent() {
        return (String) getAttributeInternal(ORGIDPARENT);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgDesc.
     * @return the OrgDesc
     */
    public String getOrgDesc() {
        return (String) getAttributeInternal(ORGDESC);
    }
}

