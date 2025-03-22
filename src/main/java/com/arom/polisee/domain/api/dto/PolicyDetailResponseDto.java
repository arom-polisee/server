package com.arom.polisee.domain.api.dto;

import com.arom.polisee.domain.policy_detail.dto.PoliciyDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyDetailResponseDto {
    private List<PoliciyDetailDTO> data;

}
