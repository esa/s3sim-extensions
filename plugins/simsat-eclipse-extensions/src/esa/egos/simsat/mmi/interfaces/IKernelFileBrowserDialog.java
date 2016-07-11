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
package esa.egos.simsat.mmi.interfaces;


/**
 * This interface represents the framework service 
 * '<b>KernelFileBrowserDialog</b>' which is used by other Plugins to open
 * a Kernel File Browse Dialogue in order to select a Kernel-side file. 
 * 
 * MMI Plugins are expected to use this service once the MMI has established
 * a connection to the Kernel. Once the Kernel connection exists clients
 * can open the Kernel File Browser by calling {@link #open() open()}.
 * 
 * @author M. Cardone
 */
public interface IKernelFileBrowserDialog
{
    /**
     * Opens the Kernel File Browser Dialogue and returns the 
     * SWT constant corresponding to the pressed button.
     * 
     * @return  SWT constant for the pressed button (SWT.OK, SWT.CANCEL)
     */
    int open();
    
    /**
     * Returns the selected file path relative to the configurable Kernel File Browser
     * root (the root is normally initialised to the Registry location).
     * 
     * @return  Selected file path
     */
    String getSelectedFile();
    
    /**
     * Set the filter for files.
     * @param forcedExtension the file extension (for instance "xml" or "txt"). Remember do not provide the initial dot "." 
     */
    void forceExtension(String forcedExtension);

}

//-----------------------------------------------------------------------------
// $Log$
// Revision 1.1  2007/01/11 16:50:30  mrc
// Created IKernelFileBrowser interface.
//
//
// Revision 1.0  Jan 11, 2007 1:33:32 PM  mrc
// Initial revision