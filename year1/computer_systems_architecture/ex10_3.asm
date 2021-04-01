bits 32

global start        

extern exit,printf,scanf,fopen,fclose,fprintf,fread               
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fread msvcrt.dll
   
segment data use32 class=data

    mesaj db 'Introduceti sirul de caractere: ',0
    format_citire db '%s',0
    sir resb 100
    suma dd 0
    numefisier db 'date.txt',0
    modacces db 'w',0
    descriptor dd 0
    format_afisare_cifra db '%c',10,0
    format_afisare_suma db '%d',0

segment code use32 class=code
    start:
        push dword mesaj
        call [printf]
        add esp,4*1
        push dword sir
        push dword format_citire
        call [scanf]
        add esp,4*2
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        cmp eax,0
        je err
        mov dword[descriptor],eax
        mov esi,sir
        repeta:
            lodsb
            cmp al,0
            je final
            cmp al,'0'
            jb cond
                cmp al,'9'
                ja cond
                    cbw
                    cwde
                    push eax
                    push dword format_afisare_cifra
                    push dword [descriptor]
                    call [fprintf]
                    add esp,4*2
                    pop eax
                    sub eax,'0'
                    add dword[suma],eax
            cond:
            jmp repeta
        final:
        push dword [suma]
        push dword format_afisare_suma
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        err:
        
        push    dword 0     
        call    [exit] 