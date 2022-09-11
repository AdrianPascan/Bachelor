;(a + b + c) - d + (b - c)

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 50
    b dw 100
    c dd 250
    d dq 500

segment code use32 class=code
start:
    
    mov AL, [a] ; AL = a
    cbw ; AX = a
    add AX, [b] ; AX = a+b
    cwde ; AX -> EAX
    add EAX, [c] ; EAX = a+b+c
    mov EBX, EAX ; EBX = a+b+c
    mov AX, [b] ; AX = b
    cwde ; AX -> EAX
    sub EAX, [c] ; EAX = b-c
    add EAX, EBX ; EAX = (a+b+c)+(b-c)
    cdq ; EAX -> EDX:EAX
    sub EAX, dword[d] 
    sbb EDX, dword[d+4] ; EDX:EAX = (a+b+c)-d+(b-c)
    
    push dword 0
    call [exit]