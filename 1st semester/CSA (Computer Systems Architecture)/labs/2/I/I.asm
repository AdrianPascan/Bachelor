bits 32 

global  start 

extern  exit

import  exit msvcrt.dll
        
segment  data use32 class=data
	
segment  code use32 class=code
start: 
	mov  AX, 126
    mov BL, 1
    div BL
	
	push   dword 0
	call   [exit]