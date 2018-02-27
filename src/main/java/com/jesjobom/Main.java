package com.jesjobom;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 *
 * @author jesjobom
 */
public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("\tTest for listing and accessing resource files inside this jar.");

		System.out.println("\nthe class loader that will be used is " + Main.class.getClassLoader());

		System.out.println("\ntrying to obtain the resource for package 'com.jesjobom'...");
		printResources("com/jesjobom");

		System.out.println("\ntrying now to directly obtain the main class 'com.jesjobom.Main.class'");
		printResources("com/jesjobom/Main.class");
	}
	
	private static void printResources(String path) throws IOException {
		
		Enumeration<URL> resources = Main.class.getClassLoader().getResources(path);
		if (resources.hasMoreElements()) {
			while (resources.hasMoreElements()) {
				URL nextElement = resources.nextElement();
				System.out.println(nextElement);
			}

		} else {
			System.out.println("empty return");
		}
	}
}
