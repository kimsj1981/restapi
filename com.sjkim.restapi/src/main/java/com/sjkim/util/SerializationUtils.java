package com.sjkim.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.castor.core.util.Base64Decoder;
import org.castor.core.util.Base64Encoder;

public class SerializationUtils {
	
	public static String toBase64String(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return new String(Base64Encoder.encode(baos.toByteArray()));
	}

	public static Object fromBase64String(String s) throws IOException,	ClassNotFoundException {
		byte[] data = Base64Decoder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}
}
