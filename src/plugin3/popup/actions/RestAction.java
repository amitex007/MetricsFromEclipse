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

public class RestAction implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public RestAction() {
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
		
		String pathV = "";

		
		InputDialog y = new InputDialog(window.getShell(), "FilePath", "Enter the output DB file",
				"C:\\ProjectTest\\out6.db", null);
		String ofname = "";
		
		if (y.open() == Window.OK) {
			ofname = y.getValue();
		}
		InputDialog xy = new InputDialog(window.getShell(), "Rest Service URL", "Enter the url for rest service",
				"C:\\ProjectTest\\out6.db", null);
		String url = "";
		
		if (xy.open() == Window.OK) {
			url = xy.getValue();
		}
		try
		{
			JSONsender jm= new JSONsender();
			jm.init(ofname,url);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

		
		MessageDialog.openInformation(shell, "Plugin3", pathV);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
