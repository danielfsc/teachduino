package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Print_DSPBlock extends TranslatorBlock
{
	public Print_DSPBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String colors[] = {"ST7735_BLACK", "ST7735_WHITE", "ST7735_GREEN", "ST7735_BLUE", "ST7735_RED", "ST7735_YELLOW", "ST7735_MAGENTA","ST7735_CYAN"};
	        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
		String lineNo = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String charNo = tb.toCode();
		String ret = "";
		if ( !(charNo.equals("0") || lineNo.equals("0")) ){
			ret = "tft.setCursor( (" + charNo + ") - 1, (" + lineNo + ") - 1 );\n";
		}
		tb = this.getRequiredTranslatorBlockAtSocket(3);
		String code = tb.toCode();
	    	int idx = Integer.parseInt(code);
		if(idx>=8) idx=0;
		ret +="tft.setTextColor("+colors[idx]+");\ntft.setTextWrap(true);\n";
		tb = this.getRequiredTranslatorBlockAtSocket(0, "tft.print( ", " );\n");
		ret += tb.toCode();





	   	return ret;
	}

}
