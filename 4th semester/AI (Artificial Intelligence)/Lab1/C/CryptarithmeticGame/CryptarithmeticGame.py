from Lab1.C.MyException import MyException
import random

class CryptarithmeticGame:

    def __init__(self, noOfAttempts, operand, firstWord, secondWord, resultWord):
        self.__noOfAttempts = noOfAttempts
        (self.__cipher, self.__decipher) = ({}, {})
        self.__hexa = self.__initialiseHexa()
        self.__operand = operand
        self.__firstWord = firstWord
        self.__secondWord = secondWord
        self.__resultWord = resultWord
        self.__currentWord = ""
        self.__currentResult = []

    def play(self):
        try:
            for attempt in range(self.__noOfAttempts):
                print("Attempt no. " + str(attempt + 1) + ":\n")
                self.__round()
                input("Press any key to continue..")
        except MyException as exception:
            print(str(self) + "\n" +
                  str(exception) + "(" + str(attempt + 1) + " st/nd/th attempt)")

    def __round(self):
        (self.__cipher, self.__decipher) = self.__initialiseCipher()
        if self.__operand == "+":
            self.__addWords()
        else:
            self.__subtractWords()
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
        length = max(len(firstWord), len(secondWord))
        carry = 0
        for index in range(length):
            if index >= len(firstWord):
                result = secondWord[index] + carry
            elif index >= len(secondWord):
                result = firstWord[index] + carry
            else:
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

    def __subtractWords(self):
        firstWord = self.__encipherWord(self.__firstWord)
        firstWord.reverse()
        secondWord = self.__encipherWord(self.__secondWord)
        secondWord.reverse()

        # bigger number becomes the minuend
        negative = False
        if len(firstWord) < len(secondWord):
            (secondWord, firstWord) = (firstWord, secondWord)
            negative = True
        if len(firstWord) == len(secondWord):
            index = len(firstWord) - 1
            while index >= 0 and firstWord[index] == secondWord[index]:
                index -= 1
            if index >= 0 and firstWord[index] < secondWord[index]:
                (secondWord, firstWord) = (firstWord, secondWord)
                negative = True

        currentList = []
        borrow = 0
        for index in range(len(firstWord)):
            if index >= len(firstWord):
                result = firstWord[index] + borrow
            else:
                result = firstWord[index] - secondWord[index] + borrow
            if result < 0:
                result = 16 + result
                borrow = -1
            else:
                borrow = 0
            currentList.append(result)
        # if borrow > 0:
        #     currentList.append(borrow)
        if negative:
            currentList.append(-1)
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

    def __deconstructWord(self, word):
        letterList = []
        for letter in word:
            letterList.append(letter)
        return letterList

    def __initialiseCipher(self):
        cipher = {}
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
                    15: "_",
                    -1: "-"}

        options = []
        options.extend(range(16))

        lettersList = []
        lettersList.extend(self.__deconstructWord(self.__firstWord))
        lettersList.extend(self.__deconstructWord(self.__secondWord))
        lettersList.extend(self.__deconstructWord(self.__currentWord))

        firstLetterList = [self.__firstWord[0], self.__secondWord[0], self.__resultWord[0]]

        for letter in lettersList:
            if letter not in cipher.keys():
                if len(options) == 0:
                    raise MyException("There are more than 16 unique letters in the equation.")
                value = random.choice(options)
                if letter in firstLetterList:
                    if len(options) == 1 and options[0] == 0:
                        return self.__initialiseCipher()
                    while value == 0:
                        value = random.choice(options)
                cipher[letter] = value
                decipher[value] = letter
                options.remove(value)

        return cipher, decipher