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
package esa.egos.simsat.mmi.simulationTree.properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import esa.egos.simsat.common.ui.dialogs.ListBucketComposite;
import esa.egos.simsat.common.util.ActionFacility;
import esa.egos.simsat.mmi.*;
import esa.egos.simsat.mmi.interfaces.IKernelFileBrowserDialog;

/**
 * @author H Boyer
 */
public class CommaSeparatedValuesDialog extends Dialog
{

	String[] values;

	/**
	 * the text for the buttons of the ListBucketComposite
	 */
	private String[] buttons = { "&Add...", "Add &File...", "&Remove" };

	/**
	 * The composite of this Dialog
	 */
	private ListBucketComposite listBucketComposite;

	CommaSeparatedValuesDialog(Shell parentShell, String[] values)
	{
		super(parentShell);
		this.values = values;
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
	}

	protected Control createDialogArea(Composite parent)
	{
		// this.setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER |
		// SWT.APPLICATION_MODAL | SWT.RESIZE);
		Composite userArea = (Composite) super.createDialogArea(parent);
		getShell().setText("Multi-String Editor Dialog");
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.minimumHeight = 300;
		gridData.minimumWidth = 600;
		IAction[] actions = createActions(parent);

		listBucketComposite = new ListBucketComposite(userArea, SWT.NONE,
				buttons, actions, true);
		listBucketComposite.setLayoutData(gridData);
		listBucketComposite.setLabel("Elements");

		for (int i = 0; i < values.length; i++)
		{
			listBucketComposite.addItem(null, null, values[i], true);
		}

		/*
		 * GridData gridData2 = new org.eclipse.swt.layout.GridData();
		 * gridData2.grabExcessHorizontalSpace = true;
		 * gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.END;
		 * gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		 * 
		 * TimeRangeSelectorComposite timeRange = new
		 * TimeRangeSelectorComposite(userArea, SWT.NONE, "Simulation Time
		 * Range", new TimePeriodValidator()); // timeRange.setLabel("Time
		 * Range"); timeRange.setLayoutData(gridData2);
		 */
		userArea.redraw();
		userArea.update();

		return userArea;
	}

	protected void okPressed()
	{
		values = listBucketComposite.getItemLabels();

		super.okPressed();
	}

	public String[] getValues()
	{
		if (values == null)
		{
			values = new String[] {};
		}
		return values;
	}

	/**
	 * Create Dialog actions
	 * 
	 */
	private IAction[] createActions(final Composite parent)
	{
		Action[] act = new Action[buttons.length];

		act[0] = new Action()
		{
			public void run()
			{
				final InputDialog inputDialog = new InputDialog(listBucketComposite
						.getShell(), "Add..", "Add a string", null, null);
				
				if (Window.OK == inputDialog.open())
				{
					final String theString = inputDialog.getValue();

					listBucketComposite.addItem(theString, null, theString, true);
				}
			}
		};
		act[1] = new Action()
		{
			public void run()
			{
				String fileName = null;

				IAction act = ActionFacility
						.getActionByID("esa.egos.simsat.mmi.kernelfilebrowser.openDialogueAction");

				if (act != null)
				{
					// open the KernelFileBrowser
					act.run();

					final IKernelFileBrowserDialog fileDialog = SimsatMmiPlugin
							.getDefault().getKernelFileBrowser();

					if (fileDialog != null)
					{
						// get the file name
						fileName = fileDialog.getSelectedFile();
						
                        if ((fileName!=null) && (fileName.length()>0))
                        {
                            listBucketComposite.addItem(fileName, null, fileName, true);                            
                        }
					}
				}
			}
		};
		
		act[2] = new Action()
		{
			public void run()
			{
				listBucketComposite.removeSelectedItems();
			}
		};

		return act;
	}

}

// -----------------------------------------------------------------------------
// $Log: not supported by cvs2svn $
// Revision 1.10  2010-01-19 12:02:33  stp
// createDialogArea() creates a ListBucketComposite with in-place editing facility.
//
// Revision 1.9  2009-10-26 17:14:59  stp
// Fixed dialogue behaviour when canceling 'Add...' action.
//
// Revision 1.8  2008/01/15 14:39:55  mrc
// Improved error handling.
//
// Revision 1.7  2007/04/26 16:45:56  hboyer
// Format and header changes
//
