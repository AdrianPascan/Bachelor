#pragma once

#include "FileRepository.h"
#include "AdministratorController.h"
#include "UserController.h"

class Tests
{
	FileRepository * repository;
	AdministratorController * administrator;
	UserController * user;

public:
	Tests();
	~Tests();
	void testAll();

private:
	void test_setFilePath();
	void test_addDog();
	void test_updateDog();
	void test_deleteDog();
	void test_getAllDogs();
	
	void test_setMatching();
	void test_next();
	void test_saveDog();
	void test_getMatching();
	void test_getSaved();
};

