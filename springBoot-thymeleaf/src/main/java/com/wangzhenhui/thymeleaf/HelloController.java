package com.wangzhenhui.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {

    static List<Sequence> seqs;
    static {
        seqs = new ArrayList<>();
        for(int i = 1;i<50;i++){
            seqs.add(new Sequence(String.valueOf(i),"testSeq"+i,1,1,1));
        }

    }
    @RequestMapping("/index")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://www.wangzhenhui.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        map.addAttribute("currentTime",new Date());
        map.addAttribute("seqs",seqs);
        return "index";
    }


    @RequestMapping(value = "/addseq",method = RequestMethod.POST)
    @ResponseBody
    public Sequence addSeq(@RequestBody Sequence seq){
        try {
            seqs.add(seq);
            return seq;
        }catch (Exception e){
            return null;
        }
    }



}
