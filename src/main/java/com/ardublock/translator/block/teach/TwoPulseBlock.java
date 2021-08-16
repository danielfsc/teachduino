package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TwoPulseBlock extends TranslatorBlock
{
	public TwoPulseBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        translator.addDefinitionCommand("long timer[2];\nvoid timers(int portA, int portB , boolean stat){\n  long tA[2], tB[2];  int cA=0, cB=0;\n  boolean sA=stat,sB=stat;\n  while(cA!=2 && cB!=2){\n    if(cA<2 && digitalRead(portA)==sA){\n      tA[cA]=millis();\n      sA=sA^1;  \n      cA+=1;      \n    }\n    if(cB<2 && digitalRead(portB)==sB){\n      tB[cB]=millis();\n      sB=sB^1;  \n      cB+=1;      \n    }\n  }\n  timer[0]=tA[1]-tA[0];\n  timer[1]=tB[1]-tB[0];\n}\n");
		String ret = "two_pulse( ";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		translator.addSetupCommand("pinMode("+tb.toCode()+",INPUT);");
		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		translator.addSetupCommand("pinMode("+tb.toCode()+",INPUT);");
		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret += tb.toCode() + " ); \n";
	    return codePrefix + ret + codeSuffix;
    }
}
