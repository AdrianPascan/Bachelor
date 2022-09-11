;(10*a-5*b)+(d-5*c)

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 5
    b db 10
    c db 2
    d dw 100
    aux1 db 10
    aux2 db 5
    
segment code use32 class=code
start:

    mov AL, 10 ; AL = 10
    mul BYTE [a] ; AX = AL * a= 10 * a
    mov BX, AX ; BX = AX = 10 * a
    mov AL, 5 ; AL = 5
    mul BYTE [b] ; AX = AL * b = 5 * b
    sub BX, AX ; BX = BX - AX = 10 * a - 5 * b
    mov AL, 5 ; AL = 5
    mul BYTE [c] ; AX = AL * c = 5 * c
    mov CX, [d] ; CX = d
    sub CX, AX ; CX = CX - AX = d - 5 * c
    add BX, CX ; BX= BX + CX = (10 * a - 5 * b) + (d - 5 * c)
    mov AX, BX ; AX = BX = (10 * a - 5 * b) + (d - 5 * c)
    
    push dword 0
    call [exit]