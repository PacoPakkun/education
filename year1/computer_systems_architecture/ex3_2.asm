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

    a dw 01010101b
    b db 11001100b
    c db 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov ax,[a]
        mov cl,8
        shl ax,cl
        mov bx,[c]
        or bh,ah
        mov al,[b]
        mov cl,3
        rol al,cl
        and al,00000111b
        or bl,al
        mov ax,[a]
        mov cl,7
        shr ax,cl
        and al,111000b
        or bl,al
        mov ax,[a]
        mov cl,2
        ror ax,cl
        and ax,1100000000000000b
        not ah
        and ah,11000000b
        or bl,ah
        mov word[c],bx
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
