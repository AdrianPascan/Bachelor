class FuzzyDescriptions:
    """
        Encapsulate a description of a fuzzy variable
        It contains a set of functions for each fuzzy region
    """
    def __init__(self):
        self.regions = {}
        self.inverse = {}

    def addRegion(self, variableName, membershipFunction, inverse=None):
        """
            Adds a region with a given membership function, optionally
            an inverse function for the Sugeno or Tsukamoto models
        """
        self.regions[variableName] = membershipFunction
        self.inverse[variableName] = inverse

    def fuzzify(self, value):
        """
            Return the fuzzified values for each region
        """
        return {name: membershipFunction(value)
                for name, membershipFunction in self.regions.items()
                }

    def defuzzify(self, variableName, value):
        return self.inverse[variableName](value)