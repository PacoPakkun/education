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
    modacces db 'r',0
    descriptor dd 0
    format_citire db '%s',0
    mesaj_criptat resb 100
    mesaj_original resb 100
    format_afisare db 'Mesajul original este: %s',0

segment code use32 class=code
    start:
    
        push dword modacces
        push dword numefisier
        call [fopen]
        add esp,4*2
        cmp eax,0
        je err
        mov dword[descriptor],eax
        push dword [descriptor]
        push dword 100
        push dword 1
        push dword mesaj_criptat
        call [fread]
        add esp,4*4
        mov esi,mesaj_criptat
        mov edi,mesaj_original
        repeta:
            lodsb
            cmp al,0
            je final
            cmp al,'a'
            jb cond1
                cmp al,'z'
                ja cond1
                    sub al,2
                    cmp al,'a'
                    jae cond1
                        add al,'z'-'a'
            cond1:
            cmp al,'A'
            jb cond2
                cmp al,'Z'
                ja cond2
                    sub al,2
                    cmp al,'A'
                    jae cond2
                        add al,'Z'-'A'
            cond2:
            stosb
            jmp repeta
        final:
        push dword mesaj_original
        push dword format_afisare
        call [printf]
        add esp,4*2
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        err:
    
    push    dword 0     
    call    [exit]