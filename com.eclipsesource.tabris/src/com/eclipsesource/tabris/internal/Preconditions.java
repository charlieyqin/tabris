/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.internal;


public class Preconditions {
  
  public static void argumentNotNull( Object object, String name ) {
    if( object == null ) {
      throw new IllegalArgumentException( name + " must not be null" );
    }
  }
  
  public static void argumentNotNullAndNotEmpty( Object object, String name ) {
    if( object == null ) {
      throw new IllegalArgumentException( name + " must not be null" );
    }
    if( object instanceof String && ( ( String )object ).isEmpty() ) {
      throw new IllegalArgumentException( name + " must not be empty" );
    }
  }
  
  private Preconditions() {
    // prevent instantiation
  }
}