package com.notefx.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MarkdownServiceTest {

    @Test
    void toHtmlConvierteEncabezadoBasico() {
        String html = MarkdownService.toHtml("# Hola");

        assertNotNull(html);
        assertTrue(html.contains("<h1>Hola</h1>"));
    }

    @Test
    void toHtmlAceptaValorNulo() {
        String html = MarkdownService.toHtml(null);

        assertNotNull(html);
    }
}
