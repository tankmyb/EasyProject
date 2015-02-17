package com.protobuf;
public class GenerateClass_Windows {
	/**
	 * Windows版本
	 * 调用protoc.exe生成java数据访问类
	 * */
	public static void main(String[] args) throws Exception {
		//EasyNT/NTNetty
		//NTProtocol
		//String strCmd = "protoc.exe --java_out=./src ./proto/" + protoFile;
		//String javaOut="E:/tank/luna/EasyNT/NTProtocol/src/main/java";
		String javaOut="E:/tank/luna/EasyNT/NTNetty/src/main/java";
		
		String protoPath="E:/tank/luna/EasyNT/NTNetty/proto/";
		String protoFile=protoPath+"message.proto";
		String strCmd = "protoc.exe --java_out="+javaOut+" --proto_path="+protoPath+" " + protoFile;
		System.out.println(strCmd);
		Runtime.getRuntime().exec("cmd /c " + strCmd).waitFor();//通过执行cmd命令调用protoc.exe程序
		System.out.println("==============");
	}
}