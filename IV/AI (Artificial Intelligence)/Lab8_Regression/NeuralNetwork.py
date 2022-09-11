from random import random

class NeuralNetwork:
    def __init__(self, trainInput, trainOutput, hiddenCount):
        self.__trainSize = len(trainInput)
        self.__attributeCount = len(trainInput[0])

        self.__trainInput = trainInput
        self.__trainOutput = trainOutput

        self.__hiddenCount = hiddenCount
        self.__weightsInputHidden = [[random() for hiddenIndex in range(self.__hiddenCount)] for attributeIndex in range(self.__attributeCount)]
        self.__weightsHiddenOutput = [random() for hiddenIndex in range(self.__hiddenCount)]
        self.__losses = []

    @property
    def losses(self):
        return self.__losses

    @property
    def weightsInputHidden(self):
        return self.__weightsInputHidden

    @property
    def weightsHiddenOutput(self):
        return self.__weightsHiddenOutput

    def train(self, learningRate):
        outputErrorsSquared = []

        for index in range(self.__trainSize):
            currentInput = self.__trainInput[index]
            currentOutput = self.__trainOutput[index]

            # feed forward
            hiddenValues = [ sum([self.__weightsInputHidden[attributeIndex][hiddenIndex] * currentInput[attributeIndex] for attributeIndex in range(self.__attributeCount)])
                             for hiddenIndex in range(self.__hiddenCount)]
            outputValue = sum([self.__weightsHiddenOutput[hiddenIndex] * hiddenValues[hiddenIndex] for hiddenIndex in range(self.__hiddenCount)])

            # back propagation
            outputError = currentOutput - outputValue
            for hiddenIndex in range(self.__hiddenCount):
                self.__weightsHiddenOutput[hiddenIndex] += learningRate * outputError * hiddenValues[hiddenIndex]

            hiddenErrors = [self.__weightsHiddenOutput[hiddenIndex] * outputError for hiddenIndex in range(self.__hiddenCount)]
            for hiddenIndex in range(self.__hiddenCount):
                for attributeIndex in range(self.__attributeCount):
                    self.__weightsInputHidden[attributeIndex][hiddenIndex] += learningRate * hiddenErrors[hiddenIndex] * currentInput[attributeIndex]

            ###
            outputErrorsSquared.append(outputError ** 2)

        self.__losses.append( (1.0 / (2 * self.__trainSize)) * sum(outputErrorsSquared) )

    def test(self, testInput, testOutput):
        hiddenValues = [sum([self.__weightsInputHidden[attributeIndex][hiddenIndex] * testInput[attributeIndex] for attributeIndex in range(self.__attributeCount)])
                            for hiddenIndex in range(self.__hiddenCount)]
        outputValue = sum([self.__weightsHiddenOutput[hiddenIndex] * hiddenValues[hiddenIndex] for hiddenIndex in range(self.__hiddenCount)])

        return outputValue