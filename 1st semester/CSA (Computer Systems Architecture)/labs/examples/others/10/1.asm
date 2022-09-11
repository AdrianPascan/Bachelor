bits 32
global start

extern exit, scanf, fprintf, fopen, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

segment data use class=data
    number times 4 db 0
    format db "%d", 0
    fileName db "ana.txt", 0
    fileDescriptor dd -1
    accessMode db "r", 0
    writeMode db "w", 0
    outputName db "result.txt"
    outputDescriptor dd -1
    printFormat db "%s \n%d", 0
    dataText db "si 4 pere", 0
    lenDataText db $-dataText
    text times 100 db 0

segment code use32 class=code
start:
    ;read number
    push dword number
    push dword format
    call [scanf]
    add esp, 4*2

    ; fopen(fileDescriptor, "r")
    push dword accessMode
    push dword fileName
    call [fopen]
    add esp, 4*2
    
    cmp eax, 0
    je cannotOpenFile
    mov [fileDescriptor], eax
    
    ; read text from "ana.txt"
    push dword [fileDescriptor]
    push dword 100
    push dword 1
    push dword text
    call [fopen]
    add esp, 4*4
    
    mov esp, -1
    findEnd:
        inc esp
        cmp byte[text+esp], '0'
        jb notDigit
        cmp byte[text+esp], '9'
        ja notDigit
        mov al, byte[text+esp]
        sub byte[text+esp], '0'
        add byte[number], al
        notDigit:
        cmp byte[text+esp], 0
        jne findEnd
    
    mov ecx, 0
    addDataText:
        mov byte[text+esp], [dataText+ecx]
        inc esp
        inc ecx
        cmp ecx, lenDataText+1
        jne addDataText
    
    push dword [fileDescriptor]
    call [fclose]
    add esp, 4*1
    
    ; fopen(outputDescriptor, "w")
    push dword writeMode
    push dword outputName
    call [fopen]
    add esp, 4*2
    
    cmp eax, 0
    je cannotOpenFile
    mov [fileDescriptor], eax
    
    push dword number
    push dword text
    push dword printFormat
    call [fprintf]
    add esp, 4*3
    
    cannotOpenFile:
    
    push dword 0
    call [exit]