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
    
    s dd 127F5678h, 0ABCDABCDh
    lung_s equ ($-s)/4
    r resd lung_s

; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        ;Se da un sir de dublucuvinte continand date impachetate (4 octeti scrisi ca un singur dublucuvant). Sa se obtina un nou sir de dublucuvinte, in care fiecare dublucuvant se va obtine dupa regula: suma octetilor de ordin impar va forma cuvantul de ordin impar, iar suma octetilor de ordin par va forma cuvantul de ordin par. Octetii se considera numere cu semn, astfel ca extensiile pe cuvant se vor realiza corespunzator aritmeticii cu semn.
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov ecx,lung_s
        mov esi,0
        mov edi,0
        repeta:
            mov al,byte[s+esi+1]
            cbw
            mov bx,ax
            mov al,byte[s+esi+3]
            cbw
            add bx,ax
            mov [r+edi+2],bx
            mov al,byte[s+esi]
            cbw
            mov bx,ax
            mov al,byte[s+esi+2]
            cbw
            add bx,ax
            mov [r+edi],bx
            add esi,4
            add edi,4
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
