package com.norwayyachtbrockers.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return """
                Are you ready, kids?
                Aye, aye, Captain!
                I can't hear you!
                Aye, aye, Captain!
                Oh!

                Who lives in a pineapple under the sea?
                SpongeBob SquarePants!
                Absorbent and yellow and porous is he
                SpongeBob SquarePants!
                If nautical nonsense be something you wish
                SpongeBob SquarePants!
                Then drop on the deck and flop like a fish!
                SpongeBob SquarePants! (Ready?!)
                SpongeBob SquarePants!
                SpongeBob SquarePants!
                SpongeBob SquarePants!""";
    }
}