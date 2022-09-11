from statistics import mean
import matplotlib.pyplot as plt
from Lab3_4.Service.Service import Service
from Lab3_4.Service.ServicePSO import ServicePSO


class Test:
    def __init__(self, noOfCases=30):
        self.__noOfCases = noOfCases

    def run(self):
        self.__runEA()
        self.__runPSO()

    def __runEA(self):
        cases = list(range(1, self.__noOfCases + 1))
        fitnesses = []

        for case in cases:
            service = Service(1, 5, 10)
            fitness, population = service.run()
            fitnesses.append(fitness)

        plt.plot(cases, fitnesses)
        plt.scatter(cases, [mean(fitnesses)] * len(cases), label='mean')
        plt.xlabel("test case")
        plt.ylabel("fitness")
        plt.legend()
        plt.title("EA & Hill Climbing")
        plt.show()

    def __runPSO(self):
        cases = list(range(1, self.__noOfCases + 1))
        fitnesses = []

        for case in cases:
            service = ServicePSO(10, 3, 2, 6, 2)
            fitness, population = service.run()
            fitnesses.append(fitness)

        plt.plot(cases, fitnesses)
        plt.scatter(cases, [mean(fitnesses)] * len(cases), label='mean')
        plt.xlabel("test case")
        plt.ylabel("fitness")
        plt.legend()
        plt.title("PSO")
        plt.show()

