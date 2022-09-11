from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import QThread
from Lab3_4.UI.GUIQtDesigner import Ui_MainWindow
from Lab3_4.Service.Service import Service
from Lab3_4.Service.ServicePSO import ServicePSO
from Lab3_4.Exception.MyException import MyException
import sys


class GUI(QThread):
    def __int__(self):
        QThread.__init__(self)

    def start(self):
        app = QtWidgets.QApplication(sys.argv)
        MainWindow = QtWidgets.QMainWindow()
        self.__ui = Ui_MainWindow()
        self.__ui.setupUi(MainWindow)

        # ADDITIONAL SETUP
        self.__ui.runPushButton.clicked.connect(self.__runButton)
        self.__ui.runPSOPushButton.clicked.connect(self.__runPSOButton)
        # END OF ADDITIONAL SETUP

        MainWindow.show()
        sys.exit(app.exec_())

    def __runButton(self):
        probabilityOfMutation = self.__ui.probabilityOfMutationAndCrossoverLineEdit.text()
        populationSize = self.__ui.populationSizeLineEdit.text()
        noOfIterations = self.__ui.noOfIterationsLineEdit.text()
        self.__ui.statusbar.showMessage("Run EA+HC button pressed!")

        try:
            service = Service(probabilityOfMutation, populationSize, noOfIterations)
            fitness, population = service.run()

            self.__ui.resultTextEdit.setText("FITNESS: " + str(fitness) + "\n"
                                             + "POPULATION: " + "\n"
                                             + str(population))
        except MyException as error:
            self.__ui.statusbar.showMessage(str(error))


    def __runPSOButton(self):
        noOfParticles = self.__ui.noOfParticlesLineEdit.text()
        sizeOfParticle = self.__ui.sizeOfParticleLineEdit.text()
        sizeOfIndividual = self.__ui.sizeOfIndividualLineEdit.text()
        noOfNeighbours = self.__ui.noOfNeighboursLineEdit.text()
        noOfIterations = self.__ui.noOfIterationsPSOLineEdit.text()
        self.__ui.statusbar.showMessage("Run PSO button pressed!")

        try:
            service = ServicePSO(noOfIterations, noOfParticles, sizeOfParticle, sizeOfIndividual, noOfNeighbours)
            fitness, population = service.run()

            self.__ui.resultTextEdit.setText("FITNESS: " + str(fitness) + "\n"
                                             + "POPULATION: " + "\n"
                                             + str(population))
        except MyException as error:
            self.__ui.statusbar.showMessage(str(error))

