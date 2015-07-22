# Data Container
As mentioned, a data container in a vocabulary defines a set of data categories to be regulated. The data categories are further organized into hierarhical structures.

An example data container view is as follows (appears when clicking "Data Container" under the id of the vocabulary in the navigational tree).

<img src="../img/data_container.png" width="800" height="600">

In the upper part of the data container view, the policy writer can edit the basic information of the data container, including its ID, Short Description and Long Description. In the lower part of the view, there is a tree showing all data categories defined in the container. When some data category in the tree is selected, the information of the data category is shown in the right part correspondingly.


## Data Category
All data categories in a data container is shown in the data category tree. In the following, we will show how to manage data categories, i.e., add/edit/delete a data category respectively.

### Add a Data Category
To add a data category, one can right click the data category tree and a popup menu will show up as follows.

<img src="../img/add_data_category.png" width="400" height="356">

Here the policy writer has two options to add a data category:

* Add: the new data category is added as the sibling (in the same level) of the selected data category.
* Add Child: the new data category is added as the child of the selected data category. 

Note that in both cases, the id of the new data category should be unique, i.e., a data container cannot have two data categories with the same id (including the [inherited data categories](#inherit)).

### Edit a Data Category
To edit a data category, one can simply click the target data category in the data category tree, and the information of the selected data category is shown in the right panel as follows.

<img src="../img/edit_data_category.png" width="800" height="359">

In the right panel, the policy writer can change the ID, parent ID, Desensitize Operation, Short Description and Long Description of the selected data category.
Here the Desensitize Operation is the operation which can desensitize the data category, i.e., make the data category safe to access. For example, for data category *name*, one desensitize operation can be *truncate* which only retains the last name.
The policy writer should specify all supported desensitize operations for a data category.
The desensitize operations for a data category must be unique, and a data category implicitly inherits all the data sensitize operations defined for its parent categories.

And remember, the ID must be unique, and the referenced parent data category cannot be a cycle.

### Delete a Data Category
To delete a data category, one can simplify click Delete on the popup menu. But note that there are two options to deal with the children data categories:

* Cascade: all children categories of the deleted data category will also be deleted.
* Non-cascade: the children categories of the deleted data category remain, and their parents are set as the parent of the deleted data category.

For example, suppose *ka* (short for *key attribute*) is deleted. In Cascade mode, *email* and *loginid* are deleted as well. While in Non-cascade mode, *email* and *loginid* are not deleted and their parents are set as *employee*.


## [Inherited Data Category](id:inherit)
For a vocabulary, if the base vocabulary is set, then the data categories defined in the base vocabulary is also shown in the data category tree. An example is shown as follows.

<img src="../img/inherited_data_category.png" width="800" height="600">

Here data categories *ka*, *qi*, *sa* are defined in the base vocabulary.
Note that the data categories defined in the base vocabulary are read-only, i.e., they cannot be edited in current vocabulary.
To edit them, one should open the base vocabularly separately, edit then save it, and reload the current vocabulary.

Also remember that the data categories should be unique among the vocabulary and all the base vocabularies.
For example, one cannot define data category *ka* in current vocabulary again since it has already been defiend in the base vocabulary.
