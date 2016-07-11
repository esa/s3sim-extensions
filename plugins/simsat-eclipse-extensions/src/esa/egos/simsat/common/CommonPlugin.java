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
package esa.egos.simsat.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class CommonPlugin extends AbstractUIPlugin
{
    /** The Plugin ID.  */
    private static final String pluginID = "esa.egos.simsat.common";
    
    /** The shared instance. */
    private static CommonPlugin plugin;

    /** Shutdown Listener List */
    private Collection<IMMIShutdownListener> shutdownListeners = new ArrayList<IMMIShutdownListener>();

    /**
     * The constructor.
     */
    public CommonPlugin()
    {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
    }

    /**
     * Controlled shutdown support to allow shutdown stages
     * 
     * @param shutdownStage
     *            int stage of the shutdown process
     */
    public void shutdownMMI(int shutdownStage)
    {
        IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        if (activeWindow != null)
        {
            MmiShutdownEvent shutdownRq = new MmiShutdownEvent(this, shutdownStage);

            Iterator<IMMIShutdownListener> iter = shutdownListeners.iterator();
            while (iter.hasNext())
            {
                IMMIShutdownListener item = (IMMIShutdownListener) iter.next();
                item.mmiShutdown(shutdownRq);
            }
        }
    }

    /**
     * Add a shutdown listener
     * 
     * @param listener
     *            IFrameworkShutdownListener
     */
    public int addShutdownListener(IMMIShutdownListener listener)
    {
        if (!shutdownListeners.contains(listener))
        {
            shutdownListeners.add(listener);
        }
        return shutdownListeners.size();
    }

    /**
     * Remove the shutdown listener
     * 
     * @param listener
     *            IFrameworkShutdownListener
     */
    public void removeShutdownListener(IMMIShutdownListener listener)
    {
        shutdownListeners.remove(listener);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception
    {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static CommonPlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns the ID.
     */
    public static String getPluginID()
    {
        return pluginID;
    }

    /**
     * Issue a warning message to the Eclipse Error Log
     * 
     * @param message:
     *            The message to be logged.
     * @param e:
     *            An optional java.lang.Exception. This parameter may be null.
     * @author Jean Schuetz
     */
    public void logWarning(String message, Exception e)
    {
        Status status = new Status(IStatus.WARNING, this.getBundle().getSymbolicName(), IStatus.OK, message, e);
        this.getLog().log(status);
    }

    /**
     * Issue an Error message to the Eclipse Error Log
     * 
     * @param message:
     *            The message to be logged.
     * @param e:
     *            An optional java.lang.Exception. This parameter may be null.
     * @author Jean Schuetz
     */
    public void logError(String message, Exception e)
    {
        Status status = new Status(IStatus.ERROR, this.getBundle().getSymbolicName(), IStatus.OK, message, e);
        this.getLog().log(status);
    }
}

/*
 * CVS Change Log
 * 
 * $Log$
 * Revision 1.12  2009/09/07 12:10:14  jco
 * MAINT_MERGE_4_0_3_i1
 *
 * Revision 1.11.4.2  2009/08/31 10:09:01  mrc
 * Extended AbstractUIPlugin.
 *
 * Revision 1.11.4.1  2009/08/26 09:35:42  mrc
 * Removed warning.
 *
 * Revision 1.11  2007/11/15 14:52:25  mrc
 * Removed compilation warnings.
 *
 * Revision 1.10  2007/11/15 10:36:04  mrc
 * Implemented Issue #912.
 *
 * Revision 1.9  2007/06/11 08:49:58  jsc
 * added shutdown notification mechanism
 * Revision 1.8 2007/03/06 13:39:30 jsc updated comments
 * 
 * Revision 1.7 2007/01/08 18:11:53 jsc minor change
 * 
 * Revision 1.6 2007/01/08 09:52:37 jsc refactored the source Revision 1.5
 * 2006/12/18 17:40:01 jsc added PerspectiveSwitchAdapter Revision 1.4
 * 2006/12/18 15:07:34 jsc modified perspective registry populator in order to
 * use the information provided in the plugin registry instead of hard coding
 * the two simsat perspectives Revision 1.3 2006/12/04 13:16:48 jsc implemented
 * perspective activate listener to hook on change of perspective (MIE/MMI).
 * 
 * Revision 1.2 2006/05/17 11:23:53 jsc Code reformatted Revision 1.1 2006/04/28
 * 16:41:02 hboyer Added esa.egos.simsat.common plugin and renamed
 * esa.egos.simsat.mmi.core.ui plugin to esa.egos.simsat.common.ui
 * 
 */
