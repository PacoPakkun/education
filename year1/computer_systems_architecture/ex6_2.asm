bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll
                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    n dd 0
    m dd 0
    k equ 10
    format db '%d',0
    mesajn db 'n=',0
    mesajm db 'm=',0
    mesajsuma db 'suma dintre %d si %d este %d,',0
    mesajconst db ' suma cu 10 este %d',0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        push dword mesajn
        call [printf]
        add esp,4*1
        push dword n
        push dword format 
        call [scanf]
        add esp,4*2
        push dword mesajm
        call [printf]
        add esp,4*1
        push dword m
        push dword format
        call [scanf]
        add esp,4*2
        mov eax,dword[n]
        add eax,dword[m]
        push dword eax
        push dword [m]
        push dword [n]
        push dword mesajsuma
        call [printf]
        add esp,4*4
        add eax,k
        push dword eax
        push dword mesajconst
        call [printf]
        add esp,4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
