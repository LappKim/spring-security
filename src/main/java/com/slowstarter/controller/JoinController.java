package com.slowstarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.slowstarter.dto.JoinDto;
import com.slowstarter.service.JoinService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JoinController
{
    private final JoinService joinService;

    public JoinController(JoinService joinService)
    {
        this.joinService = joinService;
    }
    @GetMapping(value = "/join")
    public String joinPage() {
        return "join";
    }
    @PostMapping(value = "/joinProc")
    public String joinProcess(JoinDto joinDto) {
        log.trace("joinDto -> [{}]", joinDto);
        joinService.joinProcess(joinDto);
        return "redirect:/login";
    }
}
