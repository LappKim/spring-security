package com.slowstarter.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
    @GetMapping(value = "/")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String       username = authentication.getName();
        StringBuffer sbRoles  = new StringBuffer();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        while(iterator.hasNext() == true) {
            GrantedAuthority auth = iterator.next();
            if(sbRoles.length() != 0) {
                sbRoles.append(", ");
            }
            sbRoles.append(auth.getAuthority());
        }

        log.trace("username => " + username);
        log.trace("sbRoles  => " + sbRoles);

        model.addAttribute("username", username);
        model.addAttribute("roles", sbRoles.toString());

        return "main";
    }
}
