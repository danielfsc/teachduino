package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PulseBlock extends TranslatorBlock
{
	public PulseBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        translator.addDefinitionCommand(" long pulse (int port, boolean stat, int timeout=120000){\n  unsigned long t=millis();\n  while(digitalRead(port)!=stat){\n    if(millis()-t>= timeout){\n      return 0;\n    }\n  }\n  t=millis();\n  while(digitalRead(port)==stat){}\n  return millis()-t;\n}\n      ");
		String ret = "pulse( ";
		
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
