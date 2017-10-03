package oracle.ide.extsamples.projectinspector;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.SOUTHEAST;
import static java.awt.GridBagConstraints.WEST;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import oracle.ide.Ide;
import oracle.ide.controls.tree.CustomJTree;
import oracle.ide.controls.tree.JMutableTreeNode;
import oracle.ide.controls.tree.JTreeCellData;
import oracle.ide.controls.tree.TreeCellCheckedEvent;
import oracle.ide.controls.tree.TreeCellCheckedListener;
import oracle.ide.inspector.PropertyLabel;
import oracle.ide.model.Dependable;
import oracle.ide.model.Element;
import oracle.ide.model.Folder;
import oracle.ide.model.Locatable;
import oracle.ide.model.Node;
import oracle.ide.model.Project;
import oracle.ide.model.ResourcePaths;
import oracle.ide.model.Workspace;
import oracle.ide.net.URLFileSystem;
import oracle.ide.util.Assert;
import oracle.ide.util.TriStateBoolean;

import oracle.jdeveloper.deploy.DeployUtil;
import oracle.jdeveloper.deploy.dt.Deployment;
import oracle.jdeveloper.deploy.dt.DeploymentProfiles;
import oracle.jdeveloper.deploy.dt.Profile;
import oracle.jdeveloper.model.ProjectDependencyFactory;


final class ProjectDependencyPanel extends JPanel {

  private JMutableTreeNode rootNode = new JMutableTreeNode();

  private final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
  private final CustomJTree tree = new CustomJTree(treeModel);
  //    private final JButton orderingButton = new JButton("Ordering...");

  private List dependencies;

  private final ProjectPropertyModel model;

  ProjectDependencyPanel(ProjectPropertyModel model) {
    this.model = model;
    setBackground(Color.WHITE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = c.gridy = 0;
    c.gridwidth = c.gridheight = 1;
    addPrompt(c);
    addTree(c);
    addButtons(c);
  }
  
  private void addPrompt(GridBagConstraints c) {
    c.anchor = WEST;
    c.gridy++;
    c.fill = HORIZONTAL;
    add(prompt(), c);
  }
  
  private JLabel prompt() {
    JLabel prompt = new PropertyLabel("Project Dependencies:");
    prompt.setBackground(getBackground());
    prompt.setLabelFor(tree);
    return prompt;
  }
  
  private void addTree(GridBagConstraints c) {
    c.gridy++;
    c.fill = BOTH;
    c.weightx = c.weighty = 1.0;
    c.insets = new Insets(0, 0, 5, 0);
    add(scrollForTree(), c);
  }

  private Component scrollForTree() {
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);
    // Must setScrollsOnExpand to true to stop the tree from automatically scrolling horizontally when a node label is
    // wider that the width of the tree. Setting this property along with adding a labelElement to folder nodes fixes 
    // this problem.
    tree.setScrollsOnExpand(true);
    tree.setPropagateCheckState(false);
    //      final L l = new L();
    //      _btnOrdering.addActionListener(l);
    //      _depsTree.addTreeExpansionListener(l);
    //      _depsTree.addTreeCellCheckedListener(l);
    tree.addTreeCellCheckedListener(new TreeCellCheckedListenerImpl());
    tree.addTreeExpansionListener(new TreeExpansionListenerImpl());
    tree.setPreferredSize(null);
    JScrollPane scrollPane = new JScrollPane(tree);
    return scrollPane;
  }

  private void addButtons(GridBagConstraints c) {
    c.gridy++;
    c.anchor = SOUTHEAST;
    c.fill = NONE;
    c.insets = new Insets(0, 0, 0, 0);
//    add(buttonBox(), c);
  }
  
  List dependencies() { return dependencies; }

  void dependencies(List dependencies) {
    this.dependencies = dependencies;
    populateTree();
  }

  private void populateTree() {
    final Enumeration i = tree.getExpandedDescendants(new TreePath(rootNode));
    final List expanded = new ArrayList();
    if (i != null) {
      while (i.hasMoreElements()) {
        TreePath treePath = (TreePath)i.nextElement();
        JMutableTreeNode node = (JMutableTreeNode)treePath.getLastPathComponent();
        expanded.add(node.getUserObject());
      }
    }
    rootNode = createTreeNode(null, workspace(), false);
    addChildren(rootNode, true);
    treeModel.setRoot(rootNode);
    restoreOrInitExpandedTreeNodes(rootNode, i != null, expanded);
  }

  private JMutableTreeNode createTreeNode(Element parent, Element element, boolean withCheckBox) {
    TriStateBoolean isSelected = TriStateBoolean.getState(withCheckBox && isSelected(parent, element));
    JTreeCellData data = new JTreeCellData(element.getIcon(), element.getShortLabel(), withCheckBox, isSelected);
    JMutableTreeNode node = new JMutableTreeNode(data, element.mayHaveChildren()) {
        //  We need to override this method so that the tree draws the + indicating that the node may have children.
        public boolean isLeaf() { return !allowsChildren; }
      };
    node.setUserObject(element);
    return node;
  }

  private boolean isSelected(Element parent, Element element) {
    if (dependencies == null) return false;
    final URL elementUrl = tryGetURL(element);
    if (elementUrl != null) {
      Iterator i = dependencies.iterator();
      while (i.hasNext()) {
        Dependable dependable = (Dependable)i.next();
        if (dependable != null && URLFileSystem.equals(elementUrl, tryGetURL(dependable.getSource())))
          return true;
      }
      return false;
    } 
    // This part is to verify selection for a fusion profile.
    URL parentUrl = tryGetURL(parent);
    if (parentUrl != null && parent instanceof Project && element instanceof Profile) {
      URL profileURL = DeployUtil.getProfileURL(parentUrl, (Profile)element);
      Iterator i = dependencies.iterator();
      while (i.hasNext()) {
        final Dependable dep = (Dependable)i.next();
        if (dep != null && URLFileSystem.equals(profileURL, tryGetURL(dep.getSource())))
          return true;
      }
    }
    return false;
  }

  private URL tryGetURL(Object o) {
    return o instanceof Locatable ? ((Locatable)o).getURL() : null;
  }

  private void addChildren(JMutableTreeNode parent, boolean withCheckBox) {
    final Element element = (Element)parent.getUserObject();
    if (!element.mayHaveChildren()) return;
    final URL currentProjectURL = project().getURL();
    if (element instanceof Workspace) {
      final Iterator children = element.getChildren();
      // Sort the list of projects alphabetically.
      List<Element> sortedList = new ArrayList<Element>();
      while (children.hasNext()) sortedList.add((Element)children.next());
      Collections.sort(sortedList, new Comparator<Element>() {
        public int compare(Element o1, Element o2) {
          return o1.getShortLabel().compareTo(o2.getShortLabel());
        }
      });
      for (Element child: sortedList)
        if (isShownInTree(child, withCheckBox) && !isCurrentProject(child, currentProjectURL))
          parent.add(createTreeNode(element, child, withCheckBox));
      return;
    } 
    // Pre-fusion logic.
    if (!(element instanceof Profile)) {
      Iterator children;
      if (element instanceof Project) {
        //  bug 4203533 -- performance optimized by scanning only the Resources content set.
        final ResourcePaths resourcePaths = ResourcePaths.getInstance((Project)element);
        children = resourcePaths.getResourcesContentSet().nodeIterator(""); //NOTRANS
      } else {
        // deployment container profile
        children = element.getChildren();
      }
      List excludeList = new ArrayList();
      while (children.hasNext()) {
        Element child = (Element)children.next();
        if (isShownInTree(child, withCheckBox))
          parent.add(createTreeNode(element, child, withCheckBox));
        //  This case is intended to cover the BC4J container profile.
        else if (Deployment.isDeploymentContainerProfile(child)) {
          parent.add(createTreeNode(element, child, false)); // bug 3141134
          final Iterator containedProfiles = child.getChildren();
          while (containedProfiles.hasNext())
            excludeList.add(containedProfiles.next());
        }
      }
      PRUNE_BC4J_CONTAINED_PROFILES: {
        // Unreported bug: without this pruning, the children of a BC4J container profile are shown twice in the 
        // Dependencies tree.  This strips out the copy that's a direct child of the project.
        if (!excludeList.isEmpty()) {
          final int n = parent.getChildCount();
          for (int i = n - 1; i >= 0; i--) {
            final JMutableTreeNode node = (JMutableTreeNode)parent.getChildAt(i);
            final Object userObj = node.getUserObject();
            if (excludeList.contains(userObj))
              parent.remove(node);
          }
        }
      }
    }
    // We need to add fusion profiles to the dependencies here...
    if (element instanceof Project) {
      // for each profile in the project, we need to add it to tree (parent).
      Project project = (Project)element;
      DeploymentProfiles profiles = DeploymentProfiles.getInstance(project);
      Map profileMap = profiles.getDefinedProfiles(true);
      Iterator profileIter = profileMap.entrySet().iterator();
      while (profileIter.hasNext()) {
        Map.Entry entry = (Map.Entry)profileIter.next();
        Profile profile = (Profile)entry.getValue();
         JMutableTreeNode treeNode = createTreeNode(element, profile, (withCheckBox && !(profile.mayHaveChildren())));
        if (treeNode != null) parent.add(treeNode);
        // We add any contained profiles here for
        if (profile.mayHaveChildren()) {
          Iterator iter = profile.getChildren();
          while (iter != null && iter.hasNext()) {
            Profile childProfile = (Profile)iter.next();
            JMutableTreeNode childTreeNode = createTreeNode(element, childProfile, withCheckBox);
            if (childTreeNode != null) treeNode.add(childTreeNode);
          }
        }
      }
    }
  }

  private boolean isShownInTree(Element child, boolean withCheckBox) {
    return !withCheckBox || ProjectDependencyFactory.hasFactory(child);
  }

  private boolean isCurrentProject(Element child, URL curProjectURL) {
    return child instanceof Project && URLFileSystem.equals(curProjectURL, ((Project)child).getURL());
  }

  private void restoreOrInitExpandedTreeNodes(JMutableTreeNode parent, boolean restore, List expandedElements) {
    final Enumeration iter = parent.children();
    while (iter.hasMoreElements()) {
      final JMutableTreeNode node = (JMutableTreeNode)iter.nextElement();
      final Element elem = (Element)node.getUserObject();
      //  If the tree had a previous expansion state, restore it.
      //  Otherwise, expand all nodes that have a dependency in them.
      if ((restore && expandedElements.contains(elem)) || (!restore && containsDependable(elem)))
        tree.expandPath(new TreePath(node.getPath()));
      if (!node.isLeaf())
        restoreOrInitExpandedTreeNodes(node, restore, expandedElements);
    }
  }

  private boolean containsDependable(Element element) {
    if (dependencies == null) return false;
    final Iterator iter = dependencies.iterator();
    while (iter.hasNext()) {
      final Dependable dependable = (Dependable)iter.next();
      if (dependable != null && (dependable.getSource() == element || dependable.getSourceOwner() == element))
        return true;
    }
    return false;
  }

  private class TreeExpansionListenerImpl implements TreeExpansionListener {
    public void treeExpanded(TreeExpansionEvent event) {
      final TreePath path = event.getPath();
      final JMutableTreeNode node = (JMutableTreeNode)path.getLastPathComponent();
      //  Try expanding non leaf nodes that don't currently show as having any children.
      if (node.isLeaf()) return;
      Ide.getWaitCursor().show();
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          if (node.getChildCount() == 0) {
            addChildren(node, true);
            treeModel.reload(node);
          }
          Ide.getWaitCursor().hide();
        }
      });
    }

    public void treeCollapsed(TreeExpansionEvent event) {}
  }

  private class TreeCellCheckedListenerImpl implements TreeCellCheckedListener {
    public void cellChecked(TreeCellCheckedEvent e) {
      final JMutableTreeNode node = (JMutableTreeNode)e.getSource();
      final Folder dependableOwner = getDependableSourceOwner(node);
      final Element dependableSource;
      if (getDepSource(node) instanceof Profile)
        dependableSource = new ProfileDependable((Project)dependableOwner, (Profile)getDepSource(node));
      else
        dependableSource = getDepSource(node);
      try {
        final Dependable dependable = ProjectDependencyFactory.findOrCreate(dependableSource, dependableOwner);
        if (dependable != null) {
          if (dependencies == null) dependencies = new ArrayList();
          dependencies.add(dependable);
          // updateOrderingButton();
        }
      } catch (Exception ex) {
        Assert.printStackTrace(ex);
      }
    }

    private Folder getDependableSourceOwner(JMutableTreeNode node) {
      JMutableTreeNode curNode = node;
      while (true) {
        curNode = (JMutableTreeNode)curNode.getParent();
        if (curNode == null) return null;
        final Object depSourceOwner = curNode.getUserObject();
        if (depSourceOwner instanceof Project || depSourceOwner instanceof Workspace)
          return (Folder)depSourceOwner;
      }
    }

    public void cellUnchecked(TreeCellCheckedEvent e) {
      if (dependencies == null) return;
      final JMutableTreeNode node = (JMutableTreeNode)e.getSource();

      final URL depSourceURL;
      if (node.getUserObject() instanceof Profile) {
        // fusion logic
        final Project project = getParentProject(node);
        final Profile profile = (Profile)node.getUserObject();
        depSourceURL = DeployUtil.getProfileURL(project, profile);
      } else {
        //pre-fusion logic
        depSourceURL = tryGetURL(getDepSource(node));
      }
      final URL depOwnerURL = tryGetURL(getDependableSourceOwner(node));
      for (int i = 0; i < dependencies.size(); i++) {
        final Dependable dependable = (Dependable)dependencies.get(i);
        if (matches(dependable, depSourceURL, depOwnerURL)) {
          //  Don't use Iterator.remove() because the underlying List is not guaranteed to support it.
          dependencies.remove(i);
        }
      }
      // updateOrderingButton();
    }

    private Element getDepSource(JMutableTreeNode node) {
      return (Element)node.getUserObject();
    }

    private boolean matches(Dependable dep, URL depSourceURL, URL depOwnerURL) {
      final Element depSource = dep.getSource();
      Folder dependableSourceOwner = dep.getSourceOwner();
      if (dependableSourceOwner == null && depSource instanceof Project) {
        // In the process of fixing bug 2716925, we ended up with a case where you wouldn't be able to remove a 
        // dependency from an existing project, where the Dependable's sourceOwnerURL is null. So if we find a null 
        // depSourceOwner for a depSource that's a project, we internally force the depSourceOwner to be the current 
        // workspace.
        dependableSourceOwner = workspace();
      }
      return URLFileSystem.equals(tryGetURL(depSource), depSourceURL) && 
        URLFileSystem.equals(tryGetURL(dependableSourceOwner), depOwnerURL);
    }

    private Project getParentProject(JMutableTreeNode node) {
      JMutableTreeNode curNode = node;
      while (true) {
        curNode = (JMutableTreeNode)curNode.getParent();
        if (curNode == null) return null;
        final Object obj = curNode.getUserObject();
        if (obj instanceof Project) return (Project)obj;
      }
    }
  }
  
  private Project project() { return model.project(); }
  private Workspace workspace() { return model.context().getWorkspace(); }
  
  private static class ProfileDependable extends Node {
    private Profile profile;

    ProfileDependable(Project project, Profile profile) {
      setURL(DeployUtil.getProfileURL(project, profile));
      this.profile = profile;
    }

    public Object getData() { return profile; }
  }
  
}
