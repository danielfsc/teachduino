package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Lm35Block extends TranslatorBlock
{
	public Lm35Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        


		String ret = "(analogRead(";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() ;
		ret += ")*(5.0/1023.)*100.)";
	    return codePrefix + ret + codeSuffix;
    }
}
