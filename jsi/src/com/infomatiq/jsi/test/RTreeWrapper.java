//   RTreeWrapper.java
//   Java Spatial Index Library
//   Copyright (C) 2002 Infomatiq Limited
//  
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//  
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//  
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

package com.infomatiq.jsi.test;

import java.util.Properties;

import com.infomatiq.jsi.IntProcedure;
import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.infomatiq.jsi.rtree.RTree;

/**
 * A completely useless wrapper class for the RTree class.
 * 
 * Actually the point to introduce the same overhead as 
 * the SILWrapper class, so that performance comparisons
 * can be made.
 * 
 * @author aled.morris@infomatiq.co.uk
 * @version $Revision$
 */
public class RTreeWrapper implements SpatialIndex {
  private static final String version = "1.0b2";
  
  private RTree tree;
  
  class IntProcedure2 implements IntProcedure {
    private IntProcedure m_intProcedure = null;
    
    public IntProcedure2(IntProcedure ip) {
      m_intProcedure = ip;
    }
    
    public boolean execute(int i) {
      return m_intProcedure.execute(i);
    }
  }
  
  /**
   * @see com.infomatiq.jsi.SpatialIndex#init(Properties)
   */
  public void init(Properties props) {
    // create a memory-based storage manager
    
    tree = new RTree();
    tree.init(props);
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#nearest(Rectangle, IntProcedure, float)
   */
  public void nearest(Point p , IntProcedure ip, float furthestDistance) {
    tree.nearest(new Point(p.coordinates[0], p.coordinates[1]),
                 new IntProcedure2(ip),
                 Float.POSITIVE_INFINITY);
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#intersects(Rectangle, IntProcedure)
   */
  public void intersects(Rectangle r, IntProcedure ip) {
    Rectangle r2 = new Rectangle(r.min, r.max);  
    tree.intersects(r2, new IntProcedure2(ip));
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#contains(Rectangle, IntProcedure)
   */
  public void contains(Rectangle r, IntProcedure ip) {
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#add(Rectangle, int)
   */
  public void add(Rectangle r, int id) {
    Rectangle r2 = new Rectangle(r.min, r.max);
    tree.add(r2, id);
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#delete(Rectangle, int)
   */
  public boolean delete(Rectangle r, int id) {
    Rectangle r2 = new Rectangle(r.min, r.max);
    return tree.delete(r2, id);
  }

  /**
   * @see com.infomatiq.jsi.SpatialIndex#size()
   */
  public int size() {
    return tree.size();  
  }
  
  /**
   * @see com.infomatiq.jsi.SpatialIndex#getBounds()
   */
  public Rectangle getBounds() {
    return tree.getBounds(); 
  }
  
  /**
   * @see com.infomatiq.jsi.SpatialIndex#getVersion()
   */
  public String getVersion() {
    return "RTreeWrapper-" + version;
  }
  
}
