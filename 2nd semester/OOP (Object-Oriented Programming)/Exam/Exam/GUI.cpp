#include "GUI.h"

GUI::GUI(Controller* controller, Participant participant, bool presenter, QWidget *parent)
	: participant{ participant }, presenter{ presenter }, controller{ controller }, QMainWindow(parent)
{
	ui.setupUi(this);

	controller->registerObserver(this);

	setupWindowTitle();
	populateList();
	setupButtons();
	setupListSignals();
}

void GUI::update()
{
	populateList();
}

void GUI::setupWindowTitle()
{
	if (presenter) {
		setWindowTitle("PRESENTER");
	}
	else {
		setWindowTitle(QString::fromStdString(participant.getName()));
	}
}

void GUI::populateList()
{
	if (ui.questionsListWidget->count() > 0) {
		ui.questionsListWidget->clear();
	}

	auto questions = controller->getQuestions();

	if (presenter) {
		sort(questions.begin(), questions.end(), [](Question & q1, Question & q2) {
			return q1.getId() < q2.getId();
		});
		for (auto & question : questions) {
			ui.questionsListWidget->addItem(QString::fromStdString(std::to_string(question.getId()) + ": " + question.getText() +" -> " +  question.getCorrectAnswer() + " (" + std::to_string(question.getScore()) + " points)"));
		}
	}
	else {
		for (auto & question : questions) {
			ui.questionsListWidget->addItem(QString::fromStdString(std::to_string(question.getId()) + ": " + question.getText() + " (" + std::to_string(question.getScore()) + " points)"));
		}

		for (auto row : controller->getRowsAnswered()) {
			ui.questionsListWidget->item(row)->setBackgroundColor("green");
		}
	}
}

void GUI::setupButtons()
{
	QObject::connect(ui.addPushButton, &QPushButton::clicked, this, &GUI::addQuestion);
	if (!presenter) {
		ui.addPushButton->setEnabled(false);
	}

	QObject::connect(ui.answerPushButton, &QPushButton::clicked, this, &GUI::answerQuestion);
	if (presenter) {
		ui.answerPushButton->setEnabled(false);
	}

}

void GUI::setupListSignals()
{
	QObject::connect(ui.questionsListWidget, &QListWidget::itemSelectionChanged, this, &GUI::setupCanAnswer);
}

void GUI::addQuestion()
{
	int id = 0;
	int score = 0;

	try {
		id = std::stoi(ui.idLineEdit->text().toStdString());
		score = std::stoi(ui.scoreLineEdit->text().toStdString());
	}
	catch (std::exception &) {
		ui.statusBar->showMessage("Invalid id and/or score.");
		return;
	}

	std::string text = ui.textLineEdit->text().toStdString();
	if (text.empty()) {
		ui.statusBar->showMessage("Empty text field.");
		return;
	}

	std::string correctAnswer = ui.correctAnswerLineEdit->text().toStdString();

	Question question{ id,text,correctAnswer,score };
	
	try {
		controller->addQuestion(question);
	}
	catch (std::exception & exception) {
		ui.statusBar->showMessage(exception.what());
	}
	
	controller->notify();
	//populateList();
}

void GUI::answerQuestion()
{
	try {
		int row = ui.questionsListWidget->currentRow();
		std::string answer = ui.answerLineEdit->text().toStdString();

		int newScore = controller->answerQuestion(row, answer, participant.getName());

		participant.setScore(newScore);
		ui.currentScoreLineEdit->setText(QString::fromStdString(std::to_string(participant.getScore())));

		controller->notify();
		//ui.questionsListWidget->currentItem()->setBackgroundColor("green");
		ui.answerPushButton->setEnabled(false);
	}
	catch (std::exception & exception) {
		ui.statusBar->showMessage(exception.what());
	}
}

void GUI::setupCanAnswer()
{
	if (ui.questionsListWidget->currentItem()->backgroundColor() == "green") {
		ui.answerPushButton->setEnabled(false);
	}
	else {
		ui.answerPushButton->setEnabled(true);
	}
}
