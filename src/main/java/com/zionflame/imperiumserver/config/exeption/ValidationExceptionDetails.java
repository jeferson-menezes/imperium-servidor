package com.zionflame.imperiumserver.config.exeption;

import java.util.List;

import com.zionflame.imperiumserver.controller.dto.ValidatorDetailsDto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {

	private static final long serialVersionUID = 2857156433883976294L;
	
	private final List<ValidatorDetailsDto> fields;
}
