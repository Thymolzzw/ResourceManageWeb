package com.example.demo.common.controller;


import com.example.demo.common.service.CommonService;
import com.example.demo.entity.UserTable;
import com.example.demo.user.service.UserService;
import com.example.demo.utils.VerifyUtil;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Controller
public class UserTableController {
    @Autowired
    CommonService commonService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/")
    public String userToLogin(){
        System.out.println("进入 /");
        return "/common/login";
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, UserTable userTable, String verifyCode){
        System.out.println("用户输入内容如下：");
        System.out.println("----------------用户名为："+userTable.getUserName());
        System.out.println("----------------密码为："+userTable.getPassword());
        System.out.println("----------------用户类型为："+userTable.getUserType());
        System.out.println("----------------验证码为："+verifyCode);


//        String succCode= (String) request.getSession().getAttribute("imageCode");
//        succCode = succCode.toLowerCase();
//        String userCode = verifyCode.toLowerCase();
//        if(!succCode.equals(userCode)) {
//            request.setAttribute("msg","验证码错误");
//            return "/common/login";
//        }

        UserTable user=commonService.userLogin(userTable);

        if(user!=null){
            //用户存在
            request.getSession().setAttribute("user",user);
            if(userTable.getUserType()==1){
                //注册用户
                return "registeruser/index";
            }else{
                //管理员用户
                return "admuser/index";
            }
        }else{
            //用户不存在
            request.setAttribute("msg","用户名或密码输入错误！");
            return "common/login";
        }


    }



    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        System.out.println("用户退出！");
        return "redirect:/";
    }

    @RequestMapping(value = "/seePrivateInformation")
    public String userPrivateInformation(){

        return "registeruser/seePrivateInformation";
    }

    @RequestMapping(value = "/modifyPrivateInformation")
    public String seePrivateInformation(){
        return "registeruser/modifyPrivateInformation";
    }

    @RequestMapping("/modifiedPrivateInformationToDB")
    public String modifiedPrivateInformationToDB(HttpServletResponse response,HttpServletRequest request, UserTable userTable){
        UserTable user=(UserTable) request.getSession().getAttribute("user");
        System.out.println("用户修改之前个人信息如下：");
        showUserInformation(user);

        user.setUserName(userTable.getUserName());
        user.setPassword(userTable.getPassword());
        user.setGender(userTable.getGender());
        user.setEmail(userTable.getEmail());
        user.setPhone(userTable.getPhone());

        System.out.println("用户修改之后个人信息如下：");
        showUserInformation(user);
        int flag=commonService.updateUser(user);
        if(flag!=0){
            request.getSession().setAttribute("user",user);
        }
        if(user.getUserType()==1){
            return "registeruser/seePrivateInformation";
        }else{
            return "admuser/seePrivateInformationAdm";
        }
    }

    private void showUserInformation(UserTable user) {
        System.out.println("----------------用户ID为："+user.getUserID());
        System.out.println("----------------用户名为："+user.getUserName());
        System.out.println("----------------密码为："+user.getPassword());
        System.out.println("----------------用户类型为："+user.getUserType());
        System.out.println("----------------用户性别为："+user.getGender());
        System.out.println("----------------用户邮箱为："+user.getEmail());
        System.out.println("----------------用户手机号为："+user.getPhone());
    }


    @GetMapping("/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @RequestMapping(value = "/registerAccount")
    public String registerAccount(){

        return "/common/registerAccount";
    }

    @RequestMapping(value = "/doRegister")
    public String doRegister(HttpServletRequest request){
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String gender=request.getParameter("gender");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        System.out.println("userName:"+userName);
        System.out.println("password:"+password);
        System.out.println("gender:"+gender);
        System.out.println("email:"+email);
        System.out.println("phone:"+phone);
        UserTable userTable=new UserTable();
        userTable.setUserName(userName);
        userTable.setPassword(password);
        userTable.setGender(gender);
        userTable.setUserType(1);
        if(email.equals("")){
            System.out.println("email为空串");
        }else{
            userTable.setEmail(email);
        }
        if(phone.equals("")){
            System.out.println("phone为空串");
        }else{
            userTable.setPhone(phone);
        }
        UserTable test=userService.searchUserByName(userName);
        if(test!=null){
            //数据库中已经存在此用户
            System.out.println("注册失败！");
            request.setAttribute("errormsg","用户已存在，请重新注册！");
            return "/common/registerAccount";
        }else{
            //数据库中不存在此用户
            userService.addUser(userTable);
            System.out.println("注册成功！");
            return "/common/registerok";
        }
    }
    
}
