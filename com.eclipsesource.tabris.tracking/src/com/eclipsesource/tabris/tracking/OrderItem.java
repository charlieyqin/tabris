/*******************************************************************************
 * Copyright (c) 2014 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.tracking;

import static com.eclipsesource.tabris.internal.Clauses.when;
import static com.eclipsesource.tabris.internal.Clauses.whenNull;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;


/**
 * <p>
 * An {@link Order} may consist of several {@link OrderItem}s. An {@link OrderItem} should define the sku,
 * quantity, name, price, etc. within the order.
 * </p>
 *
 * @see Order#addItem(OrderItem)
 *
 * @since 1.4
 */
@SuppressWarnings("restriction")
public class OrderItem {

  private final String name;
  private BigDecimal price;
  private int quantity;
  private String sku;
  private String category;

  public OrderItem( String name ) {
    whenNull( name ).throwIllegalArgument( "Name must not be null" );
    when( name.isEmpty() ).throwIllegalArgument( "Name must not be empty" );
    this.name = name;
    this.quantity = 1;
    this.price = ZERO;
  }

  public String getName() {
    return name;
  }

  public OrderItem setPrice( BigDecimal price ) {
    whenNull( price ).throwIllegalArgument( "Price must not be null" );
    this.price = price;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public OrderItem setQuantity( int quantity ) {
    this.quantity = quantity;
    return this;
  }

  public int getQuantity() {
    return quantity;
  }

  public OrderItem setSKU( String sku ) {
    whenNull( sku ).throwIllegalArgument( "SKU must not be null" );
    when( sku.isEmpty() ).throwIllegalArgument( "SKU must not be empty" );
    this.sku = sku;
    return this;
  }

  public String getSKU() {
    return sku;
  }

  public OrderItem setCategory( String category ) {
    whenNull( category ).throwIllegalArgument( "Category must not be null" );
    when( category.isEmpty() ).throwIllegalArgument( "Category must not be empty" );
    this.category = category;
    return this;
  }

  public String getCategory() {
    return category;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( category == null ) ? 0 : category.hashCode() );
    result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
    result = prime * result + ( ( price == null ) ? 0 : price.hashCode() );
    result = prime * result + quantity;
    result = prime * result + ( ( sku == null ) ? 0 : sku.hashCode() );
    return result;
  }

  @Override
  public boolean equals( Object obj ) {
    if( this == obj ) {
      return true;
    }
    if( obj == null ) {
      return false;
    }
    if( getClass() != obj.getClass() ) {
      return false;
    }
    OrderItem other = ( OrderItem )obj;
    if( category == null ) {
      if( other.category != null ) {
        return false;
      }
    } else if( !category.equals( other.category ) ) {
      return false;
    }
    if( name == null ) {
      if( other.name != null ) {
        return false;
      }
    } else if( !name.equals( other.name ) ) {
      return false;
    }
    if( price == null ) {
      if( other.price != null ) {
        return false;
      }
    } else if( !price.equals( other.price ) ) {
      return false;
    }
    if( quantity != other.quantity ) {
      return false;
    }
    if( sku == null ) {
      if( other.sku != null ) {
        return false;
      }
    } else if( !sku.equals( other.sku ) ) {
      return false;
    }
    return true;
  }

}
