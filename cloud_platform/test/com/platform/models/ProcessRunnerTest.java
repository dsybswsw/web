package com.platform.models;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class ProcessRunnerTest {
	public static void main(String[] args) throws IOException {
		String command = "ls /";
		Process child = Runtime.getRuntime().exec(command);
		InputStream stream = child.getInputStream();

		int c;
		while ( (c = stream.read()) != -1) {
			System.out.write(c);
		}
	}
}
