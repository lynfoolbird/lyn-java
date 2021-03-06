package com.lynjava.ddd.test;

import com.lynjava.ddd.test.dto.BaseResponse;
import com.lynjava.ddd.test.dto.LiveResponseCode;
import com.lynjava.ddd.test.strategy.MainOperateService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/test")
public class TestController extends BaseAdminController{

    @Autowired
    private MainOperateService mainOperateService;

    @Autowired
    private ExecutorService executorService;

    @GetMapping("/{strategy}")
    public String doOperate(@PathVariable("strategy") String strategy) {
        mainOperateService.doOperate(strategy);
        return "success";
    }

    @GetMapping("/test2")
    public String test2() {
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(2000L);
                return null;
            }
        };
        executorService.submit(callable);
        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
        return "kkkk";
    }

    @RequestMapping("/test3.do")
    public BaseResponse test3(){
        return msgResponse(LiveResponseCode.LIVE_ROOM_HAS_DELETED);
    }

    @RequestMapping("/test4.do")
    public BaseResponse test4(){
        return msgFormatResponse(LiveResponseCode.LIVE_ROOM_START, new Date());
    }


    @RequestMapping("/getVerifyImg.do")
    public BaseResponse getVerifyImg(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        try {
            int width = 100;
            int height = 60;
            Random random = new Random();
            //??????response?????????
            //????????????
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            //???????????????image???
            BufferedImage image = new BufferedImage(width, height, 1);
            //??????image??????Graphics??????????????????
            Graphics g = image.getGraphics();
            //Graphics????????????
            g.setColor(this.getRandColor(200, 250));
            g.setFont(new Font("Times New Roman",0,28));
            g.fillRect(0, 0, width, height);
            //???????????????
            for(int i=0;i<40;i++){
                g.setColor(this.getRandColor(130, 200));
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(12);
                int y1 = random.nextInt(12);
                g.drawLine(x, y, x + x1, y + y1);
            }
            //????????????
            String strCode = RandomStringUtils.randomAlphanumeric(4);
            for (int i=0; i<4; i++) {
                g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
                g.drawString(String.valueOf(strCode.charAt(i)), 13*i+6, 28);
            }
            //??????????????????session????????????????????????
            session.setAttribute("strCode", strCode);
            g.dispose();

            ImageIO.write(image, "JPEG", response.getOutputStream());
            response.getOutputStream().flush();

            return new BaseResponse(0, "success");
        } catch (Exception e) {
            return msgResponse(LiveResponseCode.LIVE_ROOM_HAS_DELETED);
        }
    }

    private Color getRandColor(int fc, int bc){
        Random random = new Random();
        if(fc>255) {
            fc = 255;
        }
        if(bc>255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }
}
