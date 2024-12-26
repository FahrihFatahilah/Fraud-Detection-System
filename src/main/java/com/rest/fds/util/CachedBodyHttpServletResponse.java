package com.rest.fds.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private final CharArrayWriter charArrayWriter = new CharArrayWriter();
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        this.writer = new PrintWriter(charArrayWriter);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Use getWriter() instead for capturing response body.");
    }

    public String getCapturedResponseBody() {
        writer.flush();
        return charArrayWriter.toString();
    }
}