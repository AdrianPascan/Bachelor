;[(a-d)+b]*2/c

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 5
    b db 4
    c db 3
    d db 2
    
segment code use32 class=code
start: 
    
    mov AL, [a] ; AL = a
    sub AL, [d] ; AL = a-d
    add AL, [b] ; AL = (a-d)+b
    mov BL, 2 ; BL = 2
    mul BL ; AX = AL*2 = [(a-d)+b]*2
    div BYTE [c] ; AL = [(a-d)+b]*2/c , AH = [(a-d)+b]*2%c
    
    push dword 0
    call [exit]