# Compiler settings
CXX      := g++
CXXFLAGS := -Wall -Wextra -std=c++17 -Iinclude
LIB_NAME := libG3DL.a

# Directories
SRC_DIR  := src
OBJ_DIR  := obj

# 1. Recursively find every .cpp file in src/ and its subfolders
SRCS     := $(shell find $(SRC_DIR) -name "*.cpp")

# 2. Map those .cpp files to .o files in the obj/ directory
OBJS     := $(SRCS:$(SRC_DIR)/%.cpp=$(OBJ_DIR)/%.o)

# Default target
all: $(LIB_NAME)

# Link all object files into the static library (.a)
$(LIB_NAME): $(OBJS)
	ar rcs $@ $^
	@echo "Static library $(LIB_NAME) built successfully."

# Compile each .cpp file into an .o file
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.cpp
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Remove build files
clean:
	rm -rf $(OBJ_DIR) $(LIB_NAME)

.PHONY: all clean
