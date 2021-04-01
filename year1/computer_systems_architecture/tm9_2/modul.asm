bits 32

extern _printf
extern _prop

global _modul

segment data use32 class=data

    format db '%d ',0

segment code use32 class=code
    _modul:
        push ebp
        mov ebp,esp
        mov edx,0
        mov esi,_prop
        repeta:
            lodsb
            cmp al,'A'
            jae cond1
                jmp nu
            cond1:
                cmp al,'Z'
                jbe cond2
                    jmp sau
                cond2:
                    inc edx
                    jmp final
            sau:
                cmp al,'a'
                jae cond3
                    jmp nu
                cond3:
                    cmp al,'z'
                    jbe cond4
                        jmp nu
                    cond4:
                        inc edx
                        jmp final
            nu:
                push eax
                push edx
                push dword format
                call _printf
                add esp,4*2
                mov edx,0
                pop eax
            final:
                cmp al,0
                jne repeta
        mov esp,ebp
        pop ebp