import tensorflow.keras.datasets.mnist as mnist


class Repository:
    def __init__(self):
        (self.__trainImages, self.__trainLabels), (self.__testImages, self.__testLabels) = mnist.load_data()

    @property
    def trainImages(self):
        return self.__trainImages

    @property
    def trainLabels(self):
        return self.__trainLabels

    @property
    def testImages(self):
        return self.__testImages

    @property
    def testLabels(self):
        return self.__testLabels
