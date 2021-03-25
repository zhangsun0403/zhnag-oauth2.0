package zhang.resources_zhang.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {

    @PostMapping("/user")  // 测试路径
    @PreAuthorize("hasAnyAuthority('admin')")
    public String user(){


        return "资源服务器.........";
    }
}
