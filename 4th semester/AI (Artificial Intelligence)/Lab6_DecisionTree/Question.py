class Question:
    """A Question is used to partition a dataset.
    This class just records a 'column number' (e.g., 1 for leftWeight) and a
    'column value' (e.g., 1). The 'match' method is used to compare
    the feature value in an example to the feature value stored in the
    question.
    """

    def __init__(self, column, value, columnLabel=""):
        self.column = column
        self.value = value
        self.columnLabel = columnLabel

    def match(self, example):
        # Compare the feature value in an example to the
        # feature value in this question.
        val = example[self.column]
        return val >= self.value

    def __repr__(self):
        # This is just a helper method to print
        # the question in a readable format.
        return "Is %s >= %s?" % (
            self.columnLabel, str(self.value))