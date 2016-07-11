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
package esa.egos.simsat.mmi.commander;


import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import esa.egos.simsat.common.CommonPlugin;
import esa.egos.simsat.common.IMMIShutdownListener;
import esa.egos.simsat.common.MmiShutdownEvent;


/**
 * The main plugin class to be used in the desktop.
 */
public class CommanderPlugin extends AbstractUIPlugin implements IMMIShutdownListener
{

    // The shared instance.
    private static CommanderPlugin plugin;
    
    // the MMI exiting status
    public static int MMI_EXITING = 0;


    /**
     * The constructor.
     */
    public CommanderPlugin()
    {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        MMI_EXITING = 0;
        CommonPlugin.getDefault().addShutdownListener(CommanderPlugin.getDefault());
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception
    {
        super.stop(context);

        // Save preferences
        IScopeContext scope = new ConfigurationScope();
        IEclipsePreferences preferences = scope.getNode(getPluginId());
        try
        {
            preferences.flush();
        }
        catch (BackingStoreException e)
        {
            e.printStackTrace();
        }
        CommonPlugin.getDefault().removeShutdownListener(CommanderPlugin.getDefault());
        
        plugin = null;
        MMI_EXITING = 0;
    }

    /**
     * Returns the shared instance.
     */
    public static CommanderPlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return AbstractUIPlugin.imageDescriptorFromPlugin(getPluginId(), path);
    }

    /**
     * Convenience function for getting the plugin's name as a string
     * 
     * @return plugin id
     */
    public static String getPluginId()
    {
        return CommanderPlugin.getDefault().getBundle().getSymbolicName();
    }

    /**
     * Returns the shell where this plugin is runnig. This function will be used
     * to obtain a common shell whenever a new dialog is opened.
     * 
     * @return the shell
     */
    public static Shell getShell()
    {
    	CommanderPlugin plug = CommanderPlugin.getDefault();
        IWorkbench workbench = plug.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        Shell shell = window.getShell();
        return shell;
    }

    public static void logError(String message)
    {
        // MessageHandler.logError(getDefault(), message);
        getDefault().getLog().log(new Status(Status.ERROR, getPluginId(), Status.OK, message, null));
    }

    public static void logError(String message, Exception e)
    {
        // MessageHandler.logError(getDefault(), message);
        getDefault().getLog().log(new Status(Status.ERROR, getPluginId(), Status.OK, message, e));
    }

    public static void logWarning(String message)
    {
        // MessageHandler.logError(getDefault(), message);
        getDefault().getLog().log(new Status(Status.WARNING, getPluginId(), Status.OK, message, null));
    }

    public static void logInfo(String message)
    {
        // MessageHandler.logMessage(getDefault(), message);
        getDefault().getLog().log(new Status(Status.INFO, getPluginId(), Status.OK, message, null));
    }
    
    public static void logException(String message, Throwable e)
    {
        getDefault().getLog().log(new Status(Status.ERROR, getPluginId(), Status.OK, message, e));
    }

    public static void logException(Throwable e)
    {
        String message = "Exception thrown ";
        getDefault().getLog().log(new Status(Status.ERROR, getPluginId(), Status.OK, message, e));
    }
    
    private static void doDisplayErrorBox(String msg)
    {
    	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();       
    	MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
    	box.setText("Error");
    	box.setMessage(msg);
    	box.open();
    }
    
    public static void errorBox(final String msg)
    {
    	Display display = PlatformUI.getWorkbench().getDisplay();
    	
    	if (display.getThread() != Thread.currentThread ())
    	{
    		display.syncExec(new Runnable()
    				{
						public void run() 
						{
							doDisplayErrorBox(msg);
						}
    				});
    	}
    	else
    	{
			doDisplayErrorBox(msg);
    	}
    }

    public void mmiShutdown(MmiShutdownEvent shutdownEvent)
    {
        MMI_EXITING = 1;
    }
}

/*
 * CVS Change Log
 * 
 * $Log: not supported by cvs2svn $
 * Revision 1.16  2007/09/12 11:27:01  stp
 * errorBox() does the real work in a syncExec Runnable, if not run in the UI thread.
 *
 * Revision 1.15  2007/03/30 17:12:39  jsc
 * added logError with Exception parameter
 *
 * Revision 1.14  2007/03/21 15:35:57  hboyer
 * removed deprecated code
 *
 * Revision 1.13  2007/02/15 06:27:28  hboyer
 * Split getShell to find nullException source
 *
 * Revision 1.12  2007/01/11 06:35:59  hboyer
 * new logException that combines logException with the logError.
 *
 * Revision 1.11  2006/12/19 16:51:12  hboyer
 * removed old code
 *
 * Revision 1.10  2006/11/22 14:50:24  jsc
 * removed warnings
 * Revision 1.9 2006/07/31 02:02:52 hboyer
 * removed old framework dependencies
 * 
 * Revision 1.8 2006/07/24 14:57:06 hboyer replaced deprecated code and message
 * handler
 * 
 * Revision 1.7 2006/07/16 14:16:59 hboyer removed old message handler and event
 * manager
 * 
 * Revision 1.6 2006/07/06 21:25:53 hboyer removed MMI framework
 * 
 * Revision 1.5 2006/07/05 15:20:30 hboyer initialised now called from within
 * the object
 * 
 * Revision 1.4 2006/05/09 10:40:54 alp INTERNAL REVIEW added comments
 * formatting removing unused code (there are some points still to be cleaned
 * up)
 * 
 * Revision 1.3 2006/05/03 10:29:49 alp clean-up, formatting and header/footer
 * comments
 * 
 * 
 */