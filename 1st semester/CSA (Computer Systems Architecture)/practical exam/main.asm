bits 32 

global start        

extern exit, printf, scanf, fopen, fread, fclose
import exit msvcrt.dll   
import printf msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll


segment data use32 class=data
    N dd 0
    sum dd 0 
    message1 db "File name: ", 0
    message2 db "N (0 <= N < 8)= ", 0
    message3 db "The sum of the N-th bits of every character: ", 0
    format1 db "%s", 0
    format2 db "%d", 0
    fileName times 101 db 0
    mode db "r", 0
    fileDescriptor dd 0
    string times 101 db 0 
    
    

segment code use32 class=code
    start:
        
        push dword message1
        call [printf]
        add esp, 4
        
        push dword fileName
        push dword format1
        call [scanf]
        add esp, 4*2
        
        readN:
            
            push dword message2
            call [printf]
            add esp, 4
            
            push dword N
            push dword format2
            call [scanf]
            add esp, 4*2
            cmp dword[N], 0
            jl readN
            cmp dword[N], 8
            jge readN
        
        
        push dword mode
        push dword fileName
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je final
        mov dword[fileDescriptor], eax
        
        push dword [fileDescriptor]
        push dword 100
        push dword 1
        push dword string
        call [fread]
        add esp, 4*4
        
        push dword [fileDescriptor]
        call [fclose]
        add esp, 4
        
        mov esi, string
        cld
        
        add dword[N], 1
        mov ecx, [N]
        
        character:
            
            lodsb
            
            cmp al, 0
            je finished
            
            rcr al, cl
            
            jnc bit0
            add dword[sum], 1
            bit0:
            
            jmp character
        
        
        finished:
        
        push dword message3
        call [printf]
        add esp, 4
        
        push dword [sum]
        push dword format2
        call [printf]
        add esp, 4*2
        
        final:
        
        push    dword 0     
        call    [exit]   
