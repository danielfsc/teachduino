package com.ardublock.translator.block.teach;

import com.ardublock.core.Context;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class InstallGYBlock extends TranslatorBlock
{
	public InstallGYBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    
	    String ret="setupGY(";
	    TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
	    String model=tb.toCode();
	    if (model.equals("GY-80")){
	        translator.addHeaderFile("Wire.h");
	        translator.addDefinitionCommand("int address[4]={0x1E, 0x53, 0x69, 0x77};\nint DataAddress[3][6]= {{0x03, 0x04, 0x07, 0x08, 0x05, 0x06} , {0x33, 0x32, 0x35, 0x34, 0x37, 0x36} , {0x29, 0x28, 0x2B, 0x2A, 0x2D, 0x2C} };\nint ac1, ac2, ac3,  b1, b2, mb, mc, md;\nunsigned int ac4, ac5, ac6;\nlong b5;\n");
	        
	        translator.addDefinitionCommand("long readValue(int dev, int axis){\n  if(dev==3){\n    if(axis == 0){\n      int ut;\n      Wire.beginTransmission(address[dev]);\n      Wire.write(0xF4);\n      Wire.write(0x2E);\n      Wire.endTransmission();\n      delay(8);\n      ut = ( readAddress(3,0xF6)<<8 | readAddress(3,0xF7) );  \n      long x1, x2;  \n      x1 = (((long)ut - (long)ac6)*(long)ac5) >> 15;  \n      x2 = ((long)mc << 11)/(x1 + md);\n      b5 = x1 + x2;\n      return ((b5 + 8)>>4);\n    }else if (axis == 1){\n      unsigned char OSS=0;\n      unsigned char msb, lsb, xlsb;\n      unsigned long up = 0;\n      Wire.beginTransmission(address[dev]);\n      Wire.write(0xF4);\n      Wire.write(0x34 + (OSS<<6));\n      Wire.endTransmission();\n      delay(8);\n      msb = readAddress(dev,0xF6);\n      lsb = readAddress(dev,0xF7);\n      xlsb = readAddress(dev,0xF8);\n      up = (((unsigned long) msb << 16) | ((unsigned long) lsb << 8) | (unsigned long) xlsb) >> (8-OSS);\n      long x1, x2, x3, b3, b6, p;\n      unsigned long b4, b7;\n      b6 = b5 - 4000;      x1 = (b2 * (b6 * b6)>>12)>>11;\n      x2 = (ac2 * b6)>>11;\n      x3 = x1 + x2;\n      b3 = (((((long)ac1)*4 + x3)<<OSS) + 2)>>2;\n      x1 = (ac3 * b6)>>13;\n      x2 = (b1 * ((b6 * b6)>>12))>>16;\n      x3 = ((x1 + x2) + 2)>>2;\n      b4 = (ac4 * (unsigned long)(x3 + 32768))>>15;\n      b7 = ((unsigned long)(up - b3) * (50000>>OSS));\n      if (b7 < 0x80000000)\n        p = (b7<<1)/b4;\n      else\n        p = (b7/b4)<<1;\n      x1 = (p>>8) * (p>>8);\n      x1 = (x1 * 3038)>>16;\n      x2 = (-7357 * p)>>16;\n      p += (x1 + x2 + 3791)>>4;\n      return p;\n    }\n  }else{\n    int res;\n    res=readAddress(dev, DataAddress[dev][axis*2]);\n    return ( res <<8 | readAddress(dev, DataAddress[dev][axis*2+1]) );\n  }\n}\n\nint readAddress(int dev, int addr){\n  Wire.beginTransmission(address[dev]);\n  Wire.write(addr);\n  Wire.endTransmission();\n  Wire.requestFrom(address[dev],1);\n  while(!Wire.available()){\n  }\n  return Wire.read();\n}\n\nvoid writeRegister(int deviceAddress, byte address, byte val) \n{\n    Wire.beginTransmission(deviceAddress); \n     Wire.write(address);\n    Wire.write(val);\n    Wire.endTransmission();\n}\nvoid setupGY(int dev, int scale){\n  if(dev==0){\n    writeRegister(address[dev],0x2D,8);\n  }else if(dev==1){\n    writeRegister(address[dev],0x2D,8);\n  }else if(dev==2){\n    writeRegister(address[dev], 0x20, 0b00001111);\n    writeRegister(address[dev], 0x21, 0b00000000);\n    writeRegister(address[dev], 0x22, 0b00001000);\n    if(scale <= 250){\n      writeRegister(address[dev], 0x23, 0b00000000);\n    }else if(scale>250 && scale <= 500){\n      writeRegister(address[dev], 0x23, 0b00010000);\n    }else{\n      writeRegister(address[dev], 0x23, 0b00110000);\n    }\n    writeRegister(address[dev], 0x24, 0b00000000);\n  } else if(dev ==3){\n     ac1=( readAddress(3,0xAA)<<8 | readAddress(3,0xAB) );\n     ac2=( readAddress(3,0xAC)<<8 | readAddress(3,0xAD) );\n     ac3=( readAddress(3,0xAE)<<8 | readAddress(3,0xAF) );\n     ac4=( readAddress(3,0xB0)<<8 | readAddress(3,0xB1) );\n     ac5=( readAddress(3,0xB2)<<8 | readAddress(3,0xB3) );\n     ac6=( readAddress(3,0xB4)<<8 | readAddress(3,0xB5) );\n     b1=( readAddress(3,0xB6)<<8 | readAddress(3,0xB7) );\n     b2=( readAddress(3,0xB8)<<8 | readAddress(3,0xB9) );\n     mb=( readAddress(3,0xBA)<<8 | readAddress(3,0xBB) );\n     mc=( readAddress(3,0xBC)<<8 | readAddress(3,0xBD) );\n     md=( readAddress(3,0xBE)<<8 | readAddress(3,0xBF) );\n     int a = readValue(3,0);\n  }\n}\n");
	        translator.addSetupCommand("Wire.begin();");
	        tb=this.getRequiredTranslatorBlockAtSocket(1);
	        ret = ret+ tb.toCode();
	        ret = ret+" , ";
	        tb=this.getRequiredTranslatorBlockAtSocket(2);
	        ret = ret + tb.toCode();
	        ret = ret+" ); ";
	        translator.addSetupCommand(ret);
	    }else if (model.equals("GY-85")){
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("GY_85.h");
		translator.addDefinitionCommand("GY_85 GY85;\ndouble readValue(int dev, int var){\n\tif(dev==1){\n  if (var==0){\n      return GY85.accelerometer_x( GY85.readFromAccelerometer() );\n    }else if (var==1){\n      return GY85.accelerometer_y( GY85.readFromAccelerometer() );\n    }else if (var==2){\n      return GY85.accelerometer_z( GY85.readFromAccelerometer() );\n    }\n  }else \n  if(dev==0){\n    if (var==0){\n      return GY85.compass_x( GY85.readFromCompass() );\n    }else if (var==1){\n      return GY85.compass_y( GY85.readFromCompass() );\n    }else if (var==2){\n      return GY85.compass_z( GY85.readFromCompass() );\n    }\n  }else if(dev==2){\n    if (var==0){\n      return GY85.gyro_x( GY85.readGyro() );\n    }else if (var==1){\n      return GY85.gyro_y( GY85.readGyro() );\n    }else if (var==2){\n      return GY85.gyro_z( GY85.readGyro() );\n    }\n  }else if(dev==3){\n    if (var==0){\n      return GY85.temp  ( GY85.readGyro() );\n    }else {\n      return 0;\n    }\n  }else{\n    return 0;\n  }\n}\n");
		translator.addSetupCommand("\tWire.begin();\n\tGY85.init();\n");

	    }else if (model.equals("HMC5883")){
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("Adafruit_Sensor.h");
		translator.addHeaderFile("Adafruit_HMC5883_U.h");
		translator.addDefinitionCommand("Adafruit_HMC5883_Unified mag = Adafruit_HMC5883_Unified(12345);");

		translator.addSetupCommand("mag.begin();");
		translator.addDefinitionCommand("double readValue(int dev, int var){\n  if(dev==0){\n    sensors_event_t event; \n    mag.getEvent(&event);\n    if(var==0){\n      return event.magnetic.x;\n    } else if(var==1){\n      return event.magnetic.y;\n    } else if(var==0){\n      return event.magnetic.z;\n    }else{\n      return 0;\n    }\n  }else{\n    return 0;\n  }\n}");

	    }else if (model.equals("GY-521")){
            translator.addHeaderFile("Wire.h");
            translator.addDefinitionCommand( " const int MPU=0x68;\nint AcX,AcY,AcZ,Tmp,GyX,GyY,GyZ;");
            translator.addSetupCommand("Wire.begin();\n  Wire.beginTransmission(MPU);\n  Wire.write(0x6B); \n  Wire.write(0); \n  Wire.endTransmission(true);");
            translator.addDefinitionCommand("int readValue(int dev, int axis){\n  Wire.beginTransmission(MPU);\n  Wire.write(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)\n  Wire.endTransmission(false);\n  //Solicita os dados do sensor\n  Wire.requestFrom(MPU,14,true);  \n  //Armazena o valor dos sensores nas variaveis correspondentes\n  AcX=Wire.read()<<8|Wire.read();  //0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)     \n  AcY=Wire.read()<<8|Wire.read();  //0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)\n  AcZ=Wire.read()<<8|Wire.read();  //0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)\n  Tmp=Wire.read()<<8|Wire.read();  //0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)\n  GyX=Wire.read()<<8|Wire.read();  //0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)\n  GyY=Wire.read()<<8|Wire.read();  //0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)\n  GyZ=Wire.read()<<8|Wire.read();  //0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)\n  if(dev==1){\n    if(axis==0){ return AcX;\n    }else if (axis==1){ return AcY;\n    }else if (axis==2){ return AcZ;}\n  }else if (dev==2){\n    if(axis==0){ return GyX;\n    }else if (axis==1){ return GyY;\n    }else if (axis==2){ return GyZ;}\n  }else if (dev==3){ \n    if (axis==0){ return Tmp;}\n  } else { return 0;}\n}");
        }
        

		return codePrefix + "" + codeSuffix;
	}

}
