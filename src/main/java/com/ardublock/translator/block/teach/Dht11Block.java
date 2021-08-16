package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Dht11Block extends TranslatorBlock
{
	public Dht11Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("DHT.h");
		translator.addSetupCommand("dht.begin();");
		String ret = "DHT dht( ";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() + " , DHT11);";
		translator.addDefinitionCommand(ret);
		tb = this.getRequiredTranslatorBlockAtSocket(1);

	    return codePrefix  +tb.toCode()+ codeSuffix;
    }
}
