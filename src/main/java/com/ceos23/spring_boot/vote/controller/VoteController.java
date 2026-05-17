package com.ceos23.spring_boot.vote.controller;

import com.ceos23.spring_boot.global.response.ApiResponse;
import com.ceos23.spring_boot.vote.dto.VoteRequest;
import com.ceos23.spring_boot.vote.dto.VoteResponse;
import com.ceos23.spring_boot.vote.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vote", description = "투표 관련 API")
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "투표하기", description = "후보 ID와 투표 유형을 입력받아 투표합니다.")
    @PostMapping
    public ApiResponse<VoteResponse> vote(@RequestBody VoteRequest request) {
        VoteResponse response = voteService.vote(request);
        return ApiResponse.ok("투표 성공", response);
    }
}