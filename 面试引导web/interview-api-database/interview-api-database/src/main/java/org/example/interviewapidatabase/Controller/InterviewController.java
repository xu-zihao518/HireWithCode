package org.example.interviewapidatabase.Controller;

import org.example.interviewapidatabase.pojo.InterviewChallenge;
import org.example.interviewapidatabase.pojo.Result;
import org.example.interviewapidatabase.Service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    @Autowired
    private InterviewService interviewService;
    @PostMapping()
    public Result add(@RequestBody  InterviewChallenge interviewChallenge) {
        interviewService.add(interviewChallenge);
        return Result.success();
    }
    @PutMapping()
    public Result update(@RequestBody @Validated InterviewChallenge interviewChallenge){
        interviewService.update(interviewChallenge);
        return Result.success();
    }
}
