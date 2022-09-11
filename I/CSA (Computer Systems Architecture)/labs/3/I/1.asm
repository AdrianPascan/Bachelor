;((a + b) + (a + c) + (b + c)) - d

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 50
    b dw 1000
    c dd 2500
    d dq 2000
    
segment code use32 class=code
start:

    mov AL, [a] ; AL = a
    mov AH, 0 ; AL -> AX
    mov BX, 0
    mov BX, [b] ; BX = b
    add BX, AX ; BX = a+b
    mov DX, 0 ; AX -> DX:AX
    add AX, word [c] 
    adc DX, word [c+2] ; DX:AX = a+c
    mov CX, 0 ; BX -> CX:BX
    add BX, AX 
    adc CX, DX ; CX:BX = (a+b)+(a+c)
    mov AX, [b] ; AX = b
    mov DX, 0 ; AX -> DX:AX
    add AX, word [c] 
    adc DX, word [c+2] ; DX:AX = b+c
    add AX, BX
    adc DX, CX ; DX:AX = (a+b)+(a+c)+(b+c)
    push DX
    push AX
    pop EAX ; EAX = (a+b)+(a+c)+(b+c)
    mov EDX, 0 ; EAX -> EDX:EAX
    sub EAX, dword [d]
    sbb EDX, dword [d+4]
    
    push dword 0
    call [exit]