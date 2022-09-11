'''
Created on Mar 15, 2019

@author: Adrian
'''

class Graph():
    '''
    
    '''

    def isEdge(self, x, y):
        '''
        x, y - vertices
        return: bool (True - is an edge)
        '''
        pass
    
    
    def parseNOut(self, x):
        '''
        x - vertex
        return: list of vertices
        '''
        pass
    
    
    def parseNIn(self, x):
        '''
        x - vertex
        return: list of vertices
        '''
        pass
    
    
    def nrVertices(self):
        '''
        return: nr. of vertices (int)
        '''
        pass
    
    
    def parseAllVertices(self):
        '''
        return: all vertices (list)
        '''
        pass
    
    
    def __init__(self, n):
        '''
        Constructs a graph with n vertices numbered from 0 to n-1 and no edges
        Note: it is acceptable to have n=0
        '''
        pass
    
    
    def addElem(self, x, y):
        '''
        adds an edge from x to y
        precond.: x,y exist as vertices
                  (x,y) does not exist as as edge
        return: None
        '''
        pass
    
    
    def removeEdge(self, x, y):
        '''
        removes the edge from x to y
        precond.: (x,y) exists as an edge
        return: None
        '''
        pass
    
    
    def addVertex(self, x):
        '''
        Adds x as a vertex
        precond.: x does not exist as a vertex
                  x is immutable and hashable
        return: None
        '''
        pass
    
    
def sample():
    
    g = Graph()
    
    if g.isEdge(x, y):
        pass
    
    for y in g.parseNOut(x):
        doSomething(y)
        
    for x in range(g.nrVertices()):
        pass
        
        
def main():
    pass
