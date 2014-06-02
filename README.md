README
=====
##Resources  
Resource files, such as images or SQLite files, should all be placed in the 'resources' directory. This ensures all resources are synced to git.

Relative file paths can now be used to access these files, which makes the program system independent. Simply write file paths in the form "./resources/subdir/file.ext", e.g: "./resources/img/banner-Process.jpg".
 

## JavaDoc
Please try to follow convention set out in PModel package files. JavaDoc comments should take the following form:

		/**
		 * Description of function
		 *
		 * @param paramName1 Desription of param
		 * @param paramName2 Description of param
		 *...
		 * @return Description of return (no need to enter a variable name/type here)
		 * @throws ThrownExceptionType
		 */

 It is very important to follow this format, else the JavaDoc does not generate correctly. A quick shortcut in eclipse:
 		Once the function signature is written, type /**\<return\> right above it, and the JavaDoc skeleton will be 
 		automatically formed, and you just need to fill in the descriptions. For example the following function would
 		generate the following skeleton:

 		/**
		 * 
		 * @param project
		 * @return
		 * @throws NonexistantProjectException
		 */
		public int getID(Project project) throws NonexistantProjectException {...}

 		Appropriately filled, this comment would look like this:

 		/**
 		 * Find the ID of a given project.
 		 *
 		 * @param project The project for which we wish to find the ID
 		 * @return The project's ID 
 		 * @throws NonexistantProjectException
 		 */
 		public int getID(Project project) throws NonexistantProjectException {...}

 		Do not include other parameters like @author, these do not work here. Besides, these should be
 		as short as we can make them, there's bound to be a ton by the end of this, so keep them
 		concise please.

