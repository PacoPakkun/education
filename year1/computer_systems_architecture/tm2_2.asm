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
    
    a dw 16
    b db 5
    c dw 4
    d db 6
    e dd 24
    x dq 2

; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov eax,0
        mov ebx,0
        mov ecx,0
        mov edx,0
        mov eax,dword[x]
        mov edx,dword[x+4] ;edx:eax=x
        mov ebx,2
        idiv ebx ;eax=x/2,edx=rest
        mov ebx,eax ;ebx=x/2
        mov al,byte[b]
        cbw ;ax=b
        add ax,word[a] ;ax=a+
        mov dx,100
        imul dx ;dx:ax=100*(a+b)
        push dx
        push ax
        pop eax
        add ebx,eax ;ebx=x/2+100*(a+b)
        mov al,byte[d]
        cbw ;ax=d
        add ax,word[c] ;ax=c+d
        mov cx,ax ;cx=c+d
        mov dx,0
        mov ax,3
        idiv cx ;ax=3/(c+d),dx=rest
        cwde
        sub ebx,eax ;ebx=x/2+100*(a+b)-3/(c+d)
        mov ax,word[e]
        mov dx,word[e+2] ;dx:ax=e
        imul dword[e] ;edx:eax=e*
        mov ecx,0
        add eax,ebx
        adc edx,ecx ;edx:eax=x/2+100*(a+b)-3/(c+d)+e*e
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
