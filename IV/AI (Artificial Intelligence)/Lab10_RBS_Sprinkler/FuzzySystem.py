from Lab10_RBS_Sprinkler.FuzzyRule import FuzzyRule


class FuzzySystem:
    """
        Fuzzy system object
        Receives variable descriptions and rules and outputs the defuzzified
        result of the system
    """

    def __init__(self, rules):
        self.inputDescriptions = {}
        self.outputDescription = None
        self.rules = rules

    def addDescription(self, name, description, output=False):
        """
        Receives a description
        """
        if output:
            if self.outputDescription is None:
                self.outputDescription = description
            else:
                raise ValueError('System already has an output')
        else:
            self.inputDescriptions[name] = description

    def compute(self, inputs):
        fuzzyValues = self._computeDescriptions(inputs)
        ruleValues = self._computeRulesFuzzy(fuzzyValues)

        fuzzyOutputVariables = [(list(description[0].values())[0], description[1])
                                for description in ruleValues]
        weightedTotal = 0
        weightSum = 0
        for variable in fuzzyOutputVariables:
            weightSum += variable[1]
            weightedTotal += self.outputDescription.defuzzify(*variable) * variable[1]

        return weightedTotal / weightSum

    def _computeDescriptions(self, inputs):
        return {
            variableName: self.inputDescriptions[variableName].fuzzify(inputs[variableName])
            for variableName, value in inputs.items()
        }

    def _computeRulesFuzzy(self, fuzzyValues):
        """
            Returns the fuzzy output of all rules
        """
        return [rule.evaluate(fuzzyValues) for rule in self.rules if rule.evaluate(fuzzyValues)[1] != 0]