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
package esa.egos.simsat.mmi;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import esa.egos.simsat.mmi.interfaces.IKernelFileBrowserDialog;

/**
 * The main plugin class to be used in the desktop.
 */
public class SimsatMmiPlugin extends AbstractUIPlugin  
{
	
    // The shared instance.
    private static SimsatMmiPlugin plugin;
    
    private IKernelFileBrowserDialog kernelFileBrowser;

  
     
     /**
     * The constructor.
     */
    public SimsatMmiPlugin()
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
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception
    {
    	
        super.stop(context);
        
        /*
        try
        {
            IEclipsePreferences preferences = new ConfigurationScope()
                    .getNode(getPluginId());
            preferences.flush();
        }
        catch (BackingStoreException e)
        {
            e.printStackTrace();
        }
        */
        plugin = null;
        
        
    }

    /**
     * Returns the shared instance.
     */
    public static SimsatMmiPlugin getDefault()
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
        return AbstractUIPlugin.imageDescriptorFromPlugin(SimsatMmiPlugin.getPluginId(), path);
    }
    
 /* 
     
     * Sends a message of severity error to the MessageHandling native
     * component, if loaded.
     
    private void logError(String message)
    {
   }

    
     * Sends a message of severity warning to the MessageHandling native
     * component, if loaded.
     
    private void logWarning(String message, Throwable x)
    {
   }
    
    
     * Sends a message of severity error to the MessageHandling native
     * component, if loaded.
     
    private void logInfo(String message)
    {
   }
    
 */
  
  
  
  
    /**
     * Convenience function - return the ID string for this plugin
     */
    static public String getPluginId()
    {
        return SimsatMmiPlugin.getDefault().getBundle().getSymbolicName();
    }
    

    public IKernelFileBrowserDialog getKernelFileBrowser()
    {
        return this.kernelFileBrowser;
    }

    public void setKernelFileBrowser(IKernelFileBrowserDialog kernelFileBrowser)
    {
        this.kernelFileBrowser = kernelFileBrowser;
    }



}

/*
 * CVS Change Log
 * 
 * $Log: not supported by cvs2svn $
 * Revision 1.17  2006/08/24 22:32:34  hboyer
 * removed old infrastructure
 *
 * Revision 1.16  2006/07/16 14:08:19  hboyer
 * removed service manager
 *
 * Revision 1.15  2006/07/07 19:31:24  hboyer
 * added early browser initialisation
 *
 * Revision 1.14  2006/07/06 21:25:04  hboyer
 * removed MMI framework
 *
 * Revision 1.13  2006/06/19 21:20:11  hboyer
 * removed bad imports
 *
 * Revision 1.12  2006/05/23 16:55:49  mb
 * Changes should solve Issue #289, application does not close when pressing CANCEL in the exit dialog
 *
 * Revision 1.11  2006/05/22 15:42:28  mb
 * A bit more of cleaning up
 *
 * Revision 1.10  2006/05/22 15:29:59  mb
 * Changes should solve Issue #292
 *
 * Revision 1.9  2006/05/15 11:23:16  mb
 * removed ConfigurationFileManager and its references (replaced by preferences)
 * clean-up comments and tasks left pending
 *
 * Revision 1.8  2006/05/11 09:14:07  hboyer
 * Extracting actions from code into plugin.xml. Making the simulation manager a global variable
 *
 * Revision 1.7  2006/05/10 10:11:25  mb
 * Deleted the commented function setUpRepository. This function is now in the MmiResourceLocator
 *
 * Revision 1.6  2006/05/09 11:02:46  alp
 * clean-up for INTERNAL REVIEW
 *
 * Revision 1.5  2006/05/03 12:58:40  alp
 * Added SAVE_AND_RESTORE in the general preferences
 * this allow to enable/disable the saving and restoring the
 * user setting on the SIMSAT MMI perspective
 *
 * Revision 1.4  2006/05/03 11:54:26  alp
 * cleanup and formatting adding some comments and remove some unused code
 * Revision 1.3 2006/04/06 12:11:02 alp clean-up
 * 
 * 
 */