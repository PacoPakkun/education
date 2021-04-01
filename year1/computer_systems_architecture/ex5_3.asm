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

    sw dw 1,0ABh,1234h,5678h,3,0C0Bh
    l equ ($-sw)/2
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        mov esi,1
        mov ecx,l
        mov dl,0
        repeta:
            mov al,byte[sw+esi]
            cbw
            mov bl,3
            idiv bl
            cmp ah,0
            je eticheta
                jmp final
            eticheta:
                inc dl
            final:
                add esi,2
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
