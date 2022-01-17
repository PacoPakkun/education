# university-deadline-tracker

Proiectul nostru este destinat studentilor si cadrelor didactice, pentru a isi putea urmari mai usor temele de laborator, proiectele si termenele de predare. Aplicatia va oferi o interfata interactiva, prin care utilizatorii pot vizualiza un timeline al task-urilor asignate lor de catre profesori sau adaugate chiar de ei. Acest tool va face gestionarea volumului de lucru si a timpului mai usoara si va imbunatati gradul de comunicare intre studenti si profesori referitor la temele propuse, mai ales in mediul online. Aplicatia va functiona asemanator unui ticketing system (precum Azure, Jira) in care se pot construi board-uri cu task-uri asociate diferitelor materii, intretinute de profesori si personalizate de studenti.

Componenta back-end a aplicatiei va fi construita in C# cu persistenta in Sql server (sau altceva, mai vedem😄), utilizand servicii REST pentru a realiza comunicarea cu componenta front-end. Pentru interfata cu utilizatorul vom folosi React. Din punct de vedere al organizarii ca echipa, ne propunem sa folosim Trello pentru impartirea si urmarirea task-urilor si Git pentru versionare.

Must have
- un board principal cu toate task-urile utilizatorului logat
- task-urile vor fi structurate cronologic
- fiecare task va contine un deadline, un status si o descriere succinta

Should have
- materiale si linkuri utile pentru fiecare task
- backlog cu past/completed achievements
- profesorii pot acorda note pentru task-uri complete

Nice to have
- sectiune de comentarii/feedback
- board-uri separate pentru diferite materii
- sistem de notificari pentru deadline-uri noi sau remindere
