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

    a dw 0101010101010101b
    b dw 1100110011001100b
    c dd 0b
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        ;Se dau cuvintele A si B. Sa se obtina dublucuvantul C:
        ;   bitii 0-4 ai lui C coincid cu bitii 11-15 ai lui A
        ;   bitii 5-11 ai lui C au valoarea 1
        ;   bitii 12-15 ai lui C coincid cu bitii 8-11 ai lui B
        ;   bitii 16-31 ai lui C coincid cu bitii lui A
        ;c=a15a14..a0b11b10b9b81111111a15a14a13a12a11
        
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov ax,word[a] ;ax=0101010101010101(2)=21845(10)=5555(16)
        mov cl,5 ;cl=00000101(2)=5(10)=5(16)
        rol ax,cl ;ax=1010101010101010(2)=43690(10)=AAAA(16)
        and ax,11111b ;ax=0000000000001010(2)=10(10)=A(16)
        or word[c],ax ;c=00000000000000000000000000001010(2)=10(10)=A(16)
        or word[c],111111100000b ;c=00000000000000000000111111101010(2)=4074(10)=FEA(16)
        mov ax,word[b] ;ax=1100110011001100(2)=52428(10)=CCCC(16)
        mov cl,4 ;cl=00000100(2)=4(10)=4(16)
        shl ax,cl ;ax=1100110011001100(2)=52428(10)=CCCC(16)
        and ax,1111000000000000b ;ax=1100000000000000(2)=49152(10)=C000(16)
        or word[c],ax ;c=00000000000000001100111111101010(2)=53226(10)=CFEA(16)
        mov ax,word[a] ;ax=0101010101010101(2)=21845(10)=5555(16)
        or word[c+2],ax ;c=01010101010101011100111111101010(2)=1431687146(10)=5555CFEA(16)
        mov eax,dword[c]
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
