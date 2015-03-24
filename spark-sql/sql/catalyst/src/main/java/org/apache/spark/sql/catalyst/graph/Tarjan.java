/* Copyright (C) 2009-2011  Syed Asad Rahman <asad@ebi.ac.uk>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/*
 * Finding strongly-connected components in a directed graph
 * 
 * Input: Graph G = (V, E), Start Node<T> v0
 *
 *index = 0                       // DFS Node<T> number counter 
 *S = empty                       // An empty stack of Node<T>s
 *tarjan(v0)                      // Start a DFS at the start Node<T>
 *
 *procedure tarjan(v)
 *  v.index = index               // Set the depth index for v
 *  v.lowlink = index
 * index = index + 1
 * S.push(v)                     // Push v on the stack
 *forall (v, v') in E do        // Consider successors of v 
 *   if (v'.index is undefined)  // Was successor v' visited? 
 *   tarjan(v')                // Recurse
 *   v.lowlink = min(v.lowlink, v'.lowlink)
 *elseif (v' in S)            // Is v' on the stack?
 *   v.lowlink = min(v.lowlink, v'.index)
 *if (v.lowlink == v.index)     // Is v the root of an SCC?
 *  print "SCC:"
 *  repeat
 *   v' = S.pop
 *   print v'
 *until (v' == v)
 */
package org.apache.spark.sql.catalyst.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asad
 */
public class Tarjan<T> {

  private int index = 0;
  private List<Node<T>> stack = new ArrayList<Node<T>>();
  private List<List<Node<T>>> SCC = new ArrayList<List<Node<T>>>();

  public List<List<Node<T>>> tarjan(Node<T> v, AdjacencyList<T> list) {
    v.setIndex(index);
    v.setLowlink(index);
    index++;
    stack.add(0, v);
    for (Edge<T> e : list.getAdjacent(v)) {
      Node<T> n = e.getTo();
      if (n.getIndex() == -1) {
        tarjan(n, list);
        v.setLowlink(Math.min(v.getLowlink(), n.getLowlink()));
      } else if (stack.contains(n)) {
        v.setLowlink(Math.min(v.getLowlink(), n.getIndex()));
      }
    }
    if (v.getLowlink() == v.getIndex()) {
      Node<T> n;
      ArrayList<Node<T>> component = new ArrayList<Node<T>>();
      do {
        n = stack.remove(0);
        component.add(n);
      } while (n != v);
      SCC.add(component);
    }
    return SCC;
  }
}