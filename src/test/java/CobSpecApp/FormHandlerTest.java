package CobSpecApp;

import junit.framework.TestCase;

public class FormHandlerTest extends TestCase {
    public void test_it_handles_a_basic_get_request_form() throws Exception {
        String formData = "<form action=\"http://foo.com\" method=\"get\">" +
                "<input name=\"say\" value=\"Hi\">" +
                "<input name=\"to\" value=\"Mom\">" +
                "<button>Send my greetings</button>" +
                "</form> ";
    }
}
//("<form action=\"http://foo.com\" method=\"post\">" +
//                "<input name=\"say\" value=\"Hi\">" +
//                "<input name=\"to\" value=\"Mom\">" +
//                "<button>Send my greetings</button>" +
//                "</form> ")
