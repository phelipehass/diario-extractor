package com.transprenciajoinville.diarioextractor.domain;

import com.transprenciajoinville.diarioextractor.statics.StreetTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rua {

	private long id;
	private String name;
	private StreetTypes type;
}
