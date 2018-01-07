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
        for (int i = 0; i < allPreviousBackups.size(); i++) {
            ArrayList<String> l = allPreviousBackups.get(i);
            tableHtmlBuilder.append(buildTableRow(l,i));
        }
        tableHtmlBuilder.append(
                "</table>"
        );
        return tableHtmlBuilder.toString();
    }


    public String buildTableRow(ArrayList<String> list, int index) {
        StringBuilder row = new StringBuilder("<tr>");
        for (String e : list) {
            row.append("<td>")
                    .append(e)
                    .append("</td>");
        }
        row.append("<td>" + "<a href=\"/html/servers/delete?id=")
                .append(index)
                .append("\"> delete </a>")
                .append("<td>")
                .append("</tr>");
        return row.toString();
    }
}
