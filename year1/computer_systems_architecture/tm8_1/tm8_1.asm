bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
;extern concat
                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    mesaj_a_1 db 'Introduceti lungimea primului sir de caractere',0
    mesaj_a_2 db 'Introduceti primul sir de caractere',0
    mesaj_b_1 db 'Introduceti lungimea celui de-al 2lea sir de caractere',0
    mesaj_b_2 db 'Introduceti al 2lea sir de caractere',0
    mesaj_c_1 db 'Introduceti lungimea celui de-al 3lea sir de caractere',0
    mesaj_c_2 db 'Introduceti al 3lea sir de caractere',0
    format_citire_nr db '%d',0
    format_citire_sir db '%s',0
    a resb 10
    len_a resb 1
    b resb 10
    len_b resb 1
    c resb 10
    len_c resb 1
    s resb 30
    len_s resb 1
    format_afisare db 'Sirul concatenat este %s',0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        ;Se citesc trei siruri de caractere. Sa se determine si sa se afiseze rezultatul concatenarii lor.
        
        push dword mesaj_a_1
        call [printf]
        add esp,4*1
        push dword len_a
        push dword format_citire_nr
        call [scanf]
        add esp,4*2
        ;
        push dword mesaj_a_2
        call [printf]
        add esp,4*1
        push dword a
        push dword format_citire_sir
        call [scanf]
        add esp,4*2
        ;
        push dword mesaj_b_1
        call [printf]
        add esp,4*1
        push dword len_b
        push dword format_citire_nr
        call [scanf]
        add esp,4*2
        ;
        push dword mesaj_b_2
        call [printf]
        add esp,4*1
        push dword b
        push dword format_citire_sir
        call [scanf]
        add esp,4*2
        ;
        push dword mesaj_c_1
        call [printf]
        add esp,4*1
        push dword len_c
        push dword format_citire_nr
        call [scanf]
        add esp,4*2
        ;
        push dword mesaj_c_2
        call [printf]
        add esp,4*1
        push dword c
        push dword format_citire_sir
        call [scanf]
        add esp,4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
