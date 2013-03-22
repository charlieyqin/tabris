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
package com.eclipsesource.tabris.internal.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.internal.remote.RemoteObjectImpl;
import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.testfixture.Fixture;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.eclipsesource.tabris.test.TabrisTestUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.PageStore;
import com.eclipsesource.tabris.ui.PageStyle;
import com.eclipsesource.tabris.ui.UI;


@SuppressWarnings("restriction")
public class RemotePageTest {

  private RemoteObjectImpl remoteObject;
  private PageDescriptor descriptor;
  private UI ui;
  private Shell shell;

  @Before
  public void setUp() {
    Fixture.setUp();
    Display display = new Display();
    shell = new Shell( display );
    remoteObject = ( RemoteObjectImpl )TabrisTestUtil.mockRemoteObject();
    ui = mock( UI.class );
    mockDescriptor();
  }

  private void mockDescriptor() {
    descriptor = mock( PageDescriptor.class );
    when( descriptor.getId() ).thenReturn( "foo" );
    when( descriptor.getTitle() ).thenReturn( "bar" );
    doReturn( Boolean.TRUE ).when( descriptor ).isTopLevel();
    when( descriptor.getPageStyle() ).thenReturn( new PageStyle[] { PageStyle.DEFAULT } );
    List<ActionDescriptor> actions = new ArrayList<ActionDescriptor>();
    actions.add( new ActionDescriptor( "actionFoo",
                                       new TestAction(),
                                       "actionBar",
                                       "testImage.png",
                                       true,
                                       true ) );
    when( descriptor.getActions() ).thenReturn( actions );
    when( descriptor.getImagePath() ).thenReturn( "testImage.png" );
    doReturn( TestPage.class ).when( descriptor ).getPageType();
  }

  @After
  public void tearDown() {
    Fixture.tearDown();
  }

  @Test
  public void testGetRemoteId() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );

    assertEquals( remoteObject.getId(), page.getRemotePageId() );
  }

  @Test
  public void testSetsDefaultAttributes() {
    RemotePage remotePage = new RemotePage( ui, descriptor, "foo1", mock( PageStore.class ) );
    remotePage.createControl( shell );

    verify( remoteObject ).set( "parent", "foo1" );
    verify( remoteObject ).set( "title", "bar" );
    verify( remoteObject ).set( "control", WidgetUtil.getId( remotePage.getControl() ) );
    verify( remoteObject ).set( "style", new String[] { "DEFAULT" } );
    verify( remoteObject ).set( "topLevel", true );
    ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass( Object[].class );
    verify( remoteObject ).set( eq( "image" ), captor.capture() );
    assertTrue( captor.getValue()[ 0 ] instanceof String );
    assertEquals( Integer.valueOf( 49 ), captor.getValue()[ 1 ] );
    assertEquals( Integer.valueOf( 43 ), captor.getValue()[ 2 ] );
  }

  @Test
  public void testSetsFullScreenStyle() {
    PageDescriptor localDescriptor = new PageDescriptor( "foo",
                                                                 TestPage.class,
                                                                 "bar",
                                                                 null,
                                                                 true,
                                                                 PageStyle.FULLSCREEN );

    RemotePage remotePage = new RemotePage( ui, localDescriptor, "foo1", mock( PageStore.class ) );
    remotePage.createControl( shell );

    verify( remoteObject ).set( "style", new String[] { "FULLSCREEN" } );
  }

  @Test
  public void testSetsNoEmptyScreenStyle() {
    PageDescriptor localDescriptor = new PageDescriptor( "foo",
                                                                 TestPage.class,
                                                                 "bar",
                                                                 null,
                                                                 true );

    new RemotePage( ui, localDescriptor, "foo1", mock( PageStore.class ) );

    verify( remoteObject, never() ).set( eq( "style" ), any( String[].class ) );
  }

  @Test
  public void testGetActions() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );
    page.createActions();

    List<RemoteAction> actions = page.getActions();
    assertEquals( 1, actions.size() );
    assertEquals( "actionFoo", actions.get( 0 ).getDescriptor().getId() );
  }

  @Test
  public void testSetTitle() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );

    page.setTitle( "foo" );

    verify( remoteObject ).set( "title", "bar" );
  }

  @Test
  public void testGetStore() {
    PageStore store = mock( PageStore.class );
    RemotePage page = new RemotePage( ui, descriptor, "foo", store );

    assertSame( store, page.getStore() );
  }

  @Test
  public void testDestroyDoesNotDestroyActions() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );
    page.createControl( shell );

    page.destroy();

    verify( remoteObject ).destroy();
  }

  @Test
  public void testDestroyActionsSendsDestroy() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );
    page.createActions();

    page.destroyActions();

    verify( remoteObject ).destroy();
  }

  @Test
  public void testGetDescriptor() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );

    assertSame( descriptor, page.getDescriptor() );
  }

  @Test
  public void testGetCreatesControl() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );
    page.createControl( shell );

    assertNotNull( page.getControl() );
  }

  @Test
  public void testDestoyDisposesControl() {
    RemotePage page = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );
    page.createControl( shell );

    page.destroy();

    assertTrue( page.getControl().isDisposed() );
  }

  @Test
  public void testCreatesPage() {
    RemotePage remotePage = new RemotePage( ui, descriptor, "foo", mock( PageStore.class ) );

    Page page = remotePage.getPage();

    assertNotNull( page );
    assertTrue( page instanceof TestPage );
  }
}
