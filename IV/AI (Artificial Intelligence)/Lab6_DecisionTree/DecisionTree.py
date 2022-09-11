from Lab6.Question import Question


class DecisionTree:
    def __init__(self, header, rows):
        self.header = header
        self.rows = rows
        self.rootNode = self.buildTree(rows)

    @classmethod
    def uniqueValues(cls, rows, column):
        return set([row[column] for row in rows])

    @classmethod
    def classCounts(cls, rows):
        counts = {}
        for row in rows:
            label = row[-1]
            if label not in counts:
                counts[label] = 0
            counts[label] += 1
        return counts

    @classmethod
    def partition(cls, rows, question):
        trueRows, falseRows = [], []
        for row in rows:
            if question.match(row):
                trueRows.append(row)
            else:
                falseRows.append(row)
        return trueRows, falseRows

    @classmethod
    def gini(cls, rows):
        counts = cls.classCounts(rows)
        impurity = 1
        for label in counts:
            probabilityOfLabel = counts[label] / float(len(rows))
            impurity -= probabilityOfLabel ** 2
        return impurity

    @classmethod
    def informationGain(cls, left, right, currentUncertainty):
        probability = float(len(left)) / (len(left) + len(right))
        return currentUncertainty - probability * cls.gini(left) - (1 - probability) * cls.gini(right)

    def findBestSplit(self, rows):
        bestGain = 0
        bestQuestion = None
        currentUncertainty = self.gini(rows)
        noOfFeatures = len(rows[0]) - 1

        for column in range(noOfFeatures):

            values = set([row[column] for row in rows])

            for value in values:
                columnLabel = self.header[column]
                question = Question(column, value, )

                trueRows, falseRows = self.partition(rows, question)

                if len(trueRows) == 0 or len(falseRows) == 0:
                    continue

                gain = self.informationGain(trueRows, falseRows, currentUncertainty)

                if gain > bestGain:
                    bestGain, bestQuestion = gain, question

        return bestGain, bestQuestion

    def buildTree(self, rows):
        gain, question = self.findBestSplit(rows)

        if gain == 0:
            return Leaf(rows)

        trueRows, falseRows = self.partition(rows, question)
        trueBranch = self.buildTree(trueRows)
        falseBranch = self.buildTree(falseRows)

        return DecisionNode(question, trueBranch, falseBranch)

    def printTree(self):
        self.printTreeRec(self.rootNode)

    @classmethod
    def printTreeRec(cls, node, spacing=""):
        if isinstance(node, Leaf):
            print(spacing + "Predict", node.predictions)
            return

        print(spacing + str(node.question))

        print(spacing + '--> True:')
        cls.printTreeRec(node.trueBranch, spacing + "  ")

        print(spacing + '--> False:')
        cls.printTreeRec(node.falseBranch, spacing + "  ")

    def classify(self, row):
        return self.classifyRec(row, self.rootNode)

    @classmethod
    def classifyRec(cls, row, node):
        if isinstance(node, Leaf):
            return node.predictions

        if node.question.match(row):
            return cls.classifyRec(row, node.trueBranch)
        else:
            return cls.classifyRec(row, node.falseBranch)


class DecisionNode:
    def __init__(self,
                 question,
                 trueBranch,
                 falseBranch):
        self.question = question
        self.trueBranch = trueBranch
        self.falseBranch = falseBranch



class Leaf:
    def __init__(self, rows):
        self.predictions = DecisionTree.classCounts(rows)

    def printLeaf(self):
        # dictionary for printing purposes
        total = sum(self.predictions.values()) * 1.0
        probabilities = {}
        for label in self.predictions.keys():
            probabilities[label] = str(int(self.predictions[label] / total * 100)) + "%"
        return probabilities
