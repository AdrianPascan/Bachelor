from Lab3_4.Domain.Individual import Individual
from copy import deepcopy


class Particle:
    def __init__(self, sizeOfIndividual, noOfIndividuals):
        self.__position = [Individual(sizeOfIndividual) for current in range(noOfIndividuals)]
        self.__sizeOfIndividual = sizeOfIndividual
        self.__evaluate()
        self.__velocity = [0] * noOfIndividuals
        self.__bestPosition = deepcopy(self.__position)
        self.__bestFitness = 1000000

    def __fit(self, position):
        fitness = 0

        for individual in position:
            firstHorizontalPermutation = list(range(1, self.__sizeOfIndividual + 1))
            try:
                for current in individual.first:
                    firstHorizontalPermutation.remove(current)
            except ValueError:
                fitness += 1

        for individual in position:
            secondHorizontalPermutation = list(range(1, len(individual) + 1))
            try:
                for current in individual.second:
                    secondHorizontalPermutation.remove(current)
            except ValueError:
                fitness += 1

        return fitness

    def __evaluate(self):
        self.__fitness = self.__fit(self.__position)

    def globalFitness(self):
        fitness = 0
        for individual in self.position:
            fitness += individual.fitness()
        return fitness

    @property
    def velocity(self):
        return self.__velocity

    @property
    def position(self):
        return self.__position

    @property
    def fitness(self):
        return self.__fitness

    @property
    def bestPosition(self):
        return self.__bestPosition

    @property
    def bestFitness(self):
        return self.__bestFitness

    @position.setter
    def position(self, newPosition):#
        self.__position = newPosition
        self.__evaluate()
        if self.__fitness < self.__bestFitness:
            self.__bestPosition = deepcopy(self.__position)
            self.__bestFitness = self.__fitness


