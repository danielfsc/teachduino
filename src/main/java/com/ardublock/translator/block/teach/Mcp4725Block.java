package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Mcp4725Block extends TranslatorBlock
{
	public Mcp4725Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("Adafruit_MCP4725.h");
		translator.addDefinitionCommand("Adafruit_MCP4725 dac;");
		


		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
		translator.addSetupCommand("dac.begin(0x"+tb.toCode()+");");
		tb = this.getRequiredTranslatorBlockAtSocket(0);
		
	
		String ret = "dac.setVoltage("+tb.toCode()+",false);\n";
		

	    return codePrefix  +ret+ codeSuffix;
    }
}
