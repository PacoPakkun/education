bits 32
global start
extern exit, printf, scanf
import exit msvcrt.dll 
import printf msvcrt.dll 
import scanf msvcrt.dll 

segment data use32 class=data
	_format db "Print: %d", 10, 0
	_sformat db "%d", 0
	perimet dd 0
	ok dd 0


segment code use32 class=code
start:
	push dword ok
	push dword _sformat
	call [scanf]
	add esp, 4 * 2
	mov eax, 0
	add eax, [ok]
	add eax, 10
	mov dword [ok], eax
	mov eax, 0
	add eax, [ok]
	mov dword [perimet], eax

	push dword [perimet]
	push dword _format
	call [printf]
	add esp, 4 * 2

	push dword 0
	call [exit]


