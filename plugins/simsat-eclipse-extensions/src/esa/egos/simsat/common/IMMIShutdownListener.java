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

import java.util.EventListener;

/**
 * Interface used to notify about the shutdown of the system. Plugins can
 * register as shutdown listeners at the CommonPlugin.
 * 
 * @author Jean Schuetz
 * 
 */
public interface IMMIShutdownListener extends EventListener
{

    /**
     * notifies about MMI SWhutdown
     * 
     * @param shutdownEvent
     *            a MmiShutdownEvent containing further shutdown details
     */
    void mmiShutdown(MmiShutdownEvent shutdownEvent);
}

// -----------------------------------------------------------------------------
// $Log$
//
