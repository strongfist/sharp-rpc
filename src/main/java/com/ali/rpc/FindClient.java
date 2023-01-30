package com.ali.rpc;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindClient {


    protected List<Rpc> findClientByPackage(String[] packages) {
        // FIXME: 2023/1/30 one package may have more clients
        return Arrays.asList(packages).stream().map(this::buildClient).collect(Collectors.toList());
    }


    // FIXME: 2023/1/30 one package may have more clients
    private Rpc buildClient(String packageName) {

        return null;

    }

    public List<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .toList();
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    // TODO: 2023/1/30 use this 
    public Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                // only the annotated classes are present
                .filter(cla -> cla.isAnnotationPresent(com.ali.rpc.annotation.Rpc.class))
                .collect(Collectors.toSet());
    }
}
