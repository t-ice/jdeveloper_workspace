/* $Header: jdev/src/esdk-samples/sample/ApplicationOverview/src/oracle/ide/extsamples/appoverview/MyCompositeDataProvider.java /main/12 2012/03/28 11:37:32 svassile Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Clean up code.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.appoverview;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import oracle.ide.Ide;
import oracle.ide.category.CompositeDataProvider;
import oracle.ide.category.DisplayableTreeCellData;
import oracle.ide.category.OverviewCompositeDataModel;
import oracle.ide.category.OverviewDataModel;
import oracle.ide.index.Index;
import oracle.ide.index.IndexManager;
import oracle.ide.index.QueryCriteria;
import oracle.ide.index.file.FileCriteria;
import oracle.ide.index.task.BackgroundTask;
import oracle.ide.model.Displayable;
import oracle.ide.model.Project;
import oracle.ide.model.Workspace;
import oracle.ide.net.URLFileSystem;

import oracle.javatools.util.Pair;


public final class MyCompositeDataProvider extends CompositeDataProvider {

    private static final String MY_COMPOSITE = "MyComposite";
    private static final String JAVA = ".java";
    private static final String TXT = ".txt";

    /**
     * Determines the content for the MyCompositeDataProvider Category
     *
     * The BlockingQueue is used to let this method communicate intermediate results
     * rather than having to wait for the entire result set to be determined before any
     * results are shown.
     *
     * When gathering content the "unchecked" status bucket is where content should be placed.
     * <p>
     * Note the check of the <code>_cancelContent</code> member which is used to signal
     * when the user wants to stop gathering content.
     * @param project the project to search for content in
     */
    @Override
    public void determineContentForProject(Project project) {
      final Workspace workspace = Ide.getActiveWorkspace();
      
      // First handle files that indicate that a composite is needed
      handleIndicatorURLs(workspace, project);

      // Next handle files that will belong to the composite
      handleIndicatorChildURLs(workspace, project);
    }


    /**
     * Searches for files that are indicators that a composite needs to be created.
     * In this example any url named MyCompositeXX.java is an indicator url that we
     * will create a composite object for.
     * @param project the project to search
     */
    private void handleIndicatorURLs(final Workspace workspace, final Project project) {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.put(FileCriteria.FILE_EXTENSION, JAVA);
        criteria.put(FileCriteria.FILE_NAME_STARTS_WITH, MY_COMPOSITE);

        searchIndex(workspace, project, criteria, new Callback<List<URL>>() {
                    public void call(List<URL> urls) {
                        processIndicatorURLs(project, urls);
                    }
                });
    }

    private void searchIndex(Workspace workspace, Project project, final QueryCriteria criteria,
                             Callback<List<URL>> urlsCallback) {
        // Get index manager, define a query
        final IndexManager iMgr = IndexManager.getIndexManager();
        final Index index = iMgr.getIndex(workspace, project);
        try {

            // Create a blocking queue to get intermediate results
            final BlockingQueue<URL> urlQueue = new LinkedBlockingQueue<URL>();

            // Start the query
            final BackgroundTask<URL[]> future =
                index.query(criteria, urlQueue);

            // Create a list to hold the urls
            final List<URL> urlList = new ArrayList<URL>();

            while (!future.isCancelled() && !future.isDone() &&
                   !_cancelContent) {
                try {
                    Thread.sleep(200);
                    urlQueue.drainTo(urlList);
                    if (urlList.size() > 0) {
                        if (urlList.contains(Index.END_OF_RESULTS)) {
                            urlList.remove(Index.END_OF_RESULTS);
                        }

                        urlsCallback.call(urlList);
                    }

                    urlList.clear();
                } catch (InterruptedException iex) {
                    future.cancel(true);
                    return;
                }
            }

            if (future.isDone()) {
                // Make sure to pull any final data remaining in the queue
                urlQueue.drainTo(urlList);
                if (urlList.size() > 0) {
                    if (urlList.contains(Index.END_OF_RESULTS)) {
                        urlList.remove(Index.END_OF_RESULTS);
                    }
                    urlsCallback.call(urlList);
                }
            }
        } finally {
            index.release();
        }
    }

    /**
     * This is used to create the Displayable that will be used to represent the
     * composite object and will add the url that was used as an indicator to the
     * Displayable as a child.
     * @param project the project that contains the urls
     * @param urlList the list of indicator urls found
     */
    private void processIndicatorURLs(Project project, List<URL> urlList) {
        if (urlList == null) return;

        final OverviewCompositeDataModel dataModel = (OverviewCompositeDataModel) getDataModel();

        for (URL indicatorURL : urlList) {
            Displayable displayable = createDisplayable(indicatorURL);
          
            dataModel.addDisplayable(project, displayable,
                                        OverviewDataModel.UNCHECKED, null);
            dataModel.addURLToDisplayable(project, indicatorURL,
                                          OverviewDataModel.UNCHECKED,
                                             displayable);
        }
    }
    

    /**
     * Searches for files that should belong to the composite object
     * In this example any url that begins with MyCompositeXX.txt will be considered
     * a child object of the composite object with the same name (eg, if we created
     * a composite object for MyComposite1.java and we find MyComposite1a.txt then
     * MyComposite1a.txt is assigned as a child of MyComposite1.)
     * @param project the project to search
     */
    private void handleIndicatorChildURLs(final Workspace workspace, final Project project) {

        final QueryCriteria criteria = new QueryCriteria();
        criteria.put(FileCriteria.FILE_EXTENSION, TXT);
        criteria.put(FileCriteria.FILE_NAME_STARTS_WITH, MY_COMPOSITE);

        searchIndex(workspace, project, criteria, new Callback<List<URL>>() {
                    public void call(List<URL> urls) {
                        processChildURLs(project, urls);
                    }
                });
    }

    /**
     * Processes the child urls found and adds them to the StatusResult object as
     * a child of the appropriate Displayable
     * @param project
     * @param urlList
     */
    private void processChildURLs(Project project, List<URL> urlList) {
        final OverviewCompositeDataModel dataModel = (OverviewCompositeDataModel) getDataModel();

        if (urlList != null) {
            final List<DefaultMutableTreeNode> displayableNodeList =
                dataModel.getDisplayables(project, null);

            for (URL url : urlList) {
                final Displayable displayable =
                    findDisplayableForURL(url, displayableNodeList);
                dataModel.addURLToDisplayable(project, url,
                                              OverviewDataModel.UNCHECKED,
                                              displayable);
            }
        }
    }

    /**
     * Finds the Displayable that should act as the parent of the given url
     * @param url
     * @param displayableNodeList
     * @return
     */
    private Displayable findDisplayableForURL(URL url,
                                              List<DefaultMutableTreeNode> displayableNodeList) {
        final String matchString = URLFileSystem.getName(url);
        for (DefaultMutableTreeNode displayableNode : displayableNodeList) {
            final DisplayableTreeCellData cellData =
                (DisplayableTreeCellData)displayableNode.getUserObject();
            final Displayable displayable = cellData.getDisplayable();
            final String displayableString = displayable.getShortLabel();
            if (matchString.startsWith(displayableString)) {
                return displayable;
            }
        }

        return null;
    }

    private static String suffix(URL url) {
        return URLFileSystem.getSuffix(url);
    }

    private static String name(URL url) {
        return URLFileSystem.getName(url);
    }

    private boolean textComposite(URL url) {
        return composite(url, ".txt");
    }

    private boolean javaComposite(URL url) {
        return composite(url, ".java");
    }

    private boolean composite(URL url, String suffix) {
        return name(url).startsWith("MyComposite") &&
            suffix.equals(suffix(url));
    }

    /**
     * This method is called to determine if a URL belongs to the category.
     * @param project the project where the url will be added, removed, or modified in
     * @param url the url that is being added, removed or modified
     * @return true if the URL belongs to the category; false otherwise
     */
    public boolean accept(Project project, URL url) {
        return textComposite(url) || javaComposite(url);
    }


    /**
     * This method is called when a file is added to a project. It will return
     * true if the specified project and url belong to the category and will add
     * the url to the StatusResult's unchecked status.
     *
     * This implementation creates an Element if necessary and builds up it's
     * child structure.  The StatusResult's element and url lists are both handled.
     *
     * @param project to add the url to
     * @param url the url that is being added
     * @return true if the given URL is one that belongs to the category; false
     * otherwise
     */
    public boolean addURL(Project project, URL url) {
        final OverviewCompositeDataModel dataModel = (OverviewCompositeDataModel) getDataModel();

        if (javaComposite(url)) {
            MyCompositeDisplayable displayable =
                new MyCompositeDisplayable(url);
            dataModel.addDisplayable(project, displayable,
                                    OverviewDataModel.UNCHECKED, null);
            dataModel.addURLToDisplayable(project, url,
                                          OverviewDataModel.UNCHECKED,
                                          displayable);
            return true;
        }

        if (textComposite(url)) {
            final Displayable parent =
                findDisplayableForURL(url, dataModel.getDisplayables(project,
                                                                        null));
            if (parent != null) {
               dataModel.addURLToDisplayable(project, url,
                                             OverviewDataModel.UNCHECKED,
                                             parent);
                return true;
            }
        }
    
        return false;
    }

  public boolean isComposite(Project project, URL url)
  {
    return javaComposite(url) || textComposite(url);
  }

  public boolean isChild(Project project, URL url)
  {
    return !javaComposite(url) && !textComposite(url);
  }

    /**
     * This method is called when a file is removed from a project. It will return
     * true if the specified project and url belong to the category and will remove
     * the url from the status bucket where it currently resides.
     *
     * This implementation ensures that both the Element and the URL list in the
     * StatusResult are handled
     *
     * @param project to remove the url from
     * @param url that is being removed
     * @return true if the given URL is one that belongs to the category; false
     * otherwise
     */
    public boolean removeURL(Project project, URL url) {
      OverviewCompositeDataModel dataModel = (OverviewCompositeDataModel) getDataModel();
        if (javaComposite(url)) {
            MyCompositeDisplayable displayable =
                new MyCompositeDisplayable(url);
            dataModel.removeDisplayable(project, displayable);
            return true;
        }

        if (textComposite(url)) {
            dataModel.removeURL(project, url);
            return true;
        }

        return false;
    }

    /**
     * This method is called when a file is modified
     * The implementor should return true if the URL is one that is
     * part of the category
     *
     * This implementation re-verifies the status of the URL and it's containing
     * element, moving each of them to the appropriate status list if their status
     * has changed.
     *
     * @param project whose url is being modified
     * @param url that is being removed
     * @return true if the given URL is one that belongs to the category; false
     * otherwise
     */
    public boolean modifiedURL(Project project, URL url) {
        final OverviewCompositeDataModel dataModel = (OverviewCompositeDataModel) getDataModel();

        if (textComposite(url)) {
            // First get the old status
            final Integer oldStatus = dataModel.getURLStatus(project, url);
            if (oldStatus == null)
                return false; // Not previously found, this should be an error

            // Derive the status of the URL
            final int newStatus = determineStatusForURL(project, url);

            if (oldStatus.intValue() != newStatus) {
                dataModel.changeURLStatus(project, url, newStatus);

                // Status of the url has changed, so re-derive the status of the composite
                // First need to find the displayable
                final Pair displayableStatusPair =
                    dataModel.getDisplayableStatusPairForURL(project, url);
                if (displayableStatusPair != null) {
                    final Displayable displayable =
                        (Displayable)displayableStatusPair.getFirst();

                    // Get the list of urls that make up the composite
                    final List<URL> displayableURLs =
                        dataModel.getURLsForDisplayable(project,
                                                           displayable);

                    // Get the composite's status (which is based on all its urls)
                    int newCompositeStatus =
                        dataModel.getHighestSeverity(project,
                                                        displayableURLs);

                    // Check if the  status has changed
                    int oldCompositeStatus =
                        ((Integer)displayableStatusPair.getSecond()).intValue();
                    if (oldCompositeStatus != newCompositeStatus) {
                        dataModel.changeDisplayableStatus(project,
                                                             displayable,
                                                             newCompositeStatus);
                    }
                }
            }

            return true;
        }
        return false;
    }


  public Displayable createDisplayable(URL url)
  {
    return new MyCompositeDisplayable(url);
  }

  private static interface Callback<T> {
        public void call(T parameter);
    }

    private static final class MyCompositeDisplayable implements Displayable {
        private URL indicatorURL;

        public MyCompositeDisplayable(URL url) {
            indicatorURL = url;
        }

        public String getShortLabel() {
            return toString();
        }

        public String getLongLabel() {
            return toString();
        }

        public Icon getIcon() {
            return null;
        }

        public String getToolTipText() {
            return toString();
        }

        public String toString() {
            return URLFileSystem.getName(indicatorURL);
        }
    }
}
