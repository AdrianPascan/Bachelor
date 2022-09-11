#pragma once

template <typename T>
class DynamicVector
{
private:
	T* elements;
	int size;
	int capacity;

public:
	DynamicVector(int capacity = 10);
	DynamicVector( DynamicVector& toCopy);

	~DynamicVector();

	DynamicVector& operator=( DynamicVector& toAssign);
	 T & operator[](int position) ;
	void clear();

	int add( T & element);
	int remove( T & element);
	int update( T & newElement);
	int getSize() ;
	int find( T & element);

private:
	void resize(int factor = 2);
};


template <typename T>
DynamicVector<T>::DynamicVector(int capacity)
{
	this->size = 0;
	this->capacity = capacity;
	this->elements = new T[capacity];
}


template <typename T>
DynamicVector<T>::DynamicVector( DynamicVector<T>& toCopy)
{
	this->size = toCopy.size;
	this->capacity = toCopy.capacity;
	this->elements = new T[this->capacity];
	for (int index = 0; index < this->size; index++)
		this->elements[index] = toCopy.elements[index];
}


template <typename T>
DynamicVector<T>::~DynamicVector()
{
	delete[] this->elements;
}

template <typename T>
DynamicVector<T>& DynamicVector<T>::operator=( DynamicVector<T>& toAssign)
{
	if (this == &toAssign)
		return *this;

	this->size = toAssign.size;
	this->capacity = toAssign.capacity;

	T* auxiliary = new T[this->capacity];

	delete[] this->elements;

	this->elements = auxiliary;
	for (int index = 0; index < this->size; index++)
		this->elements[index] = toAssign.elements[index];

	return *this;
}

template <typename T>
 T & DynamicVector<T>::operator[](int position) 
{
	return this->elements[position];
}

template<typename T>
void DynamicVector<T>::clear()
{
	this->size = 0;
}

template <typename T>
int DynamicVector<T>::add( T & element)
{
	int position = this->find(element);

	if (position != -1) {
		return -1;
	}

	if (this->size == this->capacity)
		this->resize();

	this->elements[this->size] = element;
	this->size++;

	return 0;
}


template<typename T>
int DynamicVector<T>::remove( T & element)
{
	int position = this->find(element);

	if (position == -1) {
		return -1;
	}

	//	delete this->elements[position];
	for (int index = position + 1; index < this->size; index++) {
		this->elements[index - 1] = this->elements[index];
	}
	this->size--;

	return 0;
}


template<typename T>
inline int DynamicVector<T>::update( T & newElement)
{
	int position = this->find(newElement);

	if (position == -1) {
		return -1;
	}

	//	delete this->elements[position];
	this->elements[position] = newElement;

	return 0;
}


template <typename T>
int DynamicVector<T>::getSize() 
{
	return this->size;
}


template<typename T>
int DynamicVector<T>::find( T & element)
{
	for (int index = 0; index < this->size; index++) {
		if (this->elements[index] == element) {
			return index;
		}
	}

	return -1;
}


template <typename T>
void DynamicVector<T>::resize(int factor)
{
	this->capacity *= factor;

	T* newElements = new T[this->capacity];
	for (int index = 0; index < this->size; index++)
		newElements[index] = this->elements[index];

	delete[] this->elements;
	this->elements = newElements;
}
