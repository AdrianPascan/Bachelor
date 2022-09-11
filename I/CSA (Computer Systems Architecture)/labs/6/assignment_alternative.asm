; Being given a string of doublewords, build another string of doublewords which will include only the doublewords from the given string which have an even number of bits with the value 1.

bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dd 132, 128, 4, 5, 64, 384, 1023, 2047; 00000084h, 00000080h, 00000004h, 00000005h, 00000040h, 00000180h, 000003FFh, 000004FFh
    len equ ($-s)/4 ; len := 8
    res times len dd 0 ; final string: 132, 5, 384, 1023
    cntB1 db 0
    two db 2

segment code use32 class=code
start:
    cld ; DF := 0
    mov esi, s ; ds:esi - far address of s
    mov edi, res ; es:edi - far address of res
    mov ecx, len ; ecx := 8
    
    repeat0:
    
        lodsd ; eax - current dword
        add eax, 0 ; eax := eax
        jnp oddNumberOfBits1
        stosd ; store eax      
        oddNumberOfBits1
        
    loop repeat0
    
    push dword 0
    call [exit]