; Given the doubleword M, compute the doubleword MNew as follows:
; - the bits 0-3 a of MNew are the same as the bits 5-8 a of M
; - the bits 4-7 a of MNew have the value 1
; - the bits 27-31 a of MNew have the value 0
; - the bits 8-26 of MNew are the same as the bits 8-26 a of M

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    M dd 10101010101010101010101010101010b
    Mnew dd 0
    
segment code use32 class=code
start:
    ; keep the result in ebx register
    mov ebx, 0 ; ebx := 00000000h
    
    ; the bits 0-3 a of ebx are the same as the bits 5-8 a of M
    mov eax, [M] ; eax := AAAAAAAAh
    and eax, 0000000000000000000000111100000b ; eax := 000000A0h
    mov cl, 5 ; cl := 05h
    ror eax, cl ; eax := 00000005h
    or ebx, eax ; ebx := 00000005h
    
    ; the bits 4-7 a of ebx have the value 1
    or ebx, 00000000000000000000000011110000b ; ebx := 000000F5h
    
    ;the bits 27-31 a of ebx have the value 0
    and ebx, 00000111111111111111111111111111b ; ebx := 000000F5h
    
    ; the bits 8-26 of ebx are the same as the bits 8-26 a of M
    mov eax, [M] ; eax := AAAAAAAAh
    and eax, 00000111111111111111111100000000b ; eax := 02AAAA00h
    or ebx, eax ; ebx := 02AAAA00h
    
    ; move the result to Mnew
    mov [Mnew], ebx ; Mnew := 02AAAA00h
    
    push dword 0
    call [exit]