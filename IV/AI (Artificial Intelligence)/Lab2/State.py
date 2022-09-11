from copy import deepcopy
from texttable import Texttable


class State:

    def __init__(self, n, state=None):
        if state is None:
            self.__table = self.__initialiseState(n)
        else:
            self.__table = deepcopy(state.table)

    @property
    def table(self):
        return self.__table

    def __initialiseState(self, n):
        state = []
        for row in range(n):
            state.append([0] * n)
        return state

    def __str__(self):
        table = Texttable()
        for row in self.__table:
            table.add_row(row)
        return table.draw()