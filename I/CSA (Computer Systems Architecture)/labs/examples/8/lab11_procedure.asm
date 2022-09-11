; the program calculates and displays the factorial of a number
; the procedure factorial is defined in the code segment of the program
bits 32
global start

extern printf, exit
import printf msvcrt.dll
import exit msvcrt.dll

segment data use32 class=data
	format_string db "factorial=%d",  10, 13, 0

segment code use32 class=code
; procedure definition
factorial: 
	mov eax, 1
	mov ecx, [esp + 4] 
	; mov ecx, [esp + 4] read the parameter from the stack
	; WARNING!!! The return address is on top of the stack.
	; The parameter required by procedure is next to the return address.
	; (see the following diagram)
	;
	; The stack (after the procedure call)
	;
	;|-------------------|
	;|   return address  |  < esp
	;|-------------------|
	;|     00000006h     |  < esp+4 - the parameter required by the procedure
	;|-------------------|
	; ....

	.repeat: 
		mul ecx
	loop .repeat ; the case ecx = 0 is not considered
	
	ret
; "main" program       
start:
	push dword 6        ; pass the parameter to procedure
	call factorial      ; call the procedure

	; display the result
	push eax
	push format_string
	call [printf]
	add esp, 4*2

	push 0
	call [exit]