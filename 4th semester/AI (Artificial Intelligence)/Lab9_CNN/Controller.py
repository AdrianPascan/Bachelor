from Lab9.Repository import Repository
from Lab9.MyException import MyException

import numpy as np
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Dense, Flatten
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.optimizers import SGD


class Controller:
    def __init__(self, repository: Repository):
        self.__repository = repository

    def runWithValidation(self, trainCountStr, testCountStr, noOfEpochsStr, learningRateStr):
        trainCount, testCount, noOfEpochs, learningRate = \
            self.__validate(trainCountStr, testCountStr, noOfEpochsStr, learningRateStr)
        self.run(trainCount, testCount, noOfEpochs, learningRate)

    def run(self, trainCount=60000, testCount=10000, noOfEpochs=3, learningRate=0.005):
        trainImages = self.__repository.trainImages[:trainCount]
        trainLabels = self.__repository.trainLabels[:trainCount]
        testImages = self.__repository.testImages[:testCount]
        testLabels = self.__repository.testLabels[:testCount]

        trainImages = (trainImages / 255) - 0.5
        testImages = (testImages / 255) - 0.5

        trainImages = np.expand_dims(trainImages, axis=3)
        testImages = np.expand_dims(testImages, axis=3)

        model = Sequential([
            Conv2D(8, 3, input_shape=(28, 28, 1), use_bias=False),
            MaxPooling2D(pool_size=2),
            Flatten(),
            Dense(10, activation='softmax'),
        ])

        model.compile(SGD(lr=learningRate), loss='categorical_crossentropy', metrics=['accuracy'])

        model.fit(
            trainImages,
            to_categorical(trainLabels),
            batch_size=1,
            epochs=noOfEpochs,
            validation_data=(testImages, to_categorical(testLabels)),
        )

    @classmethod
    def __validate(cls, trainCountStr, testCountStr, noOfEpochsStr, learningRateStr):
        try:
            trainCount = int(trainCountStr)
            if not (0 < trainCount <= 60000):
                raise ValueError()
        except ValueError:
            raise MyException("Train examples count must be an integer in range (0, 60000].")

        try:
            testCount = int(testCountStr)
            if not (0 < testCount <= 10000):
                raise ValueError()
        except ValueError:
            raise MyException("Test examples count must be an integer in range (0, 10000].")

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

        return trainCount, testCount, noOfEpochs, learningRate
