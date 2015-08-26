package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.CategoryContentProvider;
import edu.thu.ss.editor.model.CategoryLabelProvider;
import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.util.PSpecUtil;

public class UserContainerView extends EditorView<VocabularyModel, UserCategory> {

	private TreeViewer userViewer;

	private Text shortDescription;
	private Text longDescription;

	private Text userId;
	private Combo userParentId;
	private Text userShortDescription;
	private Text userLongDescription;

	private UserContainer userContainer;
	private UserCategory selectedUser;
	private TreeItem selectedItem;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public UserContainerView(Shell shell, Composite parent, VocabularyModel model,
			OutputView outputView) {
		super(shell, parent, model, outputView);
		this.userContainer = model.getVocabulary().getUserContainer();
		setBackground(EditorUtil.getDefaultBackground());
		setLayout(new GridLayout(1, false));

		Group infoGroup = EditorUtil.newGroup(this, getMessage(User_Container));
		GridData infoData = new GridData();
		infoData.horizontalAlignment = SWT.FILL;
		infoGroup.setLayoutData(infoData);

		initializeInfo(infoGroup);

		SashForm contentForm = new SashForm(this, SWT.NONE);
		GridData contentData = new GridData();
		contentData.horizontalAlignment = SWT.FILL;
		contentData.verticalAlignment = SWT.FILL;
		contentData.grabExcessVerticalSpace = true;
		contentData.grabExcessHorizontalSpace = true;

		contentForm.setLayoutData(contentData);

		initializeUserTree(contentForm, shell);

		Group categoryGroup = EditorUtil.newGroup(contentForm, null);
		initializeUserInfo(categoryGroup);

	}

	private void refresh(String userId) {
		UserCategory user = userContainer.get(userId);
		if (user != null) {
			userViewer.refresh(user);
		}
	}

	@Override
	public void refresh() {
		userViewer.refresh();
		/*
		boolean hasOutput = model.hasOutput();
		errors.clear();
		model.clearOutput();
		VocabularyAnalyzer analyzer = new VocabularyAnalyzer(table);
		try {
			analyzer.analyze(userContainer, true);
			userContainer.updateRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (hasOutput || model.hasOutput()) {
			outputView.refresh();
		}

		userViewer.refresh();
		*/
	}

	private void initializeInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		shortDescription.setText(userContainer.getShortDescription());
		shortDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				userContainer.setShortDescription(shortDescription.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 20));
		longDescription.setText(userContainer.getLongDescription());
		longDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				userContainer.setLongDescription(longDescription.getText().trim());

			}
		});
	}

	private void initializeUserTree(Composite parent, final Shell shell) {
		userViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL);

		final Tree userTree = userViewer.getTree();
		EditorUtil.processTreeViewer(userViewer);

		userViewer.setLabelProvider(new CategoryLabelProvider<UserCategory>(userContainer, model
				.getErrors()));
		userViewer.setContentProvider(new CategoryContentProvider<UserCategory>(userContainer));
		userViewer.setInput(userContainer);

		userTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedItem = (TreeItem) e.item;
				if (selectedItem == null) {
					return;
				}
				selectedUser = (UserCategory) selectedItem.getData();

				userId.setText(selectedUser.getId());

				userParentId.setItems(EditorUtil.getCategoryItems(userContainer));
				EditorUtil.setSelectedItem(userParentId, selectedUser.getParentId());

				userShortDescription.setText(selectedUser.getShortDescription());
				userLongDescription.setText(selectedUser.getLongDescription());

				if (userContainer.directContains(selectedUser)) {
					enableUserInfo();
				} else {
					disableUserInfo(false);
				}

				//clear message
				if (model.clearOutputByCategory(selectedUser.getId(), MessageType.User_Category)) {
					if (!userContainer.duplicate(selectedUser.getId())) {
						model.getErrors().remove(selectedUser.getId());
						refresh(selectedUser.getId());
					}
					outputView.refresh(OutputType.warning);
				}
			}
		});

		userTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					Menu menu = createTreePopup(userViewer.getTree());
					EditorUtil.showPopupMenu(menu, shell, e);
				}
			}
		});
	}

	private void initializeUserInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		EditorUtil.newLabel(parent, getMessage(User_Category_ID), EditorUtil.labelData());
		userId = EditorUtil.newText(parent, EditorUtil.textData());

		userId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//check empty
				String text = userId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(User_Category_ID_Not_Empty_Message), userId);
					userId.setText(selectedUser.getId());
					userId.selectAll();
					return;
				}
				//check duplicate
				boolean duplicate = false;
				if (!text.equals(selectedUser.getId())) {
					duplicate = userContainer.contains(text);
				} else {
					duplicate = userContainer.duplicate(selectedUser.getId());
				}
				if (duplicate) {
					EditorUtil.showMessage(shell, getMessage(User_Category_ID_Unique_Message, text), userId);
					userId.setText(selectedUser.getId());
					userId.selectAll();
					return;
				}
				String oldId = selectedUser.getId();
				EditorUtil.updateItem(userParentId, selectedUser.getId(), text);
				userContainer.update(text, selectedUser);
				if (model.getErrors().contains(oldId) && !userContainer.duplicate(oldId)) {
					//problem fixed
					model.clearOutputByCategory(oldId, MessageType.User_Category_Duplicate);
					model.getErrors().remove(oldId);
					refresh(oldId);
					outputView.refresh(OutputType.error);
				}
				userViewer.refresh(selectedUser);
			}
		});

		EditorUtil.newLabel(parent, getMessage(User_Category_Parent_ID), EditorUtil.labelData());
		userParentId = EditorUtil.newCombo(parent, EditorUtil.textData());
		userParentId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = userParentId.getText().trim();
				if (text.equals(selectedUser.getId())) {
					EditorUtil
							.showMessage(shell, getMessage(User_Category_Parent_Same_Message), userParentId);
					EditorUtil.setSelectedItem(userParentId, selectedUser.getParentId());
					return;
				}
				if (text.equals(selectedUser.getParentId())) {
					return;
				}
				//check cycle reference
				UserCategory oldParent = selectedUser.getParent();
				UserCategory newParent = userContainer.get(text);
				if (PSpecUtil.checkCategoryCycleReference(selectedUser, newParent, null)) {
					EditorUtil.showMessage(shell,
							getMessage(User_Category_Parent_Cycle_Message, selectedUser.getId()), userParentId);
					EditorUtil.setSelectedItem(userParentId, selectedUser.getParentId());
					return;
				}

				//set new parent
				userContainer.setParent(selectedUser, newParent);

				userParentId.setItems(EditorUtil.getCategoryItems(userContainer));
				EditorUtil.setSelectedItem(userParentId, selectedUser.getParentId());
				refreshViewer(oldParent);
				refreshViewer(newParent);
			}
		});

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		userShortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		userShortDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				selectedUser.setShortDescription(userShortDescription.getText());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		userLongDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		userLongDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		userLongDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				selectedUser.setLongDescription(userLongDescription.getText());

			}
		});

		disableUserInfo(true);

	}

	private void refreshViewer(UserCategory user) {
		if (user == null) {
			userViewer.refresh();
		} else {
			userViewer.refresh(user);
		}
	}

	private Menu createTreePopup(final Control control) {
		final TreeItem selected = userViewer.getTree().getSelectionCount() > 0 ? userViewer.getTree()
				.getSelection()[0] : null;
		Menu popMenu = new Menu(control);
		MenuItem addItem = new MenuItem(popMenu, SWT.PUSH);
		addItem.setText(getMessage(Add));

		addItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UserCategory user = new UserCategory();
				TreeItem parentItem = null;
				if (selected != null) {
					parentItem = selected.getParentItem();
				}
				if (parentItem != null) {
					user.setParentId(parentItem.getText());
				}
				int ret = new UserCategoryDialog(shell, user, userContainer).open();
				if (ret == SWT.OK) {
					if (parentItem != null) {
						UserCategory parent = userContainer.get(parentItem.getText());
						parent.buildRelation(user);
					}
					userContainer.add(user);

					if (parentItem != null) {
						userViewer.refresh(userContainer.get(parentItem.getText()));
					} else {
						userViewer.refresh();
					}
				}
			}
		});

		MenuItem addChildItem = new MenuItem(popMenu, SWT.PUSH);
		addChildItem.setEnabled(selected != null);
		addChildItem.setText(getMessage(Add_Child));

		addChildItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				UserCategory user = new UserCategory();
				user.setParentId(selected.getText());
				int ret = new UserCategoryDialog(shell, user, userContainer).open();
				if (ret == SWT.OK) {
					UserCategory parent = userContainer.get(selected.getText());
					parent.buildRelation(user);
					userContainer.add(user);

					userViewer.refresh(parent);
				}
			}
		});

		boolean editable = (selected != null && userContainer.directContains((UserCategory) selected
				.getData()));
		MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
		deleteItem.setEnabled(editable);
		deleteItem.setText(getMessage(Delete));
		deleteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				UserCategory user = userContainer.get(selected.getText());
				TreeItem parentItem = selected.getParentItem();
				UserCategory parent = null;
				if (parentItem != null) {
					parent = userContainer.get(parentItem.getText());
				}
				MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mb.setText(getMessage(Delete_User));
				mb.setMessage(getMessage(Delete_User_Message));
				int ret = mb.open();

				if (ret == SWT.YES) {
					//remove all
					userContainer.cascadeRemove(user);
					if (parent != null) {
						parent.removeRelation(user);
					}
					refreshViewer(parent);
					disableUserInfo(true);

				} else if (ret == SWT.NO) {
					userContainer.remove(user);
					List<UserCategory> children = user.getChildren();
					if (parent != null) {
						int index = parent.removeRelation(user);
						if (children != null) {
							parent.buildRelation(index, children);
						}
					} else if (children != null) {
						for (UserCategory child : user.getChildren()) {
							child.setParent(null);
						}
						userContainer.getRoot().addAll(children);
					}
					refreshViewer(parent);
					disableUserInfo(true);

				}
			}
		});

		return popMenu;
	}

	private void disableUserInfo(boolean clear) {
		if (clear) {
			userId.setText("");
			userParentId.removeAll();
			userShortDescription.setText("");
			userLongDescription.setText("");
		}

		userId.setEnabled(false);
		userParentId.setEnabled(false);
		userShortDescription.setEnabled(false);
		userLongDescription.setEnabled(false);
	}

	private void enableUserInfo() {
		userId.setEnabled(true);
		userParentId.setEnabled(true);
		userShortDescription.setEnabled(true);
		userLongDescription.setEnabled(true);
	}
}