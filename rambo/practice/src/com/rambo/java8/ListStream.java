package com.rambo.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuzhu on 7/1/19.
 */
public class ListStream {


    public static void main(String[] args) {

        filterList();
    }

    private static void filterList() {
        String subjectDN = "test";

        List<String> lines = Arrays.asList("95352495352409379025983280937902598328|L=test",
                "test asdhlh asldhljksa ", "9535240937902598328|L=testc");

        List<String> result = lines.stream()                // convert list to stream
                .filter(line -> line.endsWith(subjectDN))     // we need only that end with subjectDN
                .collect(Collectors.toList());              // collect the output and convert streams to a List
        result.forEach(System.out::println);                //output : 9535240937902598328|L=Bangalore, ST=KA, C=IN, O=DMPD, CN=Visa Inc
    }

}
