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
    
    a db 5
    b dw 8
    c dd 10
    d dq 13
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov ebx,dword[d]
        mov ecx,dword[d+4] ;ecx:ebx=d
        mov al,byte[a]
        cbw
        cwde
        cdq ;edx:eax=a
        sub ebx,eax
        sbb ecx,edx ;ecx:ebx=d-a
        mov al,byte[a]
        cbw
        cwd ;dx:ax=a
        sub ax,word[c]
        sbb dx,word[c+2] ;dx:ax=a-c
        push dx
        push ax
        pop eax 
        cdq ;edx:eax=a-c
        sub ebx,eax
        sbb ecx,edx ;ecx:ebx=(d-a)-(a-c)
        sub ebx,dword[d]
        sbb ecx,dword[d+4] ;ecx:ebx=(d-a)-(a-c)-d
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
