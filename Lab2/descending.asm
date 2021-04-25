	.data
a:
	2
	2
	6
	6
	6
	6
	6
	8
	9
	8
	9
	7
	5
n:
	13
	.text
main:
	subi %x0, 1, %x3
	addi %x0, 1, %x11
	load %x0, $n, %x8
	subi %x8, 1, %x8
loopi:
	beq %x3, %x8, endl
	addi %x3, 1, %x3
	add %x0, %x0, %x4
	jmp loopj
loopj:
	beq %x4, %x8, loopi
	addi %x4, 1, %x7
	load %x4, $a, %x9
	load %x7, $a, %x10
	slt %x9, %x10, %x6
	beq %x6, %x11, swap
	addi %x4, 1, %x4
	jmp loopj
swap:
	store %x10, $a, %x4
	store %x9, $a, %x7
	jmp loopj
endl:
	end
