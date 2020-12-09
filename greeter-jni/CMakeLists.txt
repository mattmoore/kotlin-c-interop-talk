cmake_minimum_required(VERSION 3.19)
project(greeter-jni VERSION 1.0)

configure_file(
  include/greeter_jni.h.in
  greeter_jni.h
)

add_library(${PROJECT_NAME} SHARED
  include/greeter_jni.h
  src/greeter_jni.c
)

target_include_directories(${PROJECT_NAME} PUBLIC
  include
)
target_include_directories(${PROJECT_NAME} PRIVATE
  src
)
target_include_directories(${PROJECT_NAME} PUBLIC
  $ENV{JAVA_HOME}/include
  $ENV{JAVA_HOME}/include/darwin
)

target_link_libraries(${PROJECT_NAME}
  -L../../greeter/build
  -lgreeter
)
