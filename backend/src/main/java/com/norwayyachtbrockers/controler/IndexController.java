package com.norwayyachtbrockers.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Are you ready, kids?\n" +
                "Aye, aye, Captain!\n" +
                "I can't hear you!\n" +
                "Aye, aye, Captain!\n" +
                "Oh!\n" +
                "\n" +
                "Who lives in a pineapple under the sea?\n" +
                "SpongeBob SquarePants!\n" +
                "Absorbent and yellow and porous is he\n" +
                "SpongeBob SquarePants!\n" +
                "If nautical nonsense be something you wish\n" +
                "SpongeBob SquarePants!\n" +
                "Then drop on the deck and flop like a fish!\n" +
                "SpongeBob SquarePants! (Ready?!)\n" +
                "SpongeBob SquarePants!\n" +
                "SpongeBob SquarePants!\n" +
                "SpongeBob SquarePants!";
    }
}