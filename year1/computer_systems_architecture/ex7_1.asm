bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fread,fprintf,fclose              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    numefisier db 'date.txt',0
    modacces db 'w',0
    descriptor dd -1
    numar dd 1234
    suma dd 0
    formatd db '%d',0
    formath db '%x',0
    formatlinie db 10,13,0
    copie dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        mov [descriptor],eax
        cmp eax,0
        je final
        mov eax,[numar]
        calcule:
            mov edx,0
            mov ebx,10
            div ebx
            mov [copie],eax
            add [suma],edx
            push dword edx
            push dword formatd
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            push dword formatlinie
            push dword [descriptor]
            call [fprintf]
            add esp,4*2
            mov eax,[copie]
            cmp eax,0
            jne calcule
        push dword [suma]
        push dword formath
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        final:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
