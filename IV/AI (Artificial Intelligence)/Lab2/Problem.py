from texttable import Texttable
from Lab2.State import State


class Problem:

    def __init__(self, n):
        self._n = n
        self._availableColumns = list(range(n))
        self._currentColumn = -1
        self._currentRow = 0
        self._initialState = State(n)
        self._finalState = State(n)

    def expand(self):
        pass

    def _heuristic(self):
        row = self._currentRow - 1
        column = self._currentColumn - 1
        while row >= 0 and column >= 0:
            if self._finalState.table[row][column] == 1:
                return False
            row -= 1
            column -= 1

        row = self._currentRow - 1
        column = self._currentColumn + 1
        while row >= 0 and column < self._n:
            if self._finalState.table[row][column] == 1:
                return False
            row -= 1
            column += 1

        return True

    def _initialiseState(self, n):
        state = []
        for row in range(n):
            state.append([0] * n)
        return state
