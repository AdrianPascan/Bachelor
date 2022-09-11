from Lab3_4.Domain.Particle import Particle
from texttable import Texttable


class PopulationPSO:
    def __init__(self, noOfParticles, sizeOfParticle, sizeOfIndividual):
        self.__particles = [Particle(sizeOfIndividual, sizeOfParticle) for _ in range(noOfParticles)]

    @property
    def particles(self):
        return self.__particles

    @particles.setter
    def particles(self, newParticles):
        self.__particles = newParticles

    def globalFitness(self):
        fitness = 0
        for particle in self.particles:
            fitness += particle.globalFitness()
        return fitness

    def __len__(self):
        return len(self.__particles)

    def __str__(self):
        table = Texttable()

        for particle in self.__particles:
            for individual in particle.position:
                row = []
                for column in range(len(individual)):
                    row.append(str(individual.first[column]) + ", " + str(individual.second[column]))
                table.add_row(row)

        return table.draw()


