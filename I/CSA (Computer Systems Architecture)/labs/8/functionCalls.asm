; Two numbers a and b are given. Compute the expression value: (a/b)*k, where k is a constant value defined in data segment. Display the expression value (in base 2).

bits 32
global start 

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    a dd 0
    b dd 0
    format db "%d", 0 ; format for a decimal value
    mess1 db "a= ", 0 
    mess2 db "b= ", 0
    k dd 2
    res dd 0
    binarySymbol db "b", 0

segment code use32 class=code
    start:
        
        push dword mess1 ; print message: "a= "
        call [printf]
        add esp, 4*1 ; free parameters on stack (1)
        push dword a ; read a
        push dword format
        call [scanf]
        add esp, 4*2 ; free parameters on stack (2)
        
        push dword mess2 ; print message: "b= "
        call [printf]
        add esp, 4*1 ; free parameters on stack (1)
        push dword b ; read b
        push dword format
        call [scanf]
        add esp, 4*2 ; free parameters on stack (2)
        cmp dword[b], 0
        jz zeroDivisor
        
        ; compute (a/b)*k
        mov eax, [a] ; eax := a
        cdq ; eax -> edx:eax
        idiv dword[b] ; eax := edx:eax / b ; edx := edx:eax % b
                      ; use only the quotient, eax, for further computations
        imul dword[k] ; edx:eax := eax * k
        
        ; print the result as a binary value
        mov ecx, 32 ; ecx := 32
        printBit
            dec ecx ; ecx := ecx - 1
            rcl eax, 1 ; the current byte will have the value of CF
            pushad ; save the values stored in eax, ebx, ecx, edx registers onto the stack
            jnc bit0
            push dword 1
            jmp bit1
            bit0
            push dword 0
            bit1
            push format
            call [printf] ; print the current bit
            add esp, 4*2 ; free parameters on stack (2)
            popad ; restore the values of eax, ebx, ecx, edx registers
            cmp ecx, 0
            ja printBit
        
        push dword binarySymbol ; print "b" for signalising a binary value
        call [printf]
        add esp, 4*1
        
        zeroDivisor
        
        push dword 0
        call [exit]
