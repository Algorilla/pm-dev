"""USAGE: """

# This program monitors that function signatures are not changed.

import os

SRC   = (True, 'src')  # indicates whether to test the src folder
TEST  = (False, 'test') # indicates whether to test the test folder

# The program generates and stores reports in snitch directory.
snitch_dir = os.getcwd(); os.chdir('..')
root_dir   = os.getcwd()

def getFuncSignatures(packages_to_test):
    """
    Parses each source file in a list of packages.
    Returns a dictionary mapping a package name to a dictionary of its methods.
    The method names in turn map to the method signatures they are found in.
    """    
    packages = {package.split('\\')[-1] : {} for package in packages_to_test}
    print(packages)
    signatures = {} # dictionary of funcation names to their prototype
    return 

def generateReports():
    """ """
    pass

for test in [SRC, TEST]:
    if test[0]:
        os.chdir(root_dir)
        assert test[1] in os.listdir(), '%s directory not found' % test[1]
        os.chdir(test[1])
        contents = os.listdir()
        getFuncSignatures(contents)
        generateReports()

#print('all kosher')
