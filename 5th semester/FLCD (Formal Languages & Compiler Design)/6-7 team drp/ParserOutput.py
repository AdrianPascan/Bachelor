from pptree import Node
from print_tree import print_tree
import sys
import io


class print_custom_tree(print_tree):
    def get_children(self, node):
        return node.children

    def get_node_str(self, node):
        return str(node.name)


class ParserOutput:

    def __init__(self, grammar, workingStack):
        self.grammar = grammar
        self.workingStack = workingStack
        self.root = None

    def parsing_production_string(self):
        rules = []
        for production in self.workingStack:
            if len(production) > 1:
                if production[0] in self.grammar.getProductions().keys():
                    if production[1] in self.grammar.getProductions()[production[0]]:
                        rules.append(production)
        return rules

    def parsing_table(self):
        rules = self.parsing_production_string()

        if len(rules) == 0:
            self.root = Node("empty")
        else:
            rule_index = 0
            self.root = Node(rules[0][0])
            self.parsing_table_rec(self.root, rules, rule_index)

    def parsing_table_rec(self, father, rules, rule_index):
        if rule_index == len(rules):
            return len(rules)

        prod = rules[rule_index][1]
        for term in prod:
            sibling = Node(name=term, parent=father)
            if term in self.grammar.nonterminals:
                rule_index = self.parsing_table_rec(sibling, rules, rule_index + 1)
        return rule_index

    def print_tree(self):
        print_custom_tree(self.root)

    def print_tree_to_file(self, file_path):
        original_stdout = sys.stdout
        with io.open(file_path, "w", encoding="utf-8") as f:
            sys.stdout = f
            self.print_tree()
            sys.stdout = original_stdout
