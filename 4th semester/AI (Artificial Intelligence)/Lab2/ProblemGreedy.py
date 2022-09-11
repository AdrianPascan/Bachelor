from Lab2.Problem import Problem
from random import choice
from texttable import Texttable
from Lab2.MyException import MyException
from Lab2.State import State


class ProblemGreedy(Problem):

    def expand(self):
        for self._currentRow in range(self._n):
            choiceColumns = self._availableColumns[:]
            self._currentColumn = choice(choiceColumns)
            choiceColumns.remove(self._currentColumn)
            self._finalState.table[self._currentRow][self._currentColumn] = 1
            while not self._heuristic():
                if len(choiceColumns) == 0:
                    raise MyException("Greedy method didn't find a solution. Last state was:\n" + str(self._finalState))
                self._finalState.table[self._currentRow][self._currentColumn] = 0
                self._currentColumn = choice(choiceColumns)
                choiceColumns.remove(self._currentColumn)
                self._finalState.table[self._currentRow][self._currentColumn] = 1
            self._availableColumns.remove(self._currentColumn)

        return [State(self._n, self._finalState)]