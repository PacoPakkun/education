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
    
    a db 128
    b db 127
    c db 3
    d db 4
    e db 128
    f db 128
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        
        mov al,[a]
        add al,[b] ;al=128+127
        mov eax,0
        mov al,[c]
        mul byte[d] ;ax=3*4
        mov eax,0
        mov ax,[e]
        add ax,word[f] ;ax=128+128
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
