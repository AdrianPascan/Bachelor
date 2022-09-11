#pragma once

#include <QtWidgets/QApplication>
#include <QLabel>
#include <qlistwidget.h>
#include <QFormLayout>
#include <QLineEdit>
#include <qpushbutton.h>

#include <string>

#include "Repository.h"
#include "AdministratorController.h"
#include "UserController.h"


int main(int argc, char *argv[])
{
	//GUI

	QApplication application(argc, argv);

	QWidget* main = new QWidget{};
	main->setWindowTitle("Adopt a PET");

	QHBoxLayout* modeHorizontalLayout = new QHBoxLayout{};

	// ADMINISTRATOR

	QVBoxLayout* administratorVerticalLayout = new QVBoxLayout{};

	// label

	QLabel* administartorLabel = new QLabel{ "ADMINISTRATOR" };
	administratorVerticalLayout->addWidget(administartorLabel);

	// dogs

	QLabel* dogsLabel = new QLabel{ "Dogs: " };

	QListWidget* dogsList = new QListWidget{};

	dogsLabel->setBuddy(dogsList);

	administratorVerticalLayout->addWidget(dogsLabel);
	administratorVerticalLayout->addWidget(dogsList);

	// form

	QFormLayout* administratorForm = new QFormLayout{};

	QLineEdit* nameTextBox = new QLineEdit{};
	QLabel* nameLabel = new QLabel{ "&Name: " };
	nameLabel->setBuddy(nameTextBox);
	administratorForm->addRow(nameLabel, nameTextBox);

	QLineEdit* breedTextBox = new QLineEdit{};
	QLabel* breedLabel = new QLabel{ "&Breed: " };
	breedLabel->setBuddy(breedTextBox);
	administratorForm->addRow(breedLabel, breedTextBox);

	QLineEdit* birthDateTextBox = new QLineEdit{};
	QLabel* birthDateLabel = new QLabel{ "&Birth date: " };
	birthDateLabel->setBuddy(birthDateTextBox);
	administratorForm->addRow(birthDateLabel, birthDateTextBox);

	QLineEdit* vaccinationsNumberTextBox = new QLineEdit{};
	QLabel* vaccinationsNumberLabel = new QLabel{ "&Number of vaccinations: " };
	vaccinationsNumberLabel->setBuddy(vaccinationsNumberTextBox);
	administratorForm->addRow(vaccinationsNumberLabel, vaccinationsNumberTextBox);

	administratorVerticalLayout->addItem(administratorForm);

	// buttons

	QHBoxLayout* buttonsHorizontalLayout = new QHBoxLayout{};

	QPushButton* addButton = new QPushButton{ "&Add" };
	buttonsHorizontalLayout->addWidget(addButton);

	QPushButton* updateButton = new QPushButton{ "&Update" };
	buttonsHorizontalLayout->addWidget(updateButton);

	QPushButton* deleteButton = new QPushButton{ "&Delete" };
	buttonsHorizontalLayout->addWidget(deleteButton);

	administratorVerticalLayout->addLayout(buttonsHorizontalLayout);

	// administrator file path

	QHBoxLayout* filePathHorizontalLayout = new QHBoxLayout{};

	QLineEdit* filePathTextBox = new QLineEdit{};
	QPushButton* filePathButton = new QPushButton{ "&Set file path" };

	filePathHorizontalLayout->addWidget(filePathTextBox);
	filePathHorizontalLayout->addWidget(filePathButton);

	administratorVerticalLayout->addLayout(filePathHorizontalLayout);


	// ADMINISTRATOR: END



	// USER

	QVBoxLayout* userVerticalLayout = new QVBoxLayout{};

	// label

	QLabel* userLabel = new QLabel{ "USER" };
	userVerticalLayout->addWidget(userLabel);

	// matching dogs

	QLabel* matchingLabel = new QLabel{ "Matching dogs: " };

	QListWidget* matchingList = new QListWidget{};

	matchingLabel->setBuddy(matchingList);

	userVerticalLayout->addWidget(matchingLabel);
	userVerticalLayout->addWidget(matchingList);

	// filter

	QVBoxLayout* filterHorizontalLayout = new QVBoxLayout{};

	QFormLayout* filterForm = new QFormLayout{};

	QLineEdit* breedTextBoxFilter = new QLineEdit{};
	QLabel* breedLabelFilter = new QLabel{ "&Breed: " };
	breedLabelFilter->setBuddy(breedTextBoxFilter);
	filterForm->addRow(breedLabelFilter, breedTextBoxFilter);

	QLineEdit* vaccinationsNumberTextBoxFilter = new QLineEdit{};
	QLabel* vaccinationsNumberLabelFilter = new QLabel{ "&Number of vaccinations: " };
	vaccinationsNumberLabelFilter->setBuddy(vaccinationsNumberTextBoxFilter);
	filterForm->addRow(vaccinationsNumberLabelFilter, vaccinationsNumberTextBoxFilter);

	filterHorizontalLayout->addLayout(filterForm);

	QPushButton* filterButton = new QPushButton{ "&Filter" };
	filterHorizontalLayout->addWidget(filterButton);

	userVerticalLayout->addLayout(filterHorizontalLayout);

	// saved dogs

	QVBoxLayout* savedVerticalLayout = new QVBoxLayout{};

	QLabel* savedLabel = new QLabel{ "Saved dogs: " };

	QListWidget* savedList = new QListWidget{};

	savedLabel->setBuddy(savedList);

	savedVerticalLayout->addWidget(savedLabel);
	savedVerticalLayout->addWidget(savedList);

	
	QHBoxLayout* saveNameHorizontalLayout = new QHBoxLayout{};

	QLabel* saveNameLabel = new QLabel{ "&Name: " };

	QLineEdit* saveNameTextBox = new QLineEdit{};

	saveNameLabel->setBuddy(saveNameTextBox);

	saveNameHorizontalLayout->addWidget(saveNameLabel);
	saveNameHorizontalLayout->addWidget(saveNameTextBox);

	QPushButton* saveNameButton = new QPushButton{ "&Save" };
	saveNameHorizontalLayout->addWidget(saveNameButton);

	savedVerticalLayout->addLayout(saveNameHorizontalLayout);

	userVerticalLayout->addLayout(savedVerticalLayout);

	// user file

	QVBoxLayout* userFileVerticalLayout = new QVBoxLayout{};

	QHBoxLayout* userFilePathHorizontalLayout = new QHBoxLayout{};

	QLineEdit* userFilePathTextBox = new QLineEdit{};
	QPushButton* userFilePathButton = new QPushButton{ "&Set file path" };

	userFilePathHorizontalLayout->addWidget(userFilePathTextBox);
	userFilePathHorizontalLayout->addWidget(userFilePathButton);

	userFileVerticalLayout->addLayout(userFilePathHorizontalLayout);

	QPushButton* openFileButton = new QPushButton{ "&Open file" };
	userFileVerticalLayout->addWidget(openFileButton);

	userVerticalLayout->addLayout(userFileVerticalLayout);

	// USER: END

	modeHorizontalLayout->addItem(administratorVerticalLayout);
	modeHorizontalLayout->addItem(userVerticalLayout);

	main->setLayout(modeHorizontalLayout);

	main->show();

	// GUI: END

	Repository repository{};
	repository.initialize();
	AdministratorController administratorController{&repository};
	UserController userController{ &repository };

	for (Dog & dog : administratorController.getAllDogs()) {
		QListWidgetItem* item = new QListWidgetItem{ dog.toString().c_str() };
		dogsList->insertItem(0, item);
	}
	dogsList->sortItems();

	return application.exec();
}
