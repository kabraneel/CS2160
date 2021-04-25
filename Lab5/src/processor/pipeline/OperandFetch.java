package processor.pipeline;
import processor.Processor;
import generic.Instruction;
import generic.Operand;
import generic.Simulator;


public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	public void performOF()
	{

		Instruction newinsc = new Instruction();

		Operand operand1 = new Operand();
		Operand operand2 = new Operand();
		Operand operand3 = new Operand();

		if(IF_OF_Latch.isOF_enable())
		{

			int instruction = IF_OF_Latch.getInstruction();
			int temp = instruction;

			System.out.println("OF temp(opcode) val below");
			temp = (temp>>27);
			if(temp<0){
				temp ^= -32;
			}

			System.out.println(temp);
			int optype = -1;

			if(temp<=21){
				if(temp%2==0){
					optype = 1;
				}
				else{
					optype = 2;
				}
			}

			else if(temp == 22){
				optype = 2;
			}
			else if(temp == 23){
				optype  = 5;
			}

			else if(temp == 24 || temp == 29){
				optype = 3;
			}

			else{
				optype = 4;
			}

			int rs1=-1,rs2=-1,rd=-1,imm=-1;

			switch(optype){
				case 1://R3 format
							// operand1.setOperandType(OperandType.values()[0]);
							operand1.setValue(containingProcessor.getRegisterFile().getValue((instruction>>22) & 31));

							// operand1.setOperandType(OperandType.values()[0]);
							operand2.setValue(containingProcessor.getRegisterFile().getValue((instruction>>17) & 31));

							// operand1.setOperandType(OperandType.values()[0]);
							operand3.setValue((instruction>>12) & 31);

							rs1 = ((instruction>>22) & 31);
							rs2 = ((instruction>>17) & 31);
							rd = ((instruction>>12) & 31);

							newinsc.setSourceOperand1(operand1);
							newinsc.setSourceOperand2(operand2);
							newinsc.setDestinationOperand(operand3);
							newinsc.setOperationType(Instruction.OperationType.values()[temp]);
							OF_EX_Latch.set_Instruction(newinsc);
							System.out.println("OF INS DETAILS op1 op2 op3");
							System.out.println((instruction>>22) & 31);
							System.out.println((instruction>>17) & 31);
							System.out.println((instruction>>12) & 31);
							OF_EX_Latch.setrs1(rs1);
							OF_EX_Latch.setrs2(rs2);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.setimm(imm);
							break;

				case 2://R2I format
							// Operand operand1;
							// operand1.setOperandType(OperandType.values()[0]);
							operand1.setValue(containingProcessor.getRegisterFile().getValue((instruction>>22) & 31));


							//  10101  11011 000000000000000000
							// 00000  11111
							// Operand operand2;
							// operand2.setOperandType(OperandType.values()[0]);
							// instruction = instruction>>5;
							operand2.setValue((instruction>>17) & 31);

							// Operand operand3;
							// operand3.setOperandType(OperandType.values()[1]);
							operand3.setValue((instruction) & ((1<<17)-1));

							rs1 = ((instruction>>22) & 31);
							rd = ((instruction>>17) & 31);
							// rd = ((instruction>>12) & 31);
							imm = ((instruction) & ((1<<17)-1));

							System.out.println("OF INS DETAILS op1 op2 imm");
							System.out.println((instruction>>22) & 31);
							System.out.println((instruction>>17) & 31);
							System.out.println((instruction) & ((1<<17)-1));

							newinsc.setSourceOperand1(operand1);
							newinsc.setSourceOperand2(operand3);
							newinsc.setDestinationOperand(operand2);



							newinsc.setOperationType(Instruction.OperationType.values()[temp]);
							OF_EX_Latch.set_Instruction(newinsc);
							OF_EX_Latch.setrs1(rs1);
							OF_EX_Latch.setrs2(rs2);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.setimm(imm);
							break;

				case 4://bgt, beq, blt, bne
							rs1 = ((instruction>>22) & 31);
							rd = ((instruction>>17) & 31);
							// rd = ((instruction>>12) & 31);
							imm = ((instruction) & ((1<<17)-1));

							// Operand operand1;
							// operand1.setOperandType(OperandType.values()[0]);
							operand1.setValue(containingProcessor.getRegisterFile().getValue((instruction>>22) & 31));

							// Operand operand2;
							// operand2.setOperandType(OperandType.values()[0]);
							operand2.setValue(containingProcessor.getRegisterFile().getValue((instruction>>17) & 31));

							// Operand operand3;
							// operand3.setOperandType(OperandType.values()[1]);
							operand3.setValue((instruction) & ((1<<17)-1));

							newinsc.setSourceOperand1(operand1);
							newinsc.setSourceOperand2(operand2);
							newinsc.setDestinationOperand(operand3);
							newinsc.setOperationType(Instruction.OperationType.values()[temp]);
							OF_EX_Latch.set_Instruction(newinsc);
							OF_EX_Latch.setrs1(rs1);
							OF_EX_Latch.setrs2(rs2);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.setimm(imm);
							break;

				case 3://RI format
							rd = ((instruction>>22) & 31);
							// rd = ((instruction>>17) & 31);
							// rd = ((instruction>>12) & 31);
							imm = ((instruction) & ((1<<22)-1));

							// Operand operand1;
							// operand1.setOperandType(OperandType.values()[0]);
							operand1.setValue((instruction>>22) & 31);

							// Operand operand2;
							// operand1.setOperandType(OperandType.values()[1]);
							operand2.setValue((instruction)&((1<<17)-1));
							operand3.setValue(0);

							newinsc.setSourceOperand1(operand1);
							newinsc.setSourceOperand2(operand2);
							newinsc.setDestinationOperand(operand3);
							newinsc.setOperationType(Instruction.OperationType.values()[temp]);
							OF_EX_Latch.set_Instruction(newinsc);
							OF_EX_Latch.setrs1(rs1);
							OF_EX_Latch.setrs2(rs2);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.setimm(imm);
							break;

				case 5:// store
							// here it is very clear as just need all values no index

							rs1 = ((instruction>>22) & 31);
							rd = ((instruction>>17) & 31);
							// rd = ((instruction>>12) & 31);
							imm = ((instruction) & ((1<<17)-1));

							// Operand operand1;
							// operand1.setOperandType(OperandType.values()[0]);
							operand1.setValue(containingProcessor.getRegisterFile().getValue((instruction>>22) & 31));

							// Operand operand2;
							// operand1.setOperandType(OperandType.values()[1]);
							operand2.setValue(containingProcessor.getRegisterFile().getValue((instruction>>17) & 31));

							operand3.setValue((instruction)&((1<<17)-1));

							newinsc.setSourceOperand1(operand2);
							newinsc.setSourceOperand2(operand3);
							newinsc.setDestinationOperand(operand1);
							newinsc.setOperationType(Instruction.OperationType.values()[temp]);
							OF_EX_Latch.set_Instruction(newinsc);
							OF_EX_Latch.setrs1(rs1);
							OF_EX_Latch.setrs2(rs2);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.setimm(imm);
							break;

				default:
							break;

			}

			//if conflict
			boolean conflict = false;
			// int i1 = Execute.i.getDestinationOperand();
			if (optype == 1){
				// EX
				int ex_rd = containingProcessor.getMAUnit().EX_MA_Latch.getrd();
				if (rs1 == ex_rd || rs2 == ex_rd){
					conflict = true;
				}
				int ma_rd = containingProcessor.getRWUnit().MA_RW_Latch.getrd();
				if (rs1 == ma_rd || rs2 == ma_rd){
					conflict = true;
				}
			}

			if (optype == 2){
				// EX
				int ex_rd = containingProcessor.getMAUnit().EX_MA_Latch.getrd();
				if (rs1 == ex_rd){
					conflict = true;
				}
				int ma_rd = containingProcessor.getRWUnit().MA_RW_Latch.getrd();
				if (rs1 == ma_rd){
					conflict = true;
				}
				System.out.println("IM here");
				System.out.println(ex_rd);
				System.out.println(ma_rd);
			}

			if (optype == 5 || optype == 4){
				// EX
				int ex_rd = containingProcessor.getMAUnit().EX_MA_Latch.getrd();
				if (rs1 == ex_rd || rd == ex_rd){
					conflict = true;
				}
				int ma_rd = containingProcessor.getRWUnit().MA_RW_Latch.getrd();
				if (rs1 == ma_rd || rd == ma_rd){
					conflict = true;
				}
			}

			if(conflict){
				System.out.println("THERE IS A CONFLICT");
				containingProcessor.getIFUnit().conflict = true;
				OF_EX_Latch.set_Instruction(null);
				OF_EX_Latch.setrs1(-1);
				OF_EX_Latch.setrs2(-1);
				OF_EX_Latch.setrd(-1);
				OF_EX_Latch.setimm(-1);
				Simulator.datahaz +=1;

			}

			else{
				containingProcessor.getIFUnit().conflict = false;
			}



			// if (optype == 4){
			// 	// EX
			// 	int ex_rd = ;
			// 	if (rs1 == ex_rd || rd == ex_rd){
			// 		conflict = true;
			// 	}
			// 	int ma_rd = ;
			// 	if (rs1 == ma_rd || rd == ma_rd){
			// 		conflict = true;
			// 	}
			// }

			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
			// containingProcessor.getIFUnit().IF_EnableLatch.IF_enable = true;

		}
	}

}
