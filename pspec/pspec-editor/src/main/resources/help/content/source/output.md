#Output
To inform the policy writer the possible messages, the editor includes an eclipse-style output view, which lies in the lower part of the editor.
The output view contains three messages, namely warning, error and analysis.

## Warning Message
Warning messages are those minor problems that can be automatically fixed by the editor.
Some examples include some restriction specifies some unsupported desensitize operation for some data category, user/data categories forms a cycle, the parent of some user/data category does not exist and etc.

## Error Message
Error messages are those serious problems that must be manually fixed by by the policy writer.
Some examples include the data categories in an associated data reference overlap, a rule refers non-exist user/data categories, the excluded user/data category is not a descedant of the target user/data category and etc.
Once the problem is fixed, the corresponding error message disappears automatically.

## Analysis Message
Analysis messages are the outputs of running the [policy analysis](analysis.html) algorithms.
For messages of simplification and analysis messages, one can remove the redundant element by click "Fix" menu on the popup menu of the message item.
Currently, the messages for redundancy and consistency analysis message are automatically cleared when some rule is changed.