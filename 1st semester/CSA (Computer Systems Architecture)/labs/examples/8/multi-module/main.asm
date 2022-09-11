bits 32
global start

import printf msvcrt.dll
import exit msvcrt.dll
extern printf, exit

extern factorial
import factorial modul.asm

segment data use32
	format_string db "factorial=%d", 10, 13, 0

segment code use32 public code
start:
	push dword 6
	call factorial

	push eax
	push format_string
	call [printf]
	add esp, 2*4

	push 0
	call [exit]
