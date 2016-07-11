/*******************************************************************************
 * Copyright (c) 2005, 2016 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The Eclipse Foundation - initial API and implementation
 *     European Space Agency - contributions
 *******************************************************************************/
package esa.egos.simsat.common.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;

import esa.egos.simsat.common.CommonPlugin;

/**
 * @author Jean Schuetz
 */

public class ActionFacility
{
    private static Map<String, IAction> registeredActions = new HashMap<String, IAction>();

    private static Hashtable<String, ActionFacility.ContributionRecord> actionStore = new Hashtable<String, ActionFacility.ContributionRecord>();


    public static void registerAction(String actionID, IAction action)
    {
        assert action != null : "IAction instance is null";

        registeredActions.put(actionID, action);
    }

    public static IAction getActionByID(String actionID)
    {
        if (actionID == null || actionID.length() == 0)
        {
            return null;
        }

        Object foundObject = null;
        IAction result = (IAction) registeredActions.get(actionID);

        if (result != null)
        {
            return result;
        }

        try
        {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            // IActionSetDescriptor[] allActions = ((WorkbenchPage)
            // page).getActionSets();
            IContributionItem[] contrib_a = getRootMenuManager(page).getItems();
            for (int i = 0; i < contrib_a.length; i++)
            {
                // Log.out(contrib_a[i].getId());
                Object r = ActionFacility.trverseConributionTree(contrib_a[i], actionID);
                if (r != null) foundObject = ((ActionContributionItem) r).getAction();
            }
            result = ((IAction) foundObject);
        }

        catch (Exception e)
        {
            String msg = "Exception while invoking getActionByID() for the ActionID " + actionID;
            CommonPlugin.getDefault().logError(msg, e);
        }

        return result;
    }

    public static ActionContributionItem getActionContributionItemFromMenubar(IWorkbenchPage page, String actionID)
    {
        if (actionID == null || actionID.length() == 0)
        {
            return null;
        }

        Object foundObject = null;
        ActionContributionItem result = null;
        try
        {
            IContributionItem[] contrib_a = getRootMenuManager(page).getItems();
            for (int i = 0; i < contrib_a.length; i++)
            {
                Object r = ActionFacility.trverseConributionTree(contrib_a[i], actionID);
                if (r != null) foundObject = r;
            }
            result = (ActionContributionItem) foundObject;
        }

        catch (Exception e)
        {
            String msg = "Exception while invoking getActionContributionItem() for the ActionID " + actionID;
            CommonPlugin.getDefault().logError(msg, e);
        }

        return result;
    }

    public static ActionContributionItem getActionContributionItemFromToolbar(IWorkbenchPage page, String actionID)
    {
        if (actionID == null || actionID.length() == 0)
        {
            return null;
        }

        Object foundObject = null;
        ActionContributionItem result = null;
        try
        {
            IContributionItem[] contrib_a = {};
            IActionBars actionBars = getActionBars(page);
            if (actionBars instanceof IActionBars2)
            {
                contrib_a = ((IActionBars2) actionBars).getCoolBarManager().getItems();
            }
            else
            {
                contrib_a = actionBars.getToolBarManager().getItems();
            }
            for (int i = 0; i < contrib_a.length; i++)
            {
                Object r = ActionFacility.trverseConributionTree(contrib_a[i], actionID);
                if (r != null) foundObject = r;
            }
            result = (ActionContributionItem) foundObject;
        }

        catch (Exception e)
        {
            String msg = "Exception while invoking getActionContributionItemFromToolbar() for the ActionID "
                         + actionID;
            CommonPlugin.getDefault().logError(msg, e);
        }

        return result;
    }

    private static Object trverseConributionTree(IContributionItem ci, String ID)
    {
        Object result = null;

        try
        {
            if (ci instanceof IContributionManager)
            {
                IContributionManager icm = (IContributionManager) ci;
                IContributionItem[] ci_Childs = icm.getItems();
                for (int i = 0; i < ci_Childs.length; i++)
                {
                    // Log.out(ci_Childs[i].getId());
                    Object r = ActionFacility.trverseConributionTree(ci_Childs[i], ID);
                    if (r != null) result = r;
                }
            }
            else if (ci instanceof SubContributionItem)
            {
                SubContributionItem sci = (SubContributionItem) ci;
                IContributionItem ci_Child = sci.getInnerItem();
                // Log.out(ci_Child.getId());
                Object r = ActionFacility.trverseConributionTree(ci_Child, ID);
                if (r != null) result = r;
            }
            else if (ci instanceof ActionContributionItem)
            {
                ActionContributionItem aci = (ActionContributionItem) ci;
                // Log.out(aci.getId());
                if (aci.getId() != null && aci.getId().compareTo(ID) == 0)
                {
                    result = aci;
                }
            }
            else if (ci instanceof ToolBarContributionItem)
            {
                ToolBarContributionItem tbci = (ToolBarContributionItem) ci;
                IContributionManager icm = tbci.getToolBarManager();
                if (icm != null)
                {
                    IContributionItem[] ci_Childs = icm.getItems();
                    for (int i = 0; i < ci_Childs.length; i++)
                    {
                        // Log.out(ci_Childs[i].getId());
                        Object r = ActionFacility.trverseConributionTree(ci_Childs[i], ID);
                        if (r != null) result = r;
                    }
                }
            }
        }
        catch (RuntimeException e)
        {
            String msg = "Exception while invoking trverseConributionTree() for the IContributionItem " + ci;
            CommonPlugin.getDefault().logError(msg, e);
        }

        return result;
    }

    /**
     * Sets the visibility of an arbitrary Action.
     * 
     * @param page
     *            the {@link IWorkbenchPage} containing the Action
     * @param actionID
     *            a {@link String} containing the ID of the Action
     * @param visible
     *            a boolean specifying the visibility
     * @author Jean Schuetz
     */
    public static void setActionVisible(IWorkbenchPage page, String actionID, boolean visible)
    {
        if (actionID == null || actionID.length() == 0)
        {
            return;
        }

        ActionContributionItem actionContributionItem = getActionContributionItemFromMenubar(page, actionID);

        if (actionContributionItem != null && actionContributionItem.isVisible() != visible)
        {
            actionContributionItem.setVisible(visible);
        }

        // If the active part is an editor and this editor has editor Actions,
        // we need extra handling here because of a nasty eclipse bug. If an
        // editor has actions on the toolbar, they can not be set invisible if
        // the associated editor is in focus no matter if the editor area is
        // visible or not.
        // To overcome this there is no other way than removing the actions from
        // the toolbar, storing them temporarily until they are needed again.

        try
        {
            if (visible)
            {
                // restore action
                ContributionRecord record = actionStore.remove(page.hashCode() + actionID);
                if (record != null)
                {
                    record.contMangr.add(record.actionContributionItem);
                    record.actionContributionItem.setVisible(true);
                    IEditorPart activeEditor = page.getActiveEditor();
                    if (activeEditor != null)
                    {
                        activeEditor.setFocus();
                    }
                }
            }
            else
            {
                actionContributionItem = getActionContributionItemFromToolbar(page, actionID);

                if (actionContributionItem != null && actionContributionItem.isVisible() == true)
                {
                    actionContributionItem.setVisible(false);
                    IContributionManager contMangr = actionContributionItem.getParent();
                    ContributionRecord contribRecord = new ActionFacility.ContributionRecord();
                    contribRecord.actionContributionItem = actionContributionItem;
                    contribRecord.contMangr = contMangr;
                    contMangr.remove(actionContributionItem);
                    actionStore.put(page.hashCode() + actionID, contribRecord);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        IActionBars actionBars = getActionBars(page);
        actionBars.updateActionBars();
    }

    // former way of getting hands on the action bars. This method has the
    // disadvantage of needing access to restricted internal eclipse API.
    private static IActionBars getActionBars(IWorkbenchPage page)
    {
        IActionBars result = ((WorkbenchPage) page).getActionBars();
        return result;
    }

    /**
     * Utility method that gets the top level MenuManager of the menubar in the
     * specified page.
     * 
     * @param page
     *            a IWorkbenchPage
     * @return the top level MenuManager of the menubar
     */
    private static MenuManager getRootMenuManager(IWorkbenchPage page)
    {
        MenuManager result = null;

        // get the root MenuBar from the shell
        Menu menuBar = page.getWorkbenchWindow().getShell().getMenuBar();
        // get the 1st level menu items (normally: file, edit, view, tools, ...)
        MenuItem[] menuItems = menuBar.getItems();
        for (int i = 0; i < menuItems.length; i++)
        {
            Object data = menuItems[i].getData();
            // if the current MenuItem is a sub menu then the data field of the
            // menuItem normally contains the associated MenuManager of the sub
            // menu
            if (data instanceof MenuManager)
            {
                try
                {
                    // each MenuManager has a link to its parent MenuManager,
                    // which is in our case the top level MenuManager.
                    result = (MenuManager) ((MenuManager) data).getParent();
                    if (result != null) break;
                }
                catch (Exception e)
                {
                    // parent is not a MenuManager, so continue looping
                }
            }
        }
        return result;
    }


    static class ContributionRecord
    {
        ActionContributionItem actionContributionItem = null;

        IContributionManager contMangr = null;
    }
}

// -----------------------------------------------------------------------------
// $Log$
// Revision 1.18  2009/03/03 16:58:10  stp
// Fixed deprecation warnings.
//
// Revision 1.17  2008/03/13 17:58:08  jsc
// fixed minor bug. SPR-535
//
// Revision 1.16 2008/03/13 15:46:32 jsc
// axtended setActionVisible() to overcome the nasty eclipse bug with the editor
// actions.
//
// Revision 1.15 2007/12/19 17:25:24 jsc
// added some magic
//
// Revision 1.14 2007/11/15 14:52:25 mrc
// Removed compilation warnings.
//
// Revision 1.13 2007/11/15 10:36:58 mrc
// Removed debug prints.
//
// Revision 1.12 2007/06/04 14:50:13 stp
// Updated the action facility to consult IActions registered in a map in
// addition to the ones accesible via menus.
//
// Revision 1.11 2007/05/07 08:42:47 jsc
// minor update
//
// Revision 1.10 2007/01/08 18:11:05 jsc
// added code to also consider actions on the toolbar. Modified the code to use
// API that is more eclipse compliant.
//
// Revision 1.9 2007/01/08 09:50:28 jsc
// added setActionVisible() method
//
// Revision 1.8 2006/11/23 09:53:15 jsc
// minor change
//
// Revision 1.7 2006/11/22 16:53:37 jsc
// minor change
//
// Revision 1.6 2006/09/04 21:06:53 jsc
// minor change
//
// Revision 1.5 2006/07/07 14:03:55 jsc
// minor update
//
// Revision 1.4 2006/06/01 11:47:12 hboyer
// removed an unused import
//
// Revision 1.3 2006/05/24 14:49:09 hboyer
// commented out unused code
//
// Revision 1.2 2006/05/24 11:22:01 jsc
// performed minor updates
//
// Revision 1.1 2006/05/19 17:59:02 jsc
// initial Version
//
//
// Revision 1.0 19.05.2006 10:08:17 jsc
// Initial revision
