bits 32

global start        

extern exit,printf,fopen,fclose,fread               
import exit msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
   
segment data use32 class=data

    numefisier db 'date.txt',0
    modacces db 'r',0
    descriptor dd 0
    sir resb 100
    cuvant resb 15
    invers resb 15
    primul db 0
    format_afisare db '%s',0

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
        push dword sir
        call [fread]
        add esp,4*4
        mov esi,sir
        mov edi,cuvant
        mov byte[primul],1
        mov ecx,0
        parcurgere:
            lodsb
            cmp al,0
            je final
            cmp al,' '
            je spatiu
                cmp al,'.'
                je punct
                    stosb
                    inc ecx
                    jmp eticheta
                punct:
                    push esi
                    mov ebx,ecx
                    add ebx,cuvant
                    sub ebx,1
                    mov esi,ebx
                    mov edi,invers
                    repeta1:
                        std
                        lodsb
                        cld
                        stosb
                        loop repeta1
                    pop esi
                    inc esi
                    mov byte[primul],1
                    mov al,'.'
                    stosb
                    push dword invers
                    push dword format_afisare
                    call [printf]
                    add esp,4*2
                    mov edi,cuvant
                    mov ecx,15
                    jmp stergere
            spatiu:
                cmp byte[primul],1
                jne inversare
                    push esi
                    mov ebx,ecx
                    add ebx,cuvant
                    sub ebx,1
                    mov esi,ebx
                    mov edi,invers
                    repeta2:
                        std
                        lodsb
                        cld
                        stosb
                        loop repeta2
                    sub ebx,cuvant
                    add ebx,invers
                    add byte[ebx],32
                    pop esi
                    mov byte[primul],0
                    mov al,' '
                    stosb
                    push dword invers
                    push dword format_afisare
                    call [printf]
                    add esp,4*2
                    mov edi,cuvant
                    mov ecx,15
                    jmp stergere
                inversare:
                mov al,' '
                stosb
                push dword cuvant
                push dword format_afisare
                call [printf]
                add esp,4*2
                mov edi,cuvant
                mov ecx,15
                stergere:
                    mov al,0
                    stosb
                    loop stergere
                mov ecx,0
                mov edi,cuvant
            eticheta:    
            jmp parcurgere
            final:
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        err:
    
        push    dword 0     
        call    [exit]      
