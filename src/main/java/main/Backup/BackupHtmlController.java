package main.Backup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/html/backups")
public class BackupHtmlController {
    @GetMapping(path="")
    public @ResponseBody
    String getAllInHtml() {
        return "test";
    }
}
