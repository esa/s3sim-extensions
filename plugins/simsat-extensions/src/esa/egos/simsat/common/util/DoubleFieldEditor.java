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

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for an double type preference.
 */
public class DoubleFieldEditor extends StringFieldEditor{

	private double minValidValue = Double.MIN_VALUE;

	private double maxValidValue = Double.MAX_VALUE;
    
    private static final int DEFAULT_TEXT_LIMIT = 10;

    /**
     * Creates a new double field editor 
     */
    protected DoubleFieldEditor() {
    }	
    
    /**
     * Creates an Double field editor.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public DoubleFieldEditor(String name, String labelText, Composite parent) {
        	this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
    }	

    /**
     * Creates an double field editor.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     * @param textLimit the maximum number of characters in the text.
     */
    public DoubleFieldEditor(String name, String labelText, Composite parent,
            int textLimit) {
        init(name, labelText);
        setTextLimit(textLimit);
        setEmptyStringAllowed(false);
        setErrorMessage("Value must be a Double");//$NON-NLS-1$
        createControl(parent);
    }

    /**
     * Sets the range of valid values for this field.
     * 
     * @param min the minimum allowed value (inclusive)
     * @param max the maximum allowed value (inclusive)
     */
    public void setValidRange(double min, double max) {
        minValidValue = min;
        maxValidValue = max;
        setErrorMessage( "Value must be a Double between " + (new Double(min)).toString() + (new Double(max)).toString());
    }

    /* (non-Javadoc)
     * Method declared on StringFieldEditor.
     * Checks whether the entered String is a valid double or not.
     */
    protected boolean checkState() {

        Text text = getTextControl();

        if (text == null) {
			return false;
		}

        String numberString = text.getText();
        try {
            double number = Double.valueOf(numberString);
            if (number >= minValidValue && number <= maxValidValue) {
				clearErrorMessage();
				return true;
			}
            
			showErrorMessage();
			return false;
			
        } catch (NumberFormatException e1) {
            showErrorMessage();
        }

        return false;
    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doLoad() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDouble(getPreferenceName());
            text.setText("" + value);//$NON-NLS-1$
            oldValue = "" + value; //$NON-NLS-1$
        }

    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doLoadDefault() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDefaultDouble(getPreferenceName());
            text.setText("" + value);//$NON-NLS-1$
        }
        valueChanged();
    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doStore() {
        Text text = getTextControl();
        if (text != null) {
            Double d = new Double(text.getText());
            getPreferenceStore().setValue(getPreferenceName(), d.doubleValue());
        }
    }

    /**
     * Returns this field editor's current value as an double.
     *
     * @return the value
     * @exception NumberFormatException if the <code>String</code> does not
     *   contain a parsable double
     */
    public Double getDoubleValue() throws NumberFormatException {
        return new Double(getStringValue()).doubleValue();
    }
    }
