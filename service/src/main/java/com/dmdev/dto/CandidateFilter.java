package com.dmdev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateFilter {
    String firstName;
    String lastName;
    String desirePosition;
    String hrEmail;
}
