'''
Created on Mar 18, 2019

@author: Adrian
'''
from copy import deepcopy
from random import randint


class Edge():
    '''
    
    '''
    
    def __init__(self, start, end, cost = 0):
        
        self.__start = start
        self.__end = end
        self.__cost = cost
        
    
    @property
    def start(self):
        return self.__start
    
    @start.setter
    def start(self, value):
        self.__start = value
        
        
    @property
    def end(self):
        return self.__end
    
    @end.setter
    def end(self, value):
        self.__end = value
        
        
    @property
    def cost(self):
        return self.__cost
    
    @cost.setter
    def cost(self, value):
        self.__cost = value


class DirectedGraph():
    '''    
    
    '''


    def __init__(self, n=1):
        
        self.__numberOfVertices = n;
        self.__vertices = []        
        self.__edges = {}
    
    
    def readFromFile(self, fileName):
        
        try:
            
            with open(fileName, "r") as file:
                
                vertices = set()
                
                data = file.read().split('\n')
                
                self.__numberOfVertices = int(data[0].split(' ')[0])
                                
                for edge in data[1:]:
                    
                    numbers = edge.split(' ')
                    
                    edgeID = numbers[0] + "to" + numbers[1]
                    start = int(numbers[0])
                    end = int(numbers[1])
                    cost = int(numbers[2])
                                        
                    self.__edges[edgeID] = Edge(start, end, cost)
                    
                    vertices.add(start)
                    vertices.add(end)
                    
                self.__vertices = list(vertices)
                    
        except IOError:
            
            raise IOError("Could not open file: " + str(fileName))
    
    
    def createRandom(self, numberOfVertices, numberOfEdges):
        
        if numberOfVertices < 1:
            raise ValueError("Number of vertices must be greater than 0.")
        
        if numberOfEdges < 0 or numberOfEdges > numberOfVertices*(numberOfVertices - 1):
            raise ValueError("Number of edges is invalid.")
        
        self.__numberOfVertices = numberOfVertices
        self.__vertices = list(range(numberOfVertices))
        self.__edges = {}
        
        for edgeNumber in range(numberOfEdges):
            
            edge = Edge(randint(0, numberOfVertices-1), randint(0, numberOfVertices-1), randint(0, 100))
            edgeID = str(edge.start) + "to" + str(edge.end)
            
            while(edgeID in self.__edges.keys()):
                
                edge = Edge(randint(0, numberOfVertices-1), randint(0, numberOfVertices-1), randint(0, 100))
                edgeID = str(edge.start) + "to" + str(edge.end)
                
            self.__edges[edgeID] = edge
        
        
    def getNumberOfVertices(self):
        
        return len(self.__vertices)
    
    
    def parseVertices(self):
        
        return self.__vertices[:]
    
    
    def isEdge(self, start, end):
        
        for edge in self.__edges.values():
            if edge.start == start and edge.end == end:
                return True
            
        return False
    
    
    def getInDegree(self, vertex):
        
        if vertex not in self.__vertices:
            raise ValueError("There is no vertex with specifier: " + str(vertex))
        
        inDegree = 0
        
        for edge in self.__edges.values():
            if edge.end == vertex:
                inDegree += 1
                
        return inDegree
    
    
    def getOutDegree(self, vertex):
        
        if vertex not in self.__vertices:
            raise ValueError("There is no vertex with specifier: " + str(vertex))
        
        outDegree = 0
        
        for edge in self.__edges.values():
            if edge.start == vertex:
                outDegree += 1
                
        return outDegree
    
    
    def parseOutboundEdges(self, vertex):
        
        if vertex not in self.__vertices:
            raise ValueError("There is no vertex with specifier: " + str(vertex))
        
        endVertices = []
        
        for edge in self.__edges.values():
            if edge.start == vertex:
                endVertices.append(edge.end)
        
        return endVertices
    
    
    def parseInboundEdges(self, vertex):
        
        if vertex not in self.__vertices:
            raise ValueError("There is no vertex with specifier: " + str(vertex))
        
        startVertices = []
        
        for edge in self.__edges.values():
            if edge.end == vertex:
                startVertices.append(edge.start)
        
        return startVertices
        
        
#     def getEndpointsOfEdge(self, start, end):
#         
#         edgeID = str(start) + "to" + str(end)
#         
#         try:
#             start = self.__edges[edgeID].start
#             end = self.__edges[edgeID].end
#             return (start, end)
#         except KeyError:
#             raise KeyError("There is no edge: " + str(start) + " -> " + str(end))
        
        
    def getCost(self, start, end):
        
        edgeID = str(start) + "to" + str(end)
        
        try:
            return self.__edges[edgeID].cost
        except KeyError:
            raise KeyError("There is no edge: " + str(start) + " -> " + str(end))
        
        
    def setCost(self, start, end, value):
        
        edgeID = str(start) + "to" + str(end)
        
        try:
            self.__edges[edgeID].cost = value
        except KeyError:
            raise KeyError("There is no edge: " + str(start) + " -> " + str(end))
    
    
    def addEdge(self, start, end, cost):
        
        if start not in self.__vertices or end not in self.__vertices:
            raise ValueError("At least one endpoint is not in the graph.")
        
        edgeID = str(start) + "to" + str(end)
        edge = Edge(start, end, cost)
        
        self.__edges[edgeID] = edge
        
        return edgeID
    
        
    def removeEdge(self, start, end):
        
        edgeID = str(start) + "to" + str(end)
        
        try:
            del self.__edges[edgeID]
        except KeyError:
            raise KeyError("There is no edge: " + str(start) + " -> " + str(end))
    
    
    def addVertex(self, vertex):
        
        if vertex in self.__vertices:
            raise ValueError("Vertex already exists: " + str(vertex))
        
        if not(vertex >= 0 and vertex < self.__numberOfVertices):
            raise ValueError("Invalid specifier for vertex (must be in range [0," + str(self.__numberOfVertices) + ") ): " + str(vertex))
        
        self.__vertices.append(vertex)
    
    
    def removeVertex(self, vertex):
        
        try:
            self.__vertices.remove(vertex)
        except ValueError:
            raise ValueError("There is no vertex with specifier: " + str(vertex))
        
        toDelete = []
        
        for edgeID, edge in self.__edges.items():
            if vertex == edge.start or vertex == edge.end:
                toDelete.append(edgeID)
                
        for edgeID in toDelete:
            del self.__edges[edgeID]
    
    
class Console():
    '''
    Console-based UI class
    '''
    
    def __init__(self):
        
        self.__graph = None
        self.__copy = None
    
    
    def start(self):
        
        self.__initialiseGraph()
        self.__executeCommands()
    
    
    def __initialiseGraph(self):
        
        print("Do you want to create a new graph or read one from a text file or create a random one?\nInput 'new' or 'read' or 'random'.")
        
        while True:
            
            print()
            command = input(">> ")
            
            if command == "new":
                
                while True:
                
                    try:
                        
                        print()
                        
                        number = int(input("Number of vertices: "))
                        if number <= 0:
                            raise ValueError
                        
                        self.__graph = DirectedGraph(number)
                        
                        return
                    
                    except ValueError:
                        print("\n\tNumber of vertices must be a strictly positive integer.")
                    
            elif command == "read":
                
                self.__graph = DirectedGraph()
                
                while True:
                    
                    try:
                        
                        print()
                        
                        fileName = input("File name: ")
                        
                        self.__graph.readFromFile(fileName)
                        
                        return
                        
                    except IOError as error:
                        print("\n\t" + str(error))
                        
            elif command == "random":
                
                self.__graph = DirectedGraph()
                
                while True:
                    
                    try:
                        
                        print()
                        
                        numberOfVertices = int(input("Number of vertices: "))
                        numberOfEdges = int(input("Number of edges: "))
                        
                        self.__graph.createRandom(numberOfVertices, numberOfEdges)
                        
                        return
                        
                    except ValueError as error:
                        print("\n\t" + str(error))
                        
            else:
                print ("\n\tInvalid command.")
    
    
    def __executeCommands(self):
        
        while True:
            
            self.__printMenu()
            
            command = input(">> ")
            print()
            
            try: 
                
                if command == '0':
                    
                    print("Closing application...")
                    print("Application has been closed.")
                    return
                                
                elif command == '1':
                    
                    print("\tNumber of vertices: ", self.__graph.getNumberOfVertices())
                    
                elif command == '2':
                    
                    if (self.__graph.getNumberOfVertices() == 0):
                        print("\tEmpty graph!")
                    else:
                        print("\tVertices: ", self.__graph.parseVertices())
                    
                elif command == '3':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    if self.__graph.isEdge(start, end):
                        print("\t" + str(start) + " -> " + str(end) + " is edge")
                    else:
                        print("\t" + str(start) + " -> " + str(end) + " is not an edge")
                    
                elif command == '4':
                    
                    vertex = int(input("Vertex: "))
                    
                    print("\tIn degree of " + str(vertex) + " is: " + str(self.__graph.getInDegree(vertex)))
                    
                elif command == '5':
                    
                    vertex = int(input("Vertex: "))
                    
                    print("\tOut degree of " + str(vertex) + " is: " + str(self.__graph.getOutDegree(vertex)))
                    
                elif command == '6':
                    
                    vertex = int(input("Vertex: "))
                    
                    endVertices = self.__graph.parseOutboundEdges(vertex)
                    
                    if (len(endVertices) == 0):
                        print("\tThere are no outbound edges for: " + str(vertex))
                    else:
                        print("\tOutbound edges for " + str(vertex) + " are: ", endVertices)
                    
                elif command == '7':
                    
                    vertex = int(input("Vertex: "))
                    
                    startVertices = self.__graph.parseInboundEdges(vertex)
                    
                    if (len(startVertices) == 0):
                        print("\tThere are no inbound edges for: " + str(vertex))
                    else:
                        print("\tInbound edges for " + str(vertex) + " are: ", startVertices)
                    
#                 elif command == '8':
#                     
#                     start = int(input("Start vertex: "))
#                     end = int(input("End vertex: "))
#                     print(self.__graph.getEndpointsOfEdge(start, end))
                    
                elif command == '8':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    print("\tCost of edge " + str(start) + " -> " + str(end) + " is: ", self.__graph.getCost(start, end))
                    
                elif command == '9':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    value = int(input("New cost: "))
                    
                    self.__graph.setCost(start, end, value)
                    
                    print("\tCost of edge " + str(start) + " -> " + str(end) + " modified: " + str(value))
                    
                elif command == '10':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    cost = int(input("Cost: "))
                    
                    self.__graph.addEdge(start, end, cost)
                    
                    print("\tEdge added: " + str(start) + " -> " + str(end) + " (" + str(cost) + ")")
                    
                elif command == '11':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    self.__graph.removeEdge(start, end)
                    
                    print("\tEdge removed: " + str(start) + " -> " + str(end))
                    
                elif command == '12':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.addVertex(vertex)
                    
                    print("\tVertex added: " + str(vertex))
                
                elif command == '13':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.removeVertex(vertex)
                    
                    print("\tVertex removed: " + str(vertex))
                    
                elif command == '14':
                    
                    self.__copy = deepcopy(self.__graph)
                    print("\tCopy created.")
                
                elif command == '15':
                    
                    if self.__copy == None:
                        print("\tThere is no copy created.")
                    else:
                        self.__graph = deepcopy(self.__copy)
                        print("\tCopy restored.")
                
                else:
                    print("Invalid command.")
                
            except (KeyError, ValueError) as error:
                print("\t" + str(error))
    
    
    def __printMenu(self):
        
        print("_________________________________________________________________________________")
        print("MENU:")
        print("\t 1.  number of vertices")
        print("\t 2.  parse the set of vertices")
        print("\t 3.  find if it's edge")
        print("\t 4.  indegree of a vertex")
        print("\t 5.  outdegree of a vertex")
        print("\t 6.  parse the set of outbound edges of a vertex")
        print("\t 7.  parse the set of inbound edges of a vertex")
#         print("\t 8.  endpoints of an edge")
        print("\t 8.  get the cost of an edge")
        print("\t 9. set the cost of an edge")
        print("\t 10. add an edge")
        print("\t 11. remove an edge")
        print("\t 12. add a vertex")
        print("\t 13. remove a vertex")
        print("\t 14. create a copy of the current graph")
        print("\t 15. restore the last copy of the graph")
        print("\t 0. exit")
        print("")
        
    
if __name__ == "__main__":
    
    console = Console()
    console.start()
        
        
