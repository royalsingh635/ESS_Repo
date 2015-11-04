package misRatioAnalysis.view.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.ClickEvent;

import oracle.adf.view.faces.bi.event.ZoomEvent;
import oracle.adf.view.faces.bi.event.graph.SelectionEvent;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.binding.OperationBinding;

import oracle.dss.dataView.Attributes;
import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.dataView.SeriesComponentHandle;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class misRatioAnalysisBean {
    private UIGraph lineGraph;
    private String currFy;
    private String noteMsg;
    private String liquidityRatioComp = "Current Ratio";
    private String activityRatioComp = "Fixed Asset Turnover";
    private String profitRatioComp = "Basic Earning Power Ratio";
    private String fyDesc;
    public String categoryId;
    public int financialYearId;
    private RichShowDetailItem profitTabBinding;

    public misRatioAnalysisBean() {
    }

    public void ratioComponentGraphClick(ClickEvent clickEvent) {

        String val = "";
        ComponentHandle handle = clickEvent.getComponentHandle();
        if (handle instanceof DataComponentHandle) {
            // Get the series attributes
            Attributes[] seriesInfo = ((DataComponentHandle)handle).getSeriesAttributes();

            String data = "";

            if (seriesInfo != null) {
                for (Attributes attrs : seriesInfo) {
                    data += "Series value: " + attrs.getValue(Attributes.LABEL_VALUE);
                    val = attrs.getValue(Attributes.LABEL_VALUE).toString();
                    data += " Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE);
                    data += " Series value id: " + attrs.getValue(Attributes.ID_VALUE);
                    data += " Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE);
                }

            }
        }

        if (val.equalsIgnoreCase("CurrentRatio")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setLiquidityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "CR");
            op.execute();
            setLiquidityRatioComp("Current Ratio");
        } else if (val.equalsIgnoreCase("QuickRatio")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setLiquidityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "QR");
            op.execute();
            setLiquidityRatioComp("Quick Ratio");
        }
    }

    public void ZoomListener(ZoomEvent event) {
        double zoomMin = event.getAxisMin(ZoomEvent.Y1AXIS);
        double zoomMax = event.getAxisMax(ZoomEvent.Y1AXIS);
        int groupStart = event.getAxisStartGroup(ZoomEvent.O1AXIS);
        int groupCount = event.getAxisGroupCount(ZoomEvent.O1AXIS);

    }

    public void ratioComponentGraphSelection(SelectionEvent selectionEvent) {
    }

    public void setLineGraph(UIGraph lineGraph) {
        this.lineGraph = lineGraph;
    }

    public UIGraph getLineGraph() {
        return lineGraph;
    }

    public void activityComponentGraphClick(ClickEvent clickEvent) {
        String val = "";
        ComponentHandle handle = clickEvent.getComponentHandle();
        if (handle instanceof DataComponentHandle) {
            // Get the series attributes
            Attributes[] seriesInfo = ((DataComponentHandle)handle).getSeriesAttributes();

            String data = "";

            if (seriesInfo != null) {
                for (Attributes attrs : seriesInfo) {
                    data += "Series value: " + attrs.getValue(Attributes.LABEL_VALUE);
                    val = attrs.getValue(Attributes.LABEL_VALUE).toString();
                    data += " Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE);
                    data += " Series value id: " + attrs.getValue(Attributes.ID_VALUE);
                    data += " Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE);
                }

            }
        }

        if (val.equalsIgnoreCase("FixedAssetsTurnover")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setActivityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "FT");
            op.execute();
            setActivityRatioComp("Fixed Assets Turnover");
        } else if (val.equalsIgnoreCase("InventoryTurnover")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setActivityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "IT");
            op.execute();
            setActivityRatioComp("Inventory Turnover");
        } else if (val.equalsIgnoreCase("TotalAssetsTurnover")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setActivityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "TT");
            op.execute();
            setActivityRatioComp("Total Assets Turnover");
        }
    }

    public String getNoteMsg() {
        if (noteMsg == null) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy ");
            Date date = new Date();

            noteMsg = "Note: Data Last Updated on ";
            noteMsg += dateFormat.format(date);

        }
        return noteMsg;
    }

    public String getCurrFy() {
        return currFy;
    }

    public void setLiquidityRatioComp(String liquidityRatioComp) {
        this.liquidityRatioComp = liquidityRatioComp;
    }

    public String getLiquidityRatioComp() {
        return liquidityRatioComp;
    }

    public void setActivityRatioComp(String activityRatioComp) {
        this.activityRatioComp = activityRatioComp;
    }

    public String getActivityRatioComp() {
        return activityRatioComp;
    }

    public String getFyDesc() {
        if (fyDesc == null) {

            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getFyDesc");
            op.execute();
            fyDesc = op.getResult().toString();
        }
        return fyDesc;
    }


    public void profitComponentGraphClick(ClickEvent clickEvent) {
        String val = "";
        ComponentHandle componentHandle = clickEvent.getComponentHandle();
        if (componentHandle instanceof DataComponentHandle) {
            Attributes[] seriesInfo = ((DataComponentHandle)componentHandle).getSeriesAttributes();

            String data = "";

            if (seriesInfo != null) {
                for (Attributes attrs : seriesInfo) {
                    data += "Series value: " + attrs.getValue(Attributes.LABEL_VALUE);
                    val = attrs.getValue(Attributes.LABEL_VALUE).toString();
                    data += " Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE);
                    data += " Series value id: " + attrs.getValue(Attributes.ID_VALUE);
                    data += " Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE);
                }

            }
        }
        if (val.equalsIgnoreCase("BasicEarningPowerRatio")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setProfitabilityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "BR");
            op.execute();
            setProfitRatioComp("Basic Earning Power Ratio");
        } else if (val.equalsIgnoreCase("ReturnOnAssets")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setProfitabilityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "RA");
            op.execute();
            setProfitRatioComp("Return On Assets");
        } else if (val.equalsIgnoreCase("ReturnOnEquity")) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setProfitabilityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "RE");
            op.execute();
            setProfitRatioComp("Return On Equity");
        }
    }

    public void setProfitTabBinding(RichShowDetailItem profitTabBinding) {
        this.profitTabBinding = profitTabBinding;
    }

    public RichShowDetailItem getProfitTabBinding() {
        return profitTabBinding;
    }

    public void profitTabDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setProfitabilityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "BR");
            op.execute();
        }
    }

    public void setProfitRatioComp(String profitRatioComp) {
        this.profitRatioComp = profitRatioComp;
    }

    public String getProfitRatioComp() {
        return profitRatioComp;
    }

    public void activityTabDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setActivityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "FT");
            op.execute();
            setActivityRatioComp("Fixed Assets Turnover");
        }
    }

    public void liquidityRatioDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setLiquidityRatioComponent");
            op.getParamsMap().put("p_ratio_id", "CR");
            op.execute();
            setLiquidityRatioComp("Current Ratio");
        }
    }

    public void componentCL(ClickEvent clickEvent) {
        String val = "";
        ComponentHandle componentHandle = clickEvent.getComponentHandle();
        if (componentHandle instanceof DataComponentHandle) {
            Attributes[] seriesInfo = ((DataComponentHandle)componentHandle).getSeriesAttributes();

            String data = "";

            if (seriesInfo != null) {
                for (Attributes attrs : seriesInfo) {
                    data += "Series value: " + attrs.getValue(Attributes.LABEL_VALUE);
                    val = attrs.getValue(Attributes.LABEL_VALUE).toString();
                    data += " Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE);
                    data += " Series value id: " + attrs.getValue(Attributes.ID_VALUE);
                    data += " Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE);
                }
                System.out.println("data " + data);
            }
        }
        if (val != null) {

            String category = val.toUpperCase().trim();
            System.out.println("category " + category);
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getCategoryId1");
            op.getParamsMap().put("catDesc", category);
            op.execute();
            categoryId = (String)op.getResult();
            System.out.println("categoryId " + categoryId);

            OperationBinding op1 =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getFyId");
            op1.execute();
            financialYearId = Integer.parseInt(op1.getResult().toString());
            System.out.println("financialYearId " + financialYearId);
        }
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setFinancialYearId(int financialYearId) {
        this.financialYearId = financialYearId;
    }

    public int getFinancialYearId() {
        return financialYearId;
    }

    public void refreshAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("refresh");
        op.execute();
    }
}

