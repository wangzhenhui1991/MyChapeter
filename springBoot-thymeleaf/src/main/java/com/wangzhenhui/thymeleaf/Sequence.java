package com.wangzhenhui.thymeleaf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sequence{
    String id ;
    String seqName;
    long start;
    long end;
    long tenant;
}