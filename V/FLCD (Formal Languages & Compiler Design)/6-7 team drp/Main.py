from Grammar import Grammar
from Parser import Parser
from ParserOutput import ParserOutput

if __name__ == '__main__':
    # # G1
    # g = Grammar("input/g1.txt")
    # p = Parser(g)
    # print(g.printProductions())
    # # good, prods = p.checkSequence("aaaabbbbb")
    # good, prods = p.checkSequence(["a", "a", "a", "a", "b", "b", "b", "b", "b"])
    # print(g)
    # print(good, prods)
    # for index in range(len(prods)):
    #     print(index, " : ", prods[index])
    #
    # po = ParserOutput(g, p.workingStack)
    # po.parsing_table()
    # po.print_tree()
    # po.print_tree_to_file("output/g1.out")



    # # G2_ALINA
    # g = Grammar("input/g2_alina.txt")
    # p = Parser(g)
    # print(g.printProductions())
    # good, prods = p.checkSequence(["a", "a", "a", "a", "b", "b", "b", "b", "b"])
    # print(g)
    # print(good, prods)
    # for index in range(len(prods)):
    #     print(index, " : ", prods[index])
    #
    # po = ParserOutput(g, p.workingStack)
    # po.parsing_table()
    # po.print_tree()
    # po.print_tree_to_file("output/g1.out")



    # G2_ADRIAN
    g = Grammar("input/g2_adrian.txt")
    p = Parser(g)
    po = ParserOutput(g, None)

    #p1
    sequence = [
        "30", "2", "43", "44",
        "41",
        "30", "-1", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "4", "-1", "40",
        "4", "-1", "40",
        "4", "-1", "40",
        "-1", "55", "-1", "40",
        "20", "43", "-1", "12", "-1", "44", "41",
        "-1", "55", "-1", "40",
        "42",
        "20", "43", "-1", "12", "-1", "44", "41",
        "-1", "55", "-1", "40",
        "42",
        "5", "-1", "40",
        "3", "0", "40",
        "42"
    ]
    good, prods = p.checkSequence(sequence)
    po.workingStack = p.workingStack
    po.parsing_table()
    print("P1:\n")
    po.print_tree()
    print("\n\n\n")
    po.print_tree_to_file("output/g2_adrian_p1.out")

    # p2
    sequence = [
        "30", "2", "43", "44",
        "41",
        "30", "-1", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "4", "-1", "40",
        "20", "43", "-1", "12", "-1", "44", "41",
        "5", "0", "40",
        "3", "0", "40",
        "42",
        "20", "43", "-1", "12", "-1", "44", "41",
        "5", "0", "40",
        "3", "0", "40",
        "42",
        "22", "43", "-1", "55", "0", "40", "-1", "52", "-1", "11", "-1", "40", "-1", "55", "-1", "50", "0", "44", "41",
        "20", "43", "-1", "12", "-1", "44", "41",
        "5", "0", "40",
        "3", "0", "40",
        "42",
        "42",
        "5", "0", "40",
        "3", "0", "40",
        "42"
    ]
    good, prods = p.checkSequence(sequence)
    po.workingStack = p.workingStack
    po.parsing_table()
    print("P2:\n")
    po.print_tree()
    print("\n\n\n")
    po.print_tree_to_file("output/g2_adrian_p2.out")

    # p3
    sequence = [
        "30", "2", "43", "44",
        "41",
        "30", "-1", "55", "0", "40",
        "30", "-1", "45", "0", "46", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "30", "-1", "55", "0", "40",
        "4", "-1", "40",
        "22", "43", "-1", "55", "0", "40", "-1", "11", "-1", "40", "-1", "55", "-1", "50", "0", "44", "41",
        "4", "-1", "45", "-1", "46", "40",
        "-1", "55", "-1", "50", "-1", "45", "-1", "46", "40",
        "42",
        "5", "-1", "40",
        "3", "0", "40",
        "42"
    ]
    good, prods = p.checkSequence(sequence)
    po.workingStack = p.workingStack
    po.parsing_table()
    print("P3:\n")
    po.print_tree()
    print("\n\n\n")
    po.print_tree_to_file("output/g2_adrian_p3.out")
