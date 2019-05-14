package front_end.utils;

public class XMLGenerator {
    public static String makeXML(String[] cmdStrHistory, String outputContents) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<Root doctype=\"environment\">\n" +
                "    <Commands>" +
                String.join(" ", cmdStrHistory) +
                "</Commands>\n" +
                "    <Output>" + outputContents + "</Output>\n" +
                "</Root>";
    }
}
