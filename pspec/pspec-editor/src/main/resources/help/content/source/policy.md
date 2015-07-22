#Policy
A Policy consists of a set of rules. Each rule consists of three parts: user reference, data reference and restrictions.
Briefly, each rule states certain user category can access certain data categories only when the restrictions are satisfied.
We require a query should satisfy all the applicable rules.
For detailed information about semantics of PSpec rules, please refer to our [technical report](TODO).

Beforing adding any rules, the policy writer must set the referred vocabulary for a policy, and the rules can refer user/data categories defined in the vocabulary (including all the base vocabularies).

## Policy Information
To edit policy information, simply click the id of the policy in the navigation tree. An example is shown as follows.

<img src="../img/policy.png" height="600" width="800" />

In policy information view, the policy writer can edit the Policy ID, referred Vocabulary, Short/Long Description and contact information of the issuer.

