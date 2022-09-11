#pragma once;

typedef int TElem;

//other definitions if necessary (relation, null elements)

class IteratorContainer;

class Container {

	friend class IteratorContainer;

private:
	//container representation
public:
	//implicit constructor
	Container();

	//specific operations

	//returns an iterator for the container
	IteratorContainer iterator() const;

	//destructor
	~Container();

};

