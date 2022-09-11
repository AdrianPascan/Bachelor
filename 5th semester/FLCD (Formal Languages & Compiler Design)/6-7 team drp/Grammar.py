
class Grammar:
    @staticmethod
    def parseLine(line):
        return [value.strip() for value in line.strip().split(',')]

    '''
    Reads line by line from the file:
    first line should contain terminals separated by ,
    second line should contain the nonterminals(separated by , )
    third line should contain the start symbol
    from the forth line til the end of the file should be the productions under the form : 
        the nonterminal + ':' right side + "|"  + right side or just right side
    '''
    def fromFile(self,fileName):
        with open(fileName) as file:
            self.nonterminals = Grammar.parseLine(file.readline())
            self.terminals = Grammar.parseLine(file.readline())
            self.startSymbol = file.readline()
            #self.F = Parser.parseLine(file.readline())
            self.productions = []

            i=-1
            with open(fileName) as openfileobject:
                for line in openfileobject:
                    i = i + 1
                    if i > 3:
                        self.productions.append(line.strip())
            self.productions = Grammar.parseProductions(self.productions)


    """
    Here we create a dictionary that has as keys the states, and as value, a list with dictionaries
    having as keys values from the alphabet, and as values the states in which we will arrive
    """
    @staticmethod
    def parseProductions(parts):
        result = {}
        for line in parts:

            key = line.split(":")
            value = key[1].split("|")
            key = key[0]
            for i in range(len(value)):
                value[i] = value[i].split()
            if key in result.keys():
                result[key].append(value)
            else:
                result[key]=value

        # print(result)
        return result

    def __init__(self, file):
        self.nonterminals = None
        self.terminals = None
        self.productions = None
        self.startSymbol = None
        self.fromFile(file)
        #while True:
         #   self.menu()

    def getStartSymbol(self):
        return self.startSymbol.strip()

    def getNonTerminals(self):
        return self.nonterminals

    def getTerminals(self):
        return self.terminals

    def getProductions(self):
        return self.productions

    def getProductionsN(self, non):
        for key, val in self.productions.items():
            if key == str(non):
                return val

    def getFirstProduction(self, non):
        for key, val in self.productions.items():
            if key == str(non):
                return val[0]

    def getNextProduction(self, production, productions):
        for element in range(len(productions)):
            if element < len(productions) - 1:
                if production == productions[element]:
                    element += 1
                    return productions[element]
        return None

    """
    Prints the set of nonterminals as a list
    """
    def printNonterminals(self):
        print(self.nonterminals)

    """
    Prints the set of terminals as a list
    """
    def printTerminals(self):
        print(self.terminals)

    """
    Prints the productions as a dictionary
    """
    def printProductions(self):
        print(self.productions)

    """
    Prints the final states as a list
    """
    def printProductionsN(self, non):
        for key, value in self.productions.items():
            if key == str(non):
                print(value)


    def menu(self):
        print("Select your choice\n")
        print("Press 1 to see the set of nonterminals\n")
        print("Press 2 to see the set of terminals\n")
        print("Press 3 to see the set of productions\n")
        print("Press 4 to see the productions for a given nonterminal\n")

        choice = input()
        if int(choice) == 1:
            self.printNonterminals()
        elif int(choice) == 2:
            self.printTerminals()
        elif int(choice) == 3:
            self.printProductions()
        elif int(choice) == 4:
            non = input("Nonterminal >> ")
            self.printProductionsN(non)
        else:
            print("Wrong input!")
