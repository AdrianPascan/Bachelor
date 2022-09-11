from Lab1.C.MyException import MyException
import random

class CryptarithmeticGame222:

    def __init__(self, noOfAttempts):
        self.__noOfAttempts = noOfAttempts
        (self.__cipher, self.__decipher) = ({}, {})
        self.__hexa = self.__initialiseHexa()
        self.__firstWord = "SEND"
        self.__secondWord = "MORE"
        self.__resultWord = "MONEY"
        self.__currentWord = ""
        self.__currentResult = []

    def play(self):
        try:
            for attempt in range(self.__noOfAttempts):
                print("Attempt no. " + str(attempt) + ":\n")
                self.__round()
                input("Press any key to continue..")
        except MyException as exception:
            print(str(self) + "\n" +
                  str(exception) + "(" + str(attempt) + " st/nd/th attempt)")

    def __round(self):
        (self.__cipher, self.__decipher) = self.__initialiseCipher()
        self.__addWords()
        if self.__currentWord == self.__resultWord:
            raise MyException("Solution found!")
        print(str(self) + "\n It is not a solution.")

    def __str__(self):
        return self.__firstWord + self.__hexaRepresentation(self.__encipherWord(self.__firstWord)).center(20) + "\n" + \
                self.__secondWord + self.__hexaRepresentation(self.__encipherWord(self.__secondWord)).center(20) + "\n" + \
                self.__currentWord + self.__hexaRepresentation(self.__currentResult).center(20)


    def __addWords(self):
        firstWord = self.__encipherWord(self.__firstWord)
        firstWord.reverse()
        secondWord = self.__encipherWord(self.__secondWord)
        secondWord.reverse()

        currentList = []
        length = len(firstWord)
        carry = 0
        for index in range(length):
            result = firstWord[index] + secondWord[index] + carry
            if result >= 16:
                result -= 16
                carry = 1
            else:
                carry = 0
            currentList.append(result)
        if carry > 0:
            currentList.append(carry)
        currentList.reverse()

        self.__currentWord = self.__decipherWord(currentList)
        self.__currentResult = currentList

    def __hexaRepresentation(self, encipheredList):
        result = ""
        for figure in encipheredList:
            result += self.__hexa[figure]
        return result

    def __initialiseHexa(self):
        hexa = {0: "0",
                1: "1",
                2: "2",
                3: "3",
                4: "4",
                5: "5",
                6: "6",
                7: "7",
                8: "8",
                9: "9",
                10: "A",
                11: "B",
                12: "C",
                13: "D",
                14: "E",
                15: "F"}
        return hexa

    def __encipherWord(self, word):
        encipheredList = []
        for letter in  word:
            encipheredList.append(self.__cipher[letter])
        return encipheredList

    def __decipherWord(self, encipheredList):
        word = ""
        for figure in encipheredList:
            word += self.__decipher[figure]
        return word

    def __initialiseCipher(self):
        cipher = {"S": None,
                  "E": None,
                  "N": None,
                  "D": None,
                  "M": None,
                  "O": None,
                  "R": None,
                  "Y": None}
        decipher = {0: "_",
                    1: "_",
                    2: "_",
                    3: "_",
                    4: "_",
                    5: "_",
                    6: "_",
                    7: "_",
                    8: "_",
                    9: "_",
                    10: "_",
                    11: "_",
                    12: "_",
                    13: "_",
                    14: "_",
                    15: "_"}

        options = []
        options.extend(range(16))

        for letter in cipher.keys():
            value = random.choice(options)
            if letter == "S" or letter == "M":
                while value == 0:
                    value = random.choice(options)
            cipher[letter] = value
            decipher[value] = letter
            options.remove(value)

        return cipher, decipher