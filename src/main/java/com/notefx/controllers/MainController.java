package com.notefx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ListView<?> notesList;

    @FXML
    private TextArea noteInput;

    @FXML
    private WebView preview;

    // Use reflection for flexmark to avoid compile-time module dependencies
    private Object mdParser;
    private Object mdRenderer;
    private Class<?> parserClass;
    private Class<?> rendererClass;
    private Class<?> flexmarkNodeClass;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar flexmark (Markdown -> HTML) mediante reflection si está presente
        try {
            parserClass = Class.forName("com.vladsch.flexmark.parser.Parser");
            rendererClass = Class.forName("com.vladsch.flexmark.html.HtmlRenderer");
            flexmarkNodeClass = Class.forName("com.vladsch.flexmark.util.ast.Node");

            Object parserBuilder = parserClass.getMethod("builder").invoke(null);
            mdParser = parserBuilder.getClass().getMethod("build").invoke(parserBuilder);

            Object rendererBuilder = rendererClass.getMethod("builder").invoke(null);
            mdRenderer = rendererBuilder.getClass().getMethod("build").invoke(rendererBuilder);
        } catch (ClassNotFoundException cnfe) {
            // flexmark no está en el classpath/module-path — lo manejamos mostrando texto sin formato
            mdParser = null;
            mdRenderer = null;
            flexmarkNodeClass = null;
        } catch (Exception e) {
            mdParser = null;
            mdRenderer = null;
            flexmarkNodeClass = null;
        }

        // Si el TextArea y el WebView están presentes, añadimos un listener para previsualizar Markdown
        if (noteInput != null && preview != null) {
            WebEngine engine = preview.getEngine();

            ChangeListener<String> listener = (obs, oldText, newText) -> {
                if (mdParser != null && mdRenderer != null) {
                    try {
                        Object node = parserClass.getMethod("parse", CharSequence.class).invoke(mdParser, newText == null ? "" : newText);
                        String html = (String) rendererClass.getMethod("render", flexmarkNodeClass).invoke(mdRenderer, node);
                        engine.loadContent(html);
                    } catch (Exception e) {
                        // Si ocurre algún problema con reflection, mostrar texto sin formato
                        engine.loadContent("<pre>" + escapeHtml(newText == null ? "" : newText) + "</pre>");
                    }
                } else {
                    // si no hay flexmark, mostramos el texto sin formato dentro de <pre>
                    engine.loadContent("<pre>" + (newText == null ? "" : escapeHtml(newText)) + "</pre>");
                }
            };

            // Listener inicial y suscripción
            noteInput.textProperty().addListener(listener);
            // Si ya había texto, forzamos render inicial
            listener.changed(null, null, noteInput.getText());
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    // Método de ayuda para obtener el texto de la nota
    public String getNoteText() {
        return noteInput != null ? noteInput.getText() : "";
    }
}
