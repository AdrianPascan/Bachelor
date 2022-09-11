'''
Created on May 7, 2019

@author: Adrian
'''

from copy import deepcopy
from DirectedGraphCosts.DirectedGraphCosts import DirectedGraphCosts


class DirectedGraphCostsConsole():
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
                        
                        self.__graph = DirectedGraphCosts(number)
                        
                        return
                    
                    except ValueError:
                        print("\n\tNumber of vertices must be a strictly positive integer.")
                    
            elif command == "read":
                
                self.__graph = DirectedGraphCosts()
                
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
                        
                elif command == '16':
                    
                    start = int(input("Start vertex: "))
                    end = int(input("End vertex: "))
                    
                    print("Lowest cost walk between " + str(start) + " and " + str(end) + " is: " +  str(self.__graph.lowestCostWalk(start, end)))
                
                elif command == '17':
                    
                    self.__graph.function4()
                
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
        print("\t 8.  get the cost of an edge")
        print("\t 9.  set the cost of an edge")
        print("\t 10. add an edge")
        print("\t 11. remove an edge")
        print("\t 12. add a vertex")
        print("\t 13. remove a vertex")
        print("\t 14. create a copy of the current graph")
        print("\t 15. restore the last copy of the graph")
        print("\t 16. get the lowest cost walk between two vertices")
        print("\t 17. practical work no. 4")
        print("\t 0. exit")
        print("")