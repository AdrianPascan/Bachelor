; Being given a string of doublewords, build another string of doublewords which will include only the doublewords from the given string which have an even number of bits with the value 1.

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dd 132, 128, 4, 5, 64, 384, 1023; 00000084h, 00000080h, 00000004h, 00000005h, 00000040h, 00000180h, 000003FFh 
    len equ ($-s)/4 ; len := 7
    res times len dd 0 ; final string: 132, 5, 384, 1023
    cntB1 db 0
    two db 2

segment code use32 class=code
start:
    cld ; DF := 0
    mov esi, s ; ds:esi - far address of s
    mov edi, res ; es:edi - far address of res
    mov ecx, len ; ecx := 7
    
    repeat0:
    
        lodsd ; eax - current dword
        cmp eax, 0
        jz dword0 ; eax := 0
        mov edx, eax ; edx := eax
        mov byte[cntB1], 0 ; cntB1 := 0
        mov ebx, 32 ; ecx := 32
        countBits1
            rcr eax, 1 ; last bit of edx will be kept in CF
            jnc bit0
            inc byte[cntB1] ; count bit 1
            bit0
            dec ebx ; ebx := ebx - 1 
            cmp ebx, 0
            ja countBits1
        mov al, byte[cntB1] ; al := cntB1
        mov ah, 0 ; ah := 0
        div BYTE[two] ; al := ax / 2, ah := ax % 2
        cmp ah, 0
        jnz notZero
        dword0
        mov eax, edx ; eax := edx
        stosd ; store eax
        notZero
        
    loop repeat0
    
    push dword 0
    call [exit]