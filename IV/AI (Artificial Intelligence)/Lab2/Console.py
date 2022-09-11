from Lab2.MyException import MyException
from Lab2.ProblemDFS import ProblemDFS
from Lab2.ProblemGreedy import ProblemGreedy
import sys

class Console:

    def __init__(self, controller):
        self.__controller = controller

    def start(self):
        while True:
            try:
                n = self.__readN()
                method = self.__readMethod()

                solutions = ""
                if method == "DFS":
                    problemDFS = ProblemDFS(n)
                    solutions = self.__controller.getSolution(problemDFS)
                else:
                    problemGreedy = ProblemGreedy(n)
                    solutions = self.__controller.getSolution(problemGreedy)
                print("SOLUTIONS:")
                for solution in solutions:
                    print(str(solution))
            except ValueError:
                print("N must be an integer.")
            except MyException as exception:
                print(str(exception))

    def __exit(self):
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __readN(self):
        n = int(input("N (0 -> exit):\n\t >> "))
        if n == 0:
            self.__exit()
        if n <= 0 or n > 100:
            raise MyException("N must be in range (0, 100].")
        return n

    def __readMethod(self):
        method = input("Method (DFS / Greedy) (or exit):\n\t >> ").strip().upper()
        if method == "EXIT":
            self.__exit()
        if method != "DFS" and method != "GREEDY":
            raise MyException("Invalid method name: " + method)
        return method