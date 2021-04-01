#pragma once
#include "service.h"

typedef struct {
	Service service;
} UI;

UI createUI(Service service);

void run(UI ui);