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

    numefisier db 'date.txt',0
    modacces db 'w',0
    descriptor dd 0
    format_citire db '%d',0
    format_afisare_d db '%d ',0
    format_afisare_x db '%x ',0
    format_linie db 10,0
    sir resd 100

segment code use32 class=code
    start:
        mov edi,sir-4
        citire:
            add edi,4
            push dword edi
            push dword format_citire
            call [scanf]
            add esp,4*2
            cmp dword[edi],0
            jne citire
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        cmp eax,0
        je err
        mov dword[descriptor],eax
        mov esi,sir
        repeta:
            cmp dword[esi],0
            je final
            push dword[esi]
            push dword format_afisare_d
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            push dword[esi]
            push dword format_afisare_x
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            mov ecx,0
            mov eax,dword[esi]
            nr_biti:
                shl eax,1
                adc ecx,0
                cmp eax,0
                jne nr_biti
            push ecx
            push dword format_afisare_d
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            push dword format_linie
            push dword [descriptor]
            call [fprintf]
            add esp,4*2
            add esi,4
            jmp repeta
        final:
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        err:
    
        push    dword 0     
        call    [exit] 