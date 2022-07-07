.data
a:
	0
	.text
main:
	load %x0, $a, %x6 1
	andi %x6, 1, %x7 2
	addi %x0, 1, %x8 3
	beq %x7, %x8, success 4
	jmp endl 5
success:
	addi %x0, 1, %x10 6
	end 7
endl:
	subi %x0, 1, %x10 8
	end 9
	
	
0		: 0
1		: -1341390848
2		: 1234042881
3		: 135266305
4		: -909115390
5		: -1073741821
6		: 135528449
7		: -402653184
8		: 403963905
9		: -402653184
10		: 0

