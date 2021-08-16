package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Ds18b20Block extends TranslatorBlock
{
	public Ds18b20Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("OneWire.h");
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "OneWire ds(";

		ret += tb.toCode()+");\nfloat getTemp(){\n  byte data[12];\n  byte addr[8];\n  if ( !ds.search(addr)) {\n      ds.reset_search();\n      return -1000;\n  }\n  if ( OneWire::crc8( addr, 7) != addr[7]) {\n      Serial.println(\"CRC is not valid!\");\n      return -1000;\n  }\n  if ( addr[0] != 0x10 && addr[0] != 0x28) {\n      Serial.print(\"Device is not recognized\");\n      return -1000;\n  }\n  ds.reset();\n  ds.select(addr);\n  ds.write(0x44,1);\n  delay(100);\n  byte present = ds.reset();\n  ds.select(addr);    \n  ds.write(0xBE);\n  for (int i = 0; i < 9; i++) {\n    data[i] = ds.read();\n  }\n  ds.reset_search();\n  byte MSB = data[1];\n  byte LSB = data[0];\n  float tempRead = ((MSB << 8) | LSB);\n  float TemperatureSum = tempRead / 16;\n  return TemperatureSum;\n}";
		translator.addDefinitionCommand(ret);
		

	    return codePrefix  +"getTemp()"+ codeSuffix;
    }
}
