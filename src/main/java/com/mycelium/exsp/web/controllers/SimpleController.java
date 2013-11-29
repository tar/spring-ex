package com.mycelium.exsp.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycelium.exsp.model.dao.JdbcCrudDao;
import com.mycelium.exsp.model.entities.UserEntity;

@Controller
public class SimpleController {

    @Autowired
    private JdbcCrudDao<UserEntity> _userdao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ModelAndView getRootPage() {
        ModelAndView mav = new ModelAndView("root");
        mav.addObject("time", new Date());
        return mav;
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    ModelAndView getAjaxPage() {
        ModelAndView mav = new ModelAndView("ajax");
        return mav;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    Map<String, Object> getUsers() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("items", _userdao.findAll());
        return result;
    }

    @RequestMapping(value = "/lection", method = RequestMethod.GET)
    ModelAndView getLectionPage() {
        ModelAndView mav = new ModelAndView("lection", "user", new UserEntity());
        return mav;
    }

    @RequestMapping(value = "/lection/model", method = RequestMethod.POST)
    ModelAndView processForm(@ModelAttribute(value = "user") UserEntity user) {
        System.out.println("name = " + user.getId());
        System.out.println("name = " + user.getLogin());
        System.out.println("email = " + user.getEmail());
        if (user.getId() == null) {
            _userdao.insert(user);
        }
        ModelAndView mav = new ModelAndView("lection", "user", user);
        return mav;
    }

    @RequestMapping(value = "/lection", method = RequestMethod.POST)
    ModelAndView processForm(HttpServletRequest request, @RequestParam(value = "myname") String name) {
        Map<String, Object> params = request.getParameterMap();
        System.out.println(request.getQueryString());
        for (Entry<String, Object> entry : params.entrySet()) {
            System.out.println("name = " + entry.getKey().toString());
            System.out.println("value = " + entry.getValue().getClass().getCanonicalName());
            System.out.println(request.getParameter(entry.getKey()));
        }
        ModelAndView mav = new ModelAndView("lection");
        return mav;
    }
}
