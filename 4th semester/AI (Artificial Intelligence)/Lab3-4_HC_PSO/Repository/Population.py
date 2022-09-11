from Lab3_4.Domain.Individual import Individual
from texttable import Texttable


class Population:
    def __init__(self, n):
        self.__individuals = self.__initialisePopulation(n)

    @property
    def individuals(self):
        return self.__individuals

    @individuals.setter
    def individuals(self, newIndividuals):
        self.__individuals = newIndividuals

    def __initialisePopulation(self, n):
        return [Individual(n) for current in range(n)]

    def __len__(self):
        return len(self.__individuals)

    def __str__(self):
        table = Texttable()

        for individual in self.__individuals:
            row = []
            for column in range(len(self)):
                row.append(str(individual.first[column]) + ", " + str(individual.second[column]))
            table.add_row(row)

        return table.draw()
