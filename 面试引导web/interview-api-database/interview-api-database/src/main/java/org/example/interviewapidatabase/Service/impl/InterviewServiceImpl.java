package org.example.interviewapidatabase.Service.impl;

import org.example.interviewapidatabase.Mapper.InterviewChallengeMapper;
import org.example.interviewapidatabase.Service.InterviewService;
import org.example.interviewapidatabase.pojo.InterviewChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    private InterviewChallengeMapper interviewChallengeMapper;
    @Override
    public void add(InterviewChallenge interviewChallenge) {
        interviewChallengeMapper.addchallenge(interviewChallenge);
    }

    @Override
    public void update(InterviewChallenge interviewChallenge) {
        interviewChallengeMapper.addfinished(interviewChallenge);
    }
}
