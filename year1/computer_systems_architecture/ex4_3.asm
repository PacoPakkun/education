bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    s db 1,2,3,0Ah
    lung_s equ $-s
    k equ 5
    rez times lung_s db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        mov esi,0
        mov ecx,lung_s
        repeta:
            mov al,[s+esi]
            add al,k
            mov [rez+esi],al
            add esi,1
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
