#include <QtWidgets/QApplication>
#include "test.h"
#include "gui.h"

int main(int argc, char* argv[])
{
	QApplication a(argc, argv);

	Test t;
	t.runAllTests();
	RepoFile repo{ "file.txt" };
	RepoStandard contract;
	Service srv{ repo, contract };
	Observable o;
	GUI gui{ srv, o };
	gui.show();

	return a.exec();
}
