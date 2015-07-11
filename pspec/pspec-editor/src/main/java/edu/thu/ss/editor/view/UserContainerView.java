package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
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
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.util.PSpecUtil;

public class UserContainerView extends Composite {

	private TreeViewer userViewer;
	private Display display;
	private Shell shell;

	private Text containerId;
	private Text shortDescription;
	private Text longDescription;

	private Text userId;
	private Combo userParentId;
	private Text userShortDescription;
	private Text userLongDescription;

	private VocabularyModel model;
	private UserContainer userContainer;
	private UserCategory selectedUser;
	private TreeItem selectedItem;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public UserContainerView(Shell shell, Composite parent, int style, VocabularyModel model) {
		super(parent, SWT.NONE);
		this.shell = shell;
		this.display = Display.getDefault();
		this.model = model;
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

	private void initializeInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		EditorUtil.newLabel(parent, getMessage(User_Container_ID), EditorUtil.labelData());
		containerId = EditorUtil.newText(parent, EditorUtil.textData());
		containerId.setText(userContainer.getId());
		containerId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = containerId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(User_Container_ID_Empty_Message), containerId);
					containerId.setText(userContainer.getId());
					containerId.selectAll();
					return;
				}
				userContainer.setId(containerId.getText().trim());

			}
		});

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

		userViewer.setLabelProvider(new CategoryLabelProvider(userContainer));
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

				if (userContainer.directContains(selectedUser.getId())) {
					enableUserInfo();
				} else {
					disableUserInfo(false);
				}
			}
		});

		userTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					Point p = ((Control) e.widget).toDisplay(e.x, e.y);
					Menu menu = createTreePopup(userViewer.getTree());
					menu.setLocation(p);
					menu.setVisible(true);
					while (!menu.isDisposed() && menu.isVisible()) {
						if (!display.readAndDispatch())
							display.sleep();
					}
					menu.dispose();
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
					EditorUtil.showMessage(shell, getMessage(User_Category_ID_Empty_Message), userId);
					userId.setText(selectedUser.getId());
					userId.selectAll();
					return;
				}
				//check duplicate
				if (!text.equals(selectedUser.getId()) && userContainer.get(text) != null) {
					EditorUtil.showMessage(shell, getMessage(User_Category_ID_Unique_Message, text), userId);
					userId.setText(selectedUser.getId());
					userId.selectAll();
					return;
				}

				EditorUtil.updateItem(userParentId, selectedUser.getId(), text);

				userContainer.update(text, selectedUser);

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
				if (PSpecUtil.checkCycleRefernece(selectedUser, newParent)) {
					EditorUtil.showMessage(shell, getMessage(User_Category_Parent_Cycle_Message),
							userParentId);
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

		boolean editable = (selected != null && userContainer.directContains(selected.getText()));
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
					if (parent != null) {
						int index = parent.removeRelation(user);
						if (user.getChildren() != null) {
							parent.buildRelation(index, user.getChildren());
						}
					} else if (user.getChildren() != null) {
						userContainer.getRoot().addAll(user.getChildren());
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