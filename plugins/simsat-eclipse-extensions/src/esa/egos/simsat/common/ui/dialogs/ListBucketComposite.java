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
package esa.egos.simsat.common.ui.dialogs;

import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;



public class ListBucketComposite extends Composite 
{
	private Group bucketGroup = null;
	//private Composite parent;
	
	/**
	 * The default icon for items in the list (can be null)
	 */
	ImageDescriptor defaultImage = null;
	private Table table;
    private Composite composite = null;
    private Button button1 = null;
    private Button button2 = null;
    private Button button3 = null;
    private Button button4 = null;
    private Button[] button = null;
    private String[] buttonLabel = null;
    private IAction[] action = null;
    private boolean isTableEditable = false; 
    private TableEditor editor = null;

	public ListBucketComposite(Composite parent, int style) 
	{
		super(parent, style);
		initialize();
	}
    
    /*
     * Constructor used to create a custom ListBucketComposite
     */
    public ListBucketComposite(Composite parent, int style, String[] buttons, IAction[] actions, boolean isTableEditable) 
    {
        super(parent, style);
        button = new Button[buttons.length];
        buttonLabel = buttons;
        action = actions;
        this.isTableEditable = isTableEditable;
        initialize();
    }
	
    /*
     * Constructor used to create a custom ListBucketComposite
     */
    public ListBucketComposite(Composite parent, int style, String[] buttons, IAction[] actions) 
    {
    	this(parent, style, buttons, actions, false);
    }
	
	
	/**
     * This method initializes this
     * 
     */
    private void initialize() {

		FillLayout fillLayout = new FillLayout();
        fillLayout.spacing = 5;
        fillLayout.marginWidth = 5;
        fillLayout.marginHeight = 5;
        
        this.setLayout(fillLayout);
        createBucketGroup();
        this.setSize(new org.eclipse.swt.graphics.Point(396,167));
        //pack();
	}
	
	/**
	 * Add an element to the list
	 * @param image		The icon for the list (or null)
	 * @param alternateLabel		The text in the list (if null, data.toString is called)
	 * @param data 	The data associated with the selection ()
	 * @param visible  True to add the item to the list by default
	 */
	public void addItem(Object data, Image image, String alternateLabel,  boolean visible)
	{
		String label = alternateLabel;
	
		if(label == null)
		{
			label = data.toString();
		}
		
		TableItem item = new TableItem(table,SWT.NONE);
		item.setText(0,label);
		item.setImage(0, image);
		
		table.getColumn(0).pack();
	}
    
    /**
     * Remove the selected table items.
     */
    public void removeSelectedItems()
    {
    	if (null != editor)
    	{
    		Control oldEditor = editor.getEditor();
    		if (null != oldEditor)
    		{
    			oldEditor.dispose();
    		}
    	}

    	table.remove(table.getSelectionIndices());
    }
    
    /**
     * 
     * @return returns the selcted item in the list
     */
    public Object[] getSelectedItem()
    {
        return table.getSelection();
    }
    
    /**
     * Deselect all table items  
     */
    public void deselectAll()
    {
    	table.deselectAll();
    }
	
    /**
     * Deselect all table items  
     */
    public void setSelection(Set<String> texts)
    {
    	if (texts==null)
    	{
    		return;
    	}
    	
    	for (int i=0; i<table.getItemCount(); i++)
    	{
    		if (texts.contains(table.getItem(i).getText()))
    		{
    			table.select(i);
    		}
    	}
    }
	
	/**
	 * 
	 * @return The labels of the items in the list, or an empty list
	 */
	public String[] getItemLabels()
	{
        String[] labels = null;
        
		TableItem[] items = table.getItems();
        if (items.length > 0)
        {
            labels = new String[items.length];
            for (int i = 0; i < items.length; i++)
            {
                labels[i] = items[i].getText();
            }
        }
        return labels;
	}
	
	public Object[] getItems()
	{
		return table.getItems();
	}

	/**
	 * @return Returns the defaultImage.
	 */
	public ImageDescriptor getDefaultImage() {
		return defaultImage;
	}

	/**
	 * @param defaultImage The defaultImage to set.
	 */
	public void setDefaultImage(ImageDescriptor defaultImage) {
		this.defaultImage = defaultImage;
	}

	 /**
     * This method initializes BucketGroup	
     *
     */
    private void createBucketGroup()
    {
    	GridLayout gridLayout = new GridLayout();
    	gridLayout.numColumns = 2;
    	GridData gridData = new org.eclipse.swt.layout.GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        bucketGroup = new Group(this, SWT.NONE);
        bucketGroup.setText("Text has not been set");
        bucketGroup.setLayout(gridLayout);
        table = new Table(bucketGroup, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
        new TableColumn(table, SWT.NONE);
        table.setLayoutData(gridData);
        createComposite();
        
        if (isTableEditable)
        {
            // Adding an in-place editing facility to the table.
            //[ Adapted from http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/editthetextofaSWTtableiteminplace.htm ]
            editor = new TableEditor(table);
            // The editor must have the same size as the cell and must
            // not be any smaller than 50 pixels.
            editor.horizontalAlignment = SWT.LEFT;
            editor.grabHorizontal = true;
            editor.minimumWidth = 50;
            // editing the first column
            final int EDITABLECOLUMN = 0;

            table.addSelectionListener(new SelectionAdapter()
            {
            	public void widgetSelected(SelectionEvent e)
            	{
            		// Clean up any previous editor control
            		Control oldEditor = editor.getEditor();
            		if (oldEditor != null)
            		{
            			oldEditor.dispose();
            		}

            		// Identify the selected row
            		TableItem item = (TableItem) e.item;
            		if (item == null)
            		{
            			return;
            		}

            		// The control that will be the editor must be a child of the
            		// Table
            		Text newEditor = new Text(table, SWT.NONE);
            		newEditor.setText(item.getText(EDITABLECOLUMN));
            		newEditor.addModifyListener(new ModifyListener()
            		{
            			public void modifyText(ModifyEvent me)
            			{
            				Text text = (Text) editor.getEditor();
            				editor.getItem().setText(EDITABLECOLUMN, text.getText());
            			}
            		});
            		newEditor.selectAll();
            		newEditor.setFocus();
            		editor.setEditor(newEditor, item, EDITABLECOLUMN);
            	}
            });
        }
    }
    
    public void setLabel(String labelName)
    {
    	bucketGroup.setText(labelName);
    }


    /**
     * This method initializes composite	
     *
     */
    private void createComposite()
    {
        GridData gridData1 = new org.eclipse.swt.layout.GridData();
        gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        RowLayout rowLayout = new RowLayout();
        rowLayout.type = org.eclipse.swt.SWT.VERTICAL;
        rowLayout.marginHeight = 5;
        rowLayout.marginWidth = 5;
        rowLayout.fill = true;
        rowLayout.spacing = 5;
        composite = new Composite(bucketGroup, SWT.NONE);
        composite.setLayout(rowLayout);
        composite.setLayoutData(gridData1);
        
        if (button == null)
        {
            button1 = new Button(composite, SWT.NONE);
            button1.setText("&Add...");
            button2 = new Button(composite, SWT.NONE);
            button2.setText("&Remove");
            button3 = new Button(composite, SWT.NONE);
            button3.setText("A&dd All");
            button4 = new Button(composite, SWT.NONE);
            button4.setText("Re&move All");
        }
        else
        {
            createCustomButtons();
        }

    }

    private void createCustomButtons()
    {
        for (int i = 0; i < button.length; i++)
        {
            final int ind = i;
            button[i] = new Button(composite, SWT.NONE);
            button[i].setText(buttonLabel[i]);
            button[i].addMouseListener(new MouseListener() {

                public void mouseDoubleClick(MouseEvent e)
                { }

                public void mouseDown(MouseEvent e)
                {
                    action[ind].run();
                }

                public void mouseUp(MouseEvent e)
                { }
                
            });
        }
        
    }
}

//-----------------------------------------------------------------------------
//$Log: not supported by cvs2svn $
