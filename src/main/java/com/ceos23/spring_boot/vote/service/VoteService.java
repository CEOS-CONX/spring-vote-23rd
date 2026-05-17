package com.ceos23.spring_boot.vote.service;

import com.ceos23.spring_boot.global.exception.CustomException;
import com.ceos23.spring_boot.global.exception.ErrorCode;
import com.ceos23.spring_boot.vote.dto.VoteRequest;
import com.ceos23.spring_boot.vote.dto.VoteResponse;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    public VoteResponse vote(VoteRequest request) {
        if (request.getCandidateId() == null) {
            throw new CustomException(ErrorCode.CANDIDATE_NOT_FOUND);
        }

        return VoteResponse.of(1L, request.getCandidateId(), request.getVoteType());
    }
}