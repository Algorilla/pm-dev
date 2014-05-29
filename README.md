README
=====
##Resources  
Resource files, such as images or SQLite files, should all be placed in the 'resources' directory. This ensures all resources are synced to git.

Relative file paths can now be used to access these files, which makes the program system independent. Simply write file paths in the form "./resources/subdir/file.ext", e.g: "./resources/img/banner-Process.jpg".
 

Dave
===
- How about we put all the external jar files into a local bin. That way we can leave our build paths alone.
- The .gitignore file should resolve many merge conflicts arising from discrepancies in our local files.

