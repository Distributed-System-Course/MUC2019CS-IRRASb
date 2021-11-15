package com.example.houseSearch.controller;

import com.example.houseSearch.service.loginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class loginController {

    @Resource
    loginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("username") String name,@ModelAttribute("password") String password){

        ModelAndView mv = new ModelAndView();

        if(loginService.isIn(name,password) ){
            mv.setViewName("search");
        }else {
            mv.setViewName("loginF");
        }

        return mv;

    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("name") String name,@ModelAttribute("password") String password,@ModelAttribute("tehPassword") String thePassword){

        ModelAndView mv = new ModelAndView();

        if( !password.equals(thePassword)){
            mv.addObject("err","两次密码不一致！");
            mv.setViewName("register");
        }else {

            mv.setViewName("search");
        }




        return mv;
    }


}
