/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Georgios Migdos
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.gmigdos.io.streams;

public interface IByteStream {
	
	public static final int RET_CODE_OK = 0;
	public static final int RET_CODE_EOF = -1;
	public static final int RET_CODE_ERROR = -2;
	

	public int read();
	
	public int read(byte[] data, int count);
	
	public int read(byte[] data, int offset, int count);
	
	public int write(byte b);
	
	public int write(byte[] data);
	
	public int write(byte[] data, int len);
	
	public void closeWriterEndPoint();
	
	public void closeReaderEndPoint();
	
}
