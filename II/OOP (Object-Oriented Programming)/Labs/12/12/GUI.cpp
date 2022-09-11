#include "GUI.h"

using namespace std;

GUI::GUI(AdministratorController * _administratorController, UserController * _userController, QWidget *parent)
	: administratorController{ _administratorController }, userController{ _userController }, QMainWindow(parent)
{
	ui.setupUi(this);

	configureStartUp();

	ui.mainStackedWidget->setCurrentWidget(ui.administratorStackedWidgetPage);
	administratorSetUp();

	// Administrator Buttons

	QObject::connect(ui.administratorAddPushButton, &QPushButton::clicked, this, &GUI::administratorAddDog);
	QObject::connect(ui.administratorUpdatePushButton, &QPushButton::clicked, this, &GUI::administratorUpdateDog);
	QObject::connect(ui.administratorDeletePushButton, &QPushButton::clicked, this, &GUI::administratorDeleteDog);
	QObject::connect(ui.administratorSetFilePathPushButton, &QPushButton::clicked, this, &GUI::administratorSetFilePath);
	QObject::connect(ui.administratorUserModePushButton, &QPushButton::clicked, this, &GUI::changeToUserMode);
		
	// User Buttons

	QObject::connect(ui.userFilterPushButton, &QPushButton::clicked, this, &GUI::userFilter);
	QObject::connect(ui.userSavePushButton, &QPushButton::clicked, this, &GUI::userSave);
	QObject::connect(ui.userSetFilePathPushButton, &QPushButton::clicked, this, &GUI::userSetFilePath);
	QObject::connect(ui.userOpenFilePushButton, &QPushButton::clicked, this, &GUI::userOpenFile);
	QObject::connect(ui.userSetFilePathPushButton, &QPushButton::clicked, this, &GUI::userSetFilePath);
	QObject::connect(ui.userAdministratorModePushButton, &QPushButton::clicked, this, &GUI::changeToAdministratorMode);
	QObject::connect(ui.userNextPushButton, &QPushButton::clicked, this, &GUI::userNext);
}

void GUI::configureStartUp()
{
	ifstream descriptor{ "D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Laboratories\\12\\configuration.txt"};

	string mode{};

	getline(descriptor, mode);

	//ui.statusBar->showMessage(QString::fromStdString(mode));

	if (mode == "in-memory") {
		inMemory = true;
	}
	else {
		inMemory = false;
	}

	descriptor.close();
}

void GUI::administratorSetUp()
{
	setListContent(ui.administratorDogsListWidget, administratorController->getAllDogs());
}

void GUI::administratorAddDog()
{
	string name{ ui.administratorNameLineEdit->text().toStdString() };
	string breed{ ui.administratorBreedLineEdit->text().toStdString() };
	string birthDate{ ui.administratorBirthDateLineEdit->text().toStdString() };
	string vaccinationsNumber{ ui.administratorVaccinationsNumberLineEdit->text().toStdString() };
	string photograph{ ui.administratorPhotographLineEdit->text().toStdString() };
	
	try {
		if (name.empty() || breed.empty() || birthDate.empty() || vaccinationsNumber.empty() || photograph.empty()) {
			throw MyException{ "At least one of the fields is empty." };
		}

		char * nameString = new char[name.size() + 1];
		strcpy(nameString, name.c_str());

		char * breedString = new char[breed.size() + 1];
		strcpy(breedString, breed.c_str());

		char * birthDateString = new char[birthDate.size() + 1];
		strcpy(birthDateString, birthDate.c_str());

		char * vaccinationsNumberString = new char[vaccinationsNumber.size() + 1];
		strcpy(vaccinationsNumberString, vaccinationsNumber.c_str());

		char * photographString = new char[photograph.size() + 1];
		strcpy(photographString, photograph.c_str());

		administratorController->addDog(nameString, breedString, birthDateString, vaccinationsNumberString, photographString);
		setListContent(ui.administratorDogsListWidget, administratorController->getAllDogs());

		delete[] nameString;
		delete[] breedString;
		delete[] birthDateString;
		delete[] vaccinationsNumberString;
		delete[] photographString;

		ui.statusBar->showMessage((name + " added.").c_str());
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::administratorDeleteDog()
{
	string name{ ui.administratorNameLineEdit->text().toStdString() };

	try {
		if (name.empty()) {
			throw MyException{ "'Name' field is empty." };
		}

		char * nameString = new char[name.size() + 1];
		strcpy(nameString, name.c_str());

		administratorController->deleteDog(nameString);
		setListContent(ui.administratorDogsListWidget, administratorController->getAllDogs());
		
		delete[] nameString;

		ui.statusBar->showMessage((name + " deleted.").c_str());
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::administratorUpdateDog()
{
	string name{ ui.administratorNameLineEdit->text().toStdString() };
	string breed{ ui.administratorBreedLineEdit->text().toStdString() };
	string birthDate{ ui.administratorBirthDateLineEdit->text().toStdString() };
	string vaccinationsNumber{ ui.administratorVaccinationsNumberLineEdit->text().toStdString() };
	string photograph{ ui.administratorPhotographLineEdit->text().toStdString() };

	try {
		if (name.empty() || breed.empty() || birthDate.empty() || vaccinationsNumber.empty() || photograph.empty()) {
			throw MyException{ "At least one of the fields is empty." };
		}

		char * nameString = new char[name.size() + 1];
		strcpy(nameString, name.c_str());

		char * breedString = new char[breed.size() + 1];
		strcpy(breedString, breed.c_str());

		char * birthDateString = new char[birthDate.size() + 1];
		strcpy(birthDateString, birthDate.c_str());

		char * vaccinationsNumberString = new char[vaccinationsNumber.size() + 1];
		strcpy(vaccinationsNumberString, vaccinationsNumber.c_str());

		char * photographString = new char[photograph.size() + 1];
		strcpy(photographString, photograph.c_str());

		administratorController->updateDog(nameString, breedString, birthDateString, vaccinationsNumberString, photographString);
		setListContent(ui.administratorDogsListWidget, administratorController->getAllDogs());

		delete[] nameString;
		delete[] breedString;
		delete[] birthDateString;
		delete[] vaccinationsNumberString;
		delete[] photographString;

		ui.statusBar->showMessage((name + " updated.").c_str());
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::administratorSetFilePath()
{
	if (inMemory) {
		ui.statusBar->showMessage("GUI is configured such that the repository can be only in-memory.");
	}
	else {
		string filePath{ ui.administratorFilePathLineEdit->text().toStdString() };

		char * filePathString = new char[filePath.size() + 1];
		strcpy(filePathString, filePath.c_str());

		administratorController->setFilePath(filePathString);

		delete[] filePathString;

		setListContent(ui.administratorDogsListWidget, administratorController->getAllDogs());

		ui.statusBar->showMessage("File path set.");
	}
}

void GUI::changeToUserMode()
{
	userController->resetSavedRepository();
	userSetUp();
	ui.mainStackedWidget->setCurrentWidget(ui.userStackedWidgetPage);
}

void GUI::userSetUp()
{
	userController->setMatching();
	setListContent(ui.userMatchingDogsListWidget, userController->getMatching());
	setListContent(ui.userSavedDogsListWidget, userController->getSaved());
	ui.userNextLineEdit->clear();
}

void GUI::userFilter()
{
	string breed{ ui.userBreedLineEdit->text().toStdString() };
	string vaccinationsNumber{ ui.userVaccinationsNumberLineEdit->text().toStdString() };

	try {
		if (breed.empty() || vaccinationsNumber.empty()) {
			userController->setMatching();
		}
		else {
			char * breedString = new char[breed.size() + 1];
			strcpy(breedString, breed.c_str());

			char * vaccinationsNumberString = new char[vaccinationsNumber.size() + 1];
			strcpy(vaccinationsNumberString, vaccinationsNumber.c_str());

			userController->setMatching(breedString, vaccinationsNumberString);

			delete[] breedString;
			delete[] vaccinationsNumberString;
		}

		setListContent(ui.userMatchingDogsListWidget, userController->getMatching());
		setListContent(ui.userSavedDogsListWidget, userController->getSaved());
		ui.userNextLineEdit->clear();

		ui.statusBar->showMessage("Dogs were filtered.");
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::userSave()
{
	string name{ ui.userNameLineEdit->text().toStdString() };

	try {
		if (name.empty()) {
			throw MyException{ "'Name' field is empty." };
		}

		char * nameString = new char[name.size() + 1];
		strcpy(nameString, name.c_str());

		userController->saveDog(nameString);
		setListContent(ui.userSavedDogsListWidget, userController->getSaved());

		delete[] nameString;

		ui.statusBar->showMessage((name + " saved.").c_str());
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::userOpenFile()
{
	try {
		userController->openSavedFile();
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::userSetFilePath()
{
	if (inMemory) {
		ui.statusBar->showMessage("GUI is configured such that the repository can be only in-memory.");
	}
	else {
		string filePath{ ui.userFilePathLineEdit->text().toStdString() };

		char * filePathString = new char[filePath.size() + 1];
		strcpy(filePathString, filePath.c_str());

		try {
			if (filePath.empty()) {
				throw MyException{ "'File path' field is empty." };
			}
			userController->setMylistPath(filePathString);
		}
		catch (MyException & exception) {
			ui.statusBar->showMessage(exception.what());
		}

		ui.statusBar->showMessage("File path set.");
	}
}

void GUI::changeToAdministratorMode()
{
	administratorSetUp();
	ui.mainStackedWidget->setCurrentWidget(ui.administratorStackedWidgetPage);
}

void GUI::userNext()
{
	try {
		ui.userNextLineEdit->setText(QString::fromStdString(userController->nextDog().toString()));
	}
	catch (MyException & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::setListContent(QListWidget * listWidget, std::vector<Dog>& dogs)
{
	if (listWidget->count() > 0) {
		listWidget->clear();
	}

	for (Dog & dog : dogs) {
		listWidget->addItem(QString::fromStdString(dog.toString()));
	}
}
