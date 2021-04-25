	.data
a:
	81518
	.text
main:
	load %x0, $a, %x3
	addi %x0, 10, %x4
	addi %x0, 2, %x5
	addi %x0, 1, %x12
countloop:
	div %x3, %x4, %x6
	beq %x6, %x0, conti
	muli %x4, 10, %x4
	addi %x5, 1, %x5
	jmp countloop
conti:
	divi %x4, 10, %x4
	subi %x5, 1, %x5
secloop:
	beq %x5, %x0, success
	beq %x5, %x12, success
	div %x3, %x4, %x6
	add %x0, %x31, %x3
	divi %x3, 10, %x7
	add %x0, %x31, %x7
	divi %x3, 10, %x3
	subi %x5, 2, %x5
	divi %x4, 100, %x4
	bne %x6, %x7, failure
	jmp secloop
success:
	addi %x0, 1, %x10
	end
failure:
	subi %x0, 1, %x10
	end
