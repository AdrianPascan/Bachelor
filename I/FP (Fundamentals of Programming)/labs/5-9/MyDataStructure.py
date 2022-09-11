import unittest

class MyDataStructure:


    def __init__(self, data = [], current = 0):
        
        self.data = data
        self.__current = current
    
    
    def __len__(self):
        
        return len(self.data)
    
    
    def __str__(self):
        
        return str(self.data)
        
    
    def __iter__(self):
        
        return self
        
    
    def __next__(self):
        
        if self.__current >= len(self.data):
            self.__current = 0
            raise StopIteration
        
        self.__current += 1
        return self.data[self.__current-1]        
    
    
    def __getitem__(self, key):
        
        self.__validateKey(key)
        
        return self.data[ len(self.data) * (key < 0) + key]
    
    
    def __setitem__(self, key, value):
        
        self.__validateKey(key)
        
        self.data[ len(self.data) * (key < 0) + key ] = value
    
    
    def __delitem__(self, key):
        
        self.__validateKey(key)
        
        self.data.pop( len(self.data) * (key < 0) + key)
        
    
    def __validateKey(self, key):
        
        if type(key) != int:
            raise TypeError("Key must be an integer (not: " + str(type(key)) + " ).")
        if (key > 0 and key >= len(self.data) ) or (key < 0 and abs(key) > len(self.data) ):
            raise IndexError
    
    
    def getAll (self):
        
        return self.data[:]
                
        
class testMyDataStructure (unittest.TestCase):

    def setUp(self):
        
        self.test = MyDataStructure([10, 15, 25])


    def tearDown(self):
        
        del self.test


    def test(self):
        
        self.assertEqual(len(self.test), 3)
        
        number = 0
        for element in self.test:
            number += 1
            self.assertEqual(element, self.test[number-1], 'Iteration not working')
        self.assertEqual(number, 3, "Diffrent number of elements")
        
        self.test[0] = 100
        self.assertEqual(self.test[0], 100, '__setItem__ not working.')
        
        del self.test[1]
        self.assertEqual(len(self.test), 2, "__delItem__: doesn't delete the element")
        self.assertEqual(self.test[1], 25, "__delItem__: remaining elements do not match")


def shellSort (array, order):
    
    length = len(array)
    gap = length//2
    
    while gap > 0:
        
        for start in range(gap):
            
            for index in range(start+gap, length, gap):
                
                value = array[index]
                position = index
                
                while position >= gap and order(array[position-gap], value) == False:
                    array[position] = array[position-gap]
                    position -= gap
                
                array[position] = value
            
        gap //= 2    
        

class testShellSort(unittest.TestCase):
    
    def setUp(self):
        
        self.test = MyDataStructure([100, 20, 40, 70, 90, 60, 120, 50, 40, 80])
        
        
    def tearDown(self):
        del self.test
        
        
    def test(self):
        
        shellSort(self.test, lambda a, b: a < b)  
        self.assertEqual(self.test.getAll(), [20, 40, 40, 50, 60, 70, 80, 90, 100, 120], "shellSort not working") 


def filterBy (array, ok):
    
    matching = []
    
    for current in array:
        if ok(current):
            matching.append(current)
            
    return matching

        
class testFilter(unittest.TestCase):
    
    def setUp(self):
        
        self.test = MyDataStructure([100, 20, 40, 70, 90, 60, 120, 50, 40, 80])
    
        
    def tearDown(self):
        
        del self.test
        
    def test(self):
        
        matching = filterBy(self.test, lambda number: number % 20 == 0 )
        self.assertEqual(matching, [100, 20, 40, 60, 120, 40, 80], "filterBy not working")
        
        
if __name__ == '__main__':
    unittest.main()