package com.example.pokemons.testdata.common;

import java.util.stream.Stream;

public class CommonStringTestData {


    public static Stream<String> validStringValues() {
        return Stream.of("Asj","Pikachu","Brock","Squirrle","Lesnar","Jenna","Fire","Earth","Ketcham","A"+("c".repeat(99)));
    }

    public static Stream<String> validStringValuesWithWhiteSigns() {
        return Stream.of(" Ash"," Pikachu ","Lesnar   ","  Earth   ");
    }

    public static Stream<String> invalidStringValuesTooManyCapitalsOrNotAny() {
        return Stream.of("ASH","PikaChu","brocK","lesnar","eaRth");
    }

    public static Stream<String> invalidValuesNullOrEmpty() {
        return Stream.of(null,"","       "," ");
    }

    public static Stream<String> invalidLengthValues() {
        return Stream.of("A".repeat(101),"B".repeat(120));
    }


}
