bits 32

global _printBinaryValue

extern _printf



segment data public data use32

    format db "%d", 0 ; format for a decimal value
    binarySymbol db "b", 0

    

segment code public code use32

_printBinaryValue:
    
    push ebp
    mov ebp, esp
    
    pushad
	
    ; print the parameter as a binary value
    mov ecx, 32 ; ecx := 32
    mov eax, [ebp + 8] ; eax := (value of the parameter)
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
        call _printf ; print the current bit
        add esp, 4*2 ; free parameters on stack (2)
        popad ; restore the values of eax, ebx, ecx, edx registers
        cmp ecx, 0
        ja printBit
    
    push dword binarySymbol ; print "b" for signalising a binary value
    call _printf
    add esp, 4*1
       
    popad
    
    mov esp, ebp
    
    pop ebp
    
    ret
    
    