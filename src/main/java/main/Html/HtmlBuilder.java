package main.Html;

import main.Backup.model.Backup;
import org.springframework.stereotype.Service;

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


    public String buildAllPreviousBackupsTable(Iterable<Backup> allPreviousBackups) {
        StringBuilder tableHtmlBuilder = new StringBuilder();
        tableHtmlBuilder.append(
                "<table>"
        );
        for (Backup b : allPreviousBackups) {
            tableHtmlBuilder.append(buildTableRow(b));
        }
        tableHtmlBuilder.append(
                "</table>"
        );
        return tableHtmlBuilder.toString();
    }


    public String buildTableRow(Backup backup) {
        return "<tr><td>test</td></tr>";
    }
}
