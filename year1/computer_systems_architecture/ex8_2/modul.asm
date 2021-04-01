bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global modul       

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

    
; our code starts here
segment code use32 class=code
    modul:
        ; ...
        
        mov edx,[esp+20]
        mov edi,0
        mov ebx,[esp+4]
        mov esi,0
        mov ecx,[esp+8]
        sir1:
            mov al,byte[ebx+esi]
            mov byte[edx+edi],al
            inc esi
            inc edi
            loop sir1
        mov ebx,[esp+12]
        mov esi,0
        mov ecx,[esp+16]
        sir2:
            mov al,byte[ebx+esi]
            mov byte[edx+edi],al
            inc esi
            inc edi
            loop sir2
        ret
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
