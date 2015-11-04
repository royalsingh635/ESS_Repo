package applicationentities.view.bean;

import applicationentities.model.service.GlblEntAMImpl;

import javax.faces.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ApplicationModuleImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppEnt {
    private RichInputText entid;
    private RichInputText entnm;
    private RichSelectOneChoice enttypeid;
    private RichInputText entref;
    private RichInputText entreftype2;
    private RichInputText entresv;
    private RichInputText entactv;
    private RichInputText entenfresvrule;
    private RichInputText entenfdefrule;
    private RichInputText entenfactvrule;
    private RichInputText entenfactvpercrule;
    private RichInputText entfinalizeflg;
    private RichInputText entmultilingual;
    private RichInputText entde;
    private RichInputText entreftype1;
    private RichInputText entsrreflvl;
    private RichInputText entrefothent;
    private RichInputText entrefothentinst;
    private RichInputText entrefothentcnt;
    private RichInputText entvwsupusr;
    private RichInputText entvwitadmusr;
    private RichInputText entvwothusr;
    private RichInputText entdesupusr;
    private RichInputText entdelitadmusr;
    private RichInputText entdeothusr;
    private RichInputText entedsupusr;
    private RichInputText enteditadmusr;
    private RichInputText entedothusr;
    private RichInputText entdocvwsupusr;
    private RichInputText entdocvwitadmusr;
    private RichInputText entdocvwothusr;
    private RichInputText entdbobname;
    private RichInputText entna;
    private RichInputText entseg;
    private RichInputText entlegcol;
    private RichInputText entlegcolcount;
    private RichInputText entlegcol1chkman;
    private RichInputText entlegcol2chkman;
    private RichInputText entlegcol1dt;
    private RichInputText entlegcol2dt;
    private RichInputText entlegcol1dtlen;
    private RichInputText entlegcol2dtlen;
    private RichInputText entchklegcolunique;
    private RichInputText entcoa;
    private RichInputText entcc;
    private RichInputText entattlnk;
    private RichInputText entattid;
    private RichInputText entatttypeid;
    private RichInputText entlegcol3chkman;
    private RichInputText entlegcol4chkman;
    private RichInputText entlegcol3dt;
    private RichInputText entlegcol4dt;
    private RichInputText entlegcol3dtlen;
    private RichInputText entlegcol4dtlen;
    private RichInputText entlegcol1dtprclen;
    private RichInputText entlegcol2dtprclen;
    private RichInputText entlegcol3dtprclen;
    private RichInputText entlegcol4dtprclen;

    private RichSelectOneChoice lovEntMultilingual;
    private RichSelectOneChoice lovEntResv;
    private RichSelectOneChoice lovEntActive;
    private RichSelectOneChoice lovEntEnfActvrule;
    private RichSelectOneChoice lovEntEnfDefRule;
    private RichSelectOneChoice lovEntEnfResvRule;
    private RichSelectOneChoice lovEntActvPerRule;
    private RichSelectOneChoice lovEntcc;
    private RichSelectOneChoice lovEntCoa;
    private RichSelectOneChoice lovEntNa;
    private RichSelectOneChoice lovEntSeg;
    private RichSelectOneChoice lovEntAttLnk;
    private RichSelectOneChoice lovEntLegCol;
    private RichSelectOneChoice lovEntLegCol1ChkMan;
    private RichSelectOneChoice lovEntLegCol2ChkMan;
    private RichSelectOneChoice lovEntLegCol3ChkMan;
    private RichSelectOneChoice lovEntLegCol4ChkMan;
    private RichSelectOneChoice lovEntFinaliseFlg;
    private RichSelectOneChoice lovEntDe;
    private RichSelectOneChoice lovEntLegColUnique;
    private RichSelectOneChoice lovEntDeItAdamUsr;
    private RichSelectOneChoice lovEntDeOthUsr;
    private RichSelectOneChoice lovEntDeSupUsr;
    private RichSelectOneChoice lovEntDocVwItAdamUsr;
    private RichSelectOneChoice lovEntDocVwOthUsr;
    private RichSelectOneChoice lovEntDocVwSupUsr;
    private RichSelectOneChoice lovEntEdItAdamUsr;
    private RichSelectOneChoice lovEntEdOthUsr;
    private RichSelectOneChoice lovEntEdSupUsr;
    private RichSelectOneChoice lovEntVwItAdamUsr;
    private RichSelectOneChoice lovEntVwOthUsr;
    private RichSelectOneChoice lovEntVwSupUsr;
    private RichSelectOneChoice lovEntAttId;
    private RichSelectOneChoice lovEntAttType;
    private RichSelectOneChoice lovEntRef;
    private RichSelectOneChoice lovEntRefType1;
    private RichSelectOneChoice lovEntRefType2;
    private RichSelectOneChoice lovRefOthEntInst;
    private RichSelectOneChoice lovEntAttribute;
    private RichSelectOneChoice lovEntRefOthEnt;
    private RichSelectOneChoice resvEntityAdd;
    private RichSelectOneChoice entDeAdd;
    private RichSelectOneChoice entDeEntOthUsr;
    private RichSelectOneChoice entDeSupUsr;
    private RichSelectOneChoice entEdOthUsr;
    private RichSelectOneChoice entEdSupUsr;
    private RichSelectOneChoice entEdItAdamUsr;
    private RichSelectOneChoice entVwItAdamUsr;
    private RichSelectOneChoice entVwOthusr;
    private RichSelectOneChoice entVwSupUsr;
    private RichSelectOneChoice entDocVwItadmAdd;
    private RichSelectOneChoice entDocVwOthUsrAdd;
    private RichSelectOneChoice lovLegCol1DtEdit;
    private RichSelectOneChoice lovLegCol2DtEdit;
    private RichSelectOneChoice lovLegCol3DtEdit;
    private RichSelectOneChoice lovLegCol4DtEdit;
    private RichInputText entCompPosVw;
    private RichSelectOneChoice entCompType;
    private RichSelectOneChoice entCompKey;
    private RichSelectOneChoice entCompRefCompId;
    private RichSelectOneChoice entCompRefAttId;
    private RichInputText attTypeName;
    private RichInputText entCompDesc;
    private RichSelectOneChoice entCompDt;
    private RichInputText entCompDtLen;
    private RichInputText entCompDtPrc;
    private RichSelectOneChoice entCompForChildRef;
    private RichInputText entCompDbobColNm;
    private RichSelectOneChoice entCompColType;
    private RichSelectOneChoice entCompDtFlg;


    public AppEnt() {
        
    }
    public boolean flag = true;

    public void viewEntity(ActionEvent actionEvent) {
        // Add event code here...
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");

                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void saveadd(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
        createOpBAddress.execute();
    }

    public void canceladd(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
    }

    public String edit() {
        entid.setReadOnly(false);
        entnm.setReadOnly(false);
        enttypeid.setReadOnly(false);
        entref.setReadOnly(false);
        entreftype2.setReadOnly(false);
        entde.setReadOnly(false);
        entreftype1.setReadOnly(false);
        entsrreflvl.setReadOnly(false);
        entrefothent.setReadOnly(false);
        entrefothentinst.setReadOnly(false);
        entrefothentcnt.setReadOnly(false);
        entvwsupusr.setReadOnly(false);
        entvwitadmusr.setReadOnly(false);
        entvwothusr.setReadOnly(false);
        entdesupusr.setReadOnly(false);
        entdelitadmusr.setReadOnly(false);
        entdeothusr.setReadOnly(false);
        entedsupusr.setReadOnly(false);
        enteditadmusr.setReadOnly(false);
        entedothusr.setReadOnly(false);
        entdocvwsupusr.setReadOnly(false);
        entdocvwitadmusr.setReadOnly(false);
        entdocvwothusr.setReadOnly(false);
        entdbobname.setReadOnly(false);

        entlegcolcount.setReadOnly(false);

        entlegcol1dt.setReadOnly(false);
        entlegcol2dt.setReadOnly(false);
        entlegcol1dtlen.setReadOnly(false);
        entlegcol2dtlen.setReadOnly(false);
        lovLegCol1DtEdit.setReadOnly(false);
        lovLegCol2DtEdit.setReadOnly(false);
        lovLegCol3DtEdit.setReadOnly(false);
        lovLegCol4DtEdit.setReadOnly(false);

        entattid.setReadOnly(false);
        entatttypeid.setReadOnly(false);

        entlegcol3dt.setReadOnly(false);
        entlegcol4dt.setReadOnly(false);
        entlegcol3dtlen.setReadOnly(false);
        entlegcol4dtlen.setReadOnly(false);
        entlegcol1dtprclen.setReadOnly(false);
        entlegcol2dtprclen.setReadOnly(false);
        entlegcol3dtprclen.setReadOnly(false);
        entlegcol4dtprclen.setReadOnly(false);

        return null;
    }

    public void setEntid(RichInputText entid) {
        this.entid = entid;
    }

    public RichInputText getEntid() {
        return entid;
    }

    public void setEntnm(RichInputText entnm) {
        this.entnm = entnm;
    }

    public RichInputText getEntnm() {
        return entnm;
    }

    public void setEnttypeid(RichSelectOneChoice enttypeid) {
        this.enttypeid = enttypeid;
    }

    public RichSelectOneChoice getEnttypeid() {
        return enttypeid;
    }

    public void setEntref(RichInputText entref) {
        this.entref = entref;
    }

    public RichInputText getEntref() {
        return entref;
    }

    public void setEntreftype2(RichInputText entreftype2) {
        this.entreftype2 = entreftype2;
    }

    public RichInputText getEntreftype2() {
        return entreftype2;
    }

    public void setEntresv(RichInputText entresv) {
        this.entresv = entresv;
    }

    public RichInputText getEntresv() {
        return entresv;
    }

    public void setEntactv(RichInputText entactv) {
        this.entactv = entactv;
    }

    public RichInputText getEntactv() {
        return entactv;
    }

    public void setEntenfresvrule(RichInputText entenfresvrule) {
        this.entenfresvrule = entenfresvrule;
    }

    public RichInputText getEntenfresvrule() {
        return entenfresvrule;
    }

    public void setEntenfdefrule(RichInputText entenfdefrule) {
        this.entenfdefrule = entenfdefrule;
    }

    public RichInputText getEntenfdefrule() {
        return entenfdefrule;
    }

    public void setEntenfactvrule(RichInputText entenfactvrule) {
        this.entenfactvrule = entenfactvrule;
    }

    public RichInputText getEntenfactvrule() {
        return entenfactvrule;
    }

    public void setEntenfactvpercrule(RichInputText entenfactvpercrule) {
        this.entenfactvpercrule = entenfactvpercrule;
    }

    public RichInputText getEntenfactvpercrule() {
        return entenfactvpercrule;
    }

    public void setEntfinalizeflg(RichInputText entfinalizeflg) {
        this.entfinalizeflg = entfinalizeflg;
    }

    public RichInputText getEntfinalizeflg() {
        return entfinalizeflg;
    }

    public void setEntmultilingual(RichInputText entmultilingual) {
        this.entmultilingual = entmultilingual;
    }

    public RichInputText getEntmultilingual() {
        return entmultilingual;
    }

    public void setEntde(RichInputText entde) {
        this.entde = entde;
    }

    public RichInputText getEntde() {
        return entde;
    }

    public void setEntreftype1(RichInputText entreftype1) {
        this.entreftype1 = entreftype1;
    }

    public RichInputText getEntreftype1() {
        return entreftype1;
    }

    public void setEntsrreflvl(RichInputText entsrreflvl) {
        this.entsrreflvl = entsrreflvl;
    }

    public RichInputText getEntsrreflvl() {
        return entsrreflvl;
    }

    public void setEntrefothent(RichInputText entrefothent) {
        this.entrefothent = entrefothent;
    }

    public RichInputText getEntrefothent() {
        return entrefothent;
    }

    public void setEntrefothentinst(RichInputText entrefothentinst) {
        this.entrefothentinst = entrefothentinst;
    }

    public RichInputText getEntrefothentinst() {
        return entrefothentinst;
    }

    public void setEntrefothentcnt(RichInputText entrefothentcnt) {
        this.entrefothentcnt = entrefothentcnt;
    }

    public RichInputText getEntrefothentcnt() {
        return entrefothentcnt;
    }

    public void setEntvwsupusr(RichInputText entvwsupusr) {
        this.entvwsupusr = entvwsupusr;
    }

    public RichInputText getEntvwsupusr() {
        return entvwsupusr;
    }

    public void setEntvwitadmusr(RichInputText entvwitadmusr) {
        this.entvwitadmusr = entvwitadmusr;
    }

    public RichInputText getEntvwitadmusr() {
        return entvwitadmusr;
    }

    public void setEntvwothusr(RichInputText entvwothusr) {
        this.entvwothusr = entvwothusr;
    }

    public RichInputText getEntvwothusr() {
        return entvwothusr;
    }

    public void setEntdesupusr(RichInputText entdesupusr) {
        this.entdesupusr = entdesupusr;
    }

    public RichInputText getEntdesupusr() {
        return entdesupusr;
    }

    public void setEntdelitadmusr(RichInputText entdelitadmusr) {
        this.entdelitadmusr = entdelitadmusr;
    }

    public RichInputText getEntdelitadmusr() {
        return entdelitadmusr;
    }

    public void setEntdeothusr(RichInputText entdeothusr) {
        this.entdeothusr = entdeothusr;
    }

    public RichInputText getEntdeothusr() {
        return entdeothusr;
    }

    public void setEntedsupusr(RichInputText entedsupusr) {
        this.entedsupusr = entedsupusr;
    }

    public RichInputText getEntedsupusr() {
        return entedsupusr;
    }

    public void setEnteditadmusr(RichInputText enteditadmusr) {
        this.enteditadmusr = enteditadmusr;
    }

    public RichInputText getEnteditadmusr() {
        return enteditadmusr;
    }

    public void setEntedothusr(RichInputText entedothusr) {
        this.entedothusr = entedothusr;
    }

    public RichInputText getEntedothusr() {
        return entedothusr;
    }

    public void setEntdocvwsupusr(RichInputText entdocvwsupusr) {
        this.entdocvwsupusr = entdocvwsupusr;
    }

    public RichInputText getEntdocvwsupusr() {
        return entdocvwsupusr;
    }

    public void setEntdocvwitadmusr(RichInputText entdocvwitadmusr) {
        this.entdocvwitadmusr = entdocvwitadmusr;
    }

    public RichInputText getEntdocvwitadmusr() {
        return entdocvwitadmusr;
    }

    public void setEntdocvwothusr(RichInputText entdocvwothusr) {
        this.entdocvwothusr = entdocvwothusr;
    }

    public RichInputText getEntdocvwothusr() {
        return entdocvwothusr;
    }

    public void setEntdbobname(RichInputText entdbobname) {
        this.entdbobname = entdbobname;
    }

    public RichInputText getEntdbobname() {
        return entdbobname;
    }

    public void setEntna(RichInputText entna) {
        this.entna = entna;
    }

    public RichInputText getEntna() {
        return entna;
    }

    public void setEntseg(RichInputText entseg) {
        this.entseg = entseg;
    }

    public RichInputText getEntseg() {
        return entseg;
    }

    public void setEntlegcol(RichInputText entlegcol) {
        this.entlegcol = entlegcol;
    }

    public RichInputText getEntlegcol() {
        return entlegcol;
    }

    public void setEntlegcolcount(RichInputText entlegcolcount) {
        this.entlegcolcount = entlegcolcount;
    }

    public RichInputText getEntlegcolcount() {
        return entlegcolcount;
    }

    public void setEntlegcol1chkman(RichInputText entlegcol1chkman) {
        this.entlegcol1chkman = entlegcol1chkman;
    }

    public RichInputText getEntlegcol1chkman() {
        return entlegcol1chkman;
    }

    public void setEntlegcol2chkman(RichInputText entlegcol2chkman) {
        this.entlegcol2chkman = entlegcol2chkman;
    }

    public RichInputText getEntlegcol2chkman() {
        return entlegcol2chkman;
    }

    public void setEntlegcol1dt(RichInputText entlegcol1dt) {
        this.entlegcol1dt = entlegcol1dt;
    }

    public RichInputText getEntlegcol1dt() {
        return entlegcol1dt;
    }

    public void setEntlegcol2dt(RichInputText entlegcol2dt) {
        this.entlegcol2dt = entlegcol2dt;
    }

    public RichInputText getEntlegcol2dt() {
        return entlegcol2dt;
    }

    public void setEntlegcol1dtlen(RichInputText entlegcol1dtlen) {
        this.entlegcol1dtlen = entlegcol1dtlen;
    }

    public RichInputText getEntlegcol1dtlen() {
        return entlegcol1dtlen;
    }

    public void setEntlegcol2dtlen(RichInputText entlegcol2dtlen) {
        this.entlegcol2dtlen = entlegcol2dtlen;
    }

    public RichInputText getEntlegcol2dtlen() {
        return entlegcol2dtlen;
    }

    public void setEntchklegcolunique(RichInputText entchklegcolunique) {
        this.entchklegcolunique = entchklegcolunique;
    }

    public RichInputText getEntchklegcolunique() {
        return entchklegcolunique;
    }

    public void setEntcoa(RichInputText entcoa) {
        this.entcoa = entcoa;
    }

    public RichInputText getEntcoa() {
        return entcoa;
    }

    public void setEntcc(RichInputText entcc) {
        this.entcc = entcc;
    }

    public RichInputText getEntcc() {
        return entcc;
    }

    public void setEntattlnk(RichInputText entattlnk) {
        this.entattlnk = entattlnk;
    }

    public RichInputText getEntattlnk() {
        return entattlnk;
    }

    public void setEntattid(RichInputText entattid) {
        this.entattid = entattid;
    }

    public RichInputText getEntattid() {
        return entattid;
    }

    public void setEntatttypeid(RichInputText entatttypeid) {
        this.entatttypeid = entatttypeid;
    }

    public RichInputText getEntatttypeid() {
        return entatttypeid;
    }

    public void setEntlegcol3chkman(RichInputText entlegcol3chkman) {
        this.entlegcol3chkman = entlegcol3chkman;
    }

    public RichInputText getEntlegcol3chkman() {
        return entlegcol3chkman;
    }

    public void setEntlegcol4chkman(RichInputText entlegcol4chkman) {
        this.entlegcol4chkman = entlegcol4chkman;
    }

    public RichInputText getEntlegcol4chkman() {
        return entlegcol4chkman;
    }

    public void setEntlegcol3dt(RichInputText entlegcol3dt) {
        this.entlegcol3dt = entlegcol3dt;
    }

    public RichInputText getEntlegcol3dt() {
        return entlegcol3dt;
    }

    public void setEntlegcol4dt(RichInputText entlegcol4dt) {
        this.entlegcol4dt = entlegcol4dt;
    }

    public RichInputText getEntlegcol4dt() {
        return entlegcol4dt;
    }

    public void setEntlegcol3dtlen(RichInputText entlegcol3dtlen) {
        this.entlegcol3dtlen = entlegcol3dtlen;
    }

    public RichInputText getEntlegcol3dtlen() {
        return entlegcol3dtlen;
    }

    public void setEntlegcol4dtlen(RichInputText entlegcol4dtlen) {
        this.entlegcol4dtlen = entlegcol4dtlen;
    }

    public RichInputText getEntlegcol4dtlen() {
        return entlegcol4dtlen;
    }

    public void setEntlegcol1dtprclen(RichInputText entlegcol1dtprclen) {
        this.entlegcol1dtprclen = entlegcol1dtprclen;
    }

    public RichInputText getEntlegcol1dtprclen() {
        return entlegcol1dtprclen;
    }

    public void setEntlegcol2dtprclen(RichInputText entlegcol2dtprclen) {
        this.entlegcol2dtprclen = entlegcol2dtprclen;
    }

    public RichInputText getEntlegcol2dtprclen() {
        return entlegcol2dtprclen;
    }

    public void setEntlegcol3dtprclen(RichInputText entlegcol3dtprclen) {
        this.entlegcol3dtprclen = entlegcol3dtprclen;
    }

    public RichInputText getEntlegcol3dtprclen() {
        return entlegcol3dtprclen;
    }

    public void setEntlegcol4dtprclen(RichInputText entlegcol4dtprclen) {
        this.entlegcol4dtprclen = entlegcol4dtprclen;
    }

    public RichInputText getEntlegcol4dtprclen() {
        return entlegcol4dtprclen;
    }


    public String test() {
        entid.setReadOnly(false);
        entnm.setReadOnly(false);
        enttypeid.setReadOnly(false);

        entsrreflvl.setReadOnly(false);


        entrefothentcnt.setReadOnly(false);

        entdbobname.setReadOnly(false);

        entlegcolcount.setReadOnly(false);

       
        entlegcol1dtlen.setReadOnly(false);
        entlegcol2dtlen.setReadOnly(false);


        
        entlegcol3dtlen.setReadOnly(false);
        entlegcol4dtlen.setReadOnly(false);
        entlegcol1dtprclen.setReadOnly(false);
        entlegcol2dtprclen.setReadOnly(false);
        entlegcol3dtprclen.setReadOnly(false);
        entlegcol4dtprclen.setReadOnly(false);
        lovEntMultilingual.setReadOnly(false);
        lovEntResv.setReadOnly(false);
        lovEntActive.setReadOnly(false);
        lovEntEnfActvrule.setReadOnly(false);
        lovEntEnfDefRule.setReadOnly(false);
        lovEntEnfResvRule.setReadOnly(false);
        lovEntActvPerRule.setReadOnly(false);
        lovEntcc.setReadOnly(false);
        lovEntCoa.setReadOnly(false);
        lovEntNa.setReadOnly(false);
        lovEntSeg.setReadOnly(false);
        lovEntAttLnk.setReadOnly(false);
        lovEntLegCol.setReadOnly(false);
        lovEntLegCol1ChkMan.setReadOnly(false);
        lovEntLegCol2ChkMan.setReadOnly(false);
        lovEntLegCol3ChkMan.setReadOnly(false);
        lovEntLegCol4ChkMan.setReadOnly(false);
        lovEntFinaliseFlg.setReadOnly(false);
        lovEntDe.setReadOnly(false);
        lovEntLegColUnique.setReadOnly(false);
        lovEntDeItAdamUsr.setReadOnly(false);
        lovEntDeOthUsr.setReadOnly(false);
        lovEntDeSupUsr.setReadOnly(false);
        lovEntDocVwItAdamUsr.setReadOnly(false);
        lovEntDocVwOthUsr.setReadOnly(false);
        lovEntDocVwSupUsr.setReadOnly(false);
        lovEntEdItAdamUsr.setReadOnly(false);
        lovEntEdOthUsr.setReadOnly(false);
        lovEntEdSupUsr.setReadOnly(false);
        lovEntVwItAdamUsr.setReadOnly(false);
        lovEntVwOthUsr.setReadOnly(false);
        lovEntVwSupUsr.setReadOnly(false);
        lovEntRef.setReadOnly(false);
        lovEntRefType1.setReadOnly(false);
        lovEntRefType2.setReadOnly(false);
        lovRefOthEntInst.setReadOnly(false);
        lovEntAttribute.setReadOnly(false);
        lovEntRefOthEnt.setReadOnly(false);
        lovLegCol1DtEdit.setReadOnly(false);
        lovLegCol2DtEdit.setReadOnly(false);
        lovLegCol3DtEdit.setReadOnly(false);
        lovLegCol4DtEdit.setReadOnly(false);

        return null;
    }

    public void saveComp(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
        createOpBAddress.execute();
    }

    public void cancelComp(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
    }


    public String editcompedit() {

        entCompPosVw.setReadOnly(false);
        entCompType.setReadOnly(false);
        entCompKey.setReadOnly(false);
        entCompRefCompId.setReadOnly(false);
        entCompRefAttId.setReadOnly(false);
        attTypeName.setReadOnly(false);
        entCompDesc.setReadOnly(false);
        entCompDt.setReadOnly(false);
        entCompDtLen.setReadOnly(false);
        entCompDtPrc.setReadOnly(false);
        entCompForChildRef.setReadOnly(false);
        entCompDbobColNm.setReadOnly(false);
        entCompColType.setReadOnly(false);
        entCompDtFlg.setReadOnly(false);
        return null;
    }

    public void saveeditcomp(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
        createOpBAddress.execute();

    }

    public void canceleditcomp(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();

    }

    public void setLovEntMultilingual(RichSelectOneChoice lovEntMultilingual) {
        this.lovEntMultilingual = lovEntMultilingual;
    }

    public RichSelectOneChoice getLovEntMultilingual() {
        return lovEntMultilingual;
    }

    public void setLovEntResv(RichSelectOneChoice lovEntResv) {
        this.lovEntResv = lovEntResv;
    }

    public RichSelectOneChoice getLovEntResv() {
        return lovEntResv;
    }

    public void setLovEntActive(RichSelectOneChoice lovEntActive) {
        this.lovEntActive = lovEntActive;
    }

    public RichSelectOneChoice getLovEntActive() {
        return lovEntActive;
    }

    public void setLovEntEnfActvrule(RichSelectOneChoice lovEntEnfActvrule) {
        this.lovEntEnfActvrule = lovEntEnfActvrule;
    }

    public RichSelectOneChoice getLovEntEnfActvrule() {
        return lovEntEnfActvrule;
    }

    public void setLovEntEnfDefRule(RichSelectOneChoice lovEntEnfDefRule) {
        this.lovEntEnfDefRule = lovEntEnfDefRule;
    }

    public RichSelectOneChoice getLovEntEnfDefRule() {
        return lovEntEnfDefRule;
    }

    public void setLovEntEnfResvRule(RichSelectOneChoice lovEntEnfResvRule) {
        this.lovEntEnfResvRule = lovEntEnfResvRule;
    }

    public RichSelectOneChoice getLovEntEnfResvRule() {
        return lovEntEnfResvRule;
    }

    public void setLovEntActvPerRule(RichSelectOneChoice lovEntActvPerRule) {
        this.lovEntActvPerRule = lovEntActvPerRule;
    }

    public RichSelectOneChoice getLovEntActvPerRule() {
        return lovEntActvPerRule;
    }

    public void setLovEntcc(RichSelectOneChoice lovEntcc) {
        this.lovEntcc = lovEntcc;
    }

    public RichSelectOneChoice getLovEntcc() {
        return lovEntcc;
    }

    public void setLovEntCoa(RichSelectOneChoice lovEntCoa) {
        this.lovEntCoa = lovEntCoa;
    }

    public RichSelectOneChoice getLovEntCoa() {
        return lovEntCoa;
    }

    public void setLovEntNa(RichSelectOneChoice lovEntNa) {
        this.lovEntNa = lovEntNa;
    }

    public RichSelectOneChoice getLovEntNa() {
        return lovEntNa;
    }

    public void setLovEntSeg(RichSelectOneChoice lovEntSeg) {
        this.lovEntSeg = lovEntSeg;
    }

    public RichSelectOneChoice getLovEntSeg() {
        return lovEntSeg;
    }

    public void setLovEntAttLnk(RichSelectOneChoice lovEntAttLnk) {
        this.lovEntAttLnk = lovEntAttLnk;
    }

    public RichSelectOneChoice getLovEntAttLnk() {
        return lovEntAttLnk;
    }

    public void setLovEntLegCol(RichSelectOneChoice lovEntLegCol) {
        this.lovEntLegCol = lovEntLegCol;
    }

    public RichSelectOneChoice getLovEntLegCol() {
        return lovEntLegCol;
    }

    public void setLovEntLegCol1ChkMan(RichSelectOneChoice lovEntLegCol1ChkMan) {
        this.lovEntLegCol1ChkMan = lovEntLegCol1ChkMan;
    }

    public RichSelectOneChoice getLovEntLegCol1ChkMan() {
        return lovEntLegCol1ChkMan;
    }

    public void setLovEntLegCol2ChkMan(RichSelectOneChoice lovEntLegCol2ChkMan) {
        this.lovEntLegCol2ChkMan = lovEntLegCol2ChkMan;
    }

    public RichSelectOneChoice getLovEntLegCol2ChkMan() {
        return lovEntLegCol2ChkMan;
    }

    public void setLovEntLegCol3ChkMan(RichSelectOneChoice lovEntLegCol3ChkMan) {
        this.lovEntLegCol3ChkMan = lovEntLegCol3ChkMan;
    }

    public RichSelectOneChoice getLovEntLegCol3ChkMan() {
        return lovEntLegCol3ChkMan;
    }

    public void setLovEntLegCol4ChkMan(RichSelectOneChoice lovEntLegCol4ChkMan) {
        this.lovEntLegCol4ChkMan = lovEntLegCol4ChkMan;
    }

    public RichSelectOneChoice getLovEntLegCol4ChkMan() {
        return lovEntLegCol4ChkMan;
    }

    public void setLovEntFinaliseFlg(RichSelectOneChoice lovEntFinaliseFlg) {
        this.lovEntFinaliseFlg = lovEntFinaliseFlg;
    }

    public RichSelectOneChoice getLovEntFinaliseFlg() {
        return lovEntFinaliseFlg;
    }

    public void setLovEntDe(RichSelectOneChoice lovEntDe) {
        this.lovEntDe = lovEntDe;
    }

    public RichSelectOneChoice getLovEntDe() {
        return lovEntDe;
    }

    public void setLovEntLegColUnique(RichSelectOneChoice lovEntLegColUnique) {
        this.lovEntLegColUnique = lovEntLegColUnique;
    }

    public RichSelectOneChoice getLovEntLegColUnique() {
        return lovEntLegColUnique;
    }

    public void setLovEntDeItAdamUsr(RichSelectOneChoice lovEntDeItAdamUsr) {
        this.lovEntDeItAdamUsr = lovEntDeItAdamUsr;
    }

    public RichSelectOneChoice getLovEntDeItAdamUsr() {
        return lovEntDeItAdamUsr;
    }

    public void setLovEntDeOthUsr(RichSelectOneChoice lovEntDeOthUsr) {
        this.lovEntDeOthUsr = lovEntDeOthUsr;
    }

    public RichSelectOneChoice getLovEntDeOthUsr() {
        return lovEntDeOthUsr;
    }

    public void setLovEntDeSupUsr(RichSelectOneChoice lovEntDeSupUsr) {
        this.lovEntDeSupUsr = lovEntDeSupUsr;
    }

    public RichSelectOneChoice getLovEntDeSupUsr() {
        return lovEntDeSupUsr;
    }

    public void setLovEntDocVwItAdamUsr(RichSelectOneChoice lovEntDocVwItAdamUsr) {
        this.lovEntDocVwItAdamUsr = lovEntDocVwItAdamUsr;
    }

    public RichSelectOneChoice getLovEntDocVwItAdamUsr() {
        return lovEntDocVwItAdamUsr;
    }

    public void setLovEntDocVwOthUsr(RichSelectOneChoice lovEntDocVwOthUsr) {
        this.lovEntDocVwOthUsr = lovEntDocVwOthUsr;
    }

    public RichSelectOneChoice getLovEntDocVwOthUsr() {
        return lovEntDocVwOthUsr;
    }

    public void setLovEntDocVwSupUsr(RichSelectOneChoice lovEntDocVwSupUsr) {
        this.lovEntDocVwSupUsr = lovEntDocVwSupUsr;
    }

    public RichSelectOneChoice getLovEntDocVwSupUsr() {
        return lovEntDocVwSupUsr;
    }

    public void setLovEntEdItAdamUsr(RichSelectOneChoice lovEntEdItAdamUsr) {
        this.lovEntEdItAdamUsr = lovEntEdItAdamUsr;
    }

    public RichSelectOneChoice getLovEntEdItAdamUsr() {
        return lovEntEdItAdamUsr;
    }

    public void setLovEntEdOthUsr(RichSelectOneChoice lovEntEdOthUsr) {
        this.lovEntEdOthUsr = lovEntEdOthUsr;
    }

    public RichSelectOneChoice getLovEntEdOthUsr() {
        return lovEntEdOthUsr;
    }

    public void setLovEntEdSupUsr(RichSelectOneChoice lovEntEdSupUsr) {
        this.lovEntEdSupUsr = lovEntEdSupUsr;
    }

    public RichSelectOneChoice getLovEntEdSupUsr() {
        return lovEntEdSupUsr;
    }

    public void setLovEntVwItAdamUsr(RichSelectOneChoice lovEntVwItAdamUsr) {
        this.lovEntVwItAdamUsr = lovEntVwItAdamUsr;
    }

    public RichSelectOneChoice getLovEntVwItAdamUsr() {
        return lovEntVwItAdamUsr;
    }

    public void setLovEntVwOthUsr(RichSelectOneChoice lovEntVwOthUsr) {
        this.lovEntVwOthUsr = lovEntVwOthUsr;
    }

    public RichSelectOneChoice getLovEntVwOthUsr() {
        return lovEntVwOthUsr;
    }

    public void setLovEntVwSupUsr(RichSelectOneChoice lovEntVwSupUsr) {
        this.lovEntVwSupUsr = lovEntVwSupUsr;
    }

    public RichSelectOneChoice getLovEntVwSupUsr() {
        return lovEntVwSupUsr;
    }

    public void setLovEntAttId(RichSelectOneChoice lovEntAttId) {
        this.lovEntAttId = lovEntAttId;
    }

    public RichSelectOneChoice getLovEntAttId() {
        return lovEntAttId;
    }

    public void setLovEntAttType(RichSelectOneChoice lovEntAttType) {
        this.lovEntAttType = lovEntAttType;
    }

    public RichSelectOneChoice getLovEntAttType() {
        return lovEntAttType;
    }

    public void setLovEntRef(RichSelectOneChoice lovEntRef) {
        this.lovEntRef = lovEntRef;
    }

    public RichSelectOneChoice getLovEntRef() {
        return lovEntRef;
    }

    public void setLovEntRefType1(RichSelectOneChoice lovEntRefType1) {
        this.lovEntRefType1 = lovEntRefType1;
    }

    public RichSelectOneChoice getLovEntRefType1() {
        return lovEntRefType1;
    }

    public void setLovEntRefType2(RichSelectOneChoice lovEntRefType2) {
        this.lovEntRefType2 = lovEntRefType2;
    }

    public RichSelectOneChoice getLovEntRefType2() {
        return lovEntRefType2;
    }

    public void setLovRefOthEntInst(RichSelectOneChoice lovRefOthEntInst) {
        this.lovRefOthEntInst = lovRefOthEntInst;
    }

    public RichSelectOneChoice getLovRefOthEntInst() {
        return lovRefOthEntInst;
    }

    public void setLovEntAttribute(RichSelectOneChoice lovEntAttribute) {
        this.lovEntAttribute = lovEntAttribute;
    }

    public RichSelectOneChoice getLovEntAttribute() {
        return lovEntAttribute;
    }

    public void setLovEntRefOthEnt(RichSelectOneChoice lovEntRefOthEnt) {
        this.lovEntRefOthEnt = lovEntRefOthEnt;
    }

    public RichSelectOneChoice getLovEntRefOthEnt() {
        return lovEntRefOthEnt;
    }


    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);

    }


    public String addComp() {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEntComp1();
        ViewObjectImpl f = am.getAppDsEnt1();
        Row row = v.getCurrentRow();
        Row row1 = f.getCurrentRow();
        System.out.println(row.getAttribute("EntId") + "aaaaaaaaaaaaaagffffffffffffffffff");
        String i = row.getAttribute("EntId").toString();
        String s = row1.getAttribute("EntRefOthEnt").toString();
        String t = "N";
        System.out.println(s+"sssssssssssssssssssssssssssssssssssss");
        Integer j = 0;
        Connection conn;
        try {
            conn = am.getDBTransaction().createStatement(0).getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs =
                stmt.executeQuery("select nvl(max(ENT_COMP_POS),0)+1 abc from APP$DS$ENT$COMP WHERE ent_id = '" + i +
                                  "'");

            System.out.println(rs + "------------");
            while (rs.next()) {
                j = rs.getInt("abc");
                System.out.println(j + "--------jjjjjjjjjjjjjjjjjjjj----");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (s.equalsIgnoreCase("N"))
        { row.setAttribute("EntCompForChildRef",t);
            }
        row.setAttribute("EntCompPos", j);
        row.setAttribute("EntCompPosVw", j);
        return "Addcomp";
    }


    public void coaValueChange(ValueChangeEvent valueChangeEvent) {

        String a = valueChangeEvent.getNewValue().toString();
        System.out.println(a + "aaaaaaaaaaaaaaaaiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        String i = "N";

        if (a.equalsIgnoreCase("N")) {
            System.out.println("Entered into if.........................");
            row.setAttribute("EntCc", a);
            row.setAttribute("EntSeg", a);
            row.setAttribute("EntNa", a);

            System.out.println(row.getAttribute("EntCc") + "Entered into if.........................");
        }

        else if (a.equalsIgnoreCase("Y")) {
            System.out.println("Entered into Else if.........................");
            row.setAttribute("EntCc", a);
            row.setAttribute("EntSeg", i);
            row.setAttribute("EntNa", i);
        }
    }

    public void entCcValueChangeListener(ValueChangeEvent valueChangeEvent) {
        String a = valueChangeEvent.getNewValue().toString();
        System.out.println(a + "aaaaaaaaaaaaaaaaiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        String i = "N";
        if (a.equalsIgnoreCase("Y")) {
            System.out.println("Entered into if.........................");
            row.setAttribute("EntSeg", i);
            row.setAttribute("EntNa", i);
        }
    }

    public void segmentValueChangeList(ValueChangeEvent valueChangeEvent) {
        String a = valueChangeEvent.getNewValue().toString();
        System.out.println(a + "aaaaaaaaaaaaaaaaiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        String i = "N";
        if (a.equalsIgnoreCase("Y")) {
            System.out.println("Entered into if.........................");
            row.setAttribute("EntCc", i);
            row.setAttribute("EntNa", i);
        }
    }

    public void naValueChangeList(ValueChangeEvent valueChangeEvent) {
        String a = valueChangeEvent.getNewValue().toString();
        System.out.println(a + "aaaaaaaaaaaaaaaaiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        String i = "N";
        if (a.equalsIgnoreCase("Y")) {
            System.out.println("Entered into if.........................");
            row.setAttribute("EntSeg", i);
            row.setAttribute("EntCc", i);
        }
    }

    public void validateDataTypeLen(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol1Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        Integer k = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
        else if (s != 2) {
            System.out.println("aaaaaaaaacccccccccccccvvvvvvvvv");
                    row.setAttribute("EntLegCol1DtPrcLen", k);
                }
    }

    public void validateLenPrc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol1Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } 
    }

    public void validateLegcol2(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol2Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        Integer k = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
        else if (s != 2) {
            System.out.println("aaaaaaaaacccccccccccccvvvvvvvvv");
                    row.setAttribute("EntLegCol2DtPrcLen", k);
                }
    }

    public void validateLegcol2lenPrc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol2Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
    }

    public void validateLegCol3Dt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol3Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        Integer k = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
        else if (s != 2) {
            System.out.println("aaaaaaaaacccccccccccccvvvvvvvvv");
                    row.setAttribute("EntLegCol3DtPrcLen", k);
                }
    }

    public void validateLegcol3dtlenPrc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol3Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
    }

    public void validateLegCol4DtLen(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol4Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        Integer k = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntNumLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
        else if (s != 2) {
            System.out.println("aaaaaaaaacccccccccccccvvvvvvvvv");
                    row.setAttribute("EntLegCol4DtPrcLen", k);
                }
    }

    public void validateLegCol4DtLenPrc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        Integer s = Integer.parseInt(row.getAttribute("EntLegCol4Dt").toString());
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMax").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntDecLenMin").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        if (s == 2 && i > z) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        } else if (s == 2 && i < y) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
        }
    }

    public void validateEntlegCount(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        Integer i = Integer.parseInt(c);
        System.out.println(i + "iiiiiiiiiiiiiiiiiiiii");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl e = am.getAppGlblPrf1();
        Row[] row1 = e.getAllRowsInRange();
        Integer z = 0;
        Integer y = 0;
        for (int n = 0; n < row1.length; n++) {
            z = Integer.parseInt(row1[n].getAttribute("GlblEntMaxLegacyField").toString());
            y = Integer.parseInt(row1[n].getAttribute("GlblEntMinLegacyField").toString());
            System.out.println(z + "aaaaaaassssssssssssssdffffffff");
        }
        
        if (i > z)
        {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
            }
        else if (i < y)
        {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
            }
    }

    public void entRefValueChangeListener(ValueChangeEvent valueChangeEvent) {
      String i =  valueChangeEvent.getNewValue().toString();
      String j = "Y";
      String k = "N";
      System.out.println(i+"xxxxxxxxxxxxxxxxxxxx");
        GlblEntAMImpl am = (GlblEntAMImpl)resolvEl("GlblEntAMDataControl");
        ViewObjectImpl v = am.getAppDsEnt2();
        Row row = v.getCurrentRow();
        resvEntityAdd.setValue(k);
        if (i.equalsIgnoreCase("RM"))
        {
            System.out.println("eeeeeeeeeeeeerrrrrrrrr");
            row.setAttribute("EntResv", j);
                System.out.println(row.getAttribute("EntResv")+"aaaaaaaaaaccccccc");
              resvEntityAdd.setValue(j);
                  
                        }
        
       
    }

    public void setResvEntityAdd(RichSelectOneChoice resvEntityAdd) {
        this.resvEntityAdd = resvEntityAdd;
    }

    public RichSelectOneChoice getResvEntityAdd() {
        return resvEntityAdd;
    }


    public void validateActvEnt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String c = String.valueOf(object);
        System.out.println(c + "ccccccccccccccccc");
        if (c.equalsIgnoreCase("N"))
        {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erros", null));
            }
    }

    public void setEntDeAdd(RichSelectOneChoice entDeAdd) {
        this.entDeAdd = entDeAdd;
    }

    public RichSelectOneChoice getEntDeAdd() {
        return entDeAdd;
    }

    public void entDeValueChangeList(ValueChangeEvent valueChangeEvent) {
       String c = valueChangeEvent.getNewValue().toString();
       String j = "N";
       String k = "Y";
       
             entDeAdd.setValue(k); 
           entDeEntOthUsr.setValue(k);
           entDeSupUsr.setValue(k);
           entEdOthUsr.setValue(k);
           entEdSupUsr.setValue(k);
           entEdItAdamUsr.setValue(k);
           entVwItAdamUsr.setValue(k);
           entVwOthusr.setValue(k);
           entVwSupUsr.setValue(k); 
       if (c.equalsIgnoreCase("N"))
       {   System.out.println("gooooooooooooooooooooooooooooooooooooo");
               /* entDeAdd.setRequired(false); */
           entDeAdd.setValue(j); 
               entDeAdd.setDisabled(true);
                entDeEntOthUsr.setValue(j);
               entDeSupUsr.setValue(j);
               entEdOthUsr.setValue(j);
               entEdSupUsr.setValue(j);
               entEdItAdamUsr.setValue(j);
               entVwItAdamUsr.setValue(j);
               entVwOthusr.setValue(j);
               entVwSupUsr.setValue(j);
               
               entDeEntOthUsr.setDisabled(true);
               entDeSupUsr.setDisabled(true);
               entEdOthUsr.setDisabled(true);
               entEdSupUsr.setDisabled(true);
               entEdItAdamUsr.setDisabled(true);
               entVwItAdamUsr.setDisabled(true);
               entVwOthusr.setDisabled(true);
               entVwSupUsr.setDisabled(true); 
              
           }
       else if (c.equalsIgnoreCase("Y")) {
           entDeAdd.setDisabled(false);
          /*  entDeAdd.setRequired(true); */
           
           entDeEntOthUsr.setDisabled(false);
           entDeSupUsr.setDisabled(false);
           entEdOthUsr.setDisabled(false);
           entEdSupUsr.setDisabled(false);
           entEdItAdamUsr.setDisabled(false);
           entVwItAdamUsr.setDisabled(false);
           entVwOthusr.setDisabled(false);
           entVwSupUsr.setDisabled(false); 
           
       }
       }

    public void setEntDeEntOthUsr(RichSelectOneChoice entDeEntOthUsr) {
        this.entDeEntOthUsr = entDeEntOthUsr;
    }

    public RichSelectOneChoice getEntDeEntOthUsr() {
        return entDeEntOthUsr;
    }

    public void setEntDeSupUsr(RichSelectOneChoice entDeSupUsr) {
        this.entDeSupUsr = entDeSupUsr;
    }

    public RichSelectOneChoice getEntDeSupUsr() {
        return entDeSupUsr;
    }

    public void setEntEdOthUsr(RichSelectOneChoice entEdOthUsr) {
        this.entEdOthUsr = entEdOthUsr;
    }

    public RichSelectOneChoice getEntEdOthUsr() {
        return entEdOthUsr;
    }

    public void setEntEdSupUsr(RichSelectOneChoice entEdSupUsr) {
        this.entEdSupUsr = entEdSupUsr;
    }

    public RichSelectOneChoice getEntEdSupUsr() {
        return entEdSupUsr;
    }

    public void setEntEdItAdamUsr(RichSelectOneChoice entEdItAdamUsr) {
        this.entEdItAdamUsr = entEdItAdamUsr;
    }

    public RichSelectOneChoice getEntEdItAdamUsr() {
        return entEdItAdamUsr;
    }

    public void setEntVwItAdamUsr(RichSelectOneChoice entVwItAdamUsr) {
        this.entVwItAdamUsr = entVwItAdamUsr;
    }

    public RichSelectOneChoice getEntVwItAdamUsr() {
        return entVwItAdamUsr;
    }

    public void setEntVwOthusr(RichSelectOneChoice entVwOthusr) {
        this.entVwOthusr = entVwOthusr;
    }

    public RichSelectOneChoice getEntVwOthusr() {
        return entVwOthusr;
    }

    public void setEntVwSupUsr(RichSelectOneChoice entVwSupUsr) {
        this.entVwSupUsr = entVwSupUsr;
    }

    public RichSelectOneChoice getEntVwSupUsr() {
        return entVwSupUsr;
    }

    public void entRefType1ValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c  = valueChangeEvent.getNewValue().toString();
        String j = "N";
        String k = "Y";
        entDocVwOthUsrAdd.setValue(k);
        entDocVwItadmAdd.setValue(k);
        if (c.equalsIgnoreCase("I"))
        {        
                entDocVwItadmAdd.setValue(j);
                entDocVwItadmAdd.setDisabled(true); 
                entDocVwOthUsrAdd.setValue(j);
                entDocVwOthUsrAdd.setDisabled(true);
            }
        else if (c.equalsIgnoreCase("G"))
        
        { entDocVwItadmAdd.setDisabled(false); 
                entDocVwOthUsrAdd.setDisabled(false);
            
            }
    }

   

    public void setEntDocVwItadmAdd(RichSelectOneChoice entDocVwItadmAdd) {
        this.entDocVwItadmAdd = entDocVwItadmAdd;
    }

    public RichSelectOneChoice getEntDocVwItadmAdd() {
        return entDocVwItadmAdd;
    }

    public void setEntDocVwOthUsrAdd(RichSelectOneChoice entDocVwOthUsrAdd) {
        this.entDocVwOthUsrAdd = entDocVwOthUsrAdd;
    }

    public RichSelectOneChoice getEntDocVwOthUsrAdd() {
        return entDocVwOthUsrAdd;
    }

    public void entRefEditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c = valueChangeEvent.getNewValue().toString();
        String j = "Y";
        if (c.equalsIgnoreCase("RM"))
        {lovEntResv.setValue(j);
                lovEntResv.setDisabled(true);
            }
        else 
        {   lovEntResv.setDisabled(false);
            }
    }

    public void entCoaEditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c = valueChangeEvent.getNewValue().toString();
        String k = "N";
        if (c.equalsIgnoreCase("N"))
        {lovEntcc.setValue(k);
         lovEntNa.setValue(k);
         lovEntSeg.setValue(k);
                lovEntcc.setDisabled(true);
                lovEntNa.setDisabled(true);
                lovEntSeg.setDisabled(true);
            }
        else {
            lovEntcc.setDisabled(false);
            lovEntNa.setDisabled(false);
            lovEntSeg.setDisabled(false);
        }
        }

    public void entCcEditValueChangeList(ValueChangeEvent valueChangeEvent) {
       String c =  valueChangeEvent.getNewValue().toString();
       String k = "N";
       if(c.equalsIgnoreCase("Y"))
       {  lovEntNa.setValue(k);
          lovEntSeg.setValue(k);
           }
    }

    public void entNaEditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c =  valueChangeEvent.getNewValue().toString();
        String k = "N";
        if(c.equalsIgnoreCase("Y"))
        {  lovEntcc.setValue(k);
           lovEntSeg.setValue(k);
            }
    }

    public void entSegEditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c =  valueChangeEvent.getNewValue().toString();
        String k = "N";
        if(c.equalsIgnoreCase("Y"))
        {  lovEntNa.setValue(k);
           lovEntcc.setValue(k);
            }
    }

    public void entRefType1EditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c =valueChangeEvent.getNewValue().toString();
        String j = "N";
        if (c.equalsIgnoreCase("I"))
        {
        lovEntDocVwItAdamUsr.setValue(j);
            lovEntDocVwOthUsr.setValue(j);
            lovEntDocVwItAdamUsr.setDisabled(true);
            lovEntDocVwOthUsr.setDisabled(true);
        }
        else
        {
                lovEntDocVwItAdamUsr.setDisabled(false);
                lovEntDocVwOthUsr.setDisabled(false);
            
            }
    }

    public void entDeEditValueChangeList(ValueChangeEvent valueChangeEvent) {
        String c = valueChangeEvent.getNewValue().toString();
        String j = "N";
        if (c.equalsIgnoreCase("N"))
        {
                lovEntDeItAdamUsr.setValue(j);
                  lovEntDeOthUsr.setValue(j);
                  lovEntDeSupUsr.setValue(j);
                  lovEntEdItAdamUsr.setValue(j);
                  lovEntEdOthUsr.setValue(j);
                  lovEntEdSupUsr.setValue(j);
                  lovEntVwItAdamUsr.setValue(j);
                  lovEntVwOthUsr.setValue(j);
                  lovEntVwSupUsr.setValue(j);
            }
    }

    public void setLovLegCol1DtEdit(RichSelectOneChoice lovLegCol1DtEdit) {
        this.lovLegCol1DtEdit = lovLegCol1DtEdit;
    }

    public RichSelectOneChoice getLovLegCol1DtEdit() {
        return lovLegCol1DtEdit;
    }

    public void setLovLegCol2DtEdit(RichSelectOneChoice lovLegCol2DtEdit) {
        this.lovLegCol2DtEdit = lovLegCol2DtEdit;
    }

    public RichSelectOneChoice getLovLegCol2DtEdit() {
        return lovLegCol2DtEdit;
    }

    public void setLovLegCol3DtEdit(RichSelectOneChoice lovLegCol3DtEdit) {
        this.lovLegCol3DtEdit = lovLegCol3DtEdit;
    }

    public RichSelectOneChoice getLovLegCol3DtEdit() {
        return lovLegCol3DtEdit;
    }

    public void setLovLegCol4DtEdit(RichSelectOneChoice lovLegCol4DtEdit) {
        this.lovLegCol4DtEdit = lovLegCol4DtEdit;
    }

    public RichSelectOneChoice getLovLegCol4DtEdit() {
        return lovLegCol4DtEdit;
    }

    public void setEntCompPosVw(RichInputText entCompPosVw) {
        this.entCompPosVw = entCompPosVw;
    }

    public RichInputText getEntCompPosVw() {
        return entCompPosVw;
    }

    public void setEntCompType(RichSelectOneChoice entCompType) {
        this.entCompType = entCompType;
    }

    public RichSelectOneChoice getEntCompType() {
        return entCompType;
    }

    public void setEntCompKey(RichSelectOneChoice entCompKey) {
        this.entCompKey = entCompKey;
    }

    public RichSelectOneChoice getEntCompKey() {
        return entCompKey;
    }

    public void setEntCompRefCompId(RichSelectOneChoice entCompRefCompId) {
        this.entCompRefCompId = entCompRefCompId;
    }

    public RichSelectOneChoice getEntCompRefCompId() {
        return entCompRefCompId;
    }

    public void setEntCompRefAttId(RichSelectOneChoice entCompRefAttId) {
        this.entCompRefAttId = entCompRefAttId;
    }

    public RichSelectOneChoice getEntCompRefAttId() {
        return entCompRefAttId;
    }

    public void setAttTypeName(RichInputText attTypeName) {
        this.attTypeName = attTypeName;
    }

    public RichInputText getAttTypeName() {
        return attTypeName;
    }

    public void setEntCompDesc(RichInputText entCompDesc) {
        this.entCompDesc = entCompDesc;
    }

    public RichInputText getEntCompDesc() {
        return entCompDesc;
    }

    public void setEntCompDt(RichSelectOneChoice entCompDt) {
        this.entCompDt = entCompDt;
    }

    public RichSelectOneChoice getEntCompDt() {
        return entCompDt;
    }

    public void setEntCompDtLen(RichInputText entCompDtLen) {
        this.entCompDtLen = entCompDtLen;
    }

    public RichInputText getEntCompDtLen() {
        return entCompDtLen;
    }

    public void setEntCompDtPrc(RichInputText entCompDtPrc) {
        this.entCompDtPrc = entCompDtPrc;
    }

    public RichInputText getEntCompDtPrc() {
        return entCompDtPrc;
    }

    public void setEntCompForChildRef(RichSelectOneChoice entCompForChildRef) {
        this.entCompForChildRef = entCompForChildRef;
    }

    public RichSelectOneChoice getEntCompForChildRef() {
        return entCompForChildRef;
    }

    public void setEntCompDbobColNm(RichInputText entCompDbobColNm) {
        this.entCompDbobColNm = entCompDbobColNm;
    }

    public RichInputText getEntCompDbobColNm() {
        return entCompDbobColNm;
    }

    public void setEntCompColType(RichSelectOneChoice entCompColType) {
        this.entCompColType = entCompColType;
    }

    public RichSelectOneChoice getEntCompColType() {
        return entCompColType;
    }

    public void setEntCompDtFlg(RichSelectOneChoice entCompDtFlg) {
        this.entCompDtFlg = entCompDtFlg;
    }

    public RichSelectOneChoice getEntCompDtFlg() {
        return entCompDtFlg;
    }
}


