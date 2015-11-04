package misDashboard.view.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;

public class misDashboardbean implements Serializable {


    @SuppressWarnings("compatibility:-2881898721965287986")
    private static final long serialVersionUID = 1L;

    private List<SideBarItem> _sideBarItems;
    private SideBarItem _CASHFLOW = new SideBarItem("Cash Flow", "");
    private SideBarItem _RATIOS = new SideBarItem("Ratios", "");
    private SideBarItem _AGEINGANALYSIS = new SideBarItem("Ageing Analysis", "");
    private SideBarItem _PROFITABILITY = new SideBarItem("Profitability", "");

    private Object _maximizedPanelKey;
    private RichPanelDashboard _dashboard;
    private UIXPanel _sideBarContainer;
    private UIXSwitcher _switcher;

    public misDashboardbean() {

        _sideBarItems = new ArrayList<SideBarItem>(4);
        _sideBarItems.add(_CASHFLOW);
        _sideBarItems.add(_RATIOS);
        _sideBarItems.add(_AGEINGANALYSIS);
        _sideBarItems.add(_PROFITABILITY);
    }

    /**
     * Returns the dashboard.
     * @return the dashboard
     */
    public RichPanelDashboard getDashboard() {
        return _dashboard;
    }

    /**
     * Specifies the dashboard.
     * @param dashboard the dashboard
     */
    public void setDashboard(RichPanelDashboard dashboard) {
        _dashboard = dashboard;
    }

    /**
     * Returns the container for the side bar iterator.
     * @return the container for the side bar iterator
     */
    public UIXPanel getSideBarContainer() {
        return _sideBarContainer;
    }

    /**
     * Specifies the container for the side bar iterator.
     * @param sideBarContainer the container for the side bar iterator
     */
    public void setSideBarContainer(UIXPanel sideBarContainer) {
        _sideBarContainer = sideBarContainer;
    }

    /**
     * Returns the switcher.
     * @return the switcher
     */
    public UIXSwitcher getSwitcher() {
        return _switcher;
    }

    /**
     * Specifies the switcher.
     * @param switcher the switcher
     */
    public void setSwitcher(UIXSwitcher switcher) {
        _switcher = switcher;
    }

    /**
     * Fetches the list of panel data objects.
     * @return a list of PanelData objects
     */
    public List<SideBarItem> getSideBarDataItems() {

        return _sideBarItems;
    }

    /**
     * Handler for component movements into the side bar from the dashboard.
     * @param e the DropEvent for the movement
     * @return the DnDAction that determines whether to redraw the drop target
     */
    public DnDAction handleSidebarDrop(DropEvent e) {
        UIComponent movedComponent = e.getTransferable().getData(DataFlavor.UICOMPONENT_FLAVOR);
        UIComponent movedParent = movedComponent.getParent();

        // Ensure that the drag source is one of the items from the dashboard:
        if (movedParent.equals(_dashboard)) {
            _minimize(movedComponent);
        }

        return DnDAction.NONE; // the client is already updated, so no need to redraw it again

    }

    /**
     * Handler for the re-ordering drop event on the panelDashboard.
     * Change the component order in the component tree and update the side bar with new insertion
     * indexes.
     * This is also the handler for dropping in a minimized side bar item into the dashboard.
     * In that case, the associated panelDashboard child should then be restored.
     * @param e the DropEvent for the dashboard child reordering
     * @return the DnDAction that determines whether to redraw the drop target
     */
    public DnDAction move(DropEvent e) {
        UIComponent dropComponent = e.getDropComponent();
        UIComponent movedComponent = e.getTransferable().getData(DataFlavor.UICOMPONENT_FLAVOR);
        UIComponent movedParent = movedComponent.getParent();

        // Ensure that we are handling the re-order of a direct child of the panelDashboard:
        if (movedParent.equals(dropComponent) && dropComponent.equals(_dashboard)) {
            // Move the already rendered child and redraw the side bar since the insert indexes have
            // changed:
            _moveDashboardChild(e.getDropSiteIndex(), movedComponent.getId());
        } else {
            // This isn't a re-order but rather the user dropped a minimized side bar item into the
            // dashboard, in which case that item should be restored at the specified drop location.
            String panelKey = _getAssociatedPanelKey(movedComponent);
            if (panelKey != null) {
                UIComponent panelBoxToShow = _dashboard.findComponent(panelKey);

                // Make this panelBox rendered:
                panelBoxToShow.setRendered(true);

                int insertIndex = e.getDropSiteIndex();

                // Move the already rendered child and redraw the side bar since the insert indexes have
                // changed and because the side bar minimized states are out of date:
                _moveDashboardChild(insertIndex, panelKey);

                // Let the dashboard know that only the one child should be encoded during the render phase:
                _dashboard.prepareOptimizedEncodingOfInsertedChild(FacesContext.getCurrentInstance(), insertIndex);
            }
        }

        return DnDAction.NONE; // the client is already updated, so no need to redraw it again
    }

    private void _moveDashboardChild(int dropIndex, String movedId) {
        // Build the new list of IDs, placing the moved component at the drop index.
        List<String> reorderedIdList = new ArrayList<String>(_dashboard.getChildCount());
        int index = 0;
        boolean added = false;

        for (UIComponent currChild : _dashboard.getChildren()) {
            if (currChild.isRendered()) {
                if (index == dropIndex) {
                    reorderedIdList.add(movedId);
                    added = true;
                }

                String currId = currChild.getId();
                if (currId.equals(movedId) && index < dropIndex) {
                    // component is moved later, need to shift the index by 1
                    dropIndex++;
                }

                if (!currId.equals(movedId)) {
                    reorderedIdList.add(currId);
                }
                index++;
            }
        }

        if (!added) {
            // Added to the very end:
            reorderedIdList.add(movedId);
        }

        // Apply the change to the component tree immediately:
        ComponentChange change = new ReorderChildrenComponentChange(reorderedIdList);
        change.changeComponent(_dashboard);

        // Add the side bar as a partial target since we need to redraw the state of the side bar items
        // since their insert indexes are changed and possibly because the side bar minimized states are
        // out of date:
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(_sideBarContainer);
    }

    public String getMaximizedKey() {
        if (_maximizedPanelKey == null) {
            return "empty"; // to avoid a FileNotFoundException
        }

        return _maximizedPanelKey.toString();
    }

    public String getMaximizedTitle() {
        Iterator<SideBarItem> i = _sideBarItems.iterator();
        while (i.hasNext()) {
            SideBarItem data = i.next();
            if (_maximizedPanelKey.equals(data.getItemId())) {
                return data.getTitle();
            }
        }
        return null;
    }

    public void sideBarShow(ActionEvent e) {
        if (_maximizedPanelKey == null) {
            // Show non-maximized:
            UIComponent eventComponent = e.getComponent();
            String panelKey = _getAssociatedPanelKey(eventComponent);
            UIComponent panelBoxToShow = _dashboard.findComponent(panelKey);

            // Make this panelBox rendered:
            panelBoxToShow.setRendered(true);

            // Since the dashboard is already shown, let's perform an optimized render so the whole
            // dashboard doesn't have to be re-encoded:
            int insertIndex = 0;
            List<UIComponent> children = _dashboard.getChildren();
            for (UIComponent child : children) {
                if (child.equals(panelBoxToShow)) {
                    // Let the dashboard know that only the one child should be encoded during the render phase:
                    _dashboard.prepareOptimizedEncodingOfInsertedChild(FacesContext.getCurrentInstance(), insertIndex);
                    break;
                }

                if (child.isRendered()) {
                    // Only count rendered children since that's all that the panelDashboard can see:
                    insertIndex++;
                }
            }

            // Add the side bar as a partial target since we need to redraw the state of the side bar item
            // that corresponds to the inserted item:
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.addPartialTarget(_sideBarContainer);
        } else {
            // Show maximized:
            sideBarMaximize(e);
        }
    }

    public void minimize(ActionEvent e) {
        UIComponent eventComponent = e.getComponent();
        String panelKey = _getAssociatedPanelKey(eventComponent);
        UIComponent panelBoxToMinimize = _dashboard.findComponent(panelKey);
        _minimize(panelBoxToMinimize);
    }

    private void _minimize(UIComponent panelBoxToMinimize) {
        // Make this panelBox non-rendered:
        panelBoxToMinimize.setRendered(false);

        // If the dashboard is showing, let's perform an optimized render so the whole dashboard doesn't
        // have to be re-encoded.
        // If the dashboard is hidden (because the panelBox is maximized), we will not do an optimized
        // encode since we need to draw the whole thing.
        if (_maximizedPanelKey == null) {
            int deleteIndex = 0;
            List<UIComponent> children = _dashboard.getChildren();
            for (UIComponent child : children) {
                if (child.equals(panelBoxToMinimize)) {
                    _dashboard.prepareOptimizedEncodingOfDeletedChild(FacesContext.getCurrentInstance(), deleteIndex);
                    break;
                }

                if (child.isRendered()) {
                    // Only count rendered children since that's all that the panelDashboard can see:
                    deleteIndex++;
                }
            }
        }

        RequestContext rc = RequestContext.getCurrentInstance();
        if (_maximizedPanelKey != null) {
            // Exit maximized mode:
            _maximizedPanelKey = null;

            _switcher.setFacetName("restored");
            rc.addPartialTarget(_switcher);
        }

        // Redraw the side bar so that we can update the colors of the opened items:
        rc.addPartialTarget(_sideBarContainer);
    }

    public void sideBarMaximize(ActionEvent e) {
        String panelKey = _getAssociatedPanelKey(e.getComponent());
        _maximizedPanelKey = panelKey;
        UIComponent panelBoxToMaximize = _dashboard.findComponent(panelKey);

        // Make this panelBox rendered:
        panelBoxToMaximize.setRendered(true);

        _switcher.setFacetName("maximized");
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(_switcher);

        // Redraw the side bar so that we can draw update the maximized icons:
        rc.addPartialTarget(_sideBarContainer);
    }

    public void maximize(ActionEvent e) {
        String panelKey = _getAssociatedPanelKey(e.getComponent());
        _maximizedPanelKey = panelKey;
        System.out.println("maximized key value or name is==" + _maximizedPanelKey);

        _switcher.setFacetName("maximized");
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(_switcher);

        // Redraw the side bar so that we can draw update the maximized icons:
        rc.addPartialTarget(_sideBarContainer);
    }

    public void restore(ActionEvent e) {
        _maximizedPanelKey = null;

        _switcher.setFacetName("restored");
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(_switcher);

        // Redraw the side bar so that we can draw update the maximized icons:
        rc.addPartialTarget(_sideBarContainer);
    }

    private String _getAssociatedPanelKey(UIComponent component) {
        Map<String, Object> attrs = component.getAttributes();
        return (String) attrs.get("associatedPanelKey"); // added to the component by the f:attribute tag
    }

    private Object _getAssociatedModeKey(UIComponent component) {
        Map<String, Object> attrs = component.getAttributes();
        return attrs.get("mode"); // added to the component by the f:attribute tag
    }

    /**
     * Represents a single side bar item used for links to panelBoxes into the panelDashboard.
     */
    public class SideBarItem {

        private String _title;
        private String _itemId;
        private String _status;

        public SideBarItem(String title, String status) {

            _title = title;
            _status = status;

            // Generate the ID from the title.
            // The ID uniquely identifies which component in the dashboard that will be rendered or not.
            _itemId = title.replaceAll("[ .]", ""); // strip spaces and dots
            _itemId = _itemId.substring(0, 1).toLowerCase().charAt(0) + _itemId.substring(1);


        }

        /**
         * Retrieves the title of the side bar item.
         * @return the title of the side bar item
         */
        public String getTitle() {
            return _title;
        }

        /**
         * Retrieves the status for the side bar item.
         * @return the status for the side bar item
         */
        public String getStatus() {
            return _status;
        }

        /**
         * Retrieves the ID of the panelDashboard child associated with this side bar item.
         * @return the ID of the panelDashboard child associated with this side bar item
         */
        public String getItemId() {
            return _itemId;
        }

        /**
         * Retrieves whether this panelBox child is rendered in the dashboard.
         * @return true if rendered, false otherwise
         */
        public boolean isPanelBoxRendered() {
            if (_dashboard == null) {
                throw new RuntimeException("Unexpected null dashboard.");
            }
            UIComponent panelBox = _dashboard.findComponent(_itemId);
            if (panelBox == null) {
                throw new RuntimeException("Unexpected null panelBox id=" + _itemId);
            }
            return panelBox.isRendered();
        }

        /**
         * Retrieves the index within the list of rendered dashboard children at which the insert
         * will occur.  Since the insertion placeholder gets added before the insertion occurs on the
         * server, you must specify the location where you are planning to insert the child so that if
         * the user reloads the page, the children will continue to remain displayed in the same order.
         * @return the client rendered index at which the item needs to appear
         */
        public int getIndexIfRendered() {
            int index = 0;
            for (UIComponent child : _dashboard.getChildren()) {
                if (_itemId.equals(child.getId())) {
                    return index;
                } else if (child.isRendered()) {
                    index++;
                }
            }
            throw new RuntimeException("Unable to determine the index if rendered for panelBox id=" + _itemId);
        }

    }
}
