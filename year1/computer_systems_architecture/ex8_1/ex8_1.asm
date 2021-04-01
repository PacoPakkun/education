bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern printf,scanf,exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
extern modul
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

    mesaja db 'Introduceti a=',0
    mesajb db 'Introduceti b=',0
    format db '%d',0
    formatafisare db 'suma=%d',0
    a dd 0
    b dd 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        push dword mesaja
        call [printf]
        add esp,4*1
        push dword a
        push dword format
        call [scanf]
        add esp,4*2
        push dword mesajb
        call [printf]
        add esp,4*1
        push dword b
        push dword format
        call [scanf]
        add esp,4*2
        push dword[a]
        push dword[b]
        call modul
        add esp,4*2
        push dword ebx
        push dword formatafisare
        call [printf]
        add esp,4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
