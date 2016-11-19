package plugin3.popup.actions;

import org.eclipse.core.resources.IProject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class NewAction implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public NewAction() {
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
		InputDialog x = new InputDialog(window.getShell(), "FilePath", "Enter the folder for xml Files", "C:\\ProjectTest\\XML", null);
		String fname = "";
		String ofname = "";
		if (x.open() == Window.OK) {
			fname = x.getValue();
		}
		InputDialog y = new InputDialog(window.getShell(), "FilePath", "Enter the output DB file", "C:\\ProjectTest\\out6.db", null);

		if (y.open() == Window.OK) {
			ofname = y.getValue();
		}
		final String fname1 = fname;
		final String ofname1 = ofname;

		XMLRead xmR = new XMLRead();
		try {
			xmR.parse(fname1, ofname1);
		} catch (Exception e) {

		}
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
