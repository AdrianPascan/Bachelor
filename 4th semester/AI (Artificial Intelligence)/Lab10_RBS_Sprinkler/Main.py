from Lab10_RBS_Sprinkler.Controller import Controller
from Lab10_RBS_Sprinkler.FuzzyDescriptions import FuzzyDescriptions
from Lab10_RBS_Sprinkler.FuzzyRule import FuzzyRule
from Lab10_RBS_Sprinkler.Console import Console


def trapezoidalRegion(a, b, c, d):
    """
        Returns a higher order function for a trapezoidal fuzzy region
    """
    return lambda x: max(0, min((x - a) / (b - a), 1, (d - x) / (d - c)))


def triangularRegion(a, b, c):
    """
        Returns a higher order function for a triangular fuzzy region
    """
    return trapezoidalRegion(a, b, b, c)


def inverseLine(a, b):
    return lambda val: val * (b - a) + a


def inverseTriangular(a, b, c):
    return lambda val: (inverseLine(a, b)(val) + inverseLine(c, b)(val)) / 2


if __name__ == '__main__':
    temperature = FuzzyDescriptions()
    humidity = FuzzyDescriptions()
    time = FuzzyDescriptions()
    rules = []

    temperature.addRegion('very cold', trapezoidalRegion(-1000, -30, -20, 5))
    temperature.addRegion('cold', triangularRegion(-5, 0, 10))
    temperature.addRegion('normal', trapezoidalRegion(5, 10, 15, 20))
    temperature.addRegion('warm', triangularRegion(15, 20, 25))
    temperature.addRegion('hot', trapezoidalRegion(25, 30, 35, 1000))

    humidity.addRegion('dry', triangularRegion(-1000, 0, 50))
    humidity.addRegion('normal', triangularRegion(0, 50, 100))
    humidity.addRegion('wet', triangularRegion(50, 100, 1000))

    time.addRegion('short', triangularRegion(-1000, 0, 50), inverseLine(50, 0))
    time.addRegion('medium', triangularRegion(0, 50, 100), inverseTriangular(0, 50, 100))
    time.addRegion('long', triangularRegion(50, 100, 1000), inverseLine(50, 100))

    rules.append(FuzzyRule({'temperature': 'very cold', 'humidity': 'wet'},
                           {'time': 'short'}))
    rules.append(FuzzyRule({'temperature': 'cold', 'humidity': 'wet'},
                           {'time': 'short'}))
    rules.append(FuzzyRule({'temperature': 'normal', 'humidity': 'wet'},
                           {'time': 'short'}))
    rules.append(FuzzyRule({'temperature': 'warm', 'humidity': 'wet'},
                           {'time': 'short'}))
    rules.append(FuzzyRule({'temperature': 'hot', 'humidity': 'wet'},
                           {'time': 'medium'}))

    rules.append(FuzzyRule({'temperature': 'very cold', 'humidity': 'normal'},
                           {'time': 'short'}))
    rules.append(FuzzyRule({'temperature': 'cold', 'humidity': 'normal'},
                           {'time': 'medium'}))
    rules.append(FuzzyRule({'temperature': 'normal', 'humidity': 'normal'},
                           {'time': 'medium'}))
    rules.append(FuzzyRule({'temperature': 'warm', 'humidity': 'normal'},
                           {'time': 'medium'}))
    rules.append(FuzzyRule({'temperature': 'hot', 'humidity': 'normal'},
                           {'time': 'long'}))

    rules.append(FuzzyRule({'temperature': 'very cold', 'humidity': 'dry'},
                           {'time': 'medium'}))
    rules.append(FuzzyRule({'temperature': 'cold', 'humidity': 'dry'},
                           {'time': 'long'}))
    rules.append(FuzzyRule({'temperature': 'normal', 'humidity': 'dry'},
                           {'time': 'long'}))
    rules.append(FuzzyRule({'temperature': 'warm', 'humidity': 'dry'},
                           {'time': 'long'}))
    rules.append(FuzzyRule({'temperature': 'hot', 'humidity': 'dry'},
                           {'time': 'long'}))

    controller = Controller(temperature, humidity, time, rules)

    console = Console(controller)
    console.start()
