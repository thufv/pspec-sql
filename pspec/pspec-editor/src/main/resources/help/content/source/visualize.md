#Visualization
To help the policy writer understand and analyze the rules, we have developed some visualization tools as follows.

<img src="../img/visualize.png" width="800" height="600">


The visualization view shows the scope relations among rules by default.
That is, if a rule *r* points to another rule *r'*, then whenever *r* is triggered, *r'* is triggered as well.
In other words, the scope of *r* is included by *r'*.

Moreover, the policy writer can also choose to show the policy analysis results in the graph. The results of redundancy analysis are also edges.
While the results of consistency analysis are hyperedges, since a inconsistent rule set may contain multiple rules.