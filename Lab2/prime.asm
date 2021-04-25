	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	addi %x0, 1, %x4
	beq %x3, %x0, failure
	beq %x3, %x4, failure
	addi %x4, 1, %x4
	beq %x3, %x4, success
	divi %x3, 2, %x5
	addi %x5, 1, %x5
loop:
	beq %x4, %x5, success
	div %x3, %x4, %x6
	beq %x31, %x0, failure
	addi %x4, 1, %x4
	jmp loop
success:
	addi %x0, 1, %x10
	end
failure:
	subi %x0, 1, %x10
	end
