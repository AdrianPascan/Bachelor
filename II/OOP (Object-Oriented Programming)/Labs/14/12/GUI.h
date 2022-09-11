#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_GUI.h"

#include "AdministratorController.h"
#include "UserController.h"
#include "MyException.h"
#include "MylistWindow.h"

#include <string>
#include <vector>
#include <fstream>
#include <QAbstractTableModel>

class GUI : public QMainWindow
{
	Q_OBJECT
	
	AdministratorController * administratorController;
	UserController * userController;
	bool inMemory;
	MylistModel * mylistModel;
	MylistWindow * mylistWindow;

public:
	GUI(AdministratorController * _administratorController, UserController * _userController, QWidget *parent = Q_NULLPTR);

private:
	Ui::GUIClass ui;

	void configureStartUp();

	void administratorSetUp();
	void administratorAddDog();
	void administratorDeleteDog();
	void administratorUpdateDog();
	void administratorSetFilePath();
	void changeToUserMode();
	void undo();
	void redo();
	
	void userSetUp();
	void userFilter();
	void userSave();
	void userOpenFile();
	void userSetFilePath();
	void changeToAdministratorMode();
	void userNext();

	void setListContent(QListWidget * listWidget, std::vector<Dog> & dogs);
};
