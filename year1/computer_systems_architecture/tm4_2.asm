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

    a db 2,1,3,-3
    lung_a equ $-a
    b db 4,5,-5,7
    lung_b equ $-b
    r resb lung_a+lung_b 
    lung_r equ $-r
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        ;Se dau 2 siruri de octeti A si B. Sa se construiasca sirul R care sa contina doar elementele impare si pozitive din cele 2 siruri.
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov ecx,lung_a
        mov esi,a
        mov edi,r
        repeta_a:
            lodsb
            cmp al,0
            jg pozitiv_a
                jmp final_a
            pozitiv_a:
                mov dl,al
                cbw
                mov bl,2
                idiv bl ;al=cat,ah=rest
                cmp ah,1
                je impar_a
                    jmp final_a
                impar_a
                    mov al,dl
                    stosb
            final_a:
        loop repeta_a
        mov ecx,lung_b
        mov esi,b
        repeta_b:
            lodsb
            cmp al,0
            jg pozitiv_b
                jmp final_b
            pozitiv_b:
                mov dl,al
                cbw
                mov bl,2
                idiv bl ;al=cat,ah=rest
                cmp ah,1
                je impar_b
                    jmp final_b
                impar_b:
                    mov al,dl
                    stosb
            final_b:
        loop repeta_b
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
