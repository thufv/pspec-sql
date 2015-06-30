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
package org.apache.spark.sql.catalyst.structure;

/**
 *
 * @author Asad
 */
public class Edge<T> implements Comparable<Edge<T>> {

  private final Node<T> from;
  private final Node<T> to;
  private double weight;

  /**
   * 
   * @param argFrom
   * @param argTo
   * @param bond 
   */
  public Edge(final Node<T> argFrom, final Node<T> argTo, double weight) {
    this.from = argFrom;
    this.to = argTo;
    this.weight = weight;
  }

  /**
   * 
   * @param argEdge
   * @return
   */
  @Override
  public int compareTo(final Edge<T> argEdge) {
    return Double.compare(getWeight(), argEdge.getWeight());
  }

  /**
   * 
   * @param otherObject
   * @return
   */
  @Override
  public boolean equals(Object otherObject) {
    // Not strictly necessary, but often a good optimization
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Edge)) {
      return false;
    }
    Edge<T> otherA = (Edge<T>) otherObject;
    return this.from.equals(otherA.from) && this.to.equals(otherA.to);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + (this.from != null ? this.from.hashCode() : 0);
    hash = 79 * hash + (this.to != null ? this.to.hashCode() : 0);
    return hash;
  }

  /**
   * @return the from
   */
  public Node<T> getFrom() {
    return from;
  }

  /**
   * @return the to
   */
  public Node<T> getTo() {
    return to;
  }

  /**
   * @return the weight
   */
  public double getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NEW_LINE = System.getProperty("line.separator");
    String s = "{Bond " + NEW_LINE + " From Node : " + this.from.toString() + NEW_LINE
        + " To Node: " + this.to.toString() + NEW_LINE + " weight: " + this.weight + "}" + NEW_LINE;
    result.append(s);
    return result.toString();
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

}