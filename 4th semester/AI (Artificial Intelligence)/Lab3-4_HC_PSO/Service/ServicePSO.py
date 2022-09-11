from copy import deepcopy
from Lab3_4.Repository.PopulationPSO import PopulationPSO
from Lab3_4.Exception.MyException import MyException
from PyQt5.QtCore import QThread
from random import random, randint


class ServicePSO(QThread):
    def __init__(self, noOfIterations, noOfParticles, sizeOfParticle, sizeOfIndividual, noOfNeighbours):
        QThread.__init__(self)

        self.__validate(noOfIterations, noOfParticles, sizeOfParticle, sizeOfIndividual, noOfNeighbours)
        self.__population = PopulationPSO(self.__noOfParticles, self.__sizeOfParticle, self.__sizeOfIndividual)
        self.__neighbours = self.__selectNeighbours()

        self.__w = 1.0
        self.__c1 = 1.0
        self.__c2 = 2.5

    def run(self):
        generations = []
        for iteration in range(self.__noOfIterations):
            generation = self.__iteration()
            fitness = self.__globalFitness(generation)
            print("FITNESS:", fitness)
            print(str(generation))
            generations.append(generation)

        graded = [(self.__globalFitness(generation), generation) for generation in generations]
        graded = sorted(graded, key=lambda generation: generation[0])

        result = graded[0]
        optimumFitness = result[0]
        optimumPopulation = result[1]

        return optimumFitness, optimumPopulation

    def __iteration(self):
        particles = self.__population.particles

        # best neighbours
        bestNeighbours = []
        for index in range(self.__noOfParticles):
            bestNeighbours.append(self.__neighbours[index][0])
            for currentIndex in range(1, len(self.__neighbours[index])):
                if particles[bestNeighbours[index]].fitness > particles[self.__neighbours[index][currentIndex]].fitness:
                    bestNeighbours[index] = self.__neighbours[index][currentIndex]

        # velocity
        for index in range(self.__noOfParticles):
            for currentIndex in range(len(particles[0].velocity)):
                newVelocity = self.__w * particles[index].velocity[currentIndex]
                newVelocity = newVelocity + self.__c1 * random() * (
                            particles[bestNeighbours[index]].position[currentIndex].fitness() - particles[index].position[currentIndex].fitness())
                newVelocity = newVelocity + self.__c2 * random() * (
                            particles[index].bestPosition[currentIndex].fitness() - particles[index].position[currentIndex].fitness())
                particles[index].velocity[currentIndex] = newVelocity

        # position
        for index in range(self.__noOfParticles):
            newPosition = []
            for currentIndex in range(len(particles[0].velocity)):
                newPosition.append(particles[index].position[currentIndex])
            particles[index].position = newPosition

        return deepcopy(self.__population)

    def __selectNeighbours(self):
        neighbours = []
        for index in range(self.__noOfParticles):
            localNeighbours = []
            for currentIndex in range(self.__noOfNeighbours):
                candidate = randint(0, self.__noOfParticles - 1)
                while candidate in localNeighbours:
                    candidate = randint(0, self.__noOfParticles - 1)
                localNeighbours.append(candidate)
            neighbours.append(localNeighbours)
        return neighbours

    def __globalFitness(self, population):
        return population.globalFitness()

    def __validate(self, noOfIterations, noOfParticles, sizeOfParticle, sizeOfIndividual, noOfNeighbours):
        try:
            self.__noOfIterations = int(noOfIterations)
            self.__noOfParticles = int(noOfParticles)
            self.__sizeOfParticle = int(sizeOfParticle)
            self.__sizeOfIndividual = int(sizeOfIndividual)
            self.__noOfNeighbours = int(noOfNeighbours)
        except ValueError:
            raise MyException("Invalid input data: data must be numerical.")

        if self.__noOfIterations <= 0 or self.__noOfParticles <= 0 or self.__sizeOfParticle <= 0 or self.__sizeOfIndividual <= 0:
            raise MyException("Invalid input data: data must be greater than 0.")

        if self.__sizeOfIndividual / self.__noOfParticles != int(self.__sizeOfIndividual / self.__noOfParticles):
            raise MyException("Invalid input data: the individual size must be a multiple of the particle size.")

        if self.__noOfNeighbours >= self.__noOfParticles:
            raise MyException("Invalid input data: the no. of neighbours must be smaller than the no. of particles.")
