from Lab6.MyException import MyException
from Lab6.Repository import Repository
from Lab6.DecisionTree import DecisionTree, Leaf


class Controller:
    def __init__(self, repository: Repository):
        self.__repository = repository
        self.__repository.importData('balance-scale.data')
        self.__header = ['leftWeight', 'leftDistance', 'rightWeight', 'rightDistance', 'class']

    def run(self, testSizeStr, withShuffleStr):
        testSize, withShuffle = self.__validate(testSizeStr, withShuffleStr)
        self.start(testSize, withShuffle)

    def start(self, testSize, withShuffle):
        self.__repository.splitData(testSize, withShuffle)
        decisionTree = DecisionTree(self.__header, self.__repository.trainData)

        decisionTree.printTree()
        print('\n\n\n')

        testData = self.__repository.testData
        correct = 0
        for row in testData:
            leafPredictions = decisionTree.classify(row)
            try:
                leafPredictions[row[-1]]
                correct += 1
            except KeyError:
                pass
            print("Actual: %s. Predicted: %s" %
                  (row[-1], Leaf(leafPredictions).printLeaf()))
        print('\n\n')

        accuracy = correct / float(len(testData))
        print("Accuracy: {}%".format(round(accuracy * 100, 2)))
        print('\n\n')

    @classmethod
    def __validate(cls, testSizeStr, withShuffleStr):
        try:
            testSize = float(testSizeStr)
            if not (0 <= testSize < 1):
                raise ValueError()
        except ValueError:
            raise MyException("Test size must be a float between 0 and 1.")

        withShuffle = False
        if withShuffleStr.lower() == 'false':
            pass
        elif withShuffleStr.lower() == 'true':
            withShuffle = True
        else:
            raise MyException("Shuffle option must be boolean.")

        return testSize, withShuffle
