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

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author M. Cardone
 */
public class CommaSeparatedValues
{
    ArrayList<String> tokens = new ArrayList<String>();
    public CommaSeparatedValues(String value)
    {
        StringTokenizer tok = new StringTokenizer(value,";");
        while(tok.hasMoreElements())
        {
            tokens.add(tok.nextToken());
        }   
    }
    
    public String toString()
    {
        String result = "";
        for (int i = 0; i < tokens.size(); i++) {
            String elem = (String) tokens.get(i);
            if(i > 0)
            {
                result += ", ";
            }
            result += elem;
        }
        return result;
    }
    
    public String[] getTokens()
    {
        return (String[])tokens.toArray(new String[0]);
    }
}

//-----------------------------------------------------------------------------
// $Log$
// Revision 1.2  2007/01/19 18:30:59  mrc
// Tokens separator changed (now is ';').
//
// Revision 1.1  2007/01/15 09:04:03  mrc
// Progressing Property Grid development.
//
//
// Revision 1.0  14-Jan-2007 06:58:13  mrc
// Initial revision