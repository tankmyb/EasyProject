package com.socket.nio.second;

import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Utils for charset encode and decode.
 * <p>
 * Be careful when directly use Charset.encode/Charset.decode/CharsetEncoder.encode
 * /CharsetDecoder.decode. Sometimes it will amazing you!
 * <pre>
 * For example:
 * 		String s = "a";
 * 		Charset charset = Charset.forName("UTF-8");
 * 		String utf8 = charset.decode(charset.encode(s));
 * 		charset = Charset.forName("GBK");
 * 		String gbk = charset.decode(charset.encode(s));
 *    
 * Run such exmaple, utf8 is "a", but gbk is ""!!! 
 * </pre>
 * <p>  
 * That's because of CharsetDecode.decode use averageCharsPerByte to determine result's length.
 * String s's length is 1, UTF-8's averageCharsPerByte is 1.0, GBK's averageCharsPerByte
 * is 0.5. So int 1*1.0 == 1, but 1 * 0.5 == 0(cast to int)!!! So GBK returns nothing!  
 * <p>
 * I have post this bug on sun's bug database. See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6211132.
 * 
 * @author Roger Chen
 */

public class CharsetUtils {
	/**
   * System charset utils.
   */
  public static final CharsetUtils SYSTEM = new CharsetUtils(System
          .getProperty("file.encoding"));
  /**
   * UTF-8 charset utils.
   */
  public static final CharsetUtils UTF8 = new CharsetUtils("UTF-8");

  private Charset charset;
  //Charset is thread-safe, but CharsetEncoder and CharsetDecoder are not!
  private ThreadLocal encoderCache = new ThreadLocal();
  private ThreadLocal decoderCache = new ThreadLocal();

  private static Object getReference(ThreadLocal threadLocal) {
      SoftReference reference = (SoftReference) threadLocal.get();
      if (reference != null)
          return reference.get();
      return null;
  }

  private static void setReference(ThreadLocal threadLocal, Object object) {
      threadLocal.set(new SoftReference(object));
  }

  public CharsetUtils(String charsetName) {
      charset = Charset.forName(charsetName);
  }

  /**
   * Get charset name.
   * 
   * @return
   * 		charset name
   */
  public String getCharsetName() {
      return charset.name();
  }

  private CharsetEncoder getEncoder() {
      CharsetEncoder encoder = (CharsetEncoder) getReference(encoderCache);
      if (encoder == null) {
          encoder = charset.newEncoder().onMalformedInput(
                  CodingErrorAction.REPLACE).onUnmappableCharacter(
                  CodingErrorAction.REPLACE);
          setReference(encoderCache, encoder);
      }
      return encoder;
  }

  private CharsetDecoder getDecoder() {
      CharsetDecoder decoder = (CharsetDecoder) getReference(decoderCache);
      if (decoder == null) {
          decoder = charset.newDecoder().onMalformedInput(
                  CodingErrorAction.REPLACE).onUnmappableCharacter(
                  CodingErrorAction.REPLACE);
          setReference(decoderCache, decoder);
      }
      return decoder;
  }

  /**
   * Encode string to ByteBuffer.
   * 
   * @param s
   * 		the string
   * @return
   * 		the encoded ByteBuffer
   */
  public ByteBuffer encode(String s) {
      return encode(CharBuffer.wrap(s));
  }

  /**
   * Encode CharBuffer to ByteBuffer.
   * 
   * @param buffer
   * 		the CharBuffer
   * @return
   * 		the encoded ByteBuffer
   */
  public ByteBuffer encode(CharBuffer buffer) {
      CharsetEncoder encoder = getEncoder();

      int n = 0;
      if (buffer.remaining() > 0) {
          n = (int) (buffer.remaining() * encoder.averageBytesPerChar());
          if (n == 0)
              n = (int) (buffer.remaining() * encoder.maxBytesPerChar());
      }
      ByteBuffer result = ByteBuffer.allocate(n);
      if (n == 0)
          return result;

      encoder.reset();
      while (true) {
          CoderResult cr = buffer.hasRemaining() ? encoder.encode(buffer,
                  result, true) : encoder.flush(result);
          if (cr.isUnderflow())
              break;
          if (cr.isOverflow()) {
              n *= 2;
              result.flip();
              result = ByteBuffer.allocate(n).put(result);
              continue;
          }
      }
      result.flip();
      return result;
  }

  /**
   * Encode CharBuffer to ByteBuffer array. Every ByteBuffer's capacity 
   * will equals or less than bufferMaxLength, and will not contain 
   * half of char in CharBuffer.
   * 
   * @param buffer
   * 		the CharBuffer
   * @param bufferMaxLength
   * 		the return ByteBuffer's max capacity
   * @return
   * 		the encoded ByteBuffers 
   */
  public ByteBuffer[] encode(CharBuffer buffer, int bufferMaxLength) {
      CharsetEncoder encoder = getEncoder();
      encoder.reset();

      Collection buffers = new ArrayList();
      while (true) {
          ByteBuffer out = ByteBuffer.allocate(bufferMaxLength);
          CoderResult cr = encoder.encode(buffer, out, true);
          if (cr.isUnderflow()) {
              encoder.flush(out);
              out.flip();
              buffers.add(out);
              break;
          }
          if (cr.isOverflow()) {
              if (out.position() == 0) //can't encode this char, bufferMaxLength too small
                  break;
              out.flip();
              buffers.add(out);
              continue;
          }
      }
      return (ByteBuffer[]) buffers.toArray(new ByteBuffer[0]);
  }

  /**
   * Decode byte array to CharBuffer.
   * 
   * @param b
   * 		byte array
   * @return
   * 		the decoded CharBuffer
   */
  public CharBuffer decode(byte[] b) {
      return decode(ByteBuffer.wrap(b));
  }

  /**
   * Decode ByteBuffer to CharBuffer.
   * 
   * @param buffer
   * 		the ByteBuffer
   * @return
   * 		the decoded CharBuffer
   */
  public CharBuffer decode(ByteBuffer buffer) {
      CharsetDecoder decoder = getDecoder();

      int n = 0;
      if (buffer.remaining() > 0) {
          n = (int) (buffer.remaining() * decoder.averageCharsPerByte());
          if (n == 0)
              n = (int) (buffer.remaining() * decoder.maxCharsPerByte());
      }
      CharBuffer result = CharBuffer.allocate(n);
      if (n == 0)
          return result;

      decoder.reset();
      while (true) {
          CoderResult cr = buffer.hasRemaining() ? decoder.decode(buffer,
                  result, true) : decoder.flush(result);
          if (cr.isUnderflow())
              break;
          if (cr.isOverflow()) {
              n *= 2;
              result.flip();
              result = CharBuffer.allocate(n).put(result);
              continue;
          }
      }
      result.flip();
      return result;
  }

  private static ThreadLocal charsetCache = new ThreadLocal();

  private static CharsetUtils getCharsetUtils(String charsetName) {
      CharsetUtils charset = (CharsetUtils) getReference(charsetCache);
      if (charset == null || !charset.charset.name().equals(charsetName)) {
          charset = new CharsetUtils(charsetName);
          setReference(charsetCache, charset);
      }
      return charset;
  }

  /**
   * Encode CharBuffer to ByteBuffer.
   * 
   * @param charsetName
   * 		charset name
   * @param buffer
   * 		the CharBuffer
   * @return
   * 		the encoded ByteBuffer
   */
  public static ByteBuffer encode(String charsetName, CharBuffer buffer) {
      return getCharsetUtils(charsetName).encode(buffer);
  }

  /**
   * Encode String to ByteBuffer.
   * 
   * @param charsetName
   * 		charset name
   * @param s 
   *		the string
   * @return
   * 		the encoded ByteBuffer
   */
  public static ByteBuffer encode(String charsetName, String s) {
      return getCharsetUtils(charsetName).encode(s);
  }

  /**
   * Decode ByteBuffer to CharBuffer.
   * 
   * @param charsetName
   * 		charset name
   * @param buffer
   * 		the ByteBuffer
   * @return
   * 		the decoded CharBuffer
   */
  public static CharBuffer decode(String charsetName, ByteBuffer buffer) {
      return getCharsetUtils(charsetName).decode(buffer);
  }


}
