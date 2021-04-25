package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import generic.Operand.OperandType;
import java.io.*;


public class Simulator {

	static FileInputStream inputcodeStream = null;
	static String f;

	public static void setupSimulation(String assemblyProgramFile, String objectProgramFile)
	{
		f = objectProgramFile;
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static void assemble(){
		try{

			FileOutputStream outputcode = new FileOutputStream(f);
			DataOutputStream datafile = new DataOutputStream(outputcode);

			//Header
			datafile.writeInt(ParsedProgram.mainFunctionAddress);

			//Data
			for (int i = 0; i<ParsedProgram.data.size(); i++){
				datafile.writeInt(ParsedProgram.data.get(i));
			}

			for (int i = 0; i<ParsedProgram.code.size(); i++){
				// System.out.println("i : ");
				// System.out.println(i);
				// System.out.println("\nProg cnt : ");
				// System.out.println(ParsedProgram.code.get(i).programCounter);
				// System.out.println("\nemun idx : ");
				// int index = Arrays.asList(Instruction.OperationType.values()).indexOf(Instruction.OperationType.ParsedProgram.code.get(i).getOperationType());
				// System.out.println(index);
				// System.out.println(Arrays.asList(code));
				// System.out.println(Instruction.OperationType.valueOf(ParsedProgram.code.get(i).getOperationType()).ordinal());
				Instruction.OperationType s = ParsedProgram.code.get(i).getOperationType();
	            int index = Instruction.OperationType.valueOf(String.valueOf(s)).ordinal();
	            // System.out.println(index);
				// System.out.println("\nsatyam");
				int val = index << 27;
				switch(ParsedProgram.code.get(i).getOperationType()){
						// ParsedProgram.code.get(i)
						//R3I type
						case add :
						case sub :
						case mul :
						case div :
						case and :
						case or :
						case xor :
						case slt :
						case sll :
						case srl :
						case sra :	{
										// newInstruction.setSourceOperand1(getRegisterOperandFromString(sc.next()));
										// newInstruction.setSourceOperand2(getRegisterOperandFromString(sc.next()));
										// newInstruction.setDestinationOperand(getRegisterOperandFromString(sc.next()));
										// System.out.println("\n");
										// System.out.println(ParsedProgram.code.get(i).getSourceOperand1().getValue());
										// System.out.println("\n");
										// System.out.println(ParsedProgram.code.get(i).getSourceOperand2().getValue());
										// System.out.println("\n");
										// System.out.println(ParsedProgram.code.get(i).getDestinationOperand().getValue());
										// System.out.println("\n");
										index = ParsedProgram.code.get(i).getSourceOperand1().getValue() << 22;
										val += index;
										index = ParsedProgram.code.get(i).getSourceOperand2().getValue() << 17;
										val += index;
										index = ParsedProgram.code.get(i).getDestinationOperand().getValue() << 12;
										val += index;
										datafile.writeInt(val);
										break;
									}

						//R2I type
						case addi :
						case subi :
						case muli :
						case divi :
						case andi :
						case ori :
						case xori :
						case slti :
						case slli :
						case srli :
						case srai :
						case load :
						case store :	{
											// newInstruction.setSourceOperand1(getRegisterOperandFromString(sc.next()));
											// String str = sc.next();
											// if(Pattern.matches("-?\\d+(,)",str))
											// {
											// 	//absolute immediate
											// 	newInstruction.setSourceOperand2(getImmediateOperandFromString(str));
											// }
											// else
											// {
											// 	//label / symbol
											// 	newInstruction.setSourceOperand2(getLabelOperandFromString(str));
											// }
											// newInstruction.setDestinationOperand(getRegisterOperandFromString(sc.next()));
											index = ParsedProgram.code.get(i).getSourceOperand1().getValue() << 22;
											val += index;
											index = ParsedProgram.code.get(i).getSourceOperand2().getValue();
											val += index;
											index = ParsedProgram.code.get(i).getDestinationOperand().getValue() << 17;
											val += index;
											datafile.writeInt(val);
											break;
										}

						case beq :
						case bne :
						case blt :
						case bgt : 	{
										// newInstruction.setSourceOperand1(getRegisterOperandFromString(sc.next()));
										// newInstruction.setSourceOperand2(getRegisterOperandFromString(sc.next()));
										// String str = sc.next();
										// if(Pattern.matches("[0-9]+(,)",str))
										// {
										// 	//absolute immediate
										// 	newInstruction.setDestinationOperand(getImmediateOperandFromString(str));
										// }
										// else
										// {
										// 	//label / symbol
										// 	newInstruction.setDestinationOperand(getLabelOperandFromString(str));
										// }
										index = ParsedProgram.code.get(i).getSourceOperand1().getValue() << 22;
										val += index;
										index = ParsedProgram.code.get(i).getSourceOperand2().getValue() << 17;
										val += index;
										// index = ParsedProgram.code.get(i).getDestinationOperand().getValue();
										index = ParsedProgram.symtab.get(ParsedProgram.code.get(i).getDestinationOperand().getLabelValue());
										val += index;
										datafile.writeInt(val);
										break;
									}

						//RI type :
						case jmp :		{
											// String str = sc.next();
											// if(Pattern.matches("[0-9]+(,)",str))
											// {
											// 	//absolute immediate
											// 	newInstruction.setDestinationOperand(getImmediateOperandFromString(str));
											// }
											// else if(Pattern.matches("%x([0-9]{1,2})",str)) {
											// 	newInstruction.setDestinationOperand(getRegisterOperandFromString(str));
											// }
											// else
											// {
											// 	//label / symbol
											// 	newInstruction.setDestinationOperand(getLabelOperandFromString(str));
											// }
											index = ParsedProgram.symtab.get(ParsedProgram.code.get(i).getDestinationOperand().getLabelValue());
											val += index;
											datafile.writeInt(val);
											break;
										}

						case end :		{
											datafile.writeInt(val);
											break;
										}

						default: Misc.printErrorAndExit("unknown instruction!!");
					}
					// System.out.println(Arrays.asList(code));
				}
		}
		catch(Exception e){System.out.println(e);}
	}

}
