#include "MylistModel.h"

using namespace std;

MylistModel::MylistModel()
{
}

MylistModel::MylistModel(UserController * usercontroller, QObject* parent): QAbstractTableModel{parent}
{
	this->userController = usercontroller;
}


MylistModel::~MylistModel()
{
}

int MylistModel::rowCount(const QModelIndex & parent) const
{
	return userController->getSaved().size();
}

int MylistModel::columnCount(const QModelIndex & parent) const
{
	return 5;
}

QVariant MylistModel::data(const QModelIndex & index, int role) const
{
	if (role == Qt::DisplayRole) {
		Dog dog = userController->getSaved()[index.row()];

		switch (index.column()) {
		case 0:
			return QString::fromStdString(string{ dog.getName() });
		case 1:
			return QString::fromStdString(string{ dog.getBreed() });
		case 2:
			return QString::fromStdString(string{ dog.getBirthDate() });
		case 3:
			return QString::fromStdString(to_string(dog.getVaccinations()));
		case 4:
			return QString::fromStdString(string{ dog.getPhotograph() });
		}
	}

	return QVariant{};
}

QVariant MylistModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0:
			return QString{ "Name" };
		case 1:
			return QString{ "Breed" };
		case 2:
			return QString{ "Birth date" };
		case 3:
			return QString{ "Number of vaccinations" };
		case 4:
			return QString{ "Photograph" };
		default:
			break;
		}
	}

	return QVariant();
}

Qt::ItemFlags MylistModel::flags(const QModelIndex & index) const
{
	return QAbstractTableModel::flags(index);
}
