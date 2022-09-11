; Two byte strings A and B are given. Obtain the string R by concatenating the elements of B in reverse order and the elements of A in reverse order. 

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2, 1, -3, 0
    lenA equ $-a
    b db 4, 5, 7, 6, 2, 1
    lenB equ $-b
    r times (lenA+lenB) db 0
   
segment code use32 class=code
start:
    mov ecx, lenB ; ecx := 6
    mov esi, 0 ; esi := 0
    mov edi, lenB-1 ; edi := 5
    jecxz aIsVoid
    Repeat1:
        mov al, [b+esi]
        mov [r+edi], al ; copying the elements of b in reverse order in r
        inc esi ; esi := esi+1
        dec edi ; edi := edi-1
    loop Repeat1
    aIsVoid
    
    mov ecx, lenA ; ecx := 4
    mov esi, 0 ; esi := 0
    mov edi, lenA+lenB-1 ; edi := 9
    jecxz bIsVoid
    Repeat2: 
        mov al, [a+esi]
        mov [r+edi], al ; copying the elements of a in reverse order in r
        inc esi ; esi := esi+1
        dec edi ; edi := edi-1
    loop Repeat2
    bIsVoid
    
    push dword 0
    call [exit]