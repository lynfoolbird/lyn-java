package com.lynjava.ddd.test.java.jdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Jdk8StreamDemo {
    public static void main(String[] args) {
        List<TestDto> list = new ArrayList<>();
        TestDto dto1 = new TestDto("01", "Maradona");
        TestDto dto2 = new TestDto("02", "Messi");
        TestDto dto3 = new TestDto("03", "Ronaldo");
        list.add(dto1);
        list.add(dto2);
        List<String> names = list.stream()
                .filter(testDto -> Objects.equals("02", testDto.getId()))
                .map(TestDto::getName).collect(Collectors.toList());

        List<String> hostPorts = list.stream()
                .map(dto -> dto.getId() + ":" + dto.getName())
                .collect(Collectors.toList());
        // map
//        Map<String, TestDto> map = list.stream()
//                .map(TestDto::getName)
//                .collect(Collectors.toMap(TestDto::getId, Function.identity(), (dt1, dt2)->dt1));

        // groupingby
    }
    
    
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TestDto {
        private String id;
        private String name;
    }
}
