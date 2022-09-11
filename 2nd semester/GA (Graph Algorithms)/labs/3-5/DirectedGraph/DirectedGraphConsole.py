'''
Created on Apr 16, 2019

@author: Adrian
'''

from copy import deepcopy
from DirectedGraph.DirectedGraph import DirectedGraph


class DirectedGraphConsole():
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
                    
                elif command == '8':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    self.__graph.addEdge(start, end)
                    
                    print("\tEdge added: " + str(start) + " -> " + str(end))
                    
                elif command == '9':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    self.__graph.removeEdge(start, end)
                    
                    print("\tEdge removed: " + str(start) + " -> " + str(end))
                    
                elif command == '10':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.addVertex(vertex)
                    
                    print("\tVertex added: " + str(vertex))
                
                elif command == '11':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.removeVertex(vertex)
                    
                    print("\tVertex removed: " + str(vertex))
                    
                elif command == '12':
                    
                    self.__copy = deepcopy(self.__graph)
                    print("\tCopy created.")
                
                elif command == '13':
                    
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
        print("\t 8. add an edge")
        print("\t 9. remove an edge")
        print("\t 10. add a vertex")
        print("\t 11. remove a vertex")
        print("\t 12. create a copy of the current graph")
        print("\t 13. restore the last copy of the graph")
        print("\t 0. exit")
        print("")