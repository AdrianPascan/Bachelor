'''
Created on Apr 16, 2019

@author: Adrian
'''

'''
Created on Apr 16, 2019

@author: Adrian
'''

from DirectedGraph.DirectedGraph import DirectedGraph


class UndirectedGraph():
    '''    
    
    '''


    def __init__(self, n=1):
        
        self._graph = DirectedGraph(n)
    
    
    def getNumberOfVertices(self):
        
        return self._graph.getNumberOfVertices()
    
    
    def parseVertices(self):
        
        return self._graph.parseVertices()
    
    
    def isEdge(self, start, end, validate = True):
        
        return self._graph.isEdge(start, end, validate)
    
    
    def getDegree(self, vertex, validate = True):
        
        return self._graph.getInDegree(vertex, validate)
    
    
    def parseBoundEdges(self, vertex, validate = True):
        
        return self._graph.parseOutboundEdges(vertex, validate)
    
    
    def addEdge(self, start, end, validate = True):
        
        self._graph.addEdge(start, end, validate)
        
        if start == end:
            self._graph.removeEdge(start, end, validate=False)
            raise ValueError("Cannot have loops in an undirected graph.")
        
        self._graph.addEdge(end, start, validate = False)
    
        
    def removeEdge(self, start, end, validate = True):
        
        self._graph.removeEdge(start, end, validate)
        self._graph.removeEdge(end, start, validate=False)
    
    
    def addVertex(self, vertex, validate = True):
        
        self._graph.addVertex(vertex, validate)
    
    
    def removeVertex(self, vertex, validate = True):
        
        self._graph.removeVertex(vertex, validate)
    
    
    def __validateVertex(self, vertex):
        
        if vertex not in self.__inbounds.keys():
            raise ValueError("Non-existing vertex: " + str(vertex))
        
        
    def __validatePositiveInteger(self, number):
        
        if not isinstance(number, int) or number < 0:
            raise ValueError("Invalid positive integer (must be of type int): " + str(number))
        
    
    def readFromFile(self, fileName):
        
        try:
            
            with open(fileName, "r") as file:
                                
                data = file.read().split('\n')
                
                n = int(data[0].split(' ')[0])
                self._graph.__init__(n)
                
                for edge in data[1:]:
                    numbers = edge.split(' ')

                    start = int(numbers[0])
                    end = int(numbers[1])                        
                    
                    if start != end:
                        try:
                            self.addEdge(start, end, validate=False)
                        except ValueError:
                            pass
                     
        except IOError:
            
            raise IOError("Could not open file: " + str(fileName))
        
        
    '''
    PRACTICAL WORK NO. 2
    '''
    '''
    BEGINNING
    '''
    def getConnectedComponents(self):
        
        visited = {}
        for vertex in self._graph.parseVertices():
            visited[vertex] = False
            
        components = []
            
        for vertex in visited.keys():
            if visited[vertex] == False:
                components.append(UndirectedGraph)
                self._DFSforVertex(vertex, visited, components)
                
        return components
                
    
    def _DFSforVertex(self, vertex, visited, components):
        
        visited[vertex] = True
        components[-1].append(vertex)
        
        for current in self._graph._outbounds[vertex]:
            if visited[current] == False:
                self._DFSforVertex(current, visited, components)
    '''
    END
    '''
                
    def prettyPrint(self):
        
        vertices = self._graph.parseVertices()
        print("Vertices: ", vertices)
        print ("Edges: ")
        for vertex in vertices:
            print(vertex, ": ", self.parseBoundEdges(vertex, validate=False))
                
        
        
    '''
        PRACTICAL WORK NO. 5
    '''
    
    def bronKerbosch(self, possibleClique, vertices, processed, cliques):
        
        if len(vertices) == 0 and len(processed) == 0:
            cliques.append(possibleClique)
            return
        
        for vertex in vertices:
            newPossibleClique = possibleClique[:]
            newPossibleClique.append(vertex)
            newVertices = [value for value in vertices if value in self.parseBoundEdges(vertex, validate=False)]
            newProcessed = [value for value in processed if value in self.parseBoundEdges(vertex, validate=False)]
            self.bronKerbosch(newPossibleClique, newVertices, newProcessed, cliques)
            vertices.remove(vertex)
            processed.append(vertex)
    
    
    def getMaxClique(self):
        
        vertices = self.parseVertices()
        cliques = []
        self.bronKerbosch([], vertices, [], cliques)
        
        print ("CLIQUES:")
        for clique in cliques:
            print(clique)
            
        print("MAXIMUM CLIQUE(S):")
        
        maximum = len(cliques[0])
        maximumCliques = [cliques[0]]
        for clique in cliques[1:]:
            if maximum < len(clique):
                maximum = len(clique)
                maximumCliques = [clique]
            elif maximum == len(clique):
                maximumCliques.append(clique)
        
        return (maximum, maximumCliques)
    
    '''
        PRACTICAL WORK NO. 5
    '''
        
        
        
        
        
        
        
        