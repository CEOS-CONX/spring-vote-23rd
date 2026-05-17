package com.ceos23.spring_boot.vote.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "투표 응답")
public class VoteResponse {

    @Schema(description = "투표 ID", example = "1")
    private final Long voteId;

    @Schema(description = "후보 ID", example = "1")
    private final Long candidateId;

    @Schema(description = "투표 유형", example = "PART_LEADER")
    private final String voteType;

    private VoteResponse(Long voteId, Long candidateId, String voteType) {
        this.voteId = voteId;
        this.candidateId = candidateId;
        this.voteType = voteType;
    }

    public static VoteResponse of(Long voteId, Long candidateId, String voteType) {
        return new VoteResponse(voteId, candidateId, voteType);
    }

    public Long getVoteId() {
        return voteId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getVoteType() {
        return voteType;
    }
}