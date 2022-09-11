#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_QtGuiApplication.h"

#include "Controller.h"
#include "Weather.h"

#include <vector>
#include<iostream>
#include<exception>

class QtGuiApplication : public QMainWindow
{
	Q_OBJECT

	Controller * controller;

public:
	QtGuiApplication(Controller * controller, QWidget *parent = Q_NULLPTR);

private:
	Ui::QtGuiApplicationClass ui;
	void populateList(std::vector<Weather> & data);
	void filter();
	void total();
};
