package com.ardublock.translator.block.teach;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TeachUltrasonicBlock extends TranslatorBlock
{
	public TeachUltrasonicBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String func="int ardublockUltrasonicPing(int trigPin, int echoPin)\n{\n  int duration;\n  pinMode(trigPin, OUTPUT);\n  pinMode(echoPin, INPUT);\n  digitalWrite(trigPin, LOW);\n  delayMicroseconds(2);\n  digitalWrite(trigPin, HIGH);\n  delayMicroseconds(20);\n  digitalWrite(trigPin, LOW);\n  duration = pulseIn(echoPin, HIGH);\n  if ((duration < 2) || (duration > 50000)) return false;\n  return duration;\n}\nfloat ardublockUltrasonicMesure(int trigPin, int echoPin, int mesure)\n{\n  if (mesure==0){\n    int duration=ardublockUltrasonicPing(trigPin, echoPin);\n    return (1.0*duration)/5.70;\n  }else if(mesure==1){\n    float s1=(1.0*ardublockUltrasonicPing(trigPin, echoPin))/5.7;\n    int t1=millis();\n    delay(50);\n    float s2=(1.0*ardublockUltrasonicPing(trigPin, echoPin))/5.7;\n    int t2=millis();\n    return (s2-s1)/(1.0*(t2-t1));    \n  }else if(mesure==2){\n    float s1=(1.0*ardublockUltrasonicPing(trigPin, echoPin))/5.7;\n    int t1=millis();\n    delay(50);\n    float s2=(1.0*ardublockUltrasonicPing(trigPin, echoPin))/5.7;\n    int t2=millis();\n    delay(50);\n    float s3=(1.0*ardublockUltrasonicPing(trigPin, echoPin))/5.7;\n    int t3=millis();\n    return (1.0*(s3-2.0*s2+s1))/((t3-t2)*(t2-t1));   \n  }else {\n    return false;\n  }\n}\n";
		translator.addDefinitionCommand(func);
		String ret = "ardublockUltrasonicMesure( ";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret += tb.toCode() + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret += tb.toCode() + " ) ";

	    return codePrefix + ret + codeSuffix;
    }
}
