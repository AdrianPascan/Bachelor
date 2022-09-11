#pragma once

#include "Test.h"
#include "FakeRepository.h"
#include "UserController.h"
#include <assert.h>

void test()
{
	FakeRepository * repository = new FakeRepository{};
	UserController * user = new UserController{repository};

	user->setMatching();
	assert(user->getMatching().size() == 3);

	char breed[] = "bulldog";
	char vaccinations[] = "5";
	user->setMatching(breed, vaccinations);
	assert(user->getMatching().size() == 1);

	delete user;
	delete repository;
}
