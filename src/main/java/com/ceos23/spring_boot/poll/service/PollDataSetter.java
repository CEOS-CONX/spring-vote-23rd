package com.ceos23.spring_boot.poll.service;

import com.ceos23.spring_boot.global.type.VoteType;
import com.ceos23.spring_boot.poll.domain.Candidate;
import com.ceos23.spring_boot.poll.domain.Poll;
import com.ceos23.spring_boot.poll.repository.CandidateRepository;
import com.ceos23.spring_boot.poll.repository.PollRepository;
import com.ceos23.spring_boot.user.domain.Part;
import com.ceos23.spring_boot.user.domain.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollDataSetter {
    private final PollRepository pollRepository;
    private final CandidateRepository candidateRepository;

    public PollDataSetter(PollRepository pollRepository, CandidateRepository candidateRepository) {
        this.pollRepository = pollRepository;
        this.candidateRepository = candidateRepository;
    }

    @PostConstruct
    public void voteSetting() {
        Poll fePartPoll = Poll.of(
                "FE 파트장 투표", VoteType.PART_LEADER
        );

        Poll bePartPoll = Poll.of(
                "BE 파트장 투표", VoteType.PART_LEADER
        );

        List<String> feCandidateNames = List.of("박유민", "권오진", "이윤서", "구민교", "이승연", "황영준",
                "남기림", "김민서", "김홍엽", "오유진");
        List<String> beCandidateNames = List.of("임종훈", "안준석", "황신애", "최우혁", "김동욱", "최승원",
                "오지송", "김태익", "김태희", "김도현");

        List<Team> teams = List.of(Team.Ditda, Team.JobDri, Team.Groupeat, Team.IPX, Team.CONX);
        List<Candidate> feCandidates = new ArrayList<>();
        List<Candidate> beCandidates = new ArrayList<>();

        for (int i = 0; i < feCandidateNames.size(); i++) {
            feCandidates.add(Candidate.of(fePartPoll, feCandidateNames.get(i), Part.FRONTEND, teams.get(i / 2)));
            beCandidates.add(Candidate.of(bePartPoll, beCandidateNames.get(i), Part.BACKEND, teams.get(i / 2)));
        }

        Poll demodayPoll = Poll.of(
                "데모데이 투표", VoteType.DEMO_DAY
        );

        List<Candidate> teamCandidates = new ArrayList<>();
        for (Team team : teams) {
            teamCandidates.add(Candidate.of(demodayPoll, team.toString(), null, team));
        }

        pollRepository.saveAll(List.of(fePartPoll, bePartPoll, demodayPoll));
        candidateRepository.saveAll(feCandidates);
        candidateRepository.saveAll(beCandidates);
        candidateRepository.saveAll(teamCandidates);
    }
}