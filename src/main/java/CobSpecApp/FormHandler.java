package CobSpecApp;

import HTTPServer.*;

public class FormHandler implements Handler {
    private Setup settings;

    public FormHandler(Setup settings) {
        this.settings = settings;
    }

    public Response handle(Request request) {
        Response response = new Response();
        if (request.findAction().equals("GET")) {
            response.setHeader("HTTP/1.1 200 OK\r\n".getBytes());
            response.setBody(("<!DOCTYPE html><html lang=\"en\"><body>" + basicForm() + "</body></html>").getBytes());
        }
        return response;
    }

    private String basicForm() {
        return "<form action=\"/form\" method=\"post\">" +
                "<input name=\"say\">" +
                "<input name=\"to\">" +
                "<button>Send my greetings</button>" +
                "</form>";
    }
}
