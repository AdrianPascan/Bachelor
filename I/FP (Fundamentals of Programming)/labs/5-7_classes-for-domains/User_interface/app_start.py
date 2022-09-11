from User_interface.menu import runMenu
from Domain.activity import test_activity
from Domain.person import test_person
from Operations.tests import runTests

test_person()
test_activity()
runTests()

runMenu()