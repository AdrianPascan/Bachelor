'''
Created on Apr 16, 2019

@author: Adrian
'''

from copy import deepcopy
from UndirectedGraph.UndirectedGraph import UndirectedGraph


class UndirectedGraphConsole():
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
        
        print("Do you want to create a new graph or read one from a text file?\nInput 'new' or 'read'.")
        
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
                        
                        self.__graph = UndirectedGraph(number)
                        
                        return
                    
                    except ValueError:
                        print("\n\tNumber of vertices must be a strictly positive integer.")
                    
            elif command == "read":
                
                self.__graph = UndirectedGraph()
                
                while True:
                    
                    try:
                        
                        print()
                        
                        fileName = input("File name: ")
                        
                        self.__graph.readFromFile(fileName)
                        
                        return
                        
                    except IOError as error:
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
                    
                    start = int(input("Vertex 1: "))
                    end = int(input("Vertex 2: "))
                    
                    if self.__graph.isEdge(start, end):
                        print("\t" + str(start) + " -> " + str(end) + " is edge")
                    else:
                        print("\t" + str(start) + " -> " + str(end) + " is not an edge")
                    
                elif command == '4':
                    
                    vertex = int(input("Vertex: "))
                    
                    print("\tDegree of " + str(vertex) + " is: " + str(self.__graph.getDegree(vertex)))
                    
                elif command == '5':
                    
                    vertex = int(input("Vertex: "))
                    
                    endVertices = self.__graph.parseBoundEdges(vertex)
                    
                    if (len(endVertices) == 0):
                        print("\tThere are no bound edges for: " + str(vertex))
                    else:
                        print("\tBound edges for " + str(vertex) + " are: ", endVertices)
                    
                elif command == '6':
                    
                    start = int(input("Vertex 1: "))
                    end = int(input("Vertex 2: "))
                    
                    self.__graph.addEdge(start, end)
                    
                    print("\tEdge added: " + str(start) + " -> " + str(end))
                    
                elif command == '7':
                    
                    start = int(input("Vertex 1: "))
                    end = int(input("Vertex 2: "))
                    
                    self.__graph.removeEdge(start, end)
                    
                    print("\tEdge removed: " + str(start) + " -> " + str(end))
                    
                elif command == '8':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.addVertex(vertex)
                    
                    print("\tVertex added: " + str(vertex))
                
                elif command == '9':
                    
                    vertex = int(input("Vertex: "))
                    
                    self.__graph.removeVertex(vertex)
                    
                    print("\tVertex removed: " + str(vertex))
                    
                elif command == '10':
                    
                    self.__copy = deepcopy(self.__graph)
                    print("\tCopy created.")
                
                elif command == '11':
                    
                    if self.__copy == None:
                        print("\tThere is no copy created.")
                    else:
                        self.__graph = deepcopy(self.__copy)
                        print("\tCopy restored.")
                        
                elif command == '12':
                    
                    components = self.__graph.getConnectedComponents()
                    
                    if len(components) == 0:
                        print("\tEmpty graph!")
                    else:
                        for index in range(len(components)):
                            print(str(index + 1) + ": " + str(components[index]))
                    
                elif command == '13':
                    
                    (maximum, maximumCliques) = self.__graph.getMaxClique()
                    
                    print("Maximum size of maximal cliques: ", maximum)
                    for clique in maximumCliques:
                        print("\t", clique)
                    
                    
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
        print("\t 4.  degree of a vertex")
        print("\t 5.  parse the set of bound edges of a vertex")
        print("\t 6.  add an edge")
        print("\t 7.  remove an edge")
        print("\t 8.  add a vertex")
        print("\t 9.  remove a vertex")
        print("\t 10. create a copy of the current graph")
        print("\t 11. restore the last copy of the graph")
        print("\t 12. get connected components")
        print("\t 13. maximal clique of maximum size")
        print("\t 0.  exit")
        print("")