#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ui.h"

UI createUI(Service service) {
	//creeaza un struct de tip UI
	//primeste ca parametru un service
	//manipuleaza interfata cu utilizatorul
	UI ui;
	ui.service = service;
	return ui;
}

void run(UI ui) {
	//interfata utilizator de tip command line
	//primeste ca input niste comenzi pe care le valideaza
	//afiseaza output sau erori
	printf("Aplicatie gestiune cofetarie. Selectati comanda dorita:\n[add del upd filt sort]\n");
	printf("Pentru specificatiile comenzilor, introduceti comanda help.\n");
	while (1) {
		char command[100];
		fgets(command, 100, stdin);
		char* ptr = strtok(command, "\n");
		ptr = strtok(command, " ");
		if (strcmp(ptr, "exit") == 0) return;
		else if (strcmp(ptr, "help") == 0) {
			printf("* COMANDA ADD --> permite adaugarea unei materii prime in stoc\n   [add <nume> <producator> <cantitate>]\n   Cantitatea inregistreaza o valoare numerica.\n   Adaugarea unei materii prime care exista deja va actualiza stocul initial.\n");
			printf("* COMANDA DEL --> permite eliminarea unei materii prime din stoc\n   [del <nume>]\n");
			printf("* COMANDA UPD --> permite actualizarea unei materii prime din stoc\n   [upd <nume> <producator> <cantitate>]\n   Cantitatea inregistreaza o valoare numerica.\n");
			printf("* COMANDA FILT --> permite filtrarea stocului dupa un anumit criteriu\n   [filt <nume/cant> <valoare>]\n   Filtrarea dupa nume va afisa materiile prime cu numele dat.\n   Filtrarea dupa cantitate va afisa materiile prime cu o cantitatea mai mica decat cea data\n");
			printf("* COMANDA SORT --> permite sortarea materiilor prime din stoc dupa un anumit criteriu\n   [sort <nume/cant> <cresc/desc>]\n");
		}
		else if (strcmp(ptr,"add") == 0) {
			char nume[20] = "";
			char producator[20] = "";
			ptr = strtok(NULL, " ");
			if (ptr == NULL) {
				printf("[Comanda invalida]\n");
			}
			else {
				strcpy(nume, ptr);
				ptr = strtok(NULL, " ");
				if (ptr == NULL) {
					printf("[Comanda invalida]\n");
				}
				else {
					strcpy(producator, ptr);
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						int num = 1;
						for (int i = 0; i < strlen(ptr); i++) {
							if (isdigit(ptr[i]) == 0) num = 0;
						}
						if (num == 0) {
							printf("[Parametru invalid]\n");
						}
						else {
							int cantitate = atoi(ptr);
							srv_add(&ui.service, nume, producator, cantitate);
							char repoString[1000];
							str(&repoString, ui.service.repo);
							printf("%s\n", repoString);
						}
					}
				}
			}
		}
		else if (strcmp(ptr, "del") == 0) {
			char nume[20] = "";
			ptr = strtok(NULL, " ");
			if (ptr == NULL) {
				printf("[Comanda invalida]\n");
			}
			else {
				strcpy(nume, ptr);
				int err = srv_delete(&ui.service, nume);
				if (err == 0) {
					char repoString[1000];
					str(&repoString, ui.service.repo);
					if (strcmp(repoString, "") == 0) {
						printf("[Repozitoriu vid]\n");
					}
					else {
						printf("%s\n", repoString);
					}
				}
				else {
					printf("[Inregistrare inexistenta]\n");
				}
			}
		}
		else if (strcmp(ptr, "upd") == 0) {
			char nume[20] = "";
			char producator[20] = "";
			ptr = strtok(NULL, " ");
			if (ptr == NULL) {
				printf("[Comanda invalida]\n");
			}
			else {
				strcpy(nume, ptr);
				ptr = strtok(NULL, " ");
				if (ptr == NULL) {
					printf("[Comanda invalida]\n");
				}
				else {
					strcpy(producator, ptr);
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						int num = 1;
						for (int i = 0; i < strlen(ptr); i++) {
							if (isdigit(ptr[i]) == 0) num = 0;
						}
						if (num == 0) {
							printf("[Parametru invalid]\n");
						}
						else {
							int cantitate = atoi(ptr);
							int err = srv_update(&ui.service, nume, producator, cantitate);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, ui.service.repo);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Inregistrare inexistenta]\n");
							}
						}
					}
				}
			}
		}
		else if (strcmp(ptr, "filt") == 0) {
			char criteriu[20] = "";
			ptr = strtok(NULL, " ");
			if (ptr == NULL) {
				printf("[Comanda invalida]\n");
			}
			else {
				strcpy(criteriu, ptr);
				if (strcmp(criteriu, "nume") == 0) {
					char litera;
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						litera = ptr[0];
						MateriePrimaRepo filtru;
						int err = srv_filterNume(&ui.service, &filtru, litera);
						if (err == 0) {
							char repoString[1000];
							str(&repoString, filtru);
							if (strcmp(repoString, "") == 0) {
								printf("[Repozitoriu vid]\n");
							}
							else {
								printf("%s\n", repoString);
							}
						}
						else {
							printf("[Comanda invalida]\n");
						}
						destroyRepo(&filtru);
					}
				}
				else if (strcmp(criteriu, "cant") == 0) {
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						int num = 1;
						for (int i = 0; i < strlen(ptr); i++) {
							if (isdigit(ptr[i]) == 0) num = 0;
						}
						if (num == 0) {
							printf("[Parametru invalid]\n");
						}
						else {
							int cantitate = atoi(ptr);
							MateriePrimaRepo filtru;
							int err = srv_filterCantitate(&ui.service, &filtru, cantitate);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, filtru);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Comanda invalida]\n");
							}
							destroyRepo(&filtru);
						}
					}
				}
				else {
					printf("[Comanda invalida]\n");
				}
			}
		}
		else if (strcmp(ptr, "sort") == 0) {
			char criteriu[20] = "";
			ptr = strtok(NULL, " ");
			if (ptr == NULL) {
				printf("[Comanda invalida]\n");
			}
			else {
				strcpy(criteriu, ptr);
				if (strcmp(criteriu, "nume") == 0) {
					char ordine[20] = "";
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						strcpy(ordine, ptr);
						if (strcmp(ordine, "cresc") == 0) {
							MateriePrimaRepo sortare;
							int err = srv_sortNume(&ui.service, &sortare, 1);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, sortare);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Comanda invalida]\n");
							}
							destroyRepo(&sortare);
						}
						else if (strcmp(ordine, "desc") == 0) {
							MateriePrimaRepo sortare;
							int err = srv_sortNume(&ui.service, &sortare, -1);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, sortare);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Comanda invalida]\n");
							}
							destroyRepo(&sortare);
						}
						else {
							printf("[Comanda invalida]\n");
						}
					}
				}
				else if (strcmp(criteriu, "cant") == 0) {
					char ordine[20] = "";
					ptr = strtok(NULL, " ");
					if (ptr == NULL) {
						printf("[Comanda invalida]\n");
					}
					else {
						strcpy(ordine, ptr);
						if (strcmp(ordine, "cresc") == 0) {
							MateriePrimaRepo sortare;
							int err = srv_sortCantitate(&ui.service, &sortare, 1);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, sortare);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Comanda invalida]\n");
							}
							destroyRepo(&sortare);
						}
						else if (strcmp(ordine, "desc") == 0) {
							MateriePrimaRepo sortare;
							int err = srv_sortCantitate(&ui.service, &sortare, -1);
							if (err == 0) {
								char repoString[1000];
								str(&repoString, sortare);
								if (strcmp(repoString, "") == 0) {
									printf("[Repozitoriu vid]\n");
								}
								else {
									printf("%s\n", repoString);
								}
							}
							else {
								printf("[Comanda invalida]\n");
							}
							destroyRepo(&sortare);
						}
						else {
							printf("[Comanda invalida]\n");
						}
					}
				}
				else {
					printf("[Comanda invalida]\n");
				}
			}
		}
		else {
			printf("[Comanda invalida]\n");
		}
	}
}