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
package esa.egos.simsat.mmi.commander.preferences;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import esa.egos.simsat.mmi.commander.CommanderPlugin;

/**
 * Defines the entries in the plug-in preference page.
 */
public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    /**
     * constructor
     */
    public PreferencePage()
    {
        super(GRID);

        setPreferenceStore(CommanderPlugin.getDefault().getPreferenceStore());
    }

    /**
     * initialization
     * 
     * @param workbench
     */
    public void init(IWorkbench workbench)
    {

    }

    /**
     * create the fields for the preference page
     */
    protected void createFieldEditors()
    {
    	{
            // NOTE: The path is configured by default in the
            // PreferenceInitializer pointing to a set of plug-ins wrapping
            // the relevant Jar files. However is felt as not necessary to
            // change this path from the user in the most common cases it
            // is worth to have the possibility to do so or at least to
            // be able to see what is the path actually used.
	       final FileEditor scripthostPathEditor = new FileEditor(PreferenceInitializer.PREFERENCE_SCRIPT_HOST_SERVER_PATH,
	                                               		"ScriptHostServer path",
	                                               		File.pathSeparator,
	                                               		getFieldEditorParent());
	       addField(scripthostPathEditor);
    	}
        
    	{
	        final FileEditor editorPathEditor = new FileEditor(PreferenceInitializer.PREFERENCE_TEXT_EDITOR,
	                "Text editor path",
	                ",",
	                getFieldEditorParent());
	        addField(editorPathEditor);
    	}

    	{
	        final BooleanFieldEditor autoCompEnableEditor = new BooleanFieldEditor(PreferenceInitializer.PREFERENCE_ENABLE_AUTOCOMPLETE, 
	        																		"Enable auto-completion of script commands" , 
	        																		getFieldEditorParent());
	        addField(autoCompEnableEditor);
    	}
    }

    /**
     * on OK save the preferences
     * 
     * @return true is the execution is ok
     */
    public boolean performOk()
    {
        try
        {
            ScopedPreferenceStore store = (ScopedPreferenceStore) CommanderPlugin.getDefault().getPreferenceStore();
            store.save();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return super.performOk();
    }
    
    //NOTICE: Adapted from org.eclipse.jface.preference.PathEditor.
    /**
     * A field editor to edit lists of file paths.
     */
    private static class FileEditor extends ListEditor
    {
        /**
         * The last path, or <code>null</code> if none.
         */
        private String lastPath;
        
        /**
         * The separator used to separate paths in the file path list.
         */
        private String pathListSeparator = null; 

        /**
         * Creates a path field editor.
         * 
         * @param name the name of the preference this field editor works on
         * @param labelText the label text of the field editor
         * @param parent the parent of the field editor's control
         */
        public FileEditor(String name, String labelText, String pathListSeparator, Composite parent) {
        	this.pathListSeparator = pathListSeparator;
            init(name, labelText);
            createControl(parent);
        }

        /* (non-Javadoc)
         * Method declared on ListEditor.
         * Creates a single string from the given array by separating each
         * string with pathListSeparator.
         */
        protected String createList(String[] items)
        {
            StringBuffer path = new StringBuffer("");

            for (int i = 0; i < items.length; i++)
            {
                path.append(items[i]);
                path.append(pathListSeparator);
            }
            return path.toString();
        }

        /* (non-Javadoc)
         * Method declared on ListEditor.
         * Creates a new path element by means of a file dialog.
         */
        protected String getNewInputObject() {

            FileDialog dialog = new FileDialog(getShell(), SWT.SHEET);
            if (lastPath != null)
            {
            	final File lastPathAsFile = new File(lastPath);
            	final File lastPathParent = lastPathAsFile.getParentFile();
            	
                if ( (null != lastPathParent) &&
                		lastPathParent.exists())
                {
    				dialog.setFilterPath(lastPathParent.getAbsolutePath());
    			}
            }
            String dir = dialog.open();
            if (dir != null) {
                dir = dir.trim();
                if (dir.length() == 0) {
    				return null;
    			}
                lastPath = dir;
            }
            return dir;
        }

        /* (non-Javadoc)
         * Method declared on ListEditor.
         */
        protected String[] parseString(String stringList)
        {
            StringTokenizer st = new StringTokenizer(stringList, pathListSeparator + "\n\r");
            ArrayList<String> v = new ArrayList<String>();

            while (st.hasMoreElements())
            {
                v.add((String)st.nextElement());
            }
            
            return (String[]) v.toArray(new String[v.size()]);
        }
    }
}

/*
 * CVS Change Log
 * 
 * $Log: not supported by cvs2svn $
 * Revision 1.6  2009-09-15 12:49:07  mrc
 * Removed commented out code.
 *
 * Revision 1.5  2007/05/01 14:56:08  hboyer
 * Autocompletion is enabled from the preferences
 *
 * Revision 1.4  2006/12/06 11:00:59  jsc
 * modified constructor in order to use the preference store associated with the user runtime workspace.
 * Revision 1.3 2006/05/09 10:40:54 alp INTERNAL
 * REVIEW added comments formatting removing unused code (there are some points
 * still to be cleaned up)
 * 
 * Revision 1.2 2006/05/04 16:32:21 alp New preference for ScriptHostServer path
 * 
 * Revision 1.1 2006/04/07 14:06:00 mb First version of the Commander plugin.
 * Revision 1.1 2006/03/29 11:10:25 alp integration with first attempt of GRD
 * 
 * 
 */