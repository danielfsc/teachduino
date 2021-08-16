package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CountTimeBlock extends TranslatorBlock
{
	public CountTimeBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        translator.addDefinitionCommand("long count(int port, int count, boolean stat,  int timeout=120000){\n  unsigned long t;\n  int c=0;\n  boolean s=stat; \n  while(c!=2*count+1){\n    if(digitalRead(port)==s){\n      if(c==0) t=millis();\n      s=s^1;\n      c+=1;\n    }\n  }\n  return millis()-t;\n} \n");
		String ret = "count( ";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        translator.addSetupCommand("pinMode("+tb.toCode()+",INPUT);");
		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret += tb.toCode() + " ) ";
	    return codePrefix + ret + codeSuffix;
    }
}
