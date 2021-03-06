/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.ui.action;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * A {@link ProposalHandler} acts as a bridge between server and client during a search task. The
 * {@link ProposalHandler} will be used to propose search terms to a client during the modification of a search query.
 * </p>
 *
 * @see SearchAction#modified(String, ProposalHandler)
 *
 * @since 1.2
 */
public interface ProposalHandler extends Serializable {

  /**
   * <p>
   * Sets the search term proposals to display on the client.
   * </p>
   *
   * @param proposals the proposals to set. Must not be <code>null</code>.
   */
  void setProposals( List<Proposal> proposals );

}
