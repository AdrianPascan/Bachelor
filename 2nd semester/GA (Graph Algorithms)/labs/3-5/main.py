'''
Created on Apr 16, 2019

@author: Adrian
'''
from DirectedGraph.DirectedGraphConsole import DirectedGraphConsole
from UndirectedGraph.UndirectedGraphConsole import UndirectedGraphConsole
from DirectedGraphCosts.DirectedGraphCostsConsole import DirectedGraphCostsConsole


def directedGraph():
    console = DirectedGraphConsole()
    console.start()
    
def undirectedGraph():
    console = UndirectedGraphConsole()
    console.start()

def directedGraphCosts():
    console = DirectedGraphCostsConsole()
    console.start()

if __name__ == "__main__":
    
    undirectedGraph()