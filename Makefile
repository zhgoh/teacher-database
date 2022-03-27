ifdef OS
	RM = del /Q
	FixPath = $(subst /,\,$1)
	CLS = cls
else
   ifeq ($(shell uname), Linux)
		RM = rm -f
		FixPath = $1
		CLS = clear
   endif
endif

all: app
	$(CLS)
	java -cp bin guide.cli.Program

app: src/guide/cli/Program.java
	javac ./src/guide/cli/Program.java -d bin

jar: bin/guide/cli/*.class app
	cd bin && jar cfm teacher-database.jar manifest.txt ./guide/cli/*.class

clean:
	$(RM) $(call FixPath, bin/guide/cli/*.class)
	$(RM) $(call FixPath, bin/*.jar)
