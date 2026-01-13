# Compiler Stuff
Compiler=g++
C++Version=17
CompilerFLAGS=-Wall -Wextra -std=c++$(C++Version) -Iinclude -g -lncurses

LIB_NAME=libg3dl.a

SRC=$(wildcard src/*.cpp src/*/*.cpp src/*/*/*.cpp)
OBJ=$(SRC:.cpp=.o)
OutputDIR=lib

OBJ=$(SRC:.cpp=.o)

all: $(LIB_NAME)

# Compile .cpp to .o
%.o: %.cpp
	$(Compiler) $(CompilerFLAGS) -c $< -o $@

# Archive .o files into .a
$(LIB_NAME): $(OBJ)
	mkdir -p $(OutputDIR)
	ar rcs $(OutputDIR)/$@ $^

clean:
	rm -f $(OBJ) $(OutputDIR)/$(LIB_NAME)
