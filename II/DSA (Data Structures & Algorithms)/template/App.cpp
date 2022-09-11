#include <iostream>
#include "Container.h"
#include "IteratorContainer.h"

using namespace std;


int main() {


	Container c;
	// iteration
	IteratorContainer ic = c.iterator();
	while (ic.valid()) {
		TElem e = ic.getCurrent();
		//element processing
		ic.next();
	}

	cout<<"End";


}
