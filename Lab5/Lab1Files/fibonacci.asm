	.data
n:
	10
	.text
main:
	addi %x0, 65535, %x3
	addi %x0, 1, %x5
	addi %x0, 1, %x8
	load %x0, $n, %x9
	store %x0, $n, %x3
	beq %x8, %9, endl
	subi %x3, 1, %x3
	store %x5, $n, %x3
	addi %x8, 1, %x8
	beq %x8, %9, endl
loop:
	addi %x3, 1, %x4
	load %x3, $n, %x5
	load %x4, $n, %x6
	add %x5, %x6, %x7
	subi %x3, 1, %x3
	store %x7, $n, %x3
	addi %x8, 1, %x8
	beq %x8, %x9, endl
	jmp loop
endl:
	end
