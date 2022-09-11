def addAtTheEnd (myList, element):
    if not myList:
        return [element]
    return [myList[0]] + addAtTheEnd(myList[1:], element)


def concatenate(list1, list2):
    if list1:
        return [list1[0]] + concatenate(list1[1:], list2)
    return list2[:]


def main():
    myList = [1,2,3,4,5]
    myList2 = [6,7,8,9,10]
        
    print(addAtTheEnd(myList, 11))
    
    print(concatenate(myList, myList2))
    
    print(concatenate([], []))
    print(concatenate(myList, []))
    

if __name__ == "__main__":
    main()
    