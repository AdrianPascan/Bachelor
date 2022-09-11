import unittest
from Domain.Square import SquareValidator, SquareValidatorError
from Repositories.Repository import Repository
from Controller.Controller import Controller
from Controller.ControllerException import GameOver
from Repositories.RepositoryException import RepositoryError


class Test(unittest.TestCase):


    def setUp(self):
        
        self.validator = SquareValidator()
        self.repository = Repository()
        self.controller = Controller(self.validator, self.repository)


    def tearDown(self):
        
        del self


    def test(self):
        
        self.assertRaises(SquareValidatorError, self.controller.round, "abc", "yz")
        self.assertRaises(SquareValidatorError, self.controller.round, "-1", "15")
        
        self.controller.round("8", "8")
        self.assertEqual(self.repository.board.matrix[8][8], "B")
        self.assertEqual(self.repository.board.matrix[8][7], "W")
        
        self.controller.round("7", "8")
        self.assertEqual(self.repository.board.matrix[7][8], "B")
        self.assertEqual(self.repository.board.matrix[8][6], "W")
        
        self.controller.round("6", "8")
        self.assertEqual(self.repository.board.matrix[6][8], "B")
        self.assertEqual(self.repository.board.matrix[5][8], "W")
        
        self.controller.round("9", "9")
        self.assertEqual(self.repository.board.matrix[9][9], "B")
        self.assertEqual(self.repository.board.matrix[5][7], "W")
        
        self.controller.round("7", "7")
        self.assertEqual(self.repository.board.matrix[7][7], "B")
        self.assertEqual(self.repository.board.matrix[6][6], "W")
        
        self.controller.round("7", "9")
        self.assertEqual(self.repository.board.matrix[7][9], "B")
        self.assertEqual(self.repository.board.matrix[7][6], "W")
        
        self.controller.round("6", "10")
        self.assertEqual(self.repository.board.matrix[6][10], "B")
        self.assertEqual(self.repository.board.matrix[5][11], "W")
        
        self.controller.round("10", "6")
        self.assertEqual(self.repository.board.matrix[10][6], "B")
        self.assertEqual(self.repository.board.matrix[9][7], "W")
        
        self.controller.round("8", "12")
        self.assertEqual(self.repository.board.matrix[8][12], "B")
        self.assertEqual(self.repository.board.matrix[12][7], "W")
        
        self.controller.round("7", "11")
        self.assertEqual(self.repository.board.matrix[7][11], "B")
        self.assertEqual(self.repository.board.matrix[7][10], "W")
        
        self.controller.round("5", "9")
        self.assertEqual(self.repository.board.matrix[5][9], "B")
        self.assertEqual(self.repository.board.matrix[4][8], "W")
        
        self.assertRaises(RepositoryError, self.controller.round, "8", "8")
        
        self.assertRaises(GameOver, self.controller.round, "9", "13")
        self.assertEqual(self.repository.board.matrix[9][13], "B")
        

if __name__ == "__main__":
    unittest.main()