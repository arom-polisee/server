package com.arom.polisee.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyDetailResponseDto {
    private List<PolicyDetail1Dto> data;

}
