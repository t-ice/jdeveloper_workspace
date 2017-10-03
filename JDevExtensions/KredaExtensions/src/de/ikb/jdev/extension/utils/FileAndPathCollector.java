package de.ikb.jdev.extension.utils;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileAndPathCollector extends SimpleFileVisitor<Path> {

    ArrayList<Path> dirs = new ArrayList<Path>();
    ArrayList<Path> files = new ArrayList<Path>();
    String filterPattern = ".*";

    @Override
    public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) throws IOException {
        if (Pattern.matches(filterPattern, aFile.toString())) {
            files.add(aFile);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) throws IOException {
        if (Pattern.matches(filterPattern, aDir.toString())) {
            dirs.add(aDir);
        }
        return FileVisitResult.CONTINUE;
    }

    public void collectAll(Path path) {
        this.filterPattern = ".*";
        try {
            Files.walkFileTree(path, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collect(Path path, String regEx) {
        this.filterPattern = regEx;
        try {
            Files.walkFileTree(path, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Path> getDirs() {
        return dirs;
    }

    public ArrayList<Path> getFiles() {
        return files;
    }

    public void reset() {
        getDirs().clear();
        getFiles().clear();
    }

    public Path getCollectedFile(String fileName) {
        Path searchedPath = null;
        for (Path currentFile : files) {
            if (currentFile.endsWith(fileName)) {
                searchedPath = currentFile;
                break;
            }
        }
        return searchedPath;
    }
}
