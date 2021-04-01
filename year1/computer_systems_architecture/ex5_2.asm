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

    s db 1,-1,0,0FAh,0Ah,-2,-3
    l equ $-s
    p times l db 0
    n times l db 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        mov ecx,l
        mov edi,n
        ;mov edi,0
        mov esi,s
        ;mov esi,0
        mov ebp,p
        ;mov ebp,0
        repeta:
            lodsb
            ;mov al,[s+esi]
            cmp al,0
            jge eticheta
                stosb
                ;mov [n+edi],al
                ;inc edi
                jmp final
            eticheta:
                xchg edi,ebp
                stosb
                ;mov [p+ebp],al
                ;inc ebp
                xchg ebp,edi
            final:
                ;inc esi
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
