; Write a program in assembly language which computes one of the following arithmetic expressions, considering the following domains for the variables: 
; a - doubleword; b, d - byte; c - word; e - qword
; a + b / c - d * 2 - e
bits 32 ;assembling for the 32 bits architecture
; the start label will be the entry point in the program
global  start 

extern  exit ; we inform the assembler that the exit symbol is foreign, i.e. it exists even if we won't be defining it

import  exit msvcrt.dll; exit is a function that ends the process, it is defined in msvcrt.dll
        ; msvcrt.dll contains exit, printf and all the other important C-runtime functions
segment  data use32 class=data ; the data segment where the variables are declared 
	a dd 125
	b db 2
	c dw 15
	d db 200
	e dq 80
segment  code use32 class=code ; code segment
start: 
	;for computing b/c, we convert b from byte to doubleword, so that we can divide it by the word c
	mov al, [b]
	mov ah, 0 ;unsigned conversion from al to ax
	mov dx, 0 ;unsigned conversion from ax to dx:ax
	;dx:ax = b
	div word [c] ;unsigned division dx:ax by c
	;ax=b/c	
	;catul impartirii este in ax (restul este in dx, dar mergem mai departe doar cu catul)

	mov bx, ax ;we save b/c in bx so that we can use ax for multiplying d by 2
	mov al, 2
	mul byte [d] ;ax=d*2

	sub bx, ax ;bx = b / c - d * 2
	; we convert the word bx to doubleword so that we can add it with the doubleword a
	mov cx, 0 ; unsigned conversion from bx to cx:bx
	;cx:bx=b/c-d*2

	mov ax, word [a]
	mov dx, word [a+2] ;dx:ax=a

	add ax, bx
	adc dx, cx ;dx:ax = a + b / c - d * 2
	
	push dx
	push ax
	pop eax ;eax = a + b / c - d * 2
	
	mov edx, 0 ;edx:eax = a + b / c - d * 2
	sub eax, dword [e]
	sbb edx, dword [e+4] ;edx:eax = a + b / c - d * 2 - e
	
	push   dword 0 ;saves on stack the parameter of the function exit
	call   [exit] ; function exit is called in order to end the execution of the program
