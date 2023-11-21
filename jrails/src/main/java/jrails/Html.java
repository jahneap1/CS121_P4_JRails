package jrails;

public class Html {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public Html() {
        this.text = "";
    }

    public Html(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }

    public Html seq(Html h) {
        return new Html(text + h.toString());
    }

    public Html br() {
        return this.seq(View.br());
    }

    public Html t(Object o) {
        return this.seq(new Html(o.toString()));
    }

    public Html p(Html child) {
        return this.seq(View.p(child));
    }

    public Html div(Html child) {
        return this.seq(View.div(child));
    }

    public Html strong(Html child) {
        return this.seq(View.strong(child));
    }

    public Html h1(Html child) {
        return this.seq(View.h1(child));
    }

    public Html tr(Html child) {
        return this.seq(View.tr(child));
    }

    public Html th(Html child) {
        return this.seq(View.th(child));
    }

    public Html td(Html child) {
        return this.seq(View.td(child));
    }

    public Html table(Html child) {
        return this.seq(View.table(child));
    }

    public Html thead(Html child) {
        return this.seq(View.thead(child));
    }

    public Html tbody(Html child) {
        return this.seq(View.tbody(child));
    }

    public Html textarea(String name, Html child) {
        return this.seq(View.textarea(name, child));
    }

    public Html link_to(String text, String url) {
        return this.seq(View.link_to(text, url));
    }

    public Html form(String action, Html child) {
        return this.seq(View.form(action, child));
    }

    public Html submit(String value) {
        return this.seq(View.submit(value));
    }
}