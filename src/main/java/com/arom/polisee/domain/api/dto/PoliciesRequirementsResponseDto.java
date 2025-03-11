package com.arom.polisee.domain.api.dto;

import com.arom.polisee.domain.policy_requirements.dto.PolicyRequirementsDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoliciesRequirementsResponseDto {
    private List<PolicyRequirementsDTO> data;

}
