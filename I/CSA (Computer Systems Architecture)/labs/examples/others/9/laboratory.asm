bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    doi db 2
    format db "%d", 0
    format0 db "%d %d %d", 0
    mess1 db "a= ", 0
    mess2 db "b= ", 0
    mess3 db "c= ", 0
    printMess db "Rezultatul este: %d", 0
    a dd 0
    b times 2 dw 0
    c times 4 db 0
    
segment code use32 class=code
start:
    
    push dword mess1
    call [printf]
    add esp, 4*1
    push dword a
    push dword format
    call [scanf]
    add esp, 4*2
    
    push dword mess2
    call [printf]
    add esp, 4*1
    push dword b
    push dword format
    call [scanf]
    add esp, 4*2
    
    push dword mess3
    call [printf]
    add esp, 4*1
    push dword c
    push dword format
    call [scanf]
    add esp, 4*2
    
    push dword c
    push dword b
    push dword a
    push dword format0
    call [printf]
    add esp, 4*4
    
    mov ax, word[a]
    mov dx, word[a+2]
    idiv word[b]
    cwde
    mov edx, eax
    mov al, [c]
    cbw
    cwde
    add eax, ecx
    mov edx, eax
    ror edx, 16
    mov cl, [doi]
    mov ch, 0
    idiv cx
    
    cwde
    push eax
    push printMess
    call [printf]
    add esp, 4*2
    
    push dword 0
    call [exit]