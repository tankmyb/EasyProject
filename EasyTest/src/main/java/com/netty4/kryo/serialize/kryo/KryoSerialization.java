package com.netty4.kryo.serialize.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.netty4.kryo.serialize.Serialization;

public final class KryoSerialization implements Serialization {
	
	private final KyroFactory kyroFactory;
	
	public KryoSerialization(final KyroFactory kyroFactory) {
		this.kyroFactory = kyroFactory;
	}
	
	@Override
	public void serialize(final OutputStream out, final Object message) throws IOException {
		Kryo kryo = kyroFactory.getKryo();
		//Kryo kryo = new Kryo();
		//System.out.println(kryo.hashCode());
		Output output = new Output(out);
		kryo.writeClassAndObject(output, message);
		output.close();
		kyroFactory.returnKryo(kryo);
	}
	
	@Override
	public Object deserialize(final InputStream in) throws IOException {
		Kryo kryo = kyroFactory.getKryo();
		//Kryo kryo = new Kryo();
		Input input = new Input(in);
		Object result = kryo.readClassAndObject(input);
		input.close();
		kyroFactory.returnKryo(kryo);
		return result;
	}
}
