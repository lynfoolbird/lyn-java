package com.lynjava.ddd.controller;

import com.lynjava.ddd.common.annotation.DistributeLock;
import com.lynjava.ddd.common.consts.RedisLockTypeEnum;
import com.lynjava.ddd.common.model.BaseResponse;
import com.lynjava.ddd.common.model.LiveResponseCode;
import com.lynjava.ddd.common.context.DddAppContext;
import com.lynjava.ddd.test.architecture.designpattern.strategy.MainOperateService;
import com.lynjava.ddd.test.spring.integrate.external.ITestRpcService;
import com.lynjava.limiter.annotation.LynLimit;
import com.lynjava.limiter.manager.ILockService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/test")
@Validated
public class TestRestController extends BaseAdminController {

    @Autowired
    private MainOperateService mainOperateService;
    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ILockService lockService;

    @Autowired
    private ITestRpcService testRpcService;

    @GetMapping("/{strategy}")
    public String doOperate(@PathVariable("strategy") String strategy,
                            @RequestParam("type") String type,
                            @RequestHeader("changeNo") String changeNo) {
        mainOperateService.doOperate(strategy);
        mainOperateService.doOperate2(type);
        MainOperateService service = DddAppContext.getContext().getBean(MainOperateService.class);
        service.doOperate(strategy);
        return "success";
    }

    @PatchMapping("/test2.do")
    public String test2() {
        Callable callable = () -> {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(2000L);
            return null;
        };
        executorService.submit(callable);
        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
        return "kkkk";
    }

    @RequestMapping(value = {"/test3.do"}, method = RequestMethod.GET)
    public BaseResponse test3(){
        lockService.lock("orderId001", "123");
        lockService.unLock("orderId001", "123");
        return msgResponse(LiveResponseCode.LIVE_ROOM_HAS_DELETED);
    }

    @PutMapping("/test4.do")
    @DistributeLock(typeEnum = RedisLockTypeEnum.ONE, lockTimeout = 2)
    public BaseResponse test4(@QueryParam("orderId") String orderId) {
        return testRpcService.sendJob("helloworld");
    }

    @PostMapping("/test5.do")
    @LynLimit(permitsPerSecond = 1, key = "test5")
    public BaseResponse test5() {
        return msgFormatResponse(LiveResponseCode.LIVE_ROOM_START, new Date());
    }

    @DeleteMapping("/test6.do")
    public BaseResponse test6(){
        return msgFormatResponse(LiveResponseCode.LIVE_ROOM_START, new Date());
    }

    @GetMapping("/test10.do")
    public BaseResponse test10(@Size(max = 1, message = "id less than 1")
                                   @RequestParam("id") String id){
        return msgFormatResponse(LiveResponseCode.LIVE_ROOM_START, new Date());
    }

    @RequestMapping("/getVerifyImg.do")
    public BaseResponse getVerifyImg(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        try {
            int width = 100;
            int height = 60;
            Random random = new Random();
            //设置response头信息
            //禁止缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            //生成缓冲区image类
            BufferedImage image = new BufferedImage(width, height, 1);
            //产生image类的Graphics用于绘制操作
            Graphics g = image.getGraphics();
            //Graphics类的样式
            g.setColor(this.getRandColor(200, 250));
            g.setFont(new Font("Times New Roman",0,28));
            g.fillRect(0, 0, width, height);
            //绘制干扰线
            for(int i=0;i<40;i++){
                g.setColor(this.getRandColor(130, 200));
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(12);
                int y1 = random.nextInt(12);
                g.drawLine(x, y, x + x1, y + y1);
            }
            //绘制字符
            String strCode = RandomStringUtils.randomAlphanumeric(4);
            for (int i=0; i<4; i++) {
                g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
                g.drawString(String.valueOf(strCode.charAt(i)), 13*i+6, 28);
            }
            //将字符保存到session中用于前端的验证
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
