;A file name and a text (defined in data segment) are given. The text contains lowercase letters, uppercase letters, digits and special characters. Replace all digits from the text with character 'C'. Create a file with the given name and write the generated text to file.

bits 32
global start

extern exit, fopen, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data
    fileName db "output.txt", 0
    accessMode db "w", 0
    text db "127 can be represented on a byte, but 145891 cannot.", 0
    len equ $-text
    fileDescriptor dd -1
    format db "%s", 0
    
segment code use class=code
start:
    ; fopen (fileName, accessMode)
    push dword accessMode
    push dword fileName
    call [fopen]
    add esp, 4*2
    
    cmp eax, 0
    je couldNotOpenFile
    
    mov [fileDescriptor], eax

    mov esi, 0
    mov ecx, len
    parseText:
        cmp byte[text+esi], '0'
        jb notDigit
        cmp byte[text+esi], '9'
        ja notDigit
        mov byte[text+esi], 'C'
        notDigit:
        inc esi
        cmp ecx, esi
        ja parseText
    
    push dword text
    push dword format
    push dword [fileDescriptor]
    call [fprintf]
    add esp, 4*3
    
    couldNotOpenFile:
    
    push dword 0
    call [exit]
    