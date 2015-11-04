package searchitemapp.view.bean;

import adf.utils.model.ADFModelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.component.UIXIterator;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

import searchitemapp.model.services.SearchItemAMImpl;
//import oracle.adf.share.ADFContext;

public class searchItemnewbean {
    private RichInputListOfValues pagebindgroupname;
    private RichOutputText pagebindgroupid;
    public Integer size = 0;
    public Integer newsize = 0;
    public Integer derillnewsize = 0;
    public Integer drillsize = 0;
    private RichInputListOfValues pagebinddrillattrtype;
    private String transtreegrpid;
    private RichPanelGroupLayout panelscrollfortreerefresh;

    public void setTranstreegrpid(String transtreegrpid) {
        this.transtreegrpid = transtreegrpid;
    }

    public String getTranstreegrpid() {
        return transtreegrpid;
    }

    public void setDerillnewsize(Integer derillnewsize) {
        this.derillnewsize = derillnewsize;
    }

    public Integer getDerillnewsize() {
        return derillnewsize;
    }

    public void setDrillsize(Integer drillsize) {
        this.drillsize = drillsize;
    }

    public Integer getDrillsize() {
        return drillsize;
    }
    private UIXIterator pagebindnewiterator;
    private RichSelectBooleanCheckbox pagebindincludechk;
    private RichSelectBooleanCheckbox pagebindincludeallchk;
    private RichTreeTable groupTree;
    private RichSelectOneRadio pagebindmatchradio;
    private RichOutputText pagebinddrillattrid;
    private RichInputListOfValues pagebinddrillattrname;

    public void setNewsize(Integer newsize) {
        this.newsize = newsize;
    }

    public Integer getNewsize() {
        return newsize;
    }
    public Integer sizegroupid = 0;
    public String groupname = "";
    public String iterategroupname;
    public int j = 0;
    String pass = null;
    HashMap map = new HashMap();
    HashMap newmap = new HashMap();
    HashMap drillnewmap = new HashMap();
    HashMap mapDelete = new HashMap();

    public void setDrillnewmap(HashMap drillnewmap) {
        this.drillnewmap = drillnewmap;
    }

    public HashMap getDrillnewmap() {
        return drillnewmap;
    }
    private RichInputText pagebindnewgrpid;
    private RichInputListOfValues pagebindattrname;
    private RichInputText pagebindattrId;

    public void setMap(HashMap map) {
        this.map = map;
    }

    public HashMap getMap() {
        return map;
    }

    public void setC(ArrayList<String> c) {
        this.c = c;
    }

    public ArrayList<String> getC() {
        return c;
    }
    ArrayList<String> a = new ArrayList<String>();
    ArrayList<String> b = new ArrayList<String>();
    ArrayList<String> c = new ArrayList<String>();
    ArrayList<String> d = new ArrayList<String>();
    private UIXIterator pagebindIterator;

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }


    public void setA(ArrayList<String> a) {
        this.a = a;
    }

    public ArrayList<String> getA() {
        return a;
    }

    public void addGroup(ActionEvent actionEvent) {
        if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null) {
            String grpId = pagebindnewgrpid.getValue().toString();
            System.out.println(" grp id " + grpId);
            if (a.size() != 0 && d.size() != 0) {
                for (int i = 0; i < d.size(); i++) {
                    System.out.println("d.get(i).toString() " + d.get(i));
                    if (grpId.equals(d.get(i).toString())) {
//                        Duplicate Group Name not allowed
                        String s=ADFModelUtils.resolvRsrc("['MSG.1635']");
                        FacesMessage message = new FacesMessage(s);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(pagebindgroupname.getClientId(), message);
                        return;
                        // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1635']}").toString(),null));

                    }


                }
            }

        }
        if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
            pagebindmatchradio.getValue().equals("N")) {

            System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());
            a.add(pagebindgroupname.getValue().toString());
            d.add(pagebindnewgrpid.getValue().toString());
            // map.put(pagebindgroupname.getValue().toString(), pagebindnewgrpid.getValue().toString()); // 05
            map.put(pagebindnewgrpid.getValue().toString(), pagebindgroupname.getValue().toString());
            this.size = a.size();
            System.out.println("Array List A " + a.size());
            System.out.println("Array List D " + d.size());
            System.out.println("Map size" + map.size());
            this.pagebindgroupname.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);


        } else if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
                   pagebindmatchradio.getValue().equals("D")) //  // D- all sub group O  Immediate sub group
        {
            System.out.println("check box value" + pagebindmatchradio.getValue());
            System.out.println("group name---" + pagebindgroupname.getValue());
            System.out.println("group id---" + pagebindnewgrpid.getValue());
            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            ViewObjectImpl vo = am.getLOVChkGrpParent1();
            //ViewObjectImpl vo1=(ViewObjectImpl)am.findViewObject("LOVChkGrpParent1");
            vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.setNamedWhereClauseParam("bindgrpidparent", pagebindnewgrpid.getValue().toString());
            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("bindslocid",
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.executeQuery();
            System.out.println("no. of rows---" + vo.getEstimatedRowCount());
            if (vo.getEstimatedRowCount() > 0) {
                System.out.println(map + "arr O a " + a);
                System.out.println(map + "arr O d " + d);
                a.removeAll(a);
                d.removeAll(d);
                map.clear();
                System.out.println(map + "arr after remove O a " + a);
                System.out.println(map + "arr after remove O d " + d);
                RowSetIterator rw = vo.createRowSetIterator("abc");
                while (rw.hasNext()) {
                    Row vorow = rw.next();
                    vorow.getAttribute("GrpNm");
                    vorow.getAttribute("GrpId");
                    System.out.println("group name---" + vorow.getAttribute("GrpNm"));
                    System.out.println("group id---" + vorow.getAttribute("GrpId"));
                    //System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());

                    /*  if(a.contains(vorow.getAttribute("GrpNm")))
               {
                   System.out.println("group name already exist");

               } */
                    //   else
                    //   {GrpNmDisp
                    // a.add(vorow.getAttribute("GrpNm").toString());
                    a.add(vorow.getAttribute("GrpNmDisp").toString());
                    d.add(vorow.getAttribute("GrpId").toString());
                    // map.put(vorow.getAttribute("GrpNm").toString(), vorow.getAttribute("GrpId").toString());  //05
                    //map.put(vorow.getAttribute("GrpId").toString(), vorow.getAttribute("GrpNm").toString());
                    map.put(vorow.getAttribute("GrpId").toString(), vorow.getAttribute("GrpNmDisp").toString());
                    this.size = a.size();
                    System.out.println("Array List A : " + a.size());
                    System.out.println("Array List D: " + d.size());
                    System.out.println("Map size" + map.size());

                    /*  OperationBinding ob =
                       BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
                   ob.execute(); */
                    // AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);

                    //    }

                }

                rw.closeRowSetIterator();


            }
            System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());
            a.add(pagebindgroupname.getValue().toString());
            d.add(pagebindnewgrpid.getValue().toString());
            // map.put(pagebindgroupname.getValue().toString(), pagebindnewgrpid.getValue().toString());  // 05
            map.put(pagebindnewgrpid.getValue().toString(), pagebindgroupname.getValue().toString());
            this.size = a.size();
            System.out.println("Array List A : " + a.size());
            System.out.println("Array List D : " + a.size());
            System.out.println("Map size" + map.size());
            System.out.println("Map size" + map);
            this.pagebindgroupname.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);

        } else if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
                   pagebindmatchradio.getValue().equals("O")) // D- all sub group O  Immediate sub group
        {
            System.out.println("check box value" + pagebindmatchradio.getValue());
            System.out.println("group name---" + pagebindgroupname.getValue());
            System.out.println("group id---" + pagebindnewgrpid.getValue());
            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            ViewObjectImpl vo = am.getLovIncludeAllChild1();
            //ViewObjectImpl vo1=(ViewObjectImpl)am.findViewObject("LOVChkGrpParent1");
            vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.setNamedWhereClauseParam("bindgrpid", pagebindnewgrpid.getValue().toString());
            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("bindslocid",
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.executeQuery();
            System.out.println("no. of rows---" + vo.getEstimatedRowCount());
            if (vo.getEstimatedRowCount() > 0) {
                System.out.println(map + "  arr   a : " + a);
                System.out.println(map + "  arr  D : " + a);
                a.removeAll(a);
                d.removeAll(d);
                map.clear();
                System.out.println(map + " arr after remove  A : " + a);
                System.out.println(map + " arr after remove D : " + a);
                RowSetIterator rw = vo.createRowSetIterator("abc");
                while (rw.hasNext()) {
                    Row vorow = rw.next();
                    vorow.getAttribute("GrpNm");
                    vorow.getAttribute("GrpId");
                    System.out.println("group name---" + vorow.getAttribute("GrpNm"));
                    System.out.println("group id---" + vorow.getAttribute("GrpId"));
                    //System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());

                    /*  if(a.contains(vorow.getAttribute("GrpNm")))
               {
                   System.out.println("group name already exist");

               } */

                    if (d.contains(vorow.getAttribute("GrpId"))) {
                        System.out.println("group name Id  already exist");

                    } else {
                        a.add(vorow.getAttribute("GrpNmDisp").toString());
                        //a.add(vorow.getAttribute("GrpNm").toString());
                        d.add(vorow.getAttribute("GrpId").toString());
                        //map.put(vorow.getAttribute("GrpNm").toString(), vorow.getAttribute("GrpId").toString()); // 05
                        // map.put(vorow.getAttribute("GrpId").toString(), vorow.getAttribute("GrpNm").toString());
                        map.put(vorow.getAttribute("GrpId").toString(), vorow.getAttribute("GrpNmDisp").toString());
                        this.size = a.size();
                        System.out.println("Array List A :" + a.size());
                        System.out.println("Array List D :" + d.size());
                        System.out.println("Map size" + map.size());

                        /*   OperationBinding ob =
                       BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
                   ob.execute(); */


                    }

                }

                rw.closeRowSetIterator();

            }
            System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());
            a.add(pagebindgroupname.getValue().toString());
            d.add(pagebindnewgrpid.getValue().toString());
            // map.put(pagebindgroupname.getValue().toString(), pagebindnewgrpid.getValue().toString()); // 05
            map.put(pagebindnewgrpid.getValue().toString(), pagebindgroupname.getValue().toString());
            this.size = a.size();
            System.out.println("Array List A " + a.size());
            System.out.println("Array List D " + d.size());
            System.out.println("Map size" + map.size());
            this.pagebindgroupname.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);
        }


    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setPagebindgroupname(RichInputListOfValues pagebindgroupname) {
        this.pagebindgroupname = pagebindgroupname;
    }

    public RichInputListOfValues getPagebindgroupname() {
        return pagebindgroupname;
    }

    public void setPagebindgroupid(RichOutputText pagebindgroupid) {
        this.pagebindgroupid = pagebindgroupid;
    }

    public RichOutputText getPagebindgroupid() {
        return pagebindgroupid;
    }

    public void setPagebindIterator(UIXIterator pagebindIterator) {
        this.pagebindIterator = pagebindIterator;
    }

    public UIXIterator getPagebindIterator() {
        return pagebindIterator;
    }

    public void deleteGroupname(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        System.out.println(ob.getAttributes().get("grpId") + "   Group Name to delete1=" +
                           ob.getAttributes().get("grpNm"));
        String grpName = (String) ob.getAttributes().get("grpNm");
        String grpId = (String) ob.getAttributes().get("grpId");
        System.out.println(grpId + "  Group Name to delete=" + grpName);
        //int j = map.size()-1;
        for (int i = 0; i < map.size(); i++) {
            System.out.println(" for loop delete " + i);
            String ab = d.get(i).toString(); // 05
            String newgrpNM = (String) map.get(ab); // 05
            System.out.println(ab + "  a b  " + newgrpNM);
            if (newgrpNM.equals(grpName)) {
                System.out.println(" delete grp >>>> " + ab);
                this.d.remove(ab);
                this.map.remove(ab);
                this.a.remove(grpName);
                break;
            }
        }

        // this.a.remove(grpName);
        // this.d.remove(grpId);

        //  this.map.remove(grpName);  //05
        //this.map.remove(grpId);
        System.out.println(map.size() + "  map   " + map);
        this.size = a.size();
    }

    public void setIterategroupname(String iterategroupname) {
        this.iterategroupname = iterategroupname;
    }

    public String getIterategroupname() {
        return iterategroupname;
    }

    public void setPagebindnewgrpid(RichInputText pagebindnewgrpid) {
        this.pagebindnewgrpid = pagebindnewgrpid;
    }

    public RichInputText getPagebindnewgrpid() {
        return pagebindnewgrpid;
    }

    public void searchgroupid(ActionEvent actionEvent) {

        OperationBinding ob =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchEmpNamAm");
        ob.getParamsMap().put("grpId", map);
        ob.getParamsMap().put("grpname", a);
        ob.execute();

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void validategrpname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && pagebindnewgrpid.getValue() != null) {
            /* System.out.println("inside validator ... ");
               System.out.println(" inside validator ... "+pagebindnewgrpid.getValue());
                  String st=(String)object;
                  String grpId =pagebindnewgrpid.getValue().toString();
                if(a.size() != 0 && d.size()!=0)
                { */



            /*  for(int i=0;i<a.size();i++)
                 {
                     if(st.equals(a.get(i).toString()))
                     {

                         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1635']}").toString(),null));

                     }


                 } */
            /* for(int i=0;i<d.size();i++)
                                 {
                                     if(grpId.equals(d.get(i).toString()))
                                     {

                                         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1635']}").toString(),null));

                                     }


                                 }
                } */
        }


    }

    public void setPagebindattrname(RichInputListOfValues pagebindattrname) {
        this.pagebindattrname = pagebindattrname;
    }

    public RichInputListOfValues getPagebindattrname() {
        return pagebindattrname;
    }

    public void setPagebindattrId(RichInputText pagebindattrId) {
        this.pagebindattrId = pagebindattrId;
    }

    public RichInputText getPagebindattrId() {
        return pagebindattrId;
    }

    public void addAttribute(ActionEvent actionEvent) {
        if (pagebindattrname.getValue() != null && pagebindattrId.getValue() != null) {

            System.out.println("GROUP NAME" + pagebindattrname.getValue().toString());
            b.add(pagebindattrname.getValue().toString());
            newmap.put(pagebindattrname.getValue().toString(), pagebindattrId.getValue().toString());
            this.newsize = b.size();
            System.out.println("Array List" + b.size());
            System.out.println("Map size" + newmap.size());
            this.pagebindattrname.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialattributeNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindattrname);


        }


    }

    public void deleteattrname(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        System.out.println("Atribute Name to delete1=" + ob.getAttributes().get("newgrpNm"));
        String grpName = (String) ob.getAttributes().get("newgrpNm");
        System.out.println("Attribute Name to delete=" + grpName);

        this.b.remove(grpName);
        this.newmap.remove(grpName);
        System.out.println(newmap.size());
        this.newsize = b.size();
    }

    public void setNewmap(HashMap newmap) {
        this.newmap = newmap;
    }

    public HashMap getNewmap() {
        return newmap;
    }

    public void setB(ArrayList<String> b) {
        this.b = b;
    }

    public ArrayList<String> getB() {
        return b;
    }

    public void validateattrname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String st = (String) object;

            if (b.size() != 0) {
                for (int i = 0; i < b.size(); i++) {
                    if (st.equals(b.get(i).toString())) {
//                        Duplicate Attribute Name not allowed
                        String s=ADFModelUtils.resolvRsrc("['MSG.1636']");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      s,
                                                                      null));

                    }


                }
            }
        }


    }

    public void setPagebindnewiterator(UIXIterator pagebindnewiterator) {
        this.pagebindnewiterator = pagebindnewiterator;
    }

    public UIXIterator getPagebindnewiterator() {
        return pagebindnewiterator;
    }

    public void searchgrpandattrid(ActionEvent actionEvent) {
        System.out.println(d + "searchgrpandattrid   " + map);
        System.out.println(b + "   ::newmap  :: " + newmap);

        OperationBinding ob =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchGrpAttrId");
        ob.getParamsMap().put("grpId", map);
        ob.getParamsMap().put("grpname", d); //here grpname contain grp id
        // ob.getParamsMap().put("grpname", a); //05  here grpname contain grp Name
        ob.getParamsMap().put("attrname", b);
        ob.getParamsMap().put("attrId", newmap);
        ob.execute();
    }

    public void setPagebindincludechk(RichSelectBooleanCheckbox pagebindincludechk) {
        this.pagebindincludechk = pagebindincludechk;
    }

    public RichSelectBooleanCheckbox getPagebindincludechk() {
        return pagebindincludechk;
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void valuechangechk(ValueChangeEvent valueChangeEvent) {
        if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
            pagebindmatchradio.getValue().equals("D")) //  // D- all sub group O  Immediate sub group
        {
            System.out.println("check box value" + pagebindmatchradio.getValue());
            System.out.println("group name---" + pagebindgroupname.getValue());
            System.out.println("group id---" + pagebindnewgrpid.getValue());
            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            ViewObjectImpl vo = am.getLOVChkGrpParent1();
            //ViewObjectImpl vo1=(ViewObjectImpl)am.findViewObject("LOVChkGrpParent1");
            vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.setNamedWhereClauseParam("bindgrpidparent", pagebindnewgrpid.getValue().toString());
            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("bindslocid",
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.executeQuery();
            System.out.println("no. of rows---" + vo.getEstimatedRowCount());
            if (vo.getEstimatedRowCount() > 0) {
                System.out.println("arr O a " + a);
                a.removeAll(a);
                System.out.println("arr after remove O a " + a);
                RowSetIterator rw = vo.createRowSetIterator("abc");
                while (rw.hasNext()) {
                    Row vorow = rw.next();
                    vorow.getAttribute("GrpNm");
                    vorow.getAttribute("GrpId");
                    System.out.println("group name---" + vorow.getAttribute("GrpNm"));
                    System.out.println("group id---" + vorow.getAttribute("GrpId"));
                    //System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());

                    /*  if(a.contains(vorow.getAttribute("GrpNm")))
               {
                   System.out.println("group name already exist");

               } */
                    //   else
                    //   {
                    a.add(vorow.getAttribute("GrpNm").toString());
                    map.put(vorow.getAttribute("GrpNm").toString(), vorow.getAttribute("GrpId").toString());
                    this.size = a.size();
                    System.out.println("Array List" + a.size());
                    System.out.println("Map size" + map.size());

                    OperationBinding ob =
                        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
                    ob.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);

                    //    }

                }

                rw.closeRowSetIterator();

            }
        }
        if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
            pagebindmatchradio.getValue().equals("O")) // D- all sub group O  Immediate sub group
        {
            System.out.println("check box value" + pagebindmatchradio.getValue());
            System.out.println("group name---" + pagebindgroupname.getValue());
            System.out.println("group id---" + pagebindnewgrpid.getValue());
            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            ViewObjectImpl vo = am.getLovIncludeAllChild1();
            //ViewObjectImpl vo1=(ViewObjectImpl)am.findViewObject("LOVChkGrpParent1");
            vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.setNamedWhereClauseParam("bindgrpid", pagebindnewgrpid.getValue().toString());
            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("bindslocid",
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.executeQuery();
            System.out.println("no. of rows---" + vo.getEstimatedRowCount());
            if (vo.getEstimatedRowCount() > 0) {
                System.out.println("arr  D a " + a);
                a.removeAll(a);
                System.out.println("arr after remove D a " + a);
                RowSetIterator rw = vo.createRowSetIterator("abc");
                while (rw.hasNext()) {
                    Row vorow = rw.next();
                    vorow.getAttribute("GrpNm");
                    vorow.getAttribute("GrpId");
                    System.out.println("group name---" + vorow.getAttribute("GrpNm"));
                    System.out.println("group id---" + vorow.getAttribute("GrpId"));
                    //System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());

                    if (a.contains(vorow.getAttribute("GrpNm"))) {
                        System.out.println("group name already exist");

                    } else {
                        a.add(vorow.getAttribute("GrpNm").toString());
                        map.put(vorow.getAttribute("GrpNm").toString(), vorow.getAttribute("GrpId").toString());
                        this.size = a.size();
                        System.out.println("Array List" + a.size());
                        System.out.println("Map size" + map.size());

                        OperationBinding ob =
                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
                        ob.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);

                    }

                }

                rw.closeRowSetIterator();

            }
        }

    }

    public void setPagebindincludeallchk(RichSelectBooleanCheckbox pagebindincludeallchk) {
        this.pagebindincludeallchk = pagebindincludeallchk;
    }

    public RichSelectBooleanCheckbox getPagebindincludeallchk() {
        return pagebindincludeallchk;
    }

    public void valuechangeallchild(ValueChangeEvent valueChangeEvent) {
        if (pagebindgroupname.getValue() != null && pagebindnewgrpid.getValue() != null &&
            pagebindincludeallchk.getValue().toString() != "false") {
            System.out.println("check box value" + pagebindincludeallchk.getValue());
            System.out.println("group name---" + pagebindgroupname.getValue());
            System.out.println("group id---" + pagebindnewgrpid.getValue());
            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            ViewObjectImpl vo = am.getLovIncludeAllChild1();
            //ViewObjectImpl vo1=(ViewObjectImpl)am.findViewObject("LOVChkGrpParent1");
            vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.setNamedWhereClauseParam("bindgrpid", pagebindnewgrpid.getValue().toString());
            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("bindslocid",
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.executeQuery();
            System.out.println("no. of rows---" + vo.getEstimatedRowCount());
            if (vo.getEstimatedRowCount() > 0) {
                RowSetIterator rw = vo.createRowSetIterator("abc");
                while (rw.hasNext()) {
                    Row vorow = rw.next();
                    vorow.getAttribute("GrpNm");
                    vorow.getAttribute("GrpId");
                    System.out.println("group name---" + vorow.getAttribute("GrpNm"));
                    System.out.println("group id---" + vorow.getAttribute("GrpId"));
                    //System.out.println("GROUP NAME" + pagebindgroupname.getValue().toString());

                    if (a.contains(vorow.getAttribute("GrpNm"))) {
                        System.out.println("group name already exist");

                    } else {
                        a.add(vorow.getAttribute("GrpNm").toString());
                        map.put(vorow.getAttribute("GrpNm").toString(), vorow.getAttribute("GrpId").toString());
                        this.size = a.size();
                        System.out.println("Array List" + a.size());
                        System.out.println("Map size" + map.size());

                        OperationBinding ob =
                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialgroupNamAm");
                        ob.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebindgroupname);

                    }

                }

                rw.closeRowSetIterator();

            }
        }


    }

    public void treeselectionlistner(SelectionEvent selectionEvent) {
        String adfSelectionListener = "#{bindings.AppGrp1.treeModel.makeCurrent}";

        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();
        MethodExpression me = null;
        me = exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class, new Class[] {
                                                SelectionEvent.class });
        me.invoke(elCtx, new Object[] { selectionEvent });

        RichTreeTable tree = (RichTreeTable) selectionEvent.getSource();
        TreeModel model = (TreeModel) tree.getValue();
        //get selected nodes
        RowKeySet rowKeySet = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeySet.iterator();
        while (rksIterator.hasNext()) {
            List key = (List) rksIterator.next();

            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding) ((CollectionModel) tree.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();
            System.out.println("row: " + rw.getAttribute("GrpNm")); // You can get any attribute
            System.out.println("row: " + rw.getAttribute("GrpId"));
            transtreegrpid = rw.getAttribute("GrpId").toString();
            System.out.println("View Object name---->" + nodeBinding.getViewObject().getName());

            SearchItemAMImpl am = (SearchItemAMImpl) resolvElDC("SearchItemAMDataControl");
            //        ViewObjectImpl vo=am.getLOVDrillAtttype1();
            //            System.out.println("before"+vo.getEstimatedRowCount());
            //        vo.setNamedWhereClauseParam("bindhoorgid", null);
            //        vo.setNamedWhereClauseParam("bindcldid", null);
            //        vo.setNamedWhereClauseParam("bindgrpId", null);
            //        vo.setNamedWhereClauseParam("bindslocid", null);
            //        vo.executeQuery();
            //            vo.setNamedWhereClauseParam("bindhoorgid", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            //        vo.setNamedWhereClauseParam("bindcldid", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            //        vo.setNamedWhereClauseParam("bindgrpId", rw.getAttribute("GrpId"));
            //        vo.setNamedWhereClauseParam("bindslocid", Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            //            vo.executeQuery();
            //            System.out.println("No. Of ROWS="+vo.getEstimatedRowCount());
            // AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebinddrillattrtype);

            //  System.out.println("before"+am.getLOVDrillAtttype1().getEstimatedRowCount());
            ViewObjectImpl vo1 = am.getDummyDrillAtt1();
            vo1.getCurrentRow().setAttribute("transgrpid", rw.getAttribute("GrpId"));
            // System.out.println("to check whether getter runs or not");
            //vo1.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebinddrillattrtype);
            System.out.println("arraylist size" + this.c.size());
            System.out.println("HashMap size" + this.drillnewmap.size());
            // c.clear();
            // drillnewmap.clear();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelscrollfortreerefresh);

            //            for(int i=0;i<this.c.size();i++)
            //            {
            //                this.c.remove(i);
            //            }
            System.out.println("arraylist size after" + this.c.size());
            System.out.println("HashMap size after" + this.drillnewmap.size());
            //        for(int i=0;i<this.drillnewmap.size();i++)
            //        {
            //            this.drillnewmap.remove(i);
            //        }
            //       // am.getLOVDrillAtttype1().executeQuery();
            //        System.out.println("after"+am.getLOVDrillAtttype1().getEstimatedRowCount());
            //            am.getDummyDrillAtt1().executeQuery();

        }
    }

    public void setGroupTree(RichTreeTable groupTree) {
        this.groupTree = groupTree;
    }

    public RichTreeTable getGroupTree() {
        return groupTree;
    }

    public void setPagebindmatchradio(RichSelectOneRadio pagebindmatchradio) {
        this.pagebindmatchradio = pagebindmatchradio;
    }

    public RichSelectOneRadio getPagebindmatchradio() {
        return pagebindmatchradio;
    }

    public void setPagebinddrillattrid(RichOutputText pagebinddrillattrid) {
        this.pagebinddrillattrid = pagebinddrillattrid;
    }

    public RichOutputText getPagebinddrillattrid() {
        return pagebinddrillattrid;
    }

    public void adddrillattribute(ActionEvent actionEvent) {
        if (pagebinddrillattrname.getValue() != null && pagebinddrillattrid.getValue() != null) {


            System.out.println("GROUP NAME" + pagebinddrillattrname.getValue().toString());
            c.add(pagebinddrillattrname.getValue().toString());
            drillnewmap.put(pagebinddrillattrname.getValue().toString(), pagebinddrillattrid.getValue().toString());
            this.derillnewsize = c.size();
            System.out.println("Array List" + c.size());
            System.out.println("Map size" + drillnewmap.size());
            this.pagebinddrillattrname.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialdrillattributename");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.pagebinddrillattrname);


        }
    }

    public void setPagebinddrillattrname(RichInputListOfValues pagebinddrillattrname) {
        this.pagebinddrillattrname = pagebinddrillattrname;
    }

    public RichInputListOfValues getPagebinddrillattrname() {
        return pagebinddrillattrname;
    }

    public void setPagebinddrillattrtype(RichInputListOfValues pagebinddrillattrtype) {
        this.pagebinddrillattrtype = pagebinddrillattrtype;
    }

    public RichInputListOfValues getPagebinddrillattrtype() {
        return pagebinddrillattrtype;
    }

    public void deletedrillattrname(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        System.out.println("Group Name to delete1=" + ob.getAttributes().get("newgrpNm"));
        String grpName = (String) ob.getAttributes().get("newgrpNm");
        System.out.println("Group Name to delete=" + grpName);

        this.c.remove(grpName);
        this.drillnewmap.remove(grpName);
        System.out.println(drillnewmap.size());
        this.derillnewsize = c.size();
    }

    public void searchdrillattr(ActionEvent actionEvent) {
        if (transtreegrpid != null) {
            System.out.println("Attribute Id:--" + transtreegrpid);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchdrillGrpAttrId");
            ob.getParamsMap().put("grpId", transtreegrpid);

            ob.getParamsMap().put("drillattrname", c);
            ob.getParamsMap().put("attrId", drillnewmap);
            ob.execute();
        }
    }

    public void setPanelscrollfortreerefresh(RichPanelGroupLayout panelscrollfortreerefresh) {
        this.panelscrollfortreerefresh = panelscrollfortreerefresh;
    }

    public RichPanelGroupLayout getPanelscrollfortreerefresh() {
        return panelscrollfortreerefresh;
    }

    public void validatedrillattrname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String st = (String) object;

            if (c.size() != 0) {
                for (int i = 0; i < c.size(); i++) {
                    if (st.equals(c.get(i).toString())) {
//                        Duplicate Attribute Name not allowed
                        String s=ADFModelUtils.resolvRsrc("['MSG.1636']");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                     s,
                                                                      null));

                    }


                }
            }
        }


    }

    public void clearAllMatrixGrp(ActionEvent actionEvent) {
        System.out.println(map + "clear all group O a " + a);
        System.out.println(map + "arr O d " + d);
        a.removeAll(a);
        d.removeAll(d);
        map.clear();
        System.out.println(map + "clear all group O  after remobea " + a);
        System.out.println(map + "arr clear all group O after remove O d " + d);
    }

    public void clearAllMatrixAttribute(ActionEvent actionEvent) {

        System.out.println(newmap + "arr cattributeO d " + b);
        b.removeAll(b);
        newmap.clear();
        System.out.println(newmap + "arr clear all attribute O after remove O d " + b);
    }

    public void clearAllDrillAttribute(ActionEvent actionEvent) {
        System.out.println("arraylist size" + this.c.size());
        System.out.println("HashMap size" + this.drillnewmap.size());
        c.clear();
        drillnewmap.clear();
        System.out.println("arraylist size" + this.c.size());
        System.out.println("HashMap size" + this.drillnewmap.size());
    }
}
