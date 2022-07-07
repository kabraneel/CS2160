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
	subi %x0, 1, %x3 14
	addi %x0, 1, %x11 15
	load %x0, $n, %x8 16
	subi %x8, 1, %x8 17
loopi:
	beq %x3, %x8, endl 18
	addi %x3, 1, %x3 19
	add %x0, %x0, %x4 20
	jmp loopj 21
loopj:
	beq %x4, %x8, loopi 22
	addi %x4, 1, %x7 23 
	load %x4, $a, %x9 24
	load %x7, $a, %x10 25
	slt %x9, %x10, %x6 26
	beq %x6, %x11, swap 27
	addi %x4, 1, %x4 28
	jmp loopj 29
swap:
	store %x10, $a, %x4 30
	store %x9, $a, %x7 31
	jmp loopj 32
endl:
	end 33
