package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Bmp085Block extends TranslatorBlock
{
	public Bmp085Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        translator.addHeaderFile("Wire.h");
        translator.addHeaderFile("Adafruit_BMP085.h");
        translator.addDefinitionCommand("Adafruit_BMP085 bmp;");
        translator.addSetupCommand("if (!bmp.begin()) {\n\tSerial.println(\"Sensor BMP085 not found, check connections!\");\n\t}");


		String ret = "";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() ;
		
	    return codePrefix + ret + codeSuffix;
    }
}
