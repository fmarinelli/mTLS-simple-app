package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@WebServlet
public class ServletController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ServletController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("MY HTTP Version 1.0");
        String headers = enumerationAsStream(req.getHeaderNames())
                .map(s -> {
                    String values = enumerationAsStream(req.getHeaders(s)).collect(Collectors.joining(","));
                    return s + ":[" + values + "]";
                })
                .collect(Collectors.joining("\n"));

        logger.info("Incoming request [" + req.getServletPath() + "] headers: " + headers);
    }

    public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new Iterator<T>() {
                            public T next() {
                                return e.nextElement();
                            }

                            public boolean hasNext() {
                                return e.hasMoreElements();
                            }
                        },
                        Spliterator.ORDERED), false);
    }
}
