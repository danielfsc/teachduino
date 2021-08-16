package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Thermistor10kBlock extends TranslatorBlock
{
	public Thermistor10kBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        
		translator.addHeaderFile("math.h");
         translator.addDefinitionCommand("double Thermistor(int port) {\n\tdouble Temp;\n\tint RawADC=analogRead(port);\n\tTemp = log(10000.0*((1024.0/RawADC-1)));\n\tTemp = 1 / (0.001129148 + (0.000234125 + (0.0000000876741 * Temp * Temp ))* Temp );\n\tTemp = Temp - 273.15;\n\t//Temp = (Temp * 9.0)/ 5.0 + 32.0; // Convert Celcius to Fahrenheit\n\treturn Temp;\n}");

		String ret = "(Thermistor(";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() ;
		ret += "))";
	    return codePrefix + ret + codeSuffix;
    }
}
