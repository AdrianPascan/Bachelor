#pragma once

#include <QWidget>
#include "ui_MylistWindow.h"
#include "UserController.h"
#include "MylistModel.h"

class MylistWindow : public QWidget
{
	Q_OBJECT

public:
	MylistWindow(MylistModel * model, QWidget *parent = Q_NULLPTR);
	~MylistWindow();

private:
	Ui::MylistWindow ui;
};
