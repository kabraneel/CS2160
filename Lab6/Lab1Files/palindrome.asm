	.data
a:
	81518 0
	.text
main:
	load %x0, $a, %x3 1
	addi %x0, 10, %x4 2
	addi %x0, 2, %x5 3
	addi %x0, 1, %x12 4
countloop:
	div %x3, %x4, %x6 5
	beq %x6, %x0, conti 6 
	muli %x4, 10, %x4 7
	addi %x5, 1, %x5 8
	jmp countloop 9
conti:
	divi %x4, 10, %x4 10
	subi %x5, 1, %x5 11
secloop:
	beq %x5, %x0, success 12
	beq %x5, %x12, success 13
	div %x3, %x4, %x6 14 
	add %x0, %x31, %x3 15
	divi %x3, 10, %x7 16
	add %x0, %x31, %x7 17
	divi %x3, 10, %x3 18 
	subi %x5, 2, %x5 19
	divi %x4, 100, %x4 20
	bne %x6, %x7, failure 21
	jmp secloop 22
success:
	addi %x0, 1, %x10 23
	end 24
failure:
	subi %x0, 1, %x10 25
	end 26
