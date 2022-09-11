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

    @staticmethod
    def __tokenize(line):
        tokens = line.split(' ')
        try:
            attribute = float(tokens[0])
            attribute2 = float(tokens[1])
            attribute3 = float(tokens[2])
            attribute4 = float(tokens[3])
            attribute5 = float(tokens[4])
            value = float(tokens[5])
            return [1, attribute, attribute2, attribute3, attribute4, attribute5, value]
        except IndexError:
            raise ValueError("Tokenize: insufficient no. of arguments")
        except ValueError:
            raise ValueError("Tokenize: invalid data")
