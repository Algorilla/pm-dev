package Controller;

import javax.swing.JOptionPane;

/**
 * Error Controller class for Project Management Software, following MVC design pattern. 
 * Controls all error handling.
 * @author  Robert Wolfstein
 */
public class ErrorController {

	// TODO: Change to use string buffer to manage memory more efficiently
	
	// Singleton design pattern
	private static ErrorController self = new ErrorController();
    public static ErrorController get() { return self; }
    
    // Error list to be appended to gradually
    private String errors;
    
    private boolean showErrors;
    
    /**
     * Initializes error controller by setting error list to none
     */
    ErrorController()
    {
    	errors = "";
    	showErrors = true;
    }
    
    public void SetShowErrors(boolean show)
    {
    	showErrors = show;
    }
    
    /**
     * Appends an error to the error list
     * @param error New error for error list
     */
    void AddError(String error)
    {
    	errors+=error+"\n";
    }
    
    /**
     *  Displays all current errors and then empties the list
     */
    void DisplayErrors()
    {
    	if (showErrors)
    		JOptionPane.showMessageDialog(null,"The following errors occured:\n\n"+errors);	
    	errors = "";
    }
    
    /**
     * Checks to see if any errors currently exist
     * @return The existence of any errors
     */
    boolean ErrorsExist()
    {
    	return errors!="";    	
    }
}
