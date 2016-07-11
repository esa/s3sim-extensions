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
import java.net.URL;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import esa.egos.simsat.mmi.commander.CommanderPlugin;

/**
 * Implements the Commander Plugin's contribution to the
 * org.eclipse.core.runtime.preferences extension point.
 * Defines bootstrap values for the preference keys
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	public static final String PREFERENCE_ENABLE_AUTOCOMPLETE = "autocomplete-enable";
	
    // Preference keys for the table column width

    public static final String PREFERENCE_HISTORY_TABLE_BACKGROUND = "history-background-color";

    public static final String PREFERENCE_STATUS_TABLE_BACKGROUND = "status-background-color";

    public static final String PREFERENCE_STACK_TABLE_BACKGROUND = "stack-background-color";

    public static final String PREFERENCE_OBJECTS_TABLE_BACKGROUND = "objects-background-color";

    public static final String PREFERENCE_SCRIPT_TABLE_BACKGROUND = "scripts-background-color";
    
    public static final String PREFERENCE_SCHEDULED_TABLE_BACKGROUND = "scheduled-background-color";

    public static final String PREFERENCE_CONDITIONAL_TABLE_BACKGROUND = "condiitonal-background-color";

    // Preference keys for the table column width
    // History table
    public static final String PREFERENCE_WIDTH_HISTORY_MESSAGE = "column-history-message-width";

    public static final String PREFERENCE_WIDTH_HISTORY_TIMESTAMP = "column-history-timestamp-width";

    // Status Table
    public static final String PREFERENCE_WIDTH_STATUS_COMMAND = "column-status-command-width";

    public static final String PREFERENCE_WIDTH_STATUS_STATUS = "column-status-status-width";

    public static final String PREFERENCE_WIDTH_STATUS_TIMESTAMP = "column-status-timestamp-width";

    // Stack Table
    public static final String PREFERENCE_WIDTH_STACK_COMMAND = "column-stack-command-width";

    // Objects Table
    public static final String PREFERENCE_WIDTH_OBJECTS_ALIAS = "column-objects-alias-width";

    public static final String PREFERENCE_WIDTH_OBJECTS_PATH = "column-objects-path-width";

    public static final String PREFERENCE_WIDTH_OBJECTS_STATUS = "column-objects-status-width";

    // Script Files Table
    public static final String PREFERENCE_WIDTH_SCRIPT_FILEPATH = "column-script-filepath-width";

    public static final String PREFERENCE_WIDTH_SCRIPT_STATUS = "column-script-status-width";

    // Preference keys for the table column index
    // History Table
    public static final String PREFERENCE_INDEX_HISTORY_MESSAGE = "column-history-message-index";

    public static final String PREFERENCE_INDEX_HISTORY_TIMESTAMP = "column-history-timestamp-index";

    // Status Table
    public static final String PREFERENCE_INDEX_STATUS_COMMAND = "column-status-command-index";

    public static final String PREFERENCE_INDEX_STATUS_STATUS = "column-status-status-index";

    public static final String PREFERENCE_INDEX_STATUS_TIMESTAMP = "column-status-timestamp-index";

    // Stack Table
    public static final String PREFERENCE_INDEX_STACK_COMMAND = "column-stack-command-index";

    // Objects Table
    public static final String PREFERENCE_INDEX_OBJECTS_ALIAS = "column-objects-alias-index";

    public static final String PREFERENCE_INDEX_OBJECTS_PATH = "column-objects-path-index";

    public static final String PREFERENCE_INDEX_OBJECTS_STATUS = "column-objects-status-index";

    // Scripts Files Table
    public static final String PREFERENCE_INDEX_SCRIPT_FILEPATH = "column-script-filepath-index";

    public static final String PREFERENCE_INDEX_SCRIPT_STATUS = "column-script-status-index";

    // Scripts Directories
    public static final String PREFERENCE_SCRIPT_ENGINE_CONF_DIR_PATH = "script-engine-conf-dir-path";

    public static final String PREFERENCE_SCRIPT_FILES_DIR_PATH = "script-files-dir-path";

    // Jar files to run the ScriptHost
    public static final String PREFERENCE_SCRIPT_HOST_SERVER_JAR = "preference-scripthostserver-jar";

    public static final String PREFERENCE_RHINO_JS_JAR = "preference-rhinojs-jar";

    public static final String PREFERENCE_JSCAD_JAR = "preference-jscad-jar";

    public static final String PREFERENCE_KERNELJDK_JAR = "preference-kerneljdk-jar";

    public static final String PREFERENCE_SCRIPT_HOST_SERVER_PATH = "preference-script-host-server-path";
    
    public static final String PREFERENCE_TEXT_EDITOR = "preference-text-editor";

    private String defaultSecDir = "";

    private String defaultScriptsDir = "";

    private final String textEditors = "/usr/bin/gedit" + "," +
                                        "/opt/gnome/bin/gedit" + "," +
                                        "/usr/bin/kate" + "," +
                                        "/opt/kde3/bin/kate" + "," +
                                        "C:\\WINDOWS\\system32\\notepad.exe" + "," +
                                        "C:\\WINNT\\system32\\notepad.exe ";
    /**
     * Utility function. Return a formatted string representing the red, green,
     * and blue values from the specified RGB object. This string will be
     * written into the preference file to represent a specific colour
     * 
     * @param rgb
     *            color in RGB object format
     * @return the string describing the color "<red>,<green>,<blue>"
     */
    private static String RGB2String(RGB rgb)
    {
        return rgb.red + "," + rgb.green + "," + rgb.blue;
    }

    /**
     * Utility function. Take a string in the format returned by
     * RGB2PreferenceString (above), and return a new RGB object initialized
     * with the extracted values
     * 
     * @param prefString
     *            string describing the color "<red>,<green>,<blue>"
     * @return the color in RGB object format
     * @throws IllegalArgumentException
     */
    private static RGB String2RGB(String prefString) throws IllegalArgumentException
    {
        StringTokenizer tok = new StringTokenizer(prefString, ",");
        int red = Integer.parseInt(tok.nextToken());
        int green = Integer.parseInt(tok.nextToken());
        int blue = Integer.parseInt(tok.nextToken());
        return new RGB(red, green, blue);
    }

    /**
     * Default constructor
     */
    public PreferenceInitializer()
    {
        super();
    }

    /**
     * Implement setting of default values for preference values to be set the
     * Default preference scope
     */
    public void initializeDefaultPreferences()
    {
        IScopeContext scope = new DefaultScope();
        IEclipsePreferences defaults = scope.getNode(CommanderPlugin.getPluginId());

        
        defaults.put(PREFERENCE_HISTORY_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_STATUS_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_STACK_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_OBJECTS_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_SCRIPT_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_SCHEDULED_TABLE_BACKGROUND, "255,255,255");
        defaults.put(PREFERENCE_CONDITIONAL_TABLE_BACKGROUND, "255,255,255");

        // Column widths
        defaults.putInt(PREFERENCE_WIDTH_HISTORY_MESSAGE, 350);
        defaults.putInt(PREFERENCE_WIDTH_HISTORY_TIMESTAMP, 100);

        defaults.putInt(PREFERENCE_WIDTH_STATUS_COMMAND, 250);
        defaults.putInt(PREFERENCE_WIDTH_STATUS_STATUS, 100);
        defaults.putInt(PREFERENCE_WIDTH_STATUS_TIMESTAMP, 100);

        defaults.putInt(PREFERENCE_WIDTH_STACK_COMMAND, 450);

        defaults.putInt(PREFERENCE_WIDTH_OBJECTS_ALIAS, 150);
        defaults.putInt(PREFERENCE_WIDTH_OBJECTS_PATH, 200);
        defaults.putInt(PREFERENCE_WIDTH_OBJECTS_STATUS, 100);

        defaults.putInt(PREFERENCE_WIDTH_SCRIPT_FILEPATH, 300);
        defaults.putInt(PREFERENCE_WIDTH_SCRIPT_STATUS, 150);

        defaults.putInt(PREFERENCE_INDEX_HISTORY_MESSAGE, 0);
        defaults.putInt(PREFERENCE_INDEX_HISTORY_TIMESTAMP, 1);

        defaults.putInt(PREFERENCE_INDEX_STATUS_COMMAND, 0);
        defaults.putInt(PREFERENCE_INDEX_STATUS_STATUS, 1);
        defaults.putInt(PREFERENCE_INDEX_STATUS_TIMESTAMP, 2);

        defaults.putInt(PREFERENCE_INDEX_STACK_COMMAND, 0);

        defaults.putInt(PREFERENCE_INDEX_OBJECTS_ALIAS, 0);
        defaults.putInt(PREFERENCE_INDEX_OBJECTS_PATH, 1);
        defaults.putInt(PREFERENCE_INDEX_OBJECTS_STATUS, 2);

        defaults.putInt(PREFERENCE_INDEX_SCRIPT_FILEPATH, 0);
        defaults.putInt(PREFERENCE_INDEX_SCRIPT_STATUS, 1);

        // setScriptsFilesDefaultPreferences();

        defaults.put(PREFERENCE_SCRIPT_ENGINE_CONF_DIR_PATH, defaultSecDir);
        defaults.put(PREFERENCE_SCRIPT_FILES_DIR_PATH, defaultScriptsDir);

       
        
        // initialize the path for the activation of the
        // ScriptHostServer java application
        //
        // NOTE: here we keep the four preferences for the
        // relevant "wrapper" plugins even if is not expected for
        // them to be used elsewhere

        String path = "";
        path = addJarToPath(defaults,
                            path,
                            "esa.egos.simsat.cots.scripthostserver",
                            "ScriptHost.jar",
                            PREFERENCE_SCRIPT_HOST_SERVER_JAR);

        path = addJarToPath(defaults, path, "esa.egos.simsat.cots", "js.jar", PREFERENCE_RHINO_JS_JAR);
        path = addJarToPath(defaults, path, "esa.egos.simsat.cots", "jscad.jar", PREFERENCE_JSCAD_JAR);
        path = addJarToPath(defaults,
                            path,
                            "esa.egos.simsat.cots.kerneljdk",
                            "KernelJdk.jar",
                            PREFERENCE_KERNELJDK_JAR);

        defaults.put(PREFERENCE_SCRIPT_HOST_SERVER_PATH, path);
        
        defaults.putBoolean(PREFERENCE_ENABLE_AUTOCOMPLETE, true);
        
        defaults.put(PREFERENCE_TEXT_EDITOR, textEditors);
    }

    /**
     * utility method to avoid cut & paste
     * 
     */
    private String addJarToPath(IEclipsePreferences defaults,
                                String path,
                                String bundleName,
                                String jarName,
                                String pref)
    {
        String platformSpecificFilePath = "";

        try
        {

            Bundle bundle = Platform.getBundle(bundleName);
            URL relativeURL = bundle.getEntry("/" + jarName);
            // Platform.asLocalURL(relativeUrl) deprecated in Eclipse 3.2.0
            URL absoluteLocalURL = FileLocator.toFileURL(relativeURL);
            String platformIndependantFilePath = absoluteLocalURL.getFile();
            File cotsJar = new File(platformIndependantFilePath);
            platformSpecificFilePath = cotsJar.getPath();
//            CommanderPlugin.getDefault().getLog().log(new Status(IStatus.INFO,
//                                                                 CommanderPlugin.getPluginId(),
//                                                                 IStatus.OK,
//                                                                 "Localised library " + jarName
//                                                                         + " at the following location: "
//                                                                         + platformSpecificFilePath,
//                                                                 null));
            defaults.put(pref, platformSpecificFilePath);
            if (!path.equals("")) path += File.pathSeparator;
            path += platformSpecificFilePath;
        }
        catch (Exception e)
        {
            CommanderPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
                                                                 CommanderPlugin.getPluginId(),
                                                                 IStatus.OK,
                                                                 "Error while trying to localise the cots: "
                                                                         + jarName,
                                                                 e));
        }
        return path;
    }


    private static IEclipsePreferences configurationScope;


    /**
     * @return the scope for accessing the configuration preferences for this
     *         plugin
     */
    public static IEclipsePreferences getConfigurationScope()
    {
        if (configurationScope == null)
        {
            IScopeContext scope = new ConfigurationScope();
            configurationScope = scope.getNode(CommanderPlugin.getPluginId());
        }
        return configurationScope;
    }

    /**
     * get a color preference
     * 
     * @param display
     * @param preferenceKey
     * @return the color associated with the preference key
     */
    public static Color getColor(Display display, String preferenceKey)
    {
        String rgbStr = CommanderPlugin.getDefault().getPreferenceStore().getString(preferenceKey);
        RGB rgb = String2RGB(rgbStr);
        return new Color(display, rgb);
    }

    /**
     * set a color preference
     * 
     * @param preferenceKey
     * @param color
     */
    public static void setColor(String preferenceKey, Color color)
    {
        CommanderPlugin.getDefault().getPreferenceStore().setValue(preferenceKey, RGB2String(color.getRGB()));
    }

    /**
     * get an integer preference
     * 
     * @param preferenceKey
     * @return the value associated with the preference key
     */
    public static int getInt(String preferenceKey)
    {
        return CommanderPlugin.getDefault().getPreferenceStore().getInt(preferenceKey);
    }

    /**
     * set an integer preference
     * 
     * @param preferenceKey
     * @param value
     */
    public static void setInt(String preferenceKey, int value)
    {
        CommanderPlugin.getDefault().getPreferenceStore().setValue(preferenceKey, value);
    }

    /**
     * get an string preference
     * 
     * @param preferenceKey
     * @return the value associated with the preference key
     */
    public static String getString(String preferenceKey)
    {
        return CommanderPlugin.getDefault().getPreferenceStore().getString(preferenceKey);
    }

    /**
     * set an string preference
     * 
     * @param preferenceKey
     * @param value
     */
    public static void setString(String preferenceKey, String value)
    {
        CommanderPlugin.getDefault().getPreferenceStore().setValue(preferenceKey, value);
    }
    
    public static boolean isAutoCompleteEnabled()
    {
    	boolean r = CommanderPlugin.getDefault().getPreferenceStore().getBoolean(PREFERENCE_ENABLE_AUTOCOMPLETE);
    	return r;
    }
}

/*
 * CVS Change Log
 * 
 * $Log: not supported by cvs2svn $
 * Revision 1.27  2009-09-15 12:47:57  mrc
 * Defined Text Editor preferences.
 *
 * Revision 1.26  2009/04/27 13:41:08  nin
 * ScriptHostServer.jar replace by ScriptHost.jar
 *
 * Revision 1.25  2007/11/15 10:41:38  mrc
 * Porting to Eclipse 3.2.0
 *
 * Revision 1.24  2007/07/18 15:35:29  mrc
 * Fixed key 'PREFERENCE_WIDTH_STACK_COMMAND'.
 *
 * Revision 1.23  2007/05/01 14:56:08  hboyer
 * Autocompletion is enabled from the preferences
 *
 * Revision 1.22  2007/01/29 23:25:43  hboyer
 * added background color support for scheduled/conditional command tables
 *
 * Revision 1.21  2007/01/03 17:36:39  hboyer
 * removed unecessary messages in the eclipse log
 *
 * Revision 1.20  2006/12/06 13:51:53  jsc
 * renewed get property methods to use better API.
 *
 * 
 * Revision 1.19 2006/12/06 12:26:56 jsc modified addJarToPath() to be platform
 * independant
 * 
 * Revision 1.18 2006/12/06 12:16:14 jsc minor change.
 * 
 * Revision 1.17 2006/12/05 20:19:17 jsc implemented better method for
 * retrieving the path of the .jar cots files.
 * 
 * Revision 1.16 2006/11/22 14:50:24 jsc removed warnings
 * 
 * Revision 1.15 2006/07/31 02:00:16 hboyer removed use of internal Eclipse
 * classes
 * 
 * Revision 1.14 2006/07/24 15:00:21 hboyer replaced deprecated code and message
 * handler
 * 
 * Revision 1.13 2006/07/06 21:25:53 hboyer removed MMI framework
 * 
 * Revision 1.12 2006/06/19 21:26:02 hboyer refactored the path building to
 * limit warnings
 * 
 * Revision 1.11 2006/06/12 15:49:02 hboyer Cots plugins have been renamed
 * 
 * Revision 1.10 2006/05/31 11:48:30 mb Getting rid of the MMI Repository, to
 * solve issues #292 and #293
 * 
 * Revision 1.9 2006/05/24 13:30:22 hboyer Changed the way to read preferences
 * 
 * Revision 1.8 2006/05/15 11:33:28 mb removed ConfigurationFileManager and its
 * references (replaced by preferences) clean-up comments and tasks left pending
 * 
 * Revision 1.7 2006/05/10 09:10:16 mb Small change in a log text, regarding the
 * set up of the 'script_files_directory'
 * 
 * Revision 1.6 2006/05/09 10:40:54 alp INTERNAL REVIEW added comments
 * formatting removing unused code (there are some points still to be cleaned
 * up)
 * 
 * 
 */
