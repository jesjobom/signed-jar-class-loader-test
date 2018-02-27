# Class Loader resources access from signed jar

Description of the whole problem: https://stackoverflow.com/questions/48834376/spring-boot-doesnt-read-components-after-jar-signing

## Test 1 - Accessing resources from a unsigned jar
```
$ mvn clean package
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
$ jarsigner -verify target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar

jar is unsigned.

$ java -jar target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar 
	Test for listing and accessing resource files inside this jar.

the class loader that will be used is sun.misc.Launcher$AppClassLoader@42a57993

trying to obtain the resource for package 'com.jesjobom'...
jar:file:/home/jairton/Projetos/signed-jar-class-loader-test/target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar!/com/jesjobom

trying now to directly obtain the main class 'com.jesjobom.Main.class'
jar:file:/home/jairton/Projetos/signed-jar-class-loader-test/target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar!/com/jesjobom/Main.class
```
Success! Both .class file and package folder are being accessed.


## Test 2 - Accessing resources from a self-signed jar
```
$ mvn clean package -DSELFSIGN
...
[INFO] --- maven-jarsigner-plugin:1.4:sign (default) @ signed-jar-class-loader-test ---
[INFO] 2 archive(s) processed
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
$ jarsigner -verify target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar

jar verified.

Warning: 
This jar contains entries whose certificate chain is not validated.
This jar contains signatures that does not include a timestamp. Without a timestamp, users may not be able to validate this jar after the signer certificate's expiration date (2019-02-27) or after any future revocation date.

Re-run with the -verbose and -certs options for more details.

$ java -jar target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar 
	Test for listing and accessing resource files inside this jar.

the class loader that will be used is sun.misc.Launcher$AppClassLoader@28d93b30

trying to obtain the resource for package 'com.jesjobom'...
jar:file:/home/jairton/Projetos/signed-jar-class-loader-test/target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar!/com/jesjobom

trying now to directly obtain the main class 'com.jesjobom.Main.class'
jar:file:/home/jairton/Projetos/signed-jar-class-loader-test/target/signed-jar-class-loader-test-1.0-SNAPSHOT-jar-with-dependencies.jar!/com/jesjobom/Main.class
```
Success again! Same thing as previous test.

## Test 3 - Accessing resources from a signed jar (by a valid certificate)
```
$ jarsigner -verify signed-jar-class-loader-test_signed.jar 

jar verified.

Warning: 
This jar contains signatures that does not include a timestamp. Without a timestamp, users may not be able to validate this jar after the signer certificate's expiration date (2019-07-21) or after any future revocation date.

Re-run with the -verbose and -certs options for more details.

$ java -jar signed-jar-class-loader-test_signed.jar 
	Test for listing and accessing resource files inside this jar.

the class loader that will be used is sun.misc.Launcher$AppClassLoader@4554617c

trying to obtain the resource for package 'com.jesjobom'...
empty return

trying now to directly obtain the main class 'com.jesjobom.Main.class'
jar:file:/home/jairton/Projetos/signed-jar-class-loader-test/signed-jar-class-loader-test_signed.jar!/com/jesjobom/Main.class
```
FAILURE! Unable to get the resource that refers to the package/folder "com/jesjobom".
