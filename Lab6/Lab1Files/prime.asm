	.data
a:
	5
	.text
main:
	load %x0, $a, %x3 1
	addi %x0, 1, %x4 2
	beq %x3, %x0, failure 3
	beq %x3, %x4, failure 4
	addi %x4, 1, %x4 5
	beq %x3, %x4, success 6
	divi %x3, 2, %x5 7
	addi %x5, 1, %x5 8
loop:
	beq %x4, %x5, success 9
	div %x3, %x4, %x6 10
	beq %x31, %x0, failure 11
	addi %x4, 1, %x4 12
	jmp loop 13
success:
	addi %x0, 1, %x10 14
	end 14
failure:
	subi %x0, 1, %x10 15
	end 16
