from random import shuffle


class Repository:
    def __init__(self):
        self.__data = None
        self.__trainData = None
        self.__testData = None

    @property
    def data(self):
        return self.__data

    @property
    def trainData(self):
        return self.__trainData

    @property
    def testData(self):
        return self.__testData

    def splitData(self, testSize, withShuffle=False):
        data = self.__data
        if withShuffle:
            data = self.__data[:]
            shuffle(data)

        firstTestIndex = int(len(data) * (1 - testSize))
        self.__trainData = data[: firstTestIndex]
        self.__testData = data[firstTestIndex:]

    def importData(self, databasePath):
        self.__data = []
        try:
            with open(databasePath) as file:
                try:
                    for count, line in enumerate(file):
                        self.__data.append(self.__tokenize(line))
                except ValueError as error:
                    print("ERROR when tokenizing line " + str(count) + " -> ", "\n\t", str(error))
        except FileNotFoundError as error:
            raise error



    def __tokenize(self, line):
        tokens = line.split(',')
        try:
            label = tokens[0]
            leftWeight = int(tokens[1])
            leftDistance = int(tokens[2])
            rightWeight = int(tokens[3])
            rightDistance = int(tokens[4])
            return [leftWeight, leftDistance, rightWeight, rightDistance, label]
        except IndexError:
            raise ValueError("Tokenize: insufficient no. of arguments")
        except ValueError:
            raise ValueError("Tokenize: invalid data")
