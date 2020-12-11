cmake_minimum_required(VERSION 3.19)
project(greeter VERSION 1.0)

configure_file(
  include/greeter.h.in
  include/greeter.h
)

add_library(${PROJECT_NAME} SHARED
  include/greeter.h
  src/greeter.c
)

add_executable(hello
  src/main.c
)

target_include_directories(${PROJECT_NAME} PUBLIC include)
target_include_directories(${PROJECT_NAME} PRIVATE src)
target_include_directories(hello PUBLIC include)

target_link_libraries(hello ${PROJECT_NAME})
