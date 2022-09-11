;(a-b-b-c)+(a-c-c-d)

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 50
    b db 20
    c db 2
    d db 5
    
segment code use32 class=code
start:
    
    mov BL, [a] ; BL = a
    sub BL, [c] ; BL = a-c
    mov AL, BL ; AL = a-c
    sub AL, [b] ; AL = a-b-c
    sub AL, [b] ; Al = a-b-b-c
    sub BL, [c] ; BL = a-c-c
    sub BL, [d] ; BL = a-c-c-d
    add AL, BL ; AL = (a-b-b-c)+(a-c-c-d)
    
    push dword 0
    call [exit]