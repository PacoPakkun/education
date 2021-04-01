bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
extern modul
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

    format db 'Sirul concatenat este %s',0
    s1 db 'perco',0
    lens1 equ 5
    s2 db 'anni',0
    lens2 equ 4
    s resb 15
    lens equ 9
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        push dword s
        push dword lens2
        push dword s2
        push dword lens1
        push dword s1
        call modul
        add esp,4*5
        push dword s
        push dword format
        call [printf]
        add esp,4*2
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
