.data
a:
	0
	.text
main:
	load %x0, $a, %x6
	andi %x6, 1, %x7
	addi %x0, 1, %x8
	beq %x7, %x8, success
	jmp endl
success:
	addi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end
