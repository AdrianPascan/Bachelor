#include "MylistWindow.h"

MylistWindow::MylistWindow(MylistModel * model, QWidget *parent)
	: QWidget(parent)
{
	ui.setupUi(this);


	ui.tableView->setModel(model);
	ui.tableView->resizeColumnsToContents();
}

MylistWindow::~MylistWindow()
{
}
