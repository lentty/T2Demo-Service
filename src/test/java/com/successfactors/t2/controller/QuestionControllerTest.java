package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Option;
import com.successfactors.t2.domain.Question;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionControllerTest {

    public static void main(String... args){
        ObjectMapper mapper = new ObjectMapper();
        Question question = constructQuestion();
        try{
            String jsonString = mapper.writeValueAsString(question);
            System.out.println(jsonString);
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(question);
            System.out.println(jsonString);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Question constructQuestion(){
        Question question = new Question();
        question.setOwner("oCNCe4vsdC_dr0wv6uY7uus1GheA");
        question.setContent("what's your name?");
        List<Option> options = new ArrayList<>();
        options.add(new Option("A", "ada", 1));
        options.add(new Option("B", "bella", 0));
        options.add(new Option("C", "cici", 0));
        options.add(new Option("C", "delta", 0));
        question.setOptions(options);
        return question;
    }

}
