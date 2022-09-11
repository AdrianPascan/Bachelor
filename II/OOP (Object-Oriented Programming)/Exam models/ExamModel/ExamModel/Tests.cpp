#include "Tests.h"



Tests::Tests()
{
}


Tests::~Tests()
{
}


void Tests::testAdd()
{
	Repository repository{};

	repository.add(Issue{ "bug", "open", "Adrian", "Dragos" });

	try {
		repository.add(Issue{ "bug", "open", "Adrian", "Dragos" });
		assert(false);
	}
	catch (std::exception &) {
	}
}

void Tests::testResolve()
{
	Repository repository{};
	repository.add(Issue{ "bug", "open", "Adrian", "Dragos" });
	repository.resolve(0, "Dragos");
	
	Issue updated = repository.getIssues()[0];
	assert(updated.status == "closed");
	assert(updated.solver == "Dragos");
}
