package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TCSBlock extends TranslatorBlock
{
	public TCSBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    String ret="MD_TCS230  CS(";
	    TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
	    ret = ret + tb.toCode();
	    ret = ret + " , ";
	    tb = this.getRequiredTranslatorBlockAtSocket(1);
	    ret = ret + tb.toCode();
	    ret = ret + " , ";
	    tb = this.getRequiredTranslatorBlockAtSocket(2);
	    ret = ret + tb.toCode();
	    ret = ret + " );\n colorData  rgb;\nint readSensor(int i){\n\tstatic  bool  waiting;\n\tif (!waiting){\n\t\tCS.read();\n\t\twaiting = true;\n\t}else{\n\t\tif (CS.available()){\n\t\tCS.getRGB(&rgb);\n\t\twaiting = false;\n\t\t}\n\t}\n\treturn rgb.value[i];\n}";
	    translator.addDefinitionCommand(ret);
	    translator.addSetupCommand("CS.begin();");
	    translator.addHeaderFile("MD_TCS230.h");
	    translator.addHeaderFile("FreqCount.h");
		return codePrefix + "" + codeSuffix;
	}

}
