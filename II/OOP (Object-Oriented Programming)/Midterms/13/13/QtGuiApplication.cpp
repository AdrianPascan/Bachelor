#include "QtGuiApplication.h"

using namespace std;

QtGuiApplication::QtGuiApplication(Controller * controller, QWidget *parent)
	: controller{ controller }, QMainWindow(parent)
{
	ui.setupUi(this);

	populateList(controller->getAll());

	QObject::connect(ui.filterPushButton, &QPushButton::clicked, this, &QtGuiApplication::filter);
	QObject::connect(ui.totalPushButton, &QPushButton::clicked, this, &QtGuiApplication::total);
}

void QtGuiApplication::populateList(std::vector<Weather>& data)
{
	if (ui.wheatherListWidget->count() > 0) {
		ui.wheatherListWidget->clear();
	}

	for (Weather & wheather : data) {
		ui.wheatherListWidget->addItem(QString::fromStdString(wheather.toString()));
	}
}

void QtGuiApplication::filter()
{
	string data{ ui.filterLineEdit->text().toStdString() };

	if (data.empty()) {
		ui.statusBar->showMessage("Empty precipitations field!");
	}
	else {
		try {
			double precipitations = stod(data);
			populateList(controller->getFiltered(precipitations));
		}
		catch (exception & exception) {
			ui.statusBar->showMessage("Invalid data.");
		}
	}
}

void QtGuiApplication::total()
{
	string data{ ui.totalLineEdit->text().toStdString() };

	ui.totalMessageLineEdit->setText(QString::fromStdString("There will be " + to_string(controller->getDescription(data)) + " hours."));

	//ui.statusBar->showMessage(QString::fromStdString("There will be " + to_string(controller->getDescription(data)) + " hours."));
}
