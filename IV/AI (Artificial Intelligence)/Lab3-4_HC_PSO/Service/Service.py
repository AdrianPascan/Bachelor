from random import randint
from Lab3_4.Repository.Population import Population
from Lab3_4.Domain.Individual import Individual
from Lab3_4.Exception.MyException import MyException
from copy import deepcopy
from PyQt5.QtCore import QThread


class Service(QThread):
    def __init__(self, probabilityOfMutation, populationSize, noOfIterations):
        QThread.__init__(self)

        self.__validate(probabilityOfMutation, populationSize, noOfIterations)
        self.__population = Population(self.__populationSize)

    def run(self):
        generations = []
        for iteration in range(self.__noOfIterations):
            generation = self.__iteration()
            fitness = self.__globalFitness(generation)
            print("FITNESS:", fitness)
            print(str(generation))
            generations.append(generation)

        graded = [(self.__globalFitness(generation), generation) for generation in generations]
        graded = sorted(graded, key=lambda generation: self.__globalFitness(generation))

        result = graded[0]
        optimumFitness = result[0]
        optimumPopulation = result[1]

        return optimumFitness, optimumPopulation

    def __iteration(self):
        index = randint(0, self.__populationSize - 1)
        individual = self.__population.individuals[index]

        index2 = randint(0, self.__populationSize - 1)
        individual2 = self.__population.individuals[index2]

        if individual != individual2:
            child = self.__crossover(individual, individual2)
            child = self.__mutate(child)

            fitness = self.__fitness(individual)
            self.__population.individuals[index] = child
            fitnessChild = self.__fitness(child)
            self.__population.individuals[index] = individual

            fitness2 = self.__fitness(individual2)
            self.__population.individuals[index2] = child
            fitnessChild2 = self.__fitness(child)
            self.__population.individuals[index] = individual2

            if fitness > fitness2 and fitness > fitnessChild:
                self.__population[index] = child
            if fitness2 > fitness and fitness2 > fitnessChild2:
                self.__population[index2] = child

        return deepcopy(self.__population)

    def __mutate(self, individual):
        return individual.mutate(self.__probabilityOfMutation)

    def __crossover(self, parent, parent2):
        # parent, parent2 - Individual obj.'s
        child = Individual(self.__populationSize)
        child.first = parent.first[:]
        child.second = parent2.second[:]
        return child

    def __fitness(self, individual):
        fitness = 0
        firstHorizontalPermutation = list(range(1, len(individual) + 1))
        secondHorizontalPermutation = list(range(1, len(individual) + 1))

        for column in range(len(individual)):
            try:
                firstHorizontalPermutation.remove(individual.first[column])
            except ValueError:
                pass

            try:
                secondHorizontalPermutation.remove(individual.second[column])
            except ValueError:
                pass

            firstVerticalPermutation = list(range(1, len(individual) + 1))
            try:
                for row in range(len(individual)):
                    firstVerticalPermutation.remove(self.__population.individuals[row].first[column])
            except ValueError:
                fitness += 1

            secondVerticalPermutation = list(range(1, len(individual) + 1))
            try:
                for row in range(len(individual)):
                    secondVerticalPermutation.remove(self.__population.individuals[row].second[column])
            except ValueError:
                fitness += 1

        if len(firstHorizontalPermutation) > 0:
            fitness += 1
        if len(secondHorizontalPermutation) > 0:
            fitness += 1

        return fitness

    def __globalFitness(self, population):
        globalFitness = 0

        # horizontally
        for individual in self.__population.individuals:
            firstPermutation = list(range(1, self.__populationSize+1))
            try:
                for first in individual.first:
                    firstPermutation.remove(first)
            except ValueError:
                globalFitness += 1

            secondPermutation = list(range(1, self.__populationSize + 1))
            try:
                for second in individual.second:
                    secondPermutation.remove(second)
            except ValueError:
                globalFitness += 1

        # vertically
        for column in range(self.__populationSize):
            firstPermutation = list(range(1, self.__populationSize + 1))
            try:
                for individual in self.__population.individuals:
                    firstPermutation.remove(individual.first[column])
            except ValueError:
                globalFitness += 1

            secondPermutation = list(range(1, self.__populationSize + 1))
            try:
                for individual in self.__population.individuals:
                    secondPermutation.remove(individual.second[column])
            except ValueError:
                globalFitness += 1

        return globalFitness

    def __validate(self, probabilityOfMutation, populationSize, noOfIterations):
        try:
            self.__probabilityOfMutation = float(probabilityOfMutation)
            self.__populationSize = int(populationSize)
            self.__noOfIterations = int(noOfIterations)
        except ValueError:
            raise MyException("Invalid input data: data must be numerical.")

        if not (0 <= self.__probabilityOfMutation <= 1):
            raise MyException("Invalid input data: probability of mutation must be a decimal between 0 and 1.")

        if self.__populationSize <= 0:
            raise MyException("Invalid input data: population size must be greater than 0.")

        if self.__noOfIterations <= 0:
            raise MyException("Invalid input data: no. of iterations must be greater than 0.")