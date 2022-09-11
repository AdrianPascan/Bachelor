#include "GUI.h"

GUI::GUI(User user, Controller * controller, QWidget *parent)
	: QMainWindow(parent), controller{controller}, user{user}
{
	ui.setupUi(this);

	setWindowTitle(QString::fromStdString(user.name + " - " + user.type));

	ui.resolvePushButton->setEnabled(false);
	ui.issuesListWidget->setCurrentRow(-1);

	if (user.type.compare("tester") != 0) {
		ui.addPushButton->setEnabled(false);
	}
	else {
		ui.resolvePushButton->setEnabled(false);
	}

	populateIssues();

	QObject::connect(ui.addPushButton, &QPushButton::clicked, this, &GUI::addIssue);
	QObject::connect(ui.removePushButton, &QPushButton::clicked, this, &GUI::removeIssue);
	QObject::connect(ui.resolvePushButton, &QPushButton::clicked, this, &GUI::resolveIssue);
	QObject::connect(ui.issuesListWidget, &QListWidget::itemSelectionChanged, this, &GUI::setResolveButton);

}

void GUI::update()
{
	populateIssues();
}

void GUI::populateIssues()
{
	if (ui.issuesListWidget->count() > 0) {
		ui.issuesListWidget->clear();
	}

	for (auto & issue : controller->getIssues()) {
		ui.issuesListWidget->addItem(QString::fromStdString(issue.toString()));
	}
}

void GUI::addIssue()
{
	std::string description{ ui.descriptionLineEdit->text().toStdString() };

	if (description.empty()) {
		ui.statusBar->showMessage("Empty description field!");
	}
	else {
		try {
			Issue issue{ description, "open", user.name, "" };
			controller->addIssue(issue);
			controller->notify();
			ui.statusBar->showMessage(QString::fromStdString(description + " added."));
		}
		catch (std::exception & exception) {
			ui.statusBar->showMessage(exception.what());
		}
	}
}

void GUI::removeIssue()
{
	int row = ui.issuesListWidget->currentRow();

	if (row < 0) {
		ui.statusBar->showMessage("No item selected.");
	}
	else {
		try {
			controller->removeIssue(row);
			controller->notify();

			ui.statusBar->showMessage("Item removed.");
			ui.issuesListWidget->setCurrentRow(-1);
			ui.resolvePushButton->setEnabled(false);
		}
		catch (std::exception & exception) {
			ui.statusBar->showMessage(exception.what());
		}
	}
}

void GUI::setResolveButton()
{
	if (user.type == "programmer" && controller->getIssues()[ui.issuesListWidget->currentRow()].status == "open") {
		ui.resolvePushButton->setEnabled(true);
	}
	else {
		ui.resolvePushButton->setEnabled(false);
	}
}

void GUI::resolveIssue()
{
	try {
		int row = ui.issuesListWidget->currentRow();
		controller->resolveIssue(row, user.name);
		controller->notify();

		ui.issuesListWidget->setCurrentRow(-1);
		ui.resolvePushButton->setEnabled(false);
	}
	catch (std::exception & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}
