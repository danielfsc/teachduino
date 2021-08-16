package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ST7735Block extends TranslatorBlock
{
	public ST7735Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    translator.addHeaderFile("Adafruit_GFX.h");
	    translator.addHeaderFile("Adafruit_ST7735.h");
            translator.addHeaderFile("SPI.h");
	    String ret="#define TFT_CS  ";
	    TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
	    ret = ret + tb.toCode();
	    ret = ret + "\n#define TFT_RST  ";
	    tb = this.getRequiredTranslatorBlockAtSocket(1);
	    ret = ret + tb.toCode();
	    ret = ret + "\n#define TFT_DC   ";
	    tb = this.getRequiredTranslatorBlockAtSocket(2);
	    ret = ret + tb.toCode();
	    ret = ret + "\nAdafruit_ST7735 tft = Adafruit_ST7735(TFT_CS,  TFT_DC, TFT_RST);\n";
	    ret = ret + "#define TFT_SCLK  ";
	    tb = this.getRequiredTranslatorBlockAtSocket(3);
	    ret = ret + tb.toCode();
	    ret = ret + "\n#define TFT_MOSI  ";
	    tb = this.getRequiredTranslatorBlockAtSocket(4);
	    ret = ret + tb.toCode();
            ret = ret + "\n//Adafruit_ST7735 tft = Adafruit_ST7735(TFT_CS, TFT_DC, TFT_MOSI, TFT_SCLK, TFT_RST);"; 	    
	    translator.addDefinitionCommand(ret);
	    translator.addSetupCommand("tft.initR(INITR_144GREENTAB);\ntft.fillScreen(ST7735_BLACK);");
	   	return codePrefix + "" + codeSuffix;
	}

}
