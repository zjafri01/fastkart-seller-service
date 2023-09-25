package com.fastkart.sellerservice.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomMultipartFile implements MultipartFile {

    private byte[] input;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }
    //We've defined the rest of the interface methods in the next snippet

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }


    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        Path path = Paths.get(destination.getPath());
        Files.write(path, input);
    }
}