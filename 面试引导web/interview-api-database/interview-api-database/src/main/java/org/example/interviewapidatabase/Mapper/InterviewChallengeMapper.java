package org.example.interviewapidatabase.Mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.interviewapidatabase.pojo.InterviewChallenge;

@Mapper
public interface InterviewChallengeMapper {
    @Insert("INSERT into interview_challenges(github_id,email) values(#{githubId},#{email})")
    void addchallenge(InterviewChallenge interviewChallenge);
    @Update("update interview_challenges set repo_url=#{repoUrl},vercel_url=#{vercelUrl},submit_time=NOW()where github_id=#{githubId}")
    void addfinished(InterviewChallenge interviewChallenge);
}
