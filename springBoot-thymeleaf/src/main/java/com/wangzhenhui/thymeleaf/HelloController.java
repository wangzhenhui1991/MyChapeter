package com.wangzhenhui.thymeleaf;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {

    static List<Sequence> seqs;
    private static DataSource dataSource;

    static {
        seqs = new ArrayList<>();
        for (int i = 1; i < 500; i++) {
            seqs.add(new Sequence(String.valueOf(i), "testSeq" + i, 1, 1, 1));
        }

        dataSource = new DataSource(999);

    }


    @RequestMapping("/index")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://www.wangzhenhui.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        map.addAttribute("currentTime", new Date());
        map.addAttribute("seqs", seqs);
        return "index";
    }


    @RequestMapping(value = "/addseq", method = RequestMethod.POST)
    @ResponseBody
    public Sequence addSeq(@RequestBody Sequence seq) {
        try {
            seqs.add(seq);
            return seq;
        } catch (Exception e) {
            return null;
        }
    }


    @RequestMapping("/index/datahtml")
    public String index() {
        return "server";
    }


    @RequestMapping("/loadData")
    @ResponseBody
    public String loadData(HttpServletRequest req) {

        Integer drawStr     = Integer.parseInt(req.getParameter("draw"));
        Integer startStr    = Integer.parseInt(req.getParameter("start"));
        Integer lengthStr   = Integer.parseInt(req.getParameter("length"));
//        Integer searchStr   = Integer.parseInt(req.getParameter("search"));

        DataResponse response = new DataResponse(drawStr, dataSource, startStr,lengthStr);

        return JSON.toJSONString(response);
    }


}
