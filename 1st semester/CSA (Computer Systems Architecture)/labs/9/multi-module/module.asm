bits 32

global maximum

segment code use32 class=code public

maximum:
    ; find the maximum value of the string
    
    cld
    mov esi, [esp+8] ; esi := offset of string
    mov ecx, dword[esp+4] ; ecx := length of string
    mov edx, 0FFFFFFFFh ; edx := maximum value
    
    nextElem:
        lodsd
        cmp edx, eax
        jge smallerElem
        mov edx, eax
        smallerElem:
        dec ecx
        cmp ecx, 0
        jnz nextElem
    
    mov eax, edx 
    
    ret