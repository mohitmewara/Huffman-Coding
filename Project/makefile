JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = BinaryHeap.java BinaryNode.java decoder.java encoder.java FourCacheHeap.java HeapPerformanceCheck.java InputReader.java PairingHeap.java PairingNode.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
