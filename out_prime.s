.text
.globl main
    j main
divisible:
    addi $sp, $sp, -32
    sw $ra, 0($sp)
    sw $fp, 4($sp)
    move $fp, $sp
    move $t0, $a0
    sw $t0, 8($sp)
    move $t0, $a1
    sw $t0, 12($sp)
    li $t0, 0
    sw $t0, 16($sp)
    lw $t0, 8($sp)
    lw $t1, 12($sp)
    div $t2, $t0, $t1
    sw $t2, 16($sp)
    lw $t0, 16($sp)
    lw $t1, 12($sp)
    mul $t2, $t0, $t1
    sw $t2, 16($sp)
    lw $t0, 8($sp)
    lw $t1, 16($sp)
    bne $t0, $t1, divisible_label0
    li $t0, 1
    sw $t0, 20($sp)
    lw $t0, 20($sp)
    move $v0, $t0
    j divisible_func_end
divisible_label0:
    li $t0, 0
    sw $t0, 24($sp)
    lw $t0, 24($sp)
    move $v0, $t0
    j divisible_func_end
divisible_func_end:
    lw $ra, 0($sp)
    lw $fp, 4($sp)
    addi $sp, $sp, 32
    jr $ra
main:
    addi $sp, $sp, -104
    sw $ra, 0($sp)
    sw $fp, 4($sp)
    move $fp, $sp
    li $t0, 0
    sw $t0, 8($sp)
    li $t0, 0
    sw $t0, 12($sp)
    li $t0, 0
    sw $t0, 16($sp)
    li $t0, 0
    sw $t0, 20($sp)
    li $t0, 0
    sw $t0, 24($sp)
    li $t0, 0
    sw $t0, 28($sp)
    li $t0, 0
    sw $t0, 32($sp)
    li $t0, 0
    sw $t0, 36($sp)
    li $t0, 0
    sw $t0, 40($sp)
    li $t0, 0
    sw $t0, 44($sp)
    li $t0, 0
    sw $t0, 48($sp)
    li $t0, 0
    sw $t0, 52($sp)
    li $t0, 0
    sw $t0, 56($sp)
    li $t0, 0
    sw $t0, 60($sp)
    li $t0, 0
    sw $t0, 24($sp)
    li $t0, 2
    sw $t0, 12($sp)
    li $t0, 3
    sw $t0, 16($sp)
    li $t0, 6
    sw $t0, 20($sp)
    li $t0, 0
    sw $t0, 56($sp)
    li $v0, 5
    syscall
    move $t0, $v0
    sw $t0, 28($sp)
    li $t0, 1
    sw $t0, 64($sp)
    lw $t0, 28($sp)
    lw $t1, 64($sp)
    bgt $t0, $t1, main_label0
    li $t0, 0
    sw $t0, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    j main_print
main_label0:
    li $t0, 3
    sw $t0, 68($sp)
    lw $t0, 28($sp)
    lw $t1, 68($sp)
    bgt $t0, $t1, main_label1
    li $t0, 1
    sw $t0, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    j main_print
main_label1:
    lw $t0, 28($sp)
    move $a0, $t0
    lw $t0, 12($sp)
    move $a1, $t0
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    jal divisible
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    move $t0, $v0
    sw $t0, 40($sp)
    lw $t0, 56($sp)
    move $t1, $t0
    sw $t1, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    li $t0, 1
    sw $t0, 72($sp)
    lw $t0, 40($sp)
    lw $t1, 72($sp)
    beq $t0, $t1, main_label2
    lw $t0, 28($sp)
    move $a0, $t0
    lw $t0, 16($sp)
    move $a1, $t0
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    jal divisible
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    move $t0, $v0
    sw $t0, 40($sp)
    lw $t0, 56($sp)
    move $t1, $t0
    sw $t1, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    li $t0, 1
    sw $t0, 76($sp)
    lw $t0, 40($sp)
    lw $t1, 76($sp)
    beq $t0, $t1, main_label2
    j main_label3
main_label2:
    j main_print
main_label3:
    li $t0, 5
    sw $t0, 24($sp)
main_loop:
    lw $t0, 24($sp)
    lw $t1, 24($sp)
    mul $t2, $t0, $t1
    sw $t2, 36($sp)
    lw $t0, 36($sp)
    lw $t1, 28($sp)
    bgt $t0, $t1, main_exit
    lw $t0, 28($sp)
    move $a0, $t0
    lw $t0, 24($sp)
    move $a1, $t0
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    jal divisible
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    move $t0, $v0
    sw $t0, 40($sp)
    lw $t0, 56($sp)
    move $t1, $t0
    sw $t1, 32($sp)
    li $t0, 0
    sw $t0, 48($sp)
    li $t0, 0
    sw $t0, 60($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    li $t0, 1
    sw $t0, 80($sp)
    lw $t0, 40($sp)
    lw $t1, 80($sp)
    beq $t0, $t1, main_label2
    li $t0, 2
    sw $t0, 84($sp)
    lw $t0, 24($sp)
    lw $t1, 84($sp)
    add $t2, $t0, $t1
    sw $t2, 44($sp)
    lw $t0, 28($sp)
    move $a0, $t0
    lw $t0, 44($sp)
    move $a1, $t0
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    jal divisible
    lw $t0, 8($sp)
    sw $t0, 8($sp)
    lw $t0, 12($sp)
    sw $t0, 12($sp)
    lw $t0, 16($sp)
    sw $t0, 16($sp)
    lw $t0, 20($sp)
    sw $t0, 20($sp)
    lw $t0, 24($sp)
    sw $t0, 24($sp)
    lw $t0, 28($sp)
    sw $t0, 28($sp)
    lw $t0, 32($sp)
    sw $t0, 32($sp)
    lw $t0, 36($sp)
    sw $t0, 36($sp)
    lw $t0, 40($sp)
    sw $t0, 40($sp)
    lw $t0, 44($sp)
    sw $t0, 44($sp)
    lw $t0, 48($sp)
    sw $t0, 48($sp)
    lw $t0, 52($sp)
    sw $t0, 52($sp)
    lw $t0, 56($sp)
    sw $t0, 56($sp)
    lw $t0, 60($sp)
    sw $t0, 60($sp)
    move $t0, $v0
    sw $t0, 40($sp)
    lw $t0, 56($sp)
    move $t1, $t0
    sw $t1, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
    li $t0, 1
    sw $t0, 88($sp)
    lw $t0, 40($sp)
    lw $t1, 88($sp)
    beq $t0, $t1, main_label2
    li $t0, 6
    sw $t0, 92($sp)
    lw $t0, 24($sp)
    lw $t1, 92($sp)
    add $t2, $t0, $t1
    sw $t2, 24($sp)
    j main_loop
main_exit:
    lw $t0, 48($sp)
    move $t1, $t0
    sw $t1, 52($sp)
    lw $t0, 60($sp)
    move $t1, $t0
    sw $t1, 32($sp)
    li $t0, 1
    sw $t0, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 8($sp)
main_print:
    lw $t0, 8($sp)
    move $a0, $t0
    li $v0, 1
    syscall
    li $t0, 10
    sw $t0, 96($sp)
    lw $t0, 96($sp)
    move $a0, $t0
    li $v0, 11
    syscall
main_func_end:
    lw $ra, 0($sp)
    lw $fp, 4($sp)
    addi $sp, $sp, 104
    li $v0, 10
    syscall
