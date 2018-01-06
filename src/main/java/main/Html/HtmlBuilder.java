package main.Html;

import main.Backup.model.Backup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HtmlBuilder {
    public String[] getTemplate() {
        String[] array = new String[2];
        array[0] =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Title</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n";
        array[1] =
                "       </body>\n" +
                        "</html>";
        return  array;
    }


    public String buildTable(ArrayList<ArrayList<String>> allPreviousBackups) {
        StringBuilder tableHtmlBuilder = new StringBuilder();
        tableHtmlBuilder.append(
                "<table style=\"width:100%\" border=\"1\">"
        );
        for (ArrayList<String> l : allPreviousBackups) {
            tableHtmlBuilder.append(buildTableRow(l));
        }
        tableHtmlBuilder.append(
                "</table>"
        );
        return tableHtmlBuilder.toString();
    }


    public String buildTableRow(ArrayList<String> list) {
        StringBuilder row = new StringBuilder("<tr>");
        for (String e : list) {
            row.append("<td>").append(e).append("</td>");
        }
        row.append("</tr>");
        return row.toString();
    }
}
