package com.buyi.huxq17;

import java.io.File;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompilerUtil {
    private static final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

    public static boolean CompilerJavaFile(File sourceFile, String classFileOutputPath) {
        Iterable<String> options = Arrays.asList("-d", classFileOutputPath);
        StandardJavaFileManager fileManager = javaCompiler .getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));

        return javaCompiler.getTask(null, fileManager, null, options,
                null, compilationUnits).call();
    }
}