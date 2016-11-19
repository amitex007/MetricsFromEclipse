package plugin3.popup.actions;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IProject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class ActionTwo implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public ActionTwo() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		// MetricsFirstExporter mf= new MetricsFirstExporter();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider());
		dialog.setElements(new String[] { "Number of Parameters", "Number of Static Attributes", "Efferent Coupling",
				"Specialization Index", "Number of Classes", "Number of Attributes", "Abstractness",
				"Normalized Distance", "Number of Static Methods", "Number of Interfaces", "Total Lines of Code",
				"Weighted methods per Class", "Number of Methods", "Depth of Inheritance Tree", "Number of Packages",
				"Instability", "McCabe Cyclomatic Complexity", "Nested Block Depth", "Lack of Cohesion of Methods",
				"Method Lines of Code", "Number of Overridden Methods", "Afferent Coupling", "Number of Children" });
		dialog.setTitle("Select metric");
		// user pressed cancel
		if (dialog.open() != Window.OK) {
			return;
		}
		Object[] result = dialog.getResult();

		String pathV = "";

		/*
		 * if (window != null) { IStructuredSelection selection =
		 * (IStructuredSelection) window.getSelectionService().getSelection();
		 * Object firstElement = selection.getFirstElement();
		 * 
		 * if (firstElement instanceof IAdaptable) { IProject project =
		 * (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
		 * IPath path = project.getLocation();
		 * 
		 * 
		 * 
		 * pathV=path.toString(); } }
		 */
		InputDialog y = new InputDialog(window.getShell(), "FilePath", "Enter the output DB file",
				"C:\\ProjectTest\\out6.db", null);
		String ofname = "";
		String param = (String)result[0];
		if (y.open() == Window.OK) {
			ofname = y.getValue();
		}
		final String ofname1 = ofname;
		final String param1 = param;

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				TimeSeriesPlotter tsp = new TimeSeriesPlotter();
				try {
					tsp.init(ofname1, param1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/*
		 * TimeSeriesPlotter tsp= new TimeSeriesPlotter(); try {
		 * tsp.init(ofname1); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// String
		// pathV=org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		MessageDialog.openInformation(shell, "Plugin3", pathV);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
