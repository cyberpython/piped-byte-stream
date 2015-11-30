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

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;

public class PipedByteStream implements IByteStream {
	
	private PipedInputStream inputStream;
	private PipedOutputStream outputStream;
	
	public PipedByteStream(int bufferSize) throws IOException {
		this.inputStream = new PipedInputStream(bufferSize);
		this.outputStream = new PipedOutputStream(inputStream);
	}
	
	@Override
	public int read() {
		try{
			return inputStream.read();
		}catch(IOException ioe){
			return RET_CODE_ERROR;
		}
	}
	
	@Override
	public int read(byte[] data, int count) {
		return read(data, 0, count);
	}
	
	@Override
	public int read(byte[] data, int offset, int count) {
		int totalRead = 0;
		int bytesRead = -1;
		try {
			while(totalRead < count){
				bytesRead = inputStream.read(data, offset+totalRead, count - totalRead);
				if(bytesRead == -1){
					return RET_CODE_EOF;
				}
				totalRead += bytesRead;
			}
		}catch(IOException ioe){
			return RET_CODE_ERROR;
		}
		return RET_CODE_OK;
	}
	
	@Override
	public int write(byte b) {
		try{
			outputStream.write(b);
			return RET_CODE_OK;
		}catch(IOException ioe){
			return RET_CODE_ERROR;
		}
	}
	
	@Override
	public int write(byte[] data) {
		return write(data, data.length);
	}
	
	@Override
	public int write(byte[] data, int len) {
		try{
			outputStream.write(data, 0, len);
			return RET_CODE_OK;
		}catch(IOException ioe){
			return RET_CODE_ERROR;
		}
	}
	
	@Override
	public void closeWriterEndPoint() {
		try{
			outputStream.flush();
			outputStream.close();
		}catch(IOException ioe){
			// TODO: handle exception
		}
	}
	
	@Override
	public void closeReaderEndPoint() {
		try{
			outputStream.close();
		}catch(IOException ioe){
			// TODO: handle exception
		}
	}
}
