from ParserOutput import ParserOutput


class State:
    NORMAL = 'Q'
    ERROR = 'E'
    BACK = 'B'
    FINAL = 'F'


class Parser:
    def __init__(self, grammar):
        self.state = State.NORMAL
        self.index = 0
        self.maxIndex = 0
        self.workingStack = []
        self.inputStack = [grammar.getStartSymbol()]
        self.g = grammar
        self.sequence = None
        self.left_recursive = self.check_grammar_left_recursive()

    def check_grammar_left_recursive(self):
        for nonterminal, prod_list in self.g.getProductions().items():
            for prod in prod_list:
                for symbol in prod[:-1]:
                    if symbol == nonterminal:
                        return True

        return False

    def momentary_insucces(self):
        # WE HAVE TO CHANGE THE STATE TO 'BACK'
        print("Momentary insucces\n")
        self.state = State.BACK
        print("Changing state to Back...\n")

    def succes(self):
        # OUR STATE WILL BECOME FINAL
        print("Success!\n")
        self.state = State.FINAL
        print("Changing state to Final...\n")

    def expand(self):
        print("Expand\n")
        # WE TAKE THE NONTERMINAL FROM THE INPUT STACK
        nonTerminal = self.inputStack[0]

        # WE SEARCH FOR THE FIRST PRODUCTION OF THIS NONTERMINAL
        firstProduction = self.g.getFirstProduction(nonTerminal)

        # WE PUSH ON THE WORKING STACK THE FIRST PRODUCTION OF OUR NONTERMINAL AS A LIST CONTAINING
        # THE NONTERMINAL AND THE F. PROD
        self.workingStack.append([nonTerminal, firstProduction])

        # WE POP THE NONTERMINAL FROM THE INPUT STACK
        self.inputStack = self.inputStack[1:]

        # WE PUSH THE FIRST PROFUCTION ON THE INPUT STACK
        self.inputStack = firstProduction + self.inputStack

    def advance(self):
        print("Advance\n")
        # WE INCREASE THE INDEX WITH THE VALUE 1
        self.index += 1
        if (self.index > self.maxIndex):
            self.maxIndex = self.index

        # WE PUSH THE TOP ELEMENT FROM THE INPUT STACK TO THE WORKING STACK
        self.workingStack.append(self.inputStack[0])

        # WE MAKE A POP IN THE INPUT STACK
        self.inputStack = self.inputStack[1:]

    def back(self):
        print("Back\n")
        # WE WILL DO THE OPPOSITE OF THE ADVANCE

        # SINCE WE ARE GOING BACK, WE DECRESE THE INDEX WITH THE VALUE 1
        self.index = self.index - 1

        # WE POP THE TOP ELEMENT FROM THE WORKING STACK
        terminal = self.workingStack.pop(-1)

        # WE PUSH ON THE TOP OF THE INPUT STACK THE ELEMENT THAT WE POP FROM THE WORKING STACK
        self.inputStack = [terminal] + self.inputStack

    def another_try(self):
        print("Another try\n")
        # WE GET THE TOP ELEMENT FROM THE WORKING STACK (A PRODUCTION), AND WE TAKE ITS NONTERMINAL
        last = self.workingStack[-1]
        nonTerminal = last[0]

        # WE CHECK FOR ALL ITS PRODUCTION AND WE SELECT THE NEXT ONE THAT WE NEED, IF IT EXISTS
        productions = self.g.getProductionsN(nonTerminal)
        productions = [[nonTerminal, production] for production in productions]
        next = self.g.getNextProduction(last, productions)

        # IF WE FIND IT
        if next is not None:
            print("Changing state to Normal...\n")
            self.state = State.NORMAL
            self.workingStack.pop(-1)
            self.workingStack.append([next[0], next[1]])
            self.inputStack = self.inputStack[len(last[1]):]
            self.inputStack = next[1] + self.inputStack
        elif self.index == 0 and last[0] == self.g.getStartSymbol():
            print("Changing state to Error...\n")
            print("Error around term " + self.sequence[self.maxIndex] + " (index = " + str(self.maxIndex + 1) + ")")
            self.state = State.ERROR
        else:
            self.workingStack.pop(-1)
            self.inputStack = [last[0]] + self.inputStack[len(last[1]):]

    def checkSequence(self, sequence):
        if (self.left_recursive):
            print("Grammar is LEFT-RECURSIVE")
            return False, []

        for element in sequence:
            if element not in self.g.getTerminals():
                print("Changing state to Error...\n")
                print("Error around term " + element + " as it is not a terminal")
                self.state = State.ERROR

        self.sequence = sequence

        # while (s != t) and (s != e) do
        while self.state != State.FINAL and self.state != State.ERROR:
            # if s = q then
            if self.state == State.NORMAL:
                # if (B=e) and (i = n + 1) then
                if len(self.inputStack) == 0 and self.index == len(sequence):
                    self.succes()
                elif len(self.inputStack) == 0:
                    self.momentary_insucces()
                else:
                    # if tip(B) = A then
                    if self.inputStack[0] in self.g.getNonTerminals():
                        self.expand()
                    else:
                        # backtrack if index is max
                        if self.index == len(sequence):
                            self.momentary_insucces()
                            # if tip(B = xi) then
                        elif self.inputStack[0] == sequence[self.index]:
                            self.advance()
                        else:
                            self.momentary_insucces()
            else:
                if self.state == State.BACK:
                    if self.workingStack[-1] in self.g.getTerminals():
                        self.back()
                    else:
                        self.another_try()

        Rules = []
        if self.state == State.ERROR:
            print("Final state Error...\n")
            return False, []
        else:
            print("Final state Final...\n")
            parserOutput = ParserOutput(self.g, self.workingStack)
            Rules = parserOutput.parsing_production_string()

        return True, Rules

