from Lab7.MyException import MyException
from Lab7.Repository import Repository
from random import random
import matplotlib.pyplot as plt


class Controller:
    def __init__(self, repository: Repository):
        self.__repository = repository
        self.__repository.importData('database.data')

    def run(self, testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr):
        testSize, withShuffle, noOfEpochs, learningRate = self.__validate(testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr)
        self.start(testSize, withShuffle, noOfEpochs, learningRate)

    def start(self, testSize, withShuffle, noOfEpochs, learningRate):
        self.__repository.splitData(testSize, withShuffle)

        weights = self.__train(noOfEpochs, learningRate)
        self.__test(weights)

    def __train(self, noOfEpochs, learningRate):
        rows = self.__repository.trainData
        noOfRows = len(rows)
        noOfAttributes = len(rows[0]) - 1

        losses = []

        # initialise weights
        weights = [random() for noOfWeight in range(noOfAttributes)]

        for epoch in range(noOfEpochs):

            predictedValues = [self.__compute(weights, row) for row in rows]
            errors = [predictedValues[rowIndex] - rows[rowIndex][-1] for rowIndex in range(noOfRows)]
            errorsSquared = [error ** 2 for error in errors]

            loss = (1.0 / (2 * noOfRows)) * sum(errorsSquared)
            losses.append(loss)

            derivatives = [0] * noOfAttributes
            for derivativeIndex in range(noOfAttributes):
                for rowIndex in range(noOfRows):
                    derivatives[derivativeIndex] = derivatives[derivativeIndex] + errors[rowIndex] * rows[rowIndex][derivativeIndex]
                derivatives[derivativeIndex] /= noOfRows

            # update weights
            for weightIndex in range(noOfAttributes):
                weights[weightIndex] = weights[weightIndex] - learningRate * derivatives[weightIndex]

        plt.plot(range(noOfEpochs), losses)
        plt.xlabel("iteration")
        plt.ylabel("loss function")
        plt.show()

        return weights

    def __test(self, weights):
        rows = self.__repository.testData
        noOfRows = len(rows)

        predicted = [self.__compute(weights, row) for row in rows]
        errors = [rows[index][-1] - predicted[index] for index in range(noOfRows)]

        for index in range(noOfRows):
            print("{} -> Actual: {:.2f} . Predicted: {:.2f} . Error: {:.2f} .".format(index + 1, rows[index][-1],
                                                                                    predicted[index], errors[index]))

        print("\n")

        average = sum(errors) / noOfRows
        print("Average Error: {:+.2f}".format(average))

    @staticmethod
    def __compute(weights, row):
        return sum([weight * attribute for weight, attribute in zip(weights, row)])

    @classmethod
    def __validate(cls, testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr):
        try:
            testSize = float(testSizeStr)
            if not (0 < testSize < 1):
                raise ValueError()
        except ValueError:
            raise MyException("Test size must be a float in range (0, 1).")

        withShuffle = False
        if withShuffleStr.lower() == 'false':
            pass
        elif withShuffleStr.lower() == 'true':
            withShuffle = True
        else:
            raise MyException("Shuffle option must be boolean.")

        try:
            noOfEpochs = int(noOfEpochsStr)
            if not (0 < noOfEpochs):
                raise ValueError()
        except ValueError:
            raise MyException("No. of epochs must be an integer greater than 0.")

        try:
            learningRate = float(learningRateStr)
            if not (0 < learningRate <= 1):
                raise ValueError()
        except ValueError:
            raise MyException("Learning rate must be a float in range (0, 1].")

        return testSize, withShuffle, noOfEpochs, learningRate
