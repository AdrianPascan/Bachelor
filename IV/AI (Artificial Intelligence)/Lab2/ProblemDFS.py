from Lab2.Problem import Problem
from Lab2.State import State
from copy import deepcopy
from texttable import Texttable


class ProblemDFS(Problem):

    def expand(self):
        solutions = []
        self.__expandDFS(solutions)
        return solutions

    def __expandDFS(self, solutions):
        if self._currentRow == self._n:
            # print(str(self._finalState))
            solutions.append(State(self._n, self._finalState))
        for index in range(len(self._availableColumns)):
            column = self._availableColumns[index]
            self._currentColumn = column
            self._finalState.table[self._currentRow][self._currentColumn] = 1
            if self._heuristic():
                self._currentRow += 1
                self._availableColumns.remove(self._currentColumn)
                self.__expandDFS(solutions)
                self._currentRow -= 1
                self._availableColumns.insert(index, column)
            self._finalState.table[self._currentRow][column] = 0
