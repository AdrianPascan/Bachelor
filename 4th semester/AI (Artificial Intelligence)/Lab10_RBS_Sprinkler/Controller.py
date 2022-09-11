from Lab10_RBS_Sprinkler.FuzzySystem import FuzzySystem
from Lab10_RBS_Sprinkler.MyException import MyException


class Controller:
    def __init__(self, temperature, humidity, time, rules):
        self.system = FuzzySystem(rules)
        self.system.addDescription('temperature', temperature)
        self.system.addDescription('humidity', humidity)
        self.system.addDescription('time', time, output=True)

    def computeWithValidation(self, stringInputs):
        inputs = self._validate(stringInputs)
        return self.system.compute(inputs)

    def compute(self, inputs):
        return self.system.compute(inputs)

    @staticmethod
    def _validate(stringInputs):
        try:
            temperature = int(stringInputs['temperature'])
            if not (-30 <= temperature <= 35):
                raise ValueError()
        except ValueError:
            raise MyException("Temperature must be an integer in range [-30, 35].")

        try:
            humidity = int(stringInputs['humidity'])
            if not (0 <= humidity <= 100):
                raise ValueError()
        except ValueError:
            raise MyException("Humidity must be an integer in range [0, 100].")

        return {'temperature': temperature,
                'humidity': humidity}