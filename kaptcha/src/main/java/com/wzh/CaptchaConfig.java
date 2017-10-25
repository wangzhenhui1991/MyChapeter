package com.wzh;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

/**
 * Created by wangzhenhui on 2017/10/23.
 */
@Configuration
public class CaptchaConfig {

    /**
     *
     *
     <prop key="kaptcha.border">no</prop><!-- 是否有边框 -->
     <prop key="kaptcha.noise.color">25,25,25</prop><!-- 干扰线颜色 -->
     <prop key="kaptcha.obscurificator.impl">com.google.code.kaptcha.impl.ShadowGimpy</prop>
     <prop key="kaptcha.image.width">140</prop>
     <prop key="kaptcha.image.height">40</prop>
     <prop key="kaptcha.textproducer.char.string">AZWSXEDCRFVTGBYHNUJMIKLP23456789</prop>
     <prop key="kaptcha.textproducer.font.color">4,14,156</prop><!-- 验证码字体颜色 -->
     <prop key="kaptcha.textproducer.font.size">36</prop><!-- 验证码字体大小 -->
     <prop key="kaptcha.session.key">code</prop>
     <prop key="kaptcha.textproducer.char.length">5</prop><!-- 验证码个数 -->
     <prop key="kaptcha.textproducer.font.names">宋体,楷体,微软雅黑</prop>
     kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
     kaptcha.border.color   边框颜色   默认为Color.BLACK
     kaptcha.border.thickness  边框粗细度  默认为1
     kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
     kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
     kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
     kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
     kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
     kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
     kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
     kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
     kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
     kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
     kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
     kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
     kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
     kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
     kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
     kaptcha.image.width   验证码图片宽度  默认为200
     kaptcha.image.height  验证码图片高度  默认为50
     * @return
     */
    @Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border", "yes");
        //边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //验证码噪点颜色   默认为black
        properties.setProperty("kaptcha.noise.color","blue");
        //边框粗细度,默认为1
        properties.setProperty("kaptcha.border.thickness","2");
        //阴影
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        //验证码-字库
        properties.setProperty("kaptcha.textproducer.char.string","AZWSXEDCRFVTGBYHNUJMIKLP23456789途虎");
        //验证码-字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "green");
        //验证码-个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.space", "3");

        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "45");

        //验证码-sessionKey
        properties.setProperty("kaptcha.session.key", "code");

        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //验证码生成器  默认为DefaultKaptcha
//        properties.setProperty("kaptcha.producer.impl", "DefaultKaptcha");


        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
;