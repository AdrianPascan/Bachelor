#include "FakeRepository.h"
#include "Dog.h"


FakeRepository::FakeRepository() :Repository{}
{
	char name1[] = "Boo";
	char breed1[] = "yorkshireTerrier";
	char birthDate1[] = "01-13-2017";
	int vaccinations1 = 7;
	char photograph1[] = "booPuppy.jpg";
	Dog dog1{ name1,breed1,birthDate1,vaccinations1,photograph1 };
	this->add(dog1);

	char name2[] = "Tom";
	char breed2[] = "bulldog";
	char birthDate2[] = "05-15-2018";
	int vaccinations2 = 2;
	char photograph2[] = "tom.png";
	Dog dog2{ name2,breed2,birthDate2,vaccinations2,photograph2 };
	this->add(dog2);

	char name3[] = "Ben";
	char breed3[] = "pitbull";
	char birthDate3[] = "02-05-2018";
	int vaccinations3 = 4;
	char photograph3[] = "ben.jpg";
	Dog dog3{ name3,breed3,birthDate3,vaccinations3,photograph3 };
	this->add(dog3);
}


FakeRepository::~FakeRepository()
{
}
