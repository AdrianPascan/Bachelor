'''
Created on May 7, 2019

@author: Adrian
'''

from DirectedGraph.DirectedGraph import DirectedGraph
import math
from texttable import Texttable
from copy import deepcopy


class DirectedGraphCosts(DirectedGraph):
    '''
    
    '''


    def __init__(self, n=1):
        self._graph = DirectedGraph()
        self._graph.__init__(n)
        self._costs = {}
        
        
    def getNumberOfVertices(self):
        
        return self._graph.getNumberOfVertices()
    
    
    def parseVertices(self):
        
        return self._graph.parseVertices()
    
    
    def isEdge(self, start, end, validate = True):
        
        return self._graph.isEdge(start, end, validate)
    
    
    def getInDegree(self, vertex, validate = True):
        
        return self._graph.getInDegree(vertex, validate)
    
    
    def getOutDegree(self, vertex, validate = True):
        
        return self._graph.getOutDegree(vertex, validate)
    
    
    def parseOutboundEdges(self, vertex, validate = True):
        
        return self._graph.parseOutboundEdges(vertex, validate)
    
    
    def parseInboundEdges(self, vertex, validate = True):
        
        return self._graph.parseInboundEdges(vertex, validate)
    
    
    def addEdge(self, start, end, cost, validate = True):
        
        self._graph.addEdge(start, end, validate)
        self._costs[(start, end)] = cost
    
        
    def removeEdge(self, start, end, validate = True):
        
        self._graph.removeEdge(start, end, validate)
        del self._costs[(start, end)]
    
    
    def addVertex(self, vertex, validate = True):
        
        self._graph.addVertex(vertex, validate)
    
    
    def removeVertex(self, vertex, validate = True):
        
        self._graph.removeVertex(vertex, validate)
            
            
    def readFromFile(self, fileName):
        
        try:
            
            with open(fileName, "r") as file:
                                
                data = file.read().split('\n')
                
                n = int(data[0].split(' ')[0])
                self.__init__(n)
                
                for edge in data[1:]:
                    numbers = edge.split(' ')

                    start = int(numbers[0])
                    end = int(numbers[1])      
                    cost = int(numbers[2])                  
                    
                    self.addEdge(start, end, cost, validate=False)
                    
        except IOError:
            
            raise IOError("Could not open file: " + str(fileName))

    
    def getCost(self, start, end, validate = True):
        
        if not self._graph.isEdge(start, end):
            raise ValueError("Non-existing edge: " + str(start) + "->" + str(end))
        
        return self._costs[(start, end)]

    
    def setCost(self, start, end, cost, validate = True):
        
        if not isinstance(cost, int):
            raise ValueError("Cost must be an integer.")
        
        if not self._graph.isEdge(start, end):
            raise ValueError("Non-existing edge: " + str(start) + "->" + str(end))
        
        self._costs[(start, end)] = cost
    
    
    '''
        PRACTICAL WORK NO. 3
    '''
    def lowestCostWalk(self, start, end, validate = True):
        
        self._graph._validateVertex(start)
        self._graph._validateVertex(start)
         
        verticesNumber = self.getNumberOfVertices()
        
        costMatrix = []
        for _row in range(verticesNumber):
            costMatrix.append([math.inf] * verticesNumber)
            
        for _row in range(verticesNumber):
            for _column in range(verticesNumber):
                try:
                    costMatrix[_row][_column] = self._costs[(_row, _column)]
                except KeyError:
                    if _row == _column:
                        costMatrix[_row][_column] = 0

        extendMatrix = deepcopy(costMatrix)
        
        '''
            Print costMatrix
        '''
        print("\nCOST MATRIX:\n")
        
        drawing = Texttable()
        
        header = [""]
        header.extend(range(verticesNumber))
        drawing.header(header)
        
        for _row in range(verticesNumber):
            row = [str(_row) + ")"]
            row.extend(costMatrix[_row])
            drawing.add_row(row)
            
        print(drawing.draw())
        '''
        '''
        
        print("\nEXTENDED MATRICES:\n")
        
        for iteration in range(1, verticesNumber-1):
            for _row in range(verticesNumber):
                for _column in range(verticesNumber):
                    auxiliaryMatrix = deepcopy(extendMatrix)
                    extendMatrix[_row][_column] = math.inf
                    for k in range(verticesNumber):
                        extendMatrix[_row][_column] = min(extendMatrix[_row][_column], auxiliaryMatrix[_row][k] + costMatrix[k][_column])
            
            '''
                Print extendMatrix
            '''
            print()
            
            drawing = Texttable()
            
            header = [""]
            header.extend(range(verticesNumber))
            drawing.header(header)
            
            for _row in range(verticesNumber):
                row = [str(_row) + ")"]
                row.extend(extendMatrix[_row])
                drawing.add_row(row)
                
            print(drawing.draw(), "\n")
            '''
            '''
         
        if (extendMatrix[start][end] == math.inf):
            raise ValueError("There is no path between " + str(start) + " and " + str(end) + " / there are negative cost cycles in the graph.")
            
        return extendMatrix[start][end]
    '''
        END - PRACTICAL WORK NO. 3
    '''
   
   
    '''
        PRACTICAL WORK NO. 4
    '''
    def function4(self):
        
        sortedList = self.topoSort()
        if sortedList == None:
            print("\tThe graph has at least one cycle.")
        else:
            print("\tThe graph is a DAG.");
            print("\tTopological sorting using DFS: ", sortedList)
            print("\tHighest cost path: ");
            start = int(input("\t\tStart vertex: "))
            end = int(input("\t\tStart vertex: "))
            print("\t\t", self.DAGHighestCostPath(start, end, sortedList))
    
    
    def topoSort(self):
        sortedList = []
        fullyProcessed = set()
        inProcess = set()
        for vertex in self._graph.parseVertices():
            if vertex not in fullyProcessed:
                if self._topoSortDFS(vertex, sortedList, fullyProcessed, inProcess) == False:
                    return None
        sortedList.reverse()
        return sortedList
    
    def _topoSortDFS(self, vertex, sortedList, fullyProcessed, inProcess):
        inProcess.add(vertex)
        for current in self._graph.parseOutboundEdges(vertex, validate=False):
            if current in inProcess:
                return False
            if current not in fullyProcessed:
                if self._topoSortDFS(current, sortedList, fullyProcessed, inProcess) == False:
                    return False
        inProcess.remove(vertex)
        sortedList.append(vertex)
        fullyProcessed.add(vertex)
        return True
    
    
    def DAGHighestCostPath(self, start, end, topoSorted):
         
        self._graph._validateVertex(start)
        self._graph._validateVertex(end)
                 
        dist = [-math.inf] * len(topoSorted)
        prev = [-1] * len(topoSorted)
         
        dist[start] = 0
         
        for vertex in topoSorted:
            if (vertex == end):
                break;
            for outboundVertex in self._graph.parseOutboundEdges(vertex, validate=False):
                if (dist[outboundVertex] < dist[vertex] + self.getCost(vertex, outboundVertex, validate=False)):
                    dist[outboundVertex] = dist[vertex] + self.getCost(vertex, outboundVertex, validate=False)
                    prev[outboundVertex] = vertex
                    
#         path = [end]
#         currentVertex = end
#         while(prev[currentVertex]!= start):
#             currentVertex = prev[currentVertex]
#             path.append(currentVertex)
#         path.append(start)
#         path.reverse()
                  
        print(prev)
        print(dist)          
        
        return dist[end]          
    '''
        END - PRACTICAL WORK NO. 4
    '''
        