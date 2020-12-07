cmake_minimum_required(VERSION 3.19)
project(greeter VERSION 1.0)

set(PUBLIC_INCLUDE_DIRECTORIES include/)
set(PRIVATE_INCLUDE_DIRECTORIES src/)

configure_file("${PUBLIC_INCLUDE_DIRECTORIES}/Greeter.h.in" Greeter.h)

file(GLOB_RECURSE SOURCE_FILES "src/*.c" "src/*.h*")
file(GLOB_RECURSE PUBLIC_INCLUDE_FILES "include/*.h")

add_library(${PROJECT_NAME} SHARED ${PUBLIC_INCLUDE_FILES} ${SOURCE_FILES})

target_include_directories(greeter PUBLIC "${PUBLIC_INCLUDE_DIRECTORIES}" "${PROJECT_BINARY_DIR}")
target_include_directories(greeter PRIVATE "${PRIVATE_INCLUDE_DIRECTORIES}")