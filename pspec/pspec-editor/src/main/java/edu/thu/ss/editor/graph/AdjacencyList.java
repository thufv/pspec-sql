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
package edu.thu.ss.editor.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Asad
 */
public class AdjacencyList<T> {

  private Map<Node<T>, List<Edge<T>>> adjacencies = new HashMap<Node<T>, List<Edge<T>>>();

  public AdjacencyList() {
  }

  /**
   * 
   * @param adjacencies
   */
  public void setAdjacencyList(Map<Node<T>, List<Edge<T>>> adjacencies) {
    this.adjacencies.putAll(adjacencies);
  }

  /**
   * 
   * @param source
   * @param target
   * @param bond  
   */
  public void addEdge(Node<T> source, Node<T> target, double weight) {
    List<Edge<T>> list;
    if (!adjacencies.containsKey(source)) {
      list = new ArrayList<Edge<T>>();
      adjacencies.put(source, list);
    } else {
      list = adjacencies.get(source);
    }
    list.add(new Edge<T>(source, target, weight));
  }

  public void updateEdge(Node<T> source, Node<T> target, double weight) {
    List<Edge<T>> list;
    if (!adjacencies.containsKey(source)) {
      list = new ArrayList<Edge<T>>();
      adjacencies.put(source, list);
    } else {
      list = adjacencies.get(source);
    }
    boolean create = true;
    for (Edge<T> edge : list) {
      if (edge.getFrom().equals(source) && edge.getTo().equals(target)) {
        create = false;
        edge.setWeight(weight);
      }
    }
    if (create) {
      list.add(new Edge<T>(source, target, weight));
    }
  }

  /**
   * 
   * @param source
   * @return
   */
  public List<Edge<T>> getAdjacent(Node<T> source) {
    return adjacencies.get(source);
  }

  /**
   * 
   * @param e
   */
  public void reverseEdge(Edge<T> e) {
    adjacencies.get(e.getFrom()).remove(e);
    addEdge(e.getTo(), e.getFrom(), e.getWeight());
  }

  /**
   * 
   */
  public void reverseGraph() {
    setAdjacencies(getReversedList().adjacencies);
  }

  /**
   * 
   * @return
   */
  public AdjacencyList<T> getReversedList() {
    AdjacencyList<T> newlist = new AdjacencyList<T>();
    for (List<Edge<T>> edges : adjacencies.values()) {
      for (Edge<T> e : edges) {
        newlist.addEdge(e.getTo(), e.getFrom(), e.getWeight());
      }
    }
    return newlist;
  }

  /**
   * 
   * @return
   */
  public Set<Node<T>> getSourceNodeSet() {
    return adjacencies.keySet();
  }

  /**
   * 
   * @return
   */
  public Collection<Edge<T>> getAllEdges() {
    List<Edge<T>> edges = new ArrayList<Edge<T>>();
    for (List<Edge<T>> e : adjacencies.values()) {
      edges.addAll(e);
    }
    return edges;
  }

  /**
   * @param adjacencies the adjacencies to set
   */
  private void setAdjacencies(Map<Node<T>, List<Edge<T>>> adjacencies) {
    this.adjacencies = adjacencies;
  }

  public void clear() {
    if (this.adjacencies != null) {
      adjacencies.clear();
    }
  }

  public double weightProducts(Node<T> root) {
    double result = 1;
    List<Edge<T>> list = adjacencies.get(root);
    if (list != null) {
      for (Edge<T> edge : list) {
        if (edge.getFrom().equals(root)) {
          result = result * edge.getWeight() * weightProducts(edge.getTo());
        }
      }
    }
    return result;
  }
}
