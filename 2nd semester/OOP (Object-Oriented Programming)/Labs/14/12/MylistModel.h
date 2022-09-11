#pragma once

#include <QAbstractTableModel>
#include "UserController.h"
#include <string>

class MylistModel :
	public QAbstractTableModel
{
private:
	UserController * userController;

public:
	MylistModel();
	MylistModel(UserController * usercontroller, QObject* parent = NULL);
	~MylistModel();

	int rowCount(const QModelIndex &parent = QModelIndex{}) const override;
	int columnCount(const QModelIndex &parent = QModelIndex{}) const override;
	QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
	Qt::ItemFlags flags(const QModelIndex &index) const override;

	void reset() {
		beginResetModel();
		endResetModel();
	}
};

