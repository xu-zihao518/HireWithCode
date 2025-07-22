package org.example.interviewapidatabase.pojo;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class InterviewChallenge {
    private String githubId;
    private String repoUrl;
    private String vercelUrl;
    private LocalDateTime submitTime;
    private Integer id;
    private LocalDateTime acceptTime;
    private String email;
}
