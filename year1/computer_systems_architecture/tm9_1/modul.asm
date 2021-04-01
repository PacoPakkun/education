bits 32

global _concat

segment data public data use32

    adr_sir1 dd 0
    adr_sir2 dd 0
    adr_sir_rez1 dd 0
    adr_sir_rez2 dd 0

segment code public code use32
    _concat:
        push ebp
        mov ebp,esp
        ;pushad
        mov eax,[ebp+8]
        mov [adr_sir1],eax
        mov eax,[ebp+12]
        mov [adr_sir2],eax
        mov eax,[ebp+16]
        mov [adr_sir_rez1],eax
        mov eax,[ebp+20]
        mov [adr_sir_rez2],eax
        mov esi,[adr_sir1]
        mov edi,[adr_sir_rez1]
        push ret1
        jmp eticheta
        ret1:
        mov esi,[adr_sir2]
        push ret2
        jmp eticheta
        ret2:
        mov esi,[adr_sir2]
        mov edi,[adr_sir_rez2]
        push ret3
        jmp eticheta
        ret3:
        mov esi,[adr_sir1]
        push ret4
        jmp eticheta
        ret4:
        ;popad
        mov esp,ebp
        pop ebp
        ret
		eticheta:
            lodsb
            cmp al,'0'
            jae cond1
                jmp final
            cond1:
                cmp al,'9'
                jbe cond2
                    jmp final
                cond2:
                    stosb
            final:
            cmp al,0
            jne eticheta
            jmp [esp]