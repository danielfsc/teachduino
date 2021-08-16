package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Clean_DSPBlock extends TranslatorBlock
{
	public Clean_DSPBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String colors[] = {"ST7735_BLACK", "ST7735_WHITE", "ST7735_GREEN", "ST7735_BLUE", "ST7735_RED", "ST7735_YELLOW", "ST7735_MAGENTA","ST7735_CYAN"};
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String code = tb.toCode();
	    	int idx = Integer.parseInt(code);
		if(idx>=8) idx=0;
		return codePrefix + "tft.fillScreen("+colors[idx]+");" + codeSuffix;
	}

}
