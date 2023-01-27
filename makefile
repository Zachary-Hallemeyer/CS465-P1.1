JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
    $(JC) $(JFLAGS) $*.java
CLASSES = \
    NodeInfo.java \
		Server.java

MAIN = Server

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
    $(JVM) $(MAIN)

clean:
    $(RM) *.class
