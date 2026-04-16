.text
.globl main
    j main
quicksort:
    addi $sp, $sp, -120
    sw $ra, 0($sp)
    sw $fp, 4($sp)
    move $fp, $sp
    move $t0, $a0
    sw $t0, 8($sp)
    move $t0, $a1
    sw $t0, 12($sp)
    move $t0, $a2
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
    sw $t0, 44($sp)
    li $t0, 0
    sw $t0, 48($sp)
    lw $t0, 12($sp)
    lw $t1, 16($sp)
    bge $t0, $t1, quicksort_end
    lw $t0, 12($sp)
    lw $t1, 16($sp)
    add $t2, $t0, $t1
    sw $t2, 36($sp)
    li $t0, 2
    sw $t0, 52($sp)
    lw $t0, 36($sp)
    lw $t1, 52($sp)
    div $t2, $t0, $t1
    sw $t2, 36($sp)
    lw $t0, 36($sp)
    sll $t1, $t0, 2
    sw $t1, 56($sp)
    lw $t0, 8($sp)
    lw $t1, 56($sp)
    add $t2, $t0, $t1
    sw $t2, 60($sp)
    lw $t0, 60($sp)
    lw $t1, 0($t0)
    sw $t1, 40($sp)
    li $t0, 1
    sw $t0, 64($sp)
    lw $t0, 12($sp)
    lw $t1, 64($sp)
    sub $t2, $t0, $t1
    sw $t2, 44($sp)
    li $t0, 1
    sw $t0, 68($sp)
    lw $t0, 16($sp)
    lw $t1, 68($sp)
    add $t2, $t0, $t1
    sw $t2, 48($sp)
quicksort_loop0:
quicksort_loop1:
    li $t0, 1
    sw $t0, 72($sp)
    lw $t0, 44($sp)
    lw $t1, 72($sp)
    add $t2, $t0, $t1
    sw $t2, 44($sp)
    lw $t0, 44($sp)
    sll $t1, $t0, 2
    sw $t1, 76($sp)
    lw $t0, 8($sp)
    lw $t1, 76($sp)
    add $t2, $t0, $t1
    sw $t2, 80($sp)
    lw $t0, 80($sp)
    lw $t1, 0($t0)
    sw $t1, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 20($sp)
    lw $t0, 20($sp)
    lw $t1, 40($sp)
    blt $t0, $t1, quicksort_loop1
quicksort_loop2:
    li $t0, 1
    sw $t0, 84($sp)
    lw $t0, 48($sp)
    lw $t1, 84($sp)
    sub $t2, $t0, $t1
    sw $t2, 48($sp)
    lw $t0, 48($sp)
    sll $t1, $t0, 2
    sw $t1, 88($sp)
    lw $t0, 8($sp)
    lw $t1, 88($sp)
    add $t2, $t0, $t1
    sw $t2, 92($sp)
    lw $t0, 92($sp)
    lw $t1, 0($t0)
    sw $t1, 32($sp)
    lw $t0, 32($sp)
    move $t1, $t0
    sw $t1, 24($sp)
    lw $t0, 24($sp)
    lw $t1, 40($sp)
    bgt $t0, $t1, quicksort_loop2
    lw $t0, 44($sp)
    lw $t1, 48($sp)
    bge $t0, $t1, quicksort_exit0
    lw $t0, 48($sp)
    sll $t1, $t0, 2
    sw $t1, 96($sp)
    lw $t0, 8($sp)
    lw $t1, 96($sp)
    add $t2, $t0, $t1
    sw $t2, 100($sp)
    lw $t0, 20($sp)
    lw $t1, 100($sp)
    sw $t0, 0($t1)
    lw $t0, 44($sp)
    sll $t1, $t0, 2
    sw $t1, 104($sp)
    lw $t0, 8($sp)
    lw $t1, 104($sp)
    add $t2, $t0, $t1
    sw $t2, 108($sp)
    lw $t0, 24($sp)
    lw $t1, 108($sp)
    sw $t0, 0($t1)
    j quicksort_loop0
quicksort_exit0:
    li $t0, 1
    sw $t0, 112($sp)
    lw $t0, 48($sp)
    lw $t1, 112($sp)
    add $t2, $t0, $t1
    sw $t2, 28($sp)
    lw $t0, 8($sp)
    move $a0, $t0
    lw $t0, 12($sp)
    move $a1, $t0
    lw $t0, 48($sp)
    move $a2, $t0
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
    jal quicksort
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
    li $t0, 1
    sw $t0, 116($sp)
    lw $t0, 48($sp)
    lw $t1, 116($sp)
    add $t2, $t0, $t1
    sw $t2, 48($sp)
    lw $t0, 8($sp)
    move $a0, $t0
    lw $t0, 48($sp)
    move $a1, $t0
    lw $t0, 16($sp)
    move $a2, $t0
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
    jal quicksort
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
quicksort_end:
quicksort_func_end:
    lw $ra, 0($sp)
    lw $fp, 4($sp)
    addi $sp, $sp, 120
    jr $ra
main:
    addi $sp, $sp, -464
    sw $ra, 0($sp)
    sw $fp, 4($sp)
    move $fp, $sp
    addi $t0, $sp, 8
    sw $t0, 408($sp)
    li $t0, 0
    sw $t0, 412($sp)
    li $t0, 0
    sw $t0, 416($sp)
    li $t0, 0
    sw $t0, 420($sp)
    li $t0, 0
    sw $t0, 412($sp)
    li $v0, 5
    syscall
    move $t0, $v0
    sw $t0, 420($sp)
    li $t0, 100
    sw $t0, 424($sp)
    lw $t0, 420($sp)
    lw $t1, 424($sp)
    bgt $t0, $t1, main_return
    li $t0, 1
    sw $t0, 428($sp)
    lw $t0, 420($sp)
    lw $t1, 428($sp)
    sub $t2, $t0, $t1
    sw $t2, 420($sp)
    li $t0, 0
    sw $t0, 416($sp)
main_loop0:
    lw $t0, 416($sp)
    lw $t1, 420($sp)
    bgt $t0, $t1, main_exit0
    li $v0, 5
    syscall
    move $t0, $v0
    sw $t0, 412($sp)
    lw $t0, 416($sp)
    sll $t1, $t0, 2
    sw $t1, 432($sp)
    lw $t0, 408($sp)
    lw $t1, 432($sp)
    add $t2, $t0, $t1
    sw $t2, 436($sp)
    lw $t0, 412($sp)
    lw $t1, 436($sp)
    sw $t0, 0($t1)
    li $t0, 1
    sw $t0, 440($sp)
    lw $t0, 416($sp)
    lw $t1, 440($sp)
    add $t2, $t0, $t1
    sw $t2, 416($sp)
    j main_loop0
main_exit0:
    lw $t0, 408($sp)
    move $a0, $t0
    li $t0, 0
    sw $t0, 444($sp)
    lw $t0, 444($sp)
    move $a1, $t0
    lw $t0, 420($sp)
    move $a2, $t0
    lw $t0, 408($sp)
    sw $t0, 408($sp)
    lw $t0, 412($sp)
    sw $t0, 412($sp)
    lw $t0, 416($sp)
    sw $t0, 416($sp)
    lw $t0, 420($sp)
    sw $t0, 420($sp)
    jal quicksort
    lw $t0, 408($sp)
    sw $t0, 408($sp)
    lw $t0, 412($sp)
    sw $t0, 412($sp)
    lw $t0, 416($sp)
    sw $t0, 416($sp)
    lw $t0, 420($sp)
    sw $t0, 420($sp)
    li $t0, 0
    sw $t0, 416($sp)
main_loop1:
    lw $t0, 416($sp)
    lw $t1, 420($sp)
    bgt $t0, $t1, main_exit1
    lw $t0, 416($sp)
    sll $t1, $t0, 2
    sw $t1, 448($sp)
    lw $t0, 408($sp)
    lw $t1, 448($sp)
    add $t2, $t0, $t1
    sw $t2, 452($sp)
    lw $t0, 452($sp)
    lw $t1, 0($t0)
    sw $t1, 412($sp)
    lw $t0, 412($sp)
    move $a0, $t0
    li $v0, 1
    syscall
    li $t0, 10
    sw $t0, 456($sp)
    lw $t0, 456($sp)
    move $a0, $t0
    li $v0, 11
    syscall
    li $t0, 1
    sw $t0, 460($sp)
    lw $t0, 416($sp)
    lw $t1, 460($sp)
    add $t2, $t0, $t1
    sw $t2, 416($sp)
    j main_loop1
main_exit1:
main_return:
main_func_end:
    lw $ra, 0($sp)
    lw $fp, 4($sp)
    addi $sp, $sp, 464
    li $v0, 10
    syscall
