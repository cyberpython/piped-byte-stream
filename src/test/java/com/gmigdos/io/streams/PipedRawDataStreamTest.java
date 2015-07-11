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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.gmigdos.io.streams.IByteStream;
import com.gmigdos.io.streams.PipedByteStreamFactory;

import static org.junit.Assert.*;

public class PipedRawDataStreamTest {
	
	private static final String MESSAGE = "Hello world!";
	private static final int TOTAL_MSG_COUNT = 100;
	private static final int WRITE_DELAY_MILLIS = 10;
	private static final int READ_DELAY_MILLIS = 100;

	@Test
	public void test() {
		final IByteStream s = new PipedByteStreamFactory().createStream(65535*100);
		final AtomicInteger writtenBytes = new AtomicInteger(0);
		final AtomicInteger readBytes = new AtomicInteger(0);
		ExecutorService execSvc = Executors.newFixedThreadPool(2);
		execSvc.execute(new Runnable() {
			
			@Override
			public void run() {
				byte[] data = MESSAGE.getBytes();
				int i = 1;
				while(i <= TOTAL_MSG_COUNT){
					s.write((byte)data.length);
					s.write(data);
					writtenBytes.addAndGet(data.length);
					i++;
					try{
						Thread.sleep(WRITE_DELAY_MILLIS);
					}catch(InterruptedException ie){
					}
				}	
				s.closeWriterEndPoint();
			}
		});
		
		
		execSvc.execute(new Runnable() {
			
			@Override
			public void run() {
				byte[] data = new byte[255];
				int len = 0;
				while(true){
					len = s.read();
					if(len > 0){
						if (s.read(data, len) == IByteStream.RET_CODE_OK){
							readBytes.addAndGet(len);
							try{
								Thread.sleep(READ_DELAY_MILLIS);
							}catch(InterruptedException ie){
							}
						}else{
							break;
						}
					}else if(len < 0){
						break;
					}
				}
				s.closeReaderEndPoint();
			}
		});
		
		execSvc.shutdown();
		try{
			execSvc.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		}catch(InterruptedException ie){
			
		}
		
		assertEquals(writtenBytes.get(), readBytes.get());
		
	}

}
