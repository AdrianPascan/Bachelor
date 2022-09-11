#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_GUI.h"
#include "Participant.h"
#include "Controller.h"
#include "Observer.h"
#include <string>

class GUI : public QMainWindow, public Observer
{
	Q_OBJECT

	bool presenter;
	Participant participant;
	Controller* controller;

public:
	GUI(Controller* controller, Participant participant, bool presenter = false, QWidget *parent = Q_NULLPTR);
	void update() override;

private:
	Ui::GUIClass ui;
	void setupWindowTitle();
	void populateList();
	void setupButtons();
	void setupListSignals();
	void addQuestion();
	void answerQuestion();
	void setupCanAnswer();
	//bool answered(int id);
};
