package ro.ubb.monitorweb.converter;

import ro.ubb.monitorcore.model.County;
import ro.ubb.monitorweb.dto.CountyDto;

public class CountyConverter {
    public static CountyDto convertCountServerToCountyDto(County county) {
        return CountyDto.builder()
                .countyName(county.getCountyName())
                .a(county.getCandidateA())
                .b(county.getCandidateB())
                .c(county.getCandidateC())
                .nr(county.getNr())
                .build();
    }
}
