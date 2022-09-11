from Lab8.MyException import MyException
from Lab8.Repository import Repository
import matplotlib.pyplot as plt
from Lab8.NeuralNetwork import NeuralNetwork


class Controller:
    def __init__(self, repository: Repository):
        self.__repository = repository
        self.__repository.importData('database.data')

    def run(self, testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr):
        testSize, withShuffle, noOfEpochs, learningRate = self.__validate(testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr)
        self.start(testSize, withShuffle, noOfEpochs, learningRate)

    def start(self, testSize, withShuffle, noOfEpochs, learningRate):
        self.__repository.splitData(testSize, withShuffle)

        network = self.__train(noOfEpochs, learningRate)
        self.__test(network)

    def __train(self, noOfEpochs, learningRate):
        trainData = self.__repository.trainData
        trainInput = [row[:-1] for row in trainData]
        trainOutput = [row[-1] for row in trainData]
        network = NeuralNetwork(trainInput, trainOutput, 3)

        for epoch in range(noOfEpochs):
            network.train(learningRate)

        print("WEIGHTS:")
        print("\t Input-> hidden:")
        print("\t\t", network.weightsInputHidden)
        print("\t Hidden-> output:")
        print("\t\t", network.weightsHiddenOutput)
        print("\n")

        plt.plot(range(noOfEpochs), network.losses)
        plt.xlabel("iteration")
        plt.ylabel("loss function")
        plt.show()

        return network

    def __test(self, network: NeuralNetwork):
        testData = self.__repository.testData
        testSize = len(testData)
        testInput = [row[:-1] for row in testData]
        testOutput = [row[-1] for row in testData]

        testPredicted = [network.test(currentInput, currentOutput) for currentInput, currentOutput in zip(testInput, testOutput)]
        testErrors = [output - predicted for output, predicted in zip(testOutput, testPredicted)]

        for index, output, predicted, error in zip(range(testSize), testOutput, testPredicted, testErrors):
            print("{} -> Actual: {:.2f} . Predicted: {:.2f} . Error: {:.2f} .".format(index + 1, output, predicted, error))

        print("\n")

        average = sum(testErrors) / testSize
        print("Average Error: {:+.2f}".format(average))

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
