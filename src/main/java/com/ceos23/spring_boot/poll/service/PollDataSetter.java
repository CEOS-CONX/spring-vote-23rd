package com.ceos23.spring_boot.poll.service;

import com.ceos23.spring_boot.global.type.VoteType;
import com.ceos23.spring_boot.poll.domain.Candidate;
import com.ceos23.spring_boot.poll.domain.Poll;
import jakarta.annotation.PostConstruct;

import java.util.List;

public class PollDataSetter {
    @PostConstruct
    public void voteSetting() {
        Poll fePartPoll = Poll.of(
                "FE 파트장 투표", VoteType.PART_LEADER
        );

        List<String> feCandidateNames = List.of("박유민", "권오진", "이윤서", "구민교", "이승연", "황영준", )
        Candidate fePartCandidates = Candidate.of()
    }
}

//public static Candidate of(Poll poll, String name, Part part, Team team) {
//        return new Candidate(poll, name, part, team);
//    }

//1. FE 파트장 투표
//   - voteType: PART_LEADER
//   - 후보(name): 박유민, 권오진, 이윤서, 구민교, 이승연, 황영준, 남기림, 김민서, 김홍엽, 오유진
//2. BE 파트장 투표
//   - voteType: PART_LEADER
//   - 후보(name): 임종훈, 안준석, 황신애, 최우혁, 김동욱, 최승원, 오지송, 김태익, 김태희, 김도현
//3. 데모데이 투표
//   - voteType: DEMO_DAY
//   - 후보(name): Ditda, JobDri, Groupeat, IPX, CONX