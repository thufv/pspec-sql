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
 * Refer: http://algowiki.net/wiki/index.php/Edmonds's_algorithm
 * Edmonds's - Chu-Liu algorithm finds a minimum/maximum 
 * branching for a directed graph (similar to a minimum spanning tree).
 *
 * <ul>
 *  <li>Remove all edges going into the root Node<T>
 *  <li>For each Node<T>, select only the incoming edge with smallest weight
 *  <li>For each circuit that is formed:
 *<ul>
 *  <li>edge "m" is the edge in this circuit with minimum weight
 *  <li>Combine all the circuit's Node<T>s into one pseudo-Node<T> "k"
 *  <li>For each edge "e" entering a Node<T> in "k" in the original graph:
 *<ul>
 *  <li>edge "n" is the edge currently entering this Node<T> in the circuit
 *  <li>track the minimum modified edge weight between each "e" based on the following:
 *<ul>
 *  <li>modWeight = weight("e") - ( weight("n") - weight("m") )
 *</ul>
 *</ul>
 *<li>On edge "e" with minimum modified weight, add edge "e" and remove edge "n"
 *</ul>
 * 
 * In high level words
 * 
 * Find a cycle, any cycle.
 * Remove all Node<T>s of the cycle and mark cycles to be broken
 * Recursive: find a cycle, any cycle…
 * If you can’t find a cycle, you hit the terminal case. 
 * Return the current graph (well, the greedy transformation thereof).
 * Now it’s time to break that cycle. Remove the least likely edge.
 * Remove the placeholder Node<T> from the graph and put back in all the Node<T>s from the now-broken cycle. 
 * Return.
 * 
 * 
 */
package org.apache.spark.sql.catalyst.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author Asad
 */
public class EdmondsChuLiu<T> {

  private List<Node<T>> cycle;

  public AdjacencyList<T> getMinBranching(Node<T> root, AdjacencyList<T> list) {
    AdjacencyList<T> reverse = list.getReversedList();
    // remove all edges entering the root
    if (reverse.getAdjacent(root) != null) {
      reverse.getAdjacent(root).clear();
    }
    AdjacencyList<T> outEdges = new AdjacencyList<T>();
    // for each Node<T>, select the edge entering it with smallest weight
    for (Node<T> n : reverse.getSourceNodeSet()) {
      List<Edge<T>> inEdges = reverse.getAdjacent(n);
      if (inEdges.isEmpty()) {
        continue;
      }
      Edge<T> min = inEdges.get(0);
      for (Edge<T> e : inEdges) {
        if (e.getWeight() < min.getWeight()) {
          min = e;
        }
      }
      outEdges.addEdge(min.getTo(), min.getFrom(), min.getWeight());
    }

    // detect cycles
    List<List<Node<T>>> cycles = new ArrayList<List<Node<T>>>();
    cycle = new ArrayList<Node<T>>();
    getCycle(root, outEdges);
    cycles.add(cycle);
    for (Node<T> n : outEdges.getSourceNodeSet()) {
      if (!n.isVisited()) {
        cycle = new ArrayList<Node<T>>();
        getCycle(n, outEdges);
        cycles.add(cycle);
      }
    }

    // for each cycle formed, modify the path to merge it into another part of the graph
    AdjacencyList<T> outEdgesReverse = outEdges.getReversedList();

    for (List<Node<T>> x : cycles) {
      if (x.contains(root)) {
        continue;
      }
      mergeCycles(x, list, reverse, outEdges, outEdgesReverse);
    }
    return outEdges;
  }

  private void mergeCycles(List<Node<T>> cycle, AdjacencyList<T> list, AdjacencyList<T> reverse,
      AdjacencyList<T> outEdges, AdjacencyList<T> outEdgesReverse) {
    List<Edge<T>> cycleAllInEdges = new ArrayList<Edge<T>>();
    Edge<T> minInternalEdge = null;
    // find the minimum internal edge weight
    for (Node<T> n : cycle) {
      for (Edge<T> e : reverse.getAdjacent(n)) {
        if (cycle.contains(e.getTo())) {
          if (minInternalEdge == null || minInternalEdge.getWeight() > e.getWeight()) {
            minInternalEdge = e;
            continue;
          }
        } else {
          cycleAllInEdges.add(e);
        }
      }
    }
    // find the incoming edge with minimum modified cost
    Edge<T> minExternalEdge = null;
    double minModifiedWeight = 0;
    for (Edge<T> e : cycleAllInEdges) {
      double w = e.getWeight()
          - (outEdgesReverse.getAdjacent(e.getFrom()).get(0).getWeight() - minInternalEdge
              .getWeight());
      if (minExternalEdge == null || minModifiedWeight > w) {
        minExternalEdge = e;
        minModifiedWeight = w;
      }
    }
    // add the incoming edge and remove the inner-circuit incoming edge
    Edge<T> removing = outEdgesReverse.getAdjacent(minExternalEdge.getFrom()).get(0);
    outEdgesReverse.getAdjacent(minExternalEdge.getFrom()).clear();
    outEdgesReverse.addEdge(minExternalEdge.getTo(), minExternalEdge.getFrom(),
        minExternalEdge.getWeight());
    List<Edge<T>> adj = outEdges.getAdjacent(removing.getTo());
    for (Iterator<Edge<T>> i = adj.iterator(); i.hasNext();) {
      if (i.next().getTo() == removing.getFrom()) {
        i.remove();
        break;
      }
    }
    outEdges.addEdge(minExternalEdge.getTo(), minExternalEdge.getFrom(),
        minExternalEdge.getWeight());
  }

  private void getCycle(Node<T> n, AdjacencyList<T> outEdges) {
    n.setVisited(true);
    cycle.add(n);
    if (outEdges.getAdjacent(n) == null) {
      return;
    }
    for (Edge<T> e : outEdges.getAdjacent(n)) {
      if (!e.getTo().isVisited()) {
        getCycle(e.getTo(), outEdges);
      }
    }
  }
}
