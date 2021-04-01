bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fclose,fread,printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

    numefisier db 'date.txt',0
    modacces db 'r',0
    descriptor dd -1
    count equ 255
    input resb count
    contor dd 0
    format_afisare db '%d cifre impare',0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        ;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de cifre impare si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.
    
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        mov [descriptor],eax
        cmp eax,0
        je final
            push dword [descriptor]
            push dword count
            push dword 1
            push dword input
            call [fread]
            add esp,4*4
            mov esi,input
            mov ecx,eax
            repeta:
                lodsb
                ror al,1
                jb impar
                    jmp skip
                impar:
                    inc dword[contor]
                skip:    
                    inc esi
            loop repeta
            push dword [contor]
            push dword format_afisare
            call [printf]
            add esp,4*2
            push dword [descriptor]
            call [fclose]
            add esp,4*1
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
