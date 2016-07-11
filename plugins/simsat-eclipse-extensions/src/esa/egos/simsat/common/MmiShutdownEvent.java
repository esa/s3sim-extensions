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

import java.util.EventObject;

/**
 * @author Jean Schuetz
 * 
 */
public class MmiShutdownEvent extends EventObject
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1122097642513603853L;

    /**
     * Shutdown initiator
     */
    public static final int REQUEST_USER = 0;

    public static final int REQUEST_SYSTEM = 1;

    /**
     * Shutdown stages
     */
    public static final int PRE_SHUTDOWN = 1;

    public static final int POST_SHUTDOWN = 2;

    /** the shutdown stage */
    private int shutdownStage;

    /** the initiator of the shutdown */
    private int shutdownRequestInitiator;


    public MmiShutdownEvent(Object shutdownInitiator, int shutdownStage, int shutdownRequestInitiator)
    {
        super(shutdownInitiator);
        this.shutdownStage = shutdownStage;
        this.shutdownRequestInitiator = shutdownRequestInitiator;
    }

    public MmiShutdownEvent(Object shutdownInitiator, int shutdownStage)
    {
        this(shutdownInitiator, shutdownStage, REQUEST_USER);
    }

    /**
     * @return the shutdownRequestInitiator. Either REQUEST_USER or
     *         REQUEST_SYSTEM
     */
    public int getShutdownRequestInitiator()
    {
        return this.shutdownRequestInitiator;
    }

    /**
     * @return the shutdownStage. Either PRE_SHUTDOWN or POST_SHUTDOWN
     */
    public int getShutdownStage()
    {
        return this.shutdownStage;
    }
}

// -----------------------------------------------------------------------------
// $Log$
//
