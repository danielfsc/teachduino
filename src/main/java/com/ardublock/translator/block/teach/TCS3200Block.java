package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TCS3200Block extends TranslatorBlock
{
	public TCS3200Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    String ret="int s0= ";
	    TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
	    ret = ret + tb.toCode();
	    ret = ret + ";\nint s1= ";
	    tb = this.getRequiredTranslatorBlockAtSocket(1);
	    ret = ret + tb.toCode();
	    ret = ret + ";\nint s2=  ";
	    tb = this.getRequiredTranslatorBlockAtSocket(2);
	    ret = ret + tb.toCode();
        ret = ret + ";\nint s3=  ";
        tb = this.getRequiredTranslatorBlockAtSocket(3);
	    ret = ret + tb.toCode();
        ret = ret + ";\nint out=  ";
        tb = this.getRequiredTranslatorBlockAtSocket(4);
	    ret = ret + tb.toCode();
        ret = ret + ";\n int red = 0;\nint green = 0;\nint blue = 0;";
	    translator.addDefinitionCommand(ret);
	    translator.addSetupCommand("pinMode(s0, OUTPUT);\n  pinMode(s1, OUTPUT);\n  pinMode(s2, OUTPUT);\n  pinMode(s3, OUTPUT);\n  pinMode(out, INPUT);\n  digitalWrite(s0, HIGH);\n  digitalWrite(s1, LOW);");
        ret="void color()\n{\n  digitalWrite(s2, LOW);\n  digitalWrite(s3, LOW);\n  red = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);\n  digitalWrite(s3, HIGH);\n  blue = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);\n  digitalWrite(s2, HIGH);\n  green = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);\n}\nint readColor(int cor){\n  color();\n  if (cor==0){\n    return red;\n  }else if (cor==1){\n    return green;\n  }else if(cor==2){\n    return blue;\n  }\n  return 0;\n}";
        translator.addDefinitionCommand(ret);
		return codePrefix + "" + codeSuffix;
	}

}
