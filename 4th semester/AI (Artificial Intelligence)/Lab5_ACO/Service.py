from Lab5.Colony import Colony
from Lab5.MyException import MyException
from math import inf as infinity
from random import random, randint


class Service:
    def __init__(self, squareMatrixOrder, noOfColonies, noOfAnts, alpha, beta, evaporationCoefficient):
        self.__validate(squareMatrixOrder, noOfColonies, noOfAnts, alpha, beta, evaporationCoefficient)
        # self.__alpha = 1.9
        # self.__beta = 0.9
        # self.__evaporation = 0.05

    def run(self):
        bestColony = None
        bestColonyFitness = infinity
        for colonyNo in range(self.__noOfColonies):
            colony = self.__iteration()
            colonyFitness = self.__fitness(colony)
            if colonyFitness < bestColonyFitness:
                bestColony = colony
                bestColonyFitness = colonyFitness
            # printing in service for testing purposes
            print("COLONY", colonyNo)
            print("fitness:", colonyFitness)
            print("colony:")
            print(str(colony))
            print("\n\n")
        return bestColonyFitness, bestColony

    def __iteration(self):
        colony = Colony(self.__n, self.__noOfAnts)

        for iteration in range(self.__n ** 2):
            # since the distance d between two adjacent cells i and j is 1, 1/d = 1
            # thus, probability p = trace(i,j) ** alpha / sum(traces)
            for ant in colony.ants:
                positionDict = colony.traceMatrix[ant.lastPosition.row][ant.lastPosition.column]
                positionList = [position for position in positionDict.keys() if not(position) in ant.path]
                if len(positionList) > 0:
                    denominators = []
                    for position in positionList:
                        denominators.append( positionDict[position] ** self.__alpha * (1 / ant.pathLength) ** self.__beta )
                    nominator = sum(denominators)

                    probabilities = []
                    for denominator in denominators:
                        probabilities.append( denominator / nominator )

                    # roulette wheel
                    cummulativeProbabilities = []
                    for index in range(len(positionList)):
                        cummulativeProbabilities.append(sum(probabilities[index:]))

                    randomNo = random()
                    index = len(positionList) - 1
                    while index >= 0 and randomNo > cummulativeProbabilities[index]:
                        index -= 1

                    nextPosition = positionList[index]
                    ant.path.append(nextPosition)
                    positionDict[nextPosition] += 1 / ant.pathLength
                    colony.valueMatrix[nextPosition.row][nextPosition.column] = [randint(1, self.__n),
                                                                                 randint(1, self.__n)]
            # update trace: evaporation
            for row in range(self.__n):
                for column in range(self.__n):
                    positionDict = colony.traceMatrix[row][column]
                    for position in positionDict:
                        positionDict[position] *= (1 - self.__evaporation)
        return colony

    def __fitness(self, colony):
        fitness = 0

        for row in range(self.__n):
            firstHorizontalPermutation = list(range(1, self.__n + 1))
            secondHorizontalPermutation = list(range(1, self.__n + 1))
            for column in range(self.__n):
                try:
                    firstHorizontalPermutation.remove(colony.valueMatrix[row][column][0])
                except ValueError:
                    pass
            for column in range(self.__n):
                try:
                    secondHorizontalPermutation.remove(colony.valueMatrix[row][column][1])
                except ValueError:
                    pass
            fitness += len(firstHorizontalPermutation) + len(secondHorizontalPermutation)

        for column in range(self.__n):
            firstVerticalPermutation = list(range(1, self.__n + 1))
            secondVerticalPermutation = list(range(1, self.__n + 1))
            for row in range(self.__n):
                try:
                    firstVerticalPermutation.remove(colony.valueMatrix[row][column][0])
                except ValueError:
                    pass
            for row in range(self.__n):
                try:
                    secondVerticalPermutation.remove(colony.valueMatrix[row][column][0])
                except ValueError:
                    pass
            fitness += len(firstVerticalPermutation) + len(secondVerticalPermutation)

        return fitness

    def __validate(self, squareMatrixOrder, noOfColonies, noOfAnts, alpha, beta, evaporationCoefficient):
        try:
            self.__n = int(squareMatrixOrder)
            self.__noOfColonies = int(noOfColonies)
            self.__noOfAnts = int(noOfAnts)
            if alpha == '':
                self.__alpha = 1.9
            else:
                self.__alpha = float(alpha)
            if beta == '':
                self.__beta = 0.9
            else:
                self.__beta = float(beta)
            if evaporationCoefficient == '':
                self.__evaporation = 0.05
            else:
                self.__evaporation = float(evaporationCoefficient)

            if self.__n <= 0 or self.__noOfColonies <= 0 or self.__noOfAnts <= 0 or self.__alpha <= 0 or self.__beta <= 0 or self.__evaporation <= 0:
                raise ValueError
        except ValueError:
            raise MyException("Invalid data: data must be numerical and greater than 0")

        if self.__n ** 2 < self.__noOfAnts:
            raise MyException("Invalid data: no. of ants must be at most equal with the number of elements of the matrix")

        if self.__evaporation > 1:
            raise MyException("Invalid data: evaporation coeff. must be between in range [0, 1]")
