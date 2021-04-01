bits 32 

global start        

extern exit,scanf,printf,fopen,fclose,fprintf           
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data
    ; ...
    
    mesaj db 'Introduceti un sir de nr nat pozitive incheiat in -1: ',0
    format_citire db '%d',0
    sir resd 10
    nr dd 0
    inv dd 0
    sum dd 0
    numefisier db 'date.txt',0
    modacces db 'w',0
    descriptor dd 0
    format_afisare db '%d ',0
    format_linie db 10,0

segment code use32 class=code
    start:
        push dword mesaj
        call [printf]
        add esp,4*1
        mov edi,sir
        citire:
            push dword edi
            push dword format_citire
            call [scanf]
            add esp,4*2
            add edi,4
            cmp dword[edi-4],-1
            jne citire
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        cmp eax,0
        je err
        mov dword[descriptor],eax
        mov esi,sir
        parcurgere:
            cmp dword[esi],-1
            je final
            lodsd
            mov dword[nr],eax
            mov dword[inv],0
            mov cx,10
            palindrom:
                push eax
                pop ax
                pop dx
                mov bx,10
                div bx
                cwde
                mov ebx,eax
                push dx
                mov ax,cx
                cwde
                mov ecx,eax
                mov eax,dword[inv]
                push eax
                pop ax
                pop dx
                mul ecx
                mov dword[inv],eax
                pop ax
                cwde
                add dword[inv],eax
                mov eax,ebx
                cmp eax,0
                jne palindrom
            push dword [inv]
            push dword format_afisare
            call [printf]
            add esp,4*2
            mov eax,dword[nr]
            cmp eax,dword[inv]
            jne salt
                add dword[sum],eax
                push dword [nr]
                push dword format_afisare
                push dword [descriptor]
                call [fprintf]
                add esp,4*3
            salt:
            jmp parcurgere
        final:
        push dword format_linie
        push dword [descriptor]
        call [fprintf]
        add esp,4*2
        push dword [sum]
        push dword format_afisare
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        err:
        
    
        push    dword 0      
        call    [exit]       