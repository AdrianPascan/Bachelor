;(a-c)+(b-d)

bits 32
global start

extern exit
import exit msvcrt.dll 

segment data use32 class=data 
    a dw 50
    b dw 1000
    c dw 750
    d dw 150

segment code use32 class=code
start:

    mov AX, [a] ; AX = a
    sub AX, [c] ; AX = a-c
    mov BX, [b] ; BX = b
    sub BX, [d] ; BX = b-d
    add AX, BX ; AX = (a-c)+(b-d)
    
    push dword 0
    call [exit]