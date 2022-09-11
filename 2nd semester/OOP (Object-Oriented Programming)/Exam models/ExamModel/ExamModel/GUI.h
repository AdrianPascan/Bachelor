#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_GUI.h"
#include "Controller.h"
#include "Observer.h"
#include <string>
#include <exception>

class GUI : public QMainWindow, public Observer
{
	Q_OBJECT

	Controller * controller;
	User user;

public:
	GUI(User user, Controller * controller, QWidget *parent = Q_NULLPTR);

	void update() override;

private:
	
	Ui::GUIClass ui;

	void populateIssues();
	void addIssue();
	void removeIssue();
	void setResolveButton();
	void resolveIssue();
};
