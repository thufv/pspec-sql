package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class PrivacyParams implements Parsable {
  private double budget;

  private double probability;

  private double noiseRatio;

  @Override
  public void parse(Node budgetNode) {
    NodeList list = budgetNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (ParserConstant.Ele_Policy_DP_Budget.equals(name)) {
        budget = Double.valueOf(node.getTextContent());
      } else if (ParserConstant.Ele_Policy_DP_Accuracy.equals(name)) {
        parseAccuracy(node);
      }
    }
  }

  private void parseAccuracy(Node accuracyNode) {
    NodeList list = accuracyNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (ParserConstant.Ele_Policy_DP_Accuracy_Probability.equals(name)) {
        probability = Double.valueOf(node.getTextContent());
      } else if (ParserConstant.Ele_Policy_DP_Accuracy_Noise.equals(name)) {
        noiseRatio = Double.valueOf(node.getTextContent());
      }
    }
  }

  public double getProbability() {
    return probability;
  }

  public double getNoiseRatio() {
    return noiseRatio;
  }

  public double getBudget() {
    return budget;
  }

}
