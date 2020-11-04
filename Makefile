JC = javac
JR = java -jar

make: build

clean:
	rm -f *.jar

build:
	rm -rf bin
	mkdir bin
	$(JC) src/*/*.java -d bin
	jar cvfe l2-pa-project-hackandslash.jar xboard/Main -C bin xboard -C bin datasystem -C bin intelligence -C bin utilities
	rm -rf bin
	
run:
	$(JR) l2-pa-project-hackandslash.jar