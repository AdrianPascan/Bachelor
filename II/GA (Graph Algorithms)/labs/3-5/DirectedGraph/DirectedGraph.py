'''
Created on Apr 16, 2019

@author: Adrian
'''

from random import randint


class DirectedGraph():
    '''    
    
    '''


    def __init__(self, n=1):
        
        self._inbounds = {}
        self._outbounds = {}
        
        for vertex in range(n):
            self._inbounds[vertex] = []
            self._outbounds[vertex] = []
    
    
    def getNumberOfVertices(self):
        
        return len(self._inbounds)
    
    
    def parseVertices(self):
        
        return list(self._inbounds.keys())
    
    
    def isEdge(self, start, end, validate = True):
        
        if validate:
            self._validateVertex(start)
            self._validateVertex(end)
        
        if end in self._outbounds[start]:
            return True
        
        return False
    
    
    def getInDegree(self, vertex, validate = True):
        
        if validate: 
            self._validateVertex(vertex)
        
        return len(self._inbounds[vertex])
    
    
    def getOutDegree(self, vertex, validate = True):
        
        if validate:
            self._validateVertex(vertex)
        
        return len(self._outbounds[vertex])
    
    
    def parseOutboundEdges(self, vertex, validate = True):
        
        if validate:
            self._validateVertex(vertex)
            
        return self._outbounds[vertex][:]
    
    
    def parseInboundEdges(self, vertex, validate = True):
        
        if validate:
            self._validateVertex(vertex)
        
        return self._inbounds[vertex][:]
    
    
    def addEdge(self, start, end, validate = True):
        
        if validate:
            self._validateVertex(start)
            self._validateVertex(end)
        
        if end in self._outbounds[start]:
            raise ValueError("Already existing edge: " + str(start) + "->" + str(end))
        
        self._outbounds[start].append(end)
        self._inbounds[end].append(start)
    
        
    def removeEdge(self, start, end, validate = True):
        
        if validate:
            self._validateVertex(start)
            self._validateVertex(end)
        
        try:
            self._outbounds[start].remove(end)
            self._inbounds[end].remove(start)
        except ValueError:
            raise ValueError("Non-existing edge: " + str(start) + "->" + str(end))
    
    
    def addVertex(self, vertex, validate = True):
        
        if validate:
            self._validatePositiveInteger(vertex)
                
        if vertex in self._inbounds.keys():
            raise ValueError("Already existing vertex: " + str(vertex))
        
        self._inbounds[vertex] = []
        self._outbounds[vertex] = []
    
    
    def removeVertex(self, vertex, validate = True):
        
        if validate:
            self._validateVertex(vertex)
        
        del self._inbounds[vertex]
        del self._outbounds[vertex]
        
        for endVertices in self._outbounds.values():
            try:
                endVertices.remove(vertex)
            except ValueError:
                pass
            
        for startVertices in self._inbounds.values():
            try:
                startVertices.remove(vertex)
            except ValueError:
                pass
    
    
    def _validateVertex(self, vertex):
        
        if vertex not in self._inbounds.keys():
            raise ValueError("Non-existing vertex: " + str(vertex))
        
        
    def _validatePositiveInteger(self, number):
        
        if not isinstance(number, int) or number < 0:
            raise ValueError("Invalid positive integer (must be of type int): " + str(number))
        
    
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
                    
                    self.addEdge(start, end, validate=False)
                    
        except IOError:
            
            raise IOError("Could not open file: " + str(fileName))
    
    
    def createRandom(self, numberOfVertices, numberOfEdges):
        
        self._validatePositiveInteger(numberOfVertices)
        self._validatePositiveInteger(numberOfEdges)        
        
        if numberOfVertices < 1:
            raise ValueError("Number of vertices must be greater than 0.")
        
        if numberOfEdges > numberOfVertices**2:
            raise ValueError("Number of edges is invalid.")
        
        self.__init__(numberOfVertices)
        
        for edgeNumber in range(numberOfEdges):
            
            start = randint(0, numberOfVertices-1)
            end = randint(0, numberOfVertices-1)
            
            while(self.isEdge(start, end, validate=False)):
                start = randint(0, numberOfVertices-1)
                end = randint(0, numberOfVertices-1)
                
            self.addEdge(start, end, validate=False)
        
    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
