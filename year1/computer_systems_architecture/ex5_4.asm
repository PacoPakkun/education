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

    sd dd 123456h,1A2B3C4Dh
    l equ ($-sd)/4
    r times l dw 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        mov esi,2
        mov ecx,2
        mov edi,0
        repeta:
            mov ax,word[sd+esi]
            mov bl,0
            suma:
                mov dl,10
                idiv dl
                add bl,ah
                cmp al,0
                jne suma
            cmp bl,7
            je eticheta
                jmp final
            eticheta:
                mov dx,word[sd+esi]
                mov [r+edi],dx
                add edi,2
            final:
                add esi,4
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
