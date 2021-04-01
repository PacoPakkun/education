bits 32
; informam asamblorul despre existenta functiei _citireSirC si a variabilei _str3
extern _citireSirC
extern _str3
; in Windows, directiva nasm import poate fi folosita doar pentru fisiere obj!! noi vom crea un fisier win32, functia printf se importa in felul urmator (conform documentatiei NASM)
extern _printf
; informam asamblorul ca dorim ca functia _asmConcat sa fie disponibila altor unitati de compilare
global _asmConcat
; linkeditorul poate folosi segmentul public de date si pentru date din afara
segment data public data use32
    lenRez                dd        0
    adresaSirRezultat     dd        0
    adresaSirParam        dd        0
    adresaSirCitit        dd        0
    mesaj                 db        "Sirul 2 citit din modulul asm: ", 0
; codul scris in asamblare este dispus intr-un segment public, posibil a fi partajat cu alt cod extern
segment code public code use32
; int asmConcat(char[], char[])
; conventie stdcall
_asmConcat:
    ; creare cadru de stiva pentru programul apelat
    push ebp
    mov ebp, esp
    
    ; rezervam spatiu pe stiva pentru variabilele locale
    sub esp, 4 * 3                    ; rezervam pe stiva 4*3 de octeti pentru sirul citit de la tastatura
    ; argumentele transmise pe stiva functiei asmConcat
    ; la locatia [ebp+4] se afla adresa de return (valoarea din EIP la momentul apelului)
    ; la locatia [ebp] se afla valoarea ebp pentru apelant
    mov eax, [ebp + 8]
    mov [adresaSirParam], eax
    mov eax, [ebp + 12]
    mov [adresaSirRezultat], eax
    ; salvam adresa sirului care va fi citit
    mov [adresaSirCitit], ebp
    sub dword [adresaSirCitit], 4*3
       
    ; apelam functia citireSirC din C pentru a citi sirul 2
    push dword mesaj
    call _printf
    add esp, 4*1
    push dword [adresaSirCitit]
    call _citireSirC
    add esp, 4*1
    ; concatenam sirurile
    ; copiem sirul transmis ca parametru functiei asmConcat (sir1) in sirul rezultat
    cld
    mov edi, [adresaSirRezultat]
    mov esi, [adresaSirParam]
    mov ecx, 10
    rep movsb
    ; copiem sirul citit folosind functia citireSirC in sirul rezultat
    add dword [lenRez], 10
    mov esi, [adresaSirCitit]
    mov ecx, 10
    rep movsb
    ; copiem sirul variabila globala in programul C (sir3) in sirul rezultat
    add dword [lenRez], 10
    mov esi, _str3
    mov ecx, 10
    rep movsb
    add dword [lenRez], 10
    
    ; stergem spatiul rezervat pe stiva
    ; !! nu am salvat valorile registrilor inainte de a scrie functia _asmConcat, daca se doreste acest lucru se poate face cu instructiunile PUSHAD si restaurarea lor cu POPAD
    add esp, 4 * 3
    ; refacem cadrul de stiva pentru programul apelant
    mov esp, ebp
    pop ebp
    ; cele doua instructiuni ce refac cadrul de stiva pot fi inlocuite cu instructiunea leave
    ; intoarcem ca si rezultat lungimea sirului concatenat
    mov eax, [lenRez]
    ret
    ; conventie stdcall - este responsabilitatea programului apelant sa elibereze parametrii transmisi