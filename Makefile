#CSC2002S Assignment3 Makefile
#Elle Mouton
#08/09/2018

JAVAC=/usr/bin/javac
JAVA = java

default: Tree.class SunThread.class SunCalc.class

Tree.class: Tree.java
	$(JAVAC) Tree.java

SunThread.class: SunThread.java
	$(JAVAC) SunThread.java

SunCalc.class: SunCalc.java
	$(JAVAC) SunCalc.java	


clean:
	rm  *.class

	
run:
	$(JAVA) SunCalc ${ARGS}