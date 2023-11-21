package jrails;

public class View {
    public static Html empty() {
        return new Html();
        // throw new UnsupportedOperationException();
    }

    public static Html br() {
        return new Html("<br/>");
        // throw new UnsupportedOperationException();
    }

    public static Html t(Object o) {
        // Use o.toString() to get the text for this
        return new Html(o.toString());
        // throw new UnsupportedOperationException();
    }

    public static Html p(Html child) {
        String txt = "<p>"+ child.toString()+"</p>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html div(Html child) {
        String txt = "<div>"+ child.toString()+"</div>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html strong(Html child) {
        String txt = "<strong>"+ child.toString()+"</strong>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html h1(Html child) {
        String txt = "<h1>"+ child.toString()+"</h1>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html tr(Html child) {
        String txt = "<tr>"+ child.toString()+"</tr>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html th(Html child) {
        String txt = "<th>"+ child.toString()+"</th>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html td(Html child) {
        String txt = "<td>"+ child.toString()+"</td>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html table(Html child) {
        String txt = "<table>"+ child.toString()+"</table>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html thead(Html child) {
        String txt = "<thead>"+ child.toString()+"</thead>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html tbody(Html child) {
        String txt = "<tbody>"+ child.toString()+"</tbody>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html textarea(String name, Html child) {
        String txt = "<textarea name=\"" +name+ "\">" +child.toString()+ "</textarea>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html link_to(String text, String url) {
        String txt = "<a href=\"" +url+ "\">" +text+ "</a>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html form(String action, Html child) {
        String txt = "<form action=\"" +action+ "\" accept-charset=\"UTF-8\" method=\"post\">" +child.toString()+ "</form>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }

    public static Html submit(String value) {
        String txt = "<input type=\"submit\" value=\"" +value+ "\"/>";
        return new Html(txt);
        // throw new UnsupportedOperationException();
    }
}