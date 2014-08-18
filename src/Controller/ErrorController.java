package Controller;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Error Controller class for Project Management Software, following MVC design pattern. 
 * Controls all error handling.
 * @author  Robert Wolfstein
 */
public class ErrorController {

	// DEV/TEST: refactor ErrorController: it should be the sole object to display
	// message pop-ups
	
	// Singleton design pattern
	private static ErrorController self = new ErrorController();
    public static synchronized ErrorController get() { return self; }
    
    // Error list to be appended to gradually
    private String errors;
    
    private boolean showErrors;
    
    /**
     * Initializes error controller by setting error list to none
     */
    private ErrorController()
    {
    	errors = "";
    	showErrors = true;
    }
    
    public void setShowErrors(boolean show)
    {
    	showErrors = show;
    }
    
    /**
     * Appends an error to the error list
     * @param error New error for error list
     */
    void addError(String error)
    {
    	errors+=error+"\n";
    }
    
    /**
     *  Displays all current errors and then empties the list
     */
    void displayErrors()
    {
    	if (showErrors)
    		JOptionPane.showMessageDialog(null,"The following errors occured:\n\n"+errors);	
    	errors = "";
    }
    
    /**
     * Checks to see if any errors currently exist
     * @return The existence of any errors
     */
    boolean errorsExist()
    {
    	return errors!="";    	
    }
    
    public void showError(String message){
    	
    	JOptionPane.showMessageDialog(null, message);
    	 
    	/*final JOptionPane pane = new JOptionPane(message);
    	 final JDialog d = pane.createDialog(null, "Error");
    	 d.setLocation(150,250);
    	 d.setVisible(true);*/
    }
}
