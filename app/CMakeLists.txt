cmake_minimum_required(VERSION 3.4.1)

add_library( # Sets the name of the library.
             native

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native.cpp

        src/main/cpp/bzip2/blocksort.c
        src/main/cpp/bzip2/bzip2.c
        src/main/cpp/bzip2/bzip2recover.c
        src/main/cpp/bzip2/bzlib.c
        src/main/cpp/bzip2/compress.c
        src/main/cpp/bzip2/crctable.c
        src/main/cpp/bzip2/decompress.c
        src/main/cpp/bzip2/huffman.c
        src/main/cpp/bzip2/randtable.c
        src/main/cpp/bspatch.c
        src/main/cpp/bsdiff.c
        )

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
                       native

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )