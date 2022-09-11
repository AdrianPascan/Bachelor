;a-(7+x)/(b*b-c/d+2)
;a-doubleword; b,c,d-byte; x-qword
;signed

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a dd 1
    b db 10
    c db 40
    d db 50
    x dq 300
    
segment code use32 class=code
start:
    
    mov AL, [b] ; AL = b
    imul BYTE [b] ; AX = b*b
    mov DX, AX ; DX = b*b
    mov AL, [c] ; AL = c
    cbw ; AL -> AX
    idiv BYTE [d] ; AL = c/d , AH = c%d (keep the integer part only)
    neg AL ; AL = -c/d
    cbw; AL -> AX
    add AX, DX ; AX = b*b-c/d
    adc AX, 2 ; AX = b*b-c/d+2
    cwde ; AX -> EAX
    mov EBX, EAX ; EBX = b*b-c/d+2
    mov EAX, dword [x]
    mov EDX, dword [x+4] ; EAX:EDX = x
    add EAX, 7 
    adc EDX, 0 ; EDX:EAX = 7+x
    idiv EBX ; EAX = (7+x)/(b*b-c/d+2) , EDX = (7+x)%(b*b-c/d+2) (keep the integer part only)
    mov EBX, EAX ; EBX = (7+x)/(b*b-c/d+2)
    mov EAX, [a] ; EAX = a
    sbb EAX, EBX ; EAX = a-(7+x)/(b*b-c/d+2)
    
    push dword 0
    call [exit]