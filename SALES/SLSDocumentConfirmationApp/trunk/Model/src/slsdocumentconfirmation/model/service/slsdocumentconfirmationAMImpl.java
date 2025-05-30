package slsdocumentconfirmation.model.service;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;

import java.util.Date;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;


import oracle.jbo.server.ViewObjectImpl;

import slsdocumentconfirmation.model.service.common.slsdocumentconfirmationAM;
import slsdocumentconfirmation.model.views.LOVQueryForPopulatingFlexFieldsImpl;
import slsdocumentconfirmation.model.views.SLSDocConfSrcVOImpl;
import slsdocumentconfirmation.model.views.SearchVOImpl;
import slsdocumentconfirmation.model.views.SlsDocConfFlxVOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 27 15:32:41 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class slsdocumentconfirmationAMImpl extends ApplicationModuleImpl implements slsdocumentconfirmationAM {
    /**
     * This is the default constructor (do not remove).
     */
    public slsdocumentconfirmationAMImpl() {
    }

    /**
     * Container's getter for SearchView1.
     * @return SearchView1
     */
    public SearchVOImpl getSearchView1() {
        return (SearchVOImpl) findViewObject("SearchView1");
    }

    /**
     * This search method through we get serchVO trans Attributes  value get
     */
    public void search() {
        System.out.println("search  method");
        Row currentRow = getSearchView1().getCurrentRow();
        getViewSearchDocConf().setNamedWhereClauseParam("BindCldId", EbizParams.GLBL_APP_CLD_ID());
        getViewSearchDocConf().setNamedWhereClauseParam("BindSlocId", EbizParams.GLBL_APP_SERV_LOC());
        getViewSearchDocConf().setNamedWhereClauseParam("BindHoOrgId", EbizParams.GLBL_HO_ORG_ID());
        getViewSearchDocConf().setNamedWhereClauseParam("BindOrgId", EbizParams.GLBL_APP_USR_ORG());
        getViewSearchDocConf().setNamedWhereClauseParam("BindDispId",
                                                        currentRow.getAttribute("ConfirmationDocIdTrans"));
        getViewSearchDocConf().setNamedWhereClauseParam("BindUsrId", currentRow.getAttribute("CreateIdTrans"));
        getViewSearchDocConf().executeQuery();
    }

    /**
     *
     * This method is use to reset the element of SlsDocConfVo
     */
    public void reset() {
        System.out.println("Reset  method");
        getSearchView1().executeQuery();
        getViewSearchDocConf().setNamedWhereClauseParam("BindCldId", null);
        getViewSearchDocConf().setNamedWhereClauseParam("BindSlocId", null);
        getViewSearchDocConf().setNamedWhereClauseParam("BindHoOrgId", null);
        getViewSearchDocConf().setNamedWhereClauseParam("BindOrgId", null);
        getViewSearchDocConf().setNamedWhereClauseParam("BindDispId", null);
        getViewSearchDocConf().setNamedWhereClauseParam("BindUsrId", null);
        getViewSearchDocConf().executeQuery();
    }


    /**
     * Container's getter for SlsDocConf1.
     * @return SlsDocConf1
     */
    public ViewObjectImpl getSlsDocConf1() {
        return (ViewObjectImpl) findViewObject("SlsDocConf1");
    }


    /**
     * This method is get ID
     */
    public void viewSelectedDocumentId(String docId) {

        System.out.println("currentRow.getAttribute(\"SlocId\")" + docId);
        ViewObjectImpl confForSearch = this.getSlsDocConf1();
        confForSearch.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        confForSearch.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        confForSearch.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        confForSearch.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        confForSearch.setNamedWhereClauseParam("DocIdBind", docId);
        confForSearch.executeQuery();


    }

    /**
     * Container's getter for SlsDocConf2.
     * @return SlsDocConf2
     */
    public ViewObjectImpl getSlsDocConfForSearch() {
        return (ViewObjectImpl) findViewObject("SlsDocConfForSearch");
    }

    /**
     * Container's getter for SlsDocConfForLovVO1.
     * @return SlsDocConfForLovVO1
     */
    public ViewObjectImpl getSlsDocConfForLovVO1() {
        return (ViewObjectImpl) findViewObject("SlsDocConfForLovVO1");
    }

    /**
     * Container's getter for SLSDocConfSrcVO1.
     * @return SLSDocConfSrcVO1
     */
    public SLSDocConfSrcVOImpl getSLSDocConfSrcVO1() {
        return (SLSDocConfSrcVOImpl) findViewObject("SLSDocConfSrcVO1");
    }

    /**
     * Container's getter for SLSDocConfSrcVO2.
     * @return SLSDocConfSrcVO2
     */
    public SLSDocConfSrcVOImpl getSLSDocConfSrcVO2() {
        return (SLSDocConfSrcVOImpl) findViewObject("SLSDocConfSrcVO2");
    }

    /**
     * Container's getter for FkSlsDocConfSrcLink.
     * @return FkSlsDocConfSrcLink
     */
    public ViewLinkImpl getFkSlsDocConfSrcLink() {
        return (ViewLinkImpl) findViewLink("FkSlsDocConfSrcLink");
    }

    /**
     * Method to insert into the SLS$DOC$CONF$SRC
     * @return Boolean.
     * 1 : Conf Type is not defined.
     * 2 : DocIdSrc is not Selected
     * 3 : Duplicate docIdSrc
     */
    public Integer addSrcDetailsMethod() {
        Integer i = 0;
        ViewObjectImpl conf1 = this.getSlsDocConf1();
        Row currentRow = conf1.getCurrentRow();
        Object docDtO = StaticValue.getCurrDtWidTimestamp();
        Object confTypeO = currentRow.getAttribute("ConfType");
        Object docIdSrcO = currentRow.getAttribute("TransSrcDocId");
        String docIdSrc = (docIdSrcO == null ? "" : docIdSrcO.toString());
        Integer confType = (confTypeO == null ? -1 : (Integer) confTypeO);
        System.out.println("Doc Type src = " + confType);
        System.out.println("Doc source  = " + docIdSrc);
        if (confType.equals(-1)) {
            i = 1;
        } else if ("".equals(docIdSrc)) {
            i = 2;
        } else if (checkForDuplicateEntry()) {
            i = 3;
        } else {
            ViewObjectImpl confSrcVO3 = this.getSLSDocConfSrcVO3();
            Row createRow = confSrcVO3.createRow();
            createRow.setAttribute("DocDtSrc", docDtO);
            createRow.setAttribute("DocTypeSrc", confType);
            createRow.setAttribute("DocIdSrc", docIdSrc);
            confSrcVO3.insertRow(createRow);

            ViewObjectImpl fields1 = this.getLOVQueryForPopulatingFlexFields1();
            fields1.setNamedWhereClauseParam("bindCldId", EbizParams.GLBL_APP_CLD_ID());
            fields1.setNamedWhereClauseParam("bindDocId", 21516);
            fields1.setNamedWhereClauseParam("BindDocTypeId", confType);
            fields1.executeQuery();
            ViewObjectImpl confFlxVO1 = this.getSlsDocConfFlxVO1();
            RowSetIterator crt = fields1.createRowSetIterator(null);
            while (crt.hasNext()) {
                Row next = crt.next();
                Row row = confFlxVO1.createRow();
                row.setAttribute("DocId", confSrcVO3.getCurrentRow().getAttribute("DocId"));
                row.setAttribute("FieldId", next.getAttribute("FldId"));
                //row.setAttribute("DocIdSrc", confSrcVO3.getCurrentRow().getAttribute("DocIdSrc"));
                row.setAttribute("Reqd", next.getAttribute("Reqd"));
                row.setAttribute("DataType", next.getAttribute("DataType"));
                row.setAttribute("DataLen", next.getAttribute("DataLen"));
                confFlxVO1.insertRow(row);
                System.out.println("After Insrt Row");
            }
            confFlxVO1.executeQuery();
            currentRow.setAttribute("TransSrcDocId", null);
            currentRow.setAttribute("TransSrcDispId", null);
            
        }
        return i;
    }

    /**
     * Container's getter for SLSDocConfSrcVO3.
     * @return SLSDocConfSrcVO3
     */
    public SLSDocConfSrcVOImpl getSLSDocConfSrcVO3() {
        return (SLSDocConfSrcVOImpl) findViewObject("SLSDocConfSrcVO3");
    }

    /**
     * Container's getter for SLSDocConfToSLSDocConfSrcLink1.
     * @return SLSDocConfToSLSDocConfSrcLink1
     */
    public ViewLinkImpl getSLSDocConfToSLSDocConfSrcLink1() {
        return (ViewLinkImpl) findViewLink("SLSDocConfToSLSDocConfSrcLink1");
    }

    /**
     * Container's getter for SlsDocConfFlxVO1.
     * @return SlsDocConfFlxVO1
     */
    public SlsDocConfFlxVOImpl getSlsDocConfFlxVO1() {
        return (SlsDocConfFlxVOImpl) findViewObject("SlsDocConfFlxVO1");
    }

    /**
     * Contains Logic for checking the duplicate entry in the SLS$DOC$CONF$SRC
     * @return Boolean
     */
    public Boolean checkForDuplicateEntry() {
        Boolean Flag = false;
        ViewObjectImpl conf1 = this.getSlsDocConf1();
        Integer confTypeVar =
            conf1.getCurrentRow().getAttribute("ConfType") == null ? new Integer(0) :
            (Integer) conf1.getCurrentRow().getAttribute("ConfType");
        String confSrcIdVar =
            conf1.getCurrentRow().getAttribute("TransSrcDocId") == null ? " " :
            (String) conf1.getCurrentRow().getAttribute("TransSrcDocId");
        ViewObjectImpl confSrcVO3 = this.getSLSDocConfSrcVO3();
        RowQualifier rowQualifier = new RowQualifier(confSrcVO3);
        rowQualifier.setWhereClause("DocTypeSrc=" + confTypeVar + " AND DocIdSrc='" + confSrcIdVar + "'");
        Row[] ftrRows = confSrcVO3.getFilteredRows(rowQualifier);
        System.out.println("ftrRows :::::: " + ftrRows.length);
        if (ftrRows.length > 0) {
            Flag = true;
        } else {
            Flag = false;
        }
        System.out.println("Flag :::: " + Flag);
        return Flag;
    }

    /**
     * Container's getter for SLSDocConfSrcTOSLSDocConfFlexLink1.
     * @return SLSDocConfSrcTOSLSDocConfFlexLink1
     */
    public ViewLinkImpl getSLSDocConfSrcTOSLSDocConfFlexLink1() {
        return (ViewLinkImpl) findViewLink("SLSDocConfSrcTOSLSDocConfFlexLink1");
    }

    /**
     * Container's getter for LOVQueryForPopulatingFlexFields1.
     * @return LOVQueryForPopulatingFlexFields1
     */
    public LOVQueryForPopulatingFlexFieldsImpl getLOVQueryForPopulatingFlexFields1() {
        return (LOVQueryForPopulatingFlexFieldsImpl) findViewObject("LOVQueryForPopulatingFlexFields1");
    }

    /**
     * Container's getter for LOVQueryStatucVO1.
     * @return LOVQueryStatucVO1
     */
    public ViewObjectImpl getLOVQueryStatucVO1() {
        return (ViewObjectImpl) findViewObject("LOVQueryStatucVO1");
    }

    /**
     * Container's getter for LovQueryForConfIdVO1.
     * @return LovQueryForConfIdVO1
     */
    public ViewObjectImpl getLovQueryForConfIdVO1() {
        return (ViewObjectImpl) findViewObject("LovQueryForConfIdVO1");
    }

    /**
     * Container's getter for LOVQueryForDocTypeVO1.
     * @return LOVQueryForDocTypeVO1
     */
    public ViewObjectImpl getLOVQueryForDocTypeVO1() {
        return (ViewObjectImpl) findViewObject("LOVQueryForDocTypeVO1");
    }

    /**
     * Container's getter for LOVQueryForDocIdVO1.
     * @return LOVQueryForDocIdVO1
     */
    public ViewObjectImpl getLOVQueryForDocIdVO1() {
        return (ViewObjectImpl) findViewObject("LOVQueryForDocIdVO1");
    }

    /**
     * Container's getter for ViewSearchDocConf1.
     * @return ViewSearchDocConf1
     */
    public ViewObjectImpl getViewSearchDocConf() {
        return (ViewObjectImpl) findViewObject("ViewSearchDocConf");
    }
}

